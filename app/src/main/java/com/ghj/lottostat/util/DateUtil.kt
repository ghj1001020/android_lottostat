package com.ghj.lottostat.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    // Date() -> String 변환
    fun Date.toString(format: String="yyyyMMdd") : String {
        val sdf = SimpleDateFormat(format)
        return sdf.format(this)
    }

    // 날짜 String yyyy-MM-dd 포맷적용
    fun String.dateFormatHyphen() : String {
        if( this.length == 8 ) {
            return "${this.substring(0,4)}-${this.substring(4,6)}-${this.substring(6)}"
        }
        else if( this.length == 12 ) {
            return "${this.substring(0,4)}-${this.substring(4,6)}-${this.substring(6,8)} " +
                    "${this.substring(8,10)}:${this.substring(10)}"
        }
        else if( this.length == 14 ) {
            return "${this.substring(0,4)}-${this.substring(4,6)}-${this.substring(6,8)} " +
                    "${this.substring(8,10)}:${this.substring(10,12)}:${this.substring(12)}"
        }
        return ""
    }

    // date string을 from format -> to format 으로 변환
    fun String.convertDateFormat( fromFormat: String, toFormat: String ) : String {
        val from = SimpleDateFormat(fromFormat, Locale.getDefault())
        val to = SimpleDateFormat(toFormat, Locale.getDefault())

        try {
            return to.format(from.parse(this)!!)
        }
        catch ( e: Exception) {
            return ""
        }
    }

    // date string 변환
    fun String.convertSeparator( sep1: String="-", sep2: String=":") : String {
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

