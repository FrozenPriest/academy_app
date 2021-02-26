package ru.frozenrpiest.academyapp

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {
    fun createNotificationChannel(context: Context) {
        val notificationChannel = NotificationChannelCompat
            .Builder(RECOMMEND_TOP, NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName(context.getString(R.string.channel_top_rated))
            .setDescription(context.getString(R.string.channel_top_rated_description))
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    const val RECOMMEND_TOP = "recommend_top"
    const val TOP_MOVIE_ID = 1

}