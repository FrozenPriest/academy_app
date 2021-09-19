package ru.frozenrpiest.academyapp.utils

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import ru.frozenrpiest.academyapp.R

object NotificationUtils {
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