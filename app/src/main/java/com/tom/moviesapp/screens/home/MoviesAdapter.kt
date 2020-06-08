package com.tom.moviesapp.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.moviesapp.R
import com.tom.moviesapp.models.Movie
import com.tom.moviesapp.utils.getDate
import com.tom.moviesapp.utils.loadImage

class MoviesAdapter(private val onClickListener: (movieId: Int, view: View) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    var list: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movies_item_view,
                parent,
                false
            )
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = list[position]
        holder.image.loadImage(movie.poster_path ?: "", Pair(200, 150))
        holder.title.text = movie.title
        holder.year.text = movie.release_date?.getDate("yyyy")
        holder.itemView.setOnClickListener{
            onClickListener(movie.id ?: 0, holder.itemView)
        }
    }

}