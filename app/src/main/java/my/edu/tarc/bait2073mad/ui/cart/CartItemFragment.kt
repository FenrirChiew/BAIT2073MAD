package my.edu.tarc.bait2073mad.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import my.edu.tarc.bait2073mad.databinding.FragmentCartItemBinding

class CartItemFragment: Fragment() {
    private var _binding: FragmentCartItemBinding? = null
    private val binding get() = _binding!!
    private var cartItemQuantity = 0
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartItem: CartItem = cartViewModel.cartItemList.value!!.get(cartViewModel.selectedIndex)

        binding.imageButtonPlus.setOnClickListener {
//            cartItemQuantity = binding.textViewProductQuantity.toString().toInt()
//            if(cartItemQuantity < 9){
//                cartItemQuantity++
//                binding.textViewProductQuantity.text = cartItemQuantity.toString()
//                //TODO Pass back to class and store
//                Toast.makeText(it.context,"Plus is success",Toast.LENGTH_SHORT).show()
//            }
//            else{
//                //TODO Pass back to class and store
//                Toast.makeText(it.context,"Plus is failed",Toast.LENGTH_SHORT).show()
//            }

            Toast.makeText(it.context,"Clicked",Toast.LENGTH_SHORT).show()

        }

        binding.imageButtonMinus.setOnClickListener {
//            cartItemQuantity = binding.textViewProductQuantity.toString().toInt()
//            if(cartItemQuantity > 0){
//                cartItemQuantity--
//                binding.textViewProductQuantity.text = cartItemQuantity.toString()
//                //TODO Pass back to class and store
//                Toast.makeText(it.context,"Minus is success",Toast.LENGTH_SHORT).show()
//            }
//            else{
//                //TODO Pass back to class and store
//                Toast.makeText(it.context,"Minus is failed",Toast.LENGTH_SHORT).show()
//            }

            Toast.makeText(it.context,"Clicked",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}