package my.edu.tarc.bait2073mad.ui.recentOrder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RecentOrder::class), version = 1, exportSchema = false)
abstract class RecentOrderDatabase : RoomDatabase() {
    abstract fun recentOrderDao(): RecentOrderDao

    companion object {
        //Singleton prevents multiple instances of database opening at the same time
        //singleton = can have 1 instance
        @Volatile
        private var INSTANCE: RecentOrderDatabase? = null

        fun getDatabase(context: Context): RecentOrderDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecentOrderDatabase::class.java,
                    "recentOrder_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}