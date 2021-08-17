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
}