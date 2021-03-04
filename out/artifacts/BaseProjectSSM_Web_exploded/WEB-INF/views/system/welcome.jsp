<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/3/1
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>欢迎页面</title>
</head>
<body>
<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
        <p style="font-size: 50px; line-height: 60px; height: 60px;">${admin.username}</p>
    <p style="font-size: 25px; line-height: 30px; height: 30px;">欢迎使用ssm框架管理系统</p>
    <p>开发人员：gustavo</p>
    <p>开发周期：2021/03/01 --- 2021/03/07（共计6天）</p>

    <hr />
    <h2>系统环境</h2>
    <p>系统环境：Windows</p>
    <p>开发工具：Interllij IDEA</p>
    <p>Java版本：JDK 1.8</p>
    <p>服务器：tomcat 7.0.99</p>
    <p>数据库：MySQL 5.7</p>
    <p>系统采用技术： spring+springMVC+mybaits+EasyUI+jQuery+Ajax+面向接口编程</p>
</div>
</body>
</html>