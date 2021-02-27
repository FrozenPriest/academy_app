package ru.frozenrpiest.academyapp.utils

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.data.Genre
import ru.frozenrpiest.academyapp.data.Movie
import kotlin.math.roundToInt

object DataUtils {


    fun formatGenres(genres: List<Genre>): CharSequence {
        val result = StringBuilder()
        for (i in genres.indices) {
            result.append(genres[i].name)
            if (i < genres.size - 1) result.append(", ")
        }
        return result.toString()
    }

    fun roundRating(rating: Float): Float {
//        val correction = if(rating >= 0) 0.5 else -0.5
        return ((rating / 0.5).roundToInt() * 0.5).toFloat()
    }

    fun sendToCalendar(context: Context, movie: Movie) {
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