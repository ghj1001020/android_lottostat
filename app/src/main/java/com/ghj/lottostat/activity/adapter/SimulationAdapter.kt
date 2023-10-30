package com.ghj.lottostat.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ghj.lottostat.activity.base.BaseRecyclerViewAdapter
import com.ghj.lottostat.activity.base.BaseRecyclerViewHolder
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.activity.data.SimulationNumberData
import com.ghj.lottostat.activity.data.WinRate
import com.ghj.lottostat.databinding.RowSimulationNumberBinding

class SimulationAdapter(mContext: Context, val mLottoList: ArrayList<SimulationNumberData>) : BaseRecyclerViewAdapter(mContext) {

    var ctnWin1 = 0
    var ctnWin2 = 0
    var ctnWin3 = 0
    var ctnWin4 = 0
    var ctnWin5 = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val binding = RowSimulationNumberBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return SimulationHolder(binding)
    }

    override fun getItemCount(): Int {
        return mLottoList.size
    }

    fun clearItems() {
        mLottoList.clear()
        ctnWin1 = 0
        ctnWin2 = 0
        ctnWin3 = 0
        ctnWin4 = 0
        ctnWin5 = 0
    }

    fun addItems(list: MutableList<ArrayList<Int>>, winning: LottoWinNumber ) {
        for( item in list ) {
            if( item.size < 6 ) continue

            val numList = arrayListOf<Int>(item[0], item[1], item[2], item[3], item[4], item[5])
            val result = winning.getWinningResult(numList)
            when( result ) {
                WinRate.WIN1PLACE -> {
                    ctnWin1 += 1
                }
                WinRate.WIN2PLACE -> {
                    ctnWin2 += 1
                }
                WinRate.WIN3PLACE -> {
                    ctnWin3 += 1
                }
                WinRate.WIN4PLACE -> {
                    ctnWin4 += 1
                }
                WinRate.WIN5PLACE -> {
                    ctnWin5 += 1
                }
                WinRate.NONE -> {}
            }
            mLottoList.add( SimulationNumberData(numList[0], numList[1], numList[2], numList[3], numList[4], numList[5], result) )
        }
    }


    inner class SimulationHolder(val mBinding: RowSimulationNumberBinding) : BaseRecyclerViewHolder(mBinding){

        override fun onBindViewHolder(position: Int) {
            val data = mLottoList.get(position)
            mBinding.num1.num = data.num1
            mBinding.num2.num = data.num2
            mBinding.num3.num = data.num3
            mBinding.num4.num = data.num4
            mBinding.num5.num = data.num5
            mBinding.num6.num = data.num6
            when( data.result ) {
                WinRate.WIN1PLACE -> {
                    mBinding.txtResult.text = "1등"
                }
                WinRate.WIN2PLACE -> {
                    mBinding.txtResult.text = "2등"
                }
                WinRate.WIN3PLACE -> {
                    mBinding.txtResult.text = "3등"
                }
                WinRate.WIN4PLACE -> {
                    mBinding.txtResult.text = "4등"
                }
                WinRate.WIN5PLACE -> {
                    mBinding.txtResult.text = "5등"
                }
                else -> {
                    mBinding.txtResult.text = ""
                }
            }

            mBinding.divider.visibility = if(position==mLottoList.size-1) View.GONE else View.VISIBLE
        }
    }
}