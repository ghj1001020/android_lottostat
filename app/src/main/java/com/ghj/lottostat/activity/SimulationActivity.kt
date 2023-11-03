package com.ghj.lottostat.activity

import android.os.SystemClock
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.SimulationAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.activity.viewmodel.SimulationViewModel
import com.ghj.lottostat.common.LottoScript
import com.ghj.lottostat.databinding.ActivitySimulationBinding
import com.ghj.lottostat.dialog.FilterDialog
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.StringUtil.stringToDouble
import java.text.DecimalFormat

class SimulationActivity : BaseDrawerViewModelActivity<ActivitySimulationBinding, SimulationViewModel>(), View.OnClickListener {

    // 시뮬레이션 번호 목록 어답터
    lateinit var simulationAdapter: SimulationAdapter

    // 당첨번호
    val winData : LottoWinNumber = LTApp.LottoWinNumberList.first()


    override fun newContentBinding(): ActivitySimulationBinding {
        return ActivitySimulationBinding.inflate(layoutInflater)
    }

    override fun newViewModel(): SimulationViewModel {
        return ViewModelProvider(this).get(SimulationViewModel::class.java)
    }

    override fun onCreateAfter() {
        initLayout()
    }

    fun initLayout() {
        mContent.txtWinNo.text = "${winData.no}회 담청번호"
        mContent.num1.num = winData.win1
        mContent.num2.num = winData.win2
        mContent.num3.num = winData.win3
        mContent.num4.num = winData.win4
        mContent.num5.num = winData.win5
        mContent.num6.num = winData.win6
        mContent.numBonus.num = winData.bonus
        mContent.txt1PlaceAmt.text = winData.place1Amt
        mContent.txt2PlaceAmt.text = winData.place2Amt
        mContent.txt3PlaceAmt.text = winData.place3Amt
        mContent.txt4PlaceAmt.text = winData.place4Amt
        mContent.txt5PlaceAmt.text = winData.place5Amt

        mContent.noContent.txtDesc.text = getString(R.string.no_number_simulation)
        mContent.layoutTitle.setOnClickListener { v ->
            mContent.chkFold.isChecked = !mContent.chkFold.isChecked
            mContent.layoutResult.visibility = if( mContent.chkFold.isChecked ) View.VISIBLE else View.GONE
        }
        mContent.btnFilter.setOnClickListener(this)
        mContent.btnSimulation.setOnClickListener(this)

        simulationAdapter = SimulationAdapter(this, getViewModel().mSimulationList)
        mContent.rvLottoNumber.adapter = simulationAdapter
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            // 필터
            R.id.btnFilter -> {
                val dialog = FilterDialog(this)
                dialog.show()
            }

            // 시뮬레이션
            R.id.btnSimulation -> {
                simulationLottoNumber()
            }
        }
    }

    // 시뮬레이션
    fun simulationLottoNumber() {
        val startTime = SystemClock.elapsedRealtime()
        simulationAdapter.clearItems()

        val list = LottoScript.GenerateLottoNumberList(this, winData.no, 100 )

        simulationAdapter.addItems(list, winData)
        simulationAdapter.notifyDataSetChanged()
        renderSimulationList();

        mContent.place1Result.text = "${simulationAdapter.ctnWin1}개"
        mContent.place2Result.text = "${simulationAdapter.ctnWin2}개"
        mContent.place3Result.text = "${simulationAdapter.ctnWin3}개"
        mContent.place4Result.text = "${simulationAdapter.ctnWin4}개"
        mContent.place5Result.text = "${simulationAdapter.ctnWin5}개"

        var total = mContent.txt1PlaceAmt.text.toString().stringToDouble() * simulationAdapter.ctnWin1
        total += mContent.txt2PlaceAmt.text.toString().stringToDouble() * simulationAdapter.ctnWin2
        total += mContent.txt3PlaceAmt.text.toString().stringToDouble() * simulationAdapter.ctnWin3
        total += mContent.txt4PlaceAmt.text.toString().stringToDouble() * simulationAdapter.ctnWin4
        total += mContent.txt5PlaceAmt.text.toString().stringToDouble() * simulationAdapter.ctnWin5

        val df = DecimalFormat("#,##0")
        mContent.txtTotal.text = df.format(total)

        mContent.chkFold.isChecked = true
        mContent.layoutResult.visibility = View.VISIBLE

        val runTime = SystemClock.elapsedRealtime() - startTime;
        LogUtil.d("runTime = ${runTime}")
    }

    fun renderSimulationList() {
        if( getViewModel().mSimulationList.size > 0 ) {
            mContent.rvLottoNumber.visibility = View.VISIBLE
            mContent.noContent.root.visibility = View.GONE
        }
        else {
            mContent.rvLottoNumber.visibility = View.GONE
            mContent.noContent.root.visibility = View.VISIBLE
        }
    }
}