package my.edu.tarc.bait2073mad.ui.recentOrder

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentRecentOrderBinding
import my.edu.tarc.bait2073mad.ui.cart.CartItem

class RecentOrderFragment : Fragment(), MenuProvider {

    private var _binding: FragmentRecentOrderBinding? = null
    private val binding get() = _binding!!
    private val recentOrderViewModel: RecentOrderViewModel by activityViewModels()
    var grandTotal: Double = 0.00

    //Firebase
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var docRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentOrderBinding.inflate(inflater, container, false)
        //firestore
        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid
        docRef = db.collection("RecentOrder").document(userID!!)

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
        val adapter = RecentOrderAdapter()

//        recentOrderViewModel.addOrder(RecentOrder("O001", "12/11/02", 15.0))
//        recentOrderViewModel.addOrder(RecentOrder("O002", "12/11/02", 15.0))

        //Add an observer
        recentOrderViewModel.recentOrderList.observe(
            viewLifecycleOwner,
            Observer {
                binding.textViewRecentOrderCount.isVisible = it.isEmpty()
                for (element in it) {
                    grandTotal += element.total
                }
                adapter.setRecentOrder(it)
                binding.textViewTotalSpending.text = String.format("RM %.2f",grandTotal)
                binding.recyclerViewRecentOrder.adapter = adapter
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.top_upload_download_menu, menu)
        menu.findItem(R.id.action_download).isVisible = true
        menu.findItem(R.id.action_upload).isVisible = true

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_upload) {
            recentOrderViewModel.recentOrderList.observe(
                viewLifecycleOwner,
                Observer {
                    val recentOrderItems = mutableListOf<Map<String, Any>>()
                    for (element in it) {
                        recentOrderItems.add(
                            mapOf(
                                "orderID" to element.orderID,
                                "orderDate" to element.orderDate,
                                "total" to element.total
                            )
                        )
                    }
                    docRef.set(mapOf("RecentOrder" to recentOrderItems)).addOnSuccessListener {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        } else if (menuItem.itemId == R.id.action_download) {
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val recentOrderData = documentSnapshot.get("RecentOrder") as List<Map<String, Any>>?
                    if (recentOrderData != null) {
                        for (itemData in recentOrderData) {
                            val recentOrder = RecentOrder(
                                orderID = itemData["orderID"] as String? ?: "",
                                orderDate = itemData["orderDate"] as String? ?: "",
                                total = (itemData["total"] as Double?) ?: 0.0
                            )
                            recentOrderViewModel.addOrder(recentOrder)
                        }
                    }
                }
                .addOnFailureListener { e -> Log.e(ContentValues.TAG, "Error getting recent order items", e) }
        }
        return true
    }

}