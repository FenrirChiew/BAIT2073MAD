package my.edu.tarc.bait2073mad.ui.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItem")
data class CartItem(@PrimaryKey var productID: String, var productName: String, var productPrice: Double, var quantity: Int)
