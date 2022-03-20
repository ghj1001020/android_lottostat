package com.ghj.lottostat.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.ghj.lottostat.R
import com.ghj.lottostat.common.LottoScript
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.Util

class WidgetProvider : AppWidgetProvider() {


    // 브로드캐스트 리시버
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if( context == null || intent == null ) return

        val action = intent.action
        LogUtil.d("WidgetProvider action = $action")
    }

    // 위젯 업데이트 주기, 위젯 구성완료
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if(context == null)
            return

        val remoteViews = makeWidgetUI(context)

        appWidgetIds?.forEach { _id ->
            // 뷰
            appWidgetManager?.updateAppWidget(_id, remoteViews)
        }
    }

    // 위젯 UI만들기
    fun makeWidgetUI(context: Context) : RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.appwidget)
            .apply {

            }

        val round = SQLiteService.selectMaxNo(context) + 1
        val list = LottoScript.generateLottoNumberList(context, round, 1)
        if(list.size > 0) {
            val data = list.get(0)
            remoteViews.setTextViewText(R.id.txtNum1, "${data.get(0)}")
            remoteViews.setInt(R.id.txtNum1, "setBackgroundResource", Util.getLottoNumberBgResource(data.get(0)))
            remoteViews.setTextViewText(R.id.txtNum2, "${data.get(1)}")
            remoteViews.setInt(R.id.txtNum2, "setBackgroundResource", Util.getLottoNumberBgResource(data.get(1)))
            remoteViews.setTextViewText(R.id.txtNum3, "${data.get(2)}")
            remoteViews.setInt(R.id.txtNum3, "setBackgroundResource", Util.getLottoNumberBgResource(data.get(2)))
            remoteViews.setTextViewText(R.id.txtNum4, "${data.get(3)}")
            remoteViews.setInt(R.id.txtNum4, "setBackgroundResource", Util.getLottoNumberBgResource(data.get(3)))
            remoteViews.setTextViewText(R.id.txtNum5, "${data.get(4)}")
            remoteViews.setInt(R.id.txtNum5, "setBackgroundResource", Util.getLottoNumberBgResource(data.get(4)))
            remoteViews.setTextViewText(R.id.txtNum6, "${data.get(5)}")
            remoteViews.setInt(R.id.txtNum6, "setBackgroundResource", Util.getLottoNumberBgResource(data.get(5)))
        }

        return remoteViews
    }
    
    // 위젯 삭제
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    // 최초 위젯 인스턴스 생성
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    // 마시작 인스턴스까지 삭제
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    // 처음 배치시 위젯 크기 정보
    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}