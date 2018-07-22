package com.romantsekhmeistruk.mobilunitytest.ui.fragments.base;

import com.romantsekhmeistruk.mobilunitytest.R;

import butterknife.OnClick;

public class BaseAnimatedFragment extends BaseFragment {

	@OnClick(R.id.back)
	public void onBackPressed() {
		getActivity().onBackPressed();
	}
}
