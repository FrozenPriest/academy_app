package ru.frozenrpiest.academyapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import ru.frozenrpiest.academyapp.data.Movie

class NotificationBroadcastReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.hasExtra("movie") == true) {
            val movie = intent.getParcelableExtra<Movie>("movie")
            movie?.let {

                Toast.makeText(context, "Movie got: ${movie.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}