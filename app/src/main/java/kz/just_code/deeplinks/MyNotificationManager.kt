package kz.just_code.deeplinks

import android.app.Notification
import android.app.Notification.BigPictureStyle
import android.app.Notification.MessagingStyle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import androidx.core.app.NotificationCompat.InboxStyle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat


class MyNotificationManager(context: Context) {

    private val bigText =
        "Sam squinted against the sun at the distant dust trail raked up by the car on its way up to the Big House. The horses kicked and flicked their tails at flies, not caring about their owner's first visit in ten months. Sam waited. Mr Carter didn't come out here unless he had to, which was just fine by Sam. The more he kept out of his boss's way, the longer he'd have a job."
    private val notification = NotificationCompat.Builder(context, getChannelId(context))
        .setSmallIcon(R.drawable.ic_icon)
        .setContentTitle("My custom title")
        .setContentText("My one line notification text ")
        .setSubText("Sub text")
//        .setOngoing(true)
//        .setLights()
//        .setVibrate()
//        .setSound()
        .addAction(android.R.drawable.ic_delete, "delete", getDeleteAction(context))
        .addAction(android.R.drawable.ic_dialog_alert, "show", getShowAction(context))
        .setColor(ContextCompat.getColor(context, R.color.red))
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

    private fun getMessagingStyle(context: Context): NotificationCompat.MessagingStyle {

        val sender = androidx.core.app.Person.Builder()
            .setName("You")
            .setIcon(IconCompat.createWithBitmap(context.createBitmap(R.drawable.ic_person1)))
            .build()
        val Ivan = androidx.core.app.Person.Builder()
            .setIcon(IconCompat.createWithBitmap(context.createBitmap(R.drawable.ic_person2)))
            .setName("Ivan")
            .build()

        val style = NotificationCompat.MessagingStyle(sender)
        style.conversationTitle = "Just Code chat"
        style.addMessage("Hello", System.currentTimeMillis(), Ivan)
        style.addMessage("Hi", System.currentTimeMillis(), sender)
        style.addMessage("How are you", System.currentTimeMillis(), Ivan)
        style.addMessage("Nice, and you", System.currentTimeMillis(), sender)
        style.addMessage("fine too", System.currentTimeMillis(), Ivan)
        style.addMessage("let's meet today", System.currentTimeMillis(), sender)
        style.addMessage("at 6 pm", System.currentTimeMillis(), Ivan)

        return style
    }

    private var id: Int = 456

    fun showMessage(context: Context) {
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
      id++

    }

    private fun getChannelId(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "JustCodeNotification"
            val channelName = "JustCodeName"
            val channelDescription = "Channel for just code notification"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            channel.description = channelDescription

            val manager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            channelId
        } else ""
    }

    fun clear(context: Context) {
        val manager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancelAll()

    }

    private fun getDeleteAction(context: Context): PendingIntent {
        val deleteAction = Intent(context, MainActivity::class.java)
        deleteAction.putExtra("ACTION", "DELETE")
        return  PendingIntent.getActivity(context, 0, deleteAction, PendingIntent.FLAG_MUTABLE)

    }
    private fun getShowAction(context: Context): PendingIntent {
        val showAction = Intent(context, MainActivity::class.java)
        showAction.putExtra("ACTION", "SHOW")
        return  PendingIntent.getActivity(context, 0, showAction, PendingIntent.FLAG_MUTABLE)

    }

}

fun Context.createBitmap(@DrawableRes icon: Int): Bitmap {
    return BitmapFactory.decodeResource(this.resources, icon, BitmapFactory.Options())
}