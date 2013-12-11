package cn.artwebs.control;

import java.io.File;

import cn.artwebs.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArtCamera extends RelativeLayout {
	private int cammeraIndex;
	private Button mVideoStartBtn;
	private SurfaceView mSurfaceview;
	private MediaRecorder mMediaRecorder;
	private SurfaceHolder mSurfaceHolder;
	private File mRecVedioPath;
	private File mRecAudioFile;
	private TextView timer;
	
	private Camera camera;
	private boolean isPreview;
	
	public enum CAMERATYPE{FRONT,BACK}
	public ArtCamera(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ArtCamera(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.artcamera, this);
		mSurfaceview=(SurfaceView)findViewById(R.id.video_view);
		timer=(TextView)findViewById(R.id.video_timer);
		timer.setText("30:00");
		// 绑定预览视图
		SurfaceHolder holder = mSurfaceview.getHolder();
		holder.addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (camera != null) {
					if (isPreview) {
						camera.stopPreview();
						isPreview = false;
					}
					camera.release();
					camera = null; // 记得释放
				}
				mSurfaceview = null;
				mSurfaceHolder = null;
				mMediaRecorder = null;
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					camera = Camera.open();
					Camera.Parameters parameters = camera.getParameters();
					parameters.setPreviewFrameRate(5); // 每秒5帧
					parameters.setPictureFormat(PixelFormat.JPEG);// 设置照片的输出格式
					parameters.set("jpeg-quality", 85);// 照片质量
					camera.setParameters(parameters);
					camera.setPreviewDisplay(holder);
					camera.startPreview();
					isPreview = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				mSurfaceHolder = holder;
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				mSurfaceHolder = holder;
			}
		});
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void show(CAMERATYPE type)
	{
		if(type==CAMERATYPE.FRONT)
		{
			cammeraIndex=FindFrontCamera();
		}
		if(cammeraIndex!=-1)return;
		cammeraIndex=FindBackCamera();
	}
	
	@TargetApi(10)  
    private int FindFrontCamera(){  
        int cameraCount = 0;  
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
        cameraCount = Camera.getNumberOfCameras(); // get cameras number  
                
        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo  
            if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_FRONT ) {   
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
               return camIdx;  
            }  
        }  
        return -1;  
    } 
	
    @TargetApi(10)  
    private int FindBackCamera(){  
        int cameraCount = 0;  
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
        cameraCount = Camera.getNumberOfCameras(); // get cameras number  
                
        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo  
            if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_BACK ) {   
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
               return camIdx;  
            }  
        }  
        return -1;  
    }  

	public void onBackPressed() {
		if (mMediaRecorder != null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

	
	
}
