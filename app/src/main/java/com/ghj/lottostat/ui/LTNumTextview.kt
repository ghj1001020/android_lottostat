package com.ghj.lottostat.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.ghj.lottostat.R
import com.ghj.lottostat.util.Util
import java.lang.Exception

class LTNumTextview(val mContext: Context, attrs: AttributeSet) : AppCompatTextView(mContext, attrs) {

    var num : Int = 0
        set(value) {
            setBackgroundResource( Util.getLottoNumberBgResource(value) )
            text = "${value}"
            field = value
        }


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs) { }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : this(context, attrs) {}


    init {
        val typedArray: TypedArray = mContext.theme.obtainStyledAttributes(attrs, R.styleable.LTNumTextview, 0, 0)
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