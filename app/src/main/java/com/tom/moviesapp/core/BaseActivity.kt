package com.tom.moviesapp.core

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tom.moviesapp.models.Error

abstract class BaseActivity : AppCompatActivity() {

    fun showSnackBarError(view: View, error: Error) {
        Snackbar.make(view, error.message, Snackbar.LENGTH_SHORT).show()
    }

}