package my.edu.tarc.bait2073mad.ui.voucher

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class VoucherItemRepository(private val voucherItemDao: VoucherItemDao) {
    val allVoucherItem: LiveData<List<VoucherItem>> = voucherItemDao.getAllVoucherItem()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(voucherItem: VoucherItem){
        voucherItemDao.insert(voucherItem)
    }

    @WorkerThread
    suspend fun delete(voucherItem: VoucherItem){
        voucherItemDao.delete(voucherItem)
    }

    @WorkerThread
    suspend fun update(voucherItem: VoucherItem){
        voucherItemDao.update(voucherItem)
    }

    @WorkerThread
    suspend fun deleteAll(){
        voucherItemDao.deleteAll()
    }
}