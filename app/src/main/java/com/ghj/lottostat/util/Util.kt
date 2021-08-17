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


    // 앱버전 체크 true-최신버전, false-낮은버전(업데이트 필요)
    fun checkAppVersion( serverVer: String ) : Boolean {
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
        catch ( e1: NumberFormatException ) {
            return false
        }
        catch ( e2: Exception ) {
            return false
        }
        return true
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

    // asset 폴더에 File 존재하는지 확인
    fun checkAssetFileExist( context: Context , folderName : String , fileName : String ) : Boolean {
        var fileList : Array<String>? = null
        try {
            fileList = context.resources.assets.list(folderName)
        }
        catch ( e: IOException ) {
            e.printStackTrace()
        }

        if( fileList == null ) {
            return false
        }

        var isExist = false
        for (element in fileList) {
            if( fileName.equals(element) ) {
                isExist = true
                break
            }
        }

        return isExist
    }

    // asset 폴더파일을 내부저장소 database에 복사
    fun copyFileFromAssets( context: Context , assetPath: String , toFolderPath: String , toFileName: String ) : Boolean {
        try {
            val toFolder : File = File( toFolderPath )
            val toFile : File = File( toFolderPath , toFileName )

            // 폴더생성
            if( !toFolder.exists() ) {
                toFolder.mkdir()
            }

            // 기존 DB 파일 삭제
            if( toFile.exists() ) {
                toFile.delete()
            }

            val assetManager : AssetManager = context.assets
            val assetInputStream : InputStream = assetManager.open( assetPath )
            val fos : OutputStream = toFile.outputStream()

            val buffer : ByteArray = ByteArray(1024 * 100)  // 100KB 버퍼
            var read : Int = assetInputStream.read( buffer )
            while ( read != -1 ) {
                fos.write( buffer , 0 , read )
                read = assetInputStream.read( buffer )
            }
            fos.flush()

            assetInputStream.close()
            fos.close()

            return true
        }
        catch ( e : Exception ) {
            e.printStackTrace()
            return false
        }
    }
}