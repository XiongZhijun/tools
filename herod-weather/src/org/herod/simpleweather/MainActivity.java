/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.simpleweather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.herod.android.indicator.TabPageIndicator;
import org.herod.simpleweather.LocationHelper.OnLocationSuccessListener;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.location.BDLocation;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class MainActivity extends FragmentActivity implements
		LoaderCallbacks<List<String>>, OnLocationSuccessListener {
	private ViewPager mPager;
	private TabPageIndicator mIndicator;
	private BDLocation location;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(this, WeatherService.class));
		mPager = (ViewPager) findViewById(R.id.pager);
		mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
	}

	@Override
	protected void onStart() {
		super.onStart();
		new LocationHelper(this, this).start();
		progressDialog = ProgressDialog.show(this, "提示", "定位中……");
	}

	@Override
	protected void onStop() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		super.onStop();
	}

	@Override
	public void onSuccess(LocationHelper locationHelper, BDLocation location) {
		locationHelper.stop();
		this.location = location;
		getSupportLoaderManager().initLoader(1, null, this);
	}

	@Override
	public void onFail(final LocationHelper locationHelper, BDLocation location) {
		locationHelper.stop();
		OnClickListener retryListener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				locationHelper.start();
			}
		};
		OnClickListener cancelListener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		};
		new AlertDialog.Builder(this).setTitle("提示")
				.setMessage("定位失败，请检查网络后重试！")
				.setNegativeButton("退出程序", cancelListener)
				.setPositiveButton("重试", retryListener).create().show();
	}

	@Override
	public Loader<List<String>> onCreateLoader(int id, Bundle args) {
		return new CitiesLoader(this, location);
	}

	@Override
	public void onLoadFinished(Loader<List<String>> loader, List<String> cities) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		CityWeatherFragmentAdapter mAdapter = new CityWeatherFragmentAdapter(
				cities);
		mPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mPager);
		mIndicator.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<String>> loader) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.cities_manager) {
			startActivity(new Intent(this, CityManagerActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class CityWeatherFragmentAdapter extends FragmentPagerAdapter {

		private List<String> cities;

		public CityWeatherFragmentAdapter(List<String> cities) {
			super(getSupportFragmentManager());
			this.cities = cities;
		}

		public Fragment getItem(int position) {
			return CityWeatherFragment.createFragment(cities.get(position));
		}

		public int getCount() {
			return cities != null ? cities.size() : 0;
		}

		public CharSequence getPageTitle(int position) {
			return cities.get(position);
		}
	}

	static class CitiesLoader extends AsyncTaskLoader<List<String>> {
		private BDLocation location;

		public CitiesLoader(Context context, BDLocation location) {
			super(context);
			this.location = location;
		}

		public List<String> loadInBackground() {
			List<String> cities = new ArrayList<String>();
			cities.addAll(WeatherContext.getCityService().getCities(
					getContext()));
			Collections.sort(cities);
			cities.add(0, location.getCity());
			return cities;
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

	}

}
