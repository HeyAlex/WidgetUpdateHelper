# WidgetUpdateHelper

This library helps managing the updating widgets. Just update all your widget on background thread with `android.app.IntentService`, that provides that library.

Please, have a look at the **example** project.

## Example

**WidgetBuilder**

There is an interface `WidgetBuilder`, which has only one method `update(Context context, Bundle dataBundle, int... ids)`, that called in `UpdateService`, that contains WidgetUpdateLibrary. 

Need to implement that interface.
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

Make an annotation `RemoteViewsUpdater` on `android.appwidget.AppWidgetProvider` with class that implements `WidgetBuilder`.

```java
@RemoteViewsUpdater(SingleUpdater.class)
public class ExampleSingleAppWidget extends AppWidgetProvider {
        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            //...
        }

        @Override
        public void onEnabled(Context context) {
            //...
        }

        @Override
        public void onDisabled(Context context) {
            //...
        }
}
```


## Integration
The library is published to the jcenter repository, thus your *project's* `build.gradle` must contain:

```groovy
repositories {
    jcenter()
}
```

To use this library add following to your *module's* `build.gradle`:

```groovy
dependencies {
    compile 'com.github.HeyAlex:WidgetUpdateHelper:1.0'
}
```

### Why to use
All updates are on background threads. So if you need to make RemoteViews depends on data from Database or Internet, that's what you need.

### License
```Text
WTFPL
```
