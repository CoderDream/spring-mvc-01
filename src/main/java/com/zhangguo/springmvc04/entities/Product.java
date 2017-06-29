package com.zhangguo.springmvc04.entities;

import java.io.Serializable;

/**
 * 产品
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * 编号
	 */
	private int id;
	/*
	 * 名称
	 */
	private String name;
	/*
	 * 价格
	 */
	private double price;
	/*
	 * 产品类型
	 */
	private ProductType productType;

	public Product() {
		productType=new ProductType();
	}

	public Product(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}

	public Product(int id, String name, double price, ProductType type) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.productType = type;
	}

	@Override
	public String toString() {
		return "编号(id)：" + this.getId() + "，名称(name)：" + this.getName() + "，价格(price)：" + this.getPrice()
				+ "，类型(productType.Name)：" + this.getProductType().getName();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
