/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.Set;

import android.content.Context;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public interface CityService {

	Set<String> getCities(Context context);

	void addCity(Context context, String city);

	void deleteCity(Context context, String city);
}
