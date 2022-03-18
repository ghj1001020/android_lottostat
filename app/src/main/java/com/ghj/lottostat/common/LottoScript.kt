package com.ghj.lottostat.common

import android.content.Context
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.PrefUtil
import java.security.SecureRandom

object LottoScript {

    // 로또번호 생성
    // round-생성하는 로또회차, count-생성갯수
    fun generateLottoNumberList(context: Context, round: Int, count: Int) : ArrayList<ArrayList<Int>> {
        val resultList : ArrayList<ArrayList<Int>> = arrayListOf()

        val isLastRoundWinNumber = PrefUtil.getInstance(context).getBoolean(LAST_ROUND_WIN_NUMBER.SELECT, LAST_ROUND_WIN_NUMBER.DFT_SELECT)
        val cntLastRoundWinNumber = PrefUtil.getInstance(context).getInt(LAST_ROUND_WIN_NUMBER.CNT, LAST_ROUND_WIN_NUMBER.DFT_CNT)
        val isLastRoundWinNumberWithBonus = PrefUtil.getInstance(context).getBoolean(LAST_ROUND_WIN_NUMBER.BONUS, LAST_ROUND_WIN_NUMBER.DFT_BONUS)

        val isConsecutiveNumber = PrefUtil.getInstance(context).getBoolean(CONSECUTIVE_NUMBER.SELECT, CONSECUTIVE_NUMBER.DFT_SELECT)
        val cntConsecutiveNumber = PrefUtil.getInstance(context).getInt(CONSECUTIVE_NUMBER.CNT, CONSECUTIVE_NUMBER.DFT_CNT)

        var index : Int = 0

        // count 개수만큼 당첨번호 생성
        while(index < count) {
            // 추천로또번호 생성
            val LOTTO = arrayListOf<Int>()
            // 로또번호 1~45
            val GROUP : ArrayList<Int> = arrayListOf()
            GROUP.addAll(DefineCode.LOTTERY)

            // 이전 회차 번호 중 n개 일치
            if( isLastRoundWinNumber && round > 1) {
                val lastRound = SQLiteService.selectRoundWinNumber(context, isLastRoundWinNumberWithBonus, round-1)
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

            var isConsecutiveExecuted = false
            while ( LOTTO.size < 6 ) {
                // n개 연속된수
                if(isConsecutiveNumber && cntConsecutiveNumber == (6-LOTTO.size)) {
                    if(!isConsecutiveExecuted) {
                        LOTTO.generateConsecutiveNumber(GROUP, cntConsecutiveNumber+1)
                        isConsecutiveExecuted = true
                    }
                }

                // 번호추천 결과담고 모그룹에서 삭제
                if( LOTTO.size < 6 ) {
                    val numIndex = SecureRandom().nextInt(GROUP.size)
                    val number = GROUP.get(numIndex)    // 추가할 번호

                    LOTTO.add(number)
                    LOTTO.sort()
                    GROUP.remove(number)

                    // n개 연속된수 체크
                    if(isConsecutiveNumber && (cntConsecutiveNumber+1) < LOTTO.getConsecutiveCount() && GROUP.size > 6-LOTTO.size ) {
                        LOTTO.remove(number)
                    }
                }
            }
            // end 추천로또번호 생성 while

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
        this.sort()

        var count = 1
        var tempCount = 1
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
                tempCount = 1
            }
            temp = this[idx]
        }
        return count
    }

    // 해당방향 연속수 구하기 -1:해당방향의 연속수 없음
    private fun ArrayList<Int>.getConsecutiveNumber(base: Int, group: ArrayList<Int>, direction: Int) : Int {
        var number = base
        // 왼쪽
        if(direction == 0) {
            do {
                number--
            }while( this.contains(number) )
            number = if(group.contains(number)){ number }else{ -1 }
        }
        // 오른쪽
        else if(direction == 1) {
            do {
                number++
            }while( this.contains(number) )
            number - if(group.contains(number)){ number }else{ -1 }
        }
        return number
    }

    // 연속수 구하기 기본수 유효성 체크
    private fun checkConsecutiveBaseNumber(base: Int, lotto: ArrayList<Int>, group: ArrayList<Int>, consecutive: Int) : Boolean {
        if(consecutive == 2) {
            return !lotto.contains(base-2) && group.contains(base-1) && !lotto.contains(base+1)
                    || !lotto.contains(base-1) && group.contains(base+1) && !lotto.contains(base+2)
        }
        else if(consecutive == 3) {
            return !lotto.contains(base-3) && group.contains(base-2) && group.contains(base-1) && !lotto.contains(base+1)
                    || !lotto.contains(base-2) && group.contains(base-1) && group.contains(base+1) && !lotto.contains(base+2)
                    || !lotto.contains(base-1) && group.contains(base+1) && group.contains(base+2) && !lotto.contains(base+3)
        }
        else if(consecutive == 4) {
            return !lotto.contains(base-4) && group.contains(base-3) && group.contains(base-2) && group.contains(base-1) && !lotto.contains(base+1)
                    || !lotto.contains(base-3) && group.contains(base-2) && group.contains(base-1) && group.contains(base+1) && !lotto.contains(base+2)
                    || !lotto.contains(base-2) && group.contains(base-1) && group.contains(base+1) && group.contains(base+2) && !lotto.contains(base+3)
                    || !lotto.contains(base-1) && group.contains(base+1) && group.contains(base+2) && group.contains(base+3) && !lotto.contains(base+4)
        }
        return false
    }

    // 연속수 구하기
    fun ArrayList<Int>.generateConsecutiveNumber(group: ArrayList<Int>, consecutive: Int) {
        if( this.size >= 6 || consecutive == 0 || consecutive <= this.getConsecutiveCount() ) return

        this.sort()

        // 기준수
        var number = -1
        var _baseArr = arrayListOf<Int>()
        _baseArr.addAll(this)
        while(_baseArr.size > 0) {
            val numIndex =  SecureRandom().nextInt(_baseArr.size)
            number = _baseArr[numIndex]
            val isFind = checkConsecutiveBaseNumber(number, this, group, consecutive)
            if(isFind)
                break
            _baseArr.remove(number)
            number = -1
        }
        if(number <= 0) {
            return // 기준수 없으면 종료
        }

        var lNumber = 0
        var rNumber = 0
        while(this.getConsecutiveCount() < consecutive && this.count() < 6) {
            lNumber = getConsecutiveNumber(number, group, 0)
            rNumber = getConsecutiveNumber(number, group, 1)

            val direction = SecureRandom().nextInt(2)
            if( lNumber == -1 && rNumber == -1 ) {
                break
            }
            // 왼쪽선택
            if(direction == 0 && lNumber != -1) {
                this.add(lNumber)   // 왼쪽추가
                if(consecutive < this.getConsecutiveCount()) {
                    // 연속개수가 초과하면 지우고 오른쪽선택
                    this.remove(lNumber)
                    this.add(rNumber)
                }
            }
            else if(direction == 0 && rNumber != -1) {
                this.add(rNumber)   // 무조건 오른쪽추가
            }
            // 오른쪽선택
            else if(direction == 1 && rNumber != -1) {
                this.add(rNumber)
                if(consecutive < this.getConsecutiveCount()) {
                    // 연속개수가 초과하면 지우고 왼쪽선택
                    this.remove(rNumber)
                    this.add(lNumber)
                }
            }
            else if(direction == 1 && lNumber != -1) {
                this.add(lNumber)   // 무조건 왼쪽추가
            }
        }
    }
}
