package my.edu.tarc.bait2073mad.ui.recentOrder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "recentOrder")
data class RecentOrder(@PrimaryKey var orderID: String, var orderDate: String, var total: Double){
}
