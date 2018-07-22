package com.romantsekhmeistruk.mobilunitytest.ui.fragments.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.romantsekhmeistruk.mobilunitytest.R;
import com.romantsekhmeistruk.mobilunitytest.common.OnCenterItemChangedListener;

import butterknife.BindView;
import butterknife.OnClick;

public class BaseAnimatedFragment extends BaseFragment {

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

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initializeAnimationHelpers();
		animateIn();
		startLivingRoom();
		initOnCenterItemChangedListener();
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
			animateOut();
			clearAnimations();
			switch (centerPosition) {
				case 0:
					lastShowedRoom = 0;
					livingRoomAnimateInHandler.postDelayed(livingRoomAnimateInRunnable, 1000);
					break;
				case 1:
					lastShowedRoom = 1;
					bedroomAnimateInHandler.postDelayed(bedroomAnimateInRunnable, 1000);
					break;
				case 2:
					lastShowedRoom = 2;
					kitchenAnimateInHandler.postDelayed(kitchenAnimateInRunnable, 1000);
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

	protected void animateIn() {
		animateIn(0);
	}

	protected void animateIn(long delay) {
		if (!isViewsAvailable()) {
			return;
		}
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
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		bedroomFrame.startAnimation(animation);
		bedroomFrame.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomBed(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		bedroomBed.startAnimation(animation);
		bedroomBed.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomPlant(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		bedroomPlant.startAnimation(animation);
		bedroomPlant.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomTable(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		bedroomTable.startAnimation(animation);
		bedroomTable.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomBooks(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		bedroomBooks.startAnimation(animation);
		bedroomBooks.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomLamp(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		bedroomLamp.startAnimation(animation);
		bedroomLamp.setVisibility(View.INVISIBLE);
	}

	private void animateInBedroomFrame(long delay) {
		bedroomFrame.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_frame_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		bedroomFrame.startAnimation(animation);
	}

	private void animateInBedroomBed(long delay) {
		bedroomBed.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_bed_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		bedroomBed.startAnimation(animation);
	}

	private void animateInBedroomPlant(long delay) {
		bedroomPlant.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_plant_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		bedroomPlant.startAnimation(animation);
	}

	private void animateInBedroomTable(long delay) {
		bedroomTable.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_table_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		bedroomTable.startAnimation(animation);
	}

	private void animateInBedroomBooks(long delay) {
		bedroomBooks.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_books_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		bedroomBooks.startAnimation(animation);
	}

	private void animateInBedroomLamp(long delay) {
		bedroomLamp.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_lamp_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		bedroomLamp.startAnimation(animation);
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
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		kitchenIsland.startAnimation(animation);
		kitchenIsland.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenShelf(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		kitchenShelf.startAnimation(animation);
		kitchenShelf.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenFrame(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		kitchenFrame.startAnimation(animation);
		kitchenFrame.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenFlower(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		kitchenFlower.startAnimation(animation);
		kitchenFlower.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenPan(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		kitchenPan.startAnimation(animation);
		kitchenPan.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenStool(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		kitchenStool.startAnimation(animation);
		kitchenStool.setVisibility(View.INVISIBLE);
	}

	private void animateInKitchenIsland(long delay) {
		kitchenIsland.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_island_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		kitchenIsland.startAnimation(animation);
	}

	private void animateInKitchenShelf(long delay) {
		kitchenShelf.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_shelf_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		kitchenShelf.startAnimation(animation);
	}

	private void animateInKitchenFrame(long delay) {
		kitchenFrame.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_frame_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		kitchenFrame.startAnimation(animation);
	}

	private void animateInKitchenFlower(long delay) {
		kitchenFlower.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_flower_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		kitchenFlower.startAnimation(animation);
	}

	private void animateInKitchenPan(long delay) {
		kitchenPan.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_pan_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		kitchenPan.startAnimation(animation);
	}

	private void animateInKitchenStool(long delay) {
		kitchenStool.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_stool_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		kitchenStool.startAnimation(animation);
	}

	private void animateOutFrame(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		frameImage.startAnimation(animation);
		frameImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutLamp(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		lampImage.startAnimation(animation);
		lampImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutSofa(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		sofaImage.startAnimation(animation);
		sofaImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutPlant(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		plantImage.startAnimation(animation);
		plantImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutTv(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		tvImage.startAnimation(animation);
		tvImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutTable(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		tableImage.startAnimation(animation);
		tableImage.setVisibility(View.INVISIBLE);
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
		frameImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_frame_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		frameImage.startAnimation(animation);
	}

	private void animateInLamp(long delay) {
		lampImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_lamp_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		lampImage.startAnimation(animation);
	}

	private void animateInSofa(long delay) {
		sofaImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_sofa_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		sofaImage.startAnimation(animation);
	}

	private void animateInPlant(long delay) {
		plantImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_plant_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		plantImage.startAnimation(animation);
	}

	private void animateInTv(long delay) {
		tvImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_tv_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		tvImage.startAnimation(animation);
	}

	private void animateInTable(long delay) {
		tableImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_low_table_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		tableImage.startAnimation(animation);
	}
}
