package com.ghj.lottostat.common

object DefineQuery {

    // My로또번호 테이블 생성
    val CREATE_MY_LOTTO_TABLE = "CREATE TABLE IF NOT EXISTS MY_LOTTO_TBL ( " +
            "     NO_ROUND    INTEGER NOT NULL , " +
            "     REG_DATE VARCHAR(14) NOT NULL , " +
            "     NUM1     INTEGER NOT NULL , " +
            "     NUM2     INTEGER NOT NULL , " +
            "     NUM3     INTEGER NOT NULL , " +
            "     NUM4     INTEGER NOT NULL , " +
            "     NUM5     INTEGER NOT NULL , " +
            "     NUM6     INTEGER NOT NULL " +
            ");"

    // My로또번호 테이블 삭제
    val DROP_MY_LOTTO_TABLE = "DROP TABLE IF EXISTS MY_LOTTO_TBL"

    // My로또번호 데이터 입력
    val INSERT_MY_LOTTO = "INSERT INTO MY_LOTTO_TBL(NO_ROUND, REG_DATE, NUM1, NUM2, NUM3, NUM4, NUM5, NUM6) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"

    // My로또번호 회차그룹 조회
    val SELECT_MY_LOTTO_ROUND = "SELECT   NO_ROUND as NO " +
            "FROM     MY_LOTTO_TBL " +
            "GROUP BY NO_ROUND"

    // My로또번호 데이터 조회
    val SELECT_MY_LOTTO_NUMBER = "SELECT   NO_ROUND, REG_DATE, NUM1, NUM2, NUM3, NUM4, NUM5, NUM6 " +
            "FROM     MY_LOTTO_TBL " +
            "WHERE    NO_ROUND = ? " +
            "ORDER BY REG_DATE DESC"

    // 로또당첨번호 목록 조회
    val SELECT_LOTTO_WIN_NUMBER = "SELECT   NO, LOTTERY_DATE, WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS, " +
                                  "         PLACE1CNT, PLACE1AMT, PLACE2CNT, PLACE2AMT, PLACE3CNT, PLACE3AMT, PLACE4CNT, PLACE4AMT, PLACE5CNT, PLACE5AMT " +
                                  "FROM     LOTTO_WIN_NUMBER " +
                                  "ORDER BY NO DESC"

    // 로또당첨번호 여부 조회
    val SELECT_IS_LOTTO_WIN_NUMBER = "SELECT COUNT(no) as CNT   " +
                                     "FROM   LOTTO_WIN_NUMBER "   +
                                     "WHERE  WIN1=? AND WIN2=? AND WIN3=? AND WIN4=? AND WIN5=? AND WIN6=?"

    // 마지막 회차 로또당첨번호 조회
    val SELECT_LAST_ROUND_WIN_NUMBER = "SELECT   NO, WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                       "FROM     LOTTO_WIN_NUMBER " +
                                       "ORDER BY NO DESC "          +
                                       "LIMIT    1"

    // 해당 회차 로또당첨번호 조회
    val SELECT_ROUND_WIN_NUMBER = "SELECT WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                  "FROM   LOTTO_WIN_NUMBER "                          +
                                  "WHERE  NO=?"

    // 해당 번호가 포함된 당첨번호 조회
    val SELECT_PREV_WIN_NUMBER_BY_NUM = "SELECT WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                        "FROM   LOTTO_WIN_NUMBER " +
                                        "WHERE  WIN1=? OR WIN2=? OR WIN3=? OR WIN4=? OR WIN5=? OR WIN6=?"

    // 해당 번호가 포함된 당첨번호 조회 (보너스 포함)
    val SELECT_PREV_WIN_NUMBER_BY_NUM_WITH_BONUS = "SELECT WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                                   "FROM   LOTTO_WIN_NUMBER " +
                                                   "WHERE  WIN1=? OR WIN2=? OR WIN3=? OR WIN4=? OR WIN5=? OR WIN6=? OR BONUS=?"

    // 마지막 로또번호 회차
    val SELECT_MAX_NO = "SELECT MAX(NO) as CNT " +
                        "FROM   LOTTO_WIN_NUMBER"
}
