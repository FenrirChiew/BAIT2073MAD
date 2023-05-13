package my.edu.tarc.bait2073mad.ui.paymentMethod

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import my.edu.tarc.bait2073mad.ui.voucher.VoucherItem
import my.edu.tarc.bait2073mad.ui.voucher.VoucherItemDao

class CardRepository(private val cardDao: CardDao) {
    val allCard: LiveData<List<Card>> = cardDao.getAllCard()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(card: Card){
        cardDao.insert(card)
    }

    @WorkerThread
    suspend fun delete(card: Card){
        cardDao.delete(card)
    }

    @WorkerThread
    suspend fun update(card: Card){
        cardDao.update(card)
    }

    @WorkerThread
    suspend fun deleteAll(){
        cardDao.deleteAll()
    }
}