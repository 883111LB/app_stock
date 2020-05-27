package com.cvicse.stock.pulltorefresh.internal;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	static SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
	}

	public static String timeFormat() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		return format.format(date);
	}

}
