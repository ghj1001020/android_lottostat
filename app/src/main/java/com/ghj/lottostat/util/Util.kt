package com.ghj.lottostat.util

import android.content.Context
import android.content.res.AssetManager
import android.icu.number.IntegerWidth
import android.util.DisplayMetrics
import com.ghj.lottostat.BuildConfig
import com.ghj.lottostat.R
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.lang.NumberFormatException

object Util {

    fun getLottoNumberBgResource(num: Int) : Int {
        when {
            num <= 10 -> {
                return R.drawable.lotto_bg1
            }
            num <= 20 -> {
                return R.drawable.lotto_bg2
            }
            num <= 30 -> {
                return R.drawable.lotto_bg3
            }
            num <= 40 -> {
                return R.drawable.lotto_bg4
            }
            num <= 45 -> {
                return R.drawable.lotto_bg5
            }
            else -> {
                return R.drawable.lotto_bg1
            }
        }
    }

    // 디바이스 가로너비
    fun getDisplayWidth(context: Context) : Int {
        val metrics : DisplayMetrics = context.resources.displayMetrics
        return metrics.widthPixels
    }

    // 디바이스 세로너비
    fun getDisplayHeight(context: Context) : Int {
        val metrics : DisplayMetrics = context.resources.displayMetrics
        return metrics.heightPixels
    }

}