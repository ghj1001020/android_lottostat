package com.ghj.lottostat

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception

class LTApp : MultiDexApplication() {

    companion object {
        // 현재 액티비티
        var currentActivity : Activity? = null
            set(value) {
                if( currentActivity != null && value.hashCode() == currentActivity.hashCode()) {
                    return
                }
                field = value
            }
            get() {
                return field
            }


        // 로또당첨번호
        var lottoWinNumber: ArrayList<LottoWinNumber> = arrayListOf()
    }

    override fun onCreate() {
        super.onCreate()
    }
}