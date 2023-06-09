package my.edu.tarc.bait2073mad.ui.cart

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartBinding

class CartFragment : Fragment(), MenuProvider, RecordClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    //Refer to the ViewModel created by the Main Activity
    private val cartViewModel: CartViewModel by activityViewModels()

    //Firebase
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var docRef: DocumentReference

    //total calculation
    var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        //firestore
        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid
        docRef = db.collection("Cart").document(userID!!)

        //host menu
        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(
            this, viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CartItemAdapter(requireContext(), this)

        //Add an observer
        cartViewModel.cartItemList.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isEmpty()) {
                    binding.textViewCartItemCount.isVisible = true
                    binding.buttonCheckOut.isEnabled = false
                } else {
                    binding.textViewCartItemCount.isVisible = false
                    binding.buttonCheckOut.isEnabled = true
                }
                adapter.setCartItem(it)
                total = 0.0
                for (element in it) {
                    total += (element.productPrice * element.quantity)
                }
                binding.textViewTotal.text = String.format("RM %.2f", total)
            }
        )
        binding.recyclerViewCartItem.adapter = adapter

        //button click
        binding.buttonCheckOut.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkOutFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.top_upload_download_menu, menu)
        menu.findItem(R.id.action_download).isVisible = true
        menu.findItem(R.id.action_upload).isVisible = true
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_upload) {
            cartViewModel.cartItemList.observe(
                viewLifecycleOwner,
                Observer {
                    val items = mutableListOf<Map<String, Any>>()
                    for (element in it) {
                        items.add(
                            mapOf(
                                "ProductID" to element.productID,
                                "ProductName" to element.productName,
                                "ProductPrice" to element.productPrice,
                                "Quantity" to element.quantity
                            )
                        )
                    }
                    docRef.set(mapOf("items" to items)).addOnSuccessListener {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        } else if (menuItem.itemId == R.id.action_download) {
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val cartItemData = documentSnapshot.get("items") as List<Map<String, Any>>?
                    if (cartItemData != null) {
                        for (itemData in cartItemData) {
                            val cartItem = CartItem(
                                productID = itemData["ProductID"] as String? ?: "",
                                productName = itemData["ProductName"] as String? ?: "",
                                productPrice = (itemData["ProductPrice"] as Double?) ?: 0.0,
                                quantity = (itemData["Quantity"] as Long? ?: 0).toInt()
                            )
                            cartViewModel.addCartItem(cartItem)
                        }
                    }
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
        } else if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
        }
        return true
    }

    override fun onRecordClickListener(index: Int) {
        //selectedIndex from viewModel
        cartViewModel.selectedIndex = index
        findNavController().navigate(R.id.action_cartFragment_to_cartItemDetailFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}