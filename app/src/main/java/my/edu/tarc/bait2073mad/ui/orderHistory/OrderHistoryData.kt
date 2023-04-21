package my.edu.tarc.bait2073mad.ui.orderHistory

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "orderHistory")
data class OrderHistoryData(@PrimaryKey var orderID: String, var orderDate: Date,var deliveryAddress: String, var purchasedProduct: List<Double>, var paymentMethod: String)
