/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather.test;

import java.util.Calendar;
import java.util.Date;

import org.herod.simpleweather.model.WeatherData;

import android.test.AndroidTestCase;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherDataTest extends AndroidTestCase {

	public void testByToday() {
		WeatherData weatherData = new WeatherData();
		weatherData.setDate(new Date());
		assertTrue(weatherData.isToday());
		assertTrue(weatherData.isNotBeforeToday());
	}

	public void testByBeforeToday1() {
		WeatherData weatherData = new WeatherData();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		weatherData.setDate(calendar.getTime());
		assertFalse(weatherData.isToday());
		assertFalse(weatherData.isNotBeforeToday());
	}

	public void testByBeforeToday2() {
		WeatherData weatherData = new WeatherData();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		weatherData.setDate(calendar.getTime());
		assertFalse(weatherData.isToday());
		assertFalse(weatherData.isNotBeforeToday());
	}

	public void testByBeforeToday3() {
		WeatherData weatherData = new WeatherData();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		weatherData.setDate(calendar.getTime());
		assertFalse(weatherData.isToday());
		assertFalse(weatherData.isNotBeforeToday());
	}

	public void testByAfterToday1() {
		WeatherData weatherData = new WeatherData();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		weatherData.setDate(calendar.getTime());
		assertFalse(weatherData.isToday());
		assertTrue(weatherData.isNotBeforeToday());
	}

	public void testByAfterToday2() {
		WeatherData weatherData = new WeatherData();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, +1);
		weatherData.setDate(calendar.getTime());
		assertFalse(weatherData.isToday());
		assertTrue(weatherData.isNotBeforeToday());
	}

	public void testByAfterToday3() {
		WeatherData weatherData = new WeatherData();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, +1);
		weatherData.setDate(calendar.getTime());
		assertFalse(weatherData.isToday());
		assertTrue(weatherData.isNotBeforeToday());
	}
}
