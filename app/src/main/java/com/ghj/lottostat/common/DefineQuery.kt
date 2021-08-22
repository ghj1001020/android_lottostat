package com.ghj.lottostat.common

object DefineQuery {

    // 로또당첨번호 목록 조회
    val SELECT_LOTTO_WIN_NUMBER = "SELECT NO, WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                  "FROM   LOTTO_WIN_NUMBER"

    // 로또당첨번호 여부 조회
    val SELECT_IS_LOTTO_WIN_NUMBER = "SELECT COUNT(no) as CNT   " +
                                     "FROM   LOTTO_WIN_NUMBER "   +
                                     "WHERE  WIN1=? AND WIN2=? AND WIN3=? AND WIN4=? AND WIN5=? AND WIN6=?"

    // 마지막 회차 로또당첨번호 조회
    val SELECT_LAST_ROUND_WIN_NUMBER = "SELECT   WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                       "FROM     LOTTO_WIN_NUMBER " +
                                       "ORDER BY NO DESC "          +
                                       "LIMIT    1"
}