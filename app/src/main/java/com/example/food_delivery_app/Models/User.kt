package com.example.food_delivery_app.Models


import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "users")
data class user(
    @PrimaryKey
    val id:Int,
    val firstName:String,val lastName:String,val email:String, val password:String
)