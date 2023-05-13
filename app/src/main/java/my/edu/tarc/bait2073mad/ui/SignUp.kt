package my.edu.tarc.bait2073mad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import my.edu.tarc.bait2073mad.MainActivity
import my.edu.tarc.bait2073mad.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.loginNow.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonSignUp.setOnClickListener {
            val email: String = binding.editTextSignUpEmail.text.toString()
            val password: String = binding.editTextSignUpPassword.text.toString()
            val confirmPassword: String = binding.editTextSignUpConfirmPassword.text.toString()

            if (email.isBlank()) {
                showErrorMessage(binding.signUpErrorMessage1, "Email cannot be blank")
            } else if (password.isBlank()) {
                showErrorMessage(binding.signUpErrorMessage2, "Password cannot be blank")
            } else if (confirmPassword.isBlank()) {
                showErrorMessage(binding.signUpErrorMessage3, "Confirm Password cannot be blank")
            } else {
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Account created.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Confirm password is not match.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showErrorMessage(textView: TextView, message: String) {
        textView.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}