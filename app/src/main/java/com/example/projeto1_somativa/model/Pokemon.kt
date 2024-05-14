package com.example.projeto1_somativa.model

import java.io.Serializable

data class Pokemon(
    val name : String,
    val type : String,
    val color : String,
    val description : String,
    val imageUrl : String
) : Serializable
