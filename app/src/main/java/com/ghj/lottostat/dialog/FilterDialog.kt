package com.ghj.lottostat.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.ghj.lottostat.R
import com.ghj.lottostat.common.CONSECUTIVE_NUMBER
import com.ghj.lottostat.common.DefinePref
import com.ghj.lottostat.common.LAST_ROUND_WIN_NUMBER
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
        LAST_ROUND_WIN_NUMBER ,
        CONSECUTIVE_NUMBER
    }


    lateinit var filterList : FilterListBinding
    lateinit var filterInputCount : FilterInputCountBinding

    var mLayoutType : LAYOUT_TYPE = LAYOUT_TYPE.LIST
    var mFilterType : FILTER_TYPE = FILTER_TYPE.LAST_ROUND_WIN_NUMBER

    // 이전 회차 번호 중 n개 일치
    var isLastRoundWinNumber : Boolean = true
    var cnLastRoundWinNumber : Int = 1
    var isLastRoundWinNumberWithBonus : Boolean = true

    // n개 연속된 수
    var isConsecutiveNumber : Boolean = true
    var cntConsecutiveNumber : Int = 2


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

        filterList.chkLastRoundWinNumber.setOnCheckedChangeListener( this )
        filterList.chkConsecutiveNumber.setOnCheckedChangeListener( this )

        initPreferenceLayout()
    }

    fun initPreferenceLayout() {
        // 이전 회차 번호 중 n개 일치
        updateLastRoundWinNumber()
        // n개 연속된 수
        updateConsecutiveNumber()
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

                // 이전 회차 번호 중 n개 일치
                if( mFilterType == FILTER_TYPE.LAST_ROUND_WIN_NUMBER ) {
                    PrefUtil.getInstance(mContext).put(LAST_ROUND_WIN_NUMBER.SELECT , true)
                    PrefUtil.getInstance(mContext).put(LAST_ROUND_WIN_NUMBER.CNT , cnt)
                    PrefUtil.getInstance(mContext).put(LAST_ROUND_WIN_NUMBER.BONUS , isBonus)
                    updateLastRoundWinNumber()
                }
                // n개 이상 연속된 수 제외
                else if( mFilterType == FILTER_TYPE.CONSECUTIVE_NUMBER ) {
                    PrefUtil.getInstance(mContext).put(CONSECUTIVE_NUMBER.SELECT, true)
                    PrefUtil.getInstance(mContext).put(CONSECUTIVE_NUMBER.CNT, cnt)
                    updateConsecutiveNumber()
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
            // 이전 회차 번호 중 n개 일치
            R.id.chkLastRoundWinNumber -> {
                if( isChecked ) {
                    mFilterType = FILTER_TYPE.LAST_ROUND_WIN_NUMBER
                    mLayoutType = LAYOUT_TYPE.INPUT_COUNT
                    changeFilterLayout()
                    val isBonus = PrefUtil.getInstance(mContext).getBoolean(LAST_ROUND_WIN_NUMBER.BONUS, LAST_ROUND_WIN_NUMBER.DFT_BONUS)
                    setInputCount(true, isBonus)
                }
                else {
                    PrefUtil.getInstance(mContext).put( LAST_ROUND_WIN_NUMBER.SELECT, false )
                    PrefUtil.getInstance(mContext).put( LAST_ROUND_WIN_NUMBER.BONUS, true )
                    filterList.chkLastRoundWinNumber.text = String.format(mContext.getString(R.string.filter_last_round_win_number), "n")
                }
            }

            // n개 이상 연속된 수 제외
            R.id.chkConsecutiveNumber -> {
                if( isChecked ) {
                    mFilterType = FILTER_TYPE.CONSECUTIVE_NUMBER
                    mLayoutType = LAYOUT_TYPE.INPUT_COUNT
                    changeFilterLayout()
                    setInputCount(false, false)
                }
                else {
                    PrefUtil.getInstance(mContext).put( CONSECUTIVE_NUMBER.SELECT, false )
                    filterList.chkConsecutiveNumber.text = String.format(mContext.getString(R.string.filter_consecutive_number), "n")
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
            FILTER_TYPE.LAST_ROUND_WIN_NUMBER -> {
                filterInputCount.etCount.setMinNumber(0)
                filterInputCount.etCount.setMaxNumber(4)
                filterInputCount.txtTitle.text = String.format(mContext.getString(R.string.filter_last_round_win_number), "n")
                filterInputCount.layoutDesc.visibility = View.VISIBLE
                filterInputCount.txtDesc.text = mContext.getString(R.string.filter_desc_last_round_win_number)
                val cnt = PrefUtil.getInstance(mContext).getInt(LAST_ROUND_WIN_NUMBER.CNT, LAST_ROUND_WIN_NUMBER.DFT_CNT)
                filterInputCount.etCount.setNumber(cnt)
            }

            FILTER_TYPE.CONSECUTIVE_NUMBER -> {
                filterInputCount.etCount.setMinNumber(0)
                filterInputCount.etCount.setMaxNumber(3)
                filterInputCount.txtTitle.text = String.format(mContext.getString(R.string.filter_consecutive_number), "n")
                filterInputCount.layoutDesc.visibility = View.VISIBLE
                filterInputCount.txtDesc.text = mContext.getString(R.string.filter_desc_consecutive_number)
                val cnt = PrefUtil.getInstance(mContext).getInt(CONSECUTIVE_NUMBER.CNT, CONSECUTIVE_NUMBER.DFT_CNT)
                filterInputCount.etCount.setNumber(cnt)
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

        // 이전 회차 번호 중 n개 일치
        if( mFilterType == FILTER_TYPE.LAST_ROUND_WIN_NUMBER ) {
            filterList.chkLastRoundWinNumber.isChecked = false
        }
        // n개 연속된 수
        else if( mFilterType == FILTER_TYPE.CONSECUTIVE_NUMBER ) {
            filterList.chkConsecutiveNumber.isChecked = false
        }
    }


    // 이전 회차 번호 중 n개 일치
    fun updateLastRoundWinNumber() {
        val select = PrefUtil.getInstance(mContext).getBoolean(LAST_ROUND_WIN_NUMBER.SELECT, LAST_ROUND_WIN_NUMBER.DFT_SELECT)
        val cnt = PrefUtil.getInstance(mContext).getInt(LAST_ROUND_WIN_NUMBER.CNT, LAST_ROUND_WIN_NUMBER.DFT_CNT)
        val bonus = PrefUtil.getInstance(mContext).getBoolean(LAST_ROUND_WIN_NUMBER.BONUS, LAST_ROUND_WIN_NUMBER.DFT_BONUS)

        filterList.chkLastRoundWinNumber.isChecked = select
        if( select ) {
            val strBonus = if(bonus) "보너스 포함" else "보너스 미포함"
            filterList.chkLastRoundWinNumber.text = "${String.format(mContext.getString(R.string.filter_last_round_win_number), cnt)} (${strBonus})"
        }
        else {
            filterList.chkLastRoundWinNumber.text = String.format(mContext.getString(R.string.filter_last_round_win_number), "n")
        }
    }

    // n개 연속된 수
    fun updateConsecutiveNumber() {
        val select = PrefUtil.getInstance(mContext).getBoolean(CONSECUTIVE_NUMBER.SELECT, CONSECUTIVE_NUMBER.DFT_SELECT)
        val cnt = PrefUtil.getInstance(mContext).getInt(CONSECUTIVE_NUMBER.CNT, CONSECUTIVE_NUMBER.DFT_CNT)

        filterList.chkConsecutiveNumber.isChecked = select
        val strCnt = if(select) "${cnt}" else "n"
        filterList.chkConsecutiveNumber.text = String.format(mContext.getString(R.string.filter_consecutive_number), strCnt)
    }
}