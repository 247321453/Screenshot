package com.android.screenshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by xf on 2014/9/14.
 */
public class ScreenshotUtils {

	public static Bitmap screenshot(Activity activity, boolean hasstatubar) {
		if (activity == null) {
			return null;
		}
		Window window = activity.getWindow();
		if (window == null) {
			return null;
		}
		return screenshot(window.getDecorView(), hasstatubar);
	}

	public static Bitmap screenshot(View view, boolean hasstatubar) {
		if (view == null) {
			return null;
		}
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap cachebmp = view.getDrawingCache();
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		DisplayMetrics m = view.getResources().getDisplayMetrics();
		// 获取屏幕宽和高
		int widths = m.widthPixels;
		int heights = m.heightPixels;
		Bitmap bmp = null;
		if (cachebmp != null) {
			if (hasstatubar) {
				bmp = Bitmap.createBitmap(cachebmp, 0, 0, widths, heights);
			} else {
				bmp = Bitmap.createBitmap(cachebmp, 0, statusBarHeights, widths,
						heights - statusBarHeights);
			}
		}
		view.destroyDrawingCache();
		return bmp;
	}

	public static boolean saveBitmap(Bitmap bm, String file, int quality,
			boolean isJpeg) {
		if (bm == null) {
			Log.e("bitmap", "save bitmap fail:" + file);
			return false;
		}
		File f = new File(file);
		File dir = f.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			if (isJpeg)
				bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
			else
				bm.compress(Bitmap.CompressFormat.PNG, quality, out);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Log.e("bitmap", "save bitmap ok:" + file);
		return true;
	}
}
