package com.example.food_delivery_app.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class order(
    @PrimaryKey(autoGenerate = true)
    var id:Int =0,
    var idRestaurant: Int,
    var idMenu: Int,
    var nom: String,
    var price: Double,
    var image: String,
    var quantity: Int
)