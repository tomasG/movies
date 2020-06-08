package com.tom.moviesapp.screens.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movies_item_view.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.moviesItemViewImage
    val title: TextView = view.moviesItemViewTitleTxt
    val year: TextView = view.moviesItemViewYearText
}