package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import com.ghj.lottostat.activity.data.MyLottoNumberData
import com.ghj.lottostat.activity.data.MyLottoType

class MyLottoViewModel(application: Application) : BaseViewModel(application) {

    // My로또 목록 데이터
    var mOrgMyLottoList : ArrayList<MyLottoNumberData> = arrayListOf()
    var mMyLottoList : ArrayList<MyLottoNumberData> = arrayListOf()

    // 화면에 노출할 데이터 계산
    fun calculateList() {
        mMyLottoList.clear()
        var isOpen = false
        for( item in mOrgMyLottoList ) {
            if( item.type == MyLottoType.ROUND_CLOSE ) {
                isOpen = false
                mMyLottoList.add(item)
            }
            else if( item.type == MyLottoType.ROUND_OPEN ) {
                isOpen = true
                mMyLottoList.add(item)
            }
            else if( isOpen ){
                mMyLottoList.add(item)
            }
        }
    }

    // 하위데이터 존재여부
    fun isDataExist(position: Int) : Boolean {
        if( position+1 < mOrgMyLottoList.size &&
            (mOrgMyLottoList[position+1].type != MyLottoType.ROUND_OPEN || mOrgMyLottoList[position+1].type != MyLottoType.ROUND_CLOSE) ) {
            return true
        }
        return false
    }
}