/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class CityWeather {

	private String currentCity;
	private String cityId;
	private List<WeatherData> weatherDatas = new ArrayList<WeatherData>();

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void addWeatherData(WeatherData weatherData) {
		weatherDatas.add(weatherData);
	}

	public List<WeatherData> getWeatherDatas() {
		return weatherDatas;
	}

	public void setWeatherDatas(List<WeatherData> weatherDatas) {
		this.weatherDatas = weatherDatas;
	}

	public WeatherData getTodayWeather() {
		return weatherDatas.size() > 0 ? weatherDatas.get(0) : null;
	}

}