

Spring MVC 学习总结（一）——MVC概要与环境配置
----------
http://www.cnblogs.com/best/p/5653916.html

两种方式启动和访问

方式一
----------

运行命令：mvn jetty:run 

访问路径：http://localhost:8080/Hello/SayHi



方式二
----------

运行命令：启动Tomcat

访问路径：http://localhost:8080/spring-mvc-01/Hello/SayHi

参考文档：

http://www.cnblogs.com/best/p/5653916.html





----------

Spring MVC 学习总结（二）——控制器定义与@RequestMapping详解
----------
http://www.cnblogs.com/best/p/5659596.html

 二、@RequestMapping详解
2.1、value 属性指定映射路径或URL模板

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0202.png)

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0203.png)

2.1.1、指定具体路径字符

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0204.png)

2.1.2、路径变量占位，URI模板模式

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0205.png)

2.1.3、正则表达式模式的URI模板

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0206.png)

2.1.4、矩阵变量@MatrixVariable

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0207.png)

2.1.5、Ant风格路径模式

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0208.png)

2.2、method属性指定谓词类型

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0209.png)

2.3、consumes属性指定请求的Content-Type

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0210.png)

2.4、produces属性指定响应的Content-Type

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0211.png)

2.5、params属性指定请求中必须有特定参数与值

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0212.png)

2.6、headers属性指定请求中必须有特定header值



2.7、name属性指定名称

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0213.png)

2.8、path属性指定路径

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_0214.png)






http://127.0.0.1:8080/foo03/action0?id=9527&id&name=star



http://127.0.0.1:8080/foo03/action01?id=1&name=toy&price=99


http://127.0.0.1:8080/foo03/action02?username=tom&product.name=book

为了方便这里我使用的是url，这里当然可以是一个表单，如下代码所示：

	<form method="post" action="foo/action02">
	     username:<input name="username" /><br/>
	     pdctname:<input name="product.name" /><br/>
	    <button>提交</button>
	</form>






1.1.4、List集合类型
----------

不能直接在action的参数中指定List<T>类型，定义一个类型包装List集合在其中，ProductList类如下所示：

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
		return "foo/index";
	}


运行结果：

http://localhost:8080/foo03/action03?items[0].name=phone&items[1].name=book




1.1.5、Map集合类型
----------


	// Map类型
	@RequestMapping("/action04")
	public String action04(Model model, ProductMap map) {
	    model.addAttribute("message", map.getItems().get("p1") + "<br/>" + map.getItems().get("p2"));
	    return "foo/index";
	}

访问路径：

http://localhost:8080/foo03/action04?items[p1].name=pen&items[p2].name=box

运行结果：



默认值
----------

@RequestParam共有4个注解属性，required属性表示是否为必须，默认值为true，如果请求中没有指定的参数会报异常；defaultValue用于设置参数的默认值，如果不指定值则使用默认值，只能是String类型的。name与value互为别名关系用于指定参数名称。

	// 1请求参数
	@RequestMapping("/action1")
	public String action1(Model model,
			@RequestParam(required = false, defaultValue = "99") int id) {
		model.addAttribute("message", id);
		return "foo/index";
	}


http://localhost:8080/foo03/action1?id=98


http://localhost:8080/foo03/action1



1.2.2、List与数组绑定基本数据类型
----------

在上一节中我们使用自动参数映射是不能直接完成List与数组绑定的，结合@RequestParam可以轻松实现，示例代码如下所示：


    // List集合与数组类型
    @RequestMapping("/action05")
    public String action05(Model model, @RequestParam("u") List<String> users) {
        model.addAttribute("message", users.get(0) + "," + users.get(1));
        return "foo/index";
    }


运行结果：

http://localhost:8080/foo03/action05?u=tom&u=rose



使用表单同样可行，页面脚本如下：


	<form action="bar/action11" method="post">
	    <p>
	        <label>爱好：</label> 
	        <input type="checkbox" value="15" name="id" />阅读
	         <input type="checkbox" value="20" name="id" />上网
	         <input type="checkbox" value="73" name="id" />电游
	    </p>
	    <button>提交</button>
	</form>


请求处理方法action代码如下：



    // List与数组绑定基本数据类型
    @RequestMapping("/action11")
    public String action11(Model model, @RequestParam("id") List<Integer> ids) {
        model.addAttribute("message", Arrays.deepToString(ids.toArray()));
        return "bar/index";
    }


运行结果：