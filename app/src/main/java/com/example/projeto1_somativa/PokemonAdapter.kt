package com.example.projeto1_somativa

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto1_somativa.databinding.PokemonItemBinding
import com.example.projeto1_somativa.model.Pokemon

class PokemonAdapter(private val context : Context, private val pokemonList : MutableList<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(binding : PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val pokemonImage = binding.imageViewPokemon
        val pokemonName = binding.textViewPokemonName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val pokemon = PokemonItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return PokemonViewHolder(pokemon)
    }

    override fun getItemCount() = pokemonList.size
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.pokemonName.text = pokemonList[position].name
    }

}