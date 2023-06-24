package com.example.food_delivery_app.Retrofit
import com.example.food_delivery_app.Fragments.liste_menu
import com.example.food_delivery_app.Fragments.liste_restaurant
import com.example.food_delivery_app.Models.*
import com.example.food_delivery_app.Utils.Constant
import retrofit2.Response
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    @GET("res/1")
    suspend fun getAllRestaurants(): retrofit2.Response<List<restaurant>>

    @GET("res/{id}/menu")
    suspend fun getRestaurantMenuById(
        @Path("id") id: String
    ): Response<List<menu>>

    @POST("menu/{id}/rate")
    suspend fun rateMenu(
        @Path("id") id: String,
        @Body body: Rate
    ): Response<liste_menu>

    @POST("users/login")
    suspend fun login(
        @Body body: Credentials
    ): Response<user>
    @POST("users/{id}")
    suspend fun createToken(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): Response<String>
    @POST("users/register")
    suspend fun register(
        @Body body: user
    ): Response<user>
    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    val url= Constant().url
                    endpoint = Retrofit.Builder().baseUrl(Constant().url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }

}