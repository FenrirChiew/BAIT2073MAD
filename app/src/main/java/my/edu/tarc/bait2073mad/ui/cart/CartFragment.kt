package my.edu.tarc.bait2073mad.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartBinding

class CartFragment : Fragment(), RecordClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    //Refer to the ViewModel created by the Main Activity
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CartItemAdapter(this)


        //Add an observer
        cartViewModel.cartItemList.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isEmpty()) {
                    cartViewModel.addCartItem(CartItem("P001", "Product1", 2.5, 1))
                    cartViewModel.addCartItem(CartItem("P002", "Product2", 1.5, 1))
                    cartViewModel.addCartItem(CartItem("P003", "Product3", 1.5, 1))
                    binding.textViewCartItemCount.isVisible = true
                    binding.buttonCheckOut.isEnabled = false
                } else {
                    binding.textViewCartItemCount.isVisible = false
                    binding.buttonCheckOut.isEnabled = true
                }
                adapter.setCartItem(it)
            }
        )
        binding.recyclerViewCartItem.adapter = adapter

        //button click
        binding.buttonCheckOut.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkOutFragment)
            //TODO: Pass data to checkout
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//        //menuInflater.inflate(R.menu.menu_main, menu)
//    }

//    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//        if (menuItem.itemId == R.id.action_upload) {
//            //open shared preference
//            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
//            val userRef = sharedPref.getString(getString(R.string.phone),"")
//            if(userRef.isNullOrEmpty()){
//                Toast.makeText(context, getString(R.string.error_profile), Toast.LENGTH_SHORT).show()
//            }else{
//                if (myContactViewModel.contactList.isInitialized) {
//                    val database =
//                        Firebase.database("https://contact-ef549-default-rtdb.asia-southeast1.firebasedatabase.app").reference
//                    myContactViewModel.contactList.value!!.forEach{
//                        database.child(userRef).child(it.phone).setValue(it)
//                    }
//                    Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        // for download data from server
//        if(menuItem.itemId == R.id.action_download){
//            downloadContact(requireContext(), getString(R.string.url_server)+ getString(R.string.url_get_all))
//            return true
//        }
//        return false
//    }

    override fun onRecordClickListener(index: Int) {
        //selectedIndex from viewModel
        cartViewModel.selectedIndex = index
//        Toast.makeText(context, index, Toast.LENGTH_SHORT)
//            .show()
        findNavController().navigate(R.id.action_cartFragment_to_cartItemDetailFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}