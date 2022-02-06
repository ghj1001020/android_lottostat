package com.ghj.lottostat.common

import android.content.Context
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.PrefUtil
import java.security.SecureRandom

object LottoScript {

    // 로또번호 생성
    fun generateLottoNumberList(context: Context, count: Int) : ArrayList<ArrayList<Int>> {
        val resultList : ArrayList<ArrayList<Int>> = arrayListOf()

        val isLastRoundWinNumber = PrefUtil.getInstance(context).getBoolean(LAST_ROUND_WIN_NUMBER.SELECT, LAST_ROUND_WIN_NUMBER.DFT_SELECT)
        val cntLastRoundWinNumber = PrefUtil.getInstance(context).getInt(LAST_ROUND_WIN_NUMBER.CNT, LAST_ROUND_WIN_NUMBER.DFT_CNT)
        val isLastRoundWinNumberWithBonus = PrefUtil.getInstance(context).getBoolean(LAST_ROUND_WIN_NUMBER.BONUS, LAST_ROUND_WIN_NUMBER.DFT_BONUS)

        val isConsecutiveNumber = PrefUtil.getInstance(context).getBoolean(CONSECUTIVE_NUMBER.SELECT, CONSECUTIVE_NUMBER.DFT_SELECT)
        var cntConsecutiveNumber = PrefUtil.getInstance(context).getInt(CONSECUTIVE_NUMBER.CNT, CONSECUTIVE_NUMBER.DFT_CNT)

        var index : Int = 0

        while(index < count) {

            // 번호생성
            val LOTTO = arrayListOf<Int>()  // 추천 로또번호
            // 로또번호 1~45
            val GROUP : ArrayList<Int> = arrayListOf()
            GROUP.addAll(DefineCode.LOTTERY)

            // 이전 회차 번호 중 n개 일치
            if( isLastRoundWinNumber ) {
                val lastRound = SQLiteService.selectLastRoundWinNumber(context, isLastRoundWinNumberWithBonus)
                // 0 <= idx < cntIncludeLastRoundWinNumber
                for( idx in 0 until cntLastRoundWinNumber) {
                    // 인덱스 구해서 추천번호 뽑기
                    val tempIndex = SecureRandom().nextInt(lastRound.size )
                    val goodNumber : Int = lastRound.get(tempIndex)
                    LOTTO.add(goodNumber)
                    // 당첨번호에서 추가한 번호삭제
                    lastRound.removeAt(tempIndex)
                    GROUP.remove(goodNumber)
                }

                // 나머지 당첨번호 삭제
                for( num in lastRound ) {
                    GROUP.remove(num)
                }
            }

            var isOverConsecutiveNumber = false   // 이미 제한된 연속수가 넘어갔는지 여부
            if( isConsecutiveNumber ) {
                // 뽑을수 있는 수보다 연속해야 하는 숫자가 더 크면 뽑을수 있는 수만큼만 연속해야 한다
                if( cntConsecutiveNumber > (6-LOTTO.size) ) {
                    cntConsecutiveNumber = 6-LOTTO.size
                }

                if( LOTTO.getConsecutiveCount() > cntConsecutiveNumber ) {
                    isOverConsecutiveNumber = true
                }
            }

            while ( LOTTO.size < 6 ) {

                // 번호추천
                val numIndex = SecureRandom().nextInt(GROUP.size)
                val number = GROUP.get(numIndex)

                LogUtil.d("generateLottoNumberList1 >> ${LOTTO}")

                // n개 연속된 수
                if( isConsecutiveNumber && cntConsecutiveNumber == (6-LOTTO.size) ) {
                    LOTTO.generateConsecutiveNumber(GROUP, cntConsecutiveNumber)
                }

                // 추천번호 결과담고 모그룹에서 삭제
                if( LOTTO.size < 6 ) {
                    LOTTO.add( number )
                    LOTTO.sort()
                    GROUP.removeAt( numIndex )
                }

                LogUtil.d("generateLottoNumberList2 >> ${LOTTO}")

                // n개 연속된 수
                if( isConsecutiveNumber && cntConsecutiveNumber == (6-LOTTO.size) ) {
                    LOTTO.generateConsecutiveNumber(GROUP, cntConsecutiveNumber)
                }

                // n개 연속된 수 필터 체크
                if( isConsecutiveNumber && !isOverConsecutiveNumber && LOTTO.getConsecutiveCount() > cntConsecutiveNumber ) {
                    LOTTO.remove( number )
                    GROUP.remove( number )
                }
            }

            // 번호 추천 목록에 담기
            resultList.add(LOTTO)

            // 인덱스 1추가
            index++
        }

        return resultList
    }



    // 일치하는 숫자 갯수
    fun ArrayList<Int>.getMatchCount(other: ArrayList<Int>) : Int {
        var count = 0
        for (num in other) {
            if( this.contains(num) ) {
                count++
            }
        }
        return count
    }

    // 연속하는 갯수
    fun ArrayList<Int>.getConsecutiveCount() : Int {
        var count = 0
        var tempCount = 0
        var temp = -1
        for( idx in this.indices) {
            // 두수 사이 간격이 1이면 이전번호와 연속된 수
            if( Math.abs(temp-this[idx]) <= 1 ) {
                tempCount++
                if( tempCount > count ) {
                    count = tempCount
                }
            }
            else {
                if( tempCount > count ) {
                    count = tempCount
                }
                tempCount = 0
            }
            temp = this[idx]
        }
        return count
    }


    // 연속수 구하기
    fun ArrayList<Int>.generateConsecutiveNumber(group: ArrayList<Int>, consecutive: Int) {
        if( consecutive > (6-this.size) || consecutive <= this.getConsecutiveCount() || consecutive == 0 ) return

        val _this : ArrayList<Int> = arrayListOf()
        _this.addAll(this)

        while ( _this.size > 0 ) {
            // 기준수
            val numIndex = SecureRandom().nextInt(_this.size)
            val number = _this.get(numIndex)

            val temp = arrayListOf<Array<Int>>()

            when( consecutive ) {
                // -1, 1
                1 -> {
                    // -1
                    if( 0<=number-2 && !this.contains(number-2) && !this.contains(number+1) && group.contains(number-1) ) {
                        temp.add( arrayOf(number-1) )
                    }
                    // 1
                    if( number+2<=46 && !this.contains(number-1) && !this.contains(number+2) && group.contains(number+1) ) {
                        temp.add( arrayOf(number+1) )
                    }
                }
                // -2, -1 1, 2
                2 -> {
                    // -2
                    if( 0<=number-3 && !this.contains(number-3) && !this.contains(number+1)
                        && (group.contains(number-2)||this.contains(number-2)) && (group.contains(number-1)||this.contains(number-1)) ) {
                        temp.add( arrayOf(number-2, number-1 ) )
                    }
                    // -1 1
                    if( 0<=number-2 && number+2 <= 46 && !this.contains(number-2) && !this.contains(number+2)
                        && (group.contains(number-1)||this.contains(number-1)) && (group.contains(number+1)||this.contains(number+1)) ) {
                        temp.add( arrayOf(number-1, number+1 ) )
                    }
                    // 2
                    if( number+3<=46 && !this.contains(number-1) && !this.contains(number+3)
                        && (group.contains(number+1)||this.contains(number+1)) && (group.contains(number+2)||this.contains(number+2)) ) {
                        temp.add( arrayOf(number+1, number+2 ) )
                    }
                }
                // -3, -2 1, -1 2, 3
                3 -> {
                    // -3
                    if( 0<=number-4 && !this.contains(number-4) && !this.contains(number+1)
                        && (group.contains(number-3)||this.contains(number-3)) && (group.contains(number-2)||this.contains(number-2)) && (group.contains(number-1)||this.contains(number-1)) ) {
                        temp.add( arrayOf(number-3, number-2, number-1 ) )
                    }
                    // -2 1
                    if( 0<=number-3 && number+2<=46 && !this.contains(number-3) && !this.contains(number+2)
                        && (group.contains(number-2)||this.contains(number-2)) && (group.contains(number-1)||this.contains(number-1)) && (group.contains(number+1)||this.contains(number+1)) ) {
                        temp.add( arrayOf(number-2, number-1, number+1 ) )
                    }
                    // -1 2
                    if( 0<=number-2 && number+3<=46 && !this.contains(number-2) && !this.contains(number+3)
                        && (group.contains(number-1)||this.contains(number-1)) && (group.contains(number+1)||this.contains(number+1)) && (group.contains(number+2)||this.contains(number+2)) ) {
                        temp.add( arrayOf(number-1, number+1, number+2 ) )
                    }
                    // 3
                    if( number+4<=46 && !this.contains(number-1) && !this.contains(number+4)
                        && (group.contains(number+1)||this.contains(number+1)) && (group.contains(number+2)||this.contains(number+2)) && (group.contains(number+3)||this.contains(number+3)) ) {
                        temp.add( arrayOf(number+1, number+2, number+3 ) )
                    }
                }
            }

            LogUtil.d("generateConsecutiveNumber ${temp.size}")

            if( temp.size > 0) {
                if( temp.size == 1 ) {
                    this.addAll(temp.get(0))
                }
                else {
                    val idx = SecureRandom().nextInt(temp.size)
                    this.addAll(temp.get(idx))
                }
                break
            }
            else {
                _this.remove(number)    // 기준수바꾸기
            }
        }

        val list = this.distinct()    // 중복제거
        this.clear()
        this.addAll(list)

        this.sort()
    }
}