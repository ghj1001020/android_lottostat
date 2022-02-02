package com.ghj.lottostat.dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.common.LottoScript.getConsecutiveCount
import com.ghj.lottostat.common.LottoScript.getMatchCount
import com.ghj.lottostat.databinding.DialogWinLottoAnalysisBinding
import kotlin.concurrent.thread

class WinLottoAnalysisDialog(context: Context, val currentNumber: LottoWinNumber, val prevNumber: LottoWinNumber?) : BaseBottomSheetDialog<DialogWinLottoAnalysisBinding>(context) {

    override fun newBinding(): DialogWinLottoAnalysisBinding {
        return DialogWinLottoAnalysisBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    fun initLayout() {
        mBinding.txtTitle.text = "${currentNumber.no}회 번호 분석"
        checkPrevRoundMatchCount()
        checkConsecutiveCount()
    }

    // 직전회차 당첨번호와 일치하는 번호
    fun checkPrevRoundMatchCount() {
        if( prevNumber == null ) {
            mBinding.txtLastRoundMatchCount1.text = "0"
            mBinding.txtLastRoundMatchCount2.text = "0"
            return
        }

        thread(start = true, isDaemon = false, null, "PREV_ROUND_MATCH_COUNT_THREAD") {
            val curList1 = currentNumber.getNumberList(false)
            val prevList1 = prevNumber.getNumberList(false)
            val count1 = curList1.getMatchCount(prevList1)

            val curList2 = currentNumber.getNumberList(false)
            val prevList2 = prevNumber.getNumberList()
            val count2 = curList2.getMatchCount(prevList2)

            LTApp.currentActivity?.runOnUiThread {
                mBinding.txtLastRoundMatchCount1.text = "${count1}"
                mBinding.txtLastRoundMatchCount2.text = "${count2}"
            }
        }
    }

    // 최대로 연속하는 갯수
    fun checkConsecutiveCount() {
        thread(start = true, isDaemon = false, null, "CONSECUTIVE_COUNT_THREAD") {
            val list1 = currentNumber.getNumberList(false)
            val count1 = list1.getConsecutiveCount()

            val list2 = currentNumber.getNumberList()
            val count2 = list2.getConsecutiveCount()

            LTApp.currentActivity?.runOnUiThread {
                mBinding.txtConsecutiveCount1.text = "${count1}"
                mBinding.txtConsecutiveCount2.text = "${count2}"
            }
        }
    }
}