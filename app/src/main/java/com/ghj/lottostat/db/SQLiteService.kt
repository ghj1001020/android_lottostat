package com.ghj.lottostat.db

import android.content.Context
import android.database.Cursor
import com.ghj.lottostat.activity.data.LottoWinNumber
import com.ghj.lottostat.common.DefineQuery

object SQLiteService {

    // 로또당첨번호 가져오기
    fun selectLottoWinNumber(context: Context) : ArrayList<LottoWinNumber> {
        val winList : ArrayList<LottoWinNumber> = arrayListOf()

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_LOTTO_WIN_NUMBER) { cursor: Cursor ->
            while( cursor.moveToNext() ) {
                val no = cursor.getInt( cursor.getColumnIndex("NO") )
                val win1 = cursor.getInt( cursor.getColumnIndex("WIN1") )
                val win2 = cursor.getInt( cursor.getColumnIndex("WIN2") )
                val win3 = cursor.getInt( cursor.getColumnIndex("WIN3") )
                val win4 = cursor.getInt( cursor.getColumnIndex("WIN4") )
                val win5 = cursor.getInt( cursor.getColumnIndex("WIN5") )
                val win6 = cursor.getInt( cursor.getColumnIndex("WIN6") )
                val bonus = cursor.getInt( cursor.getColumnIndex("BONUS") )

                winList.add( LottoWinNumber(no, win1, win2, win3, win4, win5, win6, bonus) )
            }
        }
        SQLite.close()

        return winList
    }

    // 로또당첨번호 여부
    fun selectIsLottoWinNumber(context: Context, number: MutableList<Int>) : Boolean {
        var result = false

        val param : Array<String> = arrayOf(number.get(0).toString(),
                                            number.get(1).toString(),
                                            number.get(2).toString(),
                                            number.get(3).toString(),
                                            number.get(4).toString(),
                                            number.get(5).toString())

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_IS_LOTTO_WIN_NUMBER, param) { cursor: Cursor ->
            cursor.moveToNext()
            val cnt = cursor.getInt( cursor.getColumnIndex("CNT") )
            result = cnt > 0
        }
        SQLite.close()

        return result
    }

    // 마지막 로또번호 조회
    fun selectPrevRoundWinNumber(context: Context, isBonus: Boolean) : ArrayList<Int>  {
        val result : ArrayList<Int> = arrayListOf()

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_LAST_ROUND_WIN_NUMBER) {cursor: Cursor ->
            cursor.moveToNext()
            result.add( cursor.getInt( cursor.getColumnIndex("WIN1") ) )
            result.add( cursor.getInt( cursor.getColumnIndex("WIN2") ) )
            result.add( cursor.getInt( cursor.getColumnIndex("WIN3") ) )
            result.add( cursor.getInt( cursor.getColumnIndex("WIN4") ) )
            result.add( cursor.getInt( cursor.getColumnIndex("WIN5") ) )
            result.add( cursor.getInt( cursor.getColumnIndex("WIN6") ) )
            if( isBonus ) {
                result.add( cursor.getInt( cursor.getColumnIndex("BONUS") ) )
            }
        }
        SQLite.close()

        return result
    }
}