/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class LocationHelper {
	private LocationClient locationClient;
	private OnLocationSuccessListener listener;

	public LocationHelper(Context context, OnLocationSuccessListener listener) {
		this.listener = listener;
		locationClient = new LocationClient(context);
		locationClient.registerLocationListener(new SimpleBDLocationListener());
		locationClient.setLocOption(buildLocationClientOption());
	}

	public void start() {
		if (locationClient.isStarted()) {
			return;
		}
		locationClient.start();
	}

	private LocationClientOption buildLocationClientOption() {
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

	public void stop() {
		locationClient.stop();
	}

	public static interface OnLocationSuccessListener {
		void onSuccess(LocationHelper locationHelper, BDLocation location);

		void onFail(LocationHelper locationHelper, BDLocation location);
	}

	class SimpleBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (isLocactionFailed(location)) {
				listener.onFail(LocationHelper.this, location);
				return;
			}
			listener.onSuccess(LocationHelper.this, location);
		}

		@Override
		public void onReceivePoi(BDLocation location) {
		}

	}

	private boolean isLocactionFailed(BDLocation location) {
		return location == null
				|| (location.getLocType() != 61 && location.getLocType() != 65
						&& location.getLocType() != 66
						&& location.getLocType() != 68 && location.getLocType() != 161)
				|| location.getCity() == null;
	}
}
