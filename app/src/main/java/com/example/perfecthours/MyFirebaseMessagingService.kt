package com.example.perfecthours

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView

import androidx.core.app.NotificationCompat
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId="notification_channel"
const val channelName="com.example.perfecthours"
class MyFirebaseMessagingService: FirebaseMessagingService() {
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.perfecthours",R.layout.notification)
        return remoteView

    }

    fun generateNotification(title:String,message:String) {

        val intent = Intent ( this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)



        var builder:NotificationCompat.Builder =NotificationCompat.Builder(applicationContext,
            channelId)
            .setSmallIcon(R.drawable.vectorimage)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        builder=builder.setContent(getRemoteView(title,message))
        // val notificationManager= GetSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        fun onMessageReceived(message: RemoteMessage) {
            super.onMessageReceived(message)
        }



    }


}
