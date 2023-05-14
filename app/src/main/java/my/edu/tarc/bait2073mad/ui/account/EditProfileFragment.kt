package my.edu.tarc.bait2073mad.ui.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.bait2073mad.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    companion object {
        fun newInstance() = EditProfileFragment()
    }

    private lateinit var viewModel: EditProfileViewModel

    private var _binding: FragmentEditProfileBinding? = null
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
            ViewModelProvider(this)[EditProfileViewModel::class.java]

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        val userID = auth.currentUser?.uid
        val userEmail = auth.currentUser?.email
        var username: String
        var address: String

        docRef = db.collection("users").document(userID!!)

        docRef.get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val profileData = doc.data
                    username =
                        profileData?.get("username") as String
                    address =
                        profileData?.get("address") as String
                    binding.editTextEditProfileUsername.setText(username)
                    binding.editTextEditProfileAddress.setText(address)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Data Read Failed", Toast.LENGTH_SHORT).show()
            }

        binding.buttonSave.setOnClickListener {
            val changedUsername: String = binding.editTextEditProfileUsername.text.toString()
            val changedAddress: String = binding.editTextEditProfileAddress.text.toString()

            if (changedUsername.isBlank()) {
                showErrorMessage(binding.editProfileErrorMessage1, "Username cannot be blank")
            } else if (changedAddress.isBlank()) {
                showErrorMessage(binding.editProfileErrorMessage2, "Address cannot be blank")
            } else {
                val profileData = hashMapOf(
                    "username" to changedUsername,
                    "email" to userEmail,
                    "address" to changedAddress
                )

                docRef.set(profileData)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Update Successfully", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Update Failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun showErrorMessage(textView: TextView, message: String) {
        textView.text = message
    }

}