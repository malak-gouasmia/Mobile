package com.example.food_delivery_app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.food_delivery_app.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    fun AllerLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        println("*********************************************************************************")
        startActivity(intent)
    }
}