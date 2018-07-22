package com.romantsekhmeistruk.mobilunitytest.widgets.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.romantsekhmeistruk.mobilunitytest.R;

import java.util.HashMap;

public class TypefaceCache {

	public static final String DEFAULT = "OpenSans-Regular.ttf";

	private static HashMap<String, Typeface> sCache;

	public static Typeface getTypeface(Context context, String typefaceName) {
		if (sCache == null) {
			sCache = new HashMap<>(5);
		}

		Typeface tf = sCache.get(typefaceName);
		if (tf == null) {
			tf = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", typefaceName));
			sCache.put(typefaceName, tf);
		}

		return tf;
	}

	public static Typeface getTypeface(Context context, AttributeSet attrs) {
		String fontName = DEFAULT;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextFont);
		if (a.hasValue(R.styleable.TextFont_textFont)) {
			fontName = a.getString(R.styleable.TextFont_textFont);
		}

		a.recycle();

		return getTypeface(context, fontName);
	}
}
