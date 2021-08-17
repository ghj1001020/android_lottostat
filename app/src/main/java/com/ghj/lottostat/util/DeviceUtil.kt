package com.ghj.lottostat.util

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

object DeviceUtil {

    // GPS On/Off 확인
    fun checkGpsOnOff( context : Context) : Boolean {
        val lm : LocationManager = context.getSystemService( Context.LOCATION_SERVICE ) as LocationManager
        return lm.isProviderEnabled( LocationManager.GPS_PROVIDER )
    }



}