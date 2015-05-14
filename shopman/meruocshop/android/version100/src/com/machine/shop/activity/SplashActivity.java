package com.machine.shop.activity;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.google.analytics.tracking.android.EasyTracker;
import com.machine.shop.MainApplication;
import com.machine.shop.R;
import com.machine.shop.data.DataManager;

public class SplashActivity extends BaseActivity implements OnClickListener {

	private Dialog mDialogRefreshConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		AQUtility.setDebug(true);

		MainApplication.getInstance().setScreenSize(this);

		final int diplayTime = 2000;
		Thread welcomeThread = new Thread() {
			int wait = 0;

			@Override
			public void run() {
				try {
					super.run();

					if (MainApplication.getInstance().getSetting("created_shortcut") == null) {
						MainApplication.getInstance().createHomeShortcut(SplashActivity.this, SplashActivity.class);
						MainApplication.getInstance().saveSetting("created_shortcut", "1");
					}

					while (wait < diplayTime) {
						sleep(100);
						wait += 100;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}
			}
		};
		welcomeThread.start();

		loadTopic();

		int TitleBarHeight = getStatusBarHeight();
		System.out.println("TitleBarHeight " + TitleBarHeight);
	}

	private void loadTopic() {
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
				boolean isError = false;
				if (json != null) {
					try {
						DataManager.getInstance().parseProductCategories(json);

						startActivity(new Intent(SplashActivity.this, HomeActivity.class));
						SplashActivity.this.mManuallyDestroyed = true;
						finish();
					} catch (Exception e) {
						e.printStackTrace();
						status.invalidate();
						isError = true;
					}
				} else {
					isError = true;
				}

				if (isError)
					confirmRefresh();
			}
		};
		AjaxCallback.setGZip(true);
		DataManager.getInstance().loadProductCategories(this, cb, R.id.progress);
	}

	private void confirmRefresh() {
		mDialogRefreshConfirm = new Dialog(this);
		mDialogRefreshConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//mDialogExitConfirm.setTitle("Thoat Tin59s");

		View dlv = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null);
		mDialogRefreshConfirm.setContentView(dlv);

		AQuery aq2 = new AQuery(dlv);
		aq2.id(R.id.prompt_content_e).text(R.string.net_refresh_confirm);
		aq2.id(R.id.prompt_accept_e).clicked(this);
		aq2.id(R.id.prompt_cancel_e).clicked(this);
		mDialogRefreshConfirm.show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.prompt_accept_e) {
			mDialogRefreshConfirm.dismiss();
			loadTopic();
		} else if (v.getId() == R.id.prompt_cancel_e) {
			mDialogRefreshConfirm.dismiss();
			mDialogRefreshConfirm = null;
			System.gc();
			MainApplication.killApp(true);
		}
	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	public void onLowMemory() {
		BitmapAjaxCallback.clearCache();
		super.onLowMemory();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this); // Add this method.
	}
}
