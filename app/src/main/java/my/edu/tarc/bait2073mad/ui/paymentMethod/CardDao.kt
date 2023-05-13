package my.edu.tarc.bait2073mad.ui.paymentMethod

import androidx.lifecycle.LiveData
import androidx.room.*
import my.edu.tarc.bait2073mad.ui.voucher.VoucherItem

@Dao
interface CardDao {
    @Query("SELECT * FROM card ORDER BY cardNumber ASC")
    fun getAllCard(): LiveData<List<Card>>

    @Query("DELETE FROM card")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(card: Card)

    @Delete
    suspend fun delete(card: Card)

    @Update
    suspend fun update(card: Card)
}