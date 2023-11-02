package com.ghj.lottostat.activity.base

import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ghj.lottostat.LTApp

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    // 뷰바인딩
    lateinit var mBinding: VB
    abstract fun newBinding() : VB
    open fun onLayoutCreate() { }

    // 앱 실행 가능 여부
    private var isAppReady : Boolean = false
    private var isPostCreate : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LTApp.mActivity = this
        LTApp.mContext = this

        mBinding = newBinding()
        onLayoutCreate()

        checkActivity()
    }

    override fun onResume() {
        super.onResume()
        LTApp.mActivity = this
        LTApp.mContext = this
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        isPostCreate = true;

        if( isAppReady && isPostCreate ) {
            onCreateAfter()
        }
    }

    // 액티비티 실행시 기본적인 체크
    private fun checkActivity() {
        isAppReady = true

        // 로직 수행
        if( isPostCreate && isAppReady )
        {
            onCreateAfter()
        }
    }

    abstract fun onCreateAfter()   // 앱 실행 후 기본적인 체크 후 액티비티 로직 실행

}