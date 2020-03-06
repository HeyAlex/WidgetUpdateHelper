package heyalex.widgethelper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.RemoteViews;

import org.jetbrains.annotations.NotNull;


/**
 * Interface for updating widget
 * Make annotation {@link RemoteViewsUpdater} on {@link android.appwidget.AppWidgetProvider} with
 * value of class that implements this interface
 */
public abstract class WidgetUpdater {

    /**
     * Channel Id of default {@link Notification}
     */
    static final String NOTIFICATION_CHANNEL_ID = "widget updates";

    /**
     * Channel Name of default {@link Notification}
     */
    static final String channelName = "Update Widget";

    /**
     * Update widget by passing {@link RemoteViews} and id into
     * {@link android.appwidget.AppWidgetManager#updateAppWidget(int, RemoteViews)}
     * <pre>{@code AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
     * for (int widgetId:ids) {
     *      appWidgetManager.updateAppWidget(widgetId, views);
     * }}</pre>
     * <p>
     * This callback will be running on background thread (IntentService or Foreground IntentService (it's Oreo or higher))
     *
     * @param context    context for getting instance of {@link android.appwidget.AppWidgetManager}
     * @param dataBundle {@link Bundle} where you can get data that you need for building RemoteViews
     * @param ids        widget ids that will be updated
     */
    public abstract void update(@NonNull Context context, @Nullable Bundle dataBundle, int... ids);

    /**
     * This callback also will be running on background thread (IntentService or Foreground IntentService (it's Oreo or higher))
     *
     * @param context context for building {@link Notification}
     * @return {@link Notification} which will be shown on Android Oreo devices and higher
     */
    @TargetApi(Build.VERSION_CODES.O)
    public Notification makeNotification(@NonNull Context context) {
        return NotificationDelegate.getNotification(context);
    }
}
