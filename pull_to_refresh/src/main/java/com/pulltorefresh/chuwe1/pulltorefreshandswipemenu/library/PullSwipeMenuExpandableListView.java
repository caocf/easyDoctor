package com.pulltorefresh.chuwe1.pulltorefreshandswipemenu.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import com.pulltorefresh.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * 下拉刷新、上拉加载、侧滑的ExpandableListView
 * 
 * @author chuwe1
 *
 */
public class PullSwipeMenuExpandableListView extends
		com.pulltorefresh.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase<com.pulltorefresh.chuwe1.swipemenu.library.swipemenuview.SwipeMenuExpandableListView> {

	public PullSwipeMenuExpandableListView(Context context) {
		super(context);
	}

	public PullSwipeMenuExpandableListView(Context context,
										   AttributeSet attrs) {
		super(context, attrs);
	}

	public PullSwipeMenuExpandableListView(Context context,
										   PullToRefreshBase.Mode mode) {
		super(context, mode);
	}

	public PullSwipeMenuExpandableListView(Context context,
										   PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final PullToRefreshBase.Orientation getPullToRefreshScrollDirection() {
		return PullToRefreshBase.Orientation.VERTICAL;
	}

	@Override
	protected com.pulltorefresh.chuwe1.swipemenu.library.swipemenuview.SwipeMenuExpandableListView createRefreshableView(
			Context context, AttributeSet attrs) {
		final com.pulltorefresh.chuwe1.swipemenu.library.swipemenuview.SwipeMenuExpandableListView lv;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			lv = new InternalExpandableListViewSDK9(context, attrs);
		} else {
			lv = new InternalExpandableListView(context, attrs);
		}

		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		return lv;
	}

	class InternalExpandableListView extends com.pulltorefresh.chuwe1.swipemenu.library.swipemenuview.SwipeMenuExpandableListView
			implements com.pulltorefresh.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor {

		public InternalExpandableListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			PullSwipeMenuExpandableListView.this
					.setEmptyView(emptyView);
		}

	}

	@TargetApi(9)
	final class InternalExpandableListViewSDK9 extends
			InternalExpandableListView {

		public InternalExpandableListViewSDK9(Context context,
				AttributeSet attrs) {
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
					PullSwipeMenuExpandableListView.this, deltaX,
					scrollX, deltaY, scrollY, isTouchEvent);

			return returnValue;
		}
	}
}