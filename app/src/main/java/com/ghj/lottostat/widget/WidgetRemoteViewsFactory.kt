package com.ghj.lottostat.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.ghj.lottostat.R
import com.ghj.lottostat.common.WidgetParam

class WidgetRemoteViewsFactory(private val context: Context, val intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private var infoList = arrayListOf<String>()

    override fun onCreate() {
        intent?.let {
            val list = it.getStringArrayListExtra(WidgetParam.PARAM_WIDGET_INFO_LIST)
            if(list!=null)
                infoList.addAll(list)
        }
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