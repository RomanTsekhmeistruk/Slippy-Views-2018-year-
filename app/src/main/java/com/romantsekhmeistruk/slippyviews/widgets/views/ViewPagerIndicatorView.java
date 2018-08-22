package com.romantsekhmeistruk.slippyviews.widgets.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.romantsekhmeistruk.slippyviews.R;

public class ViewPagerIndicatorView extends LinearLayout {

	private static final int SMALL_INDICATOR_MARGIN_DP = 10;

	private Context context;

	private ViewPager viewPager;
	private RecyclerView recyclerView;

	private int indicatorSize;
	private int indicatorMargin;

	private int lastSelected = -1;

	private ViewPagerColor viewPagerColor = ViewPagerColor.PURPLE;
	private ViewPagerMargin viewPagerMargin = ViewPagerMargin.NORMAL;

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

		this.context = context;

		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);

		indicatorSize = context.getResources().getDimensionPixelSize(R.dimen.indicator_size);
		if (viewPagerMargin.equals(ViewPagerMargin.NORMAL)) {
			indicatorMargin = context.getResources().getDimensionPixelSize(R.dimen.indicator_margin);
		}
		else {
			indicatorMargin = SMALL_INDICATOR_MARGIN_DP;
		}

		drawIndicators(1, 3);
	}

	public void setViewPagerColor(ViewPagerColor color) {
		viewPagerColor = color;
	}

	public void setViewPagerMargin(ViewPagerMargin margin) {
		viewPagerMargin = margin;
		if (viewPagerMargin.equals(ViewPagerMargin.NORMAL)) {
			indicatorMargin = context.getResources().getDimensionPixelSize(R.dimen.indicator_margin);
		}
		else {
			indicatorMargin = SMALL_INDICATOR_MARGIN_DP;
		}
		invalidateIndicators();
	}

	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;

		this.viewPager.addOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						selectIndicator(position);
					}
				});

		invalidateIndicators();
	}

	public void setRecyclerView(RecyclerView recyclerView) {
		this.recyclerView = recyclerView;
		invalidateIndicators(0);
	}

	public void invalidateIndicators() {
		invalidateIndicators(lastSelected);
	}

	public void invalidateIndicators(int currentPage) {
		int pagesCount = -1;
		if (recyclerView != null) {
			pagesCount = recyclerView.getAdapter() != null ? recyclerView.getAdapter().getItemCount() : -1;
		}
		else if (viewPager != null) {
			pagesCount = viewPager.getAdapter() != null ? viewPager.getAdapter().getCount() : -1;
		}
		removeAllViews();
		drawIndicators(currentPage, pagesCount);
	}

	private void drawIndicators(int current, int total) {
		LayoutParams layoutParams = new LayoutParams(indicatorSize, indicatorSize);
		layoutParams.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
		for (int i = 0; i < total; i++) {
			View view = new View(getContext());
			if (viewPagerColor.equals(ViewPagerColor.PURPLE)) {
				view.setBackgroundResource(R.drawable.view_pager_indicator_purple);
			}

			addView(view, layoutParams);
		}

		selectIndicator(current);
	}

	private void selectIndicator(int current) {
		if (lastSelected > -1 && lastSelected < getChildCount()) {
			getChildAt(lastSelected).setSelected(false);
		}
		if (current > -1 && current < getChildCount()) {
			getChildAt(current).setSelected(true);
		}

		lastSelected = current;
	}
}
