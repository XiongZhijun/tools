/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather.model;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class CityWeatherTypeAdapter implements JsonDeserializer<CityWeather> {

	@Override
	public CityWeather deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		if (!jsonElement.isJsonObject()) {
			return null;
		}
		JsonObject jsonObject = ((JsonObject) jsonElement)
				.getAsJsonObject("weatherinfo");
		CityWeather weather = new CityWeather();
		weather.setCurrentCity(jsonObject.get("city").getAsString());
		weather.setCityId(jsonObject.get("cityid").getAsString());
		Calendar calendar = Calendar.getInstance();
		Date date = getDate(jsonObject);
		calendar.setTime(date);
		for (int i = 1; i <= 6; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, i - 1);
			WeatherData weatherData = toWeatherData(jsonObject, calendar, i);
			weather.addWeatherData(weatherData);
		}
		return weather;
	}

	private WeatherData toWeatherData(JsonObject jsonObject, Calendar calendar,
			int i) {
		WeatherData weatherData = new WeatherData();
		weatherData.setDate(calendar.getTime());
		weatherData.setDayPictureUrl(getImageUrl(jsonObject, i * 2 - 1));
		weatherData.setDayTitle(jsonObject.get("img_title" + (i * 2 - 1))
				.getAsString());
		weatherData.setNightPictureUrl(getImageUrl(jsonObject, i * 2));
		weatherData.setNightTitle(jsonObject.get("img_title" + (i * 2))
				.getAsString());
		weatherData.setTemperature(jsonObject.get("temp" + i).getAsString());
		weatherData.setWeather(jsonObject.get("weather" + i).getAsString());
		weatherData.setWind(jsonObject.get("wind" + i).getAsString());
		return weatherData;
	}

	private String getImageUrl(JsonObject jsonObject, int i) {
		String BASE_URL = "http://www.weather.com.cn/m2/i/icon_weather/29x20/%1$s%2$02d.gif";
		String type = i % 2 == 1 ? "d" : "n";
		try {
			return String.format(BASE_URL, type, jsonObject.get("img" + i)
					.getAsInt());
		} catch (RuntimeException e) {
			Log.d("TEST", "dd", e);
			throw e;

		}
	}

	private Date getDate(JsonObject jsonObject) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String day = jsonObject.get("date_y").getAsString();
		Date date;
		try {
			date = dateFormat.parse(day);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return date;
	}

}
