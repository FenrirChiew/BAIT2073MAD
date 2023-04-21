package my.edu.tarc.bait2073mad.ui.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItem")
data class CartItem(@PrimaryKey var productName: String, var productImage: Int, var productPrice: Double)