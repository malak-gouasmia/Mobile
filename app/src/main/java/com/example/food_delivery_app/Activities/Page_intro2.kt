package com.example.food_delivery_app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.food_delivery_app.R


//import com.example.food_delivery_app.page_intro3

class page_intro2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_intro2)
    }

    fun allerPageSuivante(view: View) {
        val intent = Intent(this, page_intro3::class.java)
        startActivity(intent)
    }
    fun skip(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}