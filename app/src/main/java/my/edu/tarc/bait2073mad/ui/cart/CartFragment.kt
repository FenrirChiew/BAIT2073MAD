package my.edu.tarc.bait2073mad.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R

class CartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var b = view.findViewById<View>(R.id.buttonCheckOut)
        b.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_cart_to_checkOutFragment)
        }
    }

}