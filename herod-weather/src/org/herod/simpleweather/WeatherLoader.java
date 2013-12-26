/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import org.herod.simpleweather.model.CityWeather;

import android.content.Context;

import com.baidu.location.BDLocation;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public interface WeatherLoader {

	CityWeather getWeatherByLocation(Context context, BDLocation location);

	CityWeather getWeatherByCityName(Context context, String cityName);

	CityWeather getWeatherByCityId(Context context, String cityId);
}
