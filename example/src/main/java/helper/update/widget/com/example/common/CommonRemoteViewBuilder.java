package helper.update.widget.com.example.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import helper.update.widget.com.example.R;
import helper.update.widget.com.example.VectorUtil;
import helper.update.widget.com.widgetupdatehelper.UpdateService;

import static helper.update.widget.com.widgetupdatehelper.UpdateService.EXTRA_DATA_BUNDLE;


public class CommonRemoteViewBuilder {

    private RemoteViews remoteViews;
    private Context context;
    private int[] widgetId;
    public static final String NEXT_TEXT = "EXAMPLE NEXT TEXT";
    public static final String PREVIOUS_TEXT = "EXAMPLE PREVIOUS TEXT";
    public static final String NEXT_ACTION = "NEXT_ACTION";
    public static final String PREVIOUS_ACTION = "PREVIOUS_ACTION";
    public static final String ACTION_BUTTON = "ACTION_BUTTON";

    public CommonRemoteViewBuilder(Context context, int[] widgetId) {
        this.widgetId = widgetId;
        this.context = context;
        this.remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_common_app_widget);
    }

    public RemoteViews getFirstView(String exampleText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            remoteViews.setImageViewResource(R.id.next, R.drawable.chevron_right);
        } else {
            remoteViews.setImageViewBitmap(R.id.next, VectorUtil
                    .vectorToBitmap(context, R.drawable.chevron_right));
        }
        remoteViews.setOnClickPendingIntent(R.id.next, getPendingIntent(context, PREVIOUS_TEXT,
                NEXT_ACTION, widgetId));
        remoteViews.setViewVisibility(R.id.next, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.previous, View.INVISIBLE);
        remoteViews.setTextViewText(R.id.appwidget_text, exampleText);
        return remoteViews;
    }

    public RemoteViews getSecondView(String exampleText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            remoteViews.setImageViewResource(R.id.previous, R.drawable.chevron_left);
        } else {
            remoteViews.setImageViewBitmap(R.id.previous, VectorUtil
                    .vectorToBitmap(context, R.drawable.chevron_left));
        }

        remoteViews.setOnClickPendingIntent(R.id.previous, getPendingIntent(context, NEXT_TEXT,
                PREVIOUS_ACTION, widgetId));
        remoteViews.setViewVisibility(R.id.next, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.previous, View.VISIBLE);
        remoteViews.setTextViewText(R.id.appwidget_text, exampleText);
        return remoteViews;
    }


    private static PendingIntent getPendingIntent(Context context, String text, String action,
                                                  int[] widgetIds) {

        Bundle bundle = new Bundle();
        bundle.putString("action", action);
        bundle.putString("main_text", text);

        Intent updateServiceIntent = new Intent(context, ExampleCommonAppWidget.class)
                .setAction(ACTION_BUTTON)
                .putExtra(UpdateService.EXTRA_WIDGET_IDS, widgetIds)
                .putExtra(EXTRA_DATA_BUNDLE, bundle);


        return PendingIntent.getBroadcast(context, 0, updateServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}