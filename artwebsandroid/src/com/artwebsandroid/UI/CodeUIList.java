package com.artwebsandroid.UI;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.artwebs.R;
import com.artwebsandroid.object.BinList;
import com.artwebsandroid.object.BinMap;

public class CodeUIList extends CodeUI {
	private BinList rows=new BinList();
	private BaseAdapter adapter;
	
	public void setAdapter(BaseAdapter adapter)
	{
		this.adapter=adapter;
	}
	
	@Override
	public View drawnView(final Activity activity,Integer parentid,Integer id) {
//		adapter=new ListAdapter(this.para,activity);	
//		ListView listView=(ListView)activity.findViewById(id);
		ListView listView=new ListView(activity);
		listView.setAdapter(adapter);		
		listView.setOnItemClickListener(this.onItemClickListener);
		return listView;
	}
	
	
	
	class ListAdapter extends BaseAdapter
	{	
		private HashMap<Integer,View> rowViews=new HashMap<Integer,View>();
		private BinMap para=new BinMap();
		private BinList list=new BinList();
		private Activity activity=null;
		
		public ListAdapter(BinMap para,Activity activity)
		{
			this.para=para;
			this.list=(BinList)this.para.getValue("rows");
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
				firstView.setText(row.get("first").toString());
				rowViews.put(position, rowView);
			}
			return rowView;
		}
		
	}
	
	

}
