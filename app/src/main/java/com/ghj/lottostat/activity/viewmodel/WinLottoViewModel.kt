package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.LogUtil

class WinLottoViewModel(application: Application) : BaseViewModel(application) {

    // 로또당첨번호 목록
    var mLottoWinList : ArrayList<LottoWinNumber> = arrayListOf()

    init {

    }

    fun setWinNumber() {
        if(mLottoWinList.size > 0) return

        if(LTApp.LottoWinNumberList.size == 0) {
            LTApp.setLottoWinNumberList()
        }
        LTApp.LottoWinNumberList.map {
            mLottoWinList.add(it.copy())
        }
    }
}