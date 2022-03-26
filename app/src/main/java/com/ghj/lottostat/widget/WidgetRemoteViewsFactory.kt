package com.ghj.lottostat.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.ghj.lottostat.R

class WidgetRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var infoList = arrayListOf<String>()

    override fun onCreate() {
        infoList.add("이전 회차 번호 중 %s개 일치")
        infoList.add("%s개 연속된 수 포함")
        infoList.add("%s개 연속된 수 포함")
    }

    override fun getViewAt(index: Int): RemoteViews {
        return RemoteViews(context.packageName, R.layout.row_appwidget_info)
            .apply {
                this.setTextViewText(R.id.txtInfo, infoList[index])
            }
    }


    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.row_appwidget_info)
    }

    override fun onDestroy() {
        infoList.clear()
    }

    override fun getCount(): Int {
        return infoList.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {

    }
}