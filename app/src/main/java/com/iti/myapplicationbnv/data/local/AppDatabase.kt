package com.iti.myapplicationbnv.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iti.myapplicationbnv.data.data.local.FavoriteMeal
import com.iti.myapplicationbnv.data.data.local.FavoriteMealDao
import com.iti.myapplicationbnv.data.data.local.UserEntity

@Database(entities = [UserEntity::class, FavoriteMeal::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun favoriteMealDao(): FavoriteMealDao


    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
