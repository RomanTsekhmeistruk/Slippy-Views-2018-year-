package com.romantsekhmeistruk.slippyviews.ui.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.slippyviews.R;
import com.romantsekhmeistruk.slippyviews.ui.fragments.base.BaseAnimatedFragment;
import com.romantsekhmeistruk.slippyviews.widgets.adapters.RoomsRecyclerViewAdapter;
import com.romantsekhmeistruk.slippyviews.widgets.decorators.ItemCenterDecorator;
import com.romantsekhmeistruk.slippyviews.widgets.views.SnappedRecyclerView;
import com.romantsekhmeistruk.slippyviews.widgets.views.ViewPagerIndicatorView;

import butterknife.BindView;
import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter;

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
		initRecyclerView();
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

		recyclerView.setOnCenterItemChangedListener(onCenterItemChangedListener);

		new HorizontalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(recyclerView))
				.setOverScrollUpdateListener(
						new IOverScrollUpdateListener() { // Setting page resizing when overscroll

							private static final float MIN_SCALE = 0.85f;
							private static final float MIN_ALPHA = 0.7f;

							@Override
							public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
								View transformedView;

								if (offset > 0) {
									transformedView = recyclerView.getChildAt(0);
								}
								else {
									transformedView = recyclerView.getChildAt(recyclerView.getAdapter().getItemCount() - 1);
								}

								float scale = (float) 1.0 - (((Math.abs(offset) * 100) / size.x) / 100);
								float alpha = (float) Math.pow(scale, 3);

								scale = Math.max(scale, MIN_SCALE);
								alpha = Math.max(alpha, MIN_ALPHA);

								if (transformedView != null) {
									transformedView.setScaleX(scale);
									transformedView.setScaleY(scale);
									transformedView.setAlpha(alpha);
								}
							}
						});
	}
}
