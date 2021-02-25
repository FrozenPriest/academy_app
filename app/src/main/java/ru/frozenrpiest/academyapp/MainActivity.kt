package ru.frozenrpiest.academyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.frozenrpiest.academyapp.data.network.getMovieById
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}