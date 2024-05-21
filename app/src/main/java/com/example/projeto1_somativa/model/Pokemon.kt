package com.example.projeto1_somativa.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "table_pokemon",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["userId"])]
)
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val type : String,
    val color : String,
    val captureRate : String,
    val description : String,
    val imageUrl : String,
    var saved : Boolean,

    val userId : Int
)
