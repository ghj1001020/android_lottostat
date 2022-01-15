package com.ghj.lottostat.activity.data

enum class MyLottoType {
    ROUND_OPEN ,
    ROUND_CLOSE ,
    REG_DATE ,
    LOTTO
}

data class MyLottoNumberData(var type: MyLottoType,
                             val roundNo: Int,
                             val regDate: String,
                             val number1: Int,
                             val number2: Int,
                             val number3: Int,
                             val number4: Int,
                             val number5: Int,
                             val number6: Int) {

    constructor(type: MyLottoType, roundNo: Int, regDate: String)
            : this(type, roundNo, regDate, 0,0,0,0,0,0)

    constructor(type: MyLottoType, roundNo: Int) : this(type, roundNo, "")
}