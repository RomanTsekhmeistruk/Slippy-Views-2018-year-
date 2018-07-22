package com.romantsekhmeistruk.mobilunitytest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.base.BaseAnimatedFragment;
import com.romantsekhmeistruk.mobilunitytest.widgets.adapters.RoomPagerAdapter;
import com.romantsekhmeistruk.mobilunitytest.widgets.utils.UiUtils;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.PagerContainer;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.ViewPagerIndicatorView;

import butterknife.BindView;

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
	}

	private void initPagerContainer() {
		final ViewPager pager = pagerContainer.getViewPager();
		RoomPagerAdapter adapter = new RoomPagerAdapter(getContext());
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(adapter.getCount());
		pager.setPageMargin((int) UiUtils.dpToPix(getContext(), getResources().getDimension(R.dimen.view_pager_margin)));
		pager.setClipChildren(false);
	}
}
