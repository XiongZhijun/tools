/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import java.util.List;

import org.herod.simpleweather.LocationHelper.OnLocationSuccessListener;
import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.WeatherData;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class CityWeatherFragment extends Fragment implements
		LoaderCallbacks<CityWeather>, OnLocationSuccessListener {

	private ListView weatherListView;
	private BDLocation location;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.city_weather_fragment, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		weatherListView = (ListView) getView().findViewById(R.id.weatherList);
	}

	@Override
	public void onStart() {
		super.onStart();
		new LocationHelper(getActivity(), this).start();
	}

	@Override
	public void onSuccess(LocationHelper locationHelper, BDLocation location) {
		locationHelper.stop();
		this.location = location;
		getLoaderManager().initLoader(1001, null, this);
	}

	@Override
	public Loader<CityWeather> onCreateLoader(int id, Bundle args) {
		return new CityWeatherLoader(getActivity(), location);
	}

	@Override
	public void onLoadFinished(Loader<CityWeather> loader,
			CityWeather cityWeather) {
		weatherListView.setAdapter(new WeatherAdapater(getActivity(),
				cityWeather));
	}

	@Override
	public void onLoaderReset(Loader<CityWeather> data) {
	}

	class WeatherAdapater extends BaseAdapter {
		private List<WeatherData> weatherDatas;
		private LayoutInflater inflater;

		public WeatherAdapater(Context context, CityWeather cityWeather) {
			super();
			weatherDatas = cityWeather.getWeatherDatas();
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return weatherDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return weatherDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = inflater.inflate(R.layout.weather_item, null);
			}
			bindView(v, position);
			return v;
		}

		private void bindView(View v, int position) {
			WeatherData weatherData = weatherDatas.get(position);
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			imageView.setImageResource(weatherData.getCurrentPictureResource());
			setText(v, R.id.date, weatherData.getDateText());
			setText(v, R.id.title, weatherData.getContentTitle());
			setText(v, R.id.content, weatherData.getContentInfo());
		}

		private void setText(View v, int id, String text) {
			TextView textView = (TextView) v.findViewById(id);
			textView.setText(text);
		}

	}

	static class CityWeatherLoader extends AsyncTaskLoader<CityWeather> {

		private BDLocation location;

		public CityWeatherLoader(Context context, BDLocation location) {
			super(context);
			this.location = location;
		}

		public CityWeather loadInBackground() {
			return WeatherContext.getWeatherLoader().getWeatherByLocation(
					getContext(), location);
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}
	}

}
