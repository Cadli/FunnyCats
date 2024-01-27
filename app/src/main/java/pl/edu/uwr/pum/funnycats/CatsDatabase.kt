package pl.edu.uwr.pum.funnycats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatsDatabase : RoomDatabase() {
    abstract fun catsDao(): CatsDao

    companion object{
        @Volatile private var INSTANCE: CatsDatabase? = null

        fun getDatabase(context: Context): CatsDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatsDatabase::class.java,
                    "cats_database"
                ).build().also { INSTANCE = it }
                instance
            }
        }
    }
}