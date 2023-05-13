package my.edu.tarc.bait2073mad.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentProductDetailsBinding
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel
import my.edu.tarc.bait2073mad.ui.home.HomeViewModel

class ProductDetailsFragment : Fragment(), MenuProvider {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    //view model of cart
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.imageButtonCart).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_product_details_fragment_to_navigation_home)
        }

        val productID = homeViewModel.productList.value!![homeViewModel.selectedIndex].productID
        val productIndex = productID.substring(productID.length - 4).toInt()
        val productSelected = homeViewModel.productList.value!![productIndex]

        _binding?.textViewProductName?.text = productSelected.productName
        _binding?.textViewProductPrice?.text = getString(R.string.currency_myr).plus(" ")
            .plus("%.2f".format(productSelected.productPrice))
        _binding?.textViewProductStatus?.text = productSelected.productStatus
        _binding?.textViewProductSeller?.text = productSelected.seller
        if (productSelected.favorite) {
            _binding?.imageViewFavorite?.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            _binding?.imageViewFavorite?.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        }
        _binding?.textViewProductDescription?.text = productSelected.productDescriptions
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
        }
        return true
    }
}