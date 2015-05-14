package com.machine.shop.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.machine.shop.AppResources;
import com.machine.shop.Configuration;
import com.machine.shop.MainApplication;
import com.machine.shop.R;
import com.machine.shop.data.ProductCatalog;

public class ProductCatalogAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	protected AQuery aq;

	private ProductCatalog[] mProductCatalog;

	private View[] views;

	public ProductCatalogAdapter(Context context, ArrayList<ProductCatalog> aProductCatalog) {
		this.context = context;
		this.mProductCatalog = new ProductCatalog[aProductCatalog.size()];
		aProductCatalog.toArray(this.mProductCatalog);
		inflater = LayoutInflater.from(context);

		views = new View[this.mProductCatalog.length];
	}

	@Override
	public int getCount() {
		return mProductCatalog.length;
	}

	@Override
	public Object getItem(int position) {
		return mProductCatalog[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void refreshView() {
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (views[position] == null) {
			View gridView;

			if (views[position] == null) {
				gridView = inflater.inflate(R.layout.product_cat_item, null);
			} else {
				gridView = views[position];
			}

			aq = new AQuery(gridView);


			String avatar_url = new StringBuilder(Configuration.URL_GET_IMAGE).append(mProductCatalog[position].getAvatar()).append("/").append(MainApplication.TOPIC_AVATAR_WIDTH).append("/").append(MainApplication.TOPIC_AVATAR_HEIGHT).toString();
			Bitmap present = null;

			if (aq.getCachedFile(avatar_url) != null) {
				present = BitmapFactory.decodeFile(aq.getCachedFile(avatar_url).getAbsolutePath());
			} else {
				if (AppResources.PH_TOPIC_AVATAR == null)
					AppResources.PH_TOPIC_AVATAR = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder_category);
				present = AppResources.PH_TOPIC_AVATAR;
			}
			
//			aq.id(R.id.layout_topic_item).height(MainApplication.TOPIC_AVATAR_HEIGHT, false);
			aq.id(R.id.product_cat_img).height(MainApplication.TOPIC_AVATAR_HEIGHT, false);
			aq.id(R.id.product_cat_overlay).height(MainApplication.TOPIC_AVATAR_HEIGHT, false);
			
			aq.id(R.id.product_cat_img).progress(R.id.progress).image(avatar_url, false, true, 0, R.drawable.placeholder_category, present, AQuery.FADE_IN);
			aq.id(R.id.product_cat_name).text(mProductCatalog[position].getName());
			views[position] = gridView;
		}

		return views[position];
	}
}
