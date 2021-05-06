package ru.frozenrpiest.academyapp

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.utils.DataUtils


class NotificationBroadcastReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        context.sendBroadcast(it)

        if (!intent.hasExtra("movie")) return
        val movie = intent.getParcelableExtra<Movie>("movie")
        movie?.let {
            val permissionStatus =
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)

            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                DataUtils.sendToCalendar(context, movie)
            }
        }

        val notificationId = intent.getIntExtra("notificationId", 0)
        NotificationManagerCompat.from(context).cancel(notificationId)

    }

}