package cn.artwebs.control;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import cn.artwebs.ListAdapter.ListAdapter;
import cn.artwebs.R;
import cn.artwebs.comm.AppApplication;

public class ArtListControlViewPage extends ArtListControlPage {
	private View headerLayout;
	private View footerLayout;
	private LayoutInflater inflater;

	public ArtListControlViewPage(Activity window,ListAdapter adapter,ListView listView){
		super(window,adapter);
		inflater=LayoutInflater.from(AppApplication.getAppContext());
		footerLayout = (View) inflater.inflate(R.layout.artlistview_footer,null);
		headerLayout= (View) inflater.inflate(R.layout.artlistview_head,null);
		listView.addHeaderView(headerLayout);
		listView.addFooterView(footerLayout);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
	}


}
