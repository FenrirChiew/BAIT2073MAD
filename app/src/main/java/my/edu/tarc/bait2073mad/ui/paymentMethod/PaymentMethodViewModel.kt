package my.edu.tarc.bait2073mad.ui.paymentMethod

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.bait2073mad.ui.voucher.VoucherItem
import my.edu.tarc.bait2073mad.ui.voucher.VoucherItemDatabase
import my.edu.tarc.bait2073mad.ui.voucher.VoucherItemRepository

class PaymentMethodViewModel (application: Application): AndroidViewModel(application) {
    //LiveData gives us updated voucher item when they change
    var cardList : LiveData<List<Card>>
    private val repository: CardRepository

    init {
        val cardDao = CardDatabase.getDatabase(application).CardDao()
        repository = CardRepository(cardDao)
        cardList = repository.allCard
    }

    fun addCard(card: Card) = viewModelScope.launch{
        repository.add(card)
    }

    fun updateCard(card: Card) = viewModelScope.launch {
        repository.update(card)
    }

    //used to delete the contact
    fun deleteCard(card: Card) = viewModelScope.launch {
        repository.delete(card)
    }

    //override the local data when cloud data is downloaded
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}