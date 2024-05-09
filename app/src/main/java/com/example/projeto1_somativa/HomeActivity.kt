package com.example.projeto1_somativa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto1_somativa.databinding.ActivityHomeBinding
import com.example.projeto1_somativa.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private val pokemonList : MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestApi()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerViewPokemon
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        pokemonAdapter = PokemonAdapter(this, pokemonList)
        recyclerView.adapter = pokemonAdapter

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

        val responseBody = api.getPokemon(url)
        val body = responseBody.string()

        val name = JSONObject(body).getJSONArray("forms").getJSONObject(0).getString("name")
        val imageUrl = JSONObject(body).getJSONObject("sprites").getString("front_default")

        return Pokemon(name, imageUrl)
    }

    private fun requestApi(){

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
            }
            pokemonList.addAll(pokemons)
            pokemonAdapter.notifyDataSetChanged()
        }
    }

}