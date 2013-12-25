/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather.model;

import java.util.Calendar;
import java.util.Date;

import org.herod.simpleweather.ResourcesUtils;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherData {
	private Date date;
	private String temperature;
	private String weather;
	private String dayPictureUrl;
	private String nightPictureUrl;
	private String dayTitle;
	private String nightTitle;
	private String wind;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDayPictureUrl() {
		return dayPictureUrl;
	}

	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}

	public String getNightPictureUrl() {
		return nightPictureUrl;
	}

	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getDayTitle() {
		return dayTitle;
	}

	public void setDayTitle(String dayTitle) {
		this.dayTitle = dayTitle;
	}

	public String getNightTitle() {
		return nightTitle;
	}

	public void setNightTitle(String nightTitle) {
		this.nightTitle = nightTitle;
	}

	public String getContentTitle() {
		StringBuilder sb = new StringBuilder();
		if (isNight()) {
			sb.append(getNightTitle());
		} else {
			sb.append(getDayTitle());
		}
		sb.append("，").append(temperature);
		return sb.toString();
	}

	public String getContentInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(getWind());
		return sb.toString();
	}

	private boolean isNight() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return hour >= 18;
	}

	public int getCurrentPictureResource() {
		if (isNight()) {
			return ResourcesUtils.getResourcesIdByType(getNightPictureUrl(),
					"drawable");
		} else {
			return ResourcesUtils.getResourcesIdByType(getDayPictureUrl(),
					"drawable");
		}
	}

}
