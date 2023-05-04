package my.edu.tarc.bait2073mad.ui.product

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentProductDetailsBinding
import my.edu.tarc.bait2073mad.ui.cart.CartItem
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel


class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    //view model of cart
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.imageButtonCart).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_product_details_fragment_to_navigation_cart)
        }

        //store to cart when click add to cart button
        binding.imageButtonCart.setOnClickListener {
            binding.apply {
                val productImage = imageViewProductImage.drawToBitmap()
                val product = Product("123","123",1.0,productImage,"ss",false,"sss","asq")
                val cartItem = CartItem(product,1)
                cartViewModel.addCartItem(cartItem)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}