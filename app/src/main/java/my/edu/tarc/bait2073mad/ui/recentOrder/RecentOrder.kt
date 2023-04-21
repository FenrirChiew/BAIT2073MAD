package my.edu.tarc.bait2073mad.ui.recentOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.tarc.bait2073mad.R

class RecentOrder : Fragment() {

    companion object {
        fun newInstance() = RecentOrder()
    }

    private lateinit var viewModel: RecentOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecentOrderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}