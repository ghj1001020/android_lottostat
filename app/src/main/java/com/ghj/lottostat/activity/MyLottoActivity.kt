package com.ghj.lottostat.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.MyLottoViewModel
import com.ghj.lottostat.databinding.ActivityMyLottoBinding
import com.ghj.lottostat.db.SQLiteService

class MyLottoActivity : BaseDrawerViewModelActivity<ActivityMyLottoBinding, MyLottoViewModel>() {

    override fun newViewModel(): MyLottoViewModel {
        return ViewModelProvider(this).get(MyLottoViewModel::class.java)
    }

    override fun newContentBinding(): ActivityMyLottoBinding {
        return ActivityMyLottoBinding.inflate(layoutInflater)
    }


    override fun onCreateAfter() {
        getViewModel().mMyLottoList = SQLiteService.selectMyLottoRoundNo(this)
        initLayout()
    }

    fun initLayout() {
        mContent.noContent.txtDesc.text = getString(R.string.no_number_mylotto)
        renderMyLottoList()
    }

    fun renderMyLottoList() {
        if( getViewModel().mMyLottoList.size > 0 ) {
            mContent.rvMyLotto.visibility = View.VISIBLE
            mContent.noContent.root.visibility = View.GONE
        }
        else {
            mContent.rvMyLotto.visibility = View.GONE
            mContent.noContent.root.visibility = View.VISIBLE
        }
    }
}