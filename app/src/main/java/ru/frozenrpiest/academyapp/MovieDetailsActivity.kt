package ru.frozenrpiest.academyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.adapters.ItemAdapterCast
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager
import ru.frozenrpiest.academyapp.dataclasses.Person

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setupCast()
    }

    private fun setupCast() {
        val recyclerViewCast = findViewById<RecyclerView>(R.id.recyclerViewCast)
        recyclerViewCast.layoutManager = LinearLayoutPagerManager(this, LinearLayoutManager.HORIZONTAL, false, 4)
        recyclerViewCast.adapter = ItemAdapterCast(this, retrieveCast())
      //  recyclerViewCast.
    }

    private fun retrieveCast(): List<Person> {
        val per = Person("Robert Downey Jr.", AppCompatResources.getDrawable(baseContext, R.drawable.movie_1_)!!)
        return listOf(per, per.copy(name = "Chris Evans"), per.copy(name = "Mark Ruffalo"), per.copy(name = "Chris Hemsworth"), per.copy(name = "Chris Evans11"), per.copy(name = "Mark Ruffalo111"), per.copy(name = "Chris Hemsworth26262626"),)
    }
}