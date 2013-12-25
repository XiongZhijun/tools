/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherService extends Service implements BDLocationListener {
	private ScheduledExecutorService executorService;
	private LocationClient locationClient;

	@Override
	public void onCreate() {
		super.onCreate();
		locationClient = new LocationClient(getApplicationContext());
		locationClient.registerLocationListener(this);
		locationClient.setLocOption(buildLocationClientOption());
		executorService = Executors.newScheduledThreadPool(1);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		locationClient.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (executorService != null) {
			executorService.shutdown();
		}
		if (locationClient.isStarted()) {
			locationClient.stop();
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onReceiveLocation(final BDLocation location) {
		if (isLocactionFailed(location)) {
			return;
		}
		executorService.execute(new Runnable() {
			public void run() {
				new WeatherServer().getWeather(getApplicationContext(),
						location);
			}
		});
		locationClient.stop();
	}

	@Override
	public void onReceivePoi(BDLocation location) {
	}

	public LocationClientOption buildLocationClientOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		option.setPriority(LocationClientOption.GpsFirst);
		return option;
	}

	public boolean isLocactionFailed(BDLocation location) {
		return location == null
				|| (location.getLocType() != 61 && location.getLocType() != 65
						&& location.getLocType() != 66
						&& location.getLocType() != 68 && location.getLocType() != 161);
	}

}
