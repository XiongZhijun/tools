/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.CityWeatherTypeAdapter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class DefaultWeatherLoader implements WeatherLoader {
	private static final String URL_TEMPLATE = "http://m.weather.com.cn/data/%1$s.html";

	@Override
	public CityWeather getWeatherByLocation(Context context, BDLocation location) {
		return getWeatherByCityName(context, location.getCity());
	}

	@Override
	public CityWeather getWeatherByCityName(Context context, String cityName) {
		return getWeatherByCityId(context,
				WeatherUtils.findCityIdByCityName(context, cityName));
	}

	@Override
	public CityWeather getWeatherByCityId(Context context, String cityId) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		String url = buildUrl(context, cityId);
		String json = restTemplate.getForObject(url, String.class);

		Gson gson = createGson();
		return gson.fromJson(json, CityWeather.class);
	}

	private String buildUrl(Context context, String cityId) {
		return String.format(URL_TEMPLATE, cityId);
	}

	private Gson createGson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(CityWeather.class,
				new CityWeatherTypeAdapter()).create();
		return gson;
	}

}
