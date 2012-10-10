package com.artwebsandroid.UI;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artwebsandroid.R;
import com.artwebsandroid.object.BinList;
import com.artwebsandroid.object.BinMap;

public class ListAdapter extends BaseAdapter {
	protected HashMap<Integer,View> rowViews=new HashMap<Integer,View>();
	protected BinMap para=new BinMap();
	protected BinList list=new BinList();
	protected Activity activity=null;
	
	public ListAdapter(BinMap para,Activity activity)
	{
		this.para=para;
		if(this.para.containsKey("rows"))this.list=(BinList)this.para.getValue("rows");
		this.activity=activity;
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
		if(rowView==null)
		{
			rowView=(LinearLayout)this.activity.getLayoutInflater().inflate(R.layout.binlistitem, null);
			TextView firstView=(TextView)rowView.findViewById(R.id.first);
			firstView.setMaxLines(2);
			HashMap<Object, Object> row=(HashMap<Object, Object>)this.getItem(position);
			firstView.setText(row.get("text").toString());
			rowViews.put(position, rowView);
		}
		return rowView;
	}
}
