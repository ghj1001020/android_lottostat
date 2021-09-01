package com.ghj.lottostat.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import androidx.viewbinding.ViewBinding
import com.ghj.lottostat.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

abstract class BaseBottomSheetDialog<VB: ViewBinding>(val mContext: Context, mStyle: Int) : BottomSheetDialog(mContext, mStyle)
    , View.OnClickListener
    , CompoundButton.OnCheckedChangeListener {

    // 뷰바인딩
    lateinit var mBinding: VB
    abstract fun newBinding() : VB; // 뷰바인딩

    constructor(context: Context) : this(context, R.style.BaseBottomSheetDialog) {
        mBinding = newBinding()
        setContentView(mBinding.root)
        initLayout()
    }

    private fun initLayout() {
        val btnClose: MaterialButton = mBinding.root.findViewById(R.id.btnClose)
        btnClose.setOnClickListener{ v: View ->
            onDialogClose()
        }
    }

    open fun onDialogClose() {
        dismiss()
    }

    override fun onClick(p0: View?) {}
    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {}
}