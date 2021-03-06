package com.zhangguo.springmvc51.controllers;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/up")
public class UpFileController {

	@RequestMapping("/file")
	public String file(Model model) {
		return "up/upfile";
	}

	@RequestMapping(value = "/fileSave", method = RequestMethod.POST)
	public String fileSave(Model model, MultipartFile[] files,
			HttpServletRequest request) throws Exception {

		// 文件存放的位置 (Tocmat 运行可以成功保存，但是Jetty会失败)
		// Tomcat
		// 路径：D:\Java\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\spring-mvc-01\files
		// Jetty 路径：D:\Java\GitHub\spring-mvc-01\src\main\webapp\files
		String path = request.getServletContext().getRealPath("/files");
		System.out.println("file path: \t" + path);
		for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			System.out.println("--------------------------");
			System.out
					.println("OriginalFilename\t" + file.getOriginalFilename());
			// File tempFile = new File(path, file.getOriginalFilename());
			File tempFile = new File(path, file.getOriginalFilename());
			File tempFile1 = new File(file.getOriginalFilename());
			System.out.println("tempFile: \t" + tempFile.getAbsolutePath());
			System.out.println("tempFile1: \t" + tempFile1.getAbsolutePath());
			file.transferTo(tempFile);

			// file.transferTo(new File("d:/temp/",
			// file.getOriginalFilename()));

			// MultipartHttpServletRequest multipartRequest =
			// (MultipartHttpServletRequest) request;
			// MultipartFile multipartFile =
			// multipartRequest.getFile("filename");
			// String fileName = multipartFile.getOriginalFilename();
			// File file = new File(“目标文件夹”,"想要保存后的文件名");
			// multipartFile.transferTo(file);
		}

		System.out.println(path);
		return "up/upfile";
	}

	@RequestMapping("/file3")
	public String file3(Model model) {
		return "up/upfile3";
	}

	@RequestMapping(value = "/file3Save", method = RequestMethod.POST)
	public String file3Save(Model model, MultipartFile[] files,
			HttpServletRequest request) throws Exception {

		// 文件存放的位置
		String path = request.getSession().getServletContext()
				.getRealPath("/files");
		System.out.println(path);
		String msg = "";
		for (MultipartFile file : files) {
			// 保存文件
			File tempFile = new File(path, file.getOriginalFilename());
			file.transferTo(tempFile);
			msg += "<img src='../files/" + file.getOriginalFilename()
					+ "' width='200' />";
		}
		model.addAttribute("message", msg);
		return "up/upfile3";
	}

}
