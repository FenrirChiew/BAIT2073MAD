package my.edu.tarc.bait2073mad.ui.recentOrder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecentOrderViewModel (application: Application): AndroidViewModel(application) {
    var recentOrderList: LiveData<List<RecentOrder>>
    private val repository: RecentOrderRepository

    init {
        val recentOrderDao = RecentOrderDatabase.getDatabase(application).recentOrderDao()
        repository = RecentOrderRepository(recentOrderDao)
        recentOrderList = repository.allRecentOrder
    }

    fun addOrder(recentOrder: RecentOrder) = viewModelScope.launch {
        repository.add(recentOrder)
    }

    fun updateOrder(recentOrder: RecentOrder) = viewModelScope.launch {
        repository.update(recentOrder)
    }

    fun deleteOrder(recentOrder: RecentOrder) = viewModelScope.launch {
        repository.delete(recentOrder)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}