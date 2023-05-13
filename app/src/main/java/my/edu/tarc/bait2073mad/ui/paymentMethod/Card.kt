package my.edu.tarc.bait2073mad.ui.paymentMethod

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class Card (@PrimaryKey var cardNumber: Long, var cardHolderName: String, var cardExpiredDay: String, var cardCvc: Int){
    override fun toString(): String {
        return "$cardNumber: $cardHolderName: $cardExpiredDay: $cardCvc"
    }

}