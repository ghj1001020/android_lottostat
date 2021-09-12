package com.ghj.lottostat.common

object DefineQuery {

    // 추천로또번호 저장 테이블 생성
    val CREATE_RECOMMEND_TABLE = "CREATE TABLE IF NOT EXISTS RECOMMEND_TBL ( " +
                                 "  RECOM_NO    VARCHAR(5)  NOT NULL , " +
                                 "  REG_DATE    VARCHAR(14) NOT NULL , " +
                                 "  WIN1        NUMBER      NOT NULL , " +
                                 "  WIN2        NUMBER      NOT NULL , " +
                                 "  WIN3        NUMBER      NOT NULL , " +
                                 "  WIN4        NUMBER      NOT NULL , " +
                                 "  WIN5        NUMBER      NOT NULL , " +
                                 "  WIN6        NUMBER      NOT NULL   " +
                                 ");"

    // 추천로또번호 저장 테이블 삭제
    val DROP_RECOMMEND_TABLE = "DROP TABLE IF EXISTS RECOMMEND_TBL"

    // 추천로또번호 저장 데이터 입력
    val INSERT_RECOMMEND = "INSERT INTO RECOMMEND_TBL(RECOM_NO, REG_DATE, WIN1, WIN2, WIN3, WIN4, WIN5, WIN6) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"



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

    // 해당 번호가 포함된 당첨번호 조회
    val SELECT_PREV_WIN_NUMBER_BY_NUM = "SELECT WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                        "FROM   LOTTO_WIN_NUMBER " +
                                        "WHERE  WIN1=? OR WIN2=? OR WIN3=? OR WIN4=? OR WIN5=? OR WIN6=?"

    // 해당 번호가 포함된 당첨번호 조회 (보너스 포함)
    val SELECT_PREV_WIN_NUMBER_BY_NUM_WITH_BONUS = "SELECT WIN1, WIN2, WIN3, WIN4, WIN5, WIN6, BONUS " +
                                                   "FROM   LOTTO_WIN_NUMBER " +
                                                   "WHERE  WIN1=? OR WIN2=? OR WIN3=? OR WIN4=? OR WIN5=? OR WIN6=? OR BONUS=?"
}