package my.edu.tarc.bait2073mad.ui.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey var productID: String,
    var productName: String,
    var productPrice: Double,
    var productStatus: String,
    var seller: String,
    var productDescriptions: String
)
