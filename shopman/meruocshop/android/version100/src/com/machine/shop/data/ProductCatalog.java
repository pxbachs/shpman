package com.machine.shop.data;

import java.util.ArrayList;

public class ProductCatalog {
	private int id;
	private String name;
	private String avatar;
	private ArrayList<ProductCatalog> children;
	private ArrayList<Product> products;

	public ProductCatalog(int id, String name, String avatar) {
		this.id = id;
		this.name = name;
		this.avatar = avatar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public ArrayList<ProductCatalog> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ProductCatalog> children) {
		this.children = children;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

}
