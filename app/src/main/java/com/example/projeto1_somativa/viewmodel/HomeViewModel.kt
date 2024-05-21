package com.example.projeto1_somativa.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projeto1_somativa.HomeActivity
import com.example.projeto1_somativa.PokeApi
import com.example.projeto1_somativa.model.Pokemon
import com.example.projeto1_somativa.model.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    val pokemonLiveData = MutableLiveData<MutableList<Pokemon>>()

    fun requestApi(){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(PokeApi::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val urls = getUrls(api)
            val pokemons = mutableListOf<Pokemon>()
            for (url in urls) {
                val pokemon = getPokemonInfo(api, url)
                pokemons.add(pokemon)
                pokemonLiveData.value = pokemons
            }
            Singleton.pokemonsRequest.addAll(pokemons)
            pokemonLiveData.value = Singleton.pokemonsRequest
        }
    }

    private suspend fun getUrls(api : PokeApi) : MutableList<String>{

        val urls = mutableListOf<String>()

        val responseBody = api.getUrl()
        val body = responseBody.string()

        val resultsArray = JSONObject(body).getJSONArray("results")

        for (i in 0 until resultsArray.length()) {
            val pokemonObject = resultsArray.getJSONObject(i)
            val url = pokemonObject.getString("url")
            urls.add(url)
        }

        return urls
    }

    private suspend fun getPokemonInfo(api : PokeApi, url : String) : Pokemon {

        val responseBodyPokemon = api.getPokemon(url)
        val bodyPokemon = responseBodyPokemon.string()

        val name = JSONObject(bodyPokemon).getJSONArray("forms").getJSONObject(0).getString("name")

        val pokemon = Singleton.requestPokemon(name, Singleton.id)

        if (pokemon != null) {

            return pokemon

        } else {

            val imageUrl = JSONObject(bodyPokemon).getJSONObject("sprites").getString("front_default")

            val responseBodyPokemonType = api.getPokemonType("https://pokeapi.co/api/v2/pokemon/" + name)
            val bodyPokemonType = responseBodyPokemonType.string()

            val typesArray = JSONObject(bodyPokemonType).getJSONArray("types")
            val typeStringBuilder = StringBuilder()

            for (i in 0 until typesArray.length()) {

                val typeName = typesArray.getJSONObject(i).getJSONObject("type").getString("name")
                typeStringBuilder.append(typeName)

                if (i < typesArray.length() - 1) {
                    typeStringBuilder.append(" | ")
                }

            }

            val type = typeStringBuilder.toString()
            val urlDescription = JSONObject(bodyPokemonType).getJSONObject("species").getString("url")

            val responseBodyPokemonDescription = api.getPokemonDescription(urlDescription)
            val bodyPokemonDescription = responseBodyPokemonDescription.string()

            val color = JSONObject(bodyPokemonDescription).getJSONObject("color").getString("name")
            val description = JSONObject(bodyPokemonDescription).getJSONArray("flavor_text_entries").getJSONObject(0).getString("flavor_text").replace("\n", " ")
            val captureRate = JSONObject(bodyPokemonDescription).getString("capture_rate")

            return Pokemon(0,name, type, color, captureRate, description, imageUrl, false, Singleton.id)

        }

    }

    fun setList(isRequest : Boolean){

        if(isRequest){

            pokemonLiveData.value = Singleton.pokemonsRequest

        } else {

            pokemonLiveData.value = Singleton.pokemonsData

        }

    }

}