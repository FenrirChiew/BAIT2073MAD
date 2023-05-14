package my.edu.tarc.bait2073mad.ui.checkOut

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.bait2073mad.ui.cart.CartItem

class CheckOutViewModel (application: Application): AndroidViewModel(application) {
    var paymentMethodButtonClicked: Boolean = false
    var voucherButtonClicked: Boolean = false
}