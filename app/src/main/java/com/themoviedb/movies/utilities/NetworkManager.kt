package com.themoviedb.movies.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkManager {
    companion object {

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        return hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                        )
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        return type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE
                    }
                }
            }
            return false
        }
    }
}