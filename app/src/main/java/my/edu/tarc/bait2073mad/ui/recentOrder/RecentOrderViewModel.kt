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

    fun addRecentOrder(recentOrder: RecentOrder) = viewModelScope.launch {
        repository.add(recentOrder)
    }
}