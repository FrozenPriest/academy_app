package ru.frozenrpiest.academyapp

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.data.Movie
import ru.frozenrpiest.academyapp.data.network.getMovieById
import ru.frozenrpiest.academyapp.data.network.loadTopRatedMovie
import ru.frozenrpiest.academyapp.fragments.MovieDetailsFragment
import ru.frozenrpiest.academyapp.fragments.MoviesListFragment
import ru.frozenrpiest.academyapp.utils.NotificationUtils
import ru.frozenrpiest.academyapp.utils.NotificationUtils.TOP_MOVIE_ID
import ru.frozenrpiest.academyapp.workers.WorkRepository

class MainActivity : AppCompatActivity() {
    private val workRepository = WorkRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askPermissions()

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

    //test link                   am start -W -a android.intent.action.VIEW -d "https://www.themoviedb.org/movie/508442-soul"
    private fun handleViewIntent(intent: Intent) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val movie =
                        if (intent.hasExtra("movie")) {
                            intent.getParcelableExtra("movie")
                        } else {
                            intent.data?.lastPathSegment?.let {
                                val movieIdPath = it
                                val movieId = movieIdPath.split("-")[0].toInt()
                                getMovieById(movieId)
                            }
                        }

                    movie?.let {
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

    private fun showMovieNotification(movie: Movie) {
        Log.e("Notification", "Going to notify")

        NotificationUtils.createNotificationChannel(this)

        val image = Glide.with(this).asBitmap().load(movie.poster).submit()

        val contentView = RemoteViews(packageName, R.layout.notification_layout)
        contentView.setImageViewBitmap(R.id.imageViewPosterNotification, image.get())
        contentView.setTextViewText(R.id.textViewTitle, movie.title)
        contentView.setTextViewText(R.id.textViewOverview, movie.overview)

        //calendarIntent
        val calendarIntent = Intent(this, NotificationBroadcastReciever::class.java)
        calendarIntent.putExtra("movie", movie)
        calendarIntent.putExtra("notificationId", TOP_MOVIE_ID)
        val pCalendarIntent = PendingIntent.getBroadcast(this, ADD_TO_CALENDAR, calendarIntent, 0)
        contentView.setOnClickPendingIntent(R.id.buttonAddToCalendar, pCalendarIntent)

        //showMovieInfoIntent
        val showMovieIntent = Intent(this, MainActivity::class.java)
        showMovieIntent.putExtra("movie", movie)
        showMovieIntent.action = Intent.ACTION_VIEW
        val pShowMovieIntent = PendingIntent.getActivity(this, SHOW_MOVIE_INFO, showMovieIntent, 0)

        val notification = NotificationCompat.Builder(this, NotificationUtils.RECOMMEND_TOP)
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
            .setContentIntent(pShowMovieIntent)
            .setAutoCancel(true)
            .build()



        NotificationManagerCompat.from(this).notify("top_movie", TOP_MOVIE_ID, notification)
    }

    private fun askPermissions() {
        val permissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR),
                REQUEST_CODE_PERMISSION_CALENDAR
            )
        }
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
        const val SHOW_MOVIE_INFO = 1


        const val REQUEST_CODE_PERMISSION_CALENDAR = 2
    }
}
