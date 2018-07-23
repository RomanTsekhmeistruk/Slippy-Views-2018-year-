package com.romantsekhmeistruk.mobilunitytest.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.ui.activities.base.BaseActivity;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.RecyclerViewFragment;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.ViewPagerFragment;
import com.romantsekhmeistruk.mobilunitytest.widgets.utils.AnimationUtil;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.FontTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class LaunchActivity extends BaseActivity {

	@BindView(R.id.header)
	FontTextView header;
	@BindView(R.id.subheader)
	FontTextView subheader;
	@BindView(R.id.buttons_container)
	LinearLayout buttonsContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		animate();
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

	private void animate() {
		animateHeaderIn();
		animateSubheaderIn();
		animateButtons();
	}

	private void animateHeaderIn() {
		animateHeaderIn(0);
	}

	private void animateHeaderIn(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, header,
							  R.anim.header_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateSubheaderIn() {
		animateSubheaderIn(400);
	}

	private void animateSubheaderIn(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, subheader,
							  R.anim.header_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateButtons() {
		animateButtons(800);
	}

	private void animateButtons(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, buttonsContainer,
							  R.anim.header_animation_in, new DecelerateInterpolator(5f));
	}
}
