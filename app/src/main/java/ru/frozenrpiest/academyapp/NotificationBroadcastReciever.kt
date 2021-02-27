package ru.frozenrpiest.academyapp

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.CalendarContract
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import ru.frozenrpiest.academyapp.data.Movie


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
                addToCalendar(context, movie)
            }
        }

        val notificationId = intent.getIntExtra("notificationId", 0)
        NotificationManagerCompat.from(context).cancel(notificationId)

    }

    private fun addToCalendar(context: Context, movie: Movie) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        intent.putExtra(
            CalendarContract.Events.TITLE,
            context.resources.getString(R.string.watch_later_title, movie.title)
        )
        intent.putExtra(CalendarContract.Events.DESCRIPTION, movie.overview)

        context.startActivity(intent)
    }
}