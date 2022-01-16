package com.ghj.lottostat.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.ghj.lottostat.R

class HJCheckImageView(val mContext: Context, attrs: AttributeSet) : AppCompatImageView(mContext, attrs) {
    var isChecked = false
        set(value) {
            this.setImageDrawable( if(value) checkedImage else uncheckedImage )
            field = value
        }
    var checkedImage : Drawable?
    var uncheckedImage : Drawable?

    init {
        val typedArray: TypedArray = mContext.theme.obtainStyledAttributes(attrs, R.styleable.HJCheckImageView, 0, 0)
        try {
            checkedImage = typedArray.getDrawable(R.styleable.HJCheckImageView_checkedImage)
            if( checkedImage == null ) {
                checkedImage = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_u)
            }
            uncheckedImage = typedArray.getDrawable(R.styleable.HJCheckImageView_uncheckedImage)
            if( uncheckedImage == null ) {
                uncheckedImage = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_d)
            }
            isChecked = typedArray.getBoolean(R.styleable.HJCheckImageView_isChecked, false)
        }
        finally {
            typedArray.recycle()
        }
    }
}