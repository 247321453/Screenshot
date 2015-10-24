package com.android.screenshot.test;

import com.android.screenshot.R;
import com.android.screenshot.ScreenshotAnimation;
import com.android.screenshot.ScreenshotUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

@SuppressLint("SdCardPath")
public class MainActivity extends Activity {

	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText edt01 = (EditText) findViewById(R.id.edt01);
		final ScreenshotAnimation screenshot = new ScreenshotAnimation(this);
		findViewById(R.id.main_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//延时
				edt01.setFocusable(false);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						final Bitmap bitmap = ScreenshotUtils.screenshot(MainActivity.this, false);
						new Thread(new Runnable() {
							@Override
							public void run() {
								String file = "/sdcard/sc.png";
								if (ScreenshotUtils.saveBitmap(bitmap, file, 100,
										false)) {
								}
								if (bitmap != null && !bitmap.isRecycled()) {
									bitmap.recycle();
								}
							}
						}).start();
						edt01.setFocusable(true);
						edt01.setFocusableInTouchMode(true);
						//动画效果
						Bitmap bitmap2 = Bitmap.createBitmap(bitmap);
						screenshot.setBitmap(bitmap2);
						screenshot.startAnimation(true, true);
					}
				}, 1000);
			}
		});
	}
}
