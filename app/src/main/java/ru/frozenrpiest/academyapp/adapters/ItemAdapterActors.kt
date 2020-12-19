package ru.frozenrpiest.academyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.frozenrpiest.academyapp.R
import ru.frozenrpiest.academyapp.data.Actor

class ItemAdapterActors (val context: Context, val items: List<Actor>) :
                                RecyclerView.Adapter<ItemAdapterActors.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(
                R.layout.view_holder_actor,
                parent,
                false
        )
        return ViewHolder(view).apply {
            textViewName = view.findViewById(R.id.textViewName)
            imageViewPhoto = view.findViewById(R.id.imageViewPhoto)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textViewName.text = item.name
        Glide.with(context).load(item.picture).centerCrop().into(holder.imageViewPhoto)//todo добавить error
    }
    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        lateinit var textViewName:TextView
        lateinit var imageViewPhoto:ImageView


    }

}