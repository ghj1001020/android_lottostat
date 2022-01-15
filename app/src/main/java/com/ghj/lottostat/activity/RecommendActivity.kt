package com.ghj.lottostat.activity

import android.os.SystemClock
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.LottoNumberAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.RecommendViewModel
import com.ghj.lottostat.common.DefineCode
import com.ghj.lottostat.common.DefinePref
import com.ghj.lottostat.databinding.ActivityRecommendBinding
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.dialog.CommonDialog
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.util.AlertUtil
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.PrefUtil
import java.util.concurrent.ThreadLocalRandom

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

        val isExcludePrevWinNumber = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER, DefinePref.DFT_IS_EXCLUDE_PREV_WIN_NUMBER)
        val cntExcludePrevWinNumber = PrefUtil.getInstance(this).getInt(DefinePref.CNT_EXCLUDE_PREV_WIN_NUMBER, DefinePref.DFT_CNT_EXCLUDE_PREV_WIN_NUMBER);
        val isExcludePrevWinNumberWithBonus = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS, DefinePref.DFT_IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS)

        val isIncludeLastRoundWinNumber = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, DefinePref.DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER)
        val cntIncludeLastRoundWinNumber = PrefUtil.getInstance(this).getInt(DefinePref.CNT_INCLUDE_LAST_ROUND_WIN_NUMBER, DefinePref.DFT_CNT_INCLUDE_LAST_ROUND_WIN_NUMBER)
        val isIncludeLastRoundWinNumberWithBonus = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS, DefinePref.DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS)

        val isExcludeConsecutiveNumber = PrefUtil.getInstance(this).getBoolean(DefinePref.IS_EXCLUDE_CONSECUTIVE_NUMBER, DefinePref.DFT_IS_EXCLUDE_CONSECUTIVE_NUMBER)
        val cntExcludeConsecutiveNumber = PrefUtil.getInstance(this).getInt(DefinePref.CNT_EXCLUDE_CONSECUTIVE_NUMBER, DefinePref.DFT_CNT_EXCLUDE_CONSECUTIVE_NUMBER)

        var index : Int = 0
        val LOTTO = arrayListOf<Int>()  // 추천 로또번호

        lottoNumberAdapter.clearItems()
        while(index < count) {

            // 번호생성
            LOTTO.clear()
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
                    LOTTO.add(goodNumber)
                    // 당첨번호에서 추가한 번호삭제
                    lastRound.removeAt(tempIndex)
                    GROUP.remove(goodNumber)
                }
            }

            while ( LOTTO.size < 6 ) {
                // 이전 당첨번호와 n개이상 일치시 제외
                if( isExcludePrevWinNumber || isExcludePrevWinNumberWithBonus ) {
                    if( LOTTO.size == cntExcludePrevWinNumber-1 ) {
                        // 0번째 번호가 포함된 로또당첨번호 리스트
                        val list : ArrayList<MutableList<Int>> = SQLiteService.selectPrevWinNumberByNum(this, LOTTO.get(0), isExcludePrevWinNumberWithBonus)

                        for( idx in 0 until list.size ) {
                            val WIN_NUMBER : MutableList<Int> = list.get(idx) // 당첨번호
                            var isPrevWinNumber = true  // 이전번호와 n-1개 일치여부
                            // 이전 당첨번호와 n-1개 일치하는지 체크
                            for( i in 1 until LOTTO.size ) {
                                if( !WIN_NUMBER.contains(LOTTO.get(i)) ) {
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
                }

                // 번호추천
                val numIndex = ThreadLocalRandom.current().nextInt(0, GROUP.size)
                val number = GROUP.get(numIndex)

                // 추천번호 결과담고 모그룹에서 삭제
                LOTTO.add( number )
                LOTTO.sort()
                GROUP.removeAt( numIndex )

                // n개이상 연속된 수 체크
                if( isExcludeConsecutiveNumber && cntExcludeConsecutiveNumber <= LOTTO.size) {
                    var isConsecutive : Boolean = false    // 결과체크
                    var cntConsecutive = 1  // 연속된수 개수
                    var temp : Int = -1 // 이전번호
                    for( idx in 0 until LOTTO.size ) {
                        // 두수 사이 간격이 1이면 이전번호와 연속된 수
                        if( Math.abs(temp-LOTTO.get(idx)) <= 1 ) {
                            // 지금까지 연속수 >= n 연속수 한계 설정개수
                            if( (++cntConsecutive) >= cntExcludeConsecutiveNumber ) {
                                isConsecutive = true
                                break
                            }
                        }
                        // 이전번호와 연속되지 않은 수
                        else {
                            cntConsecutive = 1
                        }
                        temp = LOTTO.get(idx)
                    }

                    // n개이상 연속된 수이면 지우고 다시하기
                    if( isConsecutive ) {
                        LOTTO.remove( number )
                        continue
                    }
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
            mContent.layoutList.visibility = View.VISIBLE
            mContent.btnSave.visibility = View.VISIBLE
            mContent.noContent.root.visibility = View.GONE
        }
        else {
            mContent.layoutList.visibility = View.GONE
            mContent.btnSave.visibility = View.GONE
            mContent.noContent.root.visibility = View.VISIBLE
        }
    }
}