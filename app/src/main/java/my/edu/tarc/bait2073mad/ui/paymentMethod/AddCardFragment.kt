package my.edu.tarc.bait2073mad.ui.paymentMethod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

         val cardNumber = binding.editTextCardNumber.text.toString().toLong()
         val cardHolderName = binding.editTextCardHolderName.toString()
         val cardExpiredDate = binding.editTextDate.text.toString()
         val cardCvc = binding.editTextCvc.text.toString().toInt()

         //findNavController().navigate(R.id.action_addCardFragment_to_checkOutFragment)
         val action = AddCardFragmentDirections.actionAddCardFragmentToCheckOutFragment(cardNumber)
         findNavController().navigate(action)


     }

    }


}

