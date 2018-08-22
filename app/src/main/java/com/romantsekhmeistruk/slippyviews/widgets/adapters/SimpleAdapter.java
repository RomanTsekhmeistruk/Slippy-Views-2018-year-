package com.romantsekhmeistruk.slippyviews.widgets.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SimpleAdapter<T, VH extends RecyclerView.ViewHolder>
		extends RecyclerView.Adapter<VH> {

	private List<T> list;

	@Override
	public int getItemCount() {
		return list != null ? list.size() : 0;
	}

	public T getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	public void add(T... items) {
		if (list == null) {
			list = new ArrayList<>(items.length);
		}

		Collections.addAll(list, items);

		notifyDataSetChanged();
	}

	public void add(List<T> items) {
		if (list == null) {
			list = new ArrayList<>(items.size());
		}

		list.addAll(items);

		notifyDataSetChanged();
	}

	public List<T> getData() {
		return list;
	}

	public void removeItem(int position) {
		if (list != null) {
			list.remove(position);
			notifyItemRemoved(position);
			notifyItemRangeChanged(position, list.size());
		}
	}

	public void clearList() {
		if (list != null) {
			list.clear();
		}
		notifyDataSetChanged();
	}
}
