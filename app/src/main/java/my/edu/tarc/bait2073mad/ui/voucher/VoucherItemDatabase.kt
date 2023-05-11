package my.edu.tarc.bait2073mad.ui.voucher

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = arrayOf(VoucherItem::class), version = 1, exportSchema = false)
abstract class VoucherItemDatabase: RoomDatabase() {
    abstract fun voucherItemDao(): VoucherItemDao

    companion object{
        //Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: VoucherItemDatabase? = null

        fun getDatabase(context: Context) : VoucherItemDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VoucherItemDatabase::class.java,
                    "voucher_item_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}