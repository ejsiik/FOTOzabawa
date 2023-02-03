package com.example.fotozabawa.model.database

import android.content.Context
import androidx.room.*
import com.example.fotozabawa.model.dao.ModelDao
import com.example.fotozabawa.model.entity.Model


@Database(
    entities = [Model::class], version = 4, exportSchema = false
)
abstract class FotoDatabase : RoomDatabase(){

    abstract fun modelDao(): ModelDao

    companion object {
        @Volatile
        private var INSTANCE: FotoDatabase? = null

        fun getDatabase(context: Context): FotoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FotoDatabase::class.java,
                    "foto_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}