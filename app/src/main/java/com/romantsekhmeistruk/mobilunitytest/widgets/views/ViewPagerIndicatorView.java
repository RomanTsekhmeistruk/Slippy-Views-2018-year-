package com.romantsekhmeistruk.mobilunitytest.widgets.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.romantsekhmeistruk.mobilunitytest.R;

public class ViewPagerIndicatorView extends LinearLayout {

	private static final int SMALL_INDICATOR_MARGIN_DP = 10;

	private Context mContext;

	private ViewPager mViewPager;
	private RecyclerView mRecyclerView;

	private int mIndicatorSize;
	private int mIndicatorMargin;

	private int mLastSelected = -1;

	private ViewPagerColor mViewPagerColor = ViewPagerColor.PURPLE;
	private ViewPagerMargin mViewPagerMargin = ViewPagerMargin.NORMAL;

	public enum ViewPagerColor {
		PURPLE
	}

	public enum ViewPagerMargin {
		NORMAL
	}

	public ViewPagerIndicatorView(Context context) {
		this(context, null);
	}

	public ViewPagerIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);

		mIndicatorSize = context.getResources().getDimensionPixelSize(R.dimen.indicator_size);
		if (mViewPagerMargin.equals(ViewPagerMargin.NORMAL)) {
			mIndicatorMargin = context.getResources().getDimensionPixelSize(R.dimen.indicator_margin);
		}
		else {
			mIndicatorMargin = SMALL_INDICATOR_MARGIN_DP;
		}

		drawIndicators(1, 3);
	}

	public void setViewPagerColor(ViewPagerColor color) {
		mViewPagerColor = color;
	}

	public void setViewPagerMargin(ViewPagerMargin margin) {
		mViewPagerMargin = margin;
		if (mViewPagerMargin.equals(ViewPagerMargin.NORMAL)) {
			mIndicatorMargin = mContext.getResources().getDimensionPixelSize(R.dimen.indicator_margin);
		}
		else {
			mIndicatorMargin = SMALL_INDICATOR_MARGIN_DP;
		}
		invalidateIndicators();
	}

	public void setViewPager(ViewPager viewPager) {
		mViewPager = viewPager;

		mViewPager.addOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						selectIndicator(position);
					}
				});

		invalidateIndicators();
	}

	public void setRecyclerView(RecyclerView recyclerView) {
		mRecyclerView = recyclerView;
		invalidateIndicators(0);
	}

	public void invalidateIndicators() {
		invalidateIndicators(mLastSelected);
	}

	public void invalidateIndicators(int currentPage) {
		int pagesCount = -1;
		if (mRecyclerView != null) {
			pagesCount = mRecyclerView.getAdapter() != null ? mRecyclerView.getAdapter().getItemCount() : -1;
		}
		else if (mViewPager != null) {
			pagesCount = mViewPager.getAdapter() != null ? mViewPager.getAdapter().getCount() : -1;
		}
		removeAllViews();
		drawIndicators(currentPage, pagesCount);
	}

	private void drawIndicators(int current, int total) {
		LayoutParams layoutParams = new LayoutParams(mIndicatorSize, mIndicatorSize);
		layoutParams.setMargins(mIndicatorMargin, mIndicatorMargin, mIndicatorMargin, mIndicatorMargin);
		for (int i = 0; i < total; i++) {
			View view = new View(getContext());
			if (mViewPagerColor.equals(ViewPagerColor.PURPLE)) {
				view.setBackgroundResource(R.drawable.view_pager_indicator_purple);
			}

			addView(view, layoutParams);
		}

		selectIndicator(current);
	}

	private void selectIndicator(int current) {
		if (mLastSelected > -1 && mLastSelected < getChildCount()) {
			getChildAt(mLastSelected).setSelected(false);
		}
		if (current > -1 && current < getChildCount()) {
			getChildAt(current).setSelected(true);
		}

		mLastSelected = current;
	}
}
