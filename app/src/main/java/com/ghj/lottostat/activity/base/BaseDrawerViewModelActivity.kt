package com.ghj.lottostat.activity.base

import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.viewmodel.BaseViewModel
import com.ghj.lottostat.databinding.BaseDrawerBinding

abstract class BaseDrawerViewModelActivity<VB: ViewBinding, VM: BaseViewModel> : BaseViewModelActivity<BaseDrawerBinding, VM>() {

    lateinit var mContent : VB

    final override fun newBinding(): BaseDrawerBinding {
        return BaseDrawerBinding.inflate(layoutInflater)
    }

    abstract fun newContentBinding() : VB

    override fun onLayoutCreate() {
        mContent = newContentBinding()
        (mBinding.root.findViewById<DrawerLayout>(R.id.drawer)).addView( mContent.root )
    }
}