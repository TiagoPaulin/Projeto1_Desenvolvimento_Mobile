package com.example.projeto1_somativa.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert
    fun insert(pokemon : Pokemon)

    @Query("SELECT * FROM table_pokemon")
    fun getAll() : MutableList<Pokemon>

    @Query("SELECT * FROM TABLE_POKEMON WHERE name = :name AND userId = :userId")
    fun getByName(name : String, userId : Int) : Pokemon

    @Query("SELECT * FROM table_pokemon WHERE userId = :id")
    fun getAllByUserId(id : Int) : MutableList<Pokemon>

    @Delete
    fun delete(pokemon : Pokemon) : Int

}