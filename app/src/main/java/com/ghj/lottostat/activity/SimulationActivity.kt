package com.ghj.lottostat.activity

import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.viewmodel.SimulationViewModel
import com.ghj.lottostat.databinding.ActivitySimulationBinding

class SimulationActivity : BaseDrawerViewModelActivity<ActivitySimulationBinding, SimulationViewModel>() {

    override fun newContentBinding(): ActivitySimulationBinding {
        return ActivitySimulationBinding.inflate(layoutInflater)
    }

    override fun newViewModel(): SimulationViewModel {
        return ViewModelProvider(this).get(SimulationViewModel::class.java)
    }

    override fun onCreateAfter() {

    }

}