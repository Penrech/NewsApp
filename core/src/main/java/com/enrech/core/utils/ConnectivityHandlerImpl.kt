package com.enrech.core.utils

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class ConnectivityHandlerImpl @Inject constructor(private val connectivityManager: ConnectivityManager) :
    ConnectivityHandler {

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission")
    override fun isNetworkAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.let { activeNetwork ->
                    when {
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                        else -> false
                    }
                } ?: false
        } else {
            return connectivityManager.activeNetworkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI,
                    ConnectivityManager.TYPE_MOBILE,
                    ConnectivityManager.TYPE_ETHERNET,
                    ConnectivityManager.TYPE_VPN -> true
                    else -> false
                }
            } ?: false
        }
    }
}