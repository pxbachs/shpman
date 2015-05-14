package com.machine.shop.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidquery.AQuery;
import com.machine.shop.MainApplication;
import com.machine.shop.R;
import com.machine.shop.adapter.ProductCatalogAdapter;
import com.machine.shop.data.DataManager;
import com.machine.shop.data.ProductCatalog;

public class HomeActivity extends Activity implements OnItemClickListener, OnClickListener {

	private ProductCatalogAdapter mAdapter;
	private AQuery aq;
	private ArrayList<ProductCatalog> mProductCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		
		mProductCategories = DataManager.getInstance().getProductCatalog().getChildren();
		mAdapter = new ProductCatalogAdapter(this, mProductCategories);
		aq = new AQuery(this);

		aq.id(R.id.home_products).getGridView().setVerticalSpacing(20);
		aq.id(R.id.home_products).getGridView().setHorizontalSpacing(20);
		aq.id(R.id.home_products).adapter(mAdapter).itemClicked(this);

//		((ImageButton) findViewById(R.id.button_setting)).setOnClickListener(this);
//		((ImageButton) findViewById(R.id.button_refresh)).setOnClickListener(this);
//		
//		ImageButton btn = (ImageButton) findViewById(R.id.button_refresh);
//		btn.setOnClickListener(this);

		aq.hardwareAccelerated11();
		MainApplication.getInstance().setLastCachedAvatar(System.currentTimeMillis());
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, final int posision, long id) {
		Intent intent = new Intent(this, ProductSubCatalogActivity.class);
		intent.putExtra("parent_id", mProductCategories.get(posision).getId());
		
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.prompt_accept_e) {
		} else if (v.getId() == R.id.prompt_cancel_e) {
		}
	}
}
