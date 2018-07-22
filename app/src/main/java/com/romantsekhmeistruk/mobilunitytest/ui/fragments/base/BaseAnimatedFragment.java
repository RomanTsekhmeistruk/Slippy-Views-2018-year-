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

import butterknife.BindView;
import butterknife.OnClick;

public class BaseAnimatedFragment extends BaseFragment {

	// Living Room Images
	@BindView(R.id.frame_image)
	ImageView mFrameImage;
	@BindView(R.id.lamp_image)
	ImageView mLampImage;
	@BindView(R.id.sofa_image)
	ImageView mSofaImage;
	@BindView(R.id.plant_image)
	ImageView mPlantImage;
	@BindView(R.id.table_image)
	ImageView mTableImage;
	@BindView(R.id.tv_image)
	ImageView mTvImage;

	// Kitchen Images
	@BindView(R.id.kitchen_island)
	ImageView mKitchenIsland;
	@BindView(R.id.kitchen_shelf)
	ImageView mKitchenShelf;
	@BindView(R.id.kitchen_frame)
	ImageView mKitchenFrame;
	@BindView(R.id.kitchen_flower)
	ImageView mKitchenFlower;
	@BindView(R.id.kitchen_pan)
	ImageView mKitchenPan;
	@BindView(R.id.kitchen_stool)
	ImageView mKitchenStool;

	// Bedroom Images
	@BindView(R.id.bedroom_room_frame)
	ImageView mBedroomFrame;
	@BindView(R.id.bedroom_bed)
	ImageView mBedroomBed;
	@BindView(R.id.bedroom_plant)
	ImageView mBedroomPlant;
	@BindView(R.id.bedroom_table)
	ImageView mBedroomTable;
	@BindView(R.id.bedroom_books)
	ImageView mBedroomBooks;
	@BindView(R.id.bedroom_lamp)
	ImageView mBedroomLamp;

	// Rooms Containers
	@BindView(R.id.living_room)
	RelativeLayout mLivingRoomImages;
	@BindView(R.id.bedroom)
	RelativeLayout mBedroomImages;
	@BindView(R.id.kitchen)
	RelativeLayout mKitchenImages;

	// Animations Helpers
	private Handler mKitchenAnimateOutHandler;
	private Handler mBedroomAnimateOutHandler;
	private Handler mLivingRoomAnimateOutHandler;
	private Handler mKitchenAnimateInHandler;
	private Handler mBedroomAnimateInHandler;
	private Handler mLivingRoomAnimateInHandler;
	private Runnable mAnimateOutRunnable;
	private Runnable mKitchenAnimateInRunnable;
	private Runnable mBedroomAnimateInRunnable;
	private Runnable mLivingRoomAnimateInRunnable;

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initializeAnimationHelpers();
		animateIn();
		startLivingRoom();
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

	private void initializeAnimationHelpers() {
		mKitchenAnimateOutHandler = new Handler();
		mBedroomAnimateOutHandler = new Handler();
		mLivingRoomAnimateOutHandler = new Handler();
		mKitchenAnimateInHandler = new Handler();
		mBedroomAnimateInHandler = new Handler();
		mLivingRoomAnimateInHandler = new Handler();

		mAnimateOutRunnable = this::animateOut;
		mKitchenAnimateInRunnable = this::startKitchen;
		mBedroomAnimateInRunnable = this::startBedroom;
		mLivingRoomAnimateInRunnable = this::startLivingRoom;
	}

	protected void clearAnimations() {
		mKitchenAnimateOutHandler.removeCallbacks(mAnimateOutRunnable);
		mBedroomAnimateOutHandler.removeCallbacks(mAnimateOutRunnable);
		mLivingRoomAnimateOutHandler.removeCallbacks(mAnimateOutRunnable);
		mKitchenAnimateInHandler.removeCallbacks(mKitchenAnimateInRunnable);
		mBedroomAnimateInHandler.removeCallbacks(mBedroomAnimateInRunnable);
		mLivingRoomAnimateInHandler.removeCallbacks(mLivingRoomAnimateInRunnable);
	}

	protected void showRoom() {
		mLivingRoomImages.setVisibility(View.INVISIBLE);
		mBedroomImages.setVisibility(View.INVISIBLE);
		mKitchenImages.setVisibility(View.INVISIBLE);
	}

	protected void animateIn() {
		animateIn(0);
	}

	protected void animateIn(long delay) {
		if (!isViewsAvailable()) {
			return;
		}
		if (mLivingRoomImages.getVisibility() == View.VISIBLE) {
			animateLivingRoomIn(delay);
			return;
		}
		if (mKitchenImages.getVisibility() == View.VISIBLE) {
			animateKitchenIn(delay);
			return;
		}
		if (mBedroomImages.getVisibility() == View.VISIBLE) {
			animateBedroomIn(delay);
			return;
		}
	}

	private boolean isViewsAvailable() {
		if (mLivingRoomImages == null) {
			return false;
		}
		if (mKitchenImages == null) {
			return false;
		}
		if (mBedroomImages == null) {
			return false;
		}
		return true;
	}

	protected void startLivingRoom() {
		if (!isViewsAvailable()) {
			return;
		}
		mKitchenImages.setVisibility(View.INVISIBLE);
		mBedroomImages.setVisibility(View.INVISIBLE);
		mLivingRoomImages.setVisibility(View.VISIBLE);
		animateIn();
//		mLivingRoomAnimateOutHandler.postDelayed(mAnimateOutRunnable, 10000);
//		mKitchenAnimateInHandler.postDelayed(mKitchenAnimateInRunnable, 11000);
	}

	protected void startKitchen() {
		if (!isViewsAvailable()) {
			return;
		}
		mBedroomImages.setVisibility(View.INVISIBLE);
		mLivingRoomImages.setVisibility(View.INVISIBLE);
		mKitchenImages.setVisibility(View.VISIBLE);
		animateIn();
//		mKitchenAnimateOutHandler.postDelayed(mAnimateOutRunnable, 8000);
//		mBedroomAnimateInHandler.postDelayed(mBedroomAnimateInRunnable, 9000);
	}

	protected void startBedroom() {
		if (!isViewsAvailable()) {
			return;
		}
		mKitchenImages.setVisibility(View.INVISIBLE);
		mLivingRoomImages.setVisibility(View.INVISIBLE);
		mBedroomImages.setVisibility(View.VISIBLE);
		animateIn();
//		mBedroomAnimateOutHandler.postDelayed(mAnimateOutRunnable, 8000);
//		mLivingRoomAnimateInHandler.postDelayed(mLivingRoomAnimateInRunnable, 9000);
	}

	protected void animateOut() {
		animateOut(0);
	}

	protected void animateOut(long delay) {
		if (!isViewsAvailable()) {
			return;
		}
		if (mLivingRoomImages.getVisibility() == View.VISIBLE) {
			animateLivingRoomOut(delay);
			return;
		}
		if (mKitchenImages.getVisibility() == View.VISIBLE) {
			animateKitchenOut(delay);
			return;
		}
		if (mBedroomImages.getVisibility() == View.VISIBLE) {
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
		mBedroomFrame.startAnimation(animation);
		mBedroomFrame.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomBed(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mBedroomBed.startAnimation(animation);
		mBedroomBed.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomPlant(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mBedroomPlant.startAnimation(animation);
		mBedroomPlant.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomTable(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mBedroomTable.startAnimation(animation);
		mBedroomTable.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomBooks(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mBedroomBooks.startAnimation(animation);
		mBedroomBooks.setVisibility(View.INVISIBLE);
	}

	private void animateOutBedroomLamp(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mBedroomLamp.startAnimation(animation);
		mBedroomLamp.setVisibility(View.INVISIBLE);
	}

	private void animateInBedroomFrame(long delay) {
		mBedroomFrame.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_frame_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mBedroomFrame.startAnimation(animation);
	}

	private void animateInBedroomBed(long delay) {
		mBedroomBed.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_bed_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mBedroomBed.startAnimation(animation);
	}

	private void animateInBedroomPlant(long delay) {
		mBedroomPlant.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_plant_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mBedroomPlant.startAnimation(animation);
	}

	private void animateInBedroomTable(long delay) {
		mBedroomTable.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_table_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mBedroomTable.startAnimation(animation);
	}

	private void animateInBedroomBooks(long delay) {
		mBedroomBooks.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_books_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mBedroomBooks.startAnimation(animation);
	}

	private void animateInBedroomLamp(long delay) {
		mBedroomLamp.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bedroom_lamp_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mBedroomLamp.startAnimation(animation);
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
		mKitchenIsland.startAnimation(animation);
		mKitchenIsland.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenShelf(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mKitchenShelf.startAnimation(animation);
		mKitchenShelf.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenFrame(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mKitchenFrame.startAnimation(animation);
		mKitchenFrame.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenFlower(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mKitchenFlower.startAnimation(animation);
		mKitchenFlower.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenPan(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mKitchenPan.startAnimation(animation);
		mKitchenPan.setVisibility(View.INVISIBLE);
	}

	private void animateOutKitchenStool(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mKitchenStool.startAnimation(animation);
		mKitchenStool.setVisibility(View.INVISIBLE);
	}

	private void animateInKitchenIsland(long delay) {
		mKitchenIsland.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_island_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mKitchenIsland.startAnimation(animation);
	}

	private void animateInKitchenShelf(long delay) {
		mKitchenShelf.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_shelf_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mKitchenShelf.startAnimation(animation);
	}

	private void animateInKitchenFrame(long delay) {
		mKitchenFrame.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_frame_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mKitchenFrame.startAnimation(animation);
	}

	private void animateInKitchenFlower(long delay) {
		mKitchenFlower.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_flower_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mKitchenFlower.startAnimation(animation);
	}

	private void animateInKitchenPan(long delay) {
		mKitchenPan.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_pan_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mKitchenPan.startAnimation(animation);
	}

	private void animateInKitchenStool(long delay) {
		mKitchenStool.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.kitchen_stool_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mKitchenStool.startAnimation(animation);
	}

	private void animateOutFrame(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mFrameImage.startAnimation(animation);
		mFrameImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutLamp(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mLampImage.startAnimation(animation);
		mLampImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutSofa(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mSofaImage.startAnimation(animation);
		mSofaImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutPlant(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mPlantImage.startAnimation(animation);
		mPlantImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutTv(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mTvImage.startAnimation(animation);
		mTvImage.setVisibility(View.INVISIBLE);
	}

	private void animateOutTable(long delay) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.images_animation_out);
		animation.setStartOffset(delay);
		mTableImage.startAnimation(animation);
		mTableImage.setVisibility(View.INVISIBLE);
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
		mFrameImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_frame_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mFrameImage.startAnimation(animation);
	}

	private void animateInLamp(long delay) {
		mLampImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_lamp_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mLampImage.startAnimation(animation);
	}

	private void animateInSofa(long delay) {
		mSofaImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_sofa_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mSofaImage.startAnimation(animation);
	}

	private void animateInPlant(long delay) {
		mPlantImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_plant_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mPlantImage.startAnimation(animation);
	}

	private void animateInTv(long delay) {
		mTvImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_tv_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mTvImage.startAnimation(animation);
	}

	private void animateInTable(long delay) {
		mTableImage.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.living_room_low_table_image_animation_in);
		animation.setInterpolator(new DecelerateInterpolator(5f));
		animation.setStartOffset(delay);
		mTableImage.startAnimation(animation);
	}
}
