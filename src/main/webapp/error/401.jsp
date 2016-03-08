<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("401.jsp");
    if (ex != null) {
        logger.error(ex.getMessage(), ex);
    }
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title>401 - 权限认证失败</title>
</head>

<body>
	<div style="text-align: center;">
		<h4>401 - 您访问的资源受保护.<a style="color:red;" href="${ctx }">返回主页</a></h4>
	</div>
</body>
</html>
