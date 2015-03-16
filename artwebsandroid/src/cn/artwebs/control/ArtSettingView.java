package cn.artwebs.control;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.artwebs.R;
import cn.artwebs.comm.AppApplication;
import cn.artwebs.object.BinList;

/**
 * Created by rsmac on 15/3/13.
 */
public class ArtSettingView extends LinearLayout {
    private BinList list=new BinList();
    private Context context;

    public ArtSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void reloadView(){
        this.removeAllViews();
        for(int i=0;i<list.size();i++){
            LayoutInflater inflater= LayoutInflater.from(AppApplication.getAppContext());
            View view = (View) inflater.inflate(R.layout.artsettingitem,null);
            ImageView lineImageView= (ImageView) view.findViewById(R.id.lineImageView);
            this.addView(view);
            if(i==list.size()-1){
                lineImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void append(Drawable ico,String title,String value,Object obj){
        list.put(false,"ico",ico);
        list.put(true, "title", title);
        list.put(true,"value",value);
        list.put(true,"obj",obj);
    }

}
