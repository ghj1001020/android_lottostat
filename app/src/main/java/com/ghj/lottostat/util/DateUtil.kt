package com.ghj.lottostat.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    // Date() -> String 변환
    fun Date.convertToString(format: String="yyyyMMdd") : String {
        val format = SimpleDateFormat(format)
        return format.format(this)
    }
}

