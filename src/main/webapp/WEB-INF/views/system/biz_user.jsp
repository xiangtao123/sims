<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/include_biz.jsp"></jsp:include>
	<link rel="stylesheet" href="${ctx}/static/ztree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	

</head>
<body>

<div class="easyui-panel query_panel" title="查询条件" id="query_panel" data-options="collapsible:true">
    <form class="easyui-condition-form" id="listForm" action="">
        <div>
            <span>
                <label for="loginName">登陆名</label>
                <input id="loginName" name="loginName" class="easyui-textbox" type="text"/>
            </span>
            <span>
                <label for="name">姓名</label>
                <input id="name" name="name" class="easyui-textbox" type="text"/>
            </span>
            <span id="query_role_field">
                <label for="role">角色</label>
                  <input id="query_role_text" type="text" class="easyui-textbox" readonly value=""
                         />
                    &nbsp;<a id="query_menuBtn" href="#" onclick="query_showMenu(); return false;">选择</a>
                    <input name="role" type="hidden" value="" id="role"/>

                    <SCRIPT type="text/javascript">
				        var query_role_setting = {
				            async: {
				                enable: true,
				                url: "${ctx }/role/list",
				                autoParam: ["id=id"],
				                type: "get"
				            },
				            view: {
				                selectedMulti: false,
				                dblClickExpand: true
				            },
				            callback: {
				                onAsyncSuccess: function (event, treeId, treeNode, msg) {
				                    $.fn.zTree.autoLoad(treeId, treeNode);
				                },
				                onClick: query_onClick
				            }
				        };
				
				        function query_onClick(e, treeId, treeNode) {
				            var zTree = $.fn.zTree.getZTreeObj("query_roleSelete"),
				                    nodes = zTree.getSelectedNodes();
				            $("#role").val(nodes[0].id);
				            $("#query_role_text").textbox('setValue', nodes[0].name)
				
				        }
				
				        function query_showMenu() {
				            var roleObj = $("#query_role_field");
				            var roleOffset = $('#query_role_field').offset();
				            $("#query_menuContent").css({
				                left: roleOffset.left + "px",
				                top: roleOffset.top + roleObj.outerHeight() + "px"
				            }).slideDown("fast");
				
				            $("body").bind("mousedown", query_onBodyDown);
				        }
				        function query_hideMenu() {
				            $("#query_menuContent").fadeOut("fast");
				            $("body").unbind("mousedown", query_onBodyDown);
				        }
				        function query_onBodyDown(event) {
				            if (!(event.target.id == "query_menuBtn" || event.target.id == "query_menuContent" || $(event.target).parents("#query_menuContent").length > 0)) {
				                query_hideMenu();
				            }
				        }
				    </SCRIPT>
                <!--<input name="role" class="easyui-combobox" id="role" data-options="
                    url:'${ctx}/role/all',
					valueField:'id',
					textField:'name',
					multiple:false,
					method:'get',
					panelHeight:'200'">-->

            </span>
            <span>
                <label for="mail">邮箱</label>
                <input id="mail" name="mail" class="easyui-textbox" type="text"/>
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


<table id="dg" class="easyui-datagrid" title="用户列表"
       data-options="
           rownumbers:true,
           pagination:true,
           singleSelect:false,
           collapsible:true,
           url:'${ctx }/user/list',
           method:'post',
           toolbar:toolbar
       ">
    <thead>
    <tr>
        <th data-options="field:'id',width:80,checkbox:true">编号</th>
        <th data-options="field:'loginName',width:80">登录名</th>
        <th data-options="field:'name',width:80">姓名</th>
        <th data-options="field:'role',width:100">角色</th>
        <th data-options="field:'email',width:100">邮箱</th>
        <th data-options="field:'registerDate',width:200">注册时间</th>
        <th data-options="field:'companyName',width:150">公司</th>

    </tr>
    </thead>
</table>


    <div id="dlg_user_edit" style="width:480px;height:400px;" class="easyui-dialog"
         data-options="title:'添加用户',closed:true,modal:true,collapsible:false,buttons:buttons">
        <div class="ui form">
            <form id="user_detail" class="easyui-form" action="${ctx }/user/edit" method="post"
                  data-options="novalidate:true">
                <input name="id" id="user_id" type="hidden">

                <div class="field">
                    <label>登录名称:</label>
                    <input class="easyui-textbox" type="text" name="loginName" id="user_loginName"
                           data-options="required:true,missingMessage:'该输入项为必填项'" style="width: 100%">
                </div>
                <div class="field">
                    <label>用户名称:</label>
                    <input class="easyui-textbox" type="text" name="name" id="user_name"
                           data-options="required:true,missingMessage:'该输入项为必填项'" style="width: 100%">
                </div>
                <div class="field">
                    <label>密码:
                        <div id="password_warning" style="float: right"><a style="color: #ff0000">*不修改则留空</a></div>
                    </label>
                    <input class="easyui-textbox" type="password" name="password" id="user_password"
                           data-options="required:true,validType:'length[6,20]',missingMessage:'该输入项为必填项',invalidMessage:'数据不匹配'"
                           value="" style="width: 100%">
                </div>
                <div class="field" id="user_passwordConfirm_field">
                    <label>密码确认:</label>
                    <input class="easyui-textbox" type="password" name="passwordConfirm" id="user_passwordConfirm"
                           value="" style="width: 100%">
                </div>
                <div class="field">
                    <label>Email:</label>
                    <input class="easyui-textbox" type="text" name="email" id="user_email"
                           data-options="validType:'email'" style="width: 100%">
                </div>
                <div class="field" id="role_field" style="padding-bottom: 0">
                    <label>角色:</label>
                    <input id="roleSel" type="text" class="easyui-textbox" readonly value=""
                           style="width: 90%;" data-options="required:true,missingMessage:'该输入项为必填项'"/>
                    &nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                    <input name="role" type="hidden" value="" id="user_role"/>
                </div>

            </form>
        </div>
    </div>
    <div id="menuContent" class="combo-panel panel-body panel-body-noheader"
         style="display:none; position: absolute;z-index: 110005;border: 1px solid #D4D4D4;background-color: #FFF;">
        <ul id="roleSelete" class="ztree" style="margin-top:0;"></ul>
    </div>
    <div id="query_menuContent" class="combo-panel panel-body panel-body-noheader"
         style="display:none; position: absolute;z-index: 110005;border: 1px solid #D4D4D4;background-color: #FFF;">
        <ul id="query_roleSelete" class="ztree" style="margin-top:0;"></ul>
    </div>
    
    <script type="text/javascript" src="${ctx}/static/ztree_v3/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/common/jquery.ztree.extend.js"></script>
    
    <script type="text/javascript">
        var buttons = [
            {
                text: '保存',
                iconCls: 'icon-ok',
                handler: dlg_user_edit_submit
            },
            {
                text: '重置',
                handler: dlg_action_edit_reset
            },
            {
                text: '取消',
                handler: dlg_action_edit_cancle
            }];
        var user_edit_success = null;
        // extend the 'equals' rule
        $.extend($.fn.validatebox.defaults.rules, {
            equals: {
                validator: function (value, param) {
                    return value == $(param[0]).val();
                },
                message: '数据不匹配'
            }
        });
        function showUserFormAdd(success) {
            $('#password_warning').hide();
            $('#user_password').textbox({
                required: true,
                validType: 'length[6,20]'
            });

            $('#user_passwordConfirm').textbox({validType: "equals['#user_password']", missingMessage: '该输入项为必填项'});

            $('#user_detail').form('disableValidation');
            $('#dlg_user_edit').dialog({title: "添加用户"});
            $('#user_detail').form('clear');
            user_edit_success = success;
            $('#dlg_user_edit').dialog('center').dialog('open');
        }
        function showUserFormEdit(id, success) {
            $('#user_password').textbox({
                required: false,
                validType: ''
            });
            $('#password_warning').show();
            $('#dlg_user_edit').dialog({title: '编辑用户'});
            $('#user_detail').form('disableValidation');
            $('#user_detail').form('load', '${ctx}/user?id=' + id);
            user_edit_success = success;
            $('#dlg_user_edit').dialog('center').dialog('open');
        }
        function dlg_action_edit_cancle() {
            $('#dlg_user_edit').dialog('close');
        }
        function dlg_action_edit_reset() {
            if ($('#user_id').val()) {
                $('#user_detail').form('load', '${ctx}/user?id=' + $('#user_id').val());
            } else {
                $('#user_detail').form('clear');
            }
        }
        function dlg_user_edit_submit() {
            if (!$('#user_detail').form('enableValidation').form('validate'))
                return;

            var data = {
                id: $("#user_id").val(),
                params: eui.findFormDataJsonStr("#user_detail input")
            };


            $.post("${ctx}/user/edit", data)
                    .done(function (data) {
                        console.log(data);
                        if (data != 1)
                            $.messager.alert('失败', "添加失败", 'error');
                        else {
                            if (user_edit_success)
                                user_edit_success();
                            $('#dlg_user_edit').window('close');
                        }
                    });
        }



        
        var setting = {
            async: {
                enable: true,
                url: "${ctx }/role/list",
                autoParam: ["id=id"],
                type: "post"
            },
            view: {
                selectedMulti: false,
                dblClickExpand: true
            },
            callback: {
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    $.fn.zTree.autoLoad(treeId, treeNode);
                },
                onClick: onClick
            }
        };

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("roleSelete"),
                    nodes = zTree.getSelectedNodes();
            $("#user_role").val(nodes[0].id);
            $("#roleSel").textbox('setValue', nodes[0].name)

        }

        function showMenu() {
            var roleObj = $("#role_field");
            var roleOffset = $("#role_field").offset();
            $("#menuContent").css({
                left: roleOffset.left + "px",
                top: roleOffset.top + roleObj.outerHeight() + "px"
            }).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        $(document).ready(function () {
            $.fn.zTree.init($("#roleSelete"), setting);
            $.fn.zTree.init($("#query_roleSelete"), query_role_setting);
            $("#user_detail").form({
                onLoadSuccess: function (data) {
                    if (data.role) {
                        var zTree = $.fn.zTree.getZTreeObj("roleSelete");
                        var nodes = zTree.getNodesByParam("id", data.role, null);
                        if (nodes.length > 0) {
                            $("#roleSel").textbox('setValue', nodes[0].name);
                            // $("#roleSel").val(nodes[0].name);
                            zTree.expandNode(nodes[0], true, true, true);
                            zTree.selectNode(nodes[0]);
                        }
                    }
                }
            });
        });

	    function submitForm() {
	        eui.loadDataGridWithFormDataSelector('#dg', '#listForm :input', 'params');
	        eui.log.info('submit...');
	    }
	    function clearForm() {
	        $('#listForm').form('clear');
	    }
	    function togglePanel(selector) {
	        var panel = $(selector);
	        if (panel.hasClass("easyui-panel")) {
	            if (panel.css('display') == 'none') {
	                panel.panel('expand', true);
	            }
	            else {
	                panel.panel('collapse', true);
	            }
	        }
	    }

	    function refresh() {
	        $('#dg').datagrid('reload');
	    }
	
	    var toolbar = [
	        {
	            text: '添加',
	            iconCls: 'icon-add',
	            handler: function () {
	                showUserFormAdd(refresh);
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
	                    showUserFormEdit(items[0]['id'], refresh);
	                }
	            }
	        },
	        '-',
	        {
	            text: '删除',
	            iconCls: 'icon-remove',
	            handler: function () {
	                var items = $("#dg").datagrid("getSelections");
	                if (items.length <= 0) {
	                    $.messager.alert('警告', '未选择任何行！', 'warning');
	                    return false;
	                }
	
	                $.messager.confirm('删除用户', "确认删除用户？", function (r) {
	                    if (r) {
	                        var ids = [];
	                        items.forEach(function (item) {
	                            ids.push(item['id']);
	                        });
	                        $.ajax({
	                            url: '${ctx }/user/del',
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
	    $(document).keydown(function (e) {
	        if (e.keyCode == 13) {
	            $('#submitLinkBtn').focus();
	            submitForm();
	        }
	    });
	    $(document).ready(function () {
	        submitForm()
	    });
	</script>
	
</body>
</html>
