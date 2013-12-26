/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class WeatherContext {

	private static WeatherLoader weatherLoader = new DefaultWeatherLoader();
	private static CityService cityService = new DefaultCityService();

	public static WeatherLoader getWeatherLoader() {
		return weatherLoader;
	}

	public static CityService getCityService() {
		return cityService;
	}

}
