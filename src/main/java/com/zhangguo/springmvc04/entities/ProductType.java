package com.zhangguo.springmvc04.entities;

import java.io.Serializable;

/**
 * 产品类型
 * 
 */
public class ProductType implements Serializable {
	private static final long serialVersionUID = 2L;
	/**
	 * 编号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name;

	public ProductType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ProductType() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "编号："+this.getId()+"，名称："+this.getName();
	}
}
