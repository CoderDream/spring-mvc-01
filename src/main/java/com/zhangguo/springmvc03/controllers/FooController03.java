package com.zhangguo.springmvc03.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhangguo.springmvc03.entities.Product;
import com.zhangguo.springmvc03.entities.ProductList;
import com.zhangguo.springmvc03.entities.ProductMap;
import com.zhangguo.springmvc03.entities.User;

@Controller
@RequestMapping("/foo03")
public class FooController03 {
	// 0 自动参数映射
	@RequestMapping("/action0")
	public String action0(Model model, int id, String name) {
		model.addAttribute("message", "name=" + name + ",id=" + id);
		return "foo03/index";
	}

	// 自动参数映射 自定义数据类型
	@RequestMapping("/action01")
	public String action01(Model model, Product product) {
		model.addAttribute("message", product);
		return "foo03/index";
	}

	// 自动参数映射 复杂数据类型
	@RequestMapping("/action02")
	public String action02(Model model, User user) {
		model.addAttribute("message",
				user.getUsername() + "," + user.getProduct().getName());
		return "foo03/index";
	}

	// 集合类型
	@RequestMapping("/action03")
	public String action03(Model model, ProductList products) {
		System.out.println("products\t" + products);
		if (null != products.getItems()) {
			model.addAttribute("message", products.getItems().get(0) + "<br/>"
					+ products.getItems().get(1));
		} else {
			System.out.println("products is null!!!");
		}
		return "foo03/index";
	}

	// Map类型
	@RequestMapping("/action04")
	public String action04(Model model, ProductMap map) {
		model.addAttribute("message",
				map.getItems().get("p1") + "<br/>" + map.getItems().get("p2"));
		return "foo03/index";
	}

	// List集合与数组类型
	@RequestMapping("/action05")
	public String action05(Model model, @RequestParam("u") List<String> users) {
		model.addAttribute("message", users.get(0) + "," + users.get(1));
		return "foo03/index";
	}

	// List集合与数组类型
	@RequestMapping("/action06")
	@ResponseBody
	public List<Product> action06(Model model,
			@RequestBody List<Product> products) {
		return products;
	}

	// 1请求参数
	@RequestMapping("/action1")
	public String action1(Model model,
			@RequestParam(required = false, defaultValue = "99") int id) {
		model.addAttribute("message", id);
		return "foo03/index";
	}
	
    // 重定向
    @RequestMapping("/action2a")
    public String action2(Model model) {
        return "foo03/index";
    }

    @RequestMapping("/action3a")
    public String action3(Model model) {
        model.addAttribute("message", "action3Message");
        return "redirect:action2a";
    }

	// 2重定向
	@RequestMapping("/action2")
	public String action2(Model model, Product product) {
		model.addAttribute("message", product);
		System.out.println(model.containsAttribute("product")); // true
		return "foo03/index";
	}

	// 重定向属性
	@RequestMapping("/action3")
	public String action3(Model model, RedirectAttributes redirectAttributes) {
		Product product = new Product(2, "iPhone7 Plus", 6989.5);
		redirectAttributes.addFlashAttribute("product", product);
		return "redirect:action2";
	}

	@RequestMapping("/action4")
	public String action4(Model model, RedirectAttributes redirectAttributes) {
		Product product = new Product(3, "iPhone6 Plus", 4989.9);
		redirectAttributes.addFlashAttribute("product", product);
		redirectAttributes.addFlashAttribute("message", "产品信息");
		return "redirect:../show.jsp";
	}

	@RequestMapping("/action5")
	public String action5(Model model) {
		return "foo03/action5";
	}

	@RequestMapping("/action6")
	public String action6(Model model,
			@ModelAttribute(name = "product", binding = true) Product entity) {
		model.addAttribute("message",
				model.containsAttribute("product") + "<br/>" + entity);
		return "foo03/index";
	}

	@RequestMapping("/action7")
	public String action7(Model model) {
		Map<String, Object> map = model.asMap();
		for (String key : map.keySet()) {
			System.out.println(key + "：" + map.get(key));
		}
		return "foo03/index";
	}

	@ModelAttribute
	public String noaction() {
		System.out.println("noaction 方法被调用！");
		String message = "来自noaction方法的信息";
		return message;
	}
}
