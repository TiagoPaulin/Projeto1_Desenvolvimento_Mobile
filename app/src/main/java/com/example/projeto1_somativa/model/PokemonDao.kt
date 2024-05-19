package com.example.projeto1_somativa.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PokemonDao {

    @Insert
    fun insert(pokemon : PokemonData)

    @Query("SELECT * FROM table_pokemon WHERE name = :name")
    fun getByName(name : String) : PokemonData

    @Query("SELECT * FROM table_pokemon")
    fun getAll() : List<PokemonData>

    @Update
    fun update(pokemon : PokemonData) : Int

    @Delete
    fun delete(pokemon : PokemonData) : Int
}