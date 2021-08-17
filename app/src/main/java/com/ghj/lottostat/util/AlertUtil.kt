package com.ghj.lottostat.util

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.ghj.lottostat.dialog.CommonDialog

object AlertUtil {

    fun alert(context: Context, title: String="", message: String="", positiveListener: ((dialog: CommonDialog)->Unit)?=null, negativeListener: ((dialog: CommonDialog)->Unit)?=null ) : Dialog {
        val dialog = CommonDialog(context, title, message)
        dialog.positiveListener = positiveListener
        dialog.negativeListener = negativeListener
        dialog.show()
        return dialog
    }
}