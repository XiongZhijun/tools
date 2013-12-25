/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.CityWeatherTypeAdapter;
import org.herod.simpleweather.model.WeatherResult;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherServer {
	private static final String URL_TEMPLATE = "http://m.weather.com.cn/data/%1$s.html";

	public WeatherResult getWeather(Context context, BDLocation location) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		String cityId = CityLists.findCityId(context, location.getCity());
		String json = restTemplate.getForObject(
				String.format(URL_TEMPLATE, cityId), String.class);
		Gson gson = new GsonBuilder().registerTypeAdapter(CityWeather.class,
				new CityWeatherTypeAdapter()).create();
		CityWeather cityWeather = gson.fromJson(json, CityWeather.class);
		Log.d(getClass().getSimpleName(), new Gson().toJson(cityWeather));
		return null;
	}

}
