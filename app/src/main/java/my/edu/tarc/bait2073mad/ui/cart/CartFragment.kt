package my.edu.tarc.bait2073mad.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartBinding

class CartFragment : Fragment(){

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartItemViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater,container,false)

//        val menuHost: MenuHost = this.requireActivity()
//        menuHost.addMenuProvider(
//            this,viewLifecycleOwner,
//            Lifecycle.State.RESUMED
//        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.buttonCheckOut).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_cart_to_checkOutFragment)
        }

        //cannot put here, will crash
//        view.findViewById<View>(R.id.imageButtonPlus).setOnClickListener {
//            var cartItemQuantity = R.id.textViewProductQuantity.toString().toInt()
//            cartItemQuantity++
//            //TODO: Pass back to class and store
//        }

    }

//    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//        //menuInflater.inflate(R.menu.bottom_nav_menu, menu)
//    }
//
//    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//        return true
//    }
}