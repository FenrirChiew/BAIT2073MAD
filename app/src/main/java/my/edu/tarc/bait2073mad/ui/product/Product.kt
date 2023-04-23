package my.edu.tarc.bait2073mad.ui.product

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey var productID: String,
    var productName: String,
    var productPrice: Double,
    var productImage: Bitmap,
    var productStatus: String,
    var favorite: Boolean,
    var productDescriptions: String,
    var seller: String,
)
