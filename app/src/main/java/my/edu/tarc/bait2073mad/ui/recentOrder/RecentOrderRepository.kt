package my.edu.tarc.bait2073mad.ui.recentOrder

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RecentOrderRepository (private val recentOrderDao: RecentOrderDao){
    //Room execute all queries on a separate thread
    val allRecentOrder: LiveData<List<RecentOrder>> = recentOrderDao.getAllRecentOrder()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(recentOrder: RecentOrder){
        recentOrderDao.insert(recentOrder)
    }

    @WorkerThread
    suspend fun delete(recentOrder: RecentOrder){
        recentOrderDao.delete(recentOrder)
    }

    @WorkerThread
    //call the function coroutine --> asynchronous task
    suspend fun update(recentOrder: RecentOrder){
        recentOrderDao.update(recentOrder)
    }

    @WorkerThread
    suspend fun deleteAll(){
        recentOrderDao.deleteAll()
    }
}