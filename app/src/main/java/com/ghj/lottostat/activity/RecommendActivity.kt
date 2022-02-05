package com.ghj.lottostat.activity

import android.os.SystemClock
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.LottoNumberAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.RecommendViewModel
import com.ghj.lottostat.common.CONSECUTIVE_NUMBER
import com.ghj.lottostat.common.DefineCode
import com.ghj.lottostat.common.LAST_ROUND_WIN_NUMBER
import com.ghj.lottostat.common.LottoScript.generateConsecutiveNumber
import com.ghj.lottostat.common.LottoScript.getConsecutiveCount
import com.ghj.lottostat.databinding.ActivityRecommendBinding
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.dialog.CommonDialog
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.util.AlertUtil
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.PrefUtil
import java.security.SecureRandom

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

        mContent.noContent.txtDesc.text = getString(R.string.no_number_recommend)
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
                generateLottoNumber(20);
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
                dialog.setNegativeListener()
                dialog.show()
            }
        }
    }

    fun generateLottoNumber(count: Int) {
        val startTime = SystemClock.elapsedRealtime()

        val isLastRoundWinNumber = PrefUtil.getInstance(this).getBoolean(LAST_ROUND_WIN_NUMBER.SELECT, LAST_ROUND_WIN_NUMBER.DFT_SELECT)
        val cntLastRoundWinNumber = PrefUtil.getInstance(this).getInt(LAST_ROUND_WIN_NUMBER.CNT, LAST_ROUND_WIN_NUMBER.DFT_CNT)
        val isLastRoundWinNumberWithBonus = PrefUtil.getInstance(this).getBoolean(LAST_ROUND_WIN_NUMBER.BONUS, LAST_ROUND_WIN_NUMBER.DFT_BONUS)

        val isConsecutiveNumber = PrefUtil.getInstance(this).getBoolean(CONSECUTIVE_NUMBER.SELECT, CONSECUTIVE_NUMBER.DFT_SELECT)
        var cntConsecutiveNumber = PrefUtil.getInstance(this).getInt(CONSECUTIVE_NUMBER.CNT, CONSECUTIVE_NUMBER.DFT_CNT)

        var index : Int = 0
        val LOTTO = arrayListOf<Int>()  // 추천 로또번호

        lottoNumberAdapter.clearItems()
        while(index < count) {

            // 번호생성
            LOTTO.clear()
            // 로또번호 1~45
            val GROUP : ArrayList<Int> = arrayListOf()
            GROUP.addAll(DefineCode.LOTTERY)

            // 이전 회차 번호 중 n개 일치
            if( isLastRoundWinNumber ) {
                val lastRound = SQLiteService.selectLastRoundWinNumber(this, isLastRoundWinNumberWithBonus)
                // 0 <= idx < cntIncludeLastRoundWinNumber
                for( idx in 0 until cntLastRoundWinNumber) {
                    // 인덱스 구해서 추천번호 뽑기
                    val tempIndex = SecureRandom().nextInt(lastRound.size )
                    val goodNumber : Int = lastRound.get(tempIndex)
                    LOTTO.add(goodNumber)
                    // 당첨번호에서 추가한 번호삭제
                    lastRound.removeAt(tempIndex)
                    GROUP.remove(goodNumber)
                }

                // 나머지 당첨번호 삭제
                for( num in lastRound ) {
                    GROUP.remove(num)
                }
            }

            var isOverConsecutiveNumber = false   // 이미 제한된 연속수가 넘어갔는지 여부
            if( isConsecutiveNumber ) {
                // 뽑을수 있는 수보다 연속해야 하는 숫자가 더 크면 뽑을수 있는 수만큼만 연속해야 한다
                if( cntConsecutiveNumber > (6-LOTTO.size) ) {
                    cntConsecutiveNumber = 6-LOTTO.size
                }

                if( LOTTO.getConsecutiveCount() > cntConsecutiveNumber ) {
                    isOverConsecutiveNumber = true
                }
            }

            while ( LOTTO.size < 6 ) {

                // 번호추천
                val numIndex = SecureRandom().nextInt(GROUP.size)
                val number = GROUP.get(numIndex)

                // 추천번호 결과담고 모그룹에서 삭제
                LOTTO.add( number )
                LOTTO.sort()
                GROUP.removeAt( numIndex )

                LogUtil.d("${LOTTO.size} >> ${LOTTO}")

                // n개 연속된 수
                if( isConsecutiveNumber && cntConsecutiveNumber == (6-LOTTO.size) ) {
                    LOTTO.generateConsecutiveNumber(GROUP, cntConsecutiveNumber)
                }

                // n개 연속된 수 필터 체크
                if( isConsecutiveNumber && !isOverConsecutiveNumber && LOTTO.getConsecutiveCount() > cntConsecutiveNumber ) {
                    LOTTO.remove( number )
                    GROUP.remove( number )
                }
            }

            // 번호 추천 목록에 담기
            lottoNumberAdapter.addItem(LOTTO)

            // 인덱스 1추가
            index++
        }
        lottoNumberAdapter.notifyDataSetChanged()
        renderLottoNumberList();

        val runTime = SystemClock.elapsedRealtime() - startTime;
        LogUtil.d("runTime = ${runTime}")
    }

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