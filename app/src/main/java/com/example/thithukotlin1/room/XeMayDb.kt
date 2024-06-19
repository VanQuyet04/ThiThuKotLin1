package com.example.thithukotlin1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MayTinh::class], version = 2, exportSchema = false)
abstract class MayTinhDb : RoomDatabase() {

    abstract fun mayTinhDao(): MayTinhDao

    companion object {
        @Volatile
        private var INSTANCE: MayTinhDb? = null

        fun getDatabase(context: Context): MayTinhDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MayTinhDb::class.java,
                    "MayTinh.db"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }
    }

}
