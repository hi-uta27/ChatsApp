package com.tavanhieu.chatapp.fcm_notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.activity.MainActivity
import com.tavanhieu.chatapp.m_class.HangSo

class MyFirebaseMessagingResponse: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        //Nếu tài khoản người dùng đang đăng nhập trên app thì nhận thông báo:
        if(message.notification != null)
            sendNotifications(message.notification!!.title!!, message.notification!!.body!!)
        else
            super.onMessageReceived(message)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotifications(title: String, content: String) {
        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder(this, HangSo.CHANEL_ID)
            .setSmallIcon(R.drawable.logo_aph)
            .setContentTitle(title)
            .setContentText(content)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setAutoCancel(true)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(HangSo.CHANEL_ID, HangSo.CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    //Some problem:
    //Tuy acc không đăng nhập... nhưng vẫn hiện thông báo trên token của máy hiện tại... 1
}