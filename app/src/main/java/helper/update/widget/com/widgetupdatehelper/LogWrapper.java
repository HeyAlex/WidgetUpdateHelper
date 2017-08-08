package helper.update.widget.com.widgetupdatehelper;

import android.util.Log;

import yotaphone.yotadevices.com.widgetupdatehelper.BuildConfig;

public class LogWrapper {
    public static void d(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void v(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void i(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void w(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void e(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }
}