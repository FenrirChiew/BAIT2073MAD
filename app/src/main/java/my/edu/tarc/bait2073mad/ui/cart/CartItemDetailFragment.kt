package my.edu.tarc.bait2073mad.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartItemDetailBinding

class CartItemDetailFragment : Fragment() {

    private var _binding: FragmentCartItemDetailBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private var currentIndex: Int = cartViewModel.selectedIndex

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartItemDetailBinding.inflate(inflater, container, false)

        //Let ProfileFragment to manage the Menu
//        val menuHost: MenuHost = this.requireActivity()
//        menuHost.addMenuProvider(
//            this, viewLifecycleOwner,
//            Lifecycle.State.RESUMED
//        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //value!! is for the current contactList information
            val cartItem: CartItem =
                cartViewModel.cartItemList.value!!.get(currentIndex)
            //to pass the current value to the edit text
            imageViewCartItemDetailProductImage.setImageResource(R.drawable.ic_product_black_24dp)
            textViewCartItemDetailName.setText(cartItem.productName)
            textViewCartItemDetailPrice.setText(cartItem.productPrice.toString())
            textViewCartItemDetailQuantity.setText(cartItem.quantity.toString())
        }

        binding.imageButtonCartItemDetailDelete.setOnClickListener {
        }

        binding.imageButtonCartItemDetailPlus.setOnClickListener {

        }

        binding.imageButtonCartItemDetailMinus.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cartViewModel.selectedIndex = -1
    }
}