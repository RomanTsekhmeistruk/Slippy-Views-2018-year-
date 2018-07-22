package com.romantsekhmeistruk.mobilunitytest.widgets.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.romantsekhmeistruk.mobilunitytest.widgets.utils.TypefaceCache;

public class FontTextView extends AppCompatTextView {

	public FontTextView(Context context) {
		this(context, null);
	}

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (!isInEditMode()) {
			setTypeface(TypefaceCache.getTypeface(context, attrs));
		}
	}
}
