package com.romantsekhmeistruk.slippyviews.widgets.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romantsekhmeistruk.slippyviews.R;
import com.romantsekhmeistruk.slippyviews.widgets.views.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomsRecyclerViewAdapter extends SimpleAdapter<String, RecyclerView.ViewHolder> {

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		((ViewHolder) holder).roomName.setText(getItem(position));
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.room_name)
		FontTextView roomName;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
