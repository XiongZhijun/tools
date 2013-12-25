/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.List;

import org.herod.simpleweather.LocationHelper.OnLocationSuccessListener;
import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.WeatherData;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherService extends Service implements
		OnLocationSuccessListener {
	private List<CityWeather> weathers;
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
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	class LoadWeatherTask extends
			AsyncTask<BDLocation, Void, List<CityWeather>> {
		@Override
		protected void onPostExecute(List<CityWeather> weathers) {
			WeatherService.this.weathers = weathers;
			WeatherData todayWeather = weathers.get(0).getTodayWeather();
			if (todayWeather == null) {
				return;
			}
			Service service = WeatherService.this;
			Log.d(getClass().getSimpleName(), new Gson().toJson(weathers));
			PendingIntent contentIntent = PendingIntent.getActivity(service, 0,
					new Intent(service, MainActivity.class), 0);
			Notification notification = new Notification.Builder(service)
					.setContentTitle(todayWeather.getContentTitle())
					.setSmallIcon(todayWeather.getCurrentPictureResource())
					.setLargeIcon(
							BitmapFactory.decodeResource(getResources(),
									todayWeather.getCurrentPictureResource()))
					.setContentInfo(todayWeather.getContentInfo())
					.setOngoing(true).setContentIntent(contentIntent).build();
			startForeground(100011, notification);

		}

		@Override
		protected List<CityWeather> doInBackground(BDLocation... params) {
			return new WeatherServer().getWeathers(getApplicationContext(),
					params[0]);
		}

	}

	public class ServiceBinder extends Binder {

		public List<CityWeather> getWeathers() {
			return weathers;
		}

	}

}
