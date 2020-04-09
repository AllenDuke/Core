# 在浏览器中输入网址到打开网页的过程
以chrome,tomcat,springmvc为例:
- 域名解析：浏览器会将当前输入的URL发送至DNS服务器并获得域名对应的WEB服务器的ip地址，过程如下：
1. chrome搜索自身的DNS缓存（有没有以及有没有过期），查看chrome自身的缓存：chrome://net-internals/#dns
2. chrome浏览器没有找到自身缓存或者缓存已经失效，就会搜索操作系统自身的DNS缓存（有没有以及有没有过期）
3. 操作系统自身的DNS缓存没有找到或者已经失效，chrome会读取本地的HOST文件
4. 本地HOST文件的缓存没有找到或者已经失效，chrome会发起一个DNS的一个系统调用，即chrome以系统的名义向本地宽带运营商的 dns服务器
发起一个域名解析的请求，过程如下：
   1. 宽带运营商服务器查看本身缓存
   2. 运营商服务器代替浏览器发起一个迭代DNS解析的请求（询问根服务器，询问顶级域名服务器，询问权限域名服务器），
   运营商服务器把结果返回操作系统内核同时缓存起来。
   3. 操作系统内核把结果返回浏览器
   4. 最终，浏览器拿到了网址对应的IP地址
- 浏览器获得域名对应的IP地址后，发起TCP“三次握手”，建立TCP/IP连接
- tcp/ip 连接建立起来后，浏览器就可以向服务器发送 HTTP 请求(如果一次报文段过长，会产生多次HTTP请求），比如说使用 get 方式请求
一个域名。

tomcat方面:
- WEB容器在启动时，它会为每个WEB应用程序都创建一个对应的ServletContext对象（每个web应用程序唯一），它代表当前web应用web容器提供
其一个全局的上下文环境，其为后面的spring IoC
容器提供宿主环境；
- 读取web.xml
服务器软件或容器如（tomcat）加载项目中的web.xml文件，通过其中的各种配置来启动项目，只有其中配置的各项均无误时，项目才能正确启动。
web.xml	有多项标签，在其加载的过程中顺序依次为：context-param >> listener >> fileter >> servlet​。（同类多个节点以出现顺序
依次加载）因为用的是springmvc，所以只有一个映射路径为"/"的DispatchServlet。
1. Web客户向Servlet容器（Tomcat）发出Http请求
2. Servlet容器分析客户的请求信息
3. Servlet容器创建一个HttpRequest对象，将客户请求的信息封装到这个对象中
4. Servlet容器创建一个HttpResponse对象
5. Servlet容器调用HttpServlet对象的service方法，把HttpRequest对象与HttpResponse对象作为参数传给 HttpServlet对象
6. HttpServlet调用HttpRequest对象的有关方法，获取Http请求信息
7. HttpServlet调用HttpResponse对象的有关方法，生成响应数据
8. Servlet容器把HttpServlet的响应结果传给Web客户
- 服务器收到这个请求后，根据路径参数，再经过后端的一些处理，把结果返回给浏览器，这样我们就得到了该域名对应的整个页面的代码或者其他的数据。
- 浏览器拿到代码后，经过解析、渲染等，我们就看到了这个页面。
