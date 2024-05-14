package com.example.projeto1_somativa

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface PokeApi {

    @GET("pokemon?limit=100")
    suspend fun getUrl() : ResponseBody

    @GET
    suspend fun getPokemon(@Url url : String) : ResponseBody

    @GET
    suspend fun getPokemonType(@Url url : String) : ResponseBody

    @GET
    suspend fun getPokemonDescription(@Url url : String) : ResponseBody

}