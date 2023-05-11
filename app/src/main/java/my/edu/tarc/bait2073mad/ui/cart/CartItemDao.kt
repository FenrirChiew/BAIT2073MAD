package my.edu.tarc.bait2073mad.ui.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cartItem ORDER BY productID ASC")
    fun getAllCartItem(): LiveData<List<CartItem>>

    @Query("DELETE FROM cartItem")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)
}