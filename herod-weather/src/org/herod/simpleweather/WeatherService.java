/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import org.herod.simpleweather.LocationHelper.OnLocationSuccessListener;
import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.WeatherData;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.baidu.location.BDLocation;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherService extends Service implements
		OnLocationSuccessListener {
	private static final int NOTIFICATION_ID = 100011;
	private LocationHelper locationHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		locationHelper = new LocationHelper(getApplicationContext(), this);
		Intent intent = new Intent(getApplicationContext(), getClass());
		SheduleTaskUtils.shedule(getApplicationContext(), intent, 1, 5000);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		locationHelper.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onSuccess(LocationHelper locationHelper, BDLocation location) {
		locationHelper.stop();
		new LoadWeatherTask().execute(location);
	}

	@Override
	public void onDestroy() {
		locationHelper.stop();
		stopForeground(true);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	class LoadWeatherTask extends AsyncTask<BDLocation, Void, CityWeather> {

		protected void onPostExecute(CityWeather weather) {
			WeatherData todayWeather = weather != null ? weather
					.getTodayWeather() : null;
			if (todayWeather == null) {
				return;
			}
			stopForeground(true);
			Service service = WeatherService.this;
			Notification notification = WeatherUtils.createWeatherNotification(
					service, todayWeather);
			startForeground(NOTIFICATION_ID, notification);
		}

		@Override
		protected CityWeather doInBackground(BDLocation... params) {
			return WeatherContext.getWeatherLoader().getWeatherByLocation(
					getApplicationContext(), params[0]);
		}

	}

}
