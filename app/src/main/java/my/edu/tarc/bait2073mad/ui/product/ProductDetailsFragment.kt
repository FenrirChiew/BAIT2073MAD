package my.edu.tarc.bait2073mad.ui.product

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentProductDetailsBinding
import my.edu.tarc.bait2073mad.ui.cart.CartItem
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel
import my.edu.tarc.bait2073mad.ui.home.HomeViewModel
import java.io.File
import java.io.FileNotFoundException

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
        val productImage = readProductImage(productID.lowercase().plus(".jpg"))
        if (productImage != null) {
            _binding?.imageViewProductImage?.setImageBitmap(productImage)
        } else {
            _binding?.imageViewProductImage?.setImageResource(R.drawable.ic_product_black_24dp)
        }
        _binding?.textViewProductName?.text = productSelected.productName
        _binding?.textViewProductPrice?.text = getString(R.string.currency_myr).plus(" ")
            .plus("%.2f".format(productSelected.productPrice))
        _binding?.textViewProductStatus?.text = productSelected.productStatus
        _binding?.textViewProductSeller?.text = productSelected.seller
        _binding?.textViewProductDescription?.text = productSelected.productDescriptions

        //add to cart
        binding.buttonAddToCart.setOnClickListener {
            val productId = productSelected.productID
            val productName = productSelected.productName
            val productPrice = productSelected.productPrice
            val cartItem = CartItem(productId,productName,productPrice,1)
            val cartItemList = cartViewModel.cartItemList.value

            if(cartItemList!!.contains(cartItem)){
                Toast.makeText(context, "Item already exist in cart!", Toast.LENGTH_LONG).show()
            }
            else{
                cartViewModel.addCartItem(cartItem)
            }


            findNavController().navigate(R.id.action_product_details_fragment_to_cartFragment)
        }
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

    private fun readProductImage(filename: String): Bitmap? {
        val file = File(this.context?.filesDir, filename)

        if (file.isFile) {
            try {
                return BitmapFactory.decodeFile(file.absolutePath)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return null
    }
}