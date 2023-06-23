package com.example.food_delivery_app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.food_delivery_app.Activities.MainActivity
import com.example.food_delivery_app.R
class page_intro3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_intro3)
    }


    fun allerPageSuivante(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}