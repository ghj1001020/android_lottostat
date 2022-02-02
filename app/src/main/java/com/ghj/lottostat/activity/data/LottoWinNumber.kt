package com.ghj.lottostat.activity.data

enum class ListType {
    OPEN ,
    CLOSE
}

data class LottoWinNumber(
    var type: ListType, var no: Int, val date: String,
    val win1: Int, val win2: Int, val win3: Int, val win4: Int, val win5: Int, val win6: Int, val bonus: Int,
    val place1Cnt: String, val place1Amt: String, val place2Cnt: String, val place2Amt: String, val place3Cnt: String, val place3Amt: String,
    val place4Cnt: String, val place4Amt: String, val place5Cnt: String, val place5Amt: String) {

    // 번호리스트
    fun getNumberList(isBonus: Boolean=true) : Array<Int> {
        if(isBonus) {
            val list = arrayOf(win1, win2, win3, win4, win5, win6, bonus)
            list.sorted()
            return list
        }
        else {
            return arrayOf(win1, win2, win3, win4, win5, win6)
        }
    }

    // 일치하는 숫자 갯수
    fun getMatchCount(other: ArrayList<Int>, isBonus: Boolean=true) : Int {
        var count = 0
        for (num in other) {
            if (isBonus) {
                if (win1 == num || win2 == num || win3 == num || win4 == num || win5 == num || win6 == num || bonus == num) count++
            } else {
                if (win1 == num || win2 == num || win3 == num || win4 == num || win5 == num || win6 == num) count++
            }
        }
        return count
    }
}