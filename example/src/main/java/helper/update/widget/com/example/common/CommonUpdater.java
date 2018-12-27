package helper.update.widget.com.example.common;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import helper.update.widget.com.example.single.SingleRemoteViewBuilder;
import heyalex.widgethelper.WidgetUpdater;

public class CommonUpdater extends WidgetUpdater {

    @Override
    public void update(@NonNull Context context, Bundle dataBundle, int... ids) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //get action for our RemoteViews button
        String action = dataBundle.getString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION);
        //get text for our RemoteViews
        String text = dataBundle.getString("main_text", "");
        RemoteViews remoteViews = null;
        //get all widget ids
        ids = appWidgetManager.getAppWidgetIds(new ComponentName(context,
                ExampleCommonAppWidget.class));
        //make RemoteView depends on action
        if (action.equals(SingleRemoteViewBuilder.NEXT_ACTION)) {
            remoteViews = new CommonRemoteViewBuilder(context, ids).getSecondView(text);

        } else if (action.equals(SingleRemoteViewBuilder.PREVIOUS_ACTION)) {
            remoteViews = new CommonRemoteViewBuilder(context, ids).getFirstView(text);
        }
        //update all widgets
        for (int id : ids) {
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }
}