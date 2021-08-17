package com.ghj.lottostat.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.ghj.lottostat.databinding.DialogCommonBinding
import com.ghj.lottostat.util.Util

abstract class BaseDialog<VB: ViewBinding>(val mContext: Context) : Dialog(mContext), View.OnClickListener {
    // 뷰바인딩
    lateinit var mBinding: VB
    abstract fun newBinding() : VB; // 뷰바인딩

    init {
        window?.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN )
        window?.requestFeature( Window.FEATURE_NO_TITLE )
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = newBinding()
        setContentView(mBinding.root)

        setCancelable( false )
        setCanceledOnTouchOutside( false )
    }

    override fun onClick(p0: View?) { }
}