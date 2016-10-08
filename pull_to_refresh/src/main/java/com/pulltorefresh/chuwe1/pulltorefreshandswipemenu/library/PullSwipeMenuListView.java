package com.pulltorefresh.chuwe1.pulltorefreshandswipemenu.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import com.pulltorefresh.chuwe1.swipemenu.library.swipemenuview.SwipeMenuListView;
import com.pulltorefresh.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.pulltorefresh.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

/**
 * 下拉刷新、上拉加载、侧滑的ListView
 * 
 * @author chuwe1
 *
 */
public class PullSwipeMenuListView extends
		PullToRefreshAdapterViewBase<SwipeMenuListView> {

	public PullSwipeMenuListView(Context context) {
		super(context);
	}

	public PullSwipeMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullSwipeMenuListView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullSwipeMenuListView(Context context, Mode mode,
								 AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected SwipeMenuListView createRefreshableView(Context context,
			AttributeSet attrs) {
		final SwipeMenuListView lv;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			lv = new InternalListViewSDK9(context, attrs);
		} else {
			lv = new InternalListView(context, attrs);
		}

		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		return lv;
	}

	class InternalListView extends SwipeMenuListView implements
			EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			PullSwipeMenuListView.this.setEmptyView(emptyView);
		}

	}

	@TargetApi(9)
	final class InternalListViewSDK9 extends InternalListView {

		public InternalListViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
				int scrollY, int scrollRangeX, int scrollRangeY,
				int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY,
					scrollX, scrollY, scrollRangeX, scrollRangeY,
					maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			com.pulltorefresh.handmark.pulltorefresh.library.OverscrollHelper.overScrollBy(
					PullSwipeMenuListView.this, deltaX, scrollX,
					deltaY, scrollY, isTouchEvent);

			return returnValue;
		}
	}
}