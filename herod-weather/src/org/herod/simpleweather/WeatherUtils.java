/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.herod.simpleweather.model.WeatherData;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherUtils {

	private static final String CITY_ID_LIST_FILE = "citys";

	public static String findCityIdByCityName(Context context, String city) {
		if (city == null || city.length() == 0) {
			return null;
		}
		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(context
					.getAssets().open(CITY_ID_LIST_FILE)));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splits = line.split(",");
				if (splits.length == 4 && city.contains(splits[2])) {
					return splits[0];
				}
			}
		} catch (IOException e) {
			Log.w(WeatherUtils.class.getSimpleName(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.w(WeatherUtils.class.getSimpleName(), e);
				}
			}
		}
		return null;
	}

	public static Notification createWeatherNotification(Context context,
			WeatherData weatherData) {
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), 0);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.weather_notification);
		views.setImageViewResource(R.id.image,
				weatherData.getCurrentPictureResource());
		views.setTextViewText(R.id.title, weatherData.getContentTitle());
		views.setTextViewText(R.id.content, weatherData.getContentInfo());
		Notification notification = new Notification.Builder(context)
				.setContent(views)
				.setSmallIcon(weatherData.getCurrentPictureResource())
				.setOngoing(true).setContentIntent(contentIntent).getNotification();
		return notification;
	}
}
