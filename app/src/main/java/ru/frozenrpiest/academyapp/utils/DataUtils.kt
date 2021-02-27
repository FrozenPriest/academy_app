package ru.frozenrpiest.academyapp.utils

import ru.frozenrpiest.academyapp.data.Genre
import kotlin.math.roundToInt

object DataUtils {


    fun formatGenres(genres: List<Genre>): CharSequence {
        val result = StringBuilder()
        for (i in genres.indices) {
            result.append(genres[i].name)
            if (i < genres.size - 1) result.append(",")
        }
        return result.toString()
    }

    fun roundRating(rating: Float): Float {
//        val correction = if(rating >= 0) 0.5 else -0.5
        return ((rating / 0.5).roundToInt() * 0.5).toFloat()
    }
}