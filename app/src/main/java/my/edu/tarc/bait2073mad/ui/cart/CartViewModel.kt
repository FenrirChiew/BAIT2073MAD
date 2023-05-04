package my.edu.tarc.bait2073mad.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel (application:Application) : AndroidViewModel(application) {
    var cartItemList: LiveData<List<CartItem>>
    private val repository: CartItemRepository

    //variable used to pass data between fragment
    var selectedIndex: Int = -1

    init{
        val cartItemDao = CartItemDatabase.getDatabase(application).cartItemDao()
        repository = CartItemRepository(cartItemDao)
        cartItemList = repository.allCartItem
    }

    fun addCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.add(cartItem)
    }

    //used to update the contact information
    fun updateContact(cartItem: CartItem) = viewModelScope.launch {
        repository.update(cartItem)
    }

    //used to delete the contact
    fun deleteContact(cartItem: CartItem) = viewModelScope.launch {
        repository.delete(cartItem)
    }
}