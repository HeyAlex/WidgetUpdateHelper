package helper.update.widget.com.example.simple.common;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import helper.update.widget.com.example.simple.single.ExampleSingleAppWidget;
import helper.update.widget.com.example.simple.single.SingleRemoteViewBuilder;
import heyalex.widgethelper.RemoteViewsUpdater;
import heyalex.widgethelper.WidgetUpdateService;

@RemoteViewsUpdater(CommonUpdater.class)
public class ExampleCommonAppWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Bundle bundle = new Bundle();
        bundle.putString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION);
        bundle.putString("main_text", SingleRemoteViewBuilder.NEXT_TEXT);
        WidgetUpdateService.updateWidgets(context,
                this, bundle, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, ExampleSingleAppWidget.class));

        Bundle bundle = new Bundle();
        bundle.putString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION);
        bundle.putString("main_text", SingleRemoteViewBuilder.NEXT_TEXT);
        WidgetUpdateService.updateWidgets(context,
                this, bundle, widgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //handle actions from RemoteViews button, or you can start service directly like in
        // ExampleSingleAppWidgetProvider
        if (intent != null) {
            if (intent.getAction().equals(CommonRemoteViewBuilder.ACTION_BUTTON)) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] widgetIds = appWidgetManager.getAppWidgetIds(
                        new ComponentName(context, ExampleSingleAppWidget.class));
                WidgetUpdateService.updateWidgets(context, this,
                        intent.getBundleExtra(WidgetUpdateService.EXTRA_DATA_BUNDLE), widgetIds);
            } else {
                super.onReceive(context, intent);
            }
        }
    }
}
