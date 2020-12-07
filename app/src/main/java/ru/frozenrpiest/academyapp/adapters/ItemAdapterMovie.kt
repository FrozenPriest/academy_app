package ru.frozenrpiest.academyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.dataclasses.Movie
import ru.frozenrpiest.academyapp.dataclasses.Person

class ItemAdapterMovie (val context: Context, val items: List<Movie>) :
    RecyclerView.Adapter<ItemAdapterMovie.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(
            R.layout.view_holder_movie,
            parent,
            false
        )
        return ViewHolder(view).apply {
            textViewName = view.findViewById(R.id.textViewMovieName)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textViewName.text = item.name
    }
    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        lateinit var textViewName:TextView
    }

}