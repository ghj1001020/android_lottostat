package com.ghj.lottostat.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    // Date() -> String 변환
    fun Date.convertToString(format: String="yyyyMMdd") : String {
        val format = SimpleDateFormat(format)
        return format.format(this)
    }

    // date string을 from format -> to format 으로 변환
    fun convertDateFormat( date: String, fromFormat: String, toFormat: String ) : String {
        val from = SimpleDateFormat(fromFormat, Locale.getDefault())
        val to = SimpleDateFormat(toFormat, Locale.getDefault())

        try {
            return to.format(from.parse(date)!!)
        }
        catch ( e: Exception) {
            return ""
        }
    }

    // date string 변환
    fun String.convertDate( sep1: String="-", sep2: String=":") : String {
        // yyyyMMdd
        if( this.length == 8 ) {
            return this.substring(0,4) + sep1 + this.substring(4,6) + sep1 + this.substring(6)
        }
        // yyyyMMddHHmmdd
        else if( this.length == 14 ) {
            return this.substring(0,4) + sep1 + this.substring(4,6) + sep1 + this.substring(6,8) + " " + this.substring(8,10) + sep2 + this.substring(10,12) + sep2 + this.substring(12)
        }
        return this
    }
}

