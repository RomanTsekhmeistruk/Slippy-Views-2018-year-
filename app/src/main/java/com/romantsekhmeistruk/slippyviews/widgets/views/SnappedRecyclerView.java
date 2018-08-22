package com.romantsekhmeistruk.slippyviews.widgets.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.romantsekhmeistruk.slippyviews.common.OnCenterItemChangedListener;

public class SnappedRecyclerView extends RecyclerView {

	public static final int SCROLL_DIRECTION_LEFT = 0;

	public static final int SCROLL_DIRECTION_RIGHT = 1;

	private int scrollDirection;
	private OnCenterItemChangedListener centerItemChangedListener;

	private float minScale = 0.9f;
	private float minAlpha = 0.7f;

	private ViewPagerIndicatorView viewPagerIndicatorView;

	// flag to handle ViewPagerIndicatorView invalidation.
	// Needed to some devices, for which ViewPagerIndicatorView is not visible without this handling
	private boolean wasRecyclerViewScrolled = false;

	public SnappedRecyclerView(Context context) {
		super(context);
	}

	public SnappedRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SnappedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onScrolled(int dx, int dy) {
		super.onScrolled(dx, dy);
		setScrollDirection(dx);

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			float percentage = getPercentageFromCenter(child);
			float scale = 1f - (0.4f * percentage);

			scale = Math.max(scale, minScale);

			child.setScaleX(scale);
			child.setScaleY(scale);

			float alpha = (float) Math.pow(1f - (0.4f * percentage), 3);
			alpha = Math.max(alpha, minAlpha);
			child.setAlpha(alpha);

			if (scale > minScale) {
				if (viewPagerIndicatorView != null && wasRecyclerViewScrolled) {
					viewPagerIndicatorView.invalidateIndicators(getChildLayoutPosition(child));
				}
				if (centerItemChangedListener != null) {
					centerItemChangedListener.onCenterItemChanged(getChildLayoutPosition(child));
				}
			}
		}
	}

	@Override
	public boolean fling(int velocityX, int velocityY) {
		smoothScrollToCenter();
		wasRecyclerViewScrolled = true;

		return true;
	}

	public void setViewPagerIndicatorView(ViewPagerIndicatorView viewPagerIndicatorView) {
		this.viewPagerIndicatorView = viewPagerIndicatorView;
	}

	public void setMinScale(float minScale) {
		this.minScale = minScale;
	}

	public void setMinAlpha(float alpha) {
		this.minAlpha = alpha;
	}

	private float getPercentageFromCenter(View child) {
		float centerX = (getMeasuredWidth() / 2);
		float childCenterX = child.getX() + (child.getWidth() / 2);
		float offSet = Math.max(centerX, childCenterX) - Math.min(centerX, childCenterX);
		int maxOffset = (getMeasuredWidth() / 2) + child.getWidth();
		return (offSet / maxOffset);
	}

	private int findCenterViewIndex() {
		int count = getChildCount();
		int index = -1;
		int closest = Integer.MAX_VALUE;
		int centerX = (getMeasuredWidth() / 2);

		for (int i = 0; i < count; ++i) {
			View child = getLayoutManager().getChildAt(i);
			int childCenterX = (int) (child.getX() + (child.getWidth() / 2));
			int distance = Math.abs(centerX - childCenterX);
			if (distance < closest) {
				closest = distance;
				index = i;
			}
		}

		if (index == -1) {
			throw new IllegalStateException("Can\'t find central view.");
		}
		else {
			return index;
		}
	}

	private void smoothScrollToCenter() {
		LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
		int lastVisibleView = linearLayoutManager.findLastVisibleItemPosition();
		int firstVisibleView = linearLayoutManager.findFirstVisibleItemPosition();
		View firstView = linearLayoutManager.findViewByPosition(firstVisibleView);
		View lastView = linearLayoutManager.findViewByPosition(lastVisibleView);
		int screenWidth = this.getWidth();

		//since views have variable sizes, we need to calculate side margins separately.
		int leftMargin = (screenWidth - lastView.getWidth()) / 2;
		int rightMargin = (screenWidth - firstView.getWidth()) / 2 + firstView.getWidth();
		int leftEdge = lastView.getLeft();
		int rightEdge = firstView.getRight();
		int scrollDistanceLeft = leftEdge - leftMargin;
		int scrollDistanceRight = rightMargin - rightEdge;

		if (scrollDirection == SCROLL_DIRECTION_LEFT) {
			smoothScrollBy(scrollDistanceLeft, 0);
		}
		else if (scrollDirection == SCROLL_DIRECTION_RIGHT) {
			smoothScrollBy(-scrollDistanceRight, 0);
		}
	}

	public int getScrollDirection() {
		return scrollDirection;
	}

	private void setScrollDirection(int dx) {
		scrollDirection = dx >= 0 ? SCROLL_DIRECTION_LEFT : SCROLL_DIRECTION_RIGHT;
	}

	public void setOnCenterItemChangedListener(OnCenterItemChangedListener listener) {
		centerItemChangedListener = listener;
	}
}
