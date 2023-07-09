package com.example.food_delivery_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.food_delivery_app.R
import com.google.firebase.messaging.FirebaseMessaging

class Notifications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifictions)

        // Get the FCM token
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM Token HERE HEEERE", token)
                    // Here, you can save or use the token as needed
                } else {
                    Log.e("FCM Token", "Failed to retrieve token: ${task.exception?.message}")
                }
            }
    }
}
