/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
		listView = (ListView) findViewById(R.id.listView);
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
		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		new AlertDialog.Builder(this).setTitle("添加城市").setView(view)
				.setNegativeButton("取消", cancelListener)
				.setPositiveButton("添加", addListener).create().show();
	}

	class CityAdapter extends BaseAdapter {
		private List<String> cities;
		private LayoutInflater inflater;

		public CityAdapter(Context context, List<String> cities) {
			super();
			this.cities = cities;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return cities.size();
		}

		@Override
		public Object getItem(int position) {
			return cities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = inflater.inflate(R.layout.city_item, null);
			}
			bindView(v, position);
			return v;
		}

		private void bindView(View v, int position) {
			String city = cities.get(position);
			TextView nameView = (TextView) v.findViewById(R.id.name);
			nameView.setText(city);
			v.findViewById(R.id.deleteButton).setOnClickListener(
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
			DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
			DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					WeatherContext.getCityService().deleteCity(context, city);
					getLoaderManager().restartLoader(LOADER_ID, null, context);
					dialog.dismiss();
				}
			};
			new AlertDialog.Builder(context).setTitle("提示")
					.setMessage("确定删除“" + city + "”吗？")
					.setNegativeButton("取消", cancelListener)
					.setPositiveButton("确定", deleteListener).create().show();

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
