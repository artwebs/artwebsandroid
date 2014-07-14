package cn.artwebs.ListAdapter;

import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.artwebs.object.BinList;
import cn.artwebs.object.BinMap;

import cn.artwebs.R;

public class ListAdapter extends BaseAdapter {
	private final String tag="ListAdapter";
	protected HashMap<Integer,View> rowViews=new HashMap<Integer,View>();
	protected BinMap para=new BinMap();
	protected BinList list=new BinList();
	protected Activity activity=null;
	protected int dataSize=0;
	
	public ListAdapter(Activity activity)
	{
		this.activity=activity;
	}
	
	public void setList(BinList list)
	{
		dataSize=list.size();
		this.list=list;
	}
	public ListAdapter(BinMap para,Activity activity)
	{
		this.para=para;
		if(this.para.containsKey("rows"))this.list=(BinList)this.para.getValue("rows");
		dataSize=Integer.parseInt(this.para.getValue("count").toString());
		this.activity=activity;
	}
	
	public void appendItem(BinList list)
	{
		Log.d(tag,"item"+this.list.getItem().toString());
		Log.d(tag,"adder"+list.getItem().toString());
		this.list.addend(list);
		Log.d(tag,"appendItem"+this.list.getItem().toString());
	}
	
	public void clearItem()
	{
		this.list.clear();
		rowViews.clear();
	}
	
	public void removeItem(int index)
	{
		this.list.remove(index);
		rowViews.remove(index);
	}
	
	public int getDataSize() {
		return dataSize;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return this.list.getItem(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView=rowViews.get(position);
		rowView=(LinearLayout)this.activity.getLayoutInflater().inflate(R.layout.binlistitem, null);
		TextView firstView=(TextView)rowView.findViewById(R.id.first);
		firstView.setMaxLines(2);
		HashMap<Object, Object> row=(HashMap<Object, Object>)this.getItem(position);
		firstView.setText(row.get("text").toString());
		rowViews.put(position, rowView);
		return rowView;
	}
}
