package my.edu.tarc.bait2073mad

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import my.edu.tarc.bait2073mad.databinding.ActivityMainBinding
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseUser

    //Cart
    private lateinit var cartItemViewModel: CartViewModel

    companion object {
        lateinit var auth: FirebaseAuth
    }

    private var isBottomNavigationBarVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        val navView: BottomNavigationView = binding.navView

        //Initialized ViewModel- Cart
        cartItemViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_recent_order, R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home) {
                title = "Home"
            } else if (destination.id == R.id.navigation_recent_order) {
                title = "Recent Order"
            } else if (destination.id == R.id.navigation_account) {
                title = "Account"
            } else {
                title = getString(R.string.app_name)
            }

            // hide nav bar for some fragment
            isBottomNavigationBarVisible = destination.id != R.id.editProfileFragment
            navView.visibility = if (isBottomNavigationBarVisible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        //back press
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Exit", { _, _ -> finish() })
                    .setNegativeButton("Cancel", { _, _ -> })
                builder.create().show()
            }
        }
        onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}