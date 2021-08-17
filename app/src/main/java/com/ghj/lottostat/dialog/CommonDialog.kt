package com.ghj.lottostat.dialog

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ghj.lottostat.R
import com.ghj.lottostat.databinding.DialogCommonBinding
import com.ghj.lottostat.util.Util

class CommonDialog(context: Context, val title: String, val message: String) : BaseDialog<DialogCommonBinding>(context) {

    var positiveListener: ((dialog: CommonDialog)->Unit)? = null
    var negativeListener: ((dialog: CommonDialog)->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 팝업 크기
        val width = Util.getDisplayWidth(mContext)
        window?.setLayout( (0.86*width).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT )

        initLayout()
    }

    override fun newBinding(): DialogCommonBinding {
        return DialogCommonBinding.inflate(layoutInflater)
    }

    fun initLayout() {
        mBinding.txtTitle.text = title
        mBinding.txtMessage.text = message
        mBinding.btnOk.setOnClickListener(this)

        if( negativeListener != null ) {
            mBinding.btnCancel.setOnClickListener(this)
            mBinding.btnCancel.visibility = View.VISIBLE
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
        when(p0?.id) {
            // 취소
            R.id.btnCancel-> {
                negativeListener?.invoke(this)
            }

            // 확인
            R.id.btnOk-> {
                positiveListener?.invoke(this)
            }
        }
    }
}