<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html >
<html>
<head>
    <title>企业管理 </title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

    <div id="dlg_ec_edit" style="width:480px;height:360px;" class="easyui-dialog" data-options="title:'添加企业',closed:true,modal:true,collapsible:false,buttons:buttons">
        <div class="ui form">
            <form id="ec_detail" class="easyui-form" action="${ctx }/ecinfor/edit" method="post"
                  data-options="novalidate:true">
                <input name="id" id="ec_id" type="hidden">

                <div class="field">
                    <label>集团名称:</label>
                    <input class="easyui-textbox" type="text" name="corpName"
                           data-options="required:true" style="width: 100%">
                </div>
                <div class="field">
                    <label>集团客户帐号:</label>
                    <input class="easyui-textbox" type="text" name="corpAccount"
                           data-options="required:true" style="width: 100%">
                </div>
                <div class="field">
                    <label>集团证件号:</label>
                    <input class="easyui-textbox" type="text" name="cardid"
                           data-options="required:true" style="width: 100%">
                </div>
                <div class="field">
                    <label>集团邮箱:</label>
                    <input class="easyui-textbox" type="text" name="email"
                           data-options="required:true,validType:'email'" style="width: 100%">
                </div>
                <div>
                    <div class="field" style="float: left;width: 48%">
                        <label>集团联系人:</label>
                        <input class="easyui-textbox" type="text" name="linkMan"
                               data-options="required:true" style="width: 100%">
                    </div>
                    <div class="field" style="float: right;width: 48%;">
                        <label>集团联系人电话:</label>
                        <input class="easyui-textbox" type="text" name="phoneNum"
                               data-options="required:true,validType:'mobile'" style="width: 100%">
                    </div>
                </div>

            </form>
        </div>
    </div>

	<div class="easyui-panel query_panel" title="查询条件" id="query_panel" data-options="collapsible:true">
	    <form class="easyui-condition-form" id="listForm" action="">
	        <div>
	            <span>
	                <label for="corpName">集团名称</label>
	                <input id="corpName" name="corpName" class="easyui-textbox" type="text"/>
	            </span>
	            <span>
	                <a href="javascript:void(0)" class="easyui-linkbutton easyui-hotkey"
	                   data-options="iconCls:'icon-search'" id="submitLinkBtn"
	                   style="width:80px" onclick="submitForm()">查询</a>
	                <a href="javascript:void(0)" class="easyui-linkbutton"
	                   data-options="iconCls:'icon-undo'" style="width:80px"
	                   onclick="clearForm()">重置</a>
	            </span>
	        </div>
	    </form>
	
	</div>
	
	
	<table id="dg" class="easyui-datagrid" title="集团列表"
	       data-options="
	           rownumbers:true,
	           pagination:true,
	           singleSelect:false,
	           collapsible:true,
	           url:'${ctx }/ecinfor/plist',
	           method:'post',
	           toolbar:toolbar
	       ">
	    <thead>
	    <tr>
	        <th data-options="field:'id',width:80,checkbox:true">编号</th>
	        <!--<th data-options="field:'groupLogoUrl',width:80">Logo</th>-->
	        <th data-options="field:'corpName',width:100">集团名称</th>
	        <th data-options="field:'corpAccount',width:100">集团客户帐号</th>
	        <th data-options="field:'linkMan',width:200">集团联系人</th>
	        <th data-options="field:'cardid',width:150">集团证件号:</th>
	        <th data-options="field:'email',width:150">集团邮箱:</th>
	        <th data-options="field:'linkMan',width:150">集团联系人:</th>
	        <th data-options="field:'phoneNum',width:150">集团联系人电话:</th>
	        <th data-options="field:'actionState',width:150">受理状态</th>
	    </tr>
	    </thead>
	</table>
	
	<jsp:include page="/include_biz.jsp"/>

	<script type="text/javascript">
        var buttons = [
            {
                text: '保存',
                iconCls: 'icon-ok',
                handler: dlg_ec_edit_submit
            },
            {
                text: '重置',
                handler: dlg_action_edit_reset
            },
            {
                text: '取消',
                handler: dlg_action_edit_cancle
            }];
        var ec_edit_success = null;

        function showEcFormAdd(success) {
            $('#dlg_ec_edit').dialog({title: "添加企业"});
            $('#ec_detail').form('clear').form("disableValidation");
            ec_edit_success = success;
            $('#dlg_ec_edit').dialog('center').dialog('open');
        }
        function showEcFormEdit(id, success) {
            $('#dlg_ec_edit').dialog({title: '编辑企业'});
            $('#ec_detail').form("disableValidation").form('load', '${ctx}/ecinfor?id=' + id);
            ec_edit_success = success;
            $('#dlg_ec_edit').dialog('center').dialog('open');
        }
        function dlg_action_edit_cancle() {
            $('#dlg_ec_edit').dialog('close');
        }
        function dlg_action_edit_reset() {
            if ($('#ec_id').val()) {
                $('#ec_detail').form('load', '${ctx}/ecinfor?id=' + $('#ec_id').val());
            } else {
                $('#ec_detail').form('clear');
            }
        }
        var errors = ['集团名称已存在！', '企业证件号已存在！', '企业客户帐号已存在！'];
        function dlg_ec_edit_submit() {
            if (!$("#ec_detail").form('enableValidation').form('validate'))
                return;
            var data = {
                id: $("#ec_id").val(),
                params: eui.findFormDataJsonStr("#ec_detail input")
            };

            $.post("${ctx}/ecinfor/edit", data)
                .done(function (data) {
                    console.log(data);
                    if (data != 1)
                        $.messager.alert('失败', errors[data - 2], 'error');
                    else {
                        if (ec_edit_success)
                            ec_edit_success();
                        $('#dlg_ec_edit').window('close');
                    }
                });
        }


        $(document).keydown(function (e) {
	        if (e.keyCode == 13) {
	            $('#submitLinkBtn').focus();
	            submitForm();
	        }
	    });
	    
        $(document).ready(function () {
	        submitForm()
	    });
	    
        function submitForm() {
	        eui.loadDataGridWithFormDataSelector('#dg', '#listForm :input','params');
	        eui.log.info('submit...');
	    }
	    
        function clearForm() {
	        $('#listForm').form('clear');
	    }
	
	    function refresh() {
	        $('#dg').datagrid('reload');
	    }
	    var toolbar = [
	        {
	            text: '添加',
	            iconCls: 'icon-add',
	            handler: function () {
	                showEcFormAdd(refresh);
	            }
	        },
	        {
	            text: '编辑',
	            iconCls: 'icon-edit',
	            handler: function () {
	                var items = $("#dg").datagrid("getSelections");
	                if (items.length !== 1)
	                    $.messager.alert("警告", "请选择一行！", "warning");
	                else {
	                    showEcFormEdit(items[0]['id'], refresh);
	                }
	            }
	        },
	        '-',
	        {
	            text: '删除',
	            iconCls: 'icon-remove',
	            handler: function () {
	                $.messager.confirm('警告', '确认删除所选项?', function (r) {
	                    if (r) {
	                        del();
	                    }
	                });
	            }
	        },
	        {
	            text: '刷新',
	            iconCls: 'icon-reload',
	            handler: function () {
	                $('#dg').datagrid('reload');
	            }
	        }
	    ];
	    function del() {
	        var items = $("#dg").datagrid("getSelections");
	        if (items.length <= 0) {
	            $.messager.alert('警告', '未选择任何行！', 'warning');
	            return false;
	        }
	        var ids = [];
	        items.forEach(function (item) {
	            ids.push(item['id']);
	        });
	        $.ajax({
	            url: '${ctx }/ecinfor/del',
	            data: {
	                params: ids.toString()
	            },
	            method: 'POST',
	            success: function (data, st, xhr) {
	                if (data === true) {
	                    $.messager.alert('成功', '删除成功', 'info');
	                    $('#dg').datagrid('reload');
	                }
	                else {
	                    $.messager.alert('失败', '删除失败', 'error');
	                }
	            },
	            error: function (xhr, st, err) {
	                $.messager.alert('失败', '删除失败', 'error');
	            }
	        });
	    }
	</script>

</body>
</html>
