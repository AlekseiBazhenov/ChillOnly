package ru.modernsoft.chillonly.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.views.StationsActivity

class ChillNotification(private val context: Service) { //todo refactoring

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder

    fun createNotification(title: String) {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context, "ru.modernsoft.chillonly", "ChillOnly Service")
        } else {
            ""
        }

        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(context.resources.getString(R.string.connecting))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setSmallIcon(R.mipmap.ic_launcher)

        val notificationIntent = Intent(context, StationsActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        notificationBuilder.setContentIntent(pendingIntent)
        context.startForeground(100, buildNotification())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Service, channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    fun updateNotification(track: String) {
        notificationBuilder.setContentText(track)
        notificationManager.notify(100, buildNotification())
    }

    private fun buildNotification(): Notification {
        return notificationBuilder.build()
    }
}
