package helper.update.widget.com.example.single;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import heyalex.widgethelper.RemoteViewsUpdater;
import heyalex.widgethelper.UpdateService;

@RemoteViewsUpdater(SingleUpdater.class)
public class ExampleSingleAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            Bundle bundle = new Bundle();
            bundle.putString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION + String.valueOf(widgetId));
            bundle.putString("main_text", SingleRemoteViewBuilder.NEXT_TEXT);
            UpdateService.updateWidgets(context, ExampleSingleAppWidget.class, bundle, widgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, ExampleSingleAppWidget.class));

        for (int widgetId : widgetIds) {
            Bundle bundle = new Bundle();
            bundle.putString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION + String.valueOf(widgetId));
            bundle.putString("main_text", SingleRemoteViewBuilder.PREVIOUS_TEXT);

            UpdateService.updateWidgets(context, ExampleSingleAppWidget.class, bundle, widgetId);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

