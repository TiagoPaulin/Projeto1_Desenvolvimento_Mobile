package com.example.projeto1_somativa

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.projeto1_somativa.model.Pokemon

class PokemonDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pokemon: Pokemon? = intent.getSerializableExtra("pokemon") as? Pokemon

        if (pokemon != null) {

            Glide.with(this)
                .load(pokemon.imageUrl)
                .into(findViewById(R.id.pokemonImage))
            findViewById<TextView>(R.id.pokemonName).text = pokemon.name
            findViewById<TextView>(R.id.pokemonType).append(pokemon.type)
            findViewById<TextView>(R.id.pokemonColor).append(pokemon.color)
            findViewById<TextView>(R.id.pokemonCaptureRate).append(pokemon.captureRate)
            findViewById<TextView>(R.id.pokemonDescription).append("\n" + pokemon.description)

        }
    }
}