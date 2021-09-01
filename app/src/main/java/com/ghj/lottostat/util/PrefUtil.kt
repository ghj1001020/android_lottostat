package com.ghj.lottostat.util

import android.content.Context
import android.content.SharedPreferences

class PrefUtil private constructor(context: Context) {

    private val PREF_FILE_NAME = "BROWSER_PREF"
    private val sp : SharedPreferences

    init {
        sp = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private var prefUtil: PrefUtil? = null
        fun getInstance(context: Context) : PrefUtil {
            synchronized(PrefUtil::class) {
                if( prefUtil == null ) {
                    prefUtil = PrefUtil(context)
                }
            }
            return prefUtil as PrefUtil
        }
    }


    // 데이터 저장
    fun put(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    fun put(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    // 데이터 조회
    fun getString(key: String, defaultValue: String="") : String {
        return sp.getString(key, defaultValue) ?: defaultValue
    }

    fun getBoolean(key: String, defaultValue: Boolean=false) : Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0) : Int {
        return sp.getInt(key, defaultValue)
    }
}