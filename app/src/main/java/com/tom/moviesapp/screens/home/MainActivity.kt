package com.tom.moviesapp.screens.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.moviesapp.R
import com.tom.moviesapp.core.BaseActivity
import com.tom.moviesapp.models.MovieListType
import com.tom.moviesapp.screens.detail.DetailActivity
import com.tom.moviesapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_item_view.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private val movieViewModel by viewModel<MovieListViewModel>()

    private val moviesAdapter = MoviesAdapter(::onMovieClicked)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUi()
    }

    private fun setUi() {
        createSpinnerAdapter()
        moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = moviesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        moviesInputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movieViewModel.searchMovies(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        movieViewModel.getMovieList().observe(this, Observer { list ->
            moviesAdapter.list = list
        })
        movieViewModel.getError().observe(this, Observer { error ->
            showSnackBarError(mainConstraintLayout, error)
        })
    }

    /**
     * This function is triggered whe a user clicks on a Movie
     * from the list of it
     *
     * @param movie
     */
    private fun onMovieClicked(movieId: Int, view: View) {
        val imagePair = Pair(view.moviesItemViewImage as View, getString(R.string.transition_image))
        val titlePair =
            Pair(view.moviesItemViewTitleTxt as View, getString(R.string.transition_title))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, imagePair, titlePair
        )
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID, movieId)
        }
        startActivity(intent, options.toBundle())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Nothing to do
    }

    /**
     * This function is triggered when a user selects a different type
     * of movie
     * @param position is evaluated as [MovieListType]
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val option = when (position) {
            0 -> MovieListType.POPULAR
            1 -> MovieListType.TOP_RATED
            else -> MovieListType.NOTHING
        }
        if (option != MovieListType.NOTHING) {
            movieViewModel.loadMoviesList(option)
        }
    }

    private fun createSpinnerAdapter(): ArrayAdapter<CharSequence> {
        moviesTypeSpinner.onItemSelectedListener = this
        return ArrayAdapter.createFromResource(
            this,
            R.array.movies_type,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            moviesTypeSpinner.adapter = it
        }
    }


}