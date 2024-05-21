package com.example.projeto1_somativa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projeto1_somativa.databinding.PokemonItemBinding
import com.example.projeto1_somativa.model.Pokemon

class PokemonAdapter(private val context : Context, private val pokemonList : MutableList<Pokemon>, val list : String) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(binding : PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val pokemonName = binding.textViewPokemonName
        val pokemonImage = binding.imageViewPokemon

        init {
            itemView.setOnClickListener {
                val position = adapterPosition

                val intent = Intent(context, PokemonDetails::class.java)
                intent.putExtra("position", position)
                intent.putExtra("list", list)
                context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val pokemon = PokemonItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return PokemonViewHolder(pokemon)
    }

    override fun getItemCount() = pokemonList.size
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.pokemonName.text = pokemonList[position].name
        Glide.with(context)
            .load(pokemonList[position].imageUrl)
            .into(holder.pokemonImage)
    }

}