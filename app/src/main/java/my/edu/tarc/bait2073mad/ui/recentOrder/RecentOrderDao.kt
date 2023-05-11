package my.edu.tarc.bait2073mad.ui.recentOrder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecentOrderDao {
    @Query("SELECT * FROM recentOrder ORDER BY orderID ASC")
    fun getAllRecentOrder(): LiveData<List<RecentOrder>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recentOrder: RecentOrder)

    @Delete
    suspend fun delete(recentOrder: RecentOrder)

    @Update
    suspend fun update(recentOrder: RecentOrder)
}