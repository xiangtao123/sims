# SIMS（Student Infomation Management System）：学生信息管理系统


## 项目介绍
该系统是基于HTTP协议的Web应用，主要的业务是学生信息的管理；具有基于角色访问控制（RBAC）的功能授权鉴权能力。
	
	
## 技术概要
* 服务端代码使用Java语言编写；
* 前端界面使用[jQuery EasyUI](http://jeasyui.com/)；
* 项目依赖使用[Maven](http://maven.apache.org/)管理；
* 后端框架包括：Spring MVC、Hibernate、MyBatis、Hibernate JPA、Apache shiro等；
* 数据库管理系统选用MySQL；


## 系统结构
![系统架构图](https://github.com/xiangtao123/sims/raw/master/docs/design/ac.png)


## 实体关系ER图
![实体关系ER图](https://github.com/xiangtao123/sims/raw/master/docs/design/er.png)



## 系统基础功能
* - [x] 权限管理模块：基于Apache shiro权限框架实现的用户组件，功能包括：系统资源管理、角色管理、用户管理、用户认证（登录）等；
* - [x] 业务日志模块：提供使用注解anotation方式进行业务信息的采集，提供操作日志查询功能；


## 业务功能
* - [x] 院系管理：维护学校的院系结构；
* - [x] 专业管理：维护院系内专业结构；
* - [x] 课程管理：维护专业内开设的课程；
* - [x] 学生管理：维护学生信息；
* - [x] 选修管理：提供学生选课、成绩录入、成绩查询功能；
* - [x] 用户注册：设置角色是否开放注册；


## 数据初始化
* 系统数据初始化化：运行单元测试类com.jsrush.security.rbac.InitRootUserTest，需要将设置事务提交模式
	````Java
	
	@TransactionConfiguration(defaultRollback=false)
	
	````
，按测试方法的序号依  次执行；


## 项目目录介绍
* 项目文档脚本类文件目录：docs/
* 项目配置信息文件目录：src/main/resources/config/
* 项目上下文配置文件：src/main/resources/context/spring/
* Mybatis Mapper文件目录：src/main/resources/context/mybatis/
* 项目源码目录：src/main/java/
* 项目测试源码目录：src/test/java/
* web目录：src/main/webapp/
* 静态资源目录：src/main/webapp/static
* 视图文件目录：src/main/webapp/WEB-INF/views
* Spring MVC配置文件目录：src/main/webapp/WEB-INF/spring/


## 启动方式
* 首先创建数据库，修改数据源配置文件src/main/resources/config/jdbc.properties
	* 数据连接信息
	* 开启hibernate的hmb2ddl功能生成表或者执行建表脚本
* maven-jetty插件启动服务：执行命令
	````
	
	mvn jetty:run
	````
* jetty容器的配置信息在pom.xml中设置，内容如下：
	````xml
		
		<!-- jetty:config -->
		<project.jetty.port>8080</project.jetty.port>
		<project.jetty.scanIntervalSeconds>10</project.jetty.scanIntervalSeconds>
		<project.jetty.maxIdleTime>2000</project.jetty.maxIdleTime>
		
	````	
* 如果关闭热启动功能设置jetty参数project.jetty.scanIntervalSeconds为-1；


 
## 代码规范
* 提交的单元测试类，事务设置为回滚状态；
* 提交的代码要具有单元测试，保证自测通过；
* 提交的代码不能具有编译错误；
* 提交的代码不能影响项目的启动和其他功能的使用；
* 界面需要兼容IE8+、Google chrome、MAC safari等现代浏览器；
 
 
## 开发建议
* 基于面向接口开发方式，进行测试驱动开发； 
* 服务端代码使用单元测试进行功能验证；
* 服务启动后进行界面的编写和功能的调试；
 



 
