package com.machine.shop.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.machine.shop.MainApplication;
import com.machine.shop.ShopLog;

public class BaseActivity extends Activity {

	protected boolean mManuallyDestroyed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (savedInstanceState != null) {
			MainApplication.doRestart(this);
			MainApplication.killApp(true);
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("opp", "opp");
	}

	@Override
	protected void onDestroy() {
		ShopLog.d("BaseActivity.onDestroy(): " + this.getComponentName());
		if (!mManuallyDestroyed) {
			ShopLog.d("Kill app by system");
			MainApplication.killApp(true);
		}
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		this.mManuallyDestroyed = true;
		super.onBackPressed();
	}
}
