package com.ghj.lottostat.util

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.ghj.lottostat.dialog.CommonDialog

object AlertUtil {

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