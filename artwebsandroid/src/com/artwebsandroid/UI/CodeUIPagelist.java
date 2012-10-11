package com.artwebsandroid.UI;

import com.artwebsandroid.object.BinList;
import com.artwebsandroid.object.BinMap;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class CodeUIPagelist extends CodeUIList implements OnScrollListener {
	private final static String tag="CodeUIPagelist";
	private String next=null;
	private int visibleLastIndex = 0;   //最后的可视项索引    
    private int visibleItemCount;       // 当前窗口可见项总数
    private ListView listView;
    private LinearLayout loadMoreLayout ;
    private int datasize=0;
	@Override
	public View drawnView(final Activity activity,Integer parentid,Integer id) {
//		adapter=new ListAdapter(this.para,activity);	
//		ListView listView=(ListView)activity.findViewById(id);
		this.setMainActity(activity);
		loadMoreLayout = new LinearLayout(activity);
		loadMoreLayout.setOrientation(LinearLayout.HORIZONTAL);
		loadMoreLayout.setGravity(Gravity.CENTER);
					
		listView=new ListView(activity);
		listView.addFooterView(loadMoreLayout);
		if(adapter==null)this.setAdapter(new ListAdapter(this.para,activity));
		listView.setAdapter(adapter);		
		datasize=adapter.getDataSize();
		listView.setOnItemClickListener(this.onItemClickListener);
		listView.setOnScrollListener(this);
		return listView;
	}
	
	private void loadMoreData()
	{
		if(next==null)next=this.para.getValue("next").toString();
		this.uiFactory.setTransmit(transmit);
        this.uiFactory.parseData(next);        
        BinMap para=this.uiFactory.getMap();
        
        if(!para.getValue("next").equals(next))
        {
        	adapter.appendItem((BinList)para.getValue("rows"));
        	next=para.getValue("next").toString();
        }
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;  
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;            
        //如果所有的记录选项等于数据集的条数，则移除列表底部视图  
        if(totalItemCount == datasize+1){  
            listView.removeFooterView(loadMoreLayout);  
            Toast.makeText(this.getMainActity(), "数据全部加载完!", Toast.LENGTH_LONG).show();  
        }  
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount()-1;  //数据集最后一项的索引    
        int lastIndex = itemsLastIndex + 1;  
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  
                && visibleLastIndex == lastIndex &&adapter.getCount()<datasize) {  
        	loadMoreData();
			adapter.notifyDataSetChanged();
        }  
	}
}
