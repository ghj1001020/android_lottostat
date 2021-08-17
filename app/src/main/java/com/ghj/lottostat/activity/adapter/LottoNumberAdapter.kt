package com.ghj.lottostat.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.base.BaseRecyclerViewAdapter
import com.ghj.lottostat.activity.base.BaseRecyclerViewHolder
import com.ghj.lottostat.activity.data.LottoNumberData
import com.ghj.lottostat.activity.viewmodel.RecommendViewModel
import com.ghj.lottostat.databinding.RowLottoNumBinding
import com.ghj.lottostat.util.Util

class LottoNumberAdapter(mContext: Context, mViewModel: RecommendViewModel) : BaseRecyclerViewAdapter<RecommendViewModel>(mContext, mViewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val binding = RowLottoNumBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return LottoNumHolder(binding)
    }

    override fun getItemCount(): Int {
        return mViewModel.mLottoNumberList.size
    }


    inner class LottoNumHolder(val mBinding: RowLottoNumBinding) : BaseRecyclerViewHolder(mBinding) {
        override fun onBindViewHolder(position: Int) {
            val data : LottoNumberData = getItem(position)
            mBinding.num1.text = "${data.num1}"
            mBinding.num1.setBackgroundResource( Util.getLottoNumberBgResource(data.num1) )
            mBinding.num2.text = "${data.num2}"
            mBinding.num2.setBackgroundResource( Util.getLottoNumberBgResource(data.num2) )
            mBinding.num3.text = "${data.num3}"
            mBinding.num3.setBackgroundResource( Util.getLottoNumberBgResource(data.num3) )
            mBinding.num4.text = "${data.num4}"
            mBinding.num4.setBackgroundResource( Util.getLottoNumberBgResource(data.num4) )
            mBinding.num5.text = "${data.num5}"
            mBinding.num5.setBackgroundResource( Util.getLottoNumberBgResource(data.num5) )
            mBinding.num6.text = "${data.num6}"
            mBinding.num6.setBackgroundResource( Util.getLottoNumberBgResource(data.num6) )

            if( position == itemCount-1 ) {
                mBinding.divider.visibility = View.GONE
            }
            else {
                mBinding.divider.visibility = View.VISIBLE
            }
        }
    }


    fun addItems(list: MutableList<LottoNumberData> ) {
        mViewModel.mLottoNumberList.addAll( list )
    }

    fun addItem(list: MutableList<Int>) {
        if( list.size < 6 ) return
        mViewModel.mLottoNumberList.add( LottoNumberData(list[0], list[1], list[2], list[3], list[4], list[5]) )
    }

    fun clearItems() {
        mViewModel.mLottoNumberList.clear()
    }

    fun getItem(position: Int) : LottoNumberData {
        return mViewModel.mLottoNumberList.get(position)
    }
}