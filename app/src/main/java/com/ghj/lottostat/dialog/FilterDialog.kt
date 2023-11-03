package com.ghj.lottostat.dialog

import android.content.Context
import android.os.Bundle
import com.ghj.lottostat.common.FILTER
import com.ghj.lottostat.databinding.DialogFilterBinding
import com.ghj.lottostat.databinding.FilterListBinding
import com.ghj.lottostat.util.PrefUtil

class FilterDialog(context: Context) : BaseBottomSheetDialog<DialogFilterBinding>(context) {

    lateinit var filterList : FilterListBinding


    override fun newBinding(): DialogFilterBinding {
        return DialogFilterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filterList = mBinding.filterList
        initLayout()
    }

    fun initLayout() {
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        initPreferenceLayout()
    }

    fun initPreferenceLayout() {
        PrefUtil.getInstance(mContext).put(FILTER.LAST_ROUND_WIN_NUMBER, true)
        PrefUtil.getInstance(mContext).put(FILTER.CONSECUTIVE_NUMBER, true)
    }
}