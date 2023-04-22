package my.edu.tarc.bait2073mad.ui.product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentProductBinding
import my.edu.tarc.bait2073mad.ui.cart.CartItem
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    //view model of cart
    private val cartViewModel: CartViewModel by activityViewModels()

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        // TODO: Use the ViewModel


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.imageButtonCart).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_productFragment_to_navigation_cart)
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