package com.romantsekhmeistruk.mobilunitytest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.base.BaseAnimatedFragment;

// Strange name? Yes, but understandable within the project
public class ViewPagerFragment extends BaseAnimatedFragment {

	public static ViewPagerFragment newInstance() {
		return new ViewPagerFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_view_pager, container, false);
	}
}
