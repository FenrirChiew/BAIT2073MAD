package my.edu.tarc.bait2073mad.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentHomeBinding
import my.edu.tarc.bait2073mad.ui.product.Product
import my.edu.tarc.bait2073mad.ui.product.ProductAdapter
import my.edu.tarc.bait2073mad.ui.product.RecordClickListener

class HomeFragment : Fragment(), MenuProvider, RecordClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                homeViewModel.searchQuery.value = query
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                homeViewModel.searchQuery.value = newText
                return true
            }
        })

        binding.fabAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addProductFragment)
        }

        val adapter = ProductAdapter(requireContext(), this)

        // Add observers for productList and searchQuery
        homeViewModel.productList.observe(viewLifecycleOwner) { productList ->
            homeViewModel.searchQuery.observe(viewLifecycleOwner) { searchQuery ->
                if (productList.isEmpty()) {
                    binding.textViewCount.isVisible = true
                    binding.textViewCount.text = getString(R.string.no_product)
                } else {
                    if (searchQuery.isEmpty()) {
                        homeViewModel.resetProducts()
                        adapter.setProduct(productList)
                    } else {
                        val newProductList = mutableListOf<Product>()
                        productList.forEach {
                            if (it.productName.uppercase().contains(searchQuery.uppercase())) {
                                newProductList += it
                            }
                        }
                        adapter.setProduct(newProductList)
                    }
                    binding.textViewCount.isVisible = false
                }
            }
            binding.recycleViewProductList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        homeViewModel.selectedIndex = -1
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        //menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
        }
        return true
    }

    override fun onRecordClickListener(index: Int) {
        homeViewModel.selectedIndex = index
        findNavController().navigate(R.id.action_navigation_home_to_product_details_fragment)
    }
}