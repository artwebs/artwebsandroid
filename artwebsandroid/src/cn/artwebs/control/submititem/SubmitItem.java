package cn.artwebs.control.submititem;

import java.util.HashMap;

import cn.artwebs.R;
import cn.artwebs.object.BinMap;
import android.app.Activity;
import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

public abstract class SubmitItem {
	private String name;
	private String originValue="";
	public static enum ItemKey{
		name,cname,value,dicvalue,type,display,readonly,unit,mache,maxValue,minValue
	};
	public static enum ItemValueType{
		textBox,singleChoice,multipleChoice,dropdownList
	}
	
	public String getName() {
		return name;
	}
	
	
	
	public String getOriginValue() {
		return originValue;
	}

	public TableRow buildItem(Context context,BinMap paraRow)
	{
		TableRow rowLayout=(TableRow) ((Activity) context).getLayoutInflater().inflate(R.layout.submititem, null);
		TextView cnameTView=(TextView) rowLayout.findViewById(R.id.cnameTView);
		cnameTView.setText(paraRow.getValue(ItemKey.cname.toString()).toString());
		name=paraRow.getValue(ItemKey.name.toString()).toString();
		if(paraRow.containsKey(ItemKey.value.toString()))
			originValue=paraRow.getValueString(ItemKey.value.toString());
		return rowLayout;
	}
	public abstract boolean isChanged();
	public abstract String getValue(); 
}
