package com.ghj.lottostat.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.ghj.lottostat.R
import com.ghj.lottostat.common.DefinePref
import com.ghj.lottostat.databinding.DialogFilterBinding
import com.ghj.lottostat.util.PrefUtil

class FilterDialog(context: Context) : BaseBottomSheetDialog<DialogFilterBinding>(context) {

    override fun newBinding(): DialogFilterBinding {
        return DialogFilterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
    }

    fun initLayout() {
        mBinding.chkExcludePrevWinNumber.setOnCheckedChangeListener( this )
        mBinding.chkExcludePrevWinNumberWithBonus.setOnCheckedChangeListener( this )
        mBinding.chkIncludeLastRoundWinNumber.setOnCheckedChangeListener( this )
        mBinding.chkIncludeLastRoundWinNumberWithBonus.setOnCheckedChangeListener( this )

        initPreferenceLayout()
    }

    fun initPreferenceLayout() {
        // 이전 당첨번호 제외
        val isExcludePrevWinNumber = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER, true)
        mBinding.chkExcludePrevWinNumber.isChecked = isExcludePrevWinNumber

        // (보너스 포함) 이전 당첨번호 제외
        val isExcludePrevWinNumberWithBonus = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS, true)
        mBinding.chkExcludePrevWinNumberWithBonus.isChecked = isExcludePrevWinNumberWithBonus

        // 이전 회차 번호 중 1개 이상 포함
        val isIncludeLastRoundWinNumber = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, true)
        mBinding.chkIncludeLastRoundWinNumber.isChecked = isIncludeLastRoundWinNumber

        // (보너스 포함) 이전 회차 번호 중 1개 이상 포함
        val isIncludeLastRoundWinNumberWithBonus = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS, true)
        mBinding.chkIncludeLastRoundWinNumberWithBonus.isChecked = isIncludeLastRoundWinNumberWithBonus
    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        when( p0?.id ) {
            // 이전 당첨번호 제외
            R.id.chkExcludePrevWinNumber -> {
                if( !isChecked ) {
                    mBinding.chkExcludePrevWinNumberWithBonus.isChecked = false
                }
                PrefUtil.getInstance(mContext).put( DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER, isChecked )
            }

            // (보너스 포함) 이전 당첨번호 제외
            R.id.chkExcludePrevWinNumberWithBonus -> {
                if( isChecked ) {
                    mBinding.chkExcludePrevWinNumber.isChecked = true
                }
                PrefUtil.getInstance(mContext).put( DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS, isChecked )
            }

            // 이전 회차 번호 중 1개 이상 포함
            R.id.chkIncludeLastRoundWinNumber -> {
                if( !isChecked ) {
                    mBinding.chkIncludeLastRoundWinNumberWithBonus.isChecked = false
                }
                PrefUtil.getInstance(mContext).put( DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, isChecked )
            }

            // (보너스 포함) 이전 회차 번호 중 1개 이상 포함
            R.id.chkIncludeLastRoundWinNumberWithBonus -> {
                if( isChecked ) {
                    mBinding.chkIncludeLastRoundWinNumber.isChecked = true
                }
                PrefUtil.getInstance(mContext).put( DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS, isChecked )
            }
        }
    }
}