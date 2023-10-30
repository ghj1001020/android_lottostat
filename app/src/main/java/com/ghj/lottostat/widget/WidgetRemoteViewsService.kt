package com.ghj.lottostat.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetRemoteViewsService : RemoteViewsService() {

    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return WidgetRemoteViewsFactory(applicationContext, p0)
    }
}