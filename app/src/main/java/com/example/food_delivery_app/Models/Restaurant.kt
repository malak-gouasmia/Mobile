package com.example.food_delivery_app.Models

import java.net.URL
import java.sql.Time

data class restaurant(
                       val id:Int,
                       val name:String,
                       val picture: String,
                       val logo:String,
                       val locationAddress :String,
                       val locationMapLat :Float,
                      val locationMaplong:Float ,
                       val cuisineType: String,
                       val 	phoneNumber:String,
                       val averageRating:Float,
                       val 	email:String,
                       val instaLink: String,
                       val 	fbLink: String,


                       )
