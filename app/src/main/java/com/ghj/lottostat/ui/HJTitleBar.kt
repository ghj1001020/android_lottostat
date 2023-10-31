package com.ghj.lottostat.ui

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.R
import com.ghj.lottostat.databinding.AppbarMainBinding

class HJTitleBar(val mContext: Context, attrs: AttributeSet) : RelativeLayout(mContext, attrs) {

    // 버튼 타입
    enum class eButtonType {
        NONE, BACK, MENU
    }

    lateinit var mBinding : AppbarMainBinding

    // 타이틀
    var mTitle : String = ""
        set(value) {
            mBinding.txtTitle.text = value
            field = value
        }
    var mLeftButtonType : eButtonType = eButtonType.NONE // 왼쪽 버튼타입

    init {
        mBinding = AppbarMainBinding.inflate(LayoutInflater.from(mContext), this, true)

        val typedArray: TypedArray = mContext.theme.obtainStyledAttributes(attrs, R.styleable.HJTitleBar, 0, 0)
        try {
            mTitle = typedArray.getString(R.styleable.HJTitleBar_title)!!
            val leftType : Int = typedArray.getInteger(R.styleable.HJTitleBar_leftIconType, eButtonType.NONE.ordinal)
            for( type in eButtonType.values() ) {
                if( type.ordinal == leftType ) {
                    mLeftButtonType = type
                    break
                }
            }

            initLayout()
        }
        finally {
            typedArray.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs) {}
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : this(context, attrs) {}


    fun initLayout() {
        // 왼쪽버튼
        if( mLeftButtonType == eButtonType.BACK ) {
            mBinding.btnLeft.visibility = View.VISIBLE
            mBinding.btnLeft.setBackgroundResource( R.drawable.ic_back )
            mBinding.btnLeft.setOnClickListener { view: View ->
                LTApp.mActivity?.onBackPressed()
            }
        }
        if( mLeftButtonType == eButtonType.MENU ) {
            mBinding.btnLeft.visibility = View.VISIBLE
            mBinding.btnLeft.setBackgroundResource( R.drawable.ic_more )
            mBinding.btnLeft.setOnClickListener { view: View ->
                val drawer = LTApp.mActivity?.findViewById<DrawerLayout>(R.id.drawer)
                drawer?.open()
            }
        }
        else {
            mBinding.btnLeft.visibility = View.GONE
        }
    }
}