package my.edu.tarc.bait2073mad.ui.checkOut

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCheckOutBinding
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel

class CheckOutFragment : Fragment() {

    //viewModel
    private val cartViewModel: CartViewModel by activityViewModels()
    private val checkOutViewHolder: CheckOutViewModel by activityViewModels()

    //binding
    private var _binding: FragmentCheckOutBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CheckOutItemAdapter()

        cartViewModel.cartItemList.observe(
            viewLifecycleOwner,
            Observer {
                adapter.setCheckOutItem(it)
            }
        )
        binding.checkOutRecyclerView.adapter = adapter

        var paymentMethodButtonClicked = false

        binding.applyVoucherbutton.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_voucherFragment)
        }

        binding.paymentMethodButton.setOnClickListener {
            paymentMethodButtonClicked = true
            findNavController().navigate(R.id.action_checkOutFragment_to_paymentMethodFragment)
        }

        val voucherName = arguments?.getString("voucherName", "")
        binding.textViewVoucherApply.text = voucherName

        //shipping fee fix is RM5.00
        var voucherAmount = 0.0
        var shippingFee = 5.00
        voucherAmount = if(voucherName == "Free Shipping"){
            shippingFee
        } else if(voucherName == "Cashback RM3.00"){
            3.00
        } else if(voucherName == "RM10.00 Off"){
            10.00
        } else{
            0.0
        }

        //To retrieve the card number that input by user
        val cardNumber = arguments?.getLong("cardNumber", 0)
        val maskedCardNumber = "**** **** **** " + cardNumber.toString().takeLast(4)
        binding.textViewChosenPaymentMethod.text = maskedCardNumber

        binding.buttonPlaceOrder.setOnClickListener {
            if(!paymentMethodButtonClicked){
                Snackbar.make(requireView(), "Please select a payment method", Snackbar.LENGTH_SHORT).show()
            }else {
                val showDialog = SuccessDialogFragment()
                showDialog.show(
                    (activity as AppCompatActivity).supportFragmentManager,
                    "showDialog"
                )
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun calculation(subtotal: Double, shippingFee: Double, voucher: Double): Double {
        var subtotal = subtotal
        var shippingFee = shippingFee
        var voucher = voucher
        var totalPayment = 0.0

        totalPayment = (subtotal + shippingFee) - voucher

        return totalPayment
    }


}