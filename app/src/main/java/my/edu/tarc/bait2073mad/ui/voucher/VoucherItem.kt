package my.edu.tarc.bait2073mad.ui.voucher

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "voucherItem")
data class VoucherItem(@PrimaryKey var voucherName: String, var voucherTerms: String, var voucherDate: String){
    override fun toString(): String {
        return "$voucherName: $voucherTerms: $voucherDate"
    }
}
