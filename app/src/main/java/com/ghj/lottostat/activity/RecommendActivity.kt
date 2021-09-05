package com.ghj.lottostat.activity

import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.LottoNumberAdapter
import com.ghj.lottostat.activity.base.BaseViewModelActivity
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.activity.viewmodel.RecommendViewModel
import com.ghj.lottostat.common.DefineCode
import com.ghj.lottostat.common.DefinePref
import com.ghj.lottostat.databinding.ActivityRecommendBinding
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.PrefUtil
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
                generateLottoNumber(20);
            }
        }
    }

    fun generateLottoNumber(count: Int) {
        val startTime = SystemClock.elapsedRealtime()

        val isExcludePrevWinNumber = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER, DefinePref.DFT_IS_EXCLUDE_PREV_WIN_NUMBER)
        val cntExcludePrevWinNumber = PrefUtil.getInstance(this).getInt(DefinePref.CNT_EXCLUDE_PREV_WIN_NUMBER, DefinePref.DFT_CNT_EXCLUDE_PREV_WIN_NUMBER);
        val isExcludePrevWinNumberWithBonus = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS, DefinePref.DFT_IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS)

        val isIncludeLastRoundWinNumber = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, DefinePref.DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER)
        val cntIncludeLastRoundWinNumber = PrefUtil.getInstance(this).getInt(DefinePref.CNT_INCLUDE_LAST_ROUND_WIN_NUMBER, DefinePref.DFT_CNT_INCLUDE_LAST_ROUND_WIN_NUMBER)
        val isIncludeLastRoundWinNumberWithBonus = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS, DefinePref.DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS)

        val isExcludeConsecutiveNumber = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_EXCLUDE_CONSECUTIVE_NUMBER, DefinePref.DFT_IS_EXCLUDE_CONSECUTIVE_NUMBER)
        val cntExcludeConsecutiveNumber = PrefUtil.getInstance(this).getInt(DefinePref.CNT_EXCLUDE_CONSECUTIVE_NUMBER, DefinePref.DFT_CNT_EXCLUDE_CONSECUTIVE_NUMBER)


        var index : Int = 0
        val lotto = arrayListOf<Int>()  // 추천 로또번호

        // 테스트 데이터
//        var testIndex : Int = 0;
//        val testLotto = arrayListOf<MutableList<Int>>()
//        testLotto.add( mutableListOf(12, 13, 14, 15, 34, 36) )
//        testLotto.add( mutableListOf(2, 3, 9, 15, 23, 34) )
//        testLotto.add( mutableListOf(4, 17, 18, 19, 39, 43) )

        lottoNumberAdapter.clearItems()
        while(index < count) {

            // 번호생성
            lotto.clear()
            // 로또번호 1~45
            val GROUP : MutableList<Int> = DefineCode.LOTTERY.toMutableList()

            // 직전회차 당첨번호 중 n개이상 포함
            if( isIncludeLastRoundWinNumber || isIncludeLastRoundWinNumberWithBonus ) {
                val lastRound = SQLiteService.selectLastRoundWinNumber(this, isIncludeLastRoundWinNumberWithBonus)
                // 0 <= idx < cntIncludeLastRoundWinNumber
                for( idx in 0 until cntIncludeLastRoundWinNumber) {
                    // 인덱스 구해서 추천번호 뽑기
                    val tempIndex = ThreadLocalRandom.current().nextInt(0, lastRound.size )
                    val goodNumber : Int = lastRound.get(tempIndex)
                    lotto.add(goodNumber)
                    // 당첨번호에서 추가한 번호삭제
                    lastRound.removeAt(tempIndex)
                    GROUP.remove(goodNumber)
                }
            }

            while ( lotto.size < 6 ) {
                // 이전 당첨번호와 n개이상 일치시 제외
                if( lotto.size == cntExcludePrevWinNumber-1 ) {
                    // 0번째 번호가 포함된 로또당첨번호 리스트
                    val list : ArrayList<MutableList<Int>> = SQLiteService.selectPrevWinNumberByNum(this, lotto.get(0), isExcludePrevWinNumberWithBonus)

                    for( idx in 0 until list.size ) {
                        val WIN_NUMBER : MutableList<Int> = list.get(idx) // 당첨번호
                        var isPrevWinNumber = true  // 이전번호와 n-1개 일치여부
                        // 이전 당첨번호와 n-1개 일치하는지 체크
                        for( i in 1 until lotto.size ) {
                            if( !WIN_NUMBER.contains(lotto.get(i)) ) {
                                isPrevWinNumber = false
                                break
                            }
                        }

                        // 이전번호와 n-1개 일치하면 나머지번호 삭제
                        if( isPrevWinNumber ) {
                            for( i in 0 until WIN_NUMBER.size ) {
                                GROUP.remove( WIN_NUMBER.get(i) )
                            }
                        }
                    }
                }

                // 번호추천
                val numIndex = ThreadLocalRandom.current().nextInt(0, GROUP.size)
                lotto.add( GROUP.get(numIndex) )
                GROUP.removeAt( numIndex )
            }
            lotto.sort()

            // 테스트
//            if( testIndex < testLotto.size ) {
//                lotto.clear()
//                lotto.addAll( testLotto.get(testIndex) )
//                testIndex++
//            }

            // 이전로또번호 체크
            if( isExcludePrevWinNumber ) {
                val isLottoWinNumber = SQLiteService.selectIsLottoWinNumber(this, lotto)
                // 결과
                if( isLottoWinNumber ) {
                    Toast.makeText(this, "이전로또번호 체크됨" , Toast.LENGTH_SHORT).show()
                    continue
                }
            }

            // 이전로또번호 체크 보너스포함
            if( isExcludePrevWinNumberWithBonus ) {
                var isLottoWinNumberWithBonus = false
                for( item: LottoWinNumber in LTApp.LottoWinNumberList ) {
                    if( item.checkContainNumWithBonus(lotto)) {
                        isLottoWinNumberWithBonus = true
                        break
                    }
                }
                // 결과
                if( isLottoWinNumberWithBonus ) {
                    Toast.makeText(this, "(보너스포함) 이전로또번호 체크됨" , Toast.LENGTH_SHORT).show()
                    continue
                }
            }

            // 번호 추천
            lottoNumberAdapter.addItem(lotto)

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