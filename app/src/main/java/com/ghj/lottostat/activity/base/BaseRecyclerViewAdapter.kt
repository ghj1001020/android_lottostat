package com.ghj.lottostat.activity.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.ghj.lottostat.activity.viewmodel.BaseViewModel

abstract class BaseRecyclerViewAdapter<VM: BaseViewModel>(val mContext: Context, public val mViewModel: VM) : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    final override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        holder.onBindViewHolder(position)
    }
}