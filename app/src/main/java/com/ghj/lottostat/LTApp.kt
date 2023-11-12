package com.ghj.lottostat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.common.FILTER
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.PrefUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception

class LTApp : MultiDexApplication() {

    companion object {
        // 현재 액티비티
        var mActivity : Activity? = null
            set(value) {
                if( mActivity != null && value.hashCode() == mActivity.hashCode()) {
                    return
                }
                field = value
            }
            get() {
                return field
            }
        var mContext : Context? = null

        // 로또당첨번호
        var LottoWinNumberList: ArrayList<LottoWinNumber> = arrayListOf()

        // 로또당첨번호 가져오기
        fun setLottoWinNumberList() {
            LottoWinNumberList.clear()
            LottoWinNumberList.addAll( SQLiteService.selectLottoWinNumber() )
        }
    }

    override fun onCreate() {
        super.onCreate()

        PrefUtil.getInstance(this).put(FILTER.LAST_ROUND_WIN_NUMBER, true)
        PrefUtil.getInstance(this).put(FILTER.CONSECUTIVE_NUMBER, true)
    }

}