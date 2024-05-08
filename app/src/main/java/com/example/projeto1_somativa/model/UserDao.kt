package com.example.projeto1_somativa.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun insert(user : User)

    @Query("SELECT * FROM table_user WHERE username = :username")
    fun getByUsername(username : String) : User

    @Update
    fun update(user : User) : Int

    @Delete
    fun delete(user : User) : Int

}