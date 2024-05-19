package com.example.projeto1_somativa.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, PokemonData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun pokemonDao() : PokemonDao

    companion object{
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase?{

            if (instance == null){

                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_data.sqlite"
                    )
                        .allowMainThreadQueries()
                        .build()

                }

            }

            return instance

        }
    }

}