package com.ghj.lottostat.common

object DefineQuery {

    // 로또당첨번호 목록 조회
    val SELECT_LOTTO_WIN_NUMBER = "SELECT NO, WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                  "FROM   LOTTO_WIN_NUMBER"
}