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
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCheckOutBinding

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

        binding.applyVoucherbutton.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_voucherFragment)
        }

        binding.paymentMethodButton.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_paymentMethodFragment)
        }

        val voucherName = arguments?.getString("voucherName", "")
        binding.textViewVoucherApply.text = voucherName

        //To retrieve the card number that input by user
        val cardNumber = arguments?.getLong("cardNumber", 0)
        val maskedCardNumber = "**** **** **** " + cardNumber.toString().takeLast(4)
        binding.textViewChosenPaymentMethod.text = maskedCardNumber

        binding.buttonPlaceOrder.setOnClickListener {
            val showDialog = SuccessDialogFragment()
            showDialog.show((activity as AppCompatActivity).supportFragmentManager, "showDialog")
        }
    }


}