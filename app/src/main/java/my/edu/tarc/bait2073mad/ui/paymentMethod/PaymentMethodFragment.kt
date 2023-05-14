package my.edu.tarc.bait2073mad.ui.paymentMethod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentPaymentMethodBinding

class PaymentMethodFragment : Fragment() {

    private var _binding: FragmentPaymentMethodBinding? = null
    private val binding get() = _binding!!
    private val paymentMethodViewModel: PaymentMethodViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler(Looper.getMainLooper())

        binding.cardButton.setOnClickListener {
            val voucher = arguments?.getString("voucherName", "")
            val voucherName = voucher.toString()
            val action = PaymentMethodFragmentDirections.actionPaymentMethodFragmentToAddCardFragment(voucherName)
            findNavController().navigate(action)
        }

        binding.onlinePaymentButton.setOnClickListener {
            binding.paymentProgressBar.isVisible = true
            handler.postDelayed({
                findNavController().navigate(R.id.action_paymentMethodFragment_to_thirdPartyFragment)
                binding.paymentProgressBar.isVisible = false
            }, 4000)

        }

        binding.cashButton.setOnClickListener {
            binding.paymentProgressBar.isVisible = true
            handler.postDelayed({
                findNavController().navigate(R.id.action_paymentMethodFragment_to_checkOutFragment)
                binding.paymentProgressBar.isVisible = false
            }, 4000)
        }



    }
}