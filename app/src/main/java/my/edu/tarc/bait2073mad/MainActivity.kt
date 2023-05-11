package my.edu.tarc.bait2073mad

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import my.edu.tarc.bait2073mad.databinding.ActivityMainBinding
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel
import my.edu.tarc.bait2073mad.ui.product.Product

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    //Cart
    private lateinit var cartItemViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //Initialized ViewModel- Cart
        cartItemViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_order, R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{
                _,destination,_->
            if(destination.id==R.id.navigation_home)
            {
                //edit the title of fragment
                title = "Home"
            }
            else if(destination.id == R.id.navigation_order){
                title = "Order"
            }
            else{
                title = getString(R.string.app_name)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}