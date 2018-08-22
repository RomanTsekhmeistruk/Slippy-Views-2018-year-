package com.romantsekhmeistruk.slippyviews.widgets.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class UiUtils {

	public static float dpToPix(Context context, float value) {
		return dpToPix(context.getResources(), value);
	}

	public static float dpToPix(Resources resources, float value) {
		return TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, resources.getDisplayMetrics());
	}
}
