package heyalex.widgethelper.test.update_logic

import android.content.Context
import android.os.Bundle
import heyalex.widgethelper.WidgetUpdater
import org.junit.Assert

class WidgetUpdaterTest: WidgetUpdater() {
    override fun update(context: Context, dataBundle: Bundle?, vararg ids: Int) {
        Assert.assertTrue(dataBundle!!.getBoolean("TEST"))
    }
}