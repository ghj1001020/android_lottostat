package com.ghj.lottostat.db

import android.content.Context
import android.database.Cursor
import com.ghj.lottostat.activity.data.*
import com.ghj.lottostat.common.DefineQuery
import com.ghj.lottostat.util.DateUtil.convertDateFormat
import com.ghj.lottostat.util.DateUtil.toString
import java.util.*
import kotlin.collections.ArrayList

object SQLiteService {

    // 로또당첨번호 가져오기
    fun selectLottoWinNumber(context: Context) : ArrayList<LottoWinNumber> {
        val winList : ArrayList<LottoWinNumber> = arrayListOf()

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_LOTTO_WIN_NUMBER) { cursor: Cursor ->
            while( cursor.moveToNext() ) {
                val no = cursor.getInt( cursor.getColumnIndex("NO") )
                val lotteryDate = cursor.getString( cursor.getColumnIndex("LOTTERY_DATE") ).convertDateFormat("yyyy.M.d", "yyyyMMdd")
                val win1 = cursor.getInt( cursor.getColumnIndex("WIN1") )
                val win2 = cursor.getInt( cursor.getColumnIndex("WIN2") )
                val win3 = cursor.getInt( cursor.getColumnIndex("WIN3") )
                val win4 = cursor.getInt( cursor.getColumnIndex("WIN4") )
                val win5 = cursor.getInt( cursor.getColumnIndex("WIN5") )
                val win6 = cursor.getInt( cursor.getColumnIndex("WIN6") )
                val bonus = cursor.getInt( cursor.getColumnIndex("BONUS") )
                val place1Cnt = cursor.getString( cursor.getColumnIndex("PLACE1CNT") ).replace("[^0-9]", "")
                val place1Amt = cursor.getString( cursor.getColumnIndex("PLACE1AMT") ).replace("[^0-9]", "")
                val place2Cnt = cursor.getString( cursor.getColumnIndex("PLACE2CNT") ).replace("[^0-9]", "")
                val place2Amt = cursor.getString( cursor.getColumnIndex("PLACE2AMT") ).replace("[^0-9]", "")
                val place3Cnt = cursor.getString( cursor.getColumnIndex("PLACE3CNT") ).replace("[^0-9]", "")
                val place3Amt = cursor.getString( cursor.getColumnIndex("PLACE3AMT") ).replace("[^0-9]", "")
                val place4Cnt = cursor.getString( cursor.getColumnIndex("PLACE4CNT") ).replace("[^0-9]", "")
                val place4Amt = cursor.getString( cursor.getColumnIndex("PLACE4AMT") ).replace("[^0-9]", "")
                val place5Cnt = cursor.getString( cursor.getColumnIndex("PLACE5CNT") ).replace("[^0-9]", "")
                val place5Amt = cursor.getString( cursor.getColumnIndex("PLACE5AMT") ).replace("[^0-9]", "")

                winList.add( LottoWinNumber( ListType.CLOSE, no, lotteryDate, win1, win2, win3, win4, win5, win6, bonus,
                    place1Cnt, place1Amt, place2Cnt, place2Amt, place3Cnt, place3Amt, place4Cnt, place4Amt, place5Cnt, place5Amt) )
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
    fun selectLastRoundWinNumber(context: Context, isBonus: Boolean) : ArrayList<Int>  {
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

    // 특정 회차 로또당첨번호 조회
    fun selectRoundWinNumber(context: Context, isBonus: Boolean, round: Int) : ArrayList<Int> {
        val result : ArrayList<Int> = arrayListOf()

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_ROUND_WIN_NUMBER, arrayOf("${round}")) {cursor: Cursor ->
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

    // 해당 번호가 포함된 당첨번호 조회
    fun selectPrevWinNumberByNum(context: Context, num: Int, isBonus: Boolean) : ArrayList<MutableList<Int>> {
        val result : ArrayList<MutableList<Int>> = arrayListOf()
        var query : String = DefineQuery.SELECT_PREV_WIN_NUMBER_BY_NUM
        val param : ArrayList<String> = arrayListOf(num.toString(), num.toString(), num.toString(), num.toString(), num.toString(), num.toString())

        SQLite.init(context)
        if( isBonus ) {
            query = DefineQuery.SELECT_PREV_WIN_NUMBER_BY_NUM_WITH_BONUS
            param.add(num.toString())
        }
        SQLite.select(query, param.toTypedArray()) { cursor: Cursor ->
            while( cursor.moveToNext() ) {
                val arrNum : MutableList<Int> = mutableListOf()
                arrNum.add( cursor.getInt(cursor.getColumnIndex("WIN1")) )
                arrNum.add( cursor.getInt(cursor.getColumnIndex("WIN2")) )
                arrNum.add( cursor.getInt(cursor.getColumnIndex("WIN3")) )
                arrNum.add( cursor.getInt(cursor.getColumnIndex("WIN4")) )
                arrNum.add( cursor.getInt(cursor.getColumnIndex("WIN5")) )
                arrNum.add( cursor.getInt(cursor.getColumnIndex("WIN6")) )
                if( isBonus )
                    arrNum.add( cursor.getInt(cursor.getColumnIndex("BONUS")) )

                result.add( arrNum )
            }
        }
        SQLite.close()

        return result
    }

    // 마지막 로또번호 회차
    fun selectMaxNo(context: Context) : Int {
        var result = 0

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_MAX_NO) { cursor: Cursor ->
            cursor.moveToNext()
            result = cursor.getInt( cursor.getColumnIndex("CNT") )
        }
        SQLite.close()

        return result
    }

    // My로또 데이터 저장
    fun insertMyLottoData(context: Context, no: Int, datas: ArrayList<LottoNumberData>) {
        val date = Date().toString("yyyyMMddHHmmss")

        SQLite.init(context)
        for( data in datas ) {
            val param: Array<Any> =
                arrayOf(no, date, data.num1, data.num2, data.num3, data.num4, data.num5, data.num6)
            SQLite.execSQL(DefineQuery.INSERT_MY_LOTTO, param)
        }
        SQLite.close()
    }

    // My로또 회차 조회
    fun selectMyLottoRoundNo(context: Context) : ArrayList<MyLottoNumberData> {
        val result = arrayListOf<MyLottoNumberData>()

        SQLite.init(context)
        // 회차조회
        SQLite.select(DefineQuery.SELECT_MY_LOTTO_ROUND) { cursor: Cursor ->
            while( cursor.moveToNext() ) {
                val roundNo = cursor.getInt( cursor.getColumnIndex("NO") )
                result.add( MyLottoNumberData(MyLottoType.ROUND_CLOSE, roundNo) )
            }
        }

        // 첫번째회차 목록조회
        if( result.size > 0 ) {
            result[0].type = MyLottoType.ROUND_OPEN
            val roundNo = result[0].roundNo
            SQLite.select(DefineQuery.SELECT_MY_LOTTO_NUMBER, arrayOf("${roundNo}")) { cursor: Cursor ->
                var date = ""
                val temp = arrayListOf<MyLottoNumberData>()
                while( cursor.moveToNext() ) {
                    val regDate = cursor.getString( cursor.getColumnIndex("REG_DATE") )
                    val num1 = cursor.getInt( cursor.getColumnIndex("NUM1") )
                    val num2 = cursor.getInt( cursor.getColumnIndex("NUM2") )
                    val num3 = cursor.getInt( cursor.getColumnIndex("NUM3") )
                    val num4 = cursor.getInt( cursor.getColumnIndex("NUM4") )
                    val num5 = cursor.getInt( cursor.getColumnIndex("NUM5") )
                    val num6 = cursor.getInt( cursor.getColumnIndex("NUM6") )

                    if( !date.equals(regDate) ) {
                        temp.add(MyLottoNumberData(MyLottoType.REG_DATE, roundNo, regDate))
                        date = regDate
                    }
                    temp.add(MyLottoNumberData(MyLottoType.LOTTO, roundNo, regDate, num1, num2, num3, num4, num5, num6))
                }
                result.addAll(1, temp)
            }
        }

        SQLite.close()
        return result
    }

    // 해당회차의 My로또 데이터 조회
    fun selectMyLottoData(context: Context, no: Int) : ArrayList<MyLottoNumberData> {
        val result = arrayListOf<MyLottoNumberData>()

        SQLite.init(context)
        SQLite.select(DefineQuery.SELECT_MY_LOTTO_NUMBER, arrayOf("${no}")) { cursor: Cursor ->
            var date = ""
            val temp = arrayListOf<MyLottoNumberData>()
            while( cursor.moveToNext() ) {
                val roundNo = cursor.getInt( cursor.getColumnIndex("NO_ROUND") )
                val regDate = cursor.getString( cursor.getColumnIndex("REG_DATE") )
                val num1 = cursor.getInt( cursor.getColumnIndex("NUM1") )
                val num2 = cursor.getInt( cursor.getColumnIndex("NUM2") )
                val num3 = cursor.getInt( cursor.getColumnIndex("NUM3") )
                val num4 = cursor.getInt( cursor.getColumnIndex("NUM4") )
                val num5 = cursor.getInt( cursor.getColumnIndex("NUM5") )
                val num6 = cursor.getInt( cursor.getColumnIndex("NUM6") )

                if( !date.equals(regDate) ) {
                    temp.add(MyLottoNumberData(MyLottoType.REG_DATE, roundNo, regDate))
                    date = regDate
                }
                temp.add(MyLottoNumberData(MyLottoType.LOTTO, roundNo, regDate, num1, num2, num3, num4, num5, num6))
            }
            result.addAll(temp)
        }
        SQLite.close()

        return result
    }
}