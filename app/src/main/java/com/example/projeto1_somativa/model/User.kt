package com.example.projeto1_somativa.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_user")
data class User(

    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var username : String,
    var password : String

)
