package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import com.ghj.lottostat.activity.data.LottoNumberData

class RecommendViewModel(application: Application) : BaseViewModel(application) {

    // 로또번호 목록 데이터
    var mLottoNumberList : ArrayList<LottoNumberData> = arrayListOf()
}