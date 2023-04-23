package my.edu.tarc.bait2073mad.ui.checkOut

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItem")
data class CheckOutItem(@PrimaryKey var productName: String, var productImage: Int, var productPrice: Double, var productQuantity: Int)