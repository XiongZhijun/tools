/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import os.cherry.android.lang.AbstractAdapter;
import os.cherry.android.lang.ActivityUtils;
import os.cherry.android.lang.AlertDialogUtils;
import os.cherry.android.lang.ViewHelper;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class CityManagerActivity extends Activity implements
		LoaderCallbacks<List<String>> {
	private static final int LOADER_ID = 2;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cities_activity);
		setTitle("城市管理");
		listView = (ListView) findViewById(R.id.listView);
		ActivityUtils.showBackAction(this, true);
	}


	@Override
	protected void onStart() {
		super.onStart();
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<List<String>> onCreateLoader(int id, Bundle args) {
		return new CitiesLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<List<String>> loader, List<String> cities) {
		listView.setAdapter(new CityAdapter(this, cities));
	}

	@Override
	public void onLoaderReset(Loader<List<String>> loader) {
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return false;
	}

	public void addCity(View v) {
		final CityManagerActivity context = this;
		final EditText view = new EditText(this);
		DialogInterface.OnClickListener addListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				WeatherContext.getCityService().addCity(context,
						view.getText().toString());
				getLoaderManager().restartLoader(LOADER_ID, null, context);
				dialog.dismiss();
			}
		};
		AlertDialogUtils.create(context, "添加城市", view, "添加", addListener)
				.show();
	}

	class CityAdapter extends AbstractAdapter<String> {

		public CityAdapter(Context context, List<String> cities) {
			super(context, cities, R.layout.city_item);
		}

		@Override
		protected void bindView(int position, View view, ViewHelper viewHelper) {
			String city = getItemWithType(position);
			viewHelper.setTextViewValue(R.id.name, city);
			viewHelper.setOnClickListener(R.id.deleteButton,
					new DeleteCityListener(city));
		}

	}

	class DeleteCityListener implements OnClickListener {
		private String city;

		public DeleteCityListener(String city) {
			this.city = city;
		}

		@Override
		public void onClick(View v) {
			final CityManagerActivity context = CityManagerActivity.this;
			DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					WeatherContext.getCityService().deleteCity(context, city);
					getLoaderManager().restartLoader(LOADER_ID, null, context);
					dialog.dismiss();
				}
			};
			AlertDialogUtils.create(context, "提示", "确定删除“" + city + "”吗？",
					"确定", deleteListener).show();
		}

	}

	static class CitiesLoader extends AsyncTaskLoader<List<String>> {

		public CitiesLoader(Context context) {
			super(context);
		}

		@Override
		public List<String> loadInBackground() {
			Set<String> citySet = WeatherContext.getCityService().getCities(
					getContext());
			List<String> cities = new ArrayList<String>(citySet);
			Collections.sort(cities);
			return cities;
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

	}
}
