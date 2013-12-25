/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class CityLists {
	public static String findCityId(Context context, String city) {
		BufferedReader fileReader = null;
		try {

			fileReader = new BufferedReader(new InputStreamReader(context
					.getAssets().open("citys")));
			String line;
			while ((line = fileReader.readLine()) != null) {
				String[] splits = line.split(",");
				if (splits.length == 4 && splits[2].equals(city)) {
					return splits[0];
				}
			}
		} catch (IOException e) {
			Log.w(CityLists.class.getSimpleName(), e);
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					Log.w(CityLists.class.getSimpleName(), e);
				}
			}
		}
		return null;
	}
}
