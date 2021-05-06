package ru.frozenrpiest.academyapp.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.frozenrpiest.academyapp.data.Movie


class MoviesListDiffUtilCallback(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return oldProduct.id == newProduct.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return (oldProduct.id == newProduct.id) && (oldProduct.title == newProduct.title)
    }
}