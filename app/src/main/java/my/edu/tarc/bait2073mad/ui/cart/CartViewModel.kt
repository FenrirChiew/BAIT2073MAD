package my.edu.tarc.bait2073mad.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel (application: Application): AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    var cartItemList : LiveData<List<CartItem>>
    private val repository: CartItemRepository //instance of repository

    //variable used to pass data to first and second fragment on which data is clicked
    var selectedIndex: Int = -1

    init {
        val cartItemDao = CartItemDatabase.getDatabase(application).cartItemDao()
        repository = CartItemRepository(cartItemDao)
        cartItemList = repository.allCartItem
    }

    //launch - use to undergo suspend function
    fun addCartItem(cartItem: CartItem) = viewModelScope.launch{
        repository.add(cartItem)
    }

    //used to update the contact information
    fun updateCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.update(cartItem)
    }

    //used to delete the contact
    fun deleteCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.delete(cartItem)
    }

    //override the local data when cloud data is downloaded
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}