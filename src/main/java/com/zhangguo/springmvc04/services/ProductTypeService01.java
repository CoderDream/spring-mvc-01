package com.zhangguo.springmvc04.services;

import java.util.List;

import com.zhangguo.springmvc04.entities.ProductType;

/**
 * 产品类型服务
 *
 */
public interface ProductTypeService01 {
	/**
	 * 根据产品类型编号获得产品类型对象
	 */
	public ProductType getProductTypeById(int id);
	
	/**
	 * 获得所有的产品类型
	 */
	public List<ProductType> getAllProductTypes();
}
