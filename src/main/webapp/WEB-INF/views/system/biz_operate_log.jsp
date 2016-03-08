<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title>操作日志 </title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="easyui-panel" title="查询条件" >
		<form class="easyui-condition-form" id="listForm" action="">
			<table>
				<tr>
					<td>操作类型</td>
					<td>
						<input name="actionType" class="easyui-textbox" type="text" />
					</td>
					<td>业务类型</td>
					<td>
						<input name="bizType" class="easyui-textbox" type="text" />
					</td>
				 
					<td>业务信息</td>
					<td>
						<input name="bizContent" class="easyui-textbox" type="text" />
					</td>
				</tr>
				<tr>
					<td>操作者</td>
					<td>
						<input name="creatorName" class="easyui-textbox" type="text" />
					</td>
					
					<td>开始日期</td>
					<td>
						<input name="beginCreatetime" class="easyui-datebox" data-options="clear:true" type="text" />
					</td>
					<td>结束日期</td>
					<td>
						<input name="endCreatetime" class="easyui-datebox" data-options="clear:true" type="text" />
					</td>
				</tr>
				
			</table>
		</form>
		<div style="text-align:center;padding:5px">
			<!-- data-hotkey-event="click"  -->
	    	<a  href="javascript:void(0)" class="easyui-linkbutton easyui-hotkey" data-hotkey-keycode="13" data-options="iconCls:'icon-search'" style="width:80px" onclick="submitForm()">查询</a>
	    	<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="width:80px" onclick="clearForm()">重置</a>
	   </div>
	</div>
	
	<table id="dg" class="easyui-datagrid" title="操作日志列表"  data-options="rownumbers:true,pagination:true,singleSelect:false,collapsible:true,url:'${ctx }/bizActionLog/list',method:'post'">
		<thead>
			<tr>
				<th data-options="field:'id',width:80,checkbox:true">编号</th>
				<th data-options="field:'actionType',width:100">操作类型</th>
				<th data-options="field:'bizType',width:100">业务类型</th>
				<th data-options="field:'bizContent',width:200">业务信息</th>
				<th data-options="field:'creatorName',width:150">操作者</th>
				<th data-options="field:'createTime',width:150">操作时间</th>
			</tr>
		</thead>
	</table>
	
	<jsp:include page="/include_biz.jsp"></jsp:include>
	<script>
		function submitForm(){
			eui.loadDataGridWithFormDataSelector('#dg','#listForm :input','params');
			eui.log.info('submit...');
		}
		function clearForm(){
			$('#listForm').form('clear');
		}
	</script>
	
</body>
</html>