package com.machine.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidquery.AQuery;
import com.machine.shop.MainApplication;
import com.machine.shop.R;
import com.machine.shop.adapter.ProductSummaryAdapter;
import com.machine.shop.data.Product;
import com.machine.shop.data.ProductCatalog;

public class ProductSummaryActivity extends Activity implements OnItemClickListener {

	private ProductSummaryAdapter mAdapter;
	private AQuery aq;
	private ProductCatalog mProductCatalog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_product_summary);
		mProductCatalog = MainApplication.getInstance().getCurrentProductCatalog();//DataManager.getInstance().getProductCatalog(mProductCatalogId);

		mAdapter = new ProductSummaryAdapter(this, mProductCatalog);
		aq = new AQuery(this);

		aq.id(R.id.product_summary_grid).getGridView().setVerticalSpacing(20);
		aq.id(R.id.product_summary_grid).getGridView().setHorizontalSpacing(20);
		aq.id(R.id.product_summary_grid).adapter(mAdapter).itemClicked(this);

//		((ImageButton) findViewById(R.id.button_setting)).setOnClickListener(this);
//		((ImageButton) findViewById(R.id.button_refresh)).setOnClickListener(this);
//		
//		ImageButton btn = (ImageButton) findViewById(R.id.button_refresh);
//		btn.setOnClickListener(this);

		aq.id(R.id.product_summary_grid).getGridView().setVerticalSpacing(20);
		aq.id(R.id.product_summary_grid).getGridView().setHorizontalSpacing(20);

		
		aq.hardwareAccelerated11();
		MainApplication.getInstance().setLastCachedAvatar(System.currentTimeMillis());
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, final int posision, long id) {
		Product aProduct = mProductCatalog.getProducts().get(posision);
		MainApplication.getInstance().setCurrentProduct(aProduct);
		Intent intent = new Intent(this, ProductDetailActivity.class);
		startActivity(intent);
	}

//	@Override
//	public void onClick(View v) {
//		if (v.getId() == R.id.prompt_accept_e) {
//		} else if (v.getId() == R.id.prompt_cancel_e) {
//		}
//	}
}
