package com.example.projeto1_somativa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projeto1_somativa.databinding.ActivityHomeBinding
import com.example.projeto1_somativa.model.Pokemon
import com.example.projeto1_somativa.model.Singleton
import com.example.projeto1_somativa.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var pokemonAdapter : PokemonAdapter

    private lateinit var pokemonList : MutableList<Pokemon>
    private lateinit var list : String

    private lateinit var viewModel : HomeViewModel

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

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val isRequest = intent.getBooleanExtra("isRequest", true)
        val doRequest = intent.getBooleanExtra("doRequest", false)

        if (isRequest) {

            binding.buttonSwitchList.text = "Favorites"
            list = "R"

        } else {

            binding.buttonSwitchList.text = "All Results"
            if (Singleton.pokemonsData.isEmpty()) {

                showMessage()

            }
            list = "D"

        }

        val recyclerView = binding.recyclerViewPokemon
        recyclerView.layoutManager = GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true)

        viewModel.pokemonLiveData.observe(this) {
            pokemonList = it
            pokemonAdapter = PokemonAdapter(this, pokemonList, list)
            recyclerView.adapter = pokemonAdapter
            recyclerView.adapter?.notifyDataSetChanged()
        }

        if (doRequest) {

            viewModel.requestApi()

        }

        viewModel.setList(isRequest)

        binding.buttonSwitchList.setOnClickListener {

            switchList(isRequest)

        }

        binding.buttonLogout.setOnClickListener {

            viewModel.logout()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

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