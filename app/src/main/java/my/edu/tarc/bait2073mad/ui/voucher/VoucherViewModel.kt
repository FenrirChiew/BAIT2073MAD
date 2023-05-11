package my.edu.tarc.bait2073mad.ui.voucher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VoucherViewModel (application: Application): AndroidViewModel(application) {
    //LiveData gives us updated voucher item when they change
    var voucherItemList : LiveData<List<VoucherItem>>
    private val repository: VoucherItemRepository

    init {
        val voucherItemDao = VoucherItemDatabase.getDatabase(application).voucherItemDao()
        repository = VoucherItemRepository(voucherItemDao)
        voucherItemList = repository.allVoucherItem
    }

    fun addVoucher(voucherItem: VoucherItem) = viewModelScope.launch{
        repository.add(voucherItem)
    }
}
