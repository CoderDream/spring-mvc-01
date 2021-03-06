package com.zhangguo.springmvc04.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.zhangguo.springmvc04.entities.ProductType;

@Service
public class ProductTypeServiceImpl01 implements ProductTypeService01 {

	private static List<ProductType> productTypes;

	static {
		productTypes = new ArrayList<ProductType>();
		productTypes.add(new ProductType(11, "数码电子"));
		productTypes.add(new ProductType(21, "鞋帽服饰"));
		productTypes.add(new ProductType(31, "图书音像"));
		productTypes.add(new ProductType(41, "五金家电"));
		productTypes.add(new ProductType(51, "生鲜水果"));
	}

	@Override
	public ProductType getProductTypeById(int id) {
		for (ProductType productType : productTypes) {
			if (productType.getId() == id) {
				return productType;
			}
		}
		return null;
	}

	@Override
	public List<ProductType> getAllProductTypes() {
		return productTypes;
	}

}
