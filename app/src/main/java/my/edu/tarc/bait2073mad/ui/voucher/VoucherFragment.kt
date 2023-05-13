package my.edu.tarc.bait2073mad.ui.voucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentVoucherBinding

class VoucherFragment : Fragment(), RecordClickListener {

    private var _binding: FragmentVoucherBinding? = null
    private val binding get() = _binding!!
    private val voucherViewModel: VoucherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoucherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = VoucherItemAdapter(this)

        voucherViewModel.addVoucher(
            VoucherItem("Free Shipping",
            "Minimum Spend RM15.00", "12/06/2023", R.drawable.voucher)
        )
        voucherViewModel.addVoucher(VoucherItem("Cashback 10%",
            "Minimum Spend RM45.00", "19/06/2023", R.drawable.voucher))
        voucherViewModel.addVoucher(VoucherItem("RM10.00 Off",
            "Minimum Spend RM75.00", "12/08/2023", R.drawable.voucher))


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

    override fun onRecordClickListener(index: Int) {
        //selectedIndex from viewModel
        voucherViewModel.selectedIndex = index
//        Toast.makeText(context, index, Toast.LENGTH_SHORT)
//            .show()
        findNavController().navigate(R.id.action_voucherFragment_to_checkOutFragment)

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