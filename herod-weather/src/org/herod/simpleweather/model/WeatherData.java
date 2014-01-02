/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.herod.android.lang.ResourcesUtils;
import org.herod.simpleweather.R;

import android.content.Context;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherData {
	/**  */
	private static final int MILLISECOND_OF_ONE_DAY = 1000 * 60 * 60 * 24;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM月dd日");
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

	public int getCurrentPictureResource(Context context) {
		if (isNight()) {
			return getNightPictureResource(context);
		} else {
			return getDayPictureResource(context);
		}
	}

	public int getNightPictureResource(Context context) {
		int id = ResourcesUtils.getResourcesIdByType(context,
				getNightPictureUrl(), "drawable");
		return id > 0 ? id : R.drawable.n00;
	}

	public int getDayPictureResource(Context context) {
		int id = ResourcesUtils.getResourcesIdByType(context,
				getDayPictureUrl(), "drawable");
		return id > 0 ? id : R.drawable.d00;
	}

	public String getDateText() {
		if (isToday()) {
			return "今日";
		}
		return DATE_FORMAT.format(date);
	}

	public boolean isToday() {
		Date now = new Date();
		return toDayNumber(now) == toDayNumber(date);
	}

	public boolean isBeforeToday() {
		Date now = new Date();
		return toDayNumber(now) > toDayNumber(date);
	}

	public boolean isNotBeforeToday() {
		return !isBeforeToday();
	}

	public static boolean lt(int field, Calendar date1, Calendar date2) {
		return date1.get(field) < date2.get(field);
	}

	public static boolean equals(int field, Calendar date1, Calendar date2) {
		return date1.get(field) == date2.get(field);
	}

	private static int toDayNumber(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return (int) (calendar.getTime().getTime() / MILLISECOND_OF_ONE_DAY);
	}

}
