package my.edu.tarc.bait2073mad.ui.cart

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartBinding
import my.edu.tarc.bait2073mad.databinding.FragmentTryBinding

class TryFragment : Fragment() {
    private var _binding: FragmentTryBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddCart.setOnClickListener {
//            //try input picture for test
//            val icon = BitmapFactory.decodeResource(
//                this.getResources(),
//                android.R.drawable.btn_plus
//            )
//            var image1 = R.drawable.credit_card
            cartViewModel.addCartItem(CartItem("P001", "Product1", 1.50, 2))
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
//        binding.recyclerViewCartItem.adapter = CartItemAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}