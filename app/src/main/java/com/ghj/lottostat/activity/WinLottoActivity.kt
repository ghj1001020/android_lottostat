package com.ghj.lottostat.activity

import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.activity.adapter.WinLottoAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.WinLottoViewModel
import com.ghj.lottostat.databinding.ActivityWinLottoBinding

class WinLottoActivity : BaseDrawerViewModelActivity<ActivityWinLottoBinding, WinLottoViewModel>() {

    lateinit var winLottoAdapter: WinLottoAdapter

    override fun newContentBinding(): ActivityWinLottoBinding {
        return ActivityWinLottoBinding.inflate(layoutInflater)
    }

    override fun newViewModel(): WinLottoViewModel {
        return ViewModelProvider(this).get(WinLottoViewModel::class.java)
    }

    override fun onCreateAfter() {
        initLayout()
    }

    fun initLayout() {
        winLottoAdapter = WinLottoAdapter(this, getViewModel().mLottoWinList)

    }
}