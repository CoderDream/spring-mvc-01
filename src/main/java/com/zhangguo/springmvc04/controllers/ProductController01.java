package com.zhangguo.springmvc04.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zhangguo.springmvc04.entities.Product;
import com.zhangguo.springmvc04.services.ProductService01;
import com.zhangguo.springmvc04.services.ProductTypeService01;

@Controller
@RequestMapping("/product01")
public class ProductController01 {
	
	@Autowired
	ProductService01 productService;
	
	@Autowired
	ProductTypeService01 productTypeService;

	// 展示与搜索action
	@RequestMapping
	public String index(Model model, String searchKey) {
		model.addAttribute("products",
				productService.getProductsByName(searchKey));
		model.addAttribute("searchKey", searchKey);
		return "product01/index";
	}

	// 删除，id为路径变量
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		productService.deleteProduct(id);
		return "redirect:/";
	}

	// 多删除，ids的值为多个id参数组成
	@RequestMapping("/deletes")
	public String deletes(@RequestParam("id") int[] ids) {
		productService.deletesProduct(ids);
		return "redirect:/";
	}

	// 新增，渲染出新增界面
	@RequestMapping("/add")
	public String add(Model model) {
		// 与form绑定的模型
		model.addAttribute("product", new Product());
		// 用于生成下拉列表
		model.addAttribute("productTypes",
				productTypeService.getAllProductTypes());
		return "product01/add";
	}

	// 新增保存，如果新增成功转回列表页，如果失败回新增页，保持页面数据
	@RequestMapping("/addSave")
	public String addSave(Model model, Product product) {
		try {
			// 根据类型的编号获得类型对象
			product.setProductType(productTypeService
					.getProductTypeById(product.getProductType().getId()));
			productService.addProduct(product);
			return "redirect:/";
		} catch (Exception exp) {
			// 与form绑定的模型
			model.addAttribute("product", product);
			// 用于生成下拉列表
			model.addAttribute("productTypes",
					productTypeService.getAllProductTypes());
			// 错误消息
			model.addAttribute("message", exp.getMessage());
			return "product01/add";
		}
	}

	// 编辑，渲染出编辑界面，路径变量id是用户要编辑的产品编号
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		// 与form绑定的模型
		model.addAttribute("product", productService.getProductById(id));
		// 用于生成下拉列表
		model.addAttribute("productTypes",
				productTypeService.getAllProductTypes());
		return "product01/edit";
	}

	// 编辑后保存，如果更新成功转回列表页，如果失败回编辑页，保持页面数据
	@RequestMapping("/editSave")
	public String editSave(Model model, Product product) {
		try {
			// 根据类型的编号获得类型对象
			product.setProductType(productTypeService
					.getProductTypeById(product.getProductType().getId()));
			productService.updateProduct(product);
			return "redirect:/";
		} catch (Exception exp) {
			// 与form绑定的模型
			model.addAttribute("product", product);
			// 用于生成下拉列表
			model.addAttribute("productTypes",
					productTypeService.getAllProductTypes());
			// 错误消息
			model.addAttribute("message", exp.getMessage());
			return "product01/edit";
		}
	}
}
