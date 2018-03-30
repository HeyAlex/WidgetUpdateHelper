package heyalex.widgethelper.test.update_logic

import android.content.ComponentName
import android.support.test.rule.ActivityTestRule
import heyalex.widgethelper.RemoteViewsUpdater
import heyalex.widgethelper.test.TestActivity
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.lang.annotation.AnnotationFormatError

class WidgetUpdateServiceTest {

    @JvmField
    @Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    @Test
    fun testAnnotationFinding() {
        findAnnotation(AppWidgetUpdateTest::class.java)
        assertTrue(true)
    }


    @Test
    fun newInstanceOfWidgetUpdate() {
        newInstanceOfBuilder()
        assertTrue(true)
    }

    /**
     * @param clazz that contains @RemoteViewsUpdater annotation
     * @return annotation @RemoteViewsUpdater
     * @throws AnnotationFormatError if where is no [RemoteViewsUpdater] annotation in clazz
     */
    private fun findAnnotation(clazz: Class<*>): Annotation {
        val annotations = clazz.annotations

        for (annotation in annotations) {
            if (annotation.annotationClass == RemoteViewsUpdater::class) {
                return annotation
            }
        }

        throw AnnotationFormatError(clazz.name + " doesn't contains " +
                "annotation @RemoteViewsUpdater")
    }

    private fun newInstanceOfBuilder() {
        var clazz: Class<*>? = null
        try {
            clazz = Class.forName(ComponentName(rule.activity.applicationContext,
                    AppWidgetUpdateTest::class.java.name).className)
            val annotation = findAnnotation(clazz)
            val builder = (annotation as RemoteViewsUpdater).value.objectInstance
            builder?.update(rule.activity.applicationContext, null, 0)
        } catch (e: ClassNotFoundException) {
            throw InstantiationException("Unable to instantiate WidgetBuilder " +
                    clazz!!.name + ": make sure class name exists, is public, and has an"
                    + " empty constructor that is public. Error: " + e.message)
        } catch (e: IllegalAccessException) {
            throw InstantiationException("Unable to instantiate WidgetBuilder " +
                    clazz!!.name + ": make sure class name exists, is public, and has an"
                    + " empty constructor that is public. Error: " + e.message)
        } catch (e: InstantiationException) {
            throw InstantiationException("Unable to instantiate WidgetBuilder " +
                    clazz!!.name + ": make sure class name exists, is public, and has an"
                    + " empty constructor that is public. Error: " + e.message)
        }

    }
}

