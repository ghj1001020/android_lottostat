package com.ghj.lottostat.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.IntroActivity
import com.ghj.lottostat.common.LinkParam
import com.ghj.lottostat.common.LottoScript
import com.ghj.lottostat.common.WidgetParam
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.util.LogUtil
import com.ghj.lottostat.util.Util

class WidgetProvider : AppWidgetProvider() {

    // 브로드캐스트 아이드
    val REQ_ID_REFRESH = 10000
    val REQ_ID_RECOMMEND = 10001


    // 브로드캐스트 리시버
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if( context == null || intent == null ) return

        val action = intent.action
        LogUtil.d("WidgetProvider action = $action")
        Toast.makeText(context, "onReceive $action", Toast.LENGTH_SHORT).show()

        if( Intent.ACTION_MY_PACKAGE_REPLACED.equals(action) ) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            onUpdate( context, appWidgetManager, appWidgetManager.getAppWidgetIds(ComponentName(context, javaClass)) )
        }
        else if( WidgetParam.WIDGET_REFRESH.equals(action) ) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            onUpdate( context, appWidgetManager, appWidgetManager.getAppWidgetIds(ComponentName(context, javaClass)) )
        }
    }

    // 위젯 업데이트 주기, 위젯 구성완료
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show()

        if(context == null)
            return

        val remoteViews = makeWidgetUI(context)
        appWidgetIds?.forEach { _id ->
            // 리스트뷰
            val listIntent = Intent(context, WidgetRemoteViewsService::class.java).apply {
                this.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, _id)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }
            remoteViews.apply {
                this.setRemoteAdapter(R.id.listInfo, listIntent)
                this.setEmptyView(R.id.listInfo, R.id.txtEmpty)
            }
            // 뷰
            appWidgetManager?.updateAppWidget(_id, remoteViews)
        }
    }

    // 위젯 UI만들기
    fun makeWidgetUI(context: Context) : RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.appwidget)
            .apply {
                // 번호추천
                val recommendPendingIntent = moveToAppPendingIntent(context, LinkParam.RECOMMEND, REQ_ID_RECOMMEND)
                setOnClickPendingIntent(R.id.btnMoreRecommend, recommendPendingIntent)

                // 새로고침
                val refreshPendingIntent = getBroadcastPendingIntent(context, WidgetParam.WIDGET_REFRESH, REQ_ID_REFRESH)
                setOnClickPendingIntent(R.id.btnRefresh, refreshPendingIntent)
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

    // 클릭이벤트를 위한 브로드캐스트 PendingIntent
    fun getBroadcastPendingIntent(context: Context, action: String, reqId: Int) : PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        intent.flags = Intent.FLAG_RECEIVER_FOREGROUND
        return PendingIntent.getBroadcast(context, reqId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    // 클릭이벤트를 위한 액티비티 PendingIntent
    fun moveToAppPendingIntent(context: Context, linkCode: Int, reqId: Int) : PendingIntent {
        val intent = Intent(context, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(LinkParam.LINK, linkCode)
        return PendingIntent.getActivity(context, reqId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
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