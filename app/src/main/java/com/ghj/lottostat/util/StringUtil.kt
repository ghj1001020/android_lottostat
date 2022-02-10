package com.ghj.lottostat.util

import android.text.TextUtils
import com.ghj.lottostat.util.StringUtil.stringToInt
import com.ghj.lottostat.util.StringUtil.stringToLong
import java.net.URL

object StringUtil {
    // Url에서 도메인만 반환
    fun getUrlDoamin( _url : String? ) : String {
        if( TextUtils.isEmpty( _url ) ) {
            return ""
        }

        try {
            val url = URL( _url )
            return url.host
        }
        catch ( e : Exception ) {
            return ""
        }
    }

    // Url에서 파라미터 해시태그 제외한 Url만 반환
    fun removeParameterFromUrl( _url : String? ) : String {
        if( TextUtils.isEmpty( _url ) ) {
            return ""
        }

        var url = _url!!
        var index = _url.lastIndexOf("#")
        if( index > -1 ) {
            url = url.substring(0, index)
        }

        index = url.lastIndexOf("?")
        if( index > -1 ) {
            url = url.substring(0, index)
        }
        return url
    }

    // Url에서 확장자 반환
    fun getUrlExtension( _url : String? ) : String {
        if( TextUtils.isEmpty( _url ) ) {
            return ""
        }

        val url = _url!!.removeSuffix("/")
        var ext = ""
        val index = url.lastIndexOf(".")
        if( index > -1 && url.length > 1) {
            ext = url.substring(index+1)
        }
        return ext
    }

    // Url에서 파일명 구하기
    fun getFilenameFromUrl( _url: String? ) : String {
        if( TextUtils.isEmpty( _url ) ) {
            return ""
        }

        val splitUrl = _url!!.split( "/")
        var filename : String = splitUrl[splitUrl.size - 1]

        return filename
    }


    // null to string
    fun nullToString( text: String?, defaultValue : String? = "" ) : String
    {
        if( text == null )
        {
            if( !TextUtils.isEmpty( defaultValue ) )
            {

                return defaultValue as String
            }
            else
            {
                return ""
            }
        }

        return text
    }

    // file name extension
    fun getFileExtension(text: String?) : String {
        if( TextUtils.isEmpty( text ) ) {
            return ""
        }

        return text?.split(".")?.last() as String
    }

    // string -> int
    fun String?.stringToInt() : Int {
        try{
            return stringToNumber(this, false).toInt()
        }
        catch ( e: Exception ) {
            return 0
        }
    }

    // string -> long
    fun String?.stringToLong() : Long {
        try{
            return stringToNumber(this, false).toLong()
        }
        catch ( e: Exception ) {
            return 0
        }
    }

    // string -> double
    fun String?.stringToDouble() : Double {
        try{
            return stringToNumber(this, false).toDouble()
        }
        catch ( e: Exception ) {
            return 0.0
        }
    }

    // string -> 숫자형태 string
    private fun stringToNumber(str: String?, isReal: Boolean) : String {
        if( TextUtils.isEmpty(str) ) {
            return "0"
        }

        var sign = ""
        // 음수
        if( str!!.startsWith("-") ) {
            sign = "-"
        }

        // 정수
        val arrPoint = str.split(".")
        val _integer = arrPoint[0].replace("[^0-9]".toRegex(), "")
        // 소수
        var _decimal = ""
        if( arrPoint.size > 1 ) {
            _decimal = arrPoint[1].replace("[^0-9]".toRegex(), "")
        }

        var _this = sign + _integer
        if( isReal && !TextUtils.isEmpty(_decimal) ) {
            _this += ".$_decimal"
        }

        return _this
    }
}