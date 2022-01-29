package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.activity.data.LottoWinNumber

class WinLottoViewModel(application: Application) : BaseViewModel(application) {

    // 로또당첨번호 목록
    var mLottoWinList : ArrayList<LottoWinNumber> = arrayListOf()

    init {
        mLottoWinList.addAll(LTApp.LottoWinNumberList)
    }
}