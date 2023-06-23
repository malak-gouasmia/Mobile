package com.example.food_delivery_app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.food_delivery_app.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun AllerRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        println("*********************************************************************************")
        startActivity(intent)
    }
}