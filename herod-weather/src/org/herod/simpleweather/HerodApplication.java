/*
 * Copyright © 2013-2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.simpleweather;

import android.app.Application;
import android.content.Intent;

/**
 * 
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 */
public class HerodApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ResourcesUtils.setApplication(this);
		startService(new Intent(this, WeatherService.class));
	}

}
