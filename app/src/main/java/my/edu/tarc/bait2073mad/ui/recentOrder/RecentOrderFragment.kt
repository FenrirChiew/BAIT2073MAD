package my.edu.tarc.bait2073mad.ui.recentOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentRecentOrderBinding

class RecentOrderFragment : Fragment() {

    private var _binding: FragmentRecentOrderBinding? = null
    private val binding get() = _binding!!
    private val recentOrderViewModel: RecentOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentOrderBinding.inflate(inflater,container,false)

//        val menuHost: MenuHost = this.requireActivity()
//        menuHost.addMenuProvider(
//            this,viewLifecycleOwner,
//            Lifecycle.State.RESUMED
//        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: addon button click function
    }

}