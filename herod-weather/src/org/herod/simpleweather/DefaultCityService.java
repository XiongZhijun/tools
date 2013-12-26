/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.Arrays;
import java.util.List;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class DefaultCityService implements CityService {

	@Override
	public List<String> getCities() {
		return Arrays.asList("北京", "上海", "武汉", "南京");
	}

}
