package com.ghj.lottostat.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel( application: Application ) : AndroidViewModel(application) {

    val networkLiveData : MutableLiveData<Any> = MutableLiveData()

}