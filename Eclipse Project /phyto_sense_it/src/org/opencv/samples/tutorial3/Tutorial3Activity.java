package org.opencv.samples.tutorial3;

// Template Matching 
// *****************************************
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.*;
import org.opencv.core.Rect;


// *****************************************
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;







import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.imgcodecs.*;

public class Tutorial3Activity extends Activity implements CvCameraViewListener2, OnTouchListener {

	
	private static final String TAG = "OpenCV::Test";
	private Tutorial3View mOpenCvCameraView;
	private List<Size> mResolutionList;
	private MenuItem[] mEffectMenuItems;
	private SubMenu mColorEffectsMenu;
	private MenuItem[] mResolutionMenuItems;
	private SubMenu mResolutionMenu;

	public String fileName;
	// private Mat img;// matrix that will hold image

	private Button mTakePhotoButton;


	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS:
			{
				Log.i(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();

				// mOpenCvCameraView.setOnTouchListener(Tutorial3Activity.this);
			} break;
			default:
			{
				super.onManagerConnected(status);
			} break;
			}
		}
	};

	public Tutorial3Activity() {
		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.tutorial3_surface_view);

		mOpenCvCameraView = (Tutorial3View) findViewById(R.id.tutorial3_activity_java_surface_view);

		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);


		//  mOpenCvCameraView.setCvCameraViewListener(this);

		mTakePhotoButton = (Button) findViewById(R.id.button1);
		mTakePhotoButton.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {

				//Takes and saves the photo
				takePicture();

				// Sets up a delay  (5s = 5000ms)
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// Calls your algorithm after
						// yourAlgorithmCall(); 
						//matchesTemplatesToImg(); 
						// Sets 'Take Photo' Button Invisibile 
						mTakePhotoButton.setVisibility(Button.GONE);
					}
				}, 4000);
			}
		});
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (!OpenCVLoader.initDebug()) {
			Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
		} else {
			Log.d(TAG, "OpenCV library found inside package. Using it!");
			mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	public void onCameraViewStarted(int width, int height) {


	}

	public void onCameraViewStopped() {
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		Mat mRgba = inputFrame.rgba();
		return mRgba;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		List<String> effects = mOpenCvCameraView.getEffectList();

		if (effects == null) {
			Log.e(TAG, "Color effects are not supported by device!");
			return true;
		}

		mColorEffectsMenu = menu.addSubMenu("Color Effect");
		mEffectMenuItems = new MenuItem[effects.size()];

		int idx = 0;
		ListIterator<String> effectItr = effects.listIterator();
		while(effectItr.hasNext()) {
			String element = effectItr.next();
			mEffectMenuItems[idx] = mColorEffectsMenu.add(1, idx, Menu.NONE, element);
			idx++;
		}

		mResolutionMenu = menu.addSubMenu("Resolution");
		mResolutionList = mOpenCvCameraView.getResolutionList();
		mResolutionMenuItems = new MenuItem[mResolutionList.size()];

		ListIterator<Size> resolutionItr = mResolutionList.listIterator();
		idx = 0;
		while(resolutionItr.hasNext()) {
			Size element = resolutionItr.next();
			mResolutionMenuItems[idx] = mResolutionMenu.add(2, idx, Menu.NONE,
					Integer.valueOf(element.width).toString() + "x" + Integer.valueOf(element.height).toString());
			idx++;
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
		if (item.getGroupId() == 1)
		{
			mOpenCvCameraView.setEffect((String) item.getTitle());
			Toast.makeText(this, mOpenCvCameraView.getEffect(), Toast.LENGTH_SHORT).show();
		}
		else if (item.getGroupId() == 2)
		{
			int id = item.getItemId();
			Size resolution = mResolutionList.get(id);
			mOpenCvCameraView.setResolution(resolution);
			resolution = mOpenCvCameraView.getResolution();
			String caption = Integer.valueOf(resolution.width).toString() + "x" + Integer.valueOf(resolution.height).toString();
			Toast.makeText(this, caption, Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.i(TAG,"onTouch event");
		/*
		 * THIS CODE WAS ORIGINALLY IN THE SAMPLE DOC
		 * COPIED INTO 'TAKE PHOTO' FUNCTION
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateandTime = sdf.format(new Date());
        fileName = Environment.getExternalStorageDirectory().getPath() +
                               "/sample_picture_" + currentDateandTime + ".jpg";
        mOpenCvCameraView.takePicture(fileName);
        Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();
		 */
		return false;

	}

	// You can use this function as a generic image processing example 

	public void  makeBinary(){
		Mat img = Imgcodecs.imread(fileName);

		Log.i("MAKE BINARY", fileName);
		Log.i("MAKE BINARY: ","Width:"+  img.width() + " Height: " + img.height());

		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY); 
		Imgproc.adaptiveThreshold(img, img,255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 21, 4);
		Bitmap bitmap = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(img, bitmap);// convert matrix to
		mOpenCvCameraView.setVisibility(SurfaceView.GONE);
		ImageView imgV = (ImageView) findViewById(R.id.imageView);
		imgV.setImageBitmap(bitmap);// set image view to 
	}

	public void takePicture(){
		//Takes picture 
		Log.e(TAG,"onTouch event");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateandTime = sdf.format(new Date());
		fileName = Environment.getExternalStorageDirectory().getPath() +
				"/sample_picture_" + currentDateandTime + ".jpg";
		mOpenCvCameraView.takePicture(fileName);
		Log.e("onClick", fileName);

	}


}
