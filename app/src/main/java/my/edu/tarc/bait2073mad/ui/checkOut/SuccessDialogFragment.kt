package my.edu.tarc.bait2073mad.ui.checkOut

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R

class SuccessDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okButton = view.findViewById<Button>(R.id.okbutton)
        okButton.setOnClickListener {
            findNavController().navigate(R.id.action_successDialogFragment_to_orderHistoryFragment)
        }
    }
}
