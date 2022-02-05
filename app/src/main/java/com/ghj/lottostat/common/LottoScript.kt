package com.ghj.lottostat.common

import com.ghj.lottostat.util.LogUtil
import java.security.SecureRandom

object LottoScript {

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

            LogUtil.d("generateConsecutiveNumber ${temp} ${_this} ${number}")
        }

        this.distinct()    // 중복제거
        this.sort()
    }
}