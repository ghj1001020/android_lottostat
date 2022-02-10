package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import com.ghj.lottostat.activity.data.SimulationNumberData

class SimulationViewModel(application: Application) : BaseViewModel(application) {

    // 시뮬레이션 번호 목록
    val mSimulationList : ArrayList<SimulationNumberData> = arrayListOf()
}