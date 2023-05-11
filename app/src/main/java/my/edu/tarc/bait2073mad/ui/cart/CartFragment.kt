package my.edu.tarc.bait2073mad.ui.cart

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
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartBinding
import my.edu.tarc.bait2073mad.ui.product.Product

class CartFragment : Fragment(){

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()

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

        cartViewModel.cartItemList.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isEmpty()) {
                    binding.textViewCartItemCount.isVisible = true
                    binding.textViewCartItemCount.text = "No Item in Cart"
                    binding.buttonCheckOut.isActivated = false
                } else {
                    binding.textViewCartItemCount.isVisible = false
                    binding.buttonCheckOut.isActivated = true
                }
                adapter.setCartItem(it)
            }
        )
        binding.recyclerViewCartItem.adapter=adapter

        binding.buttonCheckOut.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_cart_to_tryFragment)
        }
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
//
//        if(menuItem.itemId == R.id.menu_cart){
//
//        }
//        return true
//    }
}