package com.example.food_delivery_app.Models

import androidx.room.Entity
import java.io.Serializable

data class menu( var id:Int,

                 var restaurant_id:Int,
                 var name:String,
                 var description:String,
                 var price: Double,
                 var image:String,
                 var categorie:String,




                 ) :


    Serializable
