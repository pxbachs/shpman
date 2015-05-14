package com.machine.shop;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.machine.shop.data.Product;
import com.machine.shop.data.ProductCatalog;

public class MainApplication extends Application {
	private static final String SETTING_NAME = "meruocshop";
	private static MainApplication instance;

	private long mLastCachedAvatar = 0;

	public static int IMAGE_WIDTH;

	public static int IMAGE_HEIGHT;

	public static float TOPIC_AVATAR_RATIO = 3.0f / 4.0f;

	public static float FULL_SCREEN_IMAGE_RATIO = 1.0f / 1.0f;

	public static float HALF_SCREEN_IMAGE_RATIO = 0.7f / 1.0f;

	public static int WIDTH;

	public static int HEIGHT;

	private static final int LOW_DPI_STATUS_BAR_HEIGHT = 19;

	private static final int MEDIUM_DPI_STATUS_BAR_HEIGHT = 25;

	private static final int HIGH_DPI_STATUS_BAR_HEIGHT = 38;

	private static final boolean FULL_SCREEN = false;
	public static final long CACHE_TIME = 3 * 60 * 1000;
	public static final int TOPIC_AVATAR_WIDTH = 480;
	public static final int TOPIC_AVATAR_HEIGHT = 300;

	public static int STATUS_HEIGHT = 0;

	public static Typeface mFontGeorgiaRef;
	public static Typeface mFontOpenSansLight;
	public static Typeface mFontOpenSansBold;
	public static Typeface mFontOpenSansSemiBold;

	private ProductCatalog mCurrentProductCatalog;
	private Product mCurrentProduct;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		mFontGeorgiaRef = Typeface.createFromAsset(getAssets(), "fonts/GeorgiaRef.ttf");
		mFontOpenSansLight = Typeface.createFromAsset(getAssets(), "fonts/OpenSansLight.ttf");
		//mFontOpenSansBold = Typeface.createFromAsset(getAssets(), "fonts/OpenSansBold.ttf");
		mFontOpenSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/OpenSansSemibold.ttf");
	}

	public static MainApplication getInstance() {
		return instance;
	}

	public static void saveSetting(String key, String value) {
		SharedPreferences settings = getInstance().getSharedPreferences(SETTING_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getSetting(String key, String defValue) {
		SharedPreferences settings = getInstance().getSharedPreferences(SETTING_NAME, 0);
		return settings.getString(key, defValue);
	}

	public static String getSetting(String key) {
		return getSetting(key, null);
	}

	public long getLastCachedAvatar() {
		return mLastCachedAvatar;
	}

	public void setLastCachedAvatar(long cachedTime) {
		if (this.mLastCachedAvatar == 0 || cachedTime - mLastCachedAvatar > CACHE_TIME) {
			this.mLastCachedAvatar = cachedTime;
			saveSetting("cachedavatar", "" + mLastCachedAvatar);
		}
	}

	public void setScreenSize(Activity context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		if (!FULL_SCREEN) {
			DisplayMetrics displayMetrics = new DisplayMetrics();
			((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

			switch (displayMetrics.densityDpi) {
			case DisplayMetrics.DENSITY_HIGH:
				STATUS_HEIGHT = AQUtility.dip2pixel(context, HIGH_DPI_STATUS_BAR_HEIGHT);
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				STATUS_HEIGHT = AQUtility.dip2pixel(context, MEDIUM_DPI_STATUS_BAR_HEIGHT);
				break;
			case DisplayMetrics.DENSITY_LOW:
				STATUS_HEIGHT = AQUtility.dip2pixel(context, LOW_DPI_STATUS_BAR_HEIGHT);
				break;
			default:
				STATUS_HEIGHT = AQUtility.dip2pixel(context, MEDIUM_DPI_STATUS_BAR_HEIGHT);
			}
		}

		WIDTH = metrics.widthPixels;
		HEIGHT = metrics.heightPixels - STATUS_HEIGHT;

		FULL_SCREEN_IMAGE_RATIO = (float) (HEIGHT + STATUS_HEIGHT) / (float) WIDTH;
	}

	public String getBuildId() {
		return "android_" + android.os.Build.MODEL + "_meruocshop";
	}

	public String getPlatform() {
		return new StringBuilder(android.os.Build.BOARD).append("/").append(android.os.Build.BRAND).append("/").append(android.os.Build.DEVICE).append("/").append(android.os.Build.VERSION.RELEASE).toString();
	}

	public String getIMEI() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String device_id = tm.getDeviceId();
		return device_id;
	}

	
	public ProductCatalog getCurrentProductCatalog() {
		return mCurrentProductCatalog;
	}

	public void setCurrentProductCatalog(ProductCatalog mCurrentProductCatalog) {
		this.mCurrentProductCatalog = mCurrentProductCatalog;
	}

	public Product getCurrentProduct() {
		return mCurrentProduct;
	}

	public void setCurrentProduct(Product mCurrentProduct) {
		this.mCurrentProduct = mCurrentProduct;
	}

	public static void killApp(boolean killSafely) {
		if (killSafely) {
			/*
			 * Notify the system to finalize and collect all objects of the app
			 * on exit so that the virtual machine running the app can be killed
			 * by the system without causing issues. NOTE: If this is set to
			 * true then the virtual machine will not be killed until all of its
			 * threads have closed.
			 */
			System.runFinalizersOnExit(true);

			/*
			 * Force the system to close the app down completely instead of
			 * retaining it in the background. The virtual machine that runs the
			 * app will be killed. The app will be completely created as a new
			 * app in a new virtual machine running in a new process if the user
			 * starts the app again.
			 */
			System.exit(0);
		} else {
			/*
			 * Alternatively the process that runs the virtual machine could be
			 * abruptly killed. This is the quickest way to remove the app from
			 * the device but it could cause problems since resources will not
			 * be finalized first. For example, all threads running under the
			 * process will be abruptly killed when the process is abruptly
			 * killed. If one of those threads was making multiple related
			 * changes to the database, then it may have committed some of those
			 * changes but not all of those changes when it was abruptly killed.
			 */
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	public static void doRestart(Activity anyActivity) {
		Intent i = anyActivity.getPackageManager().getLaunchIntentForPackage(anyActivity.getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		anyActivity.startActivity(i);
	}

	@Override
	public void onLowMemory() {
		BitmapAjaxCallback.clearCache();
	}

	public void showEmbeddedWebview(String url, Context from, Class wvClass) {
		Intent intent = new Intent(from, wvClass);
		intent.putExtra("url", url);

		from.startActivity(intent);
	}

	public void showWebPage(String url, Context from) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		from.startActivity(i);
	}

	public void callNumber(String number, Context from) {
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		from.startActivity(callIntent);
	}
	
	public void createHomeShortcut(Context context, Class className) {
		//Adding shortcut for MainActivity
		//on Home screen
		Intent shortcutIntent = new Intent(getApplicationContext(), className);

		shortcutIntent.setAction(Intent.ACTION_MAIN);

		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));

		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		getApplicationContext().sendBroadcast(addIntent);
	}
}
