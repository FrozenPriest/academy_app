package ru.frozenrpiest.academyapp

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.NotificationUtil.TOP_MOVIE_ID
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.network.getMovieById
import ru.frozenrpiest.academyapp.data.network.loadTopRatedMovie
import ru.frozenrpiest.academyapp.fragments.MovieDetailsFragment
import ru.frozenrpiest.academyapp.fragments.MoviesListFragment
import ru.frozenrpiest.academyapp.workers.WorkRepository

class MainActivity : AppCompatActivity() {
    private val workRepository = WorkRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Intent.ACTION_VIEW == intent.action) {
            handleViewIntent(intent)
            return
        }

        if (savedInstanceState == null) {
            WorkManager.getInstance().enqueue(workRepository.constrainedRequest)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MoviesListFragment.newInstance())
                .commit()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (Intent.ACTION_VIEW == it.action) {
                handleViewIntent(it)
            }
        }
    }

    private fun handleViewIntent(intent: Intent) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                intent.data?.let { uri ->
                    uri.lastPathSegment?.let {
                        val movieIdPath = it
                        val movieId = movieIdPath.split("-")[0].toInt()
                        try {
                            val movie = getMovieById(movieId)
                            //test link                   am start -W -a android.intent.action.VIEW -d "https://www.themoviedb.org/movie/508442-soul"
                            runOnUiThread {
                                supportFragmentManager.beginTransaction()
                                    .replace(
                                        R.id.fragmentContainer,
                                        MovieDetailsFragment.newInstance(movie)
                                    )
                                    .commit()
                            }
                            val topRatedMovie = loadTopRatedMovie()
                            if (topRatedMovie.id != movie.id) {
                                showMovieNotification(topRatedMovie)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    applicationContext,
                                    R.string.error_loading,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showMovieNotification(movie: Movie) {
        Log.e("Notification", "Going to notify")

        NotificationUtil.createNotificationChannel(this)

        val image = Glide.with(this).asBitmap().load(movie.poster).submit()


        val contentView = RemoteViews(packageName, R.layout.notification_layout)
        contentView.setImageViewBitmap(R.id.imageViewPosterNotification, image.get())
        contentView.setTextViewText(R.id.textViewTitle, movie.title)
        contentView.setTextViewText(R.id.textViewOverview, movie.overview)

        val calendarIntent = Intent(this, NotificationBroadcastReciever::class.java)
        calendarIntent.putExtra("movie", movie)
        val pCalendarIntent = PendingIntent.getBroadcast(this, ADD_TO_CALENDAR, calendarIntent, 0)
        contentView.setOnClickPendingIntent(R.id.buttonAddToCalendar, pCalendarIntent)


        val notification = NotificationCompat.Builder(this, NotificationUtil.RECOMMEND_TOP)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomBigContentView(contentView)
            .setContentTitle(
                String.format(
                    resources.getString(R.string.top_movie),
                    movie.title
                )
            )
            .setContentText(movie.overview)
            .setSmallIcon(R.drawable.ic_baseline_movie_filter_24)
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()



        NotificationManagerCompat.from(this).notify("top_movie", TOP_MOVIE_ID, notification)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val ADD_TO_CALENDAR = 0
    }
}