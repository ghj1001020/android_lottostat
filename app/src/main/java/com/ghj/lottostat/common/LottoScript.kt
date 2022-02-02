package com.ghj.lottostat.common

object LottoScript {

    // 일치하는 숫자 갯수
    fun Array<Int>.getMatchCount(other: Array<Int>) : Int {
        var count = 0
        for (num in other) {
            if( this.contains(num) ) {
                count++
            }
        }
        return count
    }

    // 연속하는 갯수
    fun Array<Int>.getConsecutiveCount() : Int {
        var count = 0
        var tempCount = 0
        var temp = -1
        for( idx in this.indices) {
            // 두수 사이 간격이 1이면 이전번호와 연속된 수
            if( Math.abs(temp-this[idx]) <= 1 ) {
                tempCount++
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
}