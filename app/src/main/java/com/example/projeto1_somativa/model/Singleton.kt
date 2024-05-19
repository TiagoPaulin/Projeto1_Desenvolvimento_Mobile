package com.example.projeto1_somativa.model

import android.content.Context

object Singleton {

    var pokemons : MutableList<PokemonData> = mutableListOf()

    private lateinit var userDao : UserDao
    private lateinit var pokemonDao : PokemonDao

    fun setContext(context: Context){

        AppDatabase.getInstance(context)?.apply {

            userDao = userDao()
            pokemonDao = pokemonDao()

            pokemons.addAll(pokemonDao.getAll())

        }

    }

    fun addUser(user: User){

        userDao.insert(user);

    }

    fun requestUser(username : String) : User {

        return userDao.getByUsername(username)

    }

    fun addPokemon(pokemon : PokemonData){

        pokemonDao.insert(pokemon)
        pokemons.addAll(pokemonDao.getAll())

    }

    fun deletePokemon(pokemon : PokemonData){

        pokemonDao.delete(pokemon)
        pokemons.addAll(pokemonDao.getAll())

    }

    fun requestPokemon(name : String){

        pokemonDao.getByName(name)

    }

}