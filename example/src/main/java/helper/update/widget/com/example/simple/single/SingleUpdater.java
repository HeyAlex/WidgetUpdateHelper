package helper.update.widget.com.example.simple.single;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import heyalex.widgethelper.WidgetUpdater;

public class SingleUpdater extends WidgetUpdater {
    @Override
    public void update(@NonNull Context context, Bundle dataBundle, int... ids) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //get action for our RemoteViews button
        String action = dataBundle.getString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION);
        //get text for our RemoteViews
        String text = dataBundle.getString("main_text", "");

        try {
            //DB or Internet requests
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Notification makeNotification(@NonNull Context context) {
        String NOTIFICATION_CHANNEL_ID = "Test CHANNEL";
        String channelName = "SINGLE WIDGET UPDATING";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(chan);
        }

        Notification.Builder notificationBuilder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID);
        return notificationBuilder.build();
    }
}
