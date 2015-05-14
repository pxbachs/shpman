package com.machine.shop;

public class Configuration {
	private static Configuration instance = new Configuration();
	public static final String URL_MAIN = "http://meruocshop.com/service/index.php/";
	public static final String URL_GET_PRODUCT_CATS = URL_MAIN + "data/productcats";
	public static final String URL_GET_PRODUCTS = URL_MAIN + "data/products/";
	public static final String URL_GET_IMAGE = URL_MAIN + "data/image/";

	public static Configuration getInstance() {
		return instance;
	}
}
