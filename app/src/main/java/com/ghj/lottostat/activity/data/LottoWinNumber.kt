package com.ghj.lottostat.activity.data

data class LottoWinNumber(val no: Int, val win1: Int, val win2: Int, val win3: Int, val win4: Int, val win5: Int, val win6: Int, val bonus: Int) {

    // 특정번호 한개가 포함되어있는지 확인
    fun checkContainNumWithBonus( num: Int ) : Boolean {
        return win1==num || win2==num || win3==num || win4==num || win5==num || win6==num || bonus==num
    }

    // 특정번호전체가 포함되어있는지 확인
    fun checkContainNumWithBonus( numbers: ArrayList<Int> ) : Boolean {
        if( numbers.size < 6 ) return false

        return checkContainNumWithBonus(numbers.get(0)) && checkContainNumWithBonus(numbers.get(1))
                && checkContainNumWithBonus(numbers.get(2)) && checkContainNumWithBonus(numbers.get(3))
                && checkContainNumWithBonus(numbers.get(4)) && checkContainNumWithBonus(numbers.get(5))
    }
}