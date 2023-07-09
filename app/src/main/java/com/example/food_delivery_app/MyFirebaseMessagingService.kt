package com.example.food_delivery_app.com.example.food_delivery_app
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.food_delivery_app.Activities.Notifications
import com.example.food_delivery_app.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.google.firebase.FirebaseMessagingService"

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage : RemoteMessage){
        if(remoteMessage.getNotification()!= null){
            generateNotification(remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!)
        }
    }

    fun getRemoteView(title: String,message: String) : RemoteViews{
        val remoteView = RemoteViews("com.example.food_delivery_app", R.layout.notifictions)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, title)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.logo)

        return remoteView
    }
    fun generateNotification(title: String, message: String){
        val intent = Intent(this , Notifications::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT)
        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,
            channelId)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if the device's API level is 26 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the notification channel
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 , builder.build())
    }

}