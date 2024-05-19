package com.example.projeto1_somativa.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("table_pokemon")
data class Pokemon(
    @PrimaryKey
    val name : String,
    val type : String,
    val color : String,
    val captureRate : String,
    val description : String,
    val imageUrl : String
)
