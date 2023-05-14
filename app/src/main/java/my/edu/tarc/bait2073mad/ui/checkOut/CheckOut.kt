package my.edu.tarc.bait2073mad.ui.checkOut

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkOut")
data class CheckOut (@PrimaryKey var totalItem: Int, var paymentAmount: Double, ){
    override fun toString(): String {
        return "$paymentAmount"
    }
}

