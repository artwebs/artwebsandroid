package cn.artwebs.control;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import cn.artwebs.ListAdapter.ListAdapter;
import cn.artwebs.R;
import cn.artwebs.comm.AppApplication;

public class ArtListControlViewPage extends ArtListControlPage {
	private final String tag="ArtListControlViewPage";
	private final RotateAnimation animation;
	private final ImageView arrowImageView;
	private View headerView;
	private View footerView;
	private LayoutInflater inflater;
	private ListView listView;
	private boolean isLastRow = false;
	private int lastPage =0;

	public ArtListControlViewPage(Activity window,ListAdapter adapter,ListView listView){
		super(window,adapter);
		this.listView=listView;
		inflater=LayoutInflater.from(AppApplication.getAppContext());
		footerView = (View) inflater.inflate(R.layout.artlistview_footer,null);
		headerView = (View) inflater.inflate(R.layout.artlistview_head,null);
//		listView.addHeaderView(headerView);
		listView.addFooterView(footerView);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);

		arrowImageView = (ImageView) headerView
				.findViewById(R.id.head_arrowImageView);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

//		arrowImageView.setAnimation(animation);
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		if (!isLastRow&&firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0 ) {
			isLastRow = true;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.e(tag, "onScrollStateChanged");
		if (lastPage !=page&&isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			//加载元素
			loadData();
			isLastRow = false;
			lastPage =page;
		}
	}


}
