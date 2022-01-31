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

}