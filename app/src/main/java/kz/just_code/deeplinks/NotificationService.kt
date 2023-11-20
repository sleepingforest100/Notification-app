package kz.just_code.deeplinks

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "FCM TOKEN: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
//        showMessage(createNotification(message.notification?.title, message.notification?.body))
        showMessage(
            createNotification(
                message.data["title"],
                message.data["body"],
                message.data["sub_title"],
                message.data["btn_name"],

            )
        )
//        Log.e(
//            "MESSAGE",
//            "message: ${message.notification?.body} ,title: ${message.notification?.title}"
//        )

    }

    private fun createNotification(
        title: String?,
        message: String?,
        subtitle: String? = null,
        name: String?
    ): Notification {
        return NotificationCompat.Builder(baseContext, getChannelId())
            .setSmallIcon(R.drawable.ic_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText(subtitle)
            .setColor(ContextCompat.getColor(baseContext, R.color.red))
            .setOngoing(false)
            .setVibrate(LongArray(250))
            .setContentIntent(
                createPendingIntent(
                    bundleOf(
                        "name" to name
                    )
                )
            )
//        .setLights()

//        .setSound()
//            .addAction(android.R.drawable.ic_delete, "delete", getDeleteAction(context))
//            .addAction(android.R.drawable.ic_dialog_alert, "show", getShowAction(context))
            // .setLargeIcon(context.createBitmap(R.drawable.ic_large))
            // .setProgress(100, 25, false)
//        .setStyle(
//            // NotificationCompat.BigPictureStyle().bigPicture(context.createBitmap(R.drawable.ic_large))
//            //BigTextStyle().bigText(bigText)
////            InboxStyle()
////                .addLine("1. First line")
////                .addLine("2. second line")
////                .addLine("3. third line")
////                .setBigContentTitle("Big Content title")
//            getMessagingStyle(context)
//        )
            .build()
    }

    private fun getChannelId(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "JustCodeNotification"
            val channelName = "JustCodeName"
            val channelDescription = "Channel for just code notification"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            channel.description = channelDescription

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            channelId
        } else ""
    }

    private fun showMessage(notification: Notification) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(4567, notification)
    }

    private fun createPendingIntent(
        args: Bundle?
    ): PendingIntent {
        return NavDeepLinkBuilder(baseContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.secondFragment)
            .setArguments(args)
            .createPendingIntent()
    }
}