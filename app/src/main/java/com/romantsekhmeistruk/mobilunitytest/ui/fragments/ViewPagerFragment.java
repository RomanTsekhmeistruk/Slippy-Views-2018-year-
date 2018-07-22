package com.romantsekhmeistruk.mobilunitytest.ui.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.common.Constants;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.base.BaseAnimatedFragment;
import com.romantsekhmeistruk.mobilunitytest.widgets.adapters.RoomPagerAdapter;
import com.romantsekhmeistruk.mobilunitytest.widgets.utils.UiUtils;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.PagerContainer;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.ViewPagerIndicatorView;

import butterknife.BindView;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

// Strange name? Yes, but understandable within the project
public class ViewPagerFragment extends BaseAnimatedFragment {

	@BindView(R.id.pager_container)
	PagerContainer pagerContainer;
	@BindView(R.id.pager_indicator)
	ViewPagerIndicatorView viewPagerIndicatorView;

	public static ViewPagerFragment newInstance() {
		return new ViewPagerFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_view_pager, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initPagerContainer();
		initViewPagerIndicator();
	}

	private void initPagerContainer() {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);

		final ViewPager pager = pagerContainer.getViewPager();
		RoomPagerAdapter adapter = new RoomPagerAdapter(getContext());
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(adapter.getCount());
		pager.setPageMargin((int) UiUtils.dpToPix(getContext(), getResources().getDimension(R.dimen.view_pager_margin)));
		pager.setClipChildren(false);

		pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				onCenterItemChangedListener.onCenterItemChanged(position);
			}
		});

		pager.setPageTransformer(false, new ViewPager.PageTransformer() {
			private static final float MIN_SCALE = 0.85f;
			private static final float MIN_ALPHA = 0.85f;

			public void transformPage(View view, float position) {
				int pageWidth = view.getWidth();
				int pageHeight = view.getHeight();

				if (position < -1) { // [-Infinity,-1)
				}
				else if (position <= 1) { // [-1,1]
					float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
					float vertMargin = pageHeight * (1 - scaleFactor) / 2;
					float horzMargin = pageWidth * (1 - scaleFactor) / 2;

					if (position < 0) {
						view.setTranslationX(horzMargin - vertMargin / 2);
					}
					else {
						view.setTranslationX(-horzMargin + vertMargin / 2);
					}

					view.setScaleX(scaleFactor);
					view.setScaleY(scaleFactor);

					view.setAlpha(MIN_ALPHA +
								  (scaleFactor - MIN_SCALE) /
								  (1 - MIN_SCALE) * (1 - MIN_ALPHA));
				}
				else { // (1,+Infinity]
					view.setScaleX(MIN_SCALE);
					view.setScaleY(MIN_SCALE);
					view.setAlpha(MIN_SCALE);
				}
			}
		});

		OverScrollDecoratorHelper.setUpOverScroll(pager)
								 .setOverScrollUpdateListener(new IOverScrollUpdateListener() { // Setting page resizing when overscroll

									 private static final float MIN_SCALE = 0.85f;
									 private static final float MIN_ALPHA = 0.85f;

									 @Override
									 public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
										 View transformedView;

										 if (offset > 0) {
											 transformedView = pager.findViewWithTag(Constants.FIRST_PAGE);
										 }
										 else {
											 transformedView = pager.findViewWithTag(Constants.LAST_PAGE);
										 }

										 float a = (float) Math.pow(1.0 - (((Math.abs(offset) * 100) / size.x) / 100), 3);

										 float scale = Math.max(a, MIN_SCALE);
										 float alpha = Math.max(a, MIN_ALPHA);

										 if (transformedView != null) {
											 transformedView.setScaleX(scale);
											 transformedView.setScaleY(scale);
											 transformedView.setAlpha(alpha);
										 }
									 }
								 });
	}

	private void initViewPagerIndicator() {
		viewPagerIndicatorView.setViewPager(pagerContainer.getViewPager());
		viewPagerIndicatorView.invalidateIndicators(0);
	}
}
