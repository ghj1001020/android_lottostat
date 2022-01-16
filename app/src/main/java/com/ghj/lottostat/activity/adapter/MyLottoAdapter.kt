package com.ghj.lottostat.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ghj.lottostat.activity.base.BaseRecyclerViewAdapter
import com.ghj.lottostat.activity.base.BaseRecyclerViewHolder
import com.ghj.lottostat.activity.base.IListListener
import com.ghj.lottostat.activity.data.MyLottoNumberData
import com.ghj.lottostat.activity.data.MyLottoType
import com.ghj.lottostat.databinding.RowMyLottoDateBinding
import com.ghj.lottostat.databinding.RowMyLottoNumberBinding
import com.ghj.lottostat.databinding.RowMyLottoRoundBinding
import com.ghj.lottostat.util.DateUtil.convertDate

class MyLottoAdapter(mContext: Context, val mMyLottoList : ArrayList<MyLottoNumberData>) : BaseRecyclerViewAdapter(mContext) {

    var mListener : IListListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        if( viewType == MyLottoType.ROUND_OPEN.ordinal || viewType == MyLottoType.ROUND_CLOSE.ordinal ) {
            val binding = RowMyLottoRoundBinding.inflate(LayoutInflater.from(mContext), parent, false)
            return MyLottoRoundHolder(binding)
        }
        else if( viewType == MyLottoType.REG_DATE.ordinal ) {
            val binding = RowMyLottoDateBinding.inflate(LayoutInflater.from(mContext), parent, false)
            return MyLottoDateHolder(binding)
        }
        else {
            val binding = RowMyLottoNumberBinding.inflate(LayoutInflater.from(mContext), parent, false)
            return MyLottoNumberHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return mMyLottoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return mMyLottoList[position].type.ordinal
    }


    // 회차
    inner class MyLottoRoundHolder(val mBinding: RowMyLottoRoundBinding) : BaseRecyclerViewHolder(mBinding) {

        override fun onBindViewHolder(position: Int) {
            val data = mMyLottoList.get(position)
            mBinding.txtNo.text = "${data.roundNo}회"
            mBinding.chkFold.isChecked = data.type == MyLottoType.ROUND_OPEN
            mBinding.root.setOnClickListener {
                mListener?.onItemClick(position)
            }
        }
    }

    // 날짜
    inner class MyLottoDateHolder(val mBinding: RowMyLottoDateBinding) : BaseRecyclerViewHolder(mBinding) {

        override fun onBindViewHolder(position: Int) {
            val data = mMyLottoList.get(position)
            mBinding.txtRegDate.text = data.regDate.convertDate()
        }
    }

    // 번호
    inner class MyLottoNumberHolder(val mBinding: RowMyLottoNumberBinding) : BaseRecyclerViewHolder(mBinding) {

        override fun onBindViewHolder(position: Int) {
            val data = mMyLottoList.get(position)
            mBinding.num1.num = data.number1
            mBinding.num2.num = data.number2
            mBinding.num3.num = data.number3
            mBinding.num4.num = data.number4
            mBinding.num5.num = data.number5
            mBinding.num6.num = data.number6

            mBinding.divider.visibility = if(position == itemCount-1) View.GONE else View.VISIBLE
        }
    }

}