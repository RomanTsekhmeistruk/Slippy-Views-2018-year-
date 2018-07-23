package com.romantsekhmeistruk.mobilunitytest.widgets.utils;

import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class AnimationUtil {

	public static void animate(long delay, int visibility, View view, @AnimRes int animRes) {
		animate(delay, visibility, view, animRes, null);
	}

	public static void animate(long delay, int visibility, View view, @AnimRes int animRes, Interpolator interpolator) {
		if (visibility == View.VISIBLE) {
			view.setVisibility(visibility);
		}
		Animation animation = AnimationUtils.loadAnimation(view.getContext(), animRes);
		animation.setInterpolator(interpolator);
		animation.setStartOffset(delay);
		view.startAnimation(animation);
		if (visibility == View.INVISIBLE || visibility == View.GONE) {
			view.setVisibility(visibility);
		}
	}
}
