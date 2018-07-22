package com.romantsekhmeistruk.mobilunitytest.ui.activities;

import android.os.Bundle;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.ui.activities.base.BaseActivity;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.RecyclerViewFragment;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.ViewPagerFragment;

import butterknife.OnClick;

public class LaunchActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
	}

	@OnClick(R.id.recycler_view)
	public void startRecyclerViewFragment() {
		startFragment(
				RecyclerViewFragment.newInstance(),
				true,
				new int[]{
						R.anim.transform_right_to_left_out,
						R.anim.transform_right_to_left_in,
						R.anim.transform_left_to_right_in,
						R.anim.transform_left_to_right_out
				});
	}

	@OnClick(R.id.view_pager)
	public void startViewPagerFragment() {
		startFragment(
				ViewPagerFragment.newInstance(),
				true,
				new int[]{
						R.anim.transform_right_to_left_out,
						R.anim.transform_right_to_left_in,
						R.anim.transform_left_to_right_in,
						R.anim.transform_left_to_right_out
				});
	}
}
