package com.machine.shop.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.machine.shop.MainApplication;
import com.machine.shop.R;
import com.machine.shop.adapter.ProductSubCatalogAdapter;
import com.machine.shop.data.DataManager;
import com.machine.shop.data.ProductCatalog;

public class ProductSubCatalogActivity extends Activity implements OnItemClickListener, OnClickListener {

	private ProductSubCatalogAdapter mAdapter;
	private AQuery aq;
	private ArrayList<ProductCatalog> mProductCategories;
	private int mCurrentPositon;
	private int mParentId;

	private ProgressDialog mLoadingDialog;
	private Dialog mDialogRefreshConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_product_sub_catalog);

		mParentId = getIntent().getExtras().getInt("parent_id");

		mProductCategories = DataManager.getInstance().getProductCatalog(mParentId).getChildren();
		mAdapter = new ProductSubCatalogAdapter(this, mProductCategories);
		aq = new AQuery(this);

		aq.id(R.id.home_products).getGridView().setVerticalSpacing(20);
		aq.id(R.id.home_products).getGridView().setHorizontalSpacing(20);
		aq.id(R.id.home_products).adapter(mAdapter).itemClicked(this);

		aq.hardwareAccelerated11();
		MainApplication.getInstance().setLastCachedAvatar(System.currentTimeMillis());
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, final int posision, long id) {
		mCurrentPositon = posision;
		loadNews(mProductCategories.get(posision));
	}

	public void loadNews(final ProductCatalog aProductCatalog) {
		MainApplication.getInstance().setCurrentProductCatalog(aProductCatalog);
		
		if (aProductCatalog.getProducts() != null) {
			Intent intent = new Intent(this, ProductSummaryActivity.class);
			startActivity(intent);
			return;
		}

		if (mLoadingDialog == null) {
			mLoadingDialog = new ProgressDialog(this, R.style.LoadingDialog);
			mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mLoadingDialog.setMessage(this.getResources().getString(R.string.net_loading));
			mLoadingDialog.setIndeterminate(false);
			mLoadingDialog.setCancelable(true);
			mLoadingDialog.setInverseBackgroundForced(false);
			mLoadingDialog.setCanceledOnTouchOutside(true);
		}

		AjaxCallback<JSONArray> cb = new AjaxCallback<JSONArray>() {

			@Override
			public void callback(String url, JSONArray json, AjaxStatus status) {
				boolean isError = false;
				if (json != null) {
					try {
						DataManager.getInstance().parseProducts(aProductCatalog, json);

						if (aProductCatalog.getProducts() != null && aProductCatalog.getProducts().size() > 0) {
							Intent intent = new Intent(ProductSubCatalogActivity.this, ProductSummaryActivity.class);
							ProductSubCatalogActivity.this.startActivity(intent);
						} else {
							isError = true;
						}
					} catch (JSONException e) {
						isError = true;
					}
				} else {
					isError = true;
				}

				if (isError)
					confirmRetry();
			}
		};

		DataManager.getInstance().loadProducts(this, aq, aProductCatalog, cb, mLoadingDialog);
	}

	private void confirmRetry() {
		if (mDialogRefreshConfirm == null) {
			mDialogRefreshConfirm = new Dialog(this);
			mDialogRefreshConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
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
			loadNews(mProductCategories.get(mCurrentPositon));
		} else if (v.getId() == R.id.prompt_cancel_e) {
			mDialogRefreshConfirm.dismiss();
			mDialogRefreshConfirm = null;
			System.gc();
		}
	}
}
