<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>工作台 </title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-easyui-1.4/themes/icon.css">
</head>
<body>

	<h2>Auto Height for Layout</h2>
	<p>This example shows how to auto adjust layout height after dynamically adding items.</p>
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addItem()">Add Item</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeItem()">Remove Item</a>

		<input class="easyui-textbox" type="text" id="task" />
	</div>

	<div id="cc" style="width: auto;height: 400px;">
		<div data-options="region:'north'" style="height:50px"></div>
		<div data-options="region:'south'" style="height:50px;"></div>
		<div data-options="region:'west'" style="width:150px;"></div>
		<div data-options="region:'center'" style="padding:20px">
			<p>示例说明：</p>
			<p>
			1.权限控制示例	[
						<shiro:hasPermission name="login">允许我执行(登录login)操作</shiro:hasPermission>
						<shiro:lacksPermission name="add">不允许我(新增Add)操作</shiro:lacksPermission>

							];
			</p>

			2.通用跳转界面：
			<div>
				<iframe src="${ctx }/dispatcher/security/target" width="100%;" height="300px;"></iframe>
			</div>

		</div>
	</div>






	<script type="text/javascript" src="${ctx }/static/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/jquery-easyui-1.4/jquery.easyui.min.js"></script>

	<script type="text/javascript">
		$(function(){
			$('#cc').layout();
			setHeight();
		});

		function addItem(){
			$('#cc').layout('panel','center').append('<p>More Panel Content is :'+$('#task').val()+'</p>');
			setHeight();
		}

		function removeItem(){
			$('#cc').layout('panel','center').find('p:last').remove();
			setHeight();
		}

		function setHeight(){
			var c = $('#cc');
			var p = c.layout('panel','center');	// get the center panel
			var oldHeight = p.panel('panel').outerHeight();
			p.panel('resize', {height:'auto'});
			var newHeight = p.panel('panel').outerHeight();
			c.layout('resize',{
				height: (c.height() + newHeight - oldHeight)
			});
			window.parent.dyniframesize('ifm_workspace');
		}
	</script>

</body>
</html>
