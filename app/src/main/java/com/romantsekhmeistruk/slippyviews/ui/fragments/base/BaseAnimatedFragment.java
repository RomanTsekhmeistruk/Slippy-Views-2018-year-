package com.romantsekhmeistruk.slippyviews.ui.fragments.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.romantsekhmeistruk.slippyviews.R;
import com.romantsekhmeistruk.slippyviews.common.OnCenterItemChangedListener;
import com.romantsekhmeistruk.slippyviews.widgets.utils.AnimationUtil;
import com.romantsekhmeistruk.slippyviews.widgets.views.FontTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class BaseAnimatedFragment extends BaseFragment {

	@BindView(R.id.header)
	FontTextView header;
	@BindView(R.id.scrollable_container)
	LinearLayout scrollableContainer;

	// Living Room Images
	@BindView(R.id.frame_image)
	ImageView frameImage;
	@BindView(R.id.lamp_image)
	ImageView lampImage;
	@BindView(R.id.sofa_image)
	ImageView sofaImage;
	@BindView(R.id.plant_image)
	ImageView plantImage;
	@BindView(R.id.table_image)
	ImageView tableImage;
	@BindView(R.id.tv_image)
	ImageView tvImage;

	// Kitchen Images
	@BindView(R.id.kitchen_island)
	ImageView kitchenIsland;
	@BindView(R.id.kitchen_shelf)
	ImageView kitchenShelf;
	@BindView(R.id.kitchen_frame)
	ImageView kitchenFrame;
	@BindView(R.id.kitchen_flower)
	ImageView kitchenFlower;
	@BindView(R.id.kitchen_pan)
	ImageView kitchenPan;
	@BindView(R.id.kitchen_stool)
	ImageView kitchenStool;

	// Bedroom Images
	@BindView(R.id.bedroom_room_frame)
	ImageView bedroomFrame;
	@BindView(R.id.bedroom_bed)
	ImageView bedroomBed;
	@BindView(R.id.bedroom_plant)
	ImageView bedroomPlant;
	@BindView(R.id.bedroom_table)
	ImageView bedroomTable;
	@BindView(R.id.bedroom_books)
	ImageView bedroomBooks;
	@BindView(R.id.bedroom_lamp)
	ImageView bedroomLamp;

	// Rooms Containers
	@BindView(R.id.living_room)
	RelativeLayout livingRoomImages;
	@BindView(R.id.bedroom)
	RelativeLayout bedroomImages;
	@BindView(R.id.kitchen)
	RelativeLayout kitchenImages;

	// Animations Helpers
	private Handler kitchenAnimateOutHandler;
	private Handler bedroomAnimateOutHandler;
	private Handler livingRoomAnimateOutHandler;
	private Handler kitchenAnimateInHandler;
	private Handler bedroomAnimateInHandler;
	private Handler livingRoomAnimateInHandler;
	private Runnable animateOutRunnable;
	private Runnable kitchenAnimateInRunnable;
	private Runnable bedroomAnimateInRunnable;
	private Runnable livingRoomAnimateInRunnable;

	protected OnCenterItemChangedListener onCenterItemChangedListener;

	private int lastShowedRoom;
	private boolean wasNewAnimationStarted;

	private int animationOutDuration;

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initialize();
		animate();
	}

	@Override
	public void onPause() {
		super.onPause();
		clearAnimations();
	}

	@OnClick(R.id.back)
	public void onBackPressed() {
		getActivity().onBackPressed();
	}

	private void initOnCenterItemChangedListener() {
		onCenterItemChangedListener = centerPosition -> {
			if (centerPosition == lastShowedRoom) {
				return;
			}

			if (wasNewAnimationStarted) {
				animateOut();
				wasNewAnimationStarted = false;
			}

			clearAnimations();
			switch (centerPosition) {
				case 0:
					lastShowedRoom = 0;
					livingRoomAnimateInHandler.postDelayed(livingRoomAnimateInRunnable, animationOutDuration);
					break;
				case 1:
					lastShowedRoom = 1;
					bedroomAnimateInHandler.postDelayed(bedroomAnimateInRunnable, animationOutDuration);
					break;
				case 2:
					lastShowedRoom = 2;
					kitchenAnimateInHandler.postDelayed(kitchenAnimateInRunnable, animationOutDuration);
					break;
			}
		};
	}

	private void initializeAnimationHelpers() {
		kitchenAnimateOutHandler = new Handler();
		bedroomAnimateOutHandler = new Handler();
		livingRoomAnimateOutHandler = new Handler();
		kitchenAnimateInHandler = new Handler();
		bedroomAnimateInHandler = new Handler();
		livingRoomAnimateInHandler = new Handler();

		animateOutRunnable = this::animateOut;
		kitchenAnimateInRunnable = this::startKitchen;
		bedroomAnimateInRunnable = this::startBedroom;
		livingRoomAnimateInRunnable = this::startLivingRoom;
	}

	protected void clearAnimations() {
		kitchenAnimateOutHandler.removeCallbacks(animateOutRunnable);
		bedroomAnimateOutHandler.removeCallbacks(animateOutRunnable);
		livingRoomAnimateOutHandler.removeCallbacks(animateOutRunnable);
		kitchenAnimateInHandler.removeCallbacks(kitchenAnimateInRunnable);
		bedroomAnimateInHandler.removeCallbacks(bedroomAnimateInRunnable);
		livingRoomAnimateInHandler.removeCallbacks(livingRoomAnimateInRunnable);
	}

	private void initialize() {
		initializeAnimationHelpers();
		initOnCenterItemChangedListener();

		animationOutDuration = getResources().getInteger(R.integer.animation_out_duration);
	}

	private void animate() {
		animateIn();
		startLivingRoom();
		animateHeaderIn();
		animateScrollableContainerIn();
	}

	private void animateHeaderIn() {
		animateHeaderIn(0);
	}

	private void animateHeaderIn(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, header,
							  R.anim.header_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateScrollableContainerIn() {
		animateScrollableContainerIn(200);
	}

	private void animateScrollableContainerIn(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, scrollableContainer,
							  R.anim.scrollable_container_animation_in, new DecelerateInterpolator(5f));
	}

	protected void animateIn() {
		animateIn(0);
	}

	protected void animateIn(long delay) {
		if (!isViewsAvailable()) {
			return;
		}

		wasNewAnimationStarted = true;

		if (livingRoomImages.getVisibility() == View.VISIBLE) {
			animateLivingRoomIn(delay);
			return;
		}
		if (kitchenImages.getVisibility() == View.VISIBLE) {
			animateKitchenIn(delay);
			return;
		}
		if (bedroomImages.getVisibility() == View.VISIBLE) {
			animateBedroomIn(delay);
			return;
		}
	}

	private boolean isViewsAvailable() {
		if (livingRoomImages == null) {
			return false;
		}
		if (kitchenImages == null) {
			return false;
		}
		if (bedroomImages == null) {
			return false;
		}
		return true;
	}

	protected void startLivingRoom() {
		if (!isViewsAvailable()) {
			return;
		}
		kitchenImages.setVisibility(View.INVISIBLE);
		bedroomImages.setVisibility(View.INVISIBLE);
		livingRoomImages.setVisibility(View.VISIBLE);
		animateIn();
	}

	protected void startKitchen() {
		if (!isViewsAvailable()) {
			return;
		}
		bedroomImages.setVisibility(View.INVISIBLE);
		livingRoomImages.setVisibility(View.INVISIBLE);
		kitchenImages.setVisibility(View.VISIBLE);
		animateIn();
	}

	protected void startBedroom() {
		if (!isViewsAvailable()) {
			return;
		}
		kitchenImages.setVisibility(View.INVISIBLE);
		livingRoomImages.setVisibility(View.INVISIBLE);
		bedroomImages.setVisibility(View.VISIBLE);
		animateIn();
	}

	protected void animateOut() {
		animateOut(0);
	}

	protected void animateOut(long delay) {
		if (!isViewsAvailable()) {
			return;
		}
		if (livingRoomImages.getVisibility() == View.VISIBLE) {
			animateLivingRoomOut(delay);
			return;
		}
		if (kitchenImages.getVisibility() == View.VISIBLE) {
			animateKitchenOut(delay);
			return;
		}
		if (bedroomImages.getVisibility() == View.VISIBLE) {
			animateBedroomOut(delay);
			return;
		}
	}

	private void animateBedroomIn(long delay) {
		animateInBedroomFrame(delay);
		animateInBedroomBed(delay);
		animateInBedroomPlant(delay);
		animateInBedroomTable(delay);
		animateInBedroomBooks(delay);
		animateInBedroomLamp(delay);
	}

	private void animateBedroomOut(long delay) {
		animateOutBedroomFrame(delay);
		animateOutBedroomBed(delay);
		animateOutBedroomPlant(delay);
		animateOutBedroomTable(delay);
		animateOutBedroomBooks(delay);
		animateOutBedroomLamp(delay);
	}

	private void animateOutBedroomFrame(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, bedroomFrame,
							  R.anim.images_animation_out);
	}

	private void animateOutBedroomBed(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, bedroomBed,
							  R.anim.images_animation_out);
	}

	private void animateOutBedroomPlant(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, bedroomPlant,
							  R.anim.images_animation_out);
	}

	private void animateOutBedroomTable(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, bedroomTable,
							  R.anim.images_animation_out);
	}

	private void animateOutBedroomBooks(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, bedroomBooks,
							  R.anim.images_animation_out);
	}

	private void animateOutBedroomLamp(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, bedroomLamp,
							  R.anim.images_animation_out);
	}

	private void animateInBedroomFrame(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, bedroomFrame,
							  R.anim.bedroom_frame_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInBedroomBed(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, bedroomBed,
							  R.anim.bedroom_bed_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInBedroomPlant(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, bedroomPlant,
							  R.anim.bedroom_plant_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInBedroomTable(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, bedroomTable,
							  R.anim.bedroom_table_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInBedroomBooks(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, bedroomBooks,
							  R.anim.bedroom_books_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInBedroomLamp(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, bedroomLamp,
							  R.anim.bedroom_lamp_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateLivingRoomOut(long delay) {
		animateOutFrame(delay);
		animateOutLamp(delay);
		animateOutSofa(delay);
		animateOutPlant(delay);
		animateOutTv(delay);
		animateOutTable(delay);
	}

	private void animateKitchenIn(long delay) {
		animateInKitchenIsland(delay);
		animateInKitchenShelf(delay);
		animateInKitchenFrame(delay);
		animateInKitchenFlower(delay);
		animateInKitchenPan(delay);
		animateInKitchenStool(delay);
	}

	private void animateKitchenOut(long delay) {
		animateOutKitchenIsland(delay);
		animateOutKitchenShelf(delay);
		animateOutKitchenFrame(delay);
		animateOutKitchenFlower(delay);
		animateOutKitchenPan(delay);
		animateOutKitchenStool(delay);
	}

	private void animateOutKitchenIsland(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, kitchenIsland,
							  R.anim.images_animation_out);
	}

	private void animateOutKitchenShelf(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, kitchenShelf,
							  R.anim.images_animation_out);
	}

	private void animateOutKitchenFrame(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, kitchenFrame,
							  R.anim.images_animation_out);
	}

	private void animateOutKitchenFlower(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, kitchenFlower,
							  R.anim.images_animation_out);
	}

	private void animateOutKitchenPan(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, kitchenPan,
							  R.anim.images_animation_out);
	}

	private void animateOutKitchenStool(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, kitchenStool,
							  R.anim.images_animation_out);
	}

	private void animateInKitchenIsland(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, kitchenIsland,
							  R.anim.kitchen_island_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInKitchenShelf(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, kitchenShelf,
							  R.anim.kitchen_shelf_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInKitchenFrame(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, kitchenFrame,
							  R.anim.kitchen_frame_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInKitchenFlower(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, kitchenFlower,
							  R.anim.kitchen_flower_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInKitchenPan(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, kitchenPan,
							  R.anim.kitchen_pan_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInKitchenStool(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, kitchenStool,
							  R.anim.kitchen_stool_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateOutFrame(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, frameImage,
							  R.anim.images_animation_out);
	}

	private void animateOutLamp(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, lampImage,
							  R.anim.images_animation_out);
	}

	private void animateOutSofa(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, sofaImage,
							  R.anim.images_animation_out);
	}

	private void animateOutPlant(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, plantImage,
							  R.anim.images_animation_out);
	}

	private void animateOutTv(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, tvImage,
							  R.anim.images_animation_out);
	}

	private void animateOutTable(long delay) {
		AnimationUtil.animate(delay, View.INVISIBLE, tableImage,
							  R.anim.images_animation_out);
	}

	private void animateLivingRoomIn(long delay) {
		animateInFrame(delay);
		animateInLamp(delay);
		animateInSofa(delay);
		animateInPlant(delay);
		animateInTv(delay);
		animateInTable(delay);
	}

	private void animateInFrame(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, frameImage,
							  R.anim.living_room_frame_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInLamp(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, lampImage,
							  R.anim.living_room_lamp_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInSofa(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, sofaImage,
							  R.anim.living_room_sofa_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInPlant(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, plantImage,
							  R.anim.living_room_plant_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInTv(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, tvImage,
							  R.anim.living_room_tv_image_animation_in, new DecelerateInterpolator(5f));
	}

	private void animateInTable(long delay) {
		AnimationUtil.animate(delay, View.VISIBLE, tableImage,
							  R.anim.living_room_low_table_image_animation_in, new DecelerateInterpolator(5f));
	}
}
