
二、视图解析器
----------

多数MVC框架都为Web应用程序提供一种它自己处理视图的办法，Spring MVC 提供视图解析器，它使用ViewResolver进行视图解析，让用户在浏览器中渲染模型。ViewResolver是一种开箱即用的技术，能够解析JSP、Velocity模板、FreeMarker模板和XSLT等多种视图。
Spring处理视图最重要的两个接口是ViewResolver和View。ViewResolver接口在视图名称和真正的视图之间提供映射关系； 而View接口则处理请求将真正的视图呈现给用户。

### 2.1、ViewResolver视图解析器 ###

在Spring MVC控制器中，所有的请求处理方法（Action）必须解析出一个逻辑视图名称，无论是显式的（返回String，View或ModelAndView）还是隐式的（基于约定的，如视图名就是方法名）。Spring中的视图由视图解析器处理这个逻辑视图名称，Spring常用的视图解析器有如下几种：

AbstractCachingViewResolver：用来缓存视图的抽象视图解析器。通常情况下，视图在使用前就准备好了。继承该解析器就能够使用视图缓存。这是一个抽象类，这种视图解析器会把它曾经解析过的视图缓存起来，然后每次要解析视图的时候先从缓存里面找，如果找到了对应的视图就直接返回，如果没有就创建一个新的视图对象，然后把它放到一个用于缓存的map中，接着再把新建的视图返回。使用这种视图缓存的方式可以把解析视图的性能问题降到最低。

XmlViewResolver XML视图解析器。它实现了ViewResolver接口，接受相同DTD定义的XML配置文件作为Spring的XML bean工厂。它继承自AbstractCachingViewResolver抽象类，所以它也是支持视图缓存的。通俗来说就是通过xml指定逻辑名称与真实视图间的关系，示例如下：

    <bean class="org.springframework.web.servlet.view.XmlViewResolver">
       <property name="location" value="/WEB-INF/views.xml"/>
       <property name="order" value="2"/>
    </bean>

views.xml是逻辑名与真实视图名的映射文件，order是定义多个视图时的优先级，可以这样定义：

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
		 http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
		<bean id="index" class="org.springframework.web.servlet.view.InternalResourceView">
			<property name="url" value="/index.jsp" />
		</bean>
	</beans>

id就是逻辑名称了，在使用时可以在请求处理方法中这样指定：

    @RequestMapping("/index")
    public String index() {
       return "index";
    }

从配置可以看出最终还是使用InternalResourceView完成了视图解析。

ResourceBundleViewResolver：它使用了ResourceBundle定义下的bean，实现了ViewResolver接口，指定了绑定包的名称。通常情况下，配置文件会定义在classpath下的properties文件中，默认的文件名字是views.properties。

UrlBasedViewResolver：它简单实现了ViewResolver接口，它不用显式定义，直接影响逻辑视图到URL的映射。它让你不用任何映射就能通过逻辑视图名称访问资源。它是对ViewResolver的一种简单实现，而且继承了AbstractCachingViewResolver，主要就是提供的一种拼接URL的方式来解析视图，它可以让我们通过prefix属性指定一个指定的前缀，通过suffix属性指定一个指定的后缀，然后把返回的逻辑视图名称加上指定的前缀和后缀就是指定的视图URL了。如prefix=/WEB-INF/views/，suffix=.jsp，返回的视图名称viewName=bar/index，则UrlBasedViewResolver解析出来的视图URL就是/WEB-INF/views/bar/index.jsp。redirect:前缀表示重定向，forword:前缀表示转发。使用UrlBasedViewResolver的时候必须指定属性viewClass，表示解析成哪种视图，一般使用较多的就是InternalResourceView，利用它来展现jsp，但是当我们使用JSTL的时候我们必须使用org.springframework.web.servlet.view.JstlView。

InternalResourceViewResolver：内部视图解析器。它是URLBasedViewResolver的子类，所以URLBasedViewResolver支持的特性它都支持。在实际应用中InternalResourceViewResolver也是使用的最广泛的一个视图解析器。


    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/views/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>

在JSP视图技术中，Spring MVC经常会使用 UrlBasedViewResolver视图解析器，该解析器会将视图名称翻译成URL并通过RequestDispatcher处理请求后渲染视图。修改springmvc-servlet.xml配置文件，增加如下视图解析器：


<bean id="viewResolver" class="org.springframework.web.servlet.view.UrlBasedViewResolver">
    <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
    <property name="prefix" value="/WEB-INF/jsp/"/>
    <property name="suffix" value=".jsp"/>
</bean>

VelocityViewResolver：Velocity视图解析器，UrlBasedViewResolver的子类，VelocityViewResolver会把返回的逻辑视图解析为VelocityView。

FreeMarkerViewResolver：FreeMarker视图解析器，UrlBasedViewResolver的子类，FreeMarkerViewResolver会把Controller处理方法返回的逻辑视图解析为FreeMarkerView，使用FreeMarkerViewResolver的时候不需要我们指定其viewClass，因为FreeMarkerViewResolver中已经把viewClass为FreeMarkerView了。Spring本身支持了对Freemarker的集成。只需要配置一个针对Freemarker的视图解析器即可。

ContentNegotiatingViewResolver：内容协商视图解析器，这个视图解析器允许你用同样的内容数据来呈现不同的view，在RESTful服务中可用。






















2.2、链式视图解析器
Spring支持同时配置多个视图解析器，也就是链式视图解析器。这样，在某些情况下，就能够重写某些视图。如果我们配置了多个视图解析器，并想要给视图解析器排序的话，设定order属性就可以指定解析器执行的顺序。order的值越高，解析器执行的顺序越晚，当一个ViewResolver在进行视图解析后返回的View对象是null的话就表示该ViewResolver不能解析该视图，这个时候如果还存在其他order值比它大的ViewResolver就会调用剩余的ViewResolver中的order值最小的那个来解析该视图，依此类推。InternalResourceViewResolver这种能解析所有的视图，即永远能返回一个非空View对象的ViewResolver一定要把它放在ViewResolver链的最后面：


	<!-- jsp jstl -->
	<bean id="JSPViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
		<property name="viewNames" value="*jsp" />
		<property name="contentType" value="text/html; charset=utf-8"/>  
		<property name="prefix" value="/" />
		<property name="suffix" value="" />
		<property name="order" value="1"></property>
	</bean>

	<!-- FreeMarker -->
	<bean id="FMViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
		<property name="viewClass" value="org.springframework.web.servlet.view.freemarker.FreeMarkerView"/>
		<property name="viewNames" value="*html" />
		<property name="contentType" value="text/html; charset=utf-8"/>
		<property name="cache" value="true" />
		<property name="prefix" value="/" />
		<property name="suffix" value="" />
		<property name="order" value="0"></property>
	</bean>

viewClass指定了视图渲染类，viewNames指定视图名称匹配规则如名称以html开头或结束，contentType支持了页面头部信息匹配规则。

2.3、FreeMarker与多视图解析示例
2.3.1、新增两个视图解析器
修改Spring MVC配置文件springmvc-servlet.xml，在beans结点中增加两个视图解析器，一个为内部解析器用于解析jsp与JSTL，另一个为解析FreeMaker格式，修改后的文件如下所示：


	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
		xmlns:mvc="http://www.springframework.org/schema/mvc"
		xsi:schemaLocation="http://www.springframework.org/schema/beans 
			 http://www.springframework.org/schema/beans/spring-beans.xsd
			http://www.springframework.org/schema/context 
			http://www.springframework.org/schema/context/spring-context-4.3.xsd
			http://www.springframework.org/schema/mvc 
			http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd">
		<!-- 自动扫描包，实现支持注解的IOC -->
		<context:component-scan base-package="com.zhangguo.MavenTest" />

		<!-- Spring MVC不处理静态资源 -->
		<mvc:default-servlet-handler />

		<!-- 支持mvc注解驱动 -->
		<mvc:annotation-driven enable-matrix-variables="true" />

		<!-- 配置映射媒体类型的策略 -->
		<bean
			class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
			<property name="removeSemicolonContent" value="false" />
		</bean>

		<!-- 内部视图解析器，JSP与JSTL模板 -->
		<bean
			class="org.springframework.web.servlet.view.InternalResourceViewResolver"
			id="internalResourceViewResolver">
			<!--指定视图渲染类 -->
			<property name="viewClass"
				value="org.springframework.web.servlet.view.JstlView" />
			<!--自动添加到路径中的前缀 -->
			<property name="prefix" value="/WEB-INF/views/" />
			<!--自动添加到路径中的后缀 -->
			<property name="suffix" value=".jsp" />
			<!--设置所有视图的内容类型，如果视图本身设置内容类型视图类可以忽略 -->
			<property name="contentType" value="text/html;charset=UTF-8" />
			<!-- 优先级，越小越前 -->
			<property name="order" value="2" />
		</bean>

		<!-- FreeMarker视图解析器与属性配置 -->
		<bean id="viewResolver"
			class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
			<!--是否启用缓存 -->
			<property name="cache" value="true" />
			<!--自动添加到路径中的前缀 -->
			<property name="prefix" value="" />
			<!--自动添加到路径中的后缀 -->
			<property name="suffix" value=".html" />
			<!--指定视图渲染类 -->
			<property name="viewClass"
				value="org.springframework.web.servlet.view.freemarker.FreeMarkerView" />
			<!-- 设置是否暴露Spring的macro辅助类库，默认为true -->
			<property name="exposeSpringMacroHelpers" value="true" />
			<!-- 是否应将所有request属性添加到与模板合并之前的模型。默认为false。 -->
			<property name="exposeRequestAttributes" value="true" />
			<!-- 是否应将所有session属性添加到与模板合并之前的模型。默认为false。 -->
			<property name="exposeSessionAttributes" value="true" />
			<!-- 在页面中使用${rc.contextPath}就可获得contextPath -->
			<property name="requestContextAttribute" value="rc" />
			<!--设置所有视图的内容类型，如果视图本身设置内容类型视图类可以忽略 -->
			<property name="contentType" value="text/html;charset=UTF-8" />
			<!-- 优先级，越小越前 -->
			<property name="order" value="1" />
		</bean>

		<!-- 配置FreeMarker细节 -->
		<bean id="freemarkerConfig"
			class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
			<!-- 模板路径 -->
			<property name="templateLoaderPath" value="/WEB-INF/htmlviews" />
			<property name="freemarkerSettings">
				<props>
					<!-- 刷新模板的周期，单位为秒 -->
					<prop key="template_update_delay">5</prop>
					<!--模板的编码格式 -->
					<prop key="defaultEncoding">UTF-8</prop>
					<!--url编码格式 -->
					<prop key="url_escaping_charset">UTF-8</prop>
					<!--此属性可以防止模板解析空值时的错误 -->
					<prop key="classic_compatible">true</prop>
					<!--该模板所使用的国际化语言环境选项-->
					<prop key="locale">zh_CN</prop>
					<!--布尔值格式-->
					<prop key="boolean_format">true,false</prop>
					<!--日期时间格式-->
					<prop key="datetime_format">yyyy-MM-dd HH:mm:ss</prop>
					<!--时间格式-->
					<prop key="time_format">HH:mm:ss</prop>
					<!--数字格式-->
					<prop key="number_format">0.######</prop>
					<!--自动开启/关闭空白移除，默认为true-->
					<prop key="whitespace_stripping">true</prop>
				</props>
			</property>
		</bean>
	</beans>

这里要注意的是的order越小解析优化级越高，在视图解析过程中，如果order为1的视图解析器不能正确解析视图的话，会将结果交给order为2的视图解析器，这里为2的视图解析器是InternalResourceViewResolver，它总是会生成一个视图的，所以一部内部视图在放在视图解析链的末尾，成一什么都没有找到他还会生成一个404的view返回。

2.3.2、修改pom.xml，添加依赖
为了使用FreeMarker，需要引用spring-context-support与FreeMarker的jar包，修改后的pom.xml配置文件如下：


	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		<modelVersion>4.0.0</modelVersion>
		<groupId>com.zhangguo</groupId>
		<artifactId>SpringMVC04</artifactId>
		<version>0.0.1</version>
		<packaging>war</packaging>
		<properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			<spring.version>4.3.0.RELEASE</spring.version>
		</properties>

		<dependencies>
			<!--Spring框架核心库 -->
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-context</artifactId>
				<version>${spring.version}</version>
			</dependency>
			<!-- Spring MVC -->
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-webmvc</artifactId>
				<version>${spring.version}</version>
			</dependency>
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-context-support</artifactId>
				<version>${spring.version}</version>
			</dependency>
			<!-- JSTL -->
			<dependency>
				<groupId>javax.servlet</groupId>
				<artifactId>jstl</artifactId>
				<version>1.2</version>
			</dependency>
			<!-- Servlet核心包 -->
			<dependency>
				<groupId>javax.servlet</groupId>
				<artifactId>javax.servlet-api</artifactId>
				<version>3.0.1</version>
				<scope>provided</scope>
			</dependency>
			<!--JSP应用程序接口 -->
			<dependency>
				<groupId>javax.servlet.jsp</groupId>
				<artifactId>jsp-api</artifactId>
				<version>2.1</version>
				<scope>provided</scope>
			</dependency>
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
			<!-- FreeMarker -->
			<dependency>
				<groupId>org.freemarker</groupId>
				<artifactId>freemarker</artifactId>
				<version>2.3.23</version>
			</dependency>
		</dependencies>

	</project>

依赖成功后的包：



2.3.3、新增加Controller与两个Action
新增一个名为FooController的控制器，增加两个请求处理方法jstl与ftl，jstl让第2个视图解析器解析，ftl让第1个解析器解析，第1个视图解析器也是默认的视图解析器，示例代码如下：


	package com.zhangguo.MavenTest.Controllers;

	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.RequestMapping;

	@Controller
	@RequestMapping("/foo")
	public class FooController {
		@RequestMapping("/jstl")
		public String jstl(Model model) {
			model.addAttribute("message", "Hello JSTL View!");
			return "foo/jstl";
		}
		
		@RequestMapping("/ftl")
		public String ftl(Model model) {
			model.addAttribute("users", new String[]{"tom","mark","jack"});
			model.addAttribute("message", "Hello FreeMarker View!");
			return "foo/ftl";
		}
	}

2.3.3、新增目录与视图
在WEB-INF/views/foo目录下新增jsp页面jstl.jsp页面，在WEB-INF/htmlviews/foo目录下新增ftl.html页面，目录结构如下：



jstl.jsp页面内容如下：


	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>foo/jstl for JSTL</title>
	</head>
	<body>
	${message}
	</body>
	</html>

ftl.html页面内容如下： 


	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>foo/ftl for Freemarker</title>
	</head>
	<body>
		<p>${message}</p>
		<ul>
		   <#list users as user><li>${user}</li></#list>
		</ul>
	</body>
	</html>

2.3.4、运行结果
当请求http://localhost:8087/MavenTest/foo/ftl时运行结果如下：



当请求http://localhost:8087/MavenTest/foo/jstl时运行结果如下：



2.3.5、小结
当访问/foo/ftl时会找到action ftl方法，该方法返回foo/ftl字符串，视图解析器中order为1的解析器去foo目录下找名称为ftl的视图，视图存在，将视图与模型渲染后输出。当访问/foo/jstl时会找到action jstl访问，该方法返回foo/jstl字符串，视图解析器中order为1的解析器去foo目录下找名称为jstl的视图，未能找到，解析失败，转到order为2的视图解析器解析，在目录foo下找到jstl的文件成功，将视图与模板渲染后输出。

如果想视图解析器更加直接的选择可以使用属性viewNames，如viewNames="html*"，则会只解析视图名以html开头的视图。

在ftl.html页面中，使用了FreeMarker模板语言，具体的应用细节请看本博客的另一篇文章。

三、综合示例
为了巩固前面学习的内容，通过一个相对综合的示例串联前面学习过的一些知识点，主要实现产品管理管理功能，包含产品的添加，删除，修改，查询，多删除功能，运行结果如下：

3.1、新建一个基于Maven的Web项目
这一步在前面的文章中也多次提到，如果熟悉的话可以跳过。如果初学可以查看前面发布过一些文章中的内容，内容更加详细。如：Spring整合MyBatis（Maven+MySQL）一。

3.1.1、创建项目
新建一个名称为SpringMVC04的Maven简单项目，填写好项目名称，组名，打包选择war。

3.1.2、修改层面信息
在项目上右键选择属性，再选择“Project Facets”，先设置java运行环境为1.7，先去掉"Dynamic Web Module"前的勾，然后保存关闭；再打开勾选上"Dynamic Web Module"，版本选择“3.0”；这里在左下解会出现一个超链接，创建“Web Content”，完成关闭。



3.1.3、修改项目的部署内容
项目上右键属性，选择“Deplyment Assembly”,删除不需要发布的内容如：带“test”的两个目录，WebContent目录，再添加一个main下的webapp目录。

 

修改后的结果如下所示：



3.1.4、修改项目内容。
将WebContent下的内容复制到/src/main/webapp下，再删除WebContent目录。

修改后的目录结构如下：



3.1.5、添加“服务器运行时（Server Runtime）”，当然如果选择直接依赖Servlet与jsp的jar包，则这一步可以跳过，添加后的结果如下：



3.2、添加依赖
项目主要依赖的jar包有Spring核心包、Spring MVC、JSTL、JSP、Servlet核心包、Jackson等，具体的pom.xml文件如下：


	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		<modelVersion>4.0.0</modelVersion>
		<groupId>com.zhangguo</groupId>
		<artifactId>SpringMVC04</artifactId>
		<version>0.0.1</version>
		<packaging>war</packaging>
		<properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			<spring.version>4.3.0.RELEASE</spring.version>
		</properties>

		<dependencies>
			<!--Spring框架核心库 -->
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-context</artifactId>
				<version>${spring.version}</version>
			</dependency>
			<!-- Spring MVC -->
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-webmvc</artifactId>
				<version>${spring.version}</version>
			</dependency>
			<!-- JSTL -->
			<dependency>
				<groupId>javax.servlet</groupId>
				<artifactId>jstl</artifactId>
				<version>1.2</version>
			</dependency>
			<!-- Servlet核心包 -->
			<dependency>
				<groupId>javax.servlet</groupId>
				<artifactId>javax.servlet-api</artifactId>
				<version>3.0.1</version>
				<scope>provided</scope>
			</dependency>
			<!--JSP应用程序接口 -->
			<dependency>
				<groupId>javax.servlet.jsp</groupId>
				<artifactId>jsp-api</artifactId>
				<version>2.1</version>
				<scope>provided</scope>
			</dependency>
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
		</dependencies>

	</project>

依赖成功后的结果如下：



3.3、配置Spring MVC运行环境
具体的内容请看该系列文章中的第一篇，Spring MVC运行环境引用的包在上一步中已完成，修改web.xml注册中心控制器，修改后的web.xml如下所示：


	<?xml version="1.0" encoding="UTF-8"?>
	<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee"
		xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
		id="WebApp_ID" version="3.0">

		<servlet>
			<servlet-name>springmvc</servlet-name>
			<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
			<init-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>classpath*:springmvc-servlet.xml</param-value>
			</init-param>
			<load-on-startup>1</load-on-startup>
		</servlet>
		
		<servlet-mapping>
			<servlet-name>springmvc</servlet-name>
			<url-pattern>/</url-pattern>
		</servlet-mapping>
	</web-app>

在源代码根目录下添加spring mvc配置文件springmvc-servlet.xml，详细内容如下：


	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:mvc="http://www.springframework.org/schema/mvc"
		xsi:schemaLocation="http://www.springframework.org/schema/beans 
			 http://www.springframework.org/schema/beans/spring-beans.xsd
			http://www.springframework.org/schema/context 
			http://www.springframework.org/schema/context/spring-context-4.3.xsd
			http://www.springframework.org/schema/mvc 
			http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd">

		<!-- 自动扫描包，实现支持注解的IOC -->
		<context:component-scan base-package="com.zhangguo.springmvc04" />
		
		<!-- Spring MVC不处理静态资源 -->
		<mvc:default-servlet-handler />

		<!-- 支持mvc注解驱动 -->
		<mvc:annotation-driven enable-matrix-variables="true" />

		<!-- 配置映射媒体类型的策略 -->
		<bean
			class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
			<property name="removeSemicolonContent" value="false" />
		</bean>
		
		<!-- 视图解析器 -->
		<bean
			class="org.springframework.web.servlet.view.InternalResourceViewResolver"
			id="internalResourceViewResolver">
			<!-- 前缀 -->
			<property name="prefix" value="/WEB-INF/views/" />
			<!-- 后缀 -->
			<property name="suffix" value=".jsp" />
		</bean>
	</beans>

3.4、新建POJO实体（entity）
在包com.zhangguo.springmvc04.entities新增加产品类型类ProductType，代码如下所示：

 View Code
添加产品POJO类Product，具体代码如下：

 View Code
3.5、新建业务层（Service）
在包com.zhangguo.springmvc04.services下创建产品类型服务接口ProductTypeService，代码如下：


	package com.zhangguo.springmvc04.services;

	import java.util.List;

	import com.zhangguo.springmvc04.entities.ProductType;

	/**
	 * 产品类型服务
	 *
	 */
	public interface ProductTypeService {
		/**
		 * 根据产品类型编号获得产品类型对象
		 */
		public ProductType getProductTypeById(int id);
		
		/**
		 * 获得所有的产品类型
		 */
		public List<ProductType> getAllProductTypes();
	}

实现类ProductTypeServiceImpl，代码如下：


	package com.zhangguo.springmvc04.services;

	import java.util.ArrayList;
	import java.util.List;

	import org.springframework.stereotype.Service;
	import com.zhangguo.springmvc04.entities.ProductType;

	@Service
	public class ProductTypeServiceImpl implements ProductTypeService {

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

创建产品服务接口ProductService，代码如下：


	package com.zhangguo.springmvc04.services;

	import java.util.List;

	import com.zhangguo.springmvc04.entities.Product;

	public interface ProductService {

		/*
		 * 获得所有的产品
		 */
		List<Product> getAllProducts();

		/*
		 * 获得产品通过编号
		 */
		Product getProductById(int id);

		/*
		 * 获得产品名称通过名称
		 */
		List<Product> getProductsByName(String productName);

		/**
		 * 新增产品对象
		 */
		void addProduct(Product enttiy) throws Exception;
		
		/**
		 * 更新产品对象
		 */
		public void updateProduct(Product entity) throws Exception;

		/**
		 * 删除产品对象
		 */
		void deleteProduct(int id);

		/**
		 * 多删除产品对象
		 */
		void deletesProduct(int[] ids);

	}

实现类ProductServiceImpl，代码如下：


	package com.zhangguo.springmvc04.services;

	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.stereotype.Service;
	import com.zhangguo.springmvc04.entities.Product;

	@Service
	public class ProductServiceImpl implements ProductService {
		private static List<Product> products;

		static {
			ProductTypeService productTypeService = new ProductTypeServiceImpl();
			products = new ArrayList<Product>();
			products.add(new Product(198, "Huwei P8", 4985.6, productTypeService.getProductTypeById(11)));
			products.add(new Product(298, "李宁运动鞋", 498.56, productTypeService.getProductTypeById(21)));
			products.add(new Product(398, "Spring MVC权威指南", 49.856, productTypeService.getProductTypeById(31)));
			products.add(new Product(498, "山东国光苹果", 4.9856, productTypeService.getProductTypeById(51)));
			products.add(new Product(598, "8开门超级大冰箱", 49856.1, productTypeService.getProductTypeById(41)));
		}

		/*
		 * 获得所有的产品
		 */
		@Override
		public List<Product> getAllProducts() {
			return products;
		}

		/*
		 * 获得产品通过编号
		 */
		@Override
		public Product getProductById(int id) {
			for (Product product : products) {
				if (product.getId() == id) {
					return product;
				}
			}
			return null;
		}

		/*
		 * 获得产品名称通过名称
		 */
		@Override
		public List<Product> getProductsByName(String productName) {
			if(productName==null||productName.equals("")){
				return getAllProducts();
			}
			List<Product> result = new ArrayList<Product>();
			for (Product product : products) {
				if (product.getName().contains(productName)) {
					result.add(product);
				}
			}
			return result;
		}

		/**
		 * 新增
		 * @throws Exception 
		 */
		@Override
		public void addProduct(Product entity) throws Exception {
			if(entity.getName()==null||entity.getName().equals("")){
				throw new Exception("产品名称必须填写");
			}
			if (products.size() > 0) {
				entity.setId(products.get(products.size() - 1).getId() + 1);
			} else {
				entity.setId(1);
			}
			products.add(entity);
		}
		
		/*
		 * 更新
		 */
		public void updateProduct(Product entity) throws Exception 
		{
			if(entity.getPrice()<0){
				throw new Exception("价格必须大于0");
			}
			Product source=getProductById(entity.getId());
			source.setName(entity.getName());
			source.setPrice(entity.getPrice());
			source.setProductType(entity.getProductType());
		}
		
		/**
		 * 删除
		 */
		@Override
		public void deleteProduct(int id){
			products.remove(getProductById(id));
		}
		
		/*
		 * 多删除
		 */
		@Override
		public void deletesProduct(int[] ids){
			for (int id : ids) {
				deleteProduct(id);
			}
		}
	}

3.6、实现展示、查询、删除与多删除功能
在com.zhangguo.springmvc04.controllers包下定义一个名为ProductController的控制器，代码如下所示：


	package com.zhangguo.springmvc04.controllers;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;

	import com.zhangguo.springmvc04.services.ProductService;

	@Controller
	@RequestMapping
	public class ProductController {
		@Autowired
		ProductService productService;
		
		//展示与搜索action
		@RequestMapping
		public String index(Model model, String searchKey) {
			model.addAttribute("products", productService.getProductsByName(searchKey));
			model.addAttribute("searchKey", searchKey);
			return "product/index";
		}
		
		//删除，id为路径变量
		@RequestMapping("/delete/{id}")
		public String delete(@PathVariable int id){
			productService.deleteProduct(id);
			return "redirect:/";
		}
		
		//多删除，ids的值为多个id参数组成
		@RequestMapping("/deletes")
		public String deletes(@RequestParam("id")int[] ids){
			productService.deletesProduct(ids);
			return "redirect:/";
		}
	}

控制器上的路径映射value并未指定值是让该控制器为默认控制器，index请求处理方法在路径映射注解@RequestMapping中也并未指定value值是让该action为默认action，所有当我们访问系统时这个index就成了欢迎页。

定义所有页面风格用的main.css样式，脚本如下：

 View Code
在views目录下新建目录product，在product目录下新建一个视图index.jsp，页面脚本如下所示：


	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<c:url value="/styles/main.css"/>"  type="text/css" rel="stylesheet" />
	<title>产品管理</title>
	</head>
	<body>
		<div class="main">
			<h2 class="title"><span>产品管理</span></h2>
			<form method="get">
			   名称：<input type="text" name="searchKey" value="${searchKey}"/>
			   <input type="submit"  value="搜索" class="btn out"/>
			</form>
			<form action="deletes" method="post">
			<table border="1" width="100%" class="tab">
				<tr>
					<th><input type="checkbox" id="chbAll"></th>
					<th>编号</th>
					<th>产品名</th>
					<th>价格</th>
					<th>类型</th>
					<th>操作</th>
				</tr>
				<c:forEach var="product" items="${products}">
					<tr>
						<th><input type="checkbox" name="id" value="${product.id}"></th>
						<td>${product.id}</td>
						<td>${product.name}</td>
						<td>${product.price}</td>
						<td>${product.productType.name}</td>
						<td>
						<a href="delete/${product.id}" class="abtn">删除</a>
						<a href="edit/${product.id}" class="abtn">编辑</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<p style="color: red">${message}</p>
			<p>
				<a href="add" class="abtn out">添加</a>
				<input type="submit"  value="删除选择项" class="btn out"/>
			</p>
			<script type="text/javascript" src="<c:url value="/scripts/jQuery1.11.3/jquery-1.11.3.min.js"/>" ></script>
		</form>
		</div>
	</body>
	</html>

 运行结果如下：



搜索：



删除与批量删除：



3.7、新增产品功能
 在ProductController控制器中添加两个Action，一个用于渲染添加页面，另一个用于响应保存功能，代码如下：


	// 新增，渲染出新增界面
    @RequestMapping("/add")
    public String add(Model model) {
        // 与form绑定的模型
        model.addAttribute("product", new Product());
        // 用于生成下拉列表
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        return "product/add";
    }

    // 新增保存，如果新增成功转回列表页，如果失败回新增页，保持页面数据
    @RequestMapping("/addSave")
    public String addSave(Model model,Product product) {
        try {
            //根据类型的编号获得类型对象
            product.setProductType(productTypeService.getProductTypeById(product.getProductType().getId()));
            productService.addProduct(product);
            return "redirect:/";
        } catch (Exception exp) {
            // 与form绑定的模型
            model.addAttribute("product", product);
            // 用于生成下拉列表
            model.addAttribute("productTypes", productTypeService.getAllProductTypes());
            //错误消息
            model.addAttribute("message", exp.getMessage());
            return "product/add";
        }
    }

在views/product目录下新增视图add.jsp页面，页面脚本如下：


	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<link href="styles/main.css" type="text/css" rel="stylesheet" />
	<title>新增产品</title>
	</head>
	<body>
		<div class="main">
			<h2 class="title"><span>新增产品</span></h2>
			<form:form action="addSave" modelAttribute="product">
			<fieldset>
				<legend>产品</legend>
				<p>
					<label for="name">产品名称：</label>
					<form:input path="name"/>
				</p>
				<p>
					<label for="title">产品类型：</label>
					<form:select path="productType.id" items="${productTypes}"  itemLabel="name" itemValue="id">
					</form:select>
				</p>
				<p>
					<label for="price">产品价格：</label>
					<form:input path="price"/>
				</p>
				<p>
				  <input type="submit" value="保存" class="btn out">
				</p>
			</fieldset>
			</form:form>
			<p style="color: red">${message}</p>
			<p>
				<a href="<c:url value="/" />"  class="abtn out">返回列表</a>
			</p>
		</div>
	</body>
	</html>

运行结果：



3.8、编辑产品
在ProductController控制器中添加两个Action，一个用于渲染编辑页面，根据要编辑的产品编号获得产品对象，另一个用于响应保存功能，代码如下：


    // 编辑，渲染出编辑界面，路径变量id是用户要编辑的产品编号
    @RequestMapping("/edit/{id}")
    public String edit(Model model,@PathVariable int id) {
        // 与form绑定的模型
        model.addAttribute("product", productService.getProductById(id));
        // 用于生成下拉列表
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        return "product/edit";
    }
    
    // 编辑后保存，如果更新成功转回列表页，如果失败回编辑页，保持页面数据
    @RequestMapping("/editSave")
    public String editSave(Model model,Product product) {
        try {
            //根据类型的编号获得类型对象
            product.setProductType(productTypeService.getProductTypeById(product.getProductType().getId()));
            productService.updateProduct(product);
            return "redirect:/";
        } catch (Exception exp) {
            // 与form绑定的模型
            model.addAttribute("product", product);
            // 用于生成下拉列表
            model.addAttribute("productTypes", productTypeService.getAllProductTypes());
            //错误消息
            model.addAttribute("message", exp.getMessage());
            return "product/edit";
        }
    }

在views/product目录下新增视图edit.jsp页面，页面脚本如下：


	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<!DOCTYPE html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<link href="<c:url value="/styles/main.css" />" type="text/css" rel="stylesheet" />
	<title>编辑产品</title>
	</head>
	<body>
		<div class="main">
			<h2 class="title"><span>编辑产品</span></h2>
			<form:form action="${pageContext.request.contextPath}/editSave" modelAttribute="product">
			<fieldset>
				<legend>产品</legend>
				<p>
					<label for="name">产品名称：</label>
					<form:input path="name"/>
				</p>
				<p>
					<label for="title">产品类型：</label>
					<form:select path="productType.id" items="${productTypes}"  itemLabel="name" itemValue="id">
					</form:select>
				</p>
				<p>
					<label for="price">产品价格：</label>
					<form:input path="price"/>
				</p>
				<p>
				  <form:hidden path="id"/>
				  <input type="submit" value="保存" class="btn out">
				</p>
			</fieldset>
			</form:form>
			<p style="color: red">${message}</p>
			<p>
				<a href="<c:url value="/" />"  class="abtn out">返回列表</a>
			</p>
		</div>
	</body>
	</html>

这里要注意路径问题使用c:url不能嵌套在form标签中，所以使用了${ctx}，运行结果如下：



完成整个功能后的控制器代码如下：



	package com.zhangguo.springmvc04.controllers;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import com.zhangguo.springmvc04.entities.Product;
	import com.zhangguo.springmvc04.services.ProductService;
	import com.zhangguo.springmvc04.services.ProductTypeService;

	@Controller
	@RequestMapping
	public class ProductController {
		@Autowired
		ProductService productService;
		@Autowired
		ProductTypeService productTypeService;

		// 展示与搜索action
		@RequestMapping
		public String index(Model model, String searchKey) {
			model.addAttribute("products", productService.getProductsByName(searchKey));
			model.addAttribute("searchKey", searchKey);
			return "product/index";
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
			model.addAttribute("productTypes", productTypeService.getAllProductTypes());
			return "product/add";
		}

		// 新增保存，如果新增成功转回列表页，如果失败回新增页，保持页面数据
		@RequestMapping("/addSave")
		public String addSave(Model model,Product product) {
			try {
				//根据类型的编号获得类型对象
				product.setProductType(productTypeService.getProductTypeById(product.getProductType().getId()));
				productService.addProduct(product);
				return "redirect:/";
			} catch (Exception exp) {
				// 与form绑定的模型
				model.addAttribute("product", product);
				// 用于生成下拉列表
				model.addAttribute("productTypes", productTypeService.getAllProductTypes());
				//错误消息
				model.addAttribute("message", exp.getMessage());
				return "product/add";
			}
		}
		
		// 编辑，渲染出编辑界面，路径变量id是用户要编辑的产品编号
		@RequestMapping("/edit/{id}")
		public String edit(Model model,@PathVariable int id) {
			// 与form绑定的模型
			model.addAttribute("product", productService.getProductById(id));
			// 用于生成下拉列表
			model.addAttribute("productTypes", productTypeService.getAllProductTypes());
			return "product/edit";
		}
		
		// 编辑后保存，如果更新成功转回列表页，如果失败回编辑页，保持页面数据
		@RequestMapping("/editSave")
		public String editSave(Model model,Product product) {
			try {
				//根据类型的编号获得类型对象
				product.setProductType(productTypeService.getProductTypeById(product.getProductType().getId()));
				productService.updateProduct(product);
				return "redirect:/";
			} catch (Exception exp) {
				// 与form绑定的模型
				model.addAttribute("product", product);
				// 用于生成下拉列表
				model.addAttribute("productTypes", productTypeService.getAllProductTypes());
				//错误消息
				model.addAttribute("message", exp.getMessage());
				return "product/edit";
			}
		}
		
	}

四、示例下载
点击下载