package my.edu.tarc.bait2073mad.ui.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import my.edu.tarc.bait2073mad.ui.product.Product

@Entity(tableName = "cartItem")
data class CartItem(@PrimaryKey var product: Product,var quantity: Int)
