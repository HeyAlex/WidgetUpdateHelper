package heyalex.widgethelper.test.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.test.rule.ActivityTestRule
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TextView
import androidx.graphics.drawable.toBitmap
import heyalex.widgethelper.extensions.*
import heyalex.widgethelper.test.R
import heyalex.widgethelper.test.TestActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemoteViewsTest {

    @JvmField
    @Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    private lateinit var remoteView: RemoteViews
    private lateinit var result: View
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var context: Context

    @Before
    fun setup() {
        remoteView = RemoteViews(
                rule.activity.packageName,
                R.layout.test_remoteviews
        )
        result = remoteView.apply(rule.activity.applicationContext, null)
        textView = result.findViewById(R.id.test_textView)
        imageView = result.findViewById(R.id.test_imageView)
        context = rule.activity.baseContext
    }

    @Test
    fun setTextViewMaxLines() {
        assertEquals(4, textView.maxLines)

        remoteView.setTextViewMaxLines(R.id.test_textView, 6)
        remoteView.reapply(rule.activity, result)
        assertEquals(6, textView.maxLines)
    }

    @Test
    fun setTextViewMinLines() {
        assertEquals(6, textView.minLines)

        remoteView.setTextViewMinLines(R.id.test_textView, 8)
        remoteView.reapply(rule.activity, result)
        assertEquals(8, textView.minLines)
    }

    @Test
    fun setTextViewTextSize() {
        var testTextSize = context.resources.getDimension(R.dimen.test_textSize_10sp)
        var actualSize = testTextSize * context.resources.displayMetrics.density
        remoteView.setTextViewTextSize(R.id.test_textView, testTextSize)
        remoteView.reapply(rule.activity, result)
        assertEquals(actualSize, textView.textSize)

        testTextSize = context.resources.getDimension(R.dimen.test_textSize_20sp)
        actualSize = testTextSize * context.resources.displayMetrics.density
        remoteView.setTextViewTextSize(R.id.test_textView, testTextSize)
        remoteView.reapply(rule.activity, result)
        assertEquals(actualSize, textView.textSize)
    }

    @Test
    fun setTextViewPaintFlags() {
        remoteView.setTextViewPaintFlags(R.id.test_textView, Paint.UNDERLINE_TEXT_FLAG)
        remoteView.reapply(rule.activity, result)
        assertEquals(textView.paintFlags, Paint.UNDERLINE_TEXT_FLAG)
    }

    @Test
    fun setImageViewAlpha() {
        remoteView.setImageViewAlpha(R.id.test_imageView, 70)
        remoteView.reapply(rule.activity, result)
        assertEquals(70, imageView.imageAlpha)

        remoteView.setImageViewAlpha(R.id.test_imageView, 20)
        remoteView.reapply(rule.activity, result)
        assertEquals(20, imageView.imageAlpha)
    }

    @Test
    fun setImageViewMaxWidth() {
        var testDimension = context.resources.getDimension(R.dimen.test_dimension_100dp).toInt()
        remoteView.setImageViewMaxWidth(R.id.test_imageView, testDimension)
        remoteView.reapply(rule.activity, result)
        assertEquals(testDimension, imageView.maxWidth)

        testDimension = context.resources.getDimension(R.dimen.test_dimension_200dp).toInt()
        remoteView.setImageViewMaxWidth(R.id.test_imageView, testDimension)
        remoteView.reapply(rule.activity, result)
        assertEquals(testDimension, imageView.maxWidth)
    }

    @Test
    fun setImageViewMaxHeight() {
        var testDimension = context.resources.getDimension(R.dimen.test_dimension_100dp).toInt()
        remoteView.setImageViewMaxHeight(R.id.test_imageView, testDimension)
        remoteView.reapply(rule.activity, result)
        assertEquals(testDimension, imageView.maxHeight)

        testDimension = context.resources.getDimension(R.dimen.test_dimension_200dp).toInt()
        remoteView.setImageViewMaxHeight(R.id.test_imageView, testDimension)
        remoteView.reapply(rule.activity, result)
        assertEquals(testDimension, imageView.maxHeight)
    }

    @Test
    fun setEnabled() {
        assertEquals(true, textView.isEnabled)

        remoteView.setEnabled(R.id.test_imageView, false)
        remoteView.reapply(rule.activity, result)
        assertEquals(false, imageView.isEnabled)

        remoteView.setEnabled(R.id.test_textView, true)
        remoteView.reapply(rule.activity, result)
        assertEquals(true, textView.isEnabled)
    }

    @Test
    fun setBackgroundColor() {
        remoteView.setBackgroundColor(R.id.test_imageView, Color.RED)
        remoteView.reapply(rule.activity, result)
        assertEquals((imageView.background as ColorDrawable).color, Color.RED)
    }

    @Test
    fun setBackgroundResource() {
        remoteView.setBackgroundResource(R.id.test_textView, R.drawable.ic_plus)
        remoteView.reapply(rule.activity, result)
        val expectedDrawable = ContextCompat.getDrawable(context, R.drawable.ic_plus)?.toBitmap()
        val isDrawablesEquals = (textView.background as Drawable)
                .toBitmap().sameAs(expectedDrawable)
        assertEquals(true, isDrawablesEquals)
    }
}
