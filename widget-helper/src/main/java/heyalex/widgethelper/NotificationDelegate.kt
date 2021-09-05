package heyalex.widgethelper

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationDelegate {
    companion object {
        @JvmStatic
        @TargetApi(Build.VERSION_CODES.O)
        fun getNotification(context: Context): Notification {
            val channel = NotificationChannel(
                WidgetUpdater.NOTIFICATION_CHANNEL_ID,
                WidgetUpdater.channelName,
                NotificationManager.IMPORTANCE_NONE
            )
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val notificationBuilder =
                Notification.Builder(context, WidgetUpdater.NOTIFICATION_CHANNEL_ID)
            notificationBuilder.setOngoing(true)
            notificationBuilder.setContentTitle("Update widget")
                .setContentText("Wait for finish updating")
                .setSmallIcon(R.drawable.stat_notify_sync)
            return notificationBuilder.build()
        }
    }
}