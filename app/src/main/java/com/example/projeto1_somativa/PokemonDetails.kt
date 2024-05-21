package com.example.projeto1_somativa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.projeto1_somativa.model.Pokemon
import com.example.projeto1_somativa.model.Singleton

class PokemonDetails : AppCompatActivity() {

    private lateinit var image : ImageView
    private lateinit var name : TextView
    private lateinit var type : TextView
    private lateinit var color : TextView
    private lateinit var captureRate : TextView
    private lateinit var description : TextView

    private lateinit var homeButton : Button
    private lateinit var saveButton : Button
    private lateinit var deleteButton : Button
    private lateinit var backButton : Button
    private lateinit var nextButton : Button

    private  var position : Int = -1

    private lateinit var pokemonList : MutableList<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        image = findViewById(R.id.pokemonImage)
        name = findViewById(R.id.pokemonName)
        type = findViewById(R.id.pokemonType)
        color = findViewById(R.id.pokemonColor)
        captureRate = findViewById(R.id.pokemonCaptureRate)
        description = findViewById(R.id.pokemonDescription)

        homeButton = findViewById(R.id.buttonHome)
        saveButton = findViewById(R.id.buttonSave)
        deleteButton = findViewById(R.id.buttonDelete)
        backButton = findViewById(R.id.buttonBack)
        nextButton = findViewById(R.id.buttonNext)

        position = intent.getIntExtra("position", -1)
        val list = intent.getStringExtra("list")

        if (list == "R") {

            pokemonList = Singleton.pokemonsRequest

        } else {

            pokemonList = Singleton.pokemonsData

        }

        var pokemon = pokemonList[position]

        loadData(pokemon)

        homeButton.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("doRequest", false)
            if (list == "D") {
                intent.putExtra("isRequest", false)
            }
            startActivity(intent)

        }

        saveButton.setOnClickListener {

            pokemon.saved = true
            Singleton.addPokemon(pokemon)
            buttonVisibility(pokemon)

        }

        deleteButton.setOnClickListener {

            Singleton.deletePokemon(pokemon)
            pokemon.saved = false
            buttonVisibility(pokemon)

        }

        backButton.setOnClickListener {

            position = position - 1
            pokemon = pokemonList[position]
            buttonVisibility(pokemon)
            loadData(pokemon)

        }

        nextButton.setOnClickListener {

            position = position + 1
            pokemon = pokemonList[position]
            buttonVisibility(pokemon)
            loadData(pokemon)

        }
    }

    private fun buttonVisibility(pokemon : Pokemon){

        if (position == 0) {

            backButton.visibility = View.INVISIBLE

        } else if(0 < position && position < pokemonList.size - 1) {

            backButton.visibility = View.VISIBLE
            nextButton.visibility = View.VISIBLE

        } else {

            nextButton.visibility = View.INVISIBLE

        }

        if (pokemon.saved) {

            saveButton.visibility = View.INVISIBLE
            deleteButton.visibility = View.VISIBLE

        } else {

            saveButton.visibility = View.VISIBLE
            deleteButton.visibility = View.INVISIBLE

        }

    }

    private fun loadData(pokemon : Pokemon) {

        buttonVisibility(pokemon)

        Glide.with(this)
            .load(pokemon.imageUrl)
            .into(image)
        name.text = "Name: " + pokemon.name
        type.text = "Type: " + pokemon.type
        color.text = "Color: " + pokemon.color
        captureRate.text = "CaptureRate: " + pokemon.captureRate
        description.text = "Description: \n" + pokemon.description

    }

}