package cn.artwebs.control.submititem;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import cn.artwebs.R;
import cn.artwebs.object.BinMap;
import cn.artwebs.utils.AndroidUtils;

public class SubmitItem2TextBox extends SubmitItem {
	private EditText nameETxt;
	public TableRow buildItem(Context context,BinMap paraRow)
	{
		TableRow rowLayout=super.buildItem(context, paraRow);
		nameETxt=(EditText) rowLayout.findViewById(R.id.nameETxt);
		if(paraRow.containsKey("value"))
		{
			nameETxt.setHint(paraRow.getValue("value").toString());
		}
		if(paraRow.containsKey("readonly"))
		{
			if("true".equals(paraRow.getValue("readonly").toString()))AndroidUtils.setEditTextReadOnly(nameETxt);
		}
		if(paraRow.containsKey("unit"))
		{
			TextView unitTView=(TextView) rowLayout.findViewById(R.id.unitTView);
			unitTView.setVisibility(View.VISIBLE);
			unitTView.setText(paraRow.getValue("unit").toString());
		}
		return rowLayout;
	}

	@Override
	public boolean isChanged() {
		// TODO Auto-generated method stub
		return !"".equals(nameETxt.getText().toString());
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return this.isChanged()?nameETxt.getText().toString():nameETxt.getHint().toString();
	}

}
