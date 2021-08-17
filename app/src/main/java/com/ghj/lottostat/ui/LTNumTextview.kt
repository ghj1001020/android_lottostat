package com.ghj.lottostat.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ghj.lottostat.R

class LTNumTextview(val mContext: Context) : AppCompatTextView(mContext) {

    var num : Int = 0


    constructor(context: Context, attrs: AttributeSet) : this(context) {
        initAttrs(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context) {
        initAttrs(attrs, defStyleAttr)
    }

    fun initAttrs(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray: TypedArray = mContext.theme.obtainStyledAttributes(attrs, R.styleable.LTNumTextview, defStyleAttr, 0)
        try {
            num = typedArray.getInteger(R.styleable.LTNumTextview_num, 0)
        }
        finally {
            typedArray.recycle()
        }

        initLayout()
    }

    fun initLayout() {
        val gd = GradientDrawable()
        gd.cornerRadius = 3f

    }
}