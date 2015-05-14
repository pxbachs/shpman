package com.machine.shop.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.machine.shop.Configuration;
import com.machine.shop.R;

public class DataManager {
	private static DataManager instance = new DataManager();
	private ProductCatalog mProductCatalog;

	private DataManager() {

	}

	public void loadProductCategories(Context content, AjaxCallback<JSONObject> callback, int progress) {
		AQuery aq = new AQuery(content);
		aq.progress(progress).ajax(Configuration.URL_GET_PRODUCT_CATS, JSONObject.class, callback);
	}

	public void parseProductCategories(JSONObject root) {
		mProductCatalog = new ProductCatalog(0, "root", "0");
		parseProductCategories(mProductCatalog, root);

	}

	private void parseProductCategories(ProductCatalog aProductCat, JSONObject root) {
		try {
			Iterator keys = root.keys();
			ArrayList<ProductCatalog> aChildren = new ArrayList<ProductCatalog>();
			while (keys.hasNext()) {
				String aId = (String) keys.next();
				JSONObject aJProduct = root.getJSONObject(aId);
				ProductCatalog aProduct = new ProductCatalog(Integer.parseInt(aId), aJProduct.getString("name"), aJProduct.getString("avatar"));
				aChildren.add(aProduct);

				if (aJProduct.has("children")) {
					parseProductCategories(aProduct, aJProduct.getJSONObject("children"));
				}

				System.out.println(aProduct.toString());
			}
			aProductCat.setChildren(aChildren);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ProductCatalog getProductCatalog(int aProductId) {
		return getProductCatalog(mProductCatalog, aProductId);
	}

	private ProductCatalog getProductCatalog(ProductCatalog root, int aProductId) {
		if (root == null || root.getChildren() == null)
			return null;
		for (ProductCatalog pr : root.getChildren()) {
			if (pr.getId() == aProductId)
				return pr;
			if (pr.getChildren() != null) {
				ProductCatalog tmp = getProductCatalog(pr, aProductId);
				if (tmp != null)
					return tmp;
			}
		}

		return null;
	}

	public static DataManager getInstance() {

		return instance;
	}

	public ProductCatalog getProductCatalog() {
		return mProductCatalog;
	}

	public void parseProducts(ProductCatalog aProductCat, JSONArray json) throws JSONException{
		ArrayList<Product> aProducts = new ArrayList<Product>();
		for(int i =0; i < json.length(); i++){
			JSONObject jo = json.getJSONObject(i);
			Product aProduct = new Product(Integer.parseInt(jo.getString("id")));
			aProduct.setAvatarId(jo.getString("avatar"));
			aProduct.setRegularPrice(Integer.parseInt(jo.getString("regular_price")));
			aProduct.setTitle(jo.getString("title"));
			aProducts.add(aProduct);
		}
		
		aProductCat.setProducts(aProducts);
	}

	public void loadProducts(Context context, AQuery aq, ProductCatalog aProductCatalog, AjaxCallback<JSONArray> callback, ProgressDialog aLoadingDialog) {
		aq.progress(aLoadingDialog).ajax(Configuration.URL_GET_PRODUCTS + "" + aProductCatalog.getId(), JSONArray.class, callback);
	}

}
