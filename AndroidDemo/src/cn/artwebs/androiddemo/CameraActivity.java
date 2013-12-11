package cn.artwebs.androiddemo;

import cn.artwebs.control.ArtCamera;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class CameraActivity extends Activity {
	private LinearLayout mainLayout;
	private ArtCamera artCamera;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mainLayout=(LinearLayout)getLayoutInflater().inflate(R.layout.camera, null);
		this.setContentView(mainLayout);
		artCamera=(ArtCamera)findViewById(R.id.artcamera);
	}
	
}
