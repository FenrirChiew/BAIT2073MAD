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
import my.edu.tarc.bait2073mad.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.textSignUp.setOnClickListener {
            val intent = Intent(applicationContext, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonLogin.setOnClickListener {
            val email: String = binding.editTextLoginEmail.text.toString()
            val password: String = binding.editTextLoginPassword.text.toString()

            if (email.isBlank()) {
                showErrorMessage(binding.loginErrorMessage1, "Email cannot be blank")
            } else if (password.isBlank()) {
                showErrorMessage(binding.loginErrorMessage2, "Password cannot be blank")
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Login successful.",
                                Toast.LENGTH_SHORT,
                            ).show()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
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