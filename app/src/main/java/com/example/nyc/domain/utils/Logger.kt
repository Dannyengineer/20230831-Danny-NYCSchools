package com.example.nyc.domain.utils

import android.util.Log

object Logger {
    private const val isDebugLoggingOn = true

    fun e(tag: String, msg: String) {
        if (isDebugLoggingOn) {
            largeLog(tag, msg)
        }
    }

    private fun largeLog(tag: String, content: String) {
        if (content.length > 4000) {
            Log.e(tag, content.substring(0, 4000))
            largeLog(tag, content.substring(4000))
        } else {
            Log.e(tag, content)
        }
    }
}