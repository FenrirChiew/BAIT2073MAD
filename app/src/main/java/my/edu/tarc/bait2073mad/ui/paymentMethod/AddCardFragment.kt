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
        docRef = db.collection("CheckOut").document(userID!!)
        Log.d("userIdTag", userID)

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
        menu.findItem(R.id.action_cart_download).isVisible = true
        menu.findItem(R.id.action_cart_upload).isVisible = true
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_cart_upload) {
            paymentMethodViewModel.cardList.observe(
                viewLifecycleOwner, Observer {
                    val items = mutableListOf<Map<String, Any>>()
                    for (element in it) {
                        items.add(
                            mapOf(
                                "CardNumber" to element.cardNumber,
                                "CardHolderName" to element.cardHolderName,
                                "CardExpiredDay" to element.cardExpiredDay,
                                "CardCvc" to element.cardCvc
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
        } else if (menuItem.itemId == R.id.action_cart_download) {
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val cardItemData = documentSnapshot.get("items") as List<Map<String, Any>>?
                if (cardItemData != null) {
                    for (storedData in cardItemData) {
                        val card = Card(
                            cardNumber = storedData["CardNumber"] as Long? ?: 0,
                            cardHolderName = storedData["CardHolderName"] as String? ?: "",
                            cardExpiredDay = storedData["CardExpiredDay"] as String? ?: "",
                            cardCvc = storedData["CardCvcNumber"] as Int? ?: 0
                        )
                        paymentMethodViewModel.addCard(card)
                    }
                }
            }.addOnFailureListener { e ->
                Log.e(TAG, "Error in getting the card items")
            }

        }
        return true
    }

}

