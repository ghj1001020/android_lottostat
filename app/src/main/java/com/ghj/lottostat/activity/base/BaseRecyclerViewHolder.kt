package com.ghj.lottostat.activity.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewHolder(vb: ViewBinding) : RecyclerView.ViewHolder(vb.root){

    abstract fun onBindViewHolder(position: Int)
}