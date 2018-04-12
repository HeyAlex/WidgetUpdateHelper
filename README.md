# WidgetUpdateHelper

This library helps managing the updating widgets. Just update all your widgets on background thread with `android.app.IntentService`, that provides that library.

Please, have a look at the **example** project.

## Example

**WidgetBuilder**

There is an interface `WidgetUpdater`, which has only one method `update(Context context, Bundle dataBundle, int... ids)`. 

```java
public class SingleUpdater implements WidgetUpdater {
    @Override
    public void update(Context context, Bundle dataBundle, int... ids) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
       
        //make RemoteViews depends on dataBundle and update by widget ID
        RemoteViews remoteViews = new RemoteViews();
        for (int widgetId : ids) {
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
```

**WidgetProvider**

When creating your own widget you can use `RemoteViewsUpdater` annotation to link the `WidgetUpdater`'s child with your `android.appwidget.AppWidgetProvider` as below.
`WidgetUpdateService` is an `android.app.IntentService` which will invokes your `WidgetUpdater` by calling method `updateWidgets(Context context, AppWidgetsProvider provider, Bundle dataBundle, int... ids)`.

```java
@RemoteViewsUpdater(SingleUpdater.class)
public class ExampleSingleAppWidget extends AppWidgetProvider {
        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
             // update widgets
             WidgetUpdateService.updateWidgets(context, this, bundle, appWidgetIds);
        }

        @Override
        public void onEnabled(Context context) {
             AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
             int[] widgetIds = appWidgetManager.getAppWidgetIds(
                           new ComponentName(context, ExampleSingleAppWidget.class));
           
             // add some params for RemoteViews
             Bundle bundle = new Bundle();
             //...
             // update widgets
             WidgetUpdateService.updateWidgets(context, this, bundle, widgetIds);
        }

        @Override
        public void onDisabled(Context context) {
            //...
        }
}
```

**RemoteViews extensions**

List of helper extensions on RemoteViews:
```
method public static void setBackgroundColor(android.widget.RemoteViews, @IdRes int viewId, @ColorInt int color);
method public static void setBackgroundResource(android.widget.RemoteViews, @IdRes int viewId, @DrawableRes int drawable);
method public static void setEnabled(android.widget.RemoteViews, @IdRes int viewId, boolean enabled);
method public static void setImageViewAlpha(android.widget.RemoteViews, @IdRes int viewId, @IntRange(from=0L, to=255L) int alpha);
method public static void setImageViewMaxHeight(android.widget.RemoteViews, @IdRes int viewId, @DimenRes int height);
method public static void setImageViewMaxWidth(android.widget.RemoteViews, @IdRes int viewId, @DimenRes int width);
method public static void setTextViewMaxLines(android.widget.RemoteViews, @IdRes int viewId, int lines);
method public static void setTextViewMinLines(android.widget.RemoteViews, @IdRes int viewId, int lines);
method public static void setTextViewPaintFlags(android.widget.RemoteViews, @IdRes int viewId, int flags);
method public static void setTextViewTextSize(android.widget.RemoteViews, @IdRes int viewId, @DimenRes float size);
```

## Integration
The library is published to the jitpack repository, your *project's* `build.gradle` must contain:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

To use this library add following to your *module's* `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.HeyAlex:WidgetUpdateHelper:1.1'
}
```

### Why to use
All updates are on background thread. So if you need to make RemoteViews depends on data from Database or Internet, that's what you need.
Android O is also supported.

### License
```Text
WTFPL
```
