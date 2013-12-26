/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class SheduleTaskUtils {

	public static void shedule(Context context, Intent intent, int requestCode,
			long interval) {
		Calendar calendar = Calendar.getInstance();
		PendingIntent sender = PendingIntent.getService(context, requestCode,
				intent, 0);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				AlarmManager.INTERVAL_HOUR, sender);
	}

	public static void cancel(Context context, Intent intent, int requestCode) {
		PendingIntent sender = PendingIntent.getService(context, requestCode,
				intent, 0);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.cancel(sender);
	}
}
