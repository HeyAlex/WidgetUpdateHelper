package helper.update.widget.com.example.simple.list;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import helper.update.widget.com.example.R;
import heyalex.widgethelper.WidgetUpdater;

public class ListRemoteViewUpdater extends WidgetUpdater {
    @Override
    public void update(Context context, Bundle dataBundle, int... ids) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ids = appWidgetManager.getAppWidgetIds(new ComponentName(context,
                ListAppWidget.class));

        //update all widgets
        for (int id : ids) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_list_app_widget);
            Intent adapter = new Intent(context, SampleRemoteService.class);
            Uri data = Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME));
            adapter.setData(data);
            remoteViews.setRemoteAdapter(R.id.list, adapter);
            appWidgetManager.updateAppWidget(id, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(id, R.id.list);
        }
    }
}
