<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="course.title"></spring:message> -  <spring:message code="app.title"></spring:message> </title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="easyui-panel" title='<spring:message code="common.search.condition"></spring:message>' >
		<form class="easyui-condition-form" id="listForm" >
			<table>
				<tr>
					<td>课程名称</td>
					<td>
						<input name="name" class="easyui-textbox" type="text" />
					</td>
					<td>教材</td>
					<td>
						<input name="material" class="easyui-textbox" type="text" />
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:5px">
			<!-- data-hotkey-event="click"  -->
	    	<a  href="javascript:void(0)" class="easyui-linkbutton easyui-hotkey" data-hotkey-keycode="13" data-options="iconCls:'icon-search'" style="width:80px" id="searchBtn" >查询</a>
	    	<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="width:80px" id="clearBtn">清空</a>
	   </div>
	</div>
	
	<table id="dataGrid" class="easyui-datagrid"></table>
	
	<div id="saveOrUpdateDlg" class="easyui-dialog" style="width:520px;height:220px;"
		data-options="iconCls:'icon-save', title:'录入信息',buttons:'#saveOrUpdateDlg-buttons',modal:true,closed:true">
		<form class="easyui-condition-form" id="saveOrUpdateForm" >
			<input name="id" type="hidden" />
			<table border="0" cellpadding="4">
				<tr>
					<td>课程名称</td>
					<td>
						<input name="name" class="easyui-textbox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
					
					<td>学分</td>
					<td>
						<input name="credit"   class="easyui-numberbox" type="text" />
					</td>
				</tr>
				<tr>
					
					<td>专业</td>
					<td colspan="3">
						<select name="specialityIds" class="easyui-combobox" data-options="multiple:true" style="width: 320px;">
							<c:if test="${specialityList != null }">
								<c:forEach items="${specialityList }" var="item">
								<option value="${item[0] }">${item[1] }</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<td>教材</td>
					<td colspan="3">
						<textarea rows="2" name="material" style="width: 320px; height: 40px;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="saveOrUpdateDlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="saveOrUpdateBtn">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="cancelBtn">取消</a>
	</div>
	
	<jsp:include page="/include_biz.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx }/static/biz/sims/course.js"></script>
</body>
</html>