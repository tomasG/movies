package com.tom.moviesapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

class Alert(
    private val context: Context,
    private val title: String,
    private val message: String,
    private val positiveButton: String,
    private val positiveButtonClickListener: (dialog: DialogInterface, which: Int) -> Unit
) {

    private var dialog:Dialog = AlertDialog.Builder(context).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButton, positiveButtonClickListener)
        setCancelable(true)
    }.create()

    fun show() {
        dialog.show()
    }


}