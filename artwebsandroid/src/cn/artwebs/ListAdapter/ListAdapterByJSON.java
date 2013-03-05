package cn.artwebs.ListAdapter;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class ListAdapterByJSON extends BaseAdapter {
	private final String tag="ListAdapterByJSON";
	protected Activity activity=null;
	protected HashMap<Integer,View> rowViews=new HashMap<Integer,View>();
	private JSONArray list;
	protected int dataSize=0;
	
	public ListAdapterByJSON(Activity activity)
	{
		this.activity=activity;
	}
	
	public void setList(JSONArray list)
	{
		dataSize=list.length();
		this.list=list;
	}
	public ListAdapterByJSON(JSONArray list,Activity activity)
	{
		dataSize=list.length();
		this.list=list;
		this.activity=activity;
	}
	
	public void appendItem(JSONObject obj)
	{
		this.list.put(obj);
	}
	
	
	
	public int getDataSize() {
		return dataSize;
	}

	@Override
	public int getCount() {
		return this.list.length();
	}

	@Override
	public Object getItem(int arg0) {
		try {
			return this.list.get(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
			JSONObject row=(JSONObject)this.getItem(position);
			try {
				firstView.setText(row.getString("fist"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rowViews.put(position, rowView);
		}
		return rowView;
	}
}
