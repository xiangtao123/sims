<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="student.title"></spring:message> -  <spring:message code="app.title"></spring:message> </title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="easyui-panel" title='<spring:message code="common.search.condition"></spring:message>' >
		<form class="easyui-condition-form" id="listForm" >
			<table>
				<tr>
					<td>姓名</td>
					<td>
						<input name="name" class="easyui-textbox" type="text" />
					</td>
					<td>身份证号</td>
					<td>
						<input name="idNo" class="easyui-textbox" type="text" />
					</td>
				</tr>
				<tr>
					<td>专业</td>
					<td>
						<select name="specialityId" class="easyui-combobox"  >
							<option value="">请选择</option>
							<c:if test="${specialityList != null }">
								<c:forEach items="${specialityList }" var="item">
								<option value="${item[0] }">${item[1] }</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
					<td>学号</td>
					<td>
						<input name="studentNo" class="easyui-textbox" type="text" />
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
	
	<div id="saveOrUpdateDlg" class="easyui-dialog" style="width:620px;"
		data-options="iconCls:'icon-save', title:'录入信息',buttons:'#saveOrUpdateDlg-buttons',modal:true,closed:true">
		<form class="easyui-condition-form" id="saveOrUpdateForm" >
			<input name="id" type="hidden" />
			<table border="0" cellpadding="4" style="width: 100%;">
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
				<tr>
					<td>学号</td>
					<td>
						<input name="studentNo"   class="easyui-textbox" type="text" />
					</td>
					
					<td>身份证号</td>
					<td>
						<input name="idNo" class="easyui-textbox" style="width: 140px;" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
										
				</tr>
				<tr>
					<td>姓名</td>
					<td>
						<input name="name" class="easyui-textbox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
					
					<td>性别</td>
					<td>
						<select name="gender" class="easyui-combobox">
							<option value="0">请选择</option>
							<option value="1">男</option>
							<option value="2">女</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>出生日期</td>
					<td>
						<input name="birthDay" class="easyui-datebox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
					
					<td>民族</td>
					<td>
						<input name="nation" class="easyui-textbox" type="text" />
					</td>
				</tr>
				
				<tr>
					<td>手机号</td>
					<td>
						<input name="mobile" class="easyui-textbox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
					
					<td>邮箱</td>
					<td>
						<input name="email" class="easyui-textbox" type="text" />
					</td>
															
				</tr>
				<tr>
					<td>家庭住址</td>
					<td colspan="3">
						<textarea name="homeAddr" rows="2" style="width:260px;height:40px;"></textarea>
					</td>
				</tr>
				<tr>
					<td>政治面貌</td>
					<td>
						<select name="politicalStatus" class="easyui-combobox">
							<option value="0">请选择</option>
							<option value="1">团员</option>
							<option value="2">党员</option>
						</select>
					</td>
					
					<td>专业</td>
					<td>
						<select name="specialityId" class="easyui-combobox">
							<c:if test="${specialityList != null }">
								<c:forEach items="${specialityList }" var="item">
								<option value="${item[0] }">${item[1] }</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
										
				</tr>
				<tr>
					<td>入学日期</td>
					<td>
						<input name="enrollDate" class="easyui-datebox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
					
					<td>毕业日期</td>
					<td>
						<input name="graduationDate" class="easyui-datebox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
										
				</tr>
				<tr>
					<td>授予学位日期</td>
					<td>
						<input name="degreeDate" class="easyui-datebox" type="text" data-options="required:true, missingMessage:'请输入必填项'" />
					</td>
					
					<td>审核状态</td>
					<td>
						<select name="auditState" class="easyui-combobox" >
							<option value="0">请选择</option>
							<option value="1">通过</option>
							<option value="2">不通过</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>审核意见</td>
					<td colspan="3">
						<textarea name="auditRemark" rows="2" style="width:260px;height:40px;"></textarea>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
	<div id="saveOrUpdateDlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="saveOrUpdateBtn">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="cancelBtn">取消</a>
	</div>
	
	<div id="auditDlg" class="easyui-dialog" style="width:420px;"
		data-options="iconCls:'icon-save', title:'录入信息',buttons:'#auditDlg-buttons',modal:true,closed:true">
		<form class="easyui-condition-form" id="auditForm" >
			<table border="0" cellpadding="4" style="width: 100%;">
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			 	
				<tr>
					<td>审核状态</td>
					<td colspan="3">
						<select name="auditState" class="easyui-combobox" data-options="required:true, missingMessage:'请输入必填项'" >
							<option value="1">通过</option>
							<option value="2">不通过</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>审核意见</td>
					<td colspan="3">
						<textarea name="auditRemark" rows="2" style="width:220px;height:40px;" ></textarea>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
	<div id="auditDlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="auditSaveBtn">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="auditCancelBtn">取消</a>
	</div>
	
	<jsp:include page="/include_biz.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx }/static/biz/sims/student.js"></script>
</body>
</html>