package my.edu.tarc.bait2073mad.ui.voucher

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voucherItem")
data class VoucherItem(@PrimaryKey var voucherName: String, var voucherTerms: String)

