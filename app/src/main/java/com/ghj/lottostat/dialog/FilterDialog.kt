package com.ghj.lottostat.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.ghj.lottostat.R
import com.ghj.lottostat.common.DefinePref
import com.ghj.lottostat.databinding.DialogFilterBinding
import com.ghj.lottostat.databinding.FilterInputCountBinding
import com.ghj.lottostat.databinding.FilterListBinding
import com.ghj.lottostat.ui.LTCountEditText
import com.ghj.lottostat.util.PrefUtil
import com.ghj.lottostat.util.StringUtil

class FilterDialog(context: Context) : BaseBottomSheetDialog<DialogFilterBinding>(context) {

    enum class LAYOUT_TYPE {
        LIST ,
        INPUT_COUNT
    }

    enum class FILTER_TYPE {
        EXCLUDE_PREV_WIN_NUMBER ,
        INCLUDE_LAST_ROUND_WIN_NUMBER ,
        EXCLUDE_CONSECUTIVE_NUMBER
    }


    lateinit var filterList : FilterListBinding
    lateinit var filterInputCount : FilterInputCountBinding

    var mLayoutType : LAYOUT_TYPE = LAYOUT_TYPE.LIST
    var mFilterType : FILTER_TYPE = FILTER_TYPE.EXCLUDE_PREV_WIN_NUMBER

    // 이전 당첨번호 n개 이상 일치시 제외
    var isExcludePrevWinNumber : Boolean = true
    var cntExcludePrevWinNumber : Int = 4
    var isExcludePrevWinNumberWithBonus : Boolean = true

    // 이전 회차 번호 중 n개 이상 포함
    var isIncludeLastRoundWinNumber : Boolean = true
    var cntIncludeLastRoundWinNumber : Int = 1
    var isIncludeLastRoundWinNumberWithBonus : Boolean = true

    // n개 이상 연속된 수 제외
    var isExcludeConsecutiveNumber : Boolean = true
    var cntExcludeConsecutiveNumber : Int = 4


    override fun newBinding(): DialogFilterBinding {
        return DialogFilterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filterList = mBinding.filterList
        filterInputCount = mBinding.filterInputCount
        initLayout()
    }

    fun initLayout() {
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        filterInputCount.btnCountCancel.setOnClickListener( this )
        filterInputCount.btnCountOk.setOnClickListener( this )
        filterInputCount.btnCountMinus.setOnClickListener( this )
        filterInputCount.btnCountPlus.setOnClickListener( this )

        filterList.chkExcludePrevWinNumber.setOnCheckedChangeListener( this )
        filterList.chkIncludeLastRoundWinNumber.setOnCheckedChangeListener( this )
        filterList.chkExcludeConsecutiveNumber.setOnCheckedChangeListener( this )

        initPreferenceLayout()
    }

    fun initPreferenceLayout() {
        // 이전 당첨번호 제외
        updateExcludePrevWinNumber()
        // 이전 회차 번호 중 1개 이상 포함
        updateIncludeLastRoundWinNumber()
        // n개 이상 연속된 수 제외
        updateExcludeConsecutiveNumber()
    }

    override fun onClick(p0: View?) {
        when( p0?.id ) {
            // 횟수입력 > 취소
            R.id.btnCountCancel -> {
                onDialogClose()
            }

            // 횟수입력 > 확인
            R.id.btnCountOk -> {
                val cnt = StringUtil.convertStringToInt( filterInputCount.etCount.text.toString() )
                val isBonus = filterInputCount.chkBonus.isChecked

                // 이전 당첨번호 제외
                if( mFilterType == FILTER_TYPE.EXCLUDE_PREV_WIN_NUMBER ) {
                    PrefUtil.getInstance(mContext).put(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER , true)
                    PrefUtil.getInstance(mContext).put(DefinePref.CNT_EXCLUDE_PREV_WIN_NUMBER , cnt)
                    PrefUtil.getInstance(mContext).put(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS , isBonus)
                    updateExcludePrevWinNumber()
                }
                // 이전 회차 번호 중 n개 이상 포함
                else if( mFilterType == FILTER_TYPE.INCLUDE_LAST_ROUND_WIN_NUMBER ) {
                    PrefUtil.getInstance(mContext).put(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, true)
                    PrefUtil.getInstance(mContext).put(DefinePref.CNT_INCLUDE_LAST_ROUND_WIN_NUMBER, cnt)
                    PrefUtil.getInstance(mContext).put(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS, isBonus)
                    updateIncludeLastRoundWinNumber()
                }
                // n개 이상 연속된 수 제외
                else if( mFilterType == FILTER_TYPE.EXCLUDE_CONSECUTIVE_NUMBER ) {
                    PrefUtil.getInstance(mContext).put(DefinePref.IS_EXCLUDE_CONSECUTIVE_NUMBER, true)
                    PrefUtil.getInstance(mContext).put(DefinePref.CNT_EXCLUDE_CONSECUTIVE_NUMBER, cnt)
                    updateExcludeConsecutiveNumber()
                }

                dismissDialog()
            }

            // 횟수입력 > 숫자 -
            R.id.btnCountMinus -> {
                filterInputCount.etCount.minus()
            }

            // 횟수입력 > 숫자 +
            R.id.btnCountPlus -> {
                filterInputCount.etCount.add()
            }
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        when( p0?.id ) {
            // 이전 당첨번호 제외
            R.id.chkExcludePrevWinNumber -> {
                if( isChecked ) {
                    mFilterType = FILTER_TYPE.EXCLUDE_PREV_WIN_NUMBER
                    mLayoutType = LAYOUT_TYPE.INPUT_COUNT
                    changeFilterLayout()
                    setInputCount(true, isExcludePrevWinNumberWithBonus)
                }
                else {
                    PrefUtil.getInstance(mContext).put( DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER, false )
                    filterList.chkExcludePrevWinNumber.text = String.format(mContext.getString(R.string.filter_exclude_prev_win_number), "n")
                }
            }

            // 이전 회차 번호 중 n개 이상 포함
            R.id.chkIncludeLastRoundWinNumber -> {
                if( isChecked ) {
                    mFilterType = FILTER_TYPE.INCLUDE_LAST_ROUND_WIN_NUMBER
                    mLayoutType = LAYOUT_TYPE.INPUT_COUNT
                    changeFilterLayout()
                    setInputCount(true, isIncludeLastRoundWinNumberWithBonus)
                }
                else {
                    PrefUtil.getInstance(mContext).put( DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, false )
                    filterList.chkIncludeLastRoundWinNumber.text = String.format(mContext.getString(R.string.filter_include_last_round_win_number), "n")
                }
            }

            // n개 이상 연속된 수 제외
            R.id.chkExcludeConsecutiveNumber -> {
                if( isChecked ) {
                    mFilterType = FILTER_TYPE.EXCLUDE_CONSECUTIVE_NUMBER
                    mLayoutType = LAYOUT_TYPE.INPUT_COUNT
                    changeFilterLayout()
                    setInputCount(false, false)
                }
                else {
                    PrefUtil.getInstance(mContext).put( DefinePref.IS_EXCLUDE_CONSECUTIVE_NUMBER, false )
                    filterList.chkExcludeConsecutiveNumber.text = String.format(mContext.getString(R.string.filter_exclude_consecutive_number), "n")
                }
            }
        }
    }

    // 닫기버튼
    override fun onDialogClose() {
        cancelFilterSetting()
        dismissDialog()
    }

    // 다이얼로그 닫기
    fun dismissDialog() {
        if( mLayoutType != LAYOUT_TYPE.LIST ) {
            mLayoutType = LAYOUT_TYPE.LIST
            changeFilterLayout()
            return
        }
        dismiss()
    }

    // 필터화면 레이아웃 변경
    fun changeFilterLayout() {
        if( mLayoutType == LAYOUT_TYPE.INPUT_COUNT ) {
            mBinding.layoutFilterList.visibility = View.GONE
            mBinding.layoutInputCount.visibility = View.VISIBLE
        }
        else {
            mBinding.layoutFilterList.visibility = View.VISIBLE
            mBinding.layoutInputCount.visibility = View.GONE
        }
    }

    // 횟수입력 레이아웃 설정
    fun setInputCount(isBonusShow: Boolean, isBonus: Boolean) {
        when( mFilterType ) {
            FILTER_TYPE.EXCLUDE_PREV_WIN_NUMBER -> {
                filterInputCount.etCount.setMinNumber(4)
                filterInputCount.etCount.setMaxNumber(6)
                filterInputCount.txtTitle.text = String.format(mContext.getString(R.string.filter_exclude_prev_win_number), "n")
                filterInputCount.layoutDesc.visibility = View.GONE
                filterInputCount.etCount.setNumber(cntExcludePrevWinNumber)
            }

            FILTER_TYPE.INCLUDE_LAST_ROUND_WIN_NUMBER -> {
                filterInputCount.etCount.setMinNumber(0)
                filterInputCount.etCount.setMaxNumber(3)
                filterInputCount.txtTitle.text = String.format(mContext.getString(R.string.filter_include_last_round_win_number), "n")
                filterInputCount.layoutDesc.visibility = View.VISIBLE
                filterInputCount.txtDesc.setText(mContext.getString(R.string.filter_desc_include_last_round_win_number))
                filterInputCount.etCount.setNumber(cntIncludeLastRoundWinNumber)
            }

            FILTER_TYPE.EXCLUDE_CONSECUTIVE_NUMBER -> {
                filterInputCount.etCount.setMinNumber(1)
                filterInputCount.etCount.setMaxNumber(4)
                filterInputCount.txtTitle.text = String.format(mContext.getString(R.string.filter_exclude_consecutive_number), "n")
                filterInputCount.layoutDesc.visibility = View.VISIBLE
                filterInputCount.txtDesc.setText(mContext.getString(R.string.filter_desc_exclude_consecutive_number))
                filterInputCount.etCount.setNumber(cntExcludeConsecutiveNumber)
            }
        }

        if( isBonusShow ) {
            filterInputCount.chkBonus.isChecked = isBonus
            filterInputCount.chkBonus.visibility = View.VISIBLE
        }
        else {
            filterInputCount.chkBonus.isChecked = false
            filterInputCount.chkBonus.visibility = View.GONE
        }

        mLayoutType = LAYOUT_TYPE.INPUT_COUNT
    }

    // 필터 설정 취소
    fun cancelFilterSetting() {
        if( mLayoutType == LAYOUT_TYPE.LIST ) {
            return
        }

        // 이전 당첨번호 제외
        if( mFilterType == FILTER_TYPE.EXCLUDE_PREV_WIN_NUMBER ) {
            filterList.chkExcludePrevWinNumber.isChecked = false
        }
        // 이전 회차 번호 중 n개 이상 포함
        else if( mFilterType == FILTER_TYPE.INCLUDE_LAST_ROUND_WIN_NUMBER ) {
            filterList.chkIncludeLastRoundWinNumber.isChecked = false
        }
        // n개 이상 연속된 수 제외
        else if( mFilterType == FILTER_TYPE.EXCLUDE_CONSECUTIVE_NUMBER ) {
            filterList.chkExcludeConsecutiveNumber.isChecked = false
        }
    }


    // 이전 당첨번호 n개 이상 일치시 제외
    fun updateExcludePrevWinNumber() {
        isExcludePrevWinNumber = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER, DefinePref.DFT_IS_EXCLUDE_PREV_WIN_NUMBER)
        cntExcludePrevWinNumber = PrefUtil.getInstance(mContext).getInt(DefinePref.CNT_EXCLUDE_PREV_WIN_NUMBER, DefinePref.DFT_CNT_EXCLUDE_PREV_WIN_NUMBER)
        isExcludePrevWinNumberWithBonus = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS, DefinePref.DFT_IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS)

        filterList.chkExcludePrevWinNumber.isChecked = isExcludePrevWinNumber
        if( isExcludePrevWinNumber ) {
            val bonus = if(isExcludePrevWinNumberWithBonus) "보너스 포함" else "보너스 미포함"
            filterList.chkExcludePrevWinNumber.text = "${String.format(mContext.getString(R.string.filter_exclude_prev_win_number), cntExcludePrevWinNumber)} (${bonus})"
        }
        else {
            filterList.chkExcludePrevWinNumber.text = String.format(mContext.getString(R.string.filter_exclude_prev_win_number), "n")
        }
    }

    // 이전 회차 번호 중 n개 이상 포함
    fun updateIncludeLastRoundWinNumber() {
        isIncludeLastRoundWinNumber = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER, DefinePref.DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER)
        cntIncludeLastRoundWinNumber = PrefUtil.getInstance(mContext).getInt(DefinePref.CNT_INCLUDE_LAST_ROUND_WIN_NUMBER, DefinePref.DFT_CNT_INCLUDE_LAST_ROUND_WIN_NUMBER)
        isIncludeLastRoundWinNumberWithBonus = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS, DefinePref.DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS)

        filterList.chkIncludeLastRoundWinNumber.isChecked = isIncludeLastRoundWinNumber
        if( isIncludeLastRoundWinNumber ) {
            val bonus = if(isIncludeLastRoundWinNumberWithBonus) "보너스 포함" else "보너스 미포함"
            filterList.chkIncludeLastRoundWinNumber.text = "${String.format(mContext.getString(R.string.filter_include_last_round_win_number), cntIncludeLastRoundWinNumber)} (${bonus})"
        }
        else {
            filterList.chkIncludeLastRoundWinNumber.text = String.format(mContext.getString(R.string.filter_include_last_round_win_number), "n")
        }
    }

    // n개 이상 연속된 수 제외
    fun updateExcludeConsecutiveNumber() {
        isExcludeConsecutiveNumber = PrefUtil.getInstance(mContext).getBoolean(DefinePref.IS_EXCLUDE_CONSECUTIVE_NUMBER, DefinePref.DFT_IS_EXCLUDE_CONSECUTIVE_NUMBER)
        cntExcludeConsecutiveNumber = PrefUtil.getInstance(mContext).getInt(DefinePref.CNT_EXCLUDE_CONSECUTIVE_NUMBER, DefinePref.DFT_CNT_EXCLUDE_CONSECUTIVE_NUMBER)

        filterList.chkExcludeConsecutiveNumber.isChecked = isExcludeConsecutiveNumber
        val arg = if(isExcludeConsecutiveNumber) "${cntExcludeConsecutiveNumber}" else "n"
        filterList.chkExcludeConsecutiveNumber.text = String.format(mContext.getString(R.string.filter_exclude_consecutive_number), arg)
    }
}