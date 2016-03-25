<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/include_biz.jsp"></jsp:include>
	<script type="text/javascript">
		var App = function(){};
		App.loginUrl = '${ctx }';
		
		App.findRoleByEcId = function (newValue, oldValue) {
			 var url = '${ctx }/register/findRoleListByEcId?ecId=' + newValue;    
	         $('#roleSelector').combobox('reload', url);
		};
		
		App.addUserAjaxOptions = {
			url 	: 	'/register/addUser',
			success	:	function(data, st, xhr) {
				if (data == 1) {
					eui.alert('操作成功：注册成功，请进入登录界面登录系统');
					location.href = App.loginUrl;
				} else if (data == -1) {
					eui.alert('操作失败：您输入的参数不合法','warning');
				}  else if (data == -3) {
					eui.alert('操作失败：您输入的登录名已被使用，请重新输入','warning');
				} else {
					eui.alert('操作失败，程序运行异常','error');
				}
			}
		};
		App.submitForm = function() {
			var isValid = $('#addUserForm').form('validate');
			if (!isValid) {
				return;
			}
			var formData = eui.findFormDataJsonObject('#addUserForm :input');
			App.addUserAjaxOptions.postData = formData;
			eui.commAjax(App.addUserAjaxOptions);
		}
	</script>
	
	<style type="text/css">
		*{font-size: 14px;}
		.easyui-combobox{width: 30%;}
		.easyui-textbox{width: 20%;}
	 
		body{ font-family:"微软雅黑",Verdana, Geneva, sans-serif; font-size:14px; background:#fff; padding: 0px;}
				
		.help{ font-size:14px; font-weight:100; color:#787878; text-decoration:none;}
		.t { height:76px; line-height:90px; background:#f5f5f5; border-bottom:1px solid #e5ecf0; overflow:hidden;}
		
		.boxc{ box-shadow: 10px 10px 5px #e5ecf0; background:#f5f5f5; width: 80%; margin: 2% auto 0px auto;}
		
	</style>
	
</head>
<body>
	
	<div class="t">
		<span style="margin: 10px 0px 0px 30px;;font-size:30px;font-weight: 100; line-height: 1.25;">
			<spring:message code="app.title"></spring:message>
		</span>
		<span class="help">
			<spring:message code="login.tips"></spring:message>
		</span>
	</div>
	
    <form id="addUserForm">
    	<table border="0" cellpadding="10" class="boxc">
    		<col width="20%">
    		<col width="60%">

    		<tr>
    			<td colspan="2"></td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<h3>用户注册</h3>
    			</td>
    		</tr>
    		
    		<tr>
    			<td align="right">学校</td>
				<td>
					<select id="ecList" class="easyui-combobox" data-options="required:true, onChange:App.findRoleByEcId">
					<option value="">请选择</option>
					<c:if test="${null != ec_list }">
						<c:forEach var="item" items="${ec_list }">
							<option value="${item.VALUE_FIELD }">${item.TEXT_FIELD }</option>
						</c:forEach>								
					</c:if>
					</select>
				</td>
    		</tr>
    		<tr>
    			<td  align="right">角色</td>
				<td>
					<input id="roleSelector" name="roleId" class="easyui-combobox" data-options="required:true, valueField: 'VALUE_FIELD', textField: 'TEXT_FIELD'" >
				</td>
    		</tr>
			<tr>
				<td  align="right">登录名</td>
				<td><input maxlength="60" id="loginName" name="loginName" class="easyui-textbox" type="text" data-options="required:true, missingMessage:'请输入必填项'"/></td>
			</tr>
			<tr>
				<td  align="right">密码</td>
				<td><input maxlength="60" id="plainPassword" name="plainPassword" class="easyui-textbox" type="password" data-options="required:true, missingMessage:'请输入必填项'"/></td>
			</tr>
			<tr>
				<td  align="right">用户名</td>
				<td><input maxlength="160" id="name" name="name" class="easyui-textbox" type="text" data-options="required:true, missingMessage:'请输入必填项'" /></td>
			</tr>
			<tr>
				<td  align="right">邮箱</td>
				<td><input maxlength="160" id="email" name="email" class="easyui-textbox" type="text" /></td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-save'" style="width:80px" onclick="App.submitForm()">注册</a>
					<a href="${ctx }/login" class="easyui-linkbutton"  data-options="iconCls:'icon-back'" style="width:80px; margin-left: 40px;" >返回</a>
				</td>
			</tr>
    	</table>
	</form>
	
	<div style="float: right;margin-right: 100px;margin-top: 10px;margin-bottom:  10px;">
		<jsp:include page="/footer.jsp"/>
	</div>
	
</body>
</html>
