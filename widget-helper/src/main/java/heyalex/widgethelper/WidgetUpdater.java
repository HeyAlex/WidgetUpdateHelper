package heyalex.widgethelper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;


/**
 * Interface for updating widget
 * Make annotation {@link RemoteViewsUpdater} on {@link android.appwidget.AppWidgetProvider} with
 * value of class that implements this interface
 */
public abstract class WidgetUpdater {
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
    public abstract void update(@NonNull Context context, @Nullable Bundle dataBundle, int... ids);

    @TargetApi(Build.VERSION_CODES.O)
    public Notification makeNotification(@NonNull Context context) {
        String NOTIFICATION_CHANNEL_ID = "widget updates";
        String channelName = "Update Widget";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(chan);
        }

        Notification.Builder notificationBuilder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID);
        return notificationBuilder.build();
    }
}
