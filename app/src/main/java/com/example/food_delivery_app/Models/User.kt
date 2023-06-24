package com.example.food_delivery_app.Models


import androidx.room.Entity
import androidx.room.PrimaryKey
data class user(
    val _id:Int,
    val username:String,
    val email:String,
    val password:String,
    val phone:String,
    val profilePic:String,

    )

data class Credentials(val email:String,val password:String)