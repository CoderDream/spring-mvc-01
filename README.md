# spring-mvc-01

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


