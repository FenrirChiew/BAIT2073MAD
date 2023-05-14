package my.edu.tarc.bait2073mad.ui.paymentMethod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private val paymentMethodViewModel: PaymentMethodViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
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


}

