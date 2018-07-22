package com.romantsekhmeistruk.mobilunitytest.ui.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.ui.fragments.base.BaseAnimatedFragment;
import com.romantsekhmeistruk.mobilunitytest.widgets.adapters.RoomsRecyclerViewAdapter;
import com.romantsekhmeistruk.mobilunitytest.widgets.decorators.ItemCenterDecorator;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.SnappedRecyclerView;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.ViewPagerIndicatorView;

import butterknife.BindView;

// Strange name? Yes, but understandable within the project
public class RecyclerViewFragment extends BaseAnimatedFragment {

	@BindView(R.id.recycler_view)
	SnappedRecyclerView recyclerView;
	@BindView(R.id.pager_indicator)
	ViewPagerIndicatorView viewPagerIndicatorView;

	public static RecyclerViewFragment newInstance() {
		return new RecyclerViewFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recycler_view, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViewPagerIndicatorView();
		initRecyclerView();
	}

	private void initViewPagerIndicatorView() {

	}

	private void initRecyclerView() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);

		RoomsRecyclerViewAdapter adapter = new RoomsRecyclerViewAdapter();
		adapter.add(getString(R.string.living_room), getString(R.string.bedroom), getString(R.string.kitchen));
		recyclerView.setAdapter(adapter);

		viewPagerIndicatorView.setRecyclerView(recyclerView);
		recyclerView.setViewPagerIndicatorView(viewPagerIndicatorView);

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);

		recyclerView.addItemDecoration(new ItemCenterDecorator((int) getResources().getDimension(R.dimen.recycler_view_padding),
															   size.x,
															   getResources().getDimension(R.dimen.item_width)));
	}
}
