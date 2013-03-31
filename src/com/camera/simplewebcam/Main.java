package com.camera.simplewebcam;

import com.camera.simplewebcam.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Main extends Activity {
	
	CameraPreview cp;
	ImageButton takePictureButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		takePicture tpListener = new takePicture();
		
		setContentView(R.layout.main);
		
		takePictureButton = (ImageButton)findViewById(R.id.button1);
		takePictureButton.setOnClickListener(tpListener);
		
		cp = (CameraPreview)findViewById(R.id.cameraSurfaceView);
		cp.setButtonObject(tpListener);
	}


class takePicture implements OnClickListener {

	Handler workerThreadHandler;
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			workerThreadHandler = (Handler)msg.obj;
				
		}
	};
	
	public Handler getHandler()
	{
		return this.handler;
	}
	
	/*
	 * Triggers a message sent to the worker thread which saves 
	 * the current image.
	 * 
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
		Log.d("takePicture", "clicked");

		Message msg = workerThreadHandler.obtainMessage();
		msg.arg1 = 2;
		msg.what = 0;
		workerThreadHandler.sendMessage(msg);
		
	}
	
}
	
}
