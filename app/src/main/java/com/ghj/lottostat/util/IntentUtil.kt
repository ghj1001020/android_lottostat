package com.ghj.lottostat.util

import android.content.Context
import android.content.Intent
import com.ghj.lottostat.activity.MainActivity

object IntentUtil {

    // 메인으로 이동
    fun moveToMain(context: Context) {
        val intent: Intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
}