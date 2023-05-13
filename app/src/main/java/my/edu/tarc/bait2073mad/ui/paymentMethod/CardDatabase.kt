package my.edu.tarc.bait2073mad.ui.paymentMethod

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Card::class), version = 1, exportSchema = false)
abstract class CardDatabase: RoomDatabase() {
    abstract fun CardDao(): CardDao

    companion object{
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getDatabase(context: Context) : CardDatabase {
            val tempInstance = CardDatabase.INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "card_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}