package org.herod.house;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		initEditText(preferences, R.id.landlordTotalPrice);
		initEditText(preferences, R.id.floorSpace);
		initEditText(preferences, R.id.bankOfEvaluatingPrice);
		initEditText(preferences, R.id.houseManagerOfEvaluatingPrice);
		initEditText(preferences, R.id.loanRatio);
		initEditText(preferences, R.id.zf);
		initEditText(preferences, R.id.hkTimes);

		initCheckBox(preferences, R.id.fiveYears);
		initCheckBox(preferences, R.id.onlyHouse);
	}

	private void initEditText(SharedPreferences preferences, int id) {
		String value = preferences.getString(id + "", null);
		if (StringUtils.isNotBlank(value)) {
			setText(id, Double.parseDouble(value));
		}
	}

	private void initCheckBox(SharedPreferences preferences, int id) {
		CheckBox cb = (CheckBox) findViewById(id);
		cb.setChecked(preferences.getBoolean(id + "", false));
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	public void calculate(View view) {
		// 房东总房款
		double zfk = getValue(R.id.landlordTotalPrice) * 10000;
		// 面积
		double floorSpace = getValue(R.id.floorSpace);
		// 银行评估价
		double bankOfEvaluatingPrice = getValue(R.id.bankOfEvaluatingPrice);
		// 房管局评估价
		double fgjpgj = getValue(R.id.houseManagerOfEvaluatingPrice);
		// 贷款比例
		double loanRatio = getValue(R.id.loanRatio);
		// 杂费
		double zf = getValue(R.id.zf);
		// 还款次数
		double times = getValue(R.id.hkTimes) * 12;

		// 贷款评估总价
		double dkpgzj = floorSpace * bankOfEvaluatingPrice;
		setText(R.id.dkpgzj, dkpgzj);
		// 贷款总额
		double dkze = Math.floor(dkpgzj * loanRatio / 10000) * 10000;
		setText(R.id.loanAmount, dkze);

		// 首付
		double sfk = zfk - dkze;

		// 契税
		double qs;
		if (!isChecked(R.id.onlyHouse)) {
			qs = getPreference(R.string.fwyqs, R.string.fwyqsValue);
		} else if (floorSpace <= 90) {
			qs = getPreference(R.string.xy90qs, R.string.xy90qsValue);
		} else {
			qs = getPreference(R.string.ptqs, R.string.ptqsValue);
		}
		// 营业税
		double yys = isChecked(R.id.fiveYears) ? 0 : getPreference(
				R.string.yys, R.string.yysValue);

		// 费用
		double fy = fgjpgj
				* floorSpace
				* (qs + yys + getPreference(R.string.grsds, R.string.grsdsValue))
				/ 100.0d + dkze
				* getPreference(R.string.dbf, R.string.dbfValue) / 100.0d
				+ dkpgzj * getPreference(R.string.pgf, R.string.pgfValue)
				/ 100.0d;
		// 中介费
		double zjf = zfk * getPreference(R.string.zjf, R.string.zjfValue)
				/ 100.0d;

		setText(R.id.sfk, sfk);
		setText(R.id.sf, fy);
		setText(R.id.zjf, zjf);
		setText(R.id.hjsfk, sfk + fy + zjf + zf);

		setText(R.id.sdyg,
				payYG(dkze,
						getPreference(R.string.sydk, R.string.sydkValue) / 100.0d,
						times));
		setText(R.id.gjjyg,
				payYG(dkze,
						getPreference(R.string.gjjdk, R.string.gjjdkValue) / 100.0d,
						times));
	}

	public static double payYG(double loanAmount, double ret, double times) {
		double rate = ret / 12;
		double trate = rate + 1.0d;
		for (int i = 0; i < times - 1; i++) {
			trate *= (rate + 1.0d);
		}
		return (loanAmount * trate * rate) / (trate - 1.0d);
	}

	private void setText(int id, double value) {
		TextView editText = (TextView) findViewById(id);
		editText.setText(String.format("%1$.2f", value));
	}

	private boolean isChecked(int id) {
		CheckBox checkBox = (CheckBox) findViewById(id);
		boolean checked = checkBox.isChecked();
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this)
				.edit();
		edit.putBoolean(id + "", checked);
		edit.commit();
		return checked;
	}

	private double getValue(int id) {
		EditText editText = (EditText) findViewById(id);
		String text = editText.getText().toString();
		double value;
		if (StringUtils.isBlank(text)) {
			value = 0;
		} else {
			value = Double.parseDouble(text);
		}
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this)
				.edit();
		edit.putString(id + "", value + "");
		edit.commit();

		return value;
	}

	private double getPreference(int key, int defaultValue) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String value = preferences.getString(getString(key), null);
		if (value == null) {
			value = getString(defaultValue);
		}
		return Double.parseDouble(value);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			startActivity(new Intent(this, SettingActivity.class));
		} else if (item.getItemId() == R.id.share) {
			File file = FileUtils.getLocalPictureFile(this, "tempImage.png");
			Utils.shoot(this, file);
			sharePhoto(file, this);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	public void sharePhoto(File photoFile, final Activity activity) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
		shareIntent.setType("image/jpeg");
		startActivity(Intent.createChooser(shareIntent, activity.getTitle()));
	}
}
