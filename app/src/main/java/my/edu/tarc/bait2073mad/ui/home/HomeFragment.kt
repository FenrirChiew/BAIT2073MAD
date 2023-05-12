package my.edu.tarc.bait2073mad.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

/*        val products = arrayOf("Product A", "Product B", "Product C")

        val productAdaptor = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, products) };

        binding.searchView.setOnQueryTextListener(object : SerachView.OnQueryTextListener)*/

        _binding!!.chip.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addProductFragment)
        }

        // val chipTags
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}