<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="panel-title">
	<span><spring:message code="app.title"></spring:message> <spring:message code="app.copyright"></spring:message></span>
</div>
