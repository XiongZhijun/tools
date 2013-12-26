/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class DefaultCityService implements CityService {

	private static final String CITIES = "cities";

	@Override
	public Set<String> getCities(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Set<String> cities = sharedPreferences.getStringSet(CITIES,
				Collections.<String> emptySet());
		return cities;
	}

	@Override
	public void addCity(Context context, String city) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Set<String> cities = new HashSet<String>(
				sharedPreferences.getStringSet(CITIES,
						Collections.<String> emptySet()));
		cities.add(city);
		Editor editor = sharedPreferences.edit();
		editor.putStringSet(CITIES, cities);
		editor.commit();
	}

	@Override
	public void deleteCity(Context context, String city) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Set<String> cities = new HashSet<String>(
				sharedPreferences.getStringSet(CITIES,
						Collections.<String> emptySet()));
		cities.remove(city);
		Editor editor = sharedPreferences.edit();
		editor.putStringSet(CITIES, cities);
		editor.commit();
	}

}
