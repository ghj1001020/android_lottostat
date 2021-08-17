package com.ghj.lottostat.activity

import android.os.SystemClock
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.LottoNumberAdapter
import com.ghj.lottostat.activity.base.BaseViewModelActivity
import com.ghj.lottostat.activity.viewmodel.RecommendViewModel
import com.ghj.lottostat.databinding.ActivityRecommendBinding
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.util.LogUtil
import java.util.concurrent.ThreadLocalRandom

class RecommendActivity : BaseViewModelActivity<ActivityRecommendBinding, RecommendViewModel>() , View.OnClickListener{

    lateinit var lottoNumberAdapter: LottoNumberAdapter


    override fun newViewModel(): RecommendViewModel {
        return ViewModelProvider(this).get(RecommendViewModel::class.java)
    }

    override fun newBinding(): ActivityRecommendBinding {
        return ActivityRecommendBinding.inflate(layoutInflater)
    }

    override fun onCreateAfter() {
        initLayout()
    }

    fun initLayout() {
        mBinding.btnFilter.setOnClickListener(this)
        mBinding.btnRecommend.setOnClickListener(this)

        lottoNumberAdapter = LottoNumberAdapter(this, getViewModel())
        mBinding.rvLottoNumber.adapter = lottoNumberAdapter
    }

    override fun onClick(p0: View?) {
        when( p0?.id ) {
            // 필터
            R.id.btnFilter -> {
                val dialog = FilterDialog(this)
                dialog.show()
            }

            // 번호새성
            R.id.btnRecommend -> {
                generateLottoNumber(10);
            }
        }
    }

    fun generateLottoNumber(count: Int) {
        val startTime = SystemClock.elapsedRealtime()

        lottoNumberAdapter.clearItems()
        repeat(count) { i: Int ->

            val lotto = mutableListOf<Int>()
            while ( lotto.size < 6 ) {
                val num = ThreadLocalRandom.current().nextInt(1, 46)  // 1~45
                if( !lotto.contains(num) ) {
                    lotto.add( num )
                }
            }
            lotto.sort()
            lottoNumberAdapter.addItem(lotto)
        }
        lottoNumberAdapter.notifyDataSetChanged()
        renderLottoNumberList();

        val runTime = SystemClock.elapsedRealtime() - startTime;
        LogUtil.d("runTime = ${runTime}")
    }

    fun renderLottoNumberList() {
        if( getViewModel().mLottoNumberList.size > 0 ) {
            mBinding.layoutList.visibility = View.VISIBLE
            mBinding.btnSave.visibility = View.VISIBLE
            mBinding.layoutNoContent.visibility = View.GONE
        }
        else {
            mBinding.layoutList.visibility = View.GONE
            mBinding.btnSave.visibility = View.GONE
            mBinding.layoutNoContent.visibility = View.VISIBLE
        }
    }
}