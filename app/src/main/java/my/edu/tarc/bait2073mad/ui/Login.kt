package my.edu.tarc.bait2073mad.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.bait2073mad.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}