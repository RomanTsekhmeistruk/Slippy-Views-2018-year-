package com.romantsekhmeistruk.mobilunitytest.widgets.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemCenterDecorator extends RecyclerView.ItemDecoration {

	private final int padding;
	private final int totalWidth;
	private final float itemWidth;

	public ItemCenterDecorator(int padding, int totalWidth, float itemWidth) {
		this.padding = padding;
		this.totalWidth = totalWidth;
		this.itemWidth = itemWidth;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
							   RecyclerView.State state) {
		if (parent.getChildAdapterPosition(view) == 0) {
			int firstPadding = (int) (this.totalWidth - itemWidth) / 2;
			firstPadding = Math.max(0, firstPadding);
			outRect.set(firstPadding, 0, 0, 0);
		}
		else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1 &&
				 parent.getAdapter().getItemCount() > 1) {
			int lastPadding = (parent.getWidth() - padding) / 2;
			lastPadding = Math.max(0, lastPadding);
			outRect.set(0, 0, lastPadding, 0);
		}
	}
}
