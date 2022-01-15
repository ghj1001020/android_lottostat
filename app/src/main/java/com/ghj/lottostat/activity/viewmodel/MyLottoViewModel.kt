package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import com.ghj.lottostat.activity.data.MyLottoNumberData

class MyLottoViewModel(application: Application) : BaseViewModel(application) {

    // My로또 목록 데이터
    var mMyLottoList : ArrayList<MyLottoNumberData> = arrayListOf()
}