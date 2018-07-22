package com.romantsekhmeistruk.mobilunitytest.ui.activities.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.romantsekhmeistruk.mobilunitytest.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {

	private Unbinder unbinder;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		unbinder = ButterKnife.bind(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	public void startFragment(Fragment fragment, boolean addToBackStack) {
		startFragment(fragment, addToBackStack, null);
	}

	public void startFragment(Fragment fragment, boolean addToBackStack, int animation[]) {
		String tag = fragment.getClass().getSimpleName();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (animation != null) {
			fragmentTransaction.setCustomAnimations(
					animation[0], animation[1], animation[2], animation[3]);
		}
		fragmentTransaction
				.replace(R.id.container, fragment, tag)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		if (addToBackStack) {
			fragmentTransaction.addToBackStack(tag);
		}
		fragmentTransaction.commit();
	}
}
