package com.ghj.lottostat.activity.base

import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.viewbinding.ViewBinding
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.MyLottoActivity
import com.ghj.lottostat.activity.RecommendActivity
import com.ghj.lottostat.activity.SimulationActivity
import com.ghj.lottostat.activity.WinLottoActivity
import com.ghj.lottostat.activity.data.SimulationNumberData
import com.ghj.lottostat.activity.viewmodel.BaseViewModel
import com.ghj.lottostat.databinding.BaseDrawerBinding

abstract class BaseDrawerViewModelActivity<VB: ViewBinding, VM: BaseViewModel> : BaseViewModelActivity<BaseDrawerBinding, VM>() {

    lateinit var mContent : VB

    final override fun newBinding(): BaseDrawerBinding {
        return DataBindingUtil.setContentView(this, R.layout.base_drawer)
    }

    abstract fun newContentBinding() : VB

    final override fun onLayoutCreate() {
        mContent = newContentBinding()
        mBinding.frame.addView( mContent.root )

        mBinding.menu.menuRecommend.setOnClickListener {
            mBinding.drawer.closeDrawers()
            val intent = Intent(this, RecommendActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        mBinding.menu.menuSimulation.setOnClickListener {
            mBinding.drawer.closeDrawers()
            val intent = Intent(this, SimulationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        mBinding.menu.menuWinNumber.setOnClickListener {
            mBinding.drawer.closeDrawers()
            val intent = Intent(this, WinLottoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        mBinding.menu.menuMyLotto.setOnClickListener {
            mBinding.drawer.closeDrawers()
            val intent = Intent(this, MyLottoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }
}