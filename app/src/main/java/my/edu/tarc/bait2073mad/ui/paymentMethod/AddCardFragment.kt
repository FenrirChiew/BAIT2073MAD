package my.edu.tarc.bait2073mad.ui.paymentMethod

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment(), MenuProvider {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private val paymentMethodViewModel: PaymentMethodViewModel by activityViewModels()

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
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        //firestone
        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid
        docRef = db.collection("checkOut").document(userID!!)

        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.AddCardButton.setOnClickListener {


            if (binding.editTextCardNumber.length() < 16) {
                Snackbar.make(
                    requireView(),
                    "You had input invalid card number",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (!binding.editTextDate.text.toString().matches(Regex("^\\d{2}/\\d{2}\$"))) {
                // The entered date does not match the format "MM/YY"
                Snackbar.make(
                    requireView(),
                    "Invalid date format. Please enter date in the format MM/YY",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val cardNumber = binding.editTextCardNumber.text.toString().toLong()
                val cardHolderName = binding.editTextCardHolderName.toString()
                val cardExpiredDate = binding.editTextDate.text.toString()
                val cardCvc = binding.editTextCvc.text.toString().toInt()


                val voucher = arguments?.getString("voucherName", "")
                val voucherName = voucher.toString()
                val action = AddCardFragmentDirections.actionAddCardFragmentToCheckOutFragment(
                    voucherName,
                    cardNumber
                )
                findNavController().navigate(action)

            }

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
            val cardData = hashMapOf (
                "cardNumber" to binding.editTextCardNumber.text.toString().toLong(),
                "cardHolderName" to binding.editTextCardHolderName.text.toString(),
                "cardExpiredDate" to binding.editTextDate.text.toString(),
                "cardCvc" to binding.editTextCvc.text.toString().toInt()
            )

            docRef.set(cardData)
                .addOnSuccessListener {
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                }

        } else if (menuItem.itemId == R.id.action_download) {
            docRef.get()
                .addOnSuccessListener {  documentSnapshot ->
                    val cardData = documentSnapshot.data
                    if (cardData != null) {
                        val card = cardData as Map<String, Any>
                        val cardNumber = card["cardNumber"] as Long? ?: 0
                        val cardHolderName = card["cardHolderName"] as String? ?: ""
                        val cardExpiredDate = card["cardExpiredDate"] as String? ?: ""
                        val cardCvc = (card["cardCvc"] as Long? ?: 0).toInt()

                        with(binding) {
                            editTextCardNumber.setText(cardNumber.toString())
                            editTextCardHolderName.setText(cardHolderName)
                            editTextDate.setText(cardExpiredDate)
                            editTextCvc.setText(cardCvc.toString())
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error getting card data", e)
                }
        }
        return true
    }

}

