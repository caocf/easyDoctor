package com.pulltorefresh.handmark.pulltorefresh.library;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import java.util.HashSet;

public class LoadingLayoutProxy implements ILoadingLayout {

	private final HashSet<com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout> mLoadingLayouts;

	LoadingLayoutProxy() {
		mLoadingLayouts = new HashSet<com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout>();
	}

	/**
	 * This allows you to add extra LoadingLayout instances to this proxy. This
	 * is only necessary if you keep your own instances, and want to have them
	 * included in any
	 * {@link PullToRefreshBase#createLoadingLayoutProxy(boolean, boolean)
	 * createLoadingLayoutProxy(...)} calls.
	 * 
	 * @param layout - LoadingLayout to have included.
	 */
	public void addLayout(com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout) {
		if (null != layout) {
			mLoadingLayouts.add(layout);
		}
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		for (com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout : mLoadingLayouts) {
			layout.setLastUpdatedLabel(label);
		}
	}

	@Override
	public void setLoadingDrawable(Drawable drawable) {
		for (com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout : mLoadingLayouts) {
			layout.setLoadingDrawable(drawable);
		}
	}

	@Override
	public void setRefreshingLabel(CharSequence refreshingLabel) {
		for (com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout : mLoadingLayouts) {
			layout.setRefreshingLabel(refreshingLabel);
		}
	}

	@Override
	public void setPullLabel(CharSequence label) {
		for (com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout : mLoadingLayouts) {
			layout.setPullLabel(label);
		}
	}

	@Override
	public void setReleaseLabel(CharSequence label) {
		for (com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout : mLoadingLayouts) {
			layout.setReleaseLabel(label);
		}
	}

	public void setTextTypeface(Typeface tf) {
		for (com.pulltorefresh.handmark.pulltorefresh.library.internal.LoadingLayout layout : mLoadingLayouts) {
			layout.setTextTypeface(tf);
		}
	}
}
