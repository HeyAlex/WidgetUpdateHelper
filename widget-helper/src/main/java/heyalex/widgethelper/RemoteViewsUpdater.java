package heyalex.widgethelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation might be on class that extends {@link android.appwidget.AppWidgetProvider} that
 * will be updated by class that implements {@link WidgetUpdater}
 * For example:
 * <pre>
 * {@code @RemoteViewsUpdater(SimpleUpdater.class)}
 * <pre>
 * where SimpleUpdater implements {@link WidgetUpdater}
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteViewsUpdater {
    /**
     * @return class that implements {@link WidgetUpdater}
     */
    Class<? extends WidgetUpdater> value();
}