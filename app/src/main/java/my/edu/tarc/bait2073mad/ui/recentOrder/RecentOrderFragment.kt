package my.edu.tarc.bait2073mad.ui.recentOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentRecentOrderBinding

class RecentOrderFragment : Fragment() {

    private var _binding: FragmentRecentOrderBinding? = null
    private val binding get() = _binding!!
    private val recentOrderViewModel: RecentOrderViewModel by activityViewModels()
    var grandTotal: Double = 0.00

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecentOrderAdapter()

        recentOrderViewModel.addOrder(RecentOrder("O001", "12/11/02", 15.0))
        recentOrderViewModel.addOrder(RecentOrder("O002", "12/11/02", 15.0))


        //Add an observer
        recentOrderViewModel.recentOrderList.observe(
            viewLifecycleOwner,
            Observer {
                binding.textViewRecentOrderCount.isVisible = it.isEmpty()
                for (element in it) {
                    grandTotal += element.total
                }
                adapter.setRecentOrder(it)
                binding.textViewTotalSpending.text = String.format("RM %.2f",grandTotal)
                binding.recyclerViewRecentOrder.adapter = adapter
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}