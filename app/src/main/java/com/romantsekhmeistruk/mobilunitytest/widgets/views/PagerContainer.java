package com.romantsekhmeistruk.mobilunitytest.widgets.views;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

// Class for making ViewPager pages visible in the sides of screen
public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

	private ViewPager pager;
	boolean needsRedraw = false;

	private Point center = new Point();
	private Point initialTouch = new Point();

	public PagerContainer(Context context) {
		super(context);
		init();
	}

	public PagerContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		//Disable clipping of children so non-selected pages are visible
		setClipChildren(false);

		//Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
		//You need to set this value here if using hardware acceleration in an
		// application targeted at these releases.
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onFinishInflate() {
		try {
			pager = (ViewPager) getChildAt(0);
			pager.setOnPageChangeListener(this);
		}
		catch (Exception e) {
			throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
		}
		super.onFinishInflate();
	}

	public ViewPager getViewPager() {
		return pager;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		center.x = w / 2;
		center.y = h / 2;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//We capture any touches not already handled by the ViewPager
		// to implement scrolling from a touch outside the pager bounds.
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				initialTouch.x = (int) ev.getX();
				initialTouch.y = (int) ev.getY();
			default:
				ev.offsetLocation(center.x - initialTouch.x, center.y - initialTouch.y);
				break;
		}

		return pager.dispatchTouchEvent(ev);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		//Force the container to redraw on scrolling.
		//Without this the outer pages render initially and then stay static
		if (needsRedraw) {
			invalidate();
		}
	}

	@Override
	public void onPageSelected(int position) { }

	@Override
	public void onPageScrollStateChanged(int state) {
		needsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
	}
}
