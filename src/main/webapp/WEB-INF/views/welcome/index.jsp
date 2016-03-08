<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>

	<head>
		<jsp:include page="/include.jsp"/>
	</head>

	<body class="easyui-layout">

		<shiro:lacksPermission name="page:welcome">
			<div class="alert">
				无登录权限
			</div>
			<script type="text/javascript">
				location.href='${ctx}/login';
			</script>
		</shiro:lacksPermission>


		<div data-options="region:'north',border:false" style="height:40px;padding:10px" class="tabs-header"> <!-- background:#B3DFDA; -->
			<span style="float: left;font-size: 20px;" >权限管理系统</span>
 			
			<span style="float: right;">
					<shiro:guest>
						您未登录
					</shiro:guest>
					<shiro:user>
						<span class="l-btn-right l-btn-icon-right" style="margin-top: 0;margin-left: 12px;">
								<span class="l-btn-text" style="font-size: 14px;font-style: italic;">
									<shiro:principal property="name"/>，欢迎登录！
									<a id="logoutLink" href="javascript:void(0);" class="panel-title">[退出]</a>
								</span>
								<span class="l-btn-icon icon-man">&nbsp;</span>
						</span>
					</shiro:user>
			</span>
		</div>
		<div data-options="region:'west',split:true,title:'功能菜单'" style="width:150px;padding:10px;">
			功能菜单
			<jsp:include page="/menu.jsp"/>
		</div>

		<div data-options="region:'center',title:'功能区间'">
			<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'">
				
			</div>
		</div>

		<div data-options="region:'south',border:false" style="height:30px;padding:10px;" class="tabs-header"><!-- background:#A9FACD; -->
			<jsp:include page="/footer.jsp"></jsp:include>
		</div>

		<script type="text/javascript">
			var logoutUrl = '${ctx}/logout';
			function logout() {
				$.messager.confirm('操作提示','您确定退出系统吗?',function(r){
					if (r){
						location.href = logoutUrl;
					}
				});
			}
			$(function(){
				$('#logoutLink').click(function(){
					logout();
				});
			});
		</script>
	</body>

</html>
