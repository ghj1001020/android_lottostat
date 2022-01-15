package com.ghj.lottostat.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ghj.lottostat.activity.base.BaseRecyclerViewAdapter
import com.ghj.lottostat.activity.base.BaseRecyclerViewHolder
import com.ghj.lottostat.activity.data.MyLottoNumberData
import com.ghj.lottostat.activity.data.MyLottoType
import com.ghj.lottostat.databinding.RowLottoNumBinding

class MyLottoAdapter(mContext: Context, val mMyLottoList : ArrayList<MyLottoNumberData>) : BaseRecyclerViewAdapter(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        if( viewType == MyLottoType.ROUND_OPEN.ordinal || viewType == MyLottoType.ROUND_CLOSE.ordinal ) {

        }
        else if( viewType == MyLottoType.REG_DATE.ordinal ) {

        }
        else {

        }
        val binding = RowLottoNumBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return LottoNumHolder(binding)
    }

    override fun getItemCount(): Int {
        return mMyLottoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return mMyLottoList[position].type.ordinal
    }

    // 회차
    inner class MyLottoRoundHolder(val mBinding: )

    // 날짜

    // 번호

}