package com.zhangguo.springmvc04.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foo04")
public class Foo04 {
	@RequestMapping("/jstl")
	public String jstl(Model model) {
		model.addAttribute("msg", "jstl view");
		model.addAttribute("users", new String[] { "Tom", "Mark", "Rose" });
		return "foo04/jstl";
	}

	@RequestMapping("/ftl")
	public String ftl(Model model) {
		model.addAttribute("msg", "FreeMarker view");
		model.addAttribute("users", new String[] { "Tom", "Mark", "Rose" });
		return "foo04/ftl";
	}
}
