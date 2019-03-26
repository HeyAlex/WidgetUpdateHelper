package helper.update.widget.com.example.simple.list;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;

import heyalex.widgethelper.RemoteViewsUpdater;
import heyalex.widgethelper.WidgetUpdateService;

@RemoteViewsUpdater(ListRemoteViewUpdater.class)
public class ListAppWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.updateWidgets(context,
                this, new Bundle(), appWidgetIds);
    }
}
