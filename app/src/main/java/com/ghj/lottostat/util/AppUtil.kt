package com.ghj.lottostat.util

import android.os.Process
import com.ghj.lottostat.BuildConfig
import com.ghj.lottostat.LTApp
import java.lang.Exception
import java.lang.NumberFormatException

object AppUtil {

    // 앱종료
    fun AppClose() {
        LTApp.mActivity?.finishAffinity()
        Process.killProcess(Process.myPid())
    }

    // 앱버전 체크 true-최신버전, false-낮은버전(업데이트 필요)
    fun CheckAppVersion( serverVer: String ) : Boolean {
        val arrServerVer = serverVer.split(".")
        val arrAppVer = BuildConfig.VERSION_NAME.split(".")

        try {
            for( i in arrServerVer.indices ) {
                if( i >= arrAppVer.size ) {
                    return false
                }

                val server = Integer.parseInt( arrServerVer[i] )
                val app = Integer.parseInt( arrAppVer[i] )
                // 앱 버전코드가 더높으면 최신버전
                if( app > server ) {
                    return true
                }
                // 서버 버전코드가 더높으면 낮은버전
                else if( app < server ) {
                    return false
                }
            }
        }
        catch ( e1: NumberFormatException) {
            return false
        }
        catch ( e2: Exception) {
            return false
        }
        return true
    }
}