<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<title><spring:message code="login.title"></spring:message> - <spring:message code="app.title" /></title>
<link href="${ctx }/static/images/favicon.ico" rel="shortcut icon" type="image/x-icon">
<link href="${ctx }/static/images/favicon.ico" rel="icon" type="image/x-icon">


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/demo.css">

<script type="text/javascript" src="${ctx }/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>


