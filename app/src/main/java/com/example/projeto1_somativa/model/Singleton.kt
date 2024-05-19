package com.example.projeto1_somativa.model

import android.content.Context

object Singleton {

    var pokemonsData : MutableList<Pokemon> = mutableListOf()
    var pokemonsRequest : MutableList<Pokemon> = mutableListOf()

    private lateinit var userDao : UserDao
    private lateinit var pokemonDao : PokemonDao

    fun setContext(context: Context){

        AppDatabase.getInstance(context)?.apply {

            userDao = userDao()
            pokemonDao = pokemonDao()

            pokemonsData = pokemonDao.getAll()

        }

    }

    fun addUser(user: User){

        userDao.insert(user);

    }

    fun requestUser(username : String) : User {

        return userDao.getByUsername(username)

    }

    fun addPokemon(pokemon : Pokemon){

        pokemonDao.insert(pokemon)
        pokemonsData = pokemonDao.getAll()

    }

    fun deletePokemon(pokemon : Pokemon){

        pokemonDao.delete(pokemon)
        pokemonsData = pokemonDao.getAll()

    }

}