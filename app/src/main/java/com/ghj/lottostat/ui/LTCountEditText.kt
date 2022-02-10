package com.ghj.lottostat.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import com.ghj.lottostat.util.StringUtil
import com.ghj.lottostat.util.StringUtil.stringToInt

class LTCountEditText(val mContext: Context, attrs: AttributeSet) : AppCompatEditText(mContext, attrs) {

    var mCurrentNumber : Int = 0
    var minNum : Int = 0
    var maxNum : Int = 6

    init {
        isEnabled = false
        mCurrentNumber = this.text.toString().stringToInt()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs) {}
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : this(context, attrs) {}

    fun setNumber(num: Int) {
        if( minNum <= num && num <= maxNum ) {
            mCurrentNumber = num
        }
        setText( "${mCurrentNumber}" )
    }

    fun add(num: Int=1) {
        setNumber(mCurrentNumber + num)
    }

    fun minus(num: Int=1) {
        setNumber(mCurrentNumber - num)
    }

    fun setMinNumber(num: Int) {
        minNum = num
    }

    fun setMaxNumber(num: Int) {
        maxNum = num
    }
}