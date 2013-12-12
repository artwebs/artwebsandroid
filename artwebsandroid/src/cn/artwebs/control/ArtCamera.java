package cn.artwebs.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.artwebs.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArtCamera extends RelativeLayout {
	private final static String tag="ArtCamera";
	private int cammeraIndex;
	private Button mVideoStartBtn;
	private SurfaceView mSurfaceview;
	private MediaRecorder mMediaRecorder;
	private SurfaceHolder mSurfaceHolder;
	private File mRecVedioPath;
	private File mRecAudioFile;
	private TextView timer;
	private int hour = 0;
	private int minute = 0;
	private int second = 0;
	private boolean bool;
	
	private Camera camera;
	private boolean isPreview;
	private SurfaceHolder holder;
	private Context context;
	private String savePath;
	private SaveCallBack callBackObj;
	private boolean isRecording = true;
	private int displayRotate=0;
	
	public enum CAMERATYPE{FRONT,BACK}
	public enum OPERATE{IMAGE,VIDEO,AUDIO}
	public ArtCamera(Context context) {
		super(context);
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	
	public ArtCamera(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.artcamera, this);
		mSurfaceview=(SurfaceView)findViewById(R.id.video_view);
		timer=(TextView)findViewById(R.id.video_timer);
		timer.setText("30:00");
		// 绑定预览视图
		holder = mSurfaceview.getHolder();
		
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	
	
	public String getSavePath() {
		if(savePath==null)savePath="/";
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
		// 设置缓存路径
		mRecVedioPath = new File(savePath+ "/temp/");
		if (!mRecVedioPath.exists()) {
			mRecVedioPath.mkdirs();
		}
	}
	
	public void setSaveCallBack(SaveCallBack obj)
	{
		this.callBackObj=obj;
	}

	public void show(CAMERATYPE type)
	{
		if(type==CAMERATYPE.FRONT)
		{
			cammeraIndex=FindFrontCamera();
		}
		if(cammeraIndex==-1)cammeraIndex=FindBackCamera();
		
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
					camera = Camera.open(cammeraIndex);
					setCameraDisplayOrientation ();
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
	}
	
	
	public void saveImage()
	{
		
		if (camera != null) {
			camera.autoFocus(null);
			camera.takePicture(null, null, new PictureCallback() {
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(data,
							0, data.length);
					Matrix matrix = new Matrix();
					// 设置缩放
					matrix.postRotate(displayRotate);
					matrix.postScale(1f, 1f);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(),
							matrix, true);

					final String fileName = new SimpleDateFormat(
							"yyyyMMddHHmmss").format(new Date())
							+ ".jpg";
					File out = new File(savePath);
					if (!out.exists()) {
						out.mkdirs();
					}
					out = new File(savePath, fileName);
					try {
						FileOutputStream outStream = new FileOutputStream(
								out);
						bitmap.compress(CompressFormat.JPEG, 100,
								outStream);
						outStream.close();
						camera.startPreview();
						if(callBackObj!=null)callBackObj.onGetFileName(savePath+ fileName,OPERATE.IMAGE);
					} catch (Exception e) {
						e.printStackTrace();
						if(callBackObj!=null)callBackObj.onGetError(e);
					}finally{}
				}
			}); // 拍照
		}
	}
	
	public void saveVideo()
	{
		if (isRecording) {
			/*
			 * 点击开始录像
			 */
			if (isPreview) {
				camera.stopPreview();
				camera.release();
				camera = null;
			}
			second = 0;
			minute = 0;
			hour = 0;
			bool = true;
			if (mMediaRecorder == null)
				mMediaRecorder = new MediaRecorder();
			else
				mMediaRecorder.reset();
			mMediaRecorder.setPreviewDisplay(mSurfaceHolder
					.getSurface());
			mMediaRecorder
					.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			mMediaRecorder
					.setAudioSource(MediaRecorder.AudioSource.MIC);
			mMediaRecorder
					.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mMediaRecorder
					.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			mMediaRecorder
					.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mMediaRecorder.setVideoSize(320, 240);
			mMediaRecorder.setVideoFrameRate(15);
			mMediaRecorder.setOrientationHint(90);
			try {
				mRecAudioFile = File.createTempFile("Vedio", ".3gp",
						mRecVedioPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mMediaRecorder.setOutputFile(mRecAudioFile
					.getAbsolutePath());
			try {
				mMediaRecorder.prepare();
				timer.setVisibility(View.VISIBLE);
				handler.postDelayed(task, 1000);
				mMediaRecorder.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			showMsg("开始录制");
//			mVideoStartBtn.setBackgroundDrawable(iconStop);
			isRecording = !isRecording;
		} else {
			/*
			 * 点击停止
			 */
			try {
				bool = false;
				mMediaRecorder.stop();
				timer.setText(format(hour) + ":" + format(minute) + ":"
						+ format(second));
				mMediaRecorder.release();
				mMediaRecorder = null;
				videoRename(OPERATE.VIDEO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			isRecording = !isRecording;
//			mVideoStartBtn.setBackgroundDrawable(iconStart);
			
//			showMsg("录制完成，已保存");

		}
	}
	
	
	public void saveAudio()
	{
		if (isRecording) {
			/*
			 * 点击开始录像
			 */
			
			second = 0;
			minute = 0;
			hour = 0;
			bool = true;
			if (mMediaRecorder == null)
				mMediaRecorder = new MediaRecorder();
			else
				mMediaRecorder.reset();
			 mMediaRecorder = new MediaRecorder();
	         
		    /* setAudioSource/setVedioSource*/
	        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
	         
	        /* 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
	         * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
	         */
	        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
	          
	         /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
	         mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			try {
				mRecAudioFile = File.createTempFile("Vedio", ".amrnb",
						mRecVedioPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mMediaRecorder.setOutputFile(mRecAudioFile
					.getAbsolutePath());
			try {
				mMediaRecorder.prepare();
				timer.setVisibility(View.VISIBLE);
				handler.postDelayed(task, 1000);
				mMediaRecorder.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			showMsg("开始录制");
//			mVideoStartBtn.setBackgroundDrawable(iconStop);
			isRecording = !isRecording;
		} else {
			/*
			 * 点击停止
			 */
			try {
				bool = false;
				mMediaRecorder.stop();
				timer.setText(format(hour) + ":" + format(minute) + ":"
						+ format(second));
				mMediaRecorder.release();
				mMediaRecorder = null;
				videoRename(OPERATE.AUDIO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			isRecording = !isRecording;
//			mVideoStartBtn.setBackgroundDrawable(iconStart);
			
//			showMsg("录制完成，已保存");

		}
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
	@TargetApi(10)  
	private void setCameraDisplayOrientation () {
	     android.hardware.Camera.CameraInfo info =
	              new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo ( cammeraIndex , info );
	      int rotation = ((Activity)context).getWindowManager ().getDefaultDisplay ().getRotation ();
	      int degrees = 0 ;
	      switch ( rotation ) {
	          case Surface.ROTATION_0 : degrees = 0 ; break ;
	          case Surface.ROTATION_90 : degrees = 90 ; break ;
	          case Surface.ROTATION_180 : degrees = 180 ; break ;
	          case Surface.ROTATION_270 : degrees = 270 ; break ;
	      }
	 
	      int result ;
	      if ( info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT ) {
	         result = ( info.orientation + degrees ) % 360 ;
	         result = ( 360 - result ) % 360 ;   // compensate the mirror
	      } else {   // back-facing
	         result = ( info.orientation - degrees + 360 ) % 360 ;
	      }
	     displayRotate=result;
	     camera.setDisplayOrientation ( result );
	     Log.d(tag, "setCameraDisplayOrientation="+result);
	  }

	
	public interface SaveCallBack
	{
		public void onGetFileName(String fileName,OPERATE opt);
		public void onGetError(Exception e);
	}
	
	/*
	 * 生成video文件名字
	 */
	protected void videoRename(OPERATE opt) {
		String ex="";
		switch(opt)
		{
		case VIDEO:
			ex=".3gp";
			break;
		case AUDIO:
			ex=".amrnb";
			break;
		default:
			ex=".3gp";
			break;
		}
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
		.format(new Date()) + ex;
		File out = new File(savePath);
		if (!out.exists()) {
			out.mkdirs();
		}
		out = new File(savePath, fileName);
		if (mRecAudioFile.exists())
		{
			mRecAudioFile.renameTo(out);
			if(callBackObj!=null)callBackObj.onGetFileName(savePath+ fileName,OPERATE.VIDEO);
		}
			
	}

	
	/*
	 * 定时器设置，实现计时
	 */
	private Handler handler = new Handler();
	private Runnable task = new Runnable() {
		public void run() {
			if (bool) {
				handler.postDelayed(this, 1000);
				second++;
				if (second >= 60) {
					minute++;
					second = second % 60;
				}
				if (minute >= 60) {
					hour++;
					minute = minute % 60;
				}
				timer.setText(format(hour) + ":" + format(minute) + ":"
						+ format(second));
			}
		}
	};
	
	/*
	 * 格式化时间
	 */
	private String format(int i) {
		String s = i + "";
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}
	
}
