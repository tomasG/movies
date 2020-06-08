package com.tom.moviesapp.utils

import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startPostponedEnterTransition
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.tom.moviesapp.R


fun ImageView.loadImage(url: String, dimens: Pair<Int, Int>, quality: String = "185") {
    if (url.isNotEmpty()) {
        val urlToSend = "${Constants.IMAGE_BASE_URL}w${quality}${url}?api_key=${Constants.API_KEY}"
        val imageView = this
        Picasso.get().load(urlToSend)
            //.resize(dimens.first, dimens.second)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .fit()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this, object : Callback {
                override fun onSuccess() {
                    Log.v("PICASSO", "Success")
                    scheduleStartPostponedTransition(imageView)
                }

                override fun onError(e: Exception?) {
                    Picasso.get().load(urlToSend)
                        .error(R.drawable.placeholder)
                        .into(imageView, object : Callback {
                            override fun onSuccess() {
                                scheduleStartPostponedTransition(imageView)
                            }

                            override fun onError(e: Exception?) {
                                Log.v("PICASSO", e?.localizedMessage ?: "")
                            }
                        })
                }
            })
    }
}

private fun scheduleStartPostponedTransition(sharedElement: View) {

    sharedElement.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener,
        ViewTreeObserver.OnDrawListener {
        override fun onPreDraw(): Boolean {
            sharedElement.viewTreeObserver.removeOnDrawListener(this)
            sharedElement.getActivity()?.let{
                startPostponedEnterTransition(it)
            }
            return true
        }

        override fun onDraw() {

        }

    })
}


