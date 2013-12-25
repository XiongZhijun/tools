/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.Arrays;
import java.util.List;

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
public class WeatherServer {
	private static final String URL_TEMPLATE = "http://m.weather.com.cn/data/%1$s.html";

	public List<CityWeather> getWeathers(Context context, BDLocation location) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		String url = buildUrl(context, location);
		String json = restTemplate.getForObject(url, String.class);
		Gson gson = createGson();
		CityWeather cityWeather = gson.fromJson(json, CityWeather.class);
		return Arrays.asList(cityWeather);
	}

	private Gson createGson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(CityWeather.class,
				new CityWeatherTypeAdapter()).create();
		return gson;
	}

	private String buildUrl(Context context, BDLocation location) {
		String cityId = CityLists.findCityId(context, location.getCity());
		String url = String.format(URL_TEMPLATE, cityId);
		return url;
	}

}
