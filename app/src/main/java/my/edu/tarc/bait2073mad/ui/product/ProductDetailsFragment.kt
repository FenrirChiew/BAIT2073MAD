package my.edu.tarc.bait2073mad.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentProductDetailsBinding
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
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
                .navigate(R.id.action_productFragment_to_navigation_cart)
        }

//        view.findViewById<View>(R.id.buttonAddToCart).setOnClickListener {
//            insert data to cart database
//            binding.apply{
//                val productName = productNameid.text.toString
//                val productImage = pass image here
//                val productPrice = productPriceid.text.toString
//                val addToCartItem = CartItem(productName,productImage,productPrice)
//                cartViewModel.addCartItem(addToCartItem)
//            }
//        }
        super.onViewCreated(view, savedInstanceState)
    }
}