package my.edu.tarc.bait2073mad.ui.cart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cartItem ORDER BY product ASC")
    fun getAllCartItem(): LiveData<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)
}