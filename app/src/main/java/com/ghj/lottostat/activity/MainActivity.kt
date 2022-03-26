package com.ghj.lottostat.activity

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.activity.base.BaseViewModelActivity
import com.ghj.lottostat.activity.data.LinkData
import com.ghj.lottostat.activity.viewmodel.MainViewModel
import com.ghj.lottostat.common.LinkParam
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
        checkIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntent(intent)
    }

    fun checkIntent(intent: Intent?) {
        if(intent != null) {
            // 외부링크 확인
            val linkType = intent.getSerializableExtra(LinkParam.LINK) as? LinkData
            // 번호추천
            if( linkType?.classCode == LinkParam.RECOMMEND ) {
                moveToRecommend()
            }
        }
    }

    fun initLayout() {
        mBinding.btnRecommend.setOnClickListener{ v: View->
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnStat.setOnClickListener { v: View ->

        }
        mBinding.btnSimulation.setOnClickListener { v: View ->
            val intent = Intent(this, SimulationActivity::class.java)
            startActivity(intent)
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

    // 번호추천 화면으로 이동
    fun moveToRecommend() {
        val intent = Intent(this, RecommendActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)
    }
}