package com.ghj.lottostat.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ghj.lottostat.activity.base.BaseRecyclerViewAdapter
import com.ghj.lottostat.activity.base.BaseRecyclerViewHolder
import com.ghj.lottostat.activity.data.ListType
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.databinding.RowWinLottoNumberBinding
import com.ghj.lottostat.ui.HJCheckImageView
import com.ghj.lottostat.util.DateUtil.dateFormatHyphen

class WinLottoAdapter(mContext: Context, val mWinList: ArrayList<LottoWinNumber>) : BaseRecyclerViewAdapter(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val binding = RowWinLottoNumberBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return WinLottoHolder(binding)
    }

    override fun getItemCount(): Int {
        return mWinList.size
    }


    inner class WinLottoHolder(val mBinding: RowWinLottoNumberBinding): BaseRecyclerViewHolder(mBinding) {

        override fun onBindViewHolder(position: Int) {
            val data = mWinList.get(position)
            mBinding.txtNo.text = "${data.no}회"
            mBinding.txtDate.text = "(${data.date.dateFormatHyphen()})"
            mBinding.num1.num = data.win1
            mBinding.num2.num = data.win2
            mBinding.num3.num = data.win3
            mBinding.num4.num = data.win4
            mBinding.num5.num = data.win5
            mBinding.num6.num = data.win6
            mBinding.numBonus.num = data.bonus
            mBinding.txt1PlaceCnt.text = data.place1Cnt
            mBinding.txt1PlaceAmt.text = data.place1Amt
            mBinding.txt2PlaceCnt.text = data.place2Cnt
            mBinding.txt2PlaceAmt.text = data.place2Amt
            mBinding.txt3PlaceCnt.text = data.place3Cnt
            mBinding.txt3PlaceAmt.text = data.place3Amt
            mBinding.txt4PlaceCnt.text = data.place4Cnt
            mBinding.txt4PlaceAmt.text = data.place4Amt
            mBinding.txt5PlaceCnt.text = data.place5Cnt
            mBinding.txt5PlaceAmt.text = data.place5Amt

            mBinding.chkFold.isChecked = data.type == ListType.OPEN
            mBinding.layoutInfo.visibility = if(data.type == ListType.OPEN) View.VISIBLE else View.GONE

            // 접기/펼치기
            mBinding.layoutRound.setOnClickListener {
                data.type = if(data.type == ListType.CLOSE) ListType.OPEN else ListType.CLOSE
                mBinding.chkFold.isChecked = data.type == ListType.OPEN
                mBinding.layoutInfo.visibility = if(data.type == ListType.OPEN) View.VISIBLE else View.GONE
            }
        }
    }
}