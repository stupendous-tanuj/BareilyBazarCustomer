package com.app.customerapp.fragment;

import com.app.customerapp.listner.ScrollTabHolder;

public abstract class SubHolderFragment extends BaseFragment implements ScrollTabHolder {

	protected ScrollTabHolder mScrollTabHolder;

	public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}
}