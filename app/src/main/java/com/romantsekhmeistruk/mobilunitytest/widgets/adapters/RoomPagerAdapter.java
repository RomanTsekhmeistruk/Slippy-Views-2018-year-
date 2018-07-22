package com.romantsekhmeistruk.mobilunitytest.widgets.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.common.Constants;
import com.romantsekhmeistruk.mobilunitytest.widgets.views.FontTextView;

public class RoomPagerAdapter extends PagerAdapter {

	private final int count = 3;
	private final Context context;

	public RoomPagerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == object;
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_room, container, false);

		FontTextView roomName = layout.findViewById(R.id.room_name);
		switch (position) {
			case 0:
				layout.setTag(Constants.FIRST_PAGE);
				roomName.setText(R.string.living_room);
				break;
			case 1:
				roomName.setText(R.string.bedroom);
				break;
			case 2:
				layout.setTag(Constants.LAST_PAGE);
				roomName.setText(R.string.kitchen);
				break;
		}

		container.addView(layout);
		return layout;
	}
}
