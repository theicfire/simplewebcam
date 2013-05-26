package com.camera.simplewebcam;

import com.camera.simplewebcam.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Main extends Activity {
	
	private static final String PREFERENCES_MATRIX = "com.camera.simplewebcam.flipmatrix_";

	private static final String MENU_ITEM_FLIPLR = "Flip Horizontal";
	private static final int MENU_ITEM_ID_FLIPLR = 1;
	private static final String MENU_ITEM_FLIPUD = "Flip Vertical";
	private static final int MENU_ITEM_ID_FLIPUD = 2;
	private static final int MENU_GROUP_FLIP = 1; 
	
	CameraPreview cp;
	ImageButton takePictureButton;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		takePicture tpListener = new takePicture();
		
		setContentView(R.layout.main);
		
		preferences = this.getPreferences(MODE_PRIVATE);
		editor = preferences.edit();
		
		takePictureButton = (ImageButton)findViewById(R.id.button1);
		takePictureButton.setOnClickListener(tpListener);
		
		cp = (CameraPreview)findViewById(R.id.cameraSurfaceView);
		cp.setButtonObject(tpListener);
		
		//load preferences to matrix
		Matrix tmp_mx = new Matrix();
		tmp_mx.reset();
		float[] mx_array = new float[9];
		tmp_mx.getValues(mx_array);
		for(int i=0;i<mx_array.length;i++)
		{
			mx_array[i] = preferences.getFloat(PREFERENCES_MATRIX + i,mx_array[i]);
		}
		this.cp.mx.setValues(mx_array);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(MENU_GROUP_FLIP,MENU_ITEM_ID_FLIPLR,Menu.NONE,MENU_ITEM_FLIPLR);
		menu.add(MENU_GROUP_FLIP,MENU_ITEM_ID_FLIPUD,Menu.NONE,MENU_ITEM_FLIPUD);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@SuppressWarnings("null")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getGroupId())
		{
			case MENU_GROUP_FLIP:
				
				switch(item.getItemId())
				{
					case MENU_ITEM_ID_FLIPLR:
						this.cp.mx.postScale(-1.0f,1.0f);
						this.cp.mx.postTranslate(CameraPreview.IMG_WIDTH, 0);
						break;
						
					case MENU_ITEM_ID_FLIPUD:
						this.cp.mx.postScale(1.0f,-1.0f);
						this.cp.mx.postTranslate(0, CameraPreview.IMG_HEIGHT);
						break;
						
					default:
						Log.e(this.getLocalClassName(), "unrecognized menu id group");
						break;
				}
				
				//save matrix to preferences
				float[] mx_array = new float[9];
				this.cp.mx.getValues(mx_array);
				for(int i=0;i<mx_array.length;i++)
				{
					editor.putFloat(PREFERENCES_MATRIX + i, mx_array[i]);
				}
				editor.commit();
				break;
				
			default:
				Log.e(this.getLocalClassName(), "unrecognized menu group");
				break;
		}
			
		
		return super.onOptionsItemSelected(item);
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
