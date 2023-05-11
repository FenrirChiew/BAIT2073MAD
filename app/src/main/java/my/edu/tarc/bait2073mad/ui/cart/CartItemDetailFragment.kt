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
        //indicate which view user is looking at (view mode - edit? new?)
        //if != -1, isEditing = True
        //isEditing = myContactViewModel.selectedIndex != -1

        with(binding) {
            //value!! is for the current contactList information
            val cartItem: CartItem =
                cartViewModel.cartItemList.value!!.get(cartViewModel.selectedIndex)
            //to pass the current value to the edit text
            textViewCartItemDetailName.setText(cartItem.productName)
            textViewCartItemDetailPrice.setText(cartItem.productPrice.toString())
            textViewCartItemDetailQuantity.setText(cartItem.quantity.toString())
        }
    }

}