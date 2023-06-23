package com.example.food_delivery_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent

import android.os.Handler
import com.example.food_delivery_app.R


class page_logo_name : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_logo_name)




        // Créer un délai de 10 secondes avant de lancer la deuxième activité
        Handler().postDelayed({
            val intent = Intent(this@page_logo_name , page_intro1::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}