package com.machine.shop.data;


public class Product {
	private int id;
	private String title;
	private String des;
	private String content;
	private int regularPrice;
	private int salePrice;
	private String avatarId;
	private String[] gallery;

	public Product(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(int regularPrice) {
		this.regularPrice = regularPrice;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String[] getGallery() {
		return gallery;
	}

	public void setGallery(String gallery) {
		this.gallery = gallery.split(",");
	}

}
