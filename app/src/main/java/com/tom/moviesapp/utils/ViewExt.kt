package com.tom.moviesapp.utils

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.widget.ProgressBar


fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.getActivity(): Activity?{
    var mContext = context
    while (mContext is ContextWrapper) {
        if (mContext is Activity) {
            return mContext
        }
        mContext = mContext.baseContext
    }
    return null
}