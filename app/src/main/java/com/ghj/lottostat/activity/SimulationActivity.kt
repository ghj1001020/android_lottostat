package com.ghj.lottostat.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.activity.viewmodel.SimulationViewModel
import com.ghj.lottostat.databinding.ActivitySimulationBinding
import com.ghj.lottostat.dialog.FilterDialog

class SimulationActivity : BaseDrawerViewModelActivity<ActivitySimulationBinding, SimulationViewModel>(), View.OnClickListener {

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

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            // 필터
            R.id.btnFilter -> {
                val dialog = FilterDialog(this)
                dialog.show()
            }
        }
    }
}