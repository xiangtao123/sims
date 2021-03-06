<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<title><spring:message code="workspace.title"></spring:message> - <spring:message code="app.title" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx }/static/images/favicon.ico" rel="shortcut icon" type="image/x-icon">
<link href="${ctx }/static/images/favicon.ico" rel="icon" type="image/x-icon">

		
<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/demo.css">


<link rel="stylesheet" type="text/css" href="${ctx }/static/style.css">

<!--[if lt IE 8 ]><srcipt src="${ctx }/static/jquery/json2.min.js"></script><![endif]-->
<script type="text/javascript" src="${ctx }/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/jquery.easyui.patch.js"></script>
<script type="text/javascript" src="${ctx }/static/common/easyui-biz.js"></script>
<script type="text/javascript" src="${ctx }/static/common/jquery.window.extend.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/plugins/extend/jquery.validate.extend.js"></script>

<script type="text/javascript">
<!--
	eui.basePath = '${ctx }';
	
//-->
</script>

