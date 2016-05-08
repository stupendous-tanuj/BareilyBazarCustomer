package com.app.bareillybazarcustomer.fragment;

import com.app.bareillybazarcustomer.listner.ScrollTabHolder;

public abstract class SubHolderFragment extends BaseFragment implements ScrollTabHolder {

	protected ScrollTabHolder mScrollTabHolder;

	public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}
}