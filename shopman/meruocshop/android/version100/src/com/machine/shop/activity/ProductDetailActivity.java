package com.machine.shop.activity;

import android.app.Activity;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.machine.shop.Configuration;
import com.machine.shop.MainApplication;
import com.machine.shop.R;
import com.machine.shop.data.Product;

public class ProductDetailActivity extends Activity {

	Product mProduct;
	AQuery aq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_product_detail);
		
		mProduct = MainApplication.getInstance().getCurrentProduct();
		aq = new AQuery(this);
		aq.id(R.id.product_detail__image_full).image(Configuration.URL_GET_IMAGE + mProduct.getAvatarId());
		aq.id(R.id.product_detail_price).text(mProduct.getRegularPrice() + "");
		aq.id(R.id.product_detail_title).text(mProduct.getTitle());
	}

}
