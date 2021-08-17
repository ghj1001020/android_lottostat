package com.ghj.lottostat.dialog

import android.content.Context
import com.ghj.lottostat.databinding.DialogFilterBinding

class FilterDialog(context: Context) : BaseBottomSheetDialog<DialogFilterBinding>(context) {

    override fun newBinding(): DialogFilterBinding {
        return DialogFilterBinding.inflate(layoutInflater)
    }
}