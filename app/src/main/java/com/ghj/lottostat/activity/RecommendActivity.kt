package com.ghj.lottostat.activity

import android.os.SystemClock
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.LottoNumberAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.RecommendViewModel
import com.ghj.lottostat.common.LottoScript
import com.ghj.lottostat.databinding.ActivityRecommendBinding
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.dialog.CommonDialog
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.util.AlertUtil
import com.ghj.lottostat.util.LogUtil

class RecommendActivity : BaseDrawerViewModelActivity<ActivityRecommendBinding, RecommendViewModel>() , View.OnClickListener{

    lateinit var lottoNumberAdapter: LottoNumberAdapter
    val mLottoNum : Int by lazy {
        SQLiteService.selectMaxNo(this) + 1
    }

    override fun newViewModel(): RecommendViewModel {
        return ViewModelProvider(this).get(RecommendViewModel::class.java)
    }

    override fun newContentBinding(): ActivityRecommendBinding {
        return ActivityRecommendBinding.inflate(layoutInflater)
    }


    override fun onCreateAfter() {
        initLayout()
    }

    fun initLayout() {
        mContent.titleBar.mTitle = "${mLottoNum}회 번호추천"

        mContent.btnSave.setOnClickListener(this)
        mContent.btnFilter.setOnClickListener(this)
        mContent.btnRecommend.setOnClickListener(this)

        lottoNumberAdapter = LottoNumberAdapter(this, getViewModel().mLottoNumberList)
        mContent.rvLottoNumber.adapter = lottoNumberAdapter
    }

    override fun onClick(p0: View?) {
        when( p0?.id ) {
            // 필터
            R.id.btnFilter -> {
                val dialog = FilterDialog(this)
                dialog.show()
            }

            // 번호생성
            R.id.btnRecommend -> {
                generateLottoNumber(10);
            }

            // 저장
            R.id.btnSave -> {
                if( getViewModel().mLottoNumberList.count() == 0 ) {
                    AlertUtil.Alert(this, "번호를 생성하세요.").show()
                    return
                }
                val dialog = AlertUtil.Alert(this,"저장하시겠습니까?")
                dialog.setPositiveListener{ dialog: CommonDialog ->
                    SQLiteService.insertMyLottoData(this, mLottoNum, getViewModel().mLottoNumberList)
                }
                dialog.show()
            }
        }
    }

    // 번호생성
    fun generateLottoNumber(count: Int) {
        val startTime = SystemClock.elapsedRealtime()
        lottoNumberAdapter.clearItems()

        val list = LottoScript.GenerateLottoNumberList(this, mLottoNum, count)

        lottoNumberAdapter.addItems(list)
        lottoNumberAdapter.notifyDataSetChanged()
        renderLottoNumberList();

        val runTime = SystemClock.elapsedRealtime() - startTime;
        LogUtil.d("runTime = ${runTime}")
    }

    // 추천받은 번호 목록 노출
    fun renderLottoNumberList() {
        if( getViewModel().mLottoNumberList.size > 0 ) {
            mContent.rvLottoNumber.visibility = View.VISIBLE
            mContent.btnSave.visibility = View.VISIBLE
            mContent.noContent.root.visibility = View.GONE
        }
        else {
            mContent.rvLottoNumber.visibility = View.GONE
            mContent.btnSave.visibility = View.GONE
            mContent.noContent.root.visibility = View.VISIBLE
        }
    }
}