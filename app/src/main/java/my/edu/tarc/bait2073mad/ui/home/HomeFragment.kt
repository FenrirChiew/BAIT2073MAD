package my.edu.tarc.bait2073mad.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentHomeBinding
import my.edu.tarc.bait2073mad.ui.product.Product
import my.edu.tarc.bait2073mad.ui.product.ProductAdapter
import my.edu.tarc.bait2073mad.ui.product.RecordClickListener

class HomeFragment : Fragment(), MenuProvider, RecordClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    val db = FirebaseFirestore.getInstance()
    private lateinit var docRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(
            this, viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        docRef = db.collection("products").document("public")
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

        val adapter = ProductAdapter(requireContext(), this)

        // Add observers for productList and searchQuery
        homeViewModel.productList.observe(viewLifecycleOwner) { productList ->
            homeViewModel.searchQuery.observe(viewLifecycleOwner) { searchQuery ->
                if (productList.isEmpty()) {
                    binding.textViewCount.text = getString(R.string.no_product)
                    binding.textViewCount.isVisible = true
                } else {
                    binding.textViewCount.text = ""
                    binding.textViewCount.isVisible = false
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
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_refresh -> {
                refreshProductList()
            }

            R.id.action_add_product -> {
                refreshProductList()
                findNavController().navigate(R.id.action_navigation_home_to_addProductFragment)
            }

            android.R.id.home -> {
                findNavController().navigateUp()
            }

            R.id.action_cart -> {
                findNavController().navigate(R.id.action_navigation_home_to_cartFragment)
            }
        }
        return true
    }

    override fun onRecordClickListener(index: Int) {
        homeViewModel.selectedIndex = index
        findNavController().navigate(R.id.action_navigation_home_to_product_details_fragment)
    }

    private fun refreshProductList() {
        binding.progressBar.isVisible = true
        homeViewModel.searchQuery = MutableLiveData<String>("")

        if (homeViewModel.productList.value?.isNotEmpty()!!) {
            homeViewModel.deleteAllProduct()
        }

        docRef.get()
            .addOnSuccessListener { document ->
                val products = document.get("products") as List<Map<String, Any>>?
                if (products != null) {
                    for (i in products.indices step 1) {
                        val product = Product(
                            productID = products[i]["ProductID"] as String? ?: "",
                            productName = products[i]["ProductName"] as String? ?: "",
                            productPrice = (products[i]["ProductPrice"] as Double?) ?: 0.0,
                            productStatus = products[i]["ProductStatus"] as String? ?: "",
                            seller = products[i]["ProductSeller"] as String? ?: "",
                            productDescriptions = products[i]["ProductDescription"] as String?
                                ?: "",
                        )
                        homeViewModel.addProduct(product)
                    }
                }
            }

        Toast.makeText(context, "Synchronizing...", Toast.LENGTH_SHORT).show()
        binding.progressBar.isVisible = false

        val adapter = ProductAdapter(requireContext(), this)

        // Add observers for productList and searchQuery
        homeViewModel.productList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.textViewCount.text = getString(R.string.no_product)
                binding.textViewCount.isVisible = true
            } else {
                binding.textViewCount.text = ""
                binding.textViewCount.isVisible = false
            }
            adapter.setProduct(it)
        }
        binding.recycleViewProductList.adapter = adapter
    }
}
