/*
 * Copyright © 2013-2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.tools.appmanager;

import java.util.List;

import org.herod.framework.BaseFragment;
import org.herod.framework.BundleBuilder;
import org.herod.framework.ci.InjectViewHelper;
import org.herod.framework.ci.annotation.InjectView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 */
public class AppInfoFragment extends BaseFragment {

	private static final String TITLE = "title";
	@InjectView(R.id.listView)
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_app_info, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		new InjectViewHelper().injectViews(this);

	}

	@Override
	public void onResume() {
		super.onResume();
		List<AppInfo> appInfos = AppInfoManager.getAppInfos(getActivity());
		listView.setAdapter(new AppInfoAdapter(getActivity(), appInfos));
	}

	public static AppInfoFragment create(String title) {
		AppInfoFragment fragment = new AppInfoFragment();
		Bundle args = new BundleBuilder().putString(TITLE, title).build();
		fragment.setArguments(args);
		return fragment;
	}

	public CharSequence getTitle() {
		return getArguments().getString(TITLE);
	}
}
