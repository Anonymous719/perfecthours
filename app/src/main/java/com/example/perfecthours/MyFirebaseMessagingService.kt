package com.example.perfecthours

import com.google.firebase.messaging.FirebaseMessagingService
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("message","messageReceived")

    }
}
