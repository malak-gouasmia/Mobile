package com.example.food_delivery_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.example.food_delivery_app.R

class page_intro1 : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.page_intro1)
        }

        fun allerPageSuivante(view: View) {
            val intent = Intent(this, page_intro2::class.java)
            println("*********************************************************************************")
            startActivity(intent)
        }

    fun skip(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    }




