package com.example.nyc.domain.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.example.nyc.R


@SuppressLint("StaticFieldLeak")
object Utils {
    private val TAG = "Utils"
    private var context: Context? = null

    fun applicationReference(mContext: Context) {
        context = mContext
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var value = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork
            if (nw != null) {
                val actNw = connectivityManager.getNetworkCapabilities(nw)
                if (actNw != null) {
                    value = when {
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        //for other device how are able to connect with Ethernet
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                        //for check internet over Bluetooth
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                        else -> false
                    }
                }
            }
        } else {
            @Suppress("DEPRECATION")
            value = connectivityManager.activeNetworkInfo?.isConnected ?: false
        }

        if (!value) {
            Toast.makeText(
                context,
                context!!.getString(R.string.string_no_internet),
                Toast.LENGTH_LONG
            ).show()
        }
        return value
    }
}