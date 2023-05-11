package my.edu.tarc.bait2073mad.ui.voucher

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VoucherItemDao {

    @Query("SELECT * FROM voucherItem ORDER BY voucherName ASC")
    fun getAllVoucherItem(): LiveData<List<VoucherItem>>

    @Query("DELETE FROM voucherItem")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(voucherItem: VoucherItem)

    @Delete
    suspend fun delete(voucherItem: VoucherItem)

    @Update
    suspend fun update(voucherItem: VoucherItem)
}