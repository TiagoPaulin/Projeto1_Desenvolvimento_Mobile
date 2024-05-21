package com.example.projeto1_somativa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projeto1_somativa.databinding.ActivityHomeBinding
import com.example.projeto1_somativa.model.Pokemon
import com.example.projeto1_somativa.model.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var pokemonAdapter : PokemonAdapter

    private lateinit var pokemonList : MutableList<Pokemon>
    private lateinit var list : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val doRequest = intent.getBooleanExtra("doRequest", false)
        val isRequest = intent.getBooleanExtra("isRequest", true)

        if (doRequest) {

            requestApi()

        }

        if (isRequest) {

            binding.buttonSwitchList.text = "Visualizar Favoritos"
            pokemonList = Singleton.pokemonsRequest
            list = "R"

        } else {

            binding.buttonSwitchList.text = "Visualizar Todos"
            pokemonList = Singleton.pokemonsData
            if (pokemonList.isEmpty()) {

                showMessage()

            }
            list = "D"

        }

        binding.buttonSwitchList.setOnClickListener {

            switchList(isRequest)

        }

        val recyclerView = binding.recyclerViewPokemon
        recyclerView.layoutManager = GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true)

        pokemonAdapter = PokemonAdapter(this, pokemonList, list)
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
            Singleton.pokemonsRequest.addAll(pokemons)
            pokemonAdapter.notifyDataSetChanged()
        }
    }

    private fun switchList(isRequest : Boolean) {

        if (isRequest) {

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("isRequest", false)
            startActivity(intent)

        } else {

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("isRequest", true)
            intent.putExtra("doRequest", false)
            startActivity(intent)

        }

    }

    private fun showMessage(){

        val builder = AlertDialog.Builder(this);

        builder.setTitle("MENSAGEM");
        builder.setMessage("Você não possui nenhum pokemon favoritado");
        builder.setPositiveButton("OK") { dialog, _ ->

            switchList(false)

        }

        val dialog = builder.create();

        dialog.show();

    }

}