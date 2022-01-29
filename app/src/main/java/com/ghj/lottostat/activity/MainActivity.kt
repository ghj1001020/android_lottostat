package com.ghj.lottostat.activity

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.activity.base.BaseViewModelActivity
import com.ghj.lottostat.activity.viewmodel.MainViewModel
import com.ghj.lottostat.databinding.ActivityMainBinding

class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {

    override fun newViewModel(): MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun newBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateAfter() {
        initLayout()
    }

    fun initLayout() {
        mBinding.btnRecommend.setOnClickListener{ v: View->
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnStat.setOnClickListener { v: View ->

        }
        mBinding.btnSimulation.setOnClickListener { v: View ->

        }
        mBinding.btnWinNumber.setOnClickListener { v: View ->
            val intent = Intent(this, WinLottoActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnMyLotto.setOnClickListener { v: View ->
            val intent = Intent(this, MyLottoActivity::class.java)
            startActivity(intent)
        }
    }
}