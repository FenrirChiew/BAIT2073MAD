package my.edu.tarc.bait2073mad.ui.cart

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CartItemRepository(private val cartItemDao: CartItemDao) {
    //Room execute all queries on a separate thread
    val allCartItem: LiveData<List<CartItem>> = cartItemDao.getAllCartItem()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(cartItem: CartItem){
        cartItemDao.insert(cartItem)
    }

    @WorkerThread
    suspend fun delete(cartItem: CartItem){
        cartItemDao.delete(cartItem)
    }

    @WorkerThread
    //call the function coroutine --> asynchronous task
    suspend fun update(cartItem: CartItem){
        cartItemDao.update(cartItem)
    }
}