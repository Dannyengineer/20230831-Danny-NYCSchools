package com.example.nyc.presentation.customViews

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.nyc.R

class ProgressDialogue(context: Context) : AlertDialog(context) {

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun show() {
        super.show()
        setContentView(R.layout.dialog_progress)
    }

    fun showLoader(isLoaderShowing: Boolean) {
        if (isLoaderShowing) {
            show()
        } else {
            dismiss()
        }
    }
}