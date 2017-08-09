package helper.update.widget.com.example.single;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Bundle;

import helper.update.widget.com.widgetupdatehelper.WidgetUpdater;

public class SingleUpdater implements WidgetUpdater {
    @Override
    public void update(Context context, Bundle dataBundle, int... ids) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //get action for our RemoteViews button
        String action = dataBundle.getString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION);
        //get text for our RemoteViews
        String text = dataBundle.getString("main_text", "");

        //make RemoteViews depends on action and update widget
        for (int widgetId : ids) {
            if (action.startsWith(SingleRemoteViewBuilder.NEXT_ACTION)) {

                appWidgetManager.updateAppWidget(widgetId,
                        new SingleRemoteViewBuilder(context, widgetId).getSecondView(text));

            } else if (action.startsWith(SingleRemoteViewBuilder.PREVIOUS_ACTION)) {
                appWidgetManager.updateAppWidget(widgetId,
                        new SingleRemoteViewBuilder(context, widgetId).getFirstView(text));
            }
        }
    }
}
