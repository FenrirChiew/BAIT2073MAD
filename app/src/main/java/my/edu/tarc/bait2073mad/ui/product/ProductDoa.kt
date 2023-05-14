package my.edu.tarc.bait2073mad.ui.product

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM product ORDER BY productID ASC")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()

    @Update
    suspend fun update(product: Product)
}
