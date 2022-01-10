package com.ghj.lottostat.activity

import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.MyLottoViewModel
import com.ghj.lottostat.databinding.ActivityMyLottoBinding

class MyLottoActivity : BaseDrawerViewModelActivity<ActivityMyLottoBinding, MyLottoViewModel>() {

    override fun newViewModel(): MyLottoViewModel {
        return ViewModelProvider(this).get(MyLottoViewModel::class.java)
    }

    override fun newContentBinding(): ActivityMyLottoBinding {
        return ActivityMyLottoBinding.inflate(layoutInflater)
    }


    override fun onCreateAfter() {

    }


}