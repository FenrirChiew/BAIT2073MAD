package my.edu.tarc.bait2073mad.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.bait2073mad.databinding.FragmentOrderBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    /*
    Firebase.auth.signOut()

    val intent = Intent(applicationContext, Login::class.java)
    startActivity(intent)
    finish()
    */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}