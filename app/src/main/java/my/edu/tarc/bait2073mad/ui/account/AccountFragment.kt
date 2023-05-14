package my.edu.tarc.bait2073mad.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentAccountBinding
import my.edu.tarc.bait2073mad.ui.Login

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var docRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel =
            ViewModelProvider(this)[AccountViewModel::class.java]

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid
        var username = ""
        val userEmail = auth.currentUser?.email
        docRef = db.collection("users").document(userID!!)

        docRef.get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val profileData = doc.data
                    username =
                        profileData?.get("username") as String
                    binding.usernameTextView.text = username
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Data Read Failed", Toast.LENGTH_SHORT).show()
            }

        binding.editProfileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_account_to_editProfileFragment)
        }

//        binding.buttonTestUpload.setOnClickListener {
//            val profileData = hashMapOf(
//                "username" to username,
//                "email" to userEmail,
//                "address" to ""
//            )
//
//            docRef.set(profileData)
//                .addOnSuccessListener {
//                    Toast.makeText(requireContext(), "Update Successfully", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(requireContext(), "Update Failed", Toast.LENGTH_SHORT).show()
//                }
//        }

        binding.buttonLogout.setOnClickListener {
            auth.signOut()

            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        accountViewModel.text.observe(viewLifecycleOwner) {

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}