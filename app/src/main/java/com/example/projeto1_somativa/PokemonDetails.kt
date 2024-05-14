package com.example.projeto1_somativa

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        // Receber o Pokemon do Intent
        val pokemon: Pokemon? = intent.getSerializableExtra("pokemon") as? Pokemon

        // Verificar se o Pokemon não é nulo antes de usar
        if (pokemon != null) {
            // Agora você pode usar o objeto Pokemon como quiser
            findViewById<TextView>(R.id.nome).text = pokemon.name + " " + pokemon.type + " " + pokemon.color + " " + pokemon.description
        }
    }
}