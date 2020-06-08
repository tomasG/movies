package com.tom.moviesapp.screens.detail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.tom.moviesapp.R
import com.tom.moviesapp.core.BaseActivity
import com.tom.moviesapp.utils.Constants
import com.tom.moviesapp.utils.loadImage
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {

    private val movieViewModel by viewModel<MovieDetailViewModel>()
    lateinit var youtubePlayer: YouTubePlayerSupportFragmentX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        postponeEnterTransition();
        youtubePlayer = youtubePlayerFragment as YouTubePlayerSupportFragmentX
        setUi()
        intent?.extras?.getInt(Constants.MOVIE_ID).also {
            movieViewModel.getMovieDetails(it)
        } ?: finish()
    }

    private fun setUi() {
        movieViewModel.getMovie().observe(this, Observer {
            movieImageImg.loadImage(it.poster_path ?: "", Pair(320, 640), "342")
            movieTitleTxt.text = it.title
            movieDescriptionTxt.text = it.overview
            movieGenresTxt.text = it.genresText
            if(!it.videos?.results.isNullOrEmpty()) {
                initializeVideo(it.videos?.results?.firstOrNull()?.key)
            }
        })
        movieViewModel.getError().observe(this, Observer {
            showSnackBarError(mainContainerDetailLayout, it)
        })

    }

    private fun initializeVideo(videoKey: String?) {
        videoKey?.let{
            youtubePlayer.initialize(
                Constants.YOUTUBE_API_KEY,
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        provider: YouTubePlayer.Provider?,
                        player: YouTubePlayer?,
                        wasResotored: Boolean
                    ) {
                        if (!wasResotored) {
                            player?.cueVideo(videoKey)
                        }
                    }

                    override fun onInitializationFailure(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubeInitializationResult?
                    ) {
                        Log.v("YOUTUBEPLAYER->", p1.toString())
                    }
                })
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }
}