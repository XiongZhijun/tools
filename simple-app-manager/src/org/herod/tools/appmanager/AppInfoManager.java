/*
 * Copyright © 2013-2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.tools.appmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 */
public class AppInfoManager {

	public static ArrayList<AppInfo> getAppInfos(Context context) {
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = am.getRunningAppProcesses();

		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				continue;
			}
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(
					packageManager).toString();
			tmpInfo.packageName = packageInfo.packageName;
			tmpInfo.versionName = packageInfo.versionName;
			tmpInfo.versionCode = packageInfo.versionCode;
			tmpInfo.appIcon = packageInfo.applicationInfo
					.loadIcon(packageManager);
			tmpInfo.isRunning = isRunning(list, packageInfo.packageName);
			appList.add(tmpInfo);
		}
		Collections.sort(appList, new Comparator<AppInfo>() {
			public int compare(AppInfo lhs, AppInfo rhs) {
				return Boolean.valueOf(rhs.isRunning).compareTo(lhs.isRunning);
			}
		});
		return appList;

	}

	public static boolean isRunning(List<RunningAppProcessInfo> list,
			String packageName) {
		for (RunningAppProcessInfo info : list) {
			if (Arrays.asList(info.pkgList).contains(packageName)) {
				return true;
			}
		}
		return false;
	}

}
