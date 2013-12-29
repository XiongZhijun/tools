/**
 * Copyright © 2014 Xiong Zhijun, All Rights Reserved.
 * Email : hust.xzj@gmail.com
 */
package org.herod.simpleweather;

import org.herod.android.lang.AbstractAdapter;
import org.herod.android.lang.AlertDialogUtils;
import org.herod.android.lang.ViewHelper;
import org.herod.simpleweather.model.CityWeather;
import org.herod.simpleweather.model.WeatherData;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 * 
 */
public class CityWeatherFragment extends Fragment implements
		LoaderCallbacks<CityWeather> {
	/**  */
	private static final int LOADER_ID = 1001;
	private static final String CITY = "city";
	private ListView weatherListView;

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
		weatherListView.setEmptyView(view.findViewById(R.id.emptyView));
	}

	@Override
	public void onStart() {
		super.onStart();
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<CityWeather> onCreateLoader(int id, Bundle args) {
		String city = getArguments().getString(CITY);
		return new CityWeatherLoader(getActivity(), city);
	}

	@Override
	public void onLoadFinished(Loader<CityWeather> loader,
			CityWeather cityWeather) {
		if (cityWeather == null) {
			OnClickListener retryListener = new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					getLoaderManager().restartLoader(LOADER_ID, null,
							CityWeatherFragment.this);
				}
			};
			OnClickListener cancelListener = new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
				}
			};
			AlertDialogUtils.create(getActivity(), R.string.alert,
					R.string.read_weather_data_failed_alert_info,
					R.string.exit_app, cancelListener, R.string.retry,
					retryListener).show();
			return;
		}
		weatherListView.setAdapter(new WeatherAdapater(getActivity(),
				cityWeather));
	}

	@Override
	public void onLoaderReset(Loader<CityWeather> data) {
	}

	public static CityWeatherFragment createFragment(String city) {
		CityWeatherFragment shopListFragment = new CityWeatherFragment();
		Bundle args = new Bundle();
		args.putString(CITY, city);
		shopListFragment.setArguments(args);
		return shopListFragment;
	}

	class WeatherAdapater extends AbstractAdapter<WeatherData> {

		public WeatherAdapater(Context context, CityWeather cityWeather) {
			super(context, cityWeather.getWeatherDatas(), R.layout.weather_item);
		}

		@Override
		protected void bindView(int position, View view, ViewHelper viewHelper) {
			WeatherData weatherData = getItemWithType(position);
			viewHelper.setImageViewValue(R.id.dayImage,
					weatherData.getDayPictureResource(getActivity()));
			viewHelper.setImageViewValue(R.id.nightImage,
					weatherData.getNightPictureResource(getActivity()));
			viewHelper.setTextViewValue(R.id.date, weatherData.getDateText());
			viewHelper.setTextViewValue(R.id.title,
					weatherData.getContentTitle());
			viewHelper.setTextViewValue(R.id.content,
					weatherData.getContentInfo());

		}

	}

	static class CityWeatherLoader extends AsyncTaskLoader<CityWeather> {
		private String city;

		public CityWeatherLoader(Context context, String city) {
			super(context);
			this.city = city;
		}

		public CityWeather loadInBackground() {
			try {
				return WeatherContext.getWeatherLoader().getWeatherByCityName(
						getContext(), city);
			} catch (RuntimeException e) {
				return null;
			}
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}
	}

}
