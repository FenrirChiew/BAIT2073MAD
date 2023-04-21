package my.edu.tarc.bait2073mad.ui.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//version will always be 1 at first but when there is update then need change the version by incremental
@Database(entities = arrayOf(CartItem::class), version = 1, exportSchema = false)
abstract class CartItemDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    companion object {
        //Singleton prevents multiple instances of database opening at the same time
        //singleton = can have 1 instance
        @Volatile
        private var INSTANCE: CartItemDatabase? = null

        fun getDatabase(context: Context): CartItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartItemDatabase::class.java,
                    "cartItem_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}