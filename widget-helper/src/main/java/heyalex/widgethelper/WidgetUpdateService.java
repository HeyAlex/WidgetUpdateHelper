package heyalex.widgethelper;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.util.Arrays;

/**
 * Update Service that helps update widget by ComponentName and Ids
 */
public class WidgetUpdateService extends IntentService {
    private static final String LOG_TAG = WidgetUpdateService.class.getSimpleName();

    /**
     * Key for gain {@link ComponentName} that associated with {@link AppWidgetProvider}
     */
    public static final String EXTRA_PROVIDER = "extra_provider";
    /**
     * Key for gain widget Ids that will be updated, if where is no Ids by this key,
     * {@link WidgetUpdateService} will skip updating of this {@link ComponentName}
     */
    public static final String EXTRA_WIDGET_IDS = "extra_widget_ids";
    /**
     * Key for gain {@link Bundle} that contains all data that you needed to build yor RemoteViews
     */
    public static final String EXTRA_DATA_BUNDLE = "extra_data_bundle";

    public static final int NOTIFICATION_ID = 100;

    public WidgetUpdateService() {
        super("WidgetUpdateService");
        setIntentRedelivery(true);
    }

    /**
     * Static method for getting intent for {@link WidgetUpdateService} that will update widget
     * class and ids if where is no widget ids, skip updating {@link ComponentName}
     *
     * @param context     context for intent
     * @param widgetClass for {@link ComponentName} associated with widget that will
     *                    be updated
     * @param dataBundle  bundle of data, that you need to build a RemoteViews
     * @param widgetIds   ids that will be updated
     */
    public static Intent getIntentUpdateWidget(Context context,
                                               Class<? extends AppWidgetProvider> widgetClass,
                                               Bundle dataBundle, int... widgetIds) {

        return new Intent(context, WidgetUpdateService.class)
                .putExtra(EXTRA_PROVIDER, new ComponentName(context, widgetClass))
                .putExtra(EXTRA_WIDGET_IDS, widgetIds)
                .putExtra(EXTRA_DATA_BUNDLE, dataBundle);
    }

    /**
     * Static method for starting update Widget with AppWidgetProvider.class and ids
     *
     * @param context     context for intent
     * @param provider for {@link ComponentName} associated with widget that will
     *                    be updated
     * @param dataBundle  bundle of data, that you need to build a RemoteViews
     * @param widgetIds   ids that will be updated
     */
    public static void updateWidgets(Context context,
                                     AppWidgetProvider provider,
                                     Bundle dataBundle,
                                     int... widgetIds) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(getIntentUpdateWidget(context, provider.getClass(),
                    dataBundle, widgetIds));
        } else {
            context.startService(getIntentUpdateWidget(context, provider.getClass(), dataBundle,
                    widgetIds));
        }
    }

    public static void scheduleWidget(Context context,
                                     AppWidgetProvider provider,
                                     Bundle dataBundle,
                                     int... widgetIds) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(getIntentUpdateWidget(context, provider.getClass(), dataBundle, widgetIds));
        } else {
            context.startService(getIntentUpdateWidget(context, provider.getClass(), dataBundle, widgetIds));
        }
    }

    public void setupAlarm(Context context, AppWidgetProvider provider, Bundle dataBundle) {
        final AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, provider.getClass()));
        Intent intent = getIntentUpdateWidget(context, provider.getClass(), dataBundle,
                widgetIds);
        PendingIntent pendingIntent = getPendingIntent(context, intent);

        alarmManager.cancel(pendingIntent);

        UpdateWidget annotation = (UpdateWidget) findAnnotation(provider.getClass(), UpdateWidget.class);
        long millis = annotation.timeUnit().toMillis(annotation.timeValue());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis,
                    pendingIntent);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis,
                        pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
            }
        }
    }

    public void stopAlarm(Context context, AppWidgetProvider provider) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, provider.getClass()));
        Intent intent = getIntentUpdateWidget(context, provider.getClass(), null,
                widgetIds);
        PendingIntent pendingIntent = getPendingIntent(context, intent);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private static PendingIntent getPendingIntent(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            return PendingIntent.getService(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogWrapper.d(LOG_TAG, String.format("onStartCommand -> intent=%s, flags=%s, startId=%s",
                intent, flags, startId));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogWrapper.d(LOG_TAG, String.format("onHandleIntent -> intent=%s", intent));
        try {
            ComponentName provider = intent.getParcelableExtra(EXTRA_PROVIDER);
            if (provider != null) {
                int[] ids = intent.getIntArrayExtra(EXTRA_WIDGET_IDS);
                if (ids == null || ids.length == 0) {
                    ids = AppWidgetManager.getInstance(this).getAppWidgetIds(provider);
                }
                Bundle bundle = intent.getBundleExtra(EXTRA_DATA_BUNDLE);
                updateWidgets(provider, bundle, ids);
            } else {
                LogWrapper.w(LOG_TAG, "cannot update widgets, intent missing value for " +
                        "EXTRA_PROVIDER");
            }
        } catch (Exception e) {
            // catch everything so the Service.stopSelf(int) will be called in any case
            LogWrapper.e(LOG_TAG, "Exception onHandleIntent " + e);
        }
        stopForeground(true);
    }

    /**
     * @param provider   {@link ComponentName} that will be updated
     * @param dataBundle {@link Bundle} that will you need to make {@link android.widget.RemoteViews}
     * @param ids        widget ids that will be updated
     * @throws InstantiationException If there is a failure in instantiating the given class that
     *                                extends {@link WidgetUpdater}. This is a runtime exception;
     *                                it is not normally expected to happen.
     */
    private void updateWidgets(ComponentName provider, Bundle dataBundle, int... ids)
            throws InstantiationException {
        if (ids.length == 0) {
            LogWrapper.d(LOG_TAG, String.format("onHandleIntent -> skipping update of %s, no ids",
                    provider.toShortString()));
        } else {
            LogWrapper.d(LOG_TAG, String.format("onHandleIntent -> updating %s, ids=%s",
                    provider.toShortString(), Arrays.toString(ids)));
            Class clazz = null;
            try {
                clazz = Class.forName(provider.getClassName());
                Annotation annotation = findAnnotation(clazz, RemoteViewsUpdater.class);
                WidgetUpdater builder = ((RemoteViewsUpdater) annotation).value().newInstance();
                if (builder != null) {
                    builder.update(this, dataBundle, ids);
                } else {
                    LogWrapper.w(LOG_TAG, "cannot update widgets, no builder set for given " +
                            "EXTRA_PROVIDER");
                }

            } catch (ClassNotFoundException e) {
                throw new InstantiationException("Unable to instantiate WidgetBuilder " +
                        clazz.getName() + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public. Error: " + e.getMessage());
            } catch (IllegalAccessException e) {
                throw new InstantiationException("Unable to instantiate WidgetBuilder " +
                        clazz.getName() + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public. Error: " + e.getMessage());
            } catch (InstantiationException e) {
                throw new InstantiationException("Unable to instantiate WidgetBuilder " +
                        clazz.getName() + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public. Error: " + e.getMessage());
            }
        }
    }

    /**
     * @param clazz that contains annotation
     * @param annotationClazz annotation
     * @return annotation @RemoteViewsUpdater
     * @throws AnnotationFormatError if where is no [RemoteViewsUpdater] annotation in clazz
     */
    private Annotation findAnnotation(Class clazz, Class annotationClazz) {
        Annotation[] annotations = clazz.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == annotationClazz) {
                return annotation;
            }
        }

        throw new AnnotationFormatError(clazz.getName() + " doesn't contains " +
                "annotation @RemoteViewsUpdater");
    }
}
