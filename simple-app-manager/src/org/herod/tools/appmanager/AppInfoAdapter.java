/*
 * Copyright © 2013-2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.tools.appmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 */
public class AppInfoAdapter extends BaseAdapter {

	private List<AppInfo> appInfos = new ArrayList<AppInfo>();
	private LayoutInflater inflater;
	private Context context;

	public AppInfoAdapter(Context context, List<AppInfo> appInfos) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		if (appInfos != null) {
			this.appInfos.addAll(appInfos);
		}
	}

	@Override
	public int getCount() {
		return appInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return appInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.fragment_app_info_item, null);
		} else {
			view = convertView;
		}
		bindView(view, position);
		return view;
	}

	private void bindView(View view, int position) {
		final AppInfo appInfo = appInfos.get(position);
		ImageView appIcon = (ImageView) view.findViewById(R.id.appIcon);
		TextView appName = (TextView) view.findViewById(R.id.appName);
		appName.setText(appInfo.appName);
		appIcon.setImageDrawable(appInfo.appIcon);

		View stopButton = view.findViewById(R.id.stop);
		if (appInfo.isRunning) {
			stopButton.setVisibility(View.VISIBLE);
		} else {
			stopButton.setVisibility(View.GONE);
		}
		stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(Uri.parse("package:" + appInfo.packageName));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				context.startActivity(intent);
			}
		});
	}
}
