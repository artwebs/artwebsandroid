package cn.artwebs.control;

import android.app.Activity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import cn.artwebs.ListAdapter.ListAdapter;
import android.widget.Toast;
import cn.artwebs.object.BinList;

public class ArtListControlPage implements OnScrollListener {
	private final static String tag="ArtListViewPage";
	protected BinList list=new BinList();
	protected  Activity window;
	protected ListAdapter adapter;
	protected int page=1;
	protected int pageSize=10;
	protected int visibleLastIndex = 0;
	protected int visibleItemCount=0;
	protected int dataSize=0;
	protected OnControlPageListener listener;

	public BinList getList() {
		return list;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public ArtListControlPage(Activity window,ListAdapter adapter)
	{
		this.window=window;
		this.adapter=adapter;
		this.adapter.setList(list);
	}
	
	public void load()
	{
		this.adapter.clearItem();
		page=1;
		setDataSize(pageSize);
		visibleLastIndex = 0;
		visibleItemCount=0;
		list.clear();
		loadData();
	}
	
	public void setListener(OnControlPageListener listener)
	{
		this.listener=listener;
	}
	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;  
        visibleLastIndex = firstVisibleItem + visibleItemCount;    
        if(totalItemCount == list.size()+1){
//	            listView.removeFooterView(loadMoreLayout);
            Toast.makeText(window, "加载完成", Toast.LENGTH_LONG).show();  
        }  
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.e(tag, "onScrollStateChanged");
		int itemsLastIndex = adapter.getCount()-1;    
        int lastIndex = itemsLastIndex + 1;  
        Log.e(tag, (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) + "");
        Log.e(tag, (visibleLastIndex == lastIndex) + "");
        Log.e(tag, (adapter.getCount() < dataSize - 1) + "");
        Log.e(tag, "------------------------");
        if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL  
                && visibleLastIndex == lastIndex &&adapter.getCount()<dataSize-1) { 
        	Log.e(tag, "loadMoreData");
        	loadData();
        }  
	}
	
	protected void loadData()
	{
		if(listener==null)
        {
			setDataSize(0);
        	Log.d(tag, "listener 没有设置");
        	return;
        }
		listener.loadMoreData(page,pageSize);
	}
	
	public void notifyDataChanged(BinList tmpList)
	{
		if(tmpList.size()>0)
    	{
    		list.addend(tmpList);
    		window.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
				}
			});
    		dataSize+=tmpList.size();
    		page++;
    	}
	}

	public interface OnControlPageListener
	{
		public void loadMoreData(int page,int pageSize);
	}
	
	
}
