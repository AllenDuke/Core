# SpringBoot
## 自动配置原理
SpringApplication.run(Main.class, args)，Main.class上有注解@SpringBootApplication，而@SpringBootApplication
包含有一个@EnableAutoConfiguration，Spring Boot启动的时候会通过@EnableAutoConfiguration注解找到
META-INF/spring.factories配置文件中的所有自动配置类，并对其进行加载，而这些自动配置类都是以AutoConfiguration结尾来命名的，
它实际上就是一个JavaConfig形式的Spring容器配置类，它能通过以Properties结尾命名的类中取得在全局配置文件中配置的属性如：server.port，
而XxxxProperties类是通过@ConfigurationProperties注解与全局配置文件中对应的属性进行绑定的。