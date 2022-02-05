package com.ghj.lottostat.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.SimulationViewModel
import com.ghj.lottostat.databinding.ActivitySimulationBinding

class SimulationActivity : BaseDrawerViewModelActivity<ActivitySimulationBinding, SimulationViewModel>(), View.OnClickListener {

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
        mContent.noContent.txtDesc.text = getString(R.string.no_number_simulation)
        mContent.layoutTitle.setOnClickListener { v ->
            mContent.chkFold.isChecked = !mContent.chkFold.isChecked
            mContent.layoutResult.visibility = if( mContent.chkFold.isChecked ) View.VISIBLE else View.GONE
        }
        mContent.btnFilter.setOnClickListener(this)
        mContent.btnSimulation.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

    }
}