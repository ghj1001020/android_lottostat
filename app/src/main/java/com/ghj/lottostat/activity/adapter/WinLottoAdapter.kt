package com.ghj.lottostat.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ghj.lottostat.activity.base.BaseRecyclerViewAdapter
import com.ghj.lottostat.activity.base.BaseRecyclerViewHolder
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.databinding.RowWinLottoNumberBinding

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

        }
    }
}