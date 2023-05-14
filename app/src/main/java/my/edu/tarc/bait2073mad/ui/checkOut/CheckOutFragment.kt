package my.edu.tarc.bait2073mad.ui.checkOut

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCheckOutBinding
import my.edu.tarc.bait2073mad.ui.paymentMethod.AddCardFragmentDirections

class CheckOutFragment : Fragment() {

    private val viewModel: CheckOutViewModel by activityViewModels()

    private var _binding: FragmentCheckOutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var paymentMethodButtonClicked = false

        binding.applyVoucherbutton.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_voucherFragment)
        }

        binding.paymentMethodButton.setOnClickListener {
            paymentMethodButtonClicked = true

            val voucher = arguments?.getString("voucherName", "")
            val voucherName = voucher.toString()
            val action = CheckOutFragmentDirections.actionCheckOutFragmentToPaymentMethodFragment(
                voucherName)
            findNavController().navigate(action)
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

    private fun calculation(subtotal: Double, shippingFee: Double, voucher: Double): Double {
        var subtotal = subtotal
        var shippingFee = shippingFee
        var voucher = voucher
        var totalPayment = 0.0

        totalPayment = (subtotal + shippingFee) - voucher

        return totalPayment
    }


}