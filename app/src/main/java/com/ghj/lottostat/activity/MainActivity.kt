package com.ghj.lottostat.activity

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.base.BaseActivity
import com.ghj.lottostat.activity.base.BaseViewModelActivity
import com.ghj.lottostat.activity.data.LinkData
import com.ghj.lottostat.activity.viewmodel.MainViewModel
import com.ghj.lottostat.common.LinkParam
import com.ghj.lottostat.databinding.ActivityMainBinding
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.Util
import com.google.rpc.Help.Link

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val mLatestNumber : ArrayList<Int> by lazy {
        SQLiteService.selectLastRoundWinNumber(this)
    }

    override fun newBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreateAfter() {
        checkIntent(intent)
        initLayout()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntent(intent)
    }

    fun checkIntent(intent: Intent?) {
        if(intent != null) {
            // 외부링크 확인
            val linkType : LinkData? = if(Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) {
                    intent.getSerializableExtra(LinkParam.LINK, LinkData::class.java) }
                else {
                    intent.getSerializableExtra(LinkParam.LINK) as? LinkData
                }
            // 번호추천
            if( linkType?.classCode == LinkParam.RECOMMEND ) {
                moveToRecommend()
            }
        }
    }

    fun initLayout() {
        mBinding.title.text = "${mLatestNumber[0]}회"
        mBinding.num1.num = mLatestNumber[1]
        mBinding.num2.num = mLatestNumber[2]
        mBinding.num3.num = mLatestNumber[3]
        mBinding.num4.num = mLatestNumber[4]
        mBinding.num5.num = mLatestNumber[5]
        mBinding.num6.num = mLatestNumber[6]
        mBinding.numBonus.num = mLatestNumber[7]

        mBinding.btnRecommend.setOnClickListener{ v: View->
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
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
        startActivity(intent)
    }
}