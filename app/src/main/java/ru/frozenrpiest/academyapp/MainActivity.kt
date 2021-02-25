package ru.frozenrpiest.academyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager
import ru.frozenrpiest.academyapp.fragments.MoviesListFragment
import ru.frozenrpiest.academyapp.workers.WorkRepository

class MainActivity : AppCompatActivity() {
    private val workRepository = WorkRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            WorkManager.getInstance().enqueue(workRepository.constrainedRequest)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MoviesListFragment.newInstance())
                .commit()
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