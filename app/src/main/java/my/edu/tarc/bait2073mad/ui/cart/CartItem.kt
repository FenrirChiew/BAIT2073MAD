package my.edu.tarc.bait2073mad.ui.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItem")
data class CartItem(@PrimaryKey val productID: String, val productName: String, val productPrice: Double, val quantity: Int) {
    override fun toString(): String {
        return "$productName: $productPrice"
    }
}
