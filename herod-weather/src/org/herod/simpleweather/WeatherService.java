/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import org.herod.android.lang.SheduleTaskUtils;
import org.herod.android.lbs.LocationHelper;
import org.herod.android.lbs.LocationHelper.OnLocationSuccessListener;
import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.WeatherData;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.baidu.location.BDLocation;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherService extends Service implements
		OnLocationSuccessListener {
	private static final long TWO_HOUR = AlarmManager.INTERVAL_HOUR * 2;
	private static final int NOTIFICATION_ID = 100011;
	private static final long DELAY_MILLIS = 60 * 1000;
	private LocationHelper locationHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		locationHelper = new LocationHelper(getApplicationContext(), this);
		Intent intent = new Intent(getApplicationContext(), getClass());
		long current = System.currentTimeMillis();
		SheduleTaskUtils.shedule(getApplicationContext(), intent, 1, current,
				TWO_HOUR);
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
	public void onFail(final LocationHelper locationHelper, BDLocation location) {
		locationHelper.stop();
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			public void run() {
				locationHelper.start();
			}
		}, DELAY_MILLIS);
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
				Toast.makeText(getApplicationContext(), "读取天气数据失败！",
						Toast.LENGTH_LONG).show();
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
			try {
				return WeatherContext.getWeatherLoader().getWeatherByLocation(
						getApplicationContext(), params[0]);
			} catch (RuntimeException e) {
				return null;
			}
		}
	}

}
