package helper.update.widget.com.widgetupdatehelper;

import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Interface for updating widget
 * Make annotation {@link RemoteViewsUpdater} on {@link android.appwidget.AppWidgetProvider} with
 * value of class that implements this interface
 */
public interface WidgetUpdater {
    /**
     * Update widget by passing {@link RemoteViews} and id into
     * {@link android.appwidget.AppWidgetManager#updateAppWidget(int, RemoteViews)}
     * <pre>{@code AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
     * for (int widgetId:ids) {
     *      appWidgetManager.updateAppWidget(widgetId, views);
     * }}</pre>
     *
     * @param context    context for getting instance of {@link android.appwidget.AppWidgetManager}
     * @param dataBundle {@link Bundle} where you can get data that you need for building RemoteViews
     * @param ids        widget ids that will be updated
     */
    void update(Context context, Bundle dataBundle, int... ids);
}
