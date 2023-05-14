package my.edu.tarc.bait2073mad.ui.voucher

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.squareup.picasso.Picasso
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentVoucherBinding
import my.edu.tarc.bait2073mad.ui.paymentMethod.Card

class VoucherFragment : Fragment(), RecordClickListener, MenuProvider {

    private var _binding: FragmentVoucherBinding? = null
    private val binding get() = _binding!!

    private val voucherViewModel: VoucherViewModel by activityViewModels()

    //Firebase
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var docRef: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoucherBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid
        docRef = db.collection("Voucher").document(userID!!)
        Log.d("userIdTag", userID)

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
        val adapter = VoucherItemAdapter(this)

        voucherViewModel.addVoucher(
            VoucherItem(
                "Free Shipping",
                "Minimum Spend RM15.00", "12/06/2023"
            )
        )
        voucherViewModel.addVoucher(
            VoucherItem(
                "Cashback RM3.00",
                "Minimum Spend RM45.00", "19/06/2023"
            )
        )
        voucherViewModel.addVoucher(
            VoucherItem(
                "RM10.00 Off",
                "Minimum Spend RM75.00", "12/08/2023"
            )
        )

        voucherViewModel.voucherItemList.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isEmpty()) {
                    binding.textViewNoVoucherMsg.isVisible = true
                } else {
                    binding.textViewNoVoucherMsg.isVisible = false
                    adapter.setVoucherItem(it)
                }
            }
        )
        binding.voucherRecyclerView.adapter = adapter


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
            voucherViewModel.voucherItemList.observe(
                viewLifecycleOwner, Observer {
                    val items = mutableListOf<Map<String, Any>>()
                    for (element in it) {
                        items.add(
                            mapOf(
                                "VoucherName" to element.voucherName,
                                "VoucherTerms" to element.voucherTerms,
                                "VoucherDate" to element.voucherDate
                            )
                        )
                    }
                    docRef.set(mapOf("items" to items)).addOnSuccessListener {
                        Toast.makeText(context, "Success Upload", Toast.LENGTH_SHORT)
                    }.addOnFailureListener {
                        Toast.makeText(context, "Fail to Upload", Toast.LENGTH_SHORT)
                    }
                }
            )
        } else if (menuItem.itemId == R.id.action_download) {
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val voucherItem = documentSnapshot.get("items") as List<Map<String, Any>>?
                if (voucherItem != null) {
                    for (storedData in voucherItem) {
                        val voucher = VoucherItem(
                            voucherName = storedData["VoucherName"] as String? ?: "",
                            voucherTerms = storedData["VoucherTerms"] as String? ?: "",
                            voucherDate = storedData["VoucherDate"] as String? ?: ""
                        )
                        voucherViewModel.addVoucher(voucher)
                    }
                }
            }.addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Error in getting the voucher items")
            }

        }
        return true
    }


    override fun onRecordClickListener(index: Int) {
        //selectedIndex from viewModel
        voucherViewModel.selectedIndex = index
//        Toast.makeText(context, index, Toast.LENGTH_SHORT)
//            .show()

        val voucherItem: VoucherItem =
            voucherViewModel.voucherItemList.value!!.get(voucherViewModel.selectedIndex)
        binding.apply {
            val voucherName = voucherItem.voucherName

            val action =
                VoucherFragmentDirections.actionVoucherFragmentToCheckOutFragment(voucherName, 0)

// Navigate to the CheckOutFragment with both arguments
            findNavController().navigate(action)
        }

        //findNavController().navigate(R.id.action_voucherFragment_to_checkOutFragment)


//        // Get the VoucherItem object corresponding to the selected voucher
//        val voucherItem = voucherViewModel.voucherItemList.value?.get(index)
//
//        // Create a Bundle object and put the VoucherItem object in it as a serializable extra
//        val bundle = Bundle().apply {
//            putSerializable("voucherItem", voucherItem)
//        }
//
//        // Call findNavController() to get the NavController and navigate to the CheckOutFragment,
//        // passing the Bundle object as an argument
//        findNavController().navigate(
//            R.id.action_voucherFragment_to_checkOutFragment,
//            bundle
//        )


    }


    override fun onDestroy() {
        super.onDestroy()
    }

}