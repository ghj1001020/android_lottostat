package com.ghj.lottostat.activity.data

enum class ListType {
    OPEN ,
    CLOSE
}

enum class WinRate {
    NONE,
    WIN1PLACE,
    WIN2PLACE,
    WIN3PLACE,
    WIN4PLACE,
    WIN5PLACE
}

data class LottoWinNumber(
    var type: ListType, var no: Int, val date: String,
    val win1: Int, val win2: Int, val win3: Int, val win4: Int, val win5: Int, val win6: Int, val bonus: Int,
    val place1Cnt: String, val place1Amt: String, val place2Cnt: String, val place2Amt: String, val place3Cnt: String, val place3Amt: String,
    val place4Cnt: String, val place4Amt: String, val place5Cnt: String, val place5Amt: String) {

    constructor(no: Int, win1: Int, win2: Int, win3: Int, win4: Int, win5: Int, win6: Int, bonus: Int)
            : this(ListType.OPEN, no, "", win1, win2, win3, win4, win5, win6, bonus, "", "", "", "", "", "", "", "", "", "" )


    // 번호리스트
    fun getNumberList(isBonus: Boolean=true) : ArrayList<Int> {
        if (isBonus) {
            val list = arrayListOf<Int>(win1, win2, win3, win4, win5, win6, bonus)
            list.sorted()
            return list
        } else {
            return arrayListOf<Int>(win1, win2, win3, win4, win5, win6)
        }
    }

    // 결과계산
    fun getWinningResult(numbers: ArrayList<Int>) : WinRate {
        val winning = arrayListOf<Int>(this.win1, this.win2, this.win3, this.win4, this.win5, this.win6)
        if( numbers.size != 6 ) return WinRate.NONE

        var matchCtn = 0
        for( num in numbers ) {
            if( winning.contains(num) ) {
                matchCtn += 1
            }
        }

        when {
            // 1등
            matchCtn == 6 -> {
                return WinRate.WIN1PLACE
            }
            // 2등
            matchCtn == 5 && numbers.contains(this.bonus) -> {
                return WinRate.WIN2PLACE
            }
            // 3등
            matchCtn == 5 -> {
                return WinRate.WIN3PLACE
            }
            // 4등
            matchCtn == 4 -> {
                return WinRate.WIN4PLACE
            }
            // 5등
            matchCtn == 3 -> {
                return WinRate.WIN5PLACE
            }
            else -> {
                return WinRate.NONE
            }
        }
    }
}