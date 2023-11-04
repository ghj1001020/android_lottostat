package com.ghj.lottostat.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.dialog.CommonDialog

object AlertUtil {

    // alert
    fun alert(buttonText: String="확인", title: String="", message: String="", listener: DialogInterface.OnClickListener?=null): AlertDialog? {
        if(LTApp.mContext == null) return null;

        val builder = AlertDialog.Builder(LTApp.mContext!!)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(buttonText) { dialog, which ->
            dialog.dismiss()
            listener?.onClick(dialog, which)
        }
        return builder.show();
    }

    class Alert(var context: Context, var title: String="", var message: String="") {
        var positiveText: String = ""
        var negativeText: String = ""
        var positiveListener : ((dialog: CommonDialog)->Unit)? = null
        var negativeListener : ((dialog: CommonDialog)->Unit)? = null

        constructor(context: Context, message: String) : this(context, "", message)


        fun setPositiveListener( text: String="확인", listener: ((dialog: CommonDialog)->Unit)?={}) {
            this.positiveText = text
            this.positiveListener = listener
        }

        fun setNegativeListener( text: String="취소", listener: ((dialog: CommonDialog) -> Unit)?={}) {
            this.negativeText = text
            this.negativeListener = listener
        }

        fun show() {
            val dialog = CommonDialog(context, title, message)
            dialog.positiveListener = positiveListener
            dialog.negativeListener = negativeListener
            dialog.show()
        }
    }
}