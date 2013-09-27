package org.herod.tools.appmanager;

import java.util.ArrayList;
import java.util.List;

import org.herod.framework.ci.InjectViewHelper;
import org.herod.framework.ci.annotation.InjectView;
import org.herod.framework.widget.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	@InjectView(R.id.pager)
	private ViewPager mPager;
	@InjectView(R.id.indicator)
	private TitlePageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new InjectViewHelper().injectViews(this);
		List<AppInfoFragment> fragments = createFragments();
		mPager.setAdapter(new AppInfoPagerAdapter(getSupportFragmentManager(),
				fragments));
		mIndicator.setViewPager(mPager);
	}

	private List<AppInfoFragment> createFragments() {
		List<AppInfoFragment> fragments = new ArrayList<AppInfoFragment>();
		fragments.add(AppInfoFragment.create("已安装应用"));
		return fragments;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class AppInfoPagerAdapter extends FragmentPagerAdapter {
		private List<AppInfoFragment> fragments;

		public AppInfoPagerAdapter(FragmentManager fm,
				List<AppInfoFragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		public int getCount() {
			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragments.get(position).getTitle();
		}
	}
}
