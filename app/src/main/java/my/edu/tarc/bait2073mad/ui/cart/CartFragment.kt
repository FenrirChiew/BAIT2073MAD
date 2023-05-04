package my.edu.tarc.bait2073mad.ui.cart

import android.graphics.BitmapFactory
import android.icu.text.AlphabeticIndex.Record
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartBinding
import my.edu.tarc.bait2073mad.ui.product.Product

class CartFragment : Fragment(){

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartItemViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

//        val menuHost: MenuHost = this.requireActivity()
//        menuHost.addMenuProvider(
//            this,viewLifecycleOwner,
//            Lifecycle.State.RESUMED
//        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CartItemAdapter()

        cartItemViewModel.cartItemList.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isEmpty()) {
                    binding.textViewCartItemCount.isVisible = true
                    binding.textViewCartItemCount.text = "No Item in Cart"
                    binding.buttonCheckOut.isActivated = false
                } else {
                    binding.textViewCartItemCount.isVisible = false
                    adapter.setCartItem(it)
                    binding.buttonCheckOut.isActivated = true
                }
            }
        )

        binding.buttonCheckOut.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_cart_to_checkOutFragment)
        }

        //try input picture for test
        val icon = BitmapFactory.decodeResource(
            this.getResources(),
            android.R.drawable.btn_plus
        )
        var product: Product = Product("1000", "Product1",13.00, icon,"Good",true,"good", "12345")
        var cartItem: CartItem = CartItem(product,1)
        cartItemViewModel.addCartItem(cartItem)

        binding.recyclerViewCartItem.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//        //menuInflater.inflate(R.menu.bottom_nav_menu, menu)
//    }
//
//    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//        return true
//    }

    private fun saveCartItem(){

    }
}