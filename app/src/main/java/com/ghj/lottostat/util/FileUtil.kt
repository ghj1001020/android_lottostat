package com.ghj.lottostat.util

import android.content.Context
import android.content.res.AssetManager
import com.ghj.lottostat.LTApp
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception

object FileUtil {

    // asset 폴더에 File 존재하는지 확인
    fun CheckAssetFileExist(folderName : String, fileName : String ) : Boolean {
        if(LTApp.mContext == null) return false

        var fileList : Array<String>? = null
        try {
            fileList = LTApp.mContext!!.resources.assets.list(folderName)
        }
        catch ( e: IOException) {
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
    fun CopyFileFromAssets(assetPath: String, toFolderPath: String, toFileName: String ) : Boolean {
        if(LTApp.mContext == null) return false

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

            val assetManager : AssetManager = LTApp.mContext!!.assets
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
        catch ( e : Exception) {
            e.printStackTrace()
            return false
        }
    }
}