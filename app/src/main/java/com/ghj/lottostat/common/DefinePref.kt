package com.ghj.lottostat.common

object DefinePref {

    const val IS_COPY_SQLITE = "pref_is_copy_sqlite"

    // 이전 당첨번호 n개 이상 일치시 제외
    const val IS_EXCLUDE_PREV_WIN_NUMBER = "pref_is_exclude_prev_win_number"
    const val CNT_EXCLUDE_PREV_WIN_NUMBER = "pref_cnt_exclude_prev_win_number"
    const val IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS = "pref_is_exclude_prev_win_number_with_bonus"
    // 초기값
    const val DFT_IS_EXCLUDE_PREV_WIN_NUMBER = true
    const val DFT_CNT_EXCLUDE_PREV_WIN_NUMBER = 4
    const val DFT_IS_EXCLUDE_PREV_WIN_NUMBER_WITH_BONUS = true

    // 이전 회차 번호 중 n개 이상 포함
    const val IS_INCLUDE_LAST_ROUND_WIN_NUMBER = "pref_is_include_last_round_win_number"
    const val CNT_INCLUDE_LAST_ROUND_WIN_NUMBER = "pref_cnt_include_last_round_win_number"
    const val IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS = "pref_is_include_last_round_win_number_with_bonus"
    // 초기값
    const val DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER = true
    const val DFT_CNT_INCLUDE_LAST_ROUND_WIN_NUMBER = 1
    const val DFT_IS_INCLUDE_LAST_ROUND_WIN_NUMBER_WITH_BONUS = true

    // n개 이상 연속된 수 제외
    const val IS_EXCLUDE_CONSECUTIVE_NUMBER = "pref_is_exclude_consecutive_number"
    const val CNT_EXCLUDE_CONSECUTIVE_NUMBER = "pref_cnt_exclude_consecutive_number"
    // 초기값
    const val DFT_IS_EXCLUDE_CONSECUTIVE_NUMBER = true
    const val DFT_CNT_EXCLUDE_CONSECUTIVE_NUMBER = 2

}