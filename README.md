

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

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action00.png)

http://127.0.0.1:8080/foo03/action01?id=1&name=toy&price=99


http://127.0.0.1:8080/foo03/action02?username=tom&product.name=book

为了方便这里我使用的是url，这里当然可以是一个表单，如下代码所示：

	<form method="post" action="foo/action02">
	     username:<input name="username" /><br/>
	     pdctname:<input name="product.name" /><br/>
	    <button>提交</button>
	</form>

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action02.png)


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

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action03.png)


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

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action04.png)

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

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action03c.png)

http://localhost:8080/foo03/action1

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action03b.png)

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

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action05.png)

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

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action11a.png)
 
![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action11b.png)


1.2.3、List与数组直接绑定自定义数据类型与AJAX
----------

上一小节中我们绑定的集合中存放的只是基本数据类型，如果需要直接绑定更加复杂的数据类型则需要使用@RequestBody与@ResponseBody注解了，先解释一下他们的作用：


- @RequestBody 将HTTP请求正文转换为适合的HttpMessageConverter对象。
- @ResponseBody 将内容或对象作为 HTTP 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。


AnnotationMethodHandlerAdapter将会初始化7个转换器，可以通过调用AnnotationMethodHandlerAdapter的getMessageConverts()方法来获取转换器的一个集合 List<HttpMessageConverter>，7个转换器类型分别是

1. ByteArrayHttpMessageConverter 
1. StringHttpMessageConverter 
1. ResourceHttpMessageConverter 
1. SourceHttpMessageConverter 
1. XmlAwareFormHttpMessageConverter 
1. Jaxb2RootElementHttpMessageConverter 
1. MappingJacksonHttpMessageConverter


@RequestBody默认接收的Content-Type是application/json，因此发送POST请求时需要设置请求报文头信息，否则Spring MVC在解析集合请求参数时不会自动的转换成JSON数据再解析成相应的集合，Spring默认的json协议解析由Jackson完成。要完成这个功能还需要修改配置环境，具体要求如下：

a)、修改Spring MVC配置文件，启用mvc注解驱动功能，<mvc:annotation-driven />

b)、pom.xml，添加jackson依赖，添加依赖的配置内容如下：

    <!-- jackson -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.5.2</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.5.2</version>
    </dependency>

c)、ajax请求时需要设置属性dataType 为 json，contentType 为 'application/json;charse=UTF-8'，data 转换成JSON字符串，如果条件不满足有可能会出现415异常。

Action定义的示例代码如下：

    // List与数组直接绑定自定义数据类型与AJAX
    @RequestMapping("/action21")
    public void action21(@RequestBody List<Product> products, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(Arrays.deepToString(products.toArray()));
        response.getWriter().write("添加成功");
    }

action21的参数@RequestBody List<Product> products是接收从客户端发送到服务器的产品集合，默认的请求内容并非是application/json，而是：application/x-www-form-urlencoded，在参数前增加@RequestBody的作用是让Spring MVC在收到客户端请求时将选择合适的转换器将参数转换成相应的对象。action22的返回值为List<Product>，且在方法上有一个注解@ResponseBody，系统会使用jackson将该对象自动序列化成json字符；在客户端请求时设置内容类型为application/json，定义一个myform21.jsp页面，页面的脚本如下所示：
	
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>List与数组直接绑定自定义数据类型与AJAX</title>
	</head>
	<body>
	    <button type="button" onclick="addPdts_click1();">向服务器发送json</button>
	    <button type="button" onclick="addPdts_click2();">接收服务器返回的json</button>
	    <p id="msg"></p>
	    <script type="text/javascript"
	        src="<c:url value="/scripts/jQuery1.11.3/jquery-1.11.3.min.js"/>"></script>
	    <script type="text/javascript">
	        var products = new Array();
	        products.push({
	            id : 1,
	            name : "iPhone 6 Plus",
	            price : 4987.5
	        });
	        products.push({
	            id : 2,
	            name : "iPhone 7 Plus",
	            price : 5987.5
	        });
	        products.push({
	            id : 3,
	            name : "iPhone 8 Plus",
	            price : 6987.5
	        });
	        function addPdts_click1() {
	            $.ajax({
	                type : "POST",
	                //请求谓词类型
	                url : "bar/action21",
	                data : JSON.stringify(products), //将products对象转换成json字符串
	                contentType : "application/json;charset=UTF-8",
	                //发送信息至服务器时内容编码类型，(默认: "application/x-www-form-urlencoded")
	                dataType : "text", //预期服务器返回的数据类型
	                success : function(result) {
	                    $("#msg").html(result);
	                }
	            });
	        }
	        function addPdts_click2() {
	            $.ajax({
	                type : "POST",
	                //请求谓词类型
	                url : "bar/action22",
	                data : JSON.stringify(products), //将products对象转换成json字符串
	                contentType : "application/json;charset=UTF-8",
	                //发送信息至服务器时内容编码类型，(默认: "application/x-www-form-urlencoded")
	                dataType : "json", //预期服务器返回的数据类型
	                success : function(result) {
	                    var str = "";
	                    $.each(result, function(i, obj) {
	                        str += "编号：" + obj.id + ",名称：" + obj.name + ",价格："+ obj.price + "<br/>";
	                    });
	                    $("#msg").html(str);
	                }
	            });
	        }
	    </script>
	</body>
	</html>


页面中有两个方法，第一个方法是实现将一个json集合发送到服务器并映射成一个List集合；第二个方法是实现接收服务器返回的json对象。

点击按钮1时的运行结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action21a.png)

控制台输出：

	[编号(id)：1，名称(name)：iPhone 6 Plus，价格(price)：4987.5, 编号(id)：2，名称(name)：iPhone 7 Plus，价格(price)：5987.5, 编号(id)：3，名称(name)：iPhone 8 Plus，价格(price)：6987.5]

点击按钮2时的运行结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action21b.png)

1.3、重定向与Flash属性
----------

在一个请求处理方法Action中如果返回结果为“index”字符则表示转发到视图index，有时候我们需要重定向，则可以在返回的结果前加上一个前缀“redirect:”，可以重定向到一个指定的页面也可以是另一个action，示例代码如下：


    // 重定向
    @RequestMapping("/action2a")
    public String action2(Model model) {
        return "foo/index";
    }

    @RequestMapping("/action3a")
    public String action3(Model model) {
        model.addAttribute("message", "action3Message");
        return "redirect:action2a";
    }


当请求 http://localhost:8080/foo03/action3a 时运行结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action22b.png)

在action3中返回的结果为redirect:action2，则表示重定向到action2这个请求处理方法，所有重定向都是以当前路径为起点的，请注意路径。在action3向model中添加了名称message的数据，因为重定向到action2中会发起2次请求，为了保持action3中的数据Spring MVC自动将数据重写到了url中。为了实现重定向时传递复杂数据，可以使用Flash属性，示例代码如下：

    // 接收重定向参数
    @RequestMapping("/action2")
    public String action2(Model model, Product product) {
        model.addAttribute("message", product);
        System.out.println(model.containsAttribute("product")); // true
        return "foo/index";
    } 

    //重定向属性
    @RequestMapping("/action3")
    public String action3(Model model, RedirectAttributes redirectAttributes) {
        Product product = new Product(2, "iPhone7 Plus", 6989.5);
        redirectAttributes.addFlashAttribute("product", product);
        return "redirect:action2";
    }


当访问action3时，首先创建了一个product产口对象，将该对象添加到了Flash属性中，在重定向后取出，个人猜测应该暂时将对象存入了Session中。当请求foo03/action3时运行结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action22a.png)

url地址已经发生了变化，product对象其实也已经被存入了model中，在action的视图中可以直接拿到。



1.4、@ModelAttribute模型特性
----------

@ModelAttribute可以应用在方法参数上或方法上，他的作用主要是当注解在方法中时会将注解的参数对象添加到Model中；当注解在请求处理方法Action上时会将该方法变成一个非请求处理的方法，但其它Action被调用时会首先调用该方法。

### 1.4.1、注解在参数上 ###

当注解在参数上时会将被注解的参数添加到Model中，并默认完成自动数据绑定，示例代码如下：


    @RequestMapping("/action6")
    public String action6(Model model, @ModelAttribute(name = "product", binding = true) Product entity) {
        model.addAttribute("message", model.containsAttribute("product") + "<br/>" + entity);
        return "foo/index";
    }

http://localhost:8080/foo03/action6?id=6&name=pen&price=15.8

运行结果：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action26.png)

其实不使用@ModelAttribute我也样可以完成参数与对象间的自支映射，但使用注解可以设置更多详细内容，如名称，是否绑定等。

### 1.4.2、注解在方法上 ###

用于标注一个非请求处理方法，通俗说就是一个非Action，普通方法。如果一个控制器类有多个请求处理方法，以及一个有@ModelAttribute注解的方法，则在调用其它Action时会先调用非请求处理的Action，示例代码如下：

    @RequestMapping("/action7")
    public String action7(Model model) {
        Map<String, Object> map = model.asMap();
        for (String key : map.keySet()) {
            System.out.println(key + "：" + map.get(key));
        }
        return "foo/index";
    }

    @ModelAttribute
    public String noaction() {
        System.out.println("noaction 方法被调用！");
        String message = "来自noaction方法的信息";
        return message;
    }

当访问http://localhost:8080/foo03/action7时，控制台显示结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action27.png)

非请求处理方法可以返回void，也可以返回一个任意对象，该对象会被自动添加到每一个要被访问的Action的Model中，key从示例中可以看出为类型名称。

二、Action返回值类型
----------

1. ModelAndView
1. Model
1. Map 包含模型的属性
1. View
1. String 视图名称
1. void
1. HttpServletResponse
1. HttpEntity<?>或ResponseEntity<?>
1. HttpHeaders
1. Callable<?>
1. DeferredResult<?>
1. ListenableFuture<?>
1. ResponseBodyEmitter
1. SseEmitter
1. StreamingResponseBody

其它任意类型，Spring将其视作输出给View的对象模型

2.1、视图中url问题
新增一个action5，代码如下：


    @RequestMapping("/action5")
    public String action5(Model model) {
        return "foo/action5";
    }

在foo目录下添加视图action5.jsp，内容如下：

	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>action5的视图</title>
	</head>
	<body>
	    <img alt="风景" src="../../images/3.jpg">
	</body>
	</html>


目标结构如下：



访问结果：



这里图片访问不到的原因是因为：action5.jsp视图此时并非以它所在的目录为实际路径，他是以当前action所在的控制器为起始目录的，当前控制器的url为：http://localhost:8087/SpringMVC02/foo/，而图片的src为：../../images/3.jpg，向上2级后变成：http://localhost:8087/images/3.jpg，但我们的项目实际路径中并没有存放3.jpg这张图片，解决的办法是在视图中使用“绝对”路径；另外一个问题是我们将静态资源存放到WEB-INF下不太合理，因为该目录禁止客户端访问，修改后的视图如下：

	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>action5的视图</title>
	</head>
	<body>
	    <img alt="风景" src="<c:url value="/images/3.jpg"></c:url>">
	</body>
	</html>


目录结构变化后如下所示：

 

运行结果：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action28.png)

小结：主要是借助了标签<c:url value="/images/3.jpg"></c:url>，将路径转换成“绝对路径”；建议在引用外部资源如js、css、图片信息时都使用该标签解析路径。

2.2、返回值为String
----------

### 2.2.1、String作为视图名称 ###

默认如果action返回String，此时的String为视图名称，会去视图解析器的设定的目录下查找，查找的规则是：URL= prefix前缀+视图名称 +suffix后缀组成，示例代码如下：


    //返回值为String
    @RequestMapping("/action31")
    public String action31(Model model)    {
        model.addAttribute("message","action31");
        return "bar03/action31";
    }

Spring MVC的配置文件内容如下：

    <!-- 视图解析器 -->
    <bean
        class="org.springframework.web.servlet.view.InternalResourceViewResolver"
        id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/views/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>


实际url=/WEB-INF/views/bar03/action31.jsp

http://localhost:8080/bar03/action31

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action31.png)

### 2.2.2、String作为内容输出 ###
如果方法声明了注解@ResponseBody ，将内容或对象作为 HTTP 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。些时的String不再是路径而是内容，示例脚本如下：


    @RequestMapping("/action32")
    @ResponseBody
    public String action32()    {
        return "not <b>path</b>,but <b>content</b>";
    }

http://localhost:8080/bar03/action32

测试运行结果：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action32.png)

### 2.3、返回值为void ###

void在普通方法中是没有返回值的意思，但作为请求处理方法并非这样，存在如下两种情况：

#### 2.3.1、方法名默认作为视图名 ####

当方法没有返回值时，方法中并未指定视图的名称，则默认视图的名称为方法名，如下代码所示：

    @RequestMapping("/action33")
    public void action33()  {
    }

直接会去访问的路径是：url=/WEB-INF/views/bar03/action33.jsp，bar是当前控制器映射的路径，action33是方法名，上面的代码等同于：

    @RequestMapping("/action33")
    public String action33()   {
        return "bar03/action33";  //bar03是控制器的路径
    }

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action33.png)

可见URL= prefix前缀+控制器路径+方法名称 +suffix后缀组成。

#### 2.3.2、直接响应输出结果 ####

当方法的返回值为void，但输出流中存在输出内容时，则不会去查找视图，而是将输入流中的内容直接响应到客户端，响应的内容类型是纯文本，如下代码所示：


    @RequestMapping("/action34")
    public void action34(HttpServletResponse response) throws IOException    {
        response.getWriter().write("<h2>void method</h2>");
    }


运行结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action34.png)

 可以看到h2标签并未渲染成标题。

### 2.4、返回值为ModelAndView ###

 在旧的Spring MVC中ModelAndView使用频率非常高，它可以同时指定须返回的模型与视图对象或名称，示例代码如下：


    @RequestMapping("/action35")
    public ModelAndView action35()    {
        //1只指定视图
        //return new ModelAndView("/bar/index");
        
        //2分别指定视图与模型
        //Map<String, Object> model=new HashMap<String,Object>();
        //model.put("message", "ModelAndView action35");
        //return new ModelAndView("/bar/index",model);
        
        //3同时指定视图与模型
        //return new ModelAndView("/bar/index","message","action35 ModelAndView ");
        
        //4分开指定视图与模型
        ModelAndView modelAndView=new ModelAndView();
        //指定视图名称
        modelAndView.setViewName("/bar/index");
        //添加模型中的对象
        modelAndView.addObject("message", "<h2>Hello ModelAndView</h2>");
        return modelAndView;
    }


http://localhost:8080/bar03/action35

ModelAndView有个多构造方法重载，单独设置属性也很方便，运行结果如下：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action35.png)

### 2.5、返回值为Map ###

当返回结果为Map时，相当于只是返回了Model，并未指定具体的视图，返回视图的办法与void是一样的，即URL= prefix前缀+控制器路径+方法名称 +suffix后缀组成，示例代码如下：


    @RequestMapping("/action36")
    public Map<String, Object> action36()    {
        Map<String, Object> model=new HashMap<String,Object>();
        model.put("message", "Hello Map");
        model.put("other", "more item");
        return model;
    }

实际访问的路径是：/WEB-INF/views/bar03/action36.jsp，返回给客户端的map相当于模型，在视图中可以取出。

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action36.png)

### 2.6、返回值为任意类型 ###

 #### 2.6.1、返回值为基本数据类型 ####

当返回结果直接为int,double,boolean等基本数据类型时的状态，测试代码如下：

    @RequestMapping("/action37")
    public Integer action37()  {
        return 9527;
    }


测试运行的结果是：exception is java.lang.IllegalArgumentException: Unknown return value type异常。

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action37.png)

如果确实需要直接将基本数据类型返回，则可以使用注解@ReponseBody。

    @RequestMapping("/action38")
    @ResponseBody
    public int action38()    {
        return 9527;
    }


运行结果：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action38.png)

#### 2.6.2、当返值为自定义类型 ####

当返回值为自定义类型时Spring会把方法认为是视图名称，与返回值为void的类似办法处理URL，但页面中获得数据比较麻烦，示例代码如下：


    @RequestMapping("/action39")
    public Product action39()    {
        return new Product(1,"iPhone",1980.5);
    }


如果存在action39对应的视图，页面还是可以正常显示。

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action39a.png)

如果在action上添加@ResponseBody注解则返回的是Product本身，而非视图，Spring会选择一个合适的方式解析对象，默认是json。示例代码如下：


    @RequestMapping("/action39")
    @ResponseBody
    public Product action39()    {
        return new Product(1,"iPhone",1980.5);
    }

运行结果：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action39.png)

如果是接收json值，则需要使用注解@RequestBody指定在相应参数上。

### 2.7、返回值为Model类型 ###

 该接口Model定义在包org.springframework.ui下，model对象会用于页面渲染，视图路径使用方法名，与void类似。示例代码如下：


    @RequestMapping("/action40")
    public Model action40(Model model)    {
        model.addAttribute("message", "返回类型为org.springframework.ui.Model");
        return model;
    }


运行结果：

 ![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action40.png)

返回的类型还有许多如view等，通过view可指定一个具体的视图，如下载Excel、Pdf文档，其实它们也修改http的头部信息，手动同样可以实现，如下代码所示：


    @RequestMapping("/action41")
    @ResponseBody
    public String action41(HttpServletResponse response)    {
        response.setHeader("Content-type","application/octet-stream");         
        response.setHeader("Content-Disposition","attachment; filename=table.xls");
        return "<table><tr><td>Hello</td><td>Excel</td></tr></table>";
    }


运行结果：

![](https://raw.githubusercontent.com/CoderDream/spring-mvc-01/master/snapshot/spring_mvc_03_action41.png)

2.8、小结
----------

使用 String 作为请求处理方法的返回值类型是比较通用的方法，这样返回的逻辑视图名不会和请求 URL 绑定，具有很高的灵活性，而模型数据又可以通过Model控制。

使用void,map,Model时，返回对应的逻辑视图名称真实url为：prefix前缀+控制器路径+方法名 +suffix后缀组成。

使用String,ModelAndView返回视图名称可以不受请求的url绑定，ModelAndView可以设置返回的视图名称。

另外在非MVC中使用的许多办法在Action也可以使用。
.

























































































