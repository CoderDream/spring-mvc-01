package com.zhangguo.springmvc02.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/*
 * 定义控制器
 */
public class FooController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//返回一个模型视图对象，指定路径，指定模型的名称为message，值为一段字符串
		return new ModelAndView("foo/index", "message", "Hello，我是通过实现接口定义的一个控制器");
	}

}
