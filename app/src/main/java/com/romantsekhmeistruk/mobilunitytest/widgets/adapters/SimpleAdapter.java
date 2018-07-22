package com.romantsekhmeistruk.mobilunitytest.widgets.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Roman Tsekhmeistruk on 09.10.2017.
 */
public abstract class SimpleAdapter<T, VH extends RecyclerView.ViewHolder>
		extends RecyclerView.Adapter<VH> {

	private List<T> mList;

	@Override
	public int getItemCount() {
		return mList != null ? mList.size() : 0;
	}

	public T getItem(int i) {
		return mList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	public void add(T... items) {
		if (mList == null) {
			mList = new ArrayList<>(items.length);
		}

		Collections.addAll(mList, items);

		notifyDataSetChanged();
	}

	public void add(List<T> items) {
		if (mList == null) {
			mList = new ArrayList<>(items.size());
		}

		mList.addAll(items);

		notifyDataSetChanged();
	}

	public List<T> getData() {
		return mList;
	}

	public void removeItem(int position) {
		if (mList != null) {
			mList.remove(position);
			notifyItemRemoved(position);
			notifyItemRangeChanged(position, mList.size());
		}
	}

	public void clearList() {
		if (mList != null) {
			mList.clear();
		}
		notifyDataSetChanged();
	}
}
