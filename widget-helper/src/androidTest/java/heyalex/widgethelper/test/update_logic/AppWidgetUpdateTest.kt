package heyalex.widgethelper.test.update_logic

import android.appwidget.AppWidgetProvider
import heyalex.widgethelper.RemoteViewsUpdater
import heyalex.widgethelper.UpdateWidget
import java.util.concurrent.TimeUnit

@RemoteViewsUpdater(WidgetUpdaterTest::class)
@UpdateWidget(timeUnit = TimeUnit.DAYS, timeValue = 1)
class AppWidgetUpdateTest: AppWidgetProvider()
