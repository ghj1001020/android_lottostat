package com.ghj.lottostat.activity

import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.activity.adapter.WinLottoAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.base.IListListener
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.activity.viewmodel.WinLottoViewModel
import com.ghj.lottostat.databinding.ActivityWinLottoBinding
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.dialog.WinLottoAnalysisDialog

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
        winLottoAdapter.mListener = object : IListListener {
            // 분석정보
            override fun onItemClick(position: Int) {
                LTApp.mActivity?.run {
                    val currentData = winLottoAdapter.mWinList.get(position)
                    var prevData: LottoWinNumber? = null
                    if( position+1 < winLottoAdapter.mWinList.size ) {
                        prevData = winLottoAdapter.mWinList.get(position+1)
                    }
                    val dialog = WinLottoAnalysisDialog(this, currentData, prevData)
                    dialog.show()
                }
            }
        }
        mContent.rvWinLotto.adapter = winLottoAdapter
    }
}