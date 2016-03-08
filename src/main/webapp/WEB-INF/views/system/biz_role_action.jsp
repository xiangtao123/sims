<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>角色管理 </title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="/include_biz.jsp"/>

    <div id="dlg_action_edit" class="easyui-dialog"
         data-options="title:'添加资源',closed:true,modal:true,collapsible:false,buttons:dlg_action_edit_buttons"
         style="width: 400px;height:420px;padding: 10px;margin: auto">
        <div class="ui form">
            <form id="action_detail" class="easyui-form" action="${ctx}/action/edit" method="post">
                <input id="action_id" type="hidden" name="id">
                <input id="action_pid" type="hidden" name="pId">

                <div class="field">
                    <label for="action_name">资源名称：</label>
                    <input type="text" class="easyui-textbox" name="name" data-options="required:true" id="action_name"
                           style="width: 100%">
                </div>
                <div class="field">
                    <label for="action_text">显示名称：</label>
                    <input type="text" name="text" data-options="required:true" class="easyui-textbox"
                           style="width: 100%" id="action_text">
                </div>
                <div class="field">
                    <label for="action_idx">序号：</label>
                    <input type="text" name="idx" data-options="required:true" class="easyui-numberbox" id="action_idx">
                </div>
                <div class="field">
                    <label for="action_type">类型：</label>
                    <select id="action_type" name="type" class="easyui-combobox" data-options="editable:false,onSelect: function(rec) {
				if (rec.value == 'page') {
					$('.type-extend').show();
				} else {
					$('.type-extend').hide();
				}
	    	}">
                        <option value="module">模块</option>
                        <option value="page">页面菜单</option>
                        <option value="btn">按钮</option>
                        <option value="gateway">登录</option>
                    </select>
                </div>
                <div class="field type-extend">
                    <label for="action_targetUrl">目标地址：</label>
                    <input id="action_targetUrl" type="text" name="targetUrl" class="easyui-textbox"
                           style="width: 100%">
                </div>
                <div class="field">
                    <label for="action_remark">备注：</label>
                    <input id="action_remark" type="text" name="remark" class="easyui-textbox"  style="width: 80%">
                </div>

            </form>
        </div>
    </div>
    <script type="text/javascript" >
        var dlg_action_edit_buttons = [
            {
                text: '保存',
                iconCls: 'icon-save',
                handler: dlg_action_edit_submit
            },
            {
                text: '重置',
                iconCls: 'icon-undo',
                handler: dlg_action_edit_reset
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: dlg_action_edit_cancle
            }];
        var action_edit_success = null;
        function showActionAdd(pId, success) {
            $('#dlg_action_edit').dialog({title: "添加资源"});
            $('#action_detail').form('clear').form("disableValidation");
            $('#action_pid').val(pId);
            action_edit_success = success;
            $('#dlg_action_edit').dialog('center').dialog('open');
        }
        function showActionEdit(actionId, success) {
            $('#dlg_action_edit').dialog({title: '编辑资源'});
            $('#action_detail').form({
                onLoadSuccess: function (data) {
                    if (data) {
                        if (data.type == 'page') {
                            $('.type-extend').show();
                        } else {
                            $('.type-extend').hide();
                        }
                    }
                    return data;
                }
            }).form("disableValidation").form('load', '${ctx}/action?id=' + actionId);
            action_edit_success = success;
            $('#dlg_action_edit').dialog('center').dialog('open');
        }
        function getActionFormData() {
            return eui.findFormDataJsonObject('#action_detail :input');
        }
        function dlg_action_edit_submit() {
            var form = $('#action_detail');
            if (!form.form("enableValidation").form("validate"))
                return;
            var formDataJsonStr = eui.findFormDataJsonStr('#action_detail :input');
            eui.log.info(formDataJsonStr);
            $.post("${ctx}/action/saveOrUpdate", {'params': formDataJsonStr})
                .done(function (data) {
                    //console.log(data);
                    if (data && data > 0) {
                        action_edit_success(data);
                        dlg_action_edit_cancle();
                        reloadActionTree();
                    }
                    else {
                        $.messager.alert('失败', "添加失败！", 'error');
                    }
                });
        }
        function dlg_action_edit_cancle() {
            $('#dlg_action_edit').dialog('center').dialog('close');
        }
        function dlg_action_edit_reset() {
            if ($('#action_id').val()) {
                $('#action_detail').form('load', '${ctx}/action?id=' + $('#action_id').val());
            } else {
                var pid = $('#action_pid').val();
                $('#action_detail').form('clear');
                $('#action_pid').val(pid);
            }
        }

        // 重新加载 系统资源 tree
        function reloadActionTree(){
	       	 var roleId = 0;
	    	 var zTree = $.fn.zTree.getZTreeObj('role_tree');
	         var nodes = zTree.getSelectedNodes();
	         if (nodes.length == 1) {
	        	 roleId = nodes[0].id;
	         }
	    	initActionTree(roleId);
        };
        
    </script>

    <div id="dlg_role_edit" style="width: 400px;height:300px;" class="easyui-dialog"
         data-options="closed:true,modal:true,collapsible:false,buttons:dlg_user_edit_buttons">
        <div class="ui form">
            <form id="role_detail" class="easyui-form" method="post" data-options="novalidate:true" action="">
                <input id="role_id" type="hidden" name="id">
                <input id="role_pid" type="hidden" name="pId">

                <div class="field">
                    <label for="role_name">角色名称:</label>
                    <input id="role_name" name="name" class="easyui-textbox" style="width: 100%"
                           data-options="required:true">
                </div>
                <c:if test="${roleLevel == 1}">
                    <div class="field">
                        <label for="role_ec">企业:</label>
                        <input name="ec" id="role_ec" class="easyui-combobox" style="width: 100%"
                               data-options="
                    url:'${ctx}/ecinfor/list',
                    required:true,
					valueField:'id',
					textField:'corpName',
					multiple:false,
					method:'get',
					panelHeight:'auto'">
                    </div>
                </c:if>
            </form>

        </div>
    </div>

    <script type="text/javascript">
        var dlg_user_edit_buttons = [
            {
                text: '保存',
                iconCls: 'icon-ok',
                handler: dlg_role_edit_submit
            },
            {
                text: '重置',
                handler: dlg_role_edit_reset
            },
            {
                text: '取消',
                handler: dlg_role_edit_cancle
            }];
        var role_edit_success = null;
        function showRoleAdd(pId, success) {
            $('#dlg_role_edit').dialog({title: "添加角色"});
            $('#role_detail').form('clear').form("disableValidation");
            $('#role_pid').val(pId);
            role_edit_success = success;
            $('#dlg_role_edit').dialog('center').dialog('open');
        }
        function showRoleEdit(roleId, success) {
            $('#dlg_role_edit').dialog({title: '编辑角色'});
            $('#role_detail').form("disableValidation").form('load', '${ctx}/role?id=' + roleId);
            role_edit_success = success;
            $('#dlg_role_edit').dialog('center').dialog('open');
        }
        function getRoleFormData() {
            return {
                id: $("#role_id").val(),
                pId: $("#role_pid").val(),
                name: $("#role_name").val(),
                ec: function () {
                    if ($("#role_ec").length) {
                        return $("#role_ec").combobox('getValue');
                    }
                    return '';
                }
            };
        }
        function dlg_role_edit_submit() {
            var form = $('#role_detail');
            form.form('enableValidation');
            if (!form.form('validate'))
                return;

            var data = getRoleFormData();
            $.post("${ctx}/role/edit", data)
                .done(function (data) {
                    //console.log(data);
                    if (data && data > 0) {
                        role_edit_success(data);
                        dlg_role_edit_cancle();
                    }
                    else {
                        $.messager.alert('失败', "添加失败！", 'error');
                    }
                });
        }
        function saveRole(data, callback) {
            $.post("${ctx}/role/edit", data)
                .done(function (data) {
                    //console.log(data);
                    if (data && data > 0) {
                        if (callback)
                            callback(data);
                    }
                    else {
                        $.messager.alert('失败', "添加失败！", 'error');
                    }
                });
        }
        function dlg_role_edit_cancle() {
            $('#dlg_role_edit').dialog('center').dialog('close');
        }
        function dlg_role_edit_reset() {
            if ($('#role_id').val()) {
                $('#role_detail').form('load', '${ctx}/role?id=' + $('#role_id').val());
            } else {
                var pid = $('#role_pid').val();
                $('#role_detail').form('clear');
                $('#role_pid').val(pid);
            }
        }
    </script>

<link rel="stylesheet" type="text/css" href="${ctx }/static/ztree_v3/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx }/static/ztree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/common/jquery.ztree.extend.js"></script>

<div id="layout" class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'east',split:true,title:'角色资源信息',collapsible:false" style="width:60%;">
        <div class="easyui-panel" style="padding:5px;">
            <shiro:hasPermission name="element:action:cud">
                <a id="btn_action_add" href="#" class="easyui-linkbutton"
                   data-options="plain:true,iconCls:'icon-add'">添加</a>
                <a id="btn_action_edit" href="#" class="easyui-linkbutton"
                   data-options="plain:true,iconCls:'icon-edit'">编辑</a>
                <a id="btn_action_del" href="#" class="easyui-linkbutton"
                   data-options="plain:true,iconCls:'icon-remove'">删除</a>
            </shiro:hasPermission>

            <a id="btn_action_save" href="#" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-ok'">授权</a>
            <!-- <a id="btn_role_del_many" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">批量删除</a>-->

            <div style="float: right">
                <input class="easyui-searchbox" data-options="prompt:'输入关键字',searcher:searchAction"
                       style="width:300px">
            </div>
        </div>
        <editor-folder desc="">

            <ul id="action_tree" class="ztree"></ul>

            <script type="text/javascript">
                var action_list = [];
                var action_setting = {
                    data: {
                        key: {
                            title: "remark"
                        }
                    },
                    async: {
                        enable: true,
                        url: "${ctx}/action/list",
                        autoParam: ["id=parent"],
                        otherParam: {
                            role: 0
                        },
                        type: "get"
                    },
                    view: {
                        fontCss: function (treeId, treeNode) {
                            return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
                                color: "#333",
                                "font-weight": "normal"
                            };
                        },
                        expandSpeed: ""
                    },
                    check: {
                        enable: true,
                        chkboxType: {"Y": "p", "N": "s"}
                    },
                    callback: {
                        onAsyncSuccess: function (event, treeId, treeNode, msg) {
                            $.fn.zTree.autoExpand(treeId, treeNode);
                            resize();
                        },
                        beforeRemove: function (treeId, treeNode) {
                            delAction(treeId, treeNode);
                            return false;
                        }
                    }
                };
                function delAction(treeId, treeNode) {
                    $.messager.confirm('删除资源', "确认删除资源？", function (r) {
                        if (r)
                            $.post("${ctx}/action/del", {id: treeNode.id})
                                .done(function (data) {
                                    //console.log(data);
                                    var zTree = $.fn.zTree.getZTreeObj(treeId);
                                    zTree.removeNode(treeNode);
                                })
                                .fail(function () {
                                    $.messager.alert('失败', "删除失败！", 'error');
                                });
                    });
                }
                function searchAction(name) {
                    updateActions(false);
                    var zTree = $.fn.zTree.getZTreeObj("action_tree");
                    action_list = zTree.getNodesByParamFuzzy('name', name);
                    updateActions(true);
                }

                function updateActions(highlight) {
                    var zTree = $.fn.zTree.getZTreeObj('action_tree');
                    for (var i = 0, l = action_list.length; i < l; i++) {
                        action_list[i].highlight = highlight;
                        zTree.updateNode(action_list[i]);
                    }
                }
                function getCurrentAction() {
                    var zTree = $.fn.zTree.getZTreeObj('action_tree');
                    var nodes = zTree.getSelectedNodes();
                    if (nodes.length !== 1) {
                        $.messager.alert('错误', "请选择一个资源！", 'error');
                        return;
                    }
                    return nodes[0];
                }
                function getActionTree() {
                    return $.fn.zTree.getZTreeObj('action_tree');
                }
                function initActionTree(roleId) {
                    /*if (roleId) {
                     var zTree = $.fn.zTree.getZTreeObj('action_tree');
                     zTree.checkAllNodes(false);
                     $.get("
                    ${ctx}/role/actions?role=" + roleId, function (result) {
                     result.forEach(function (val) {
                     var node = zTree.getNodeByParam("id", val, null);
                     zTree.checkNode(node, true, true);
                     });
                     });
                     }*/


                    if (roleId)
                        action_setting.async.otherParam.role = roleId;
                    $.fn.zTree.init($("#action_tree"), action_setting);
                }
                $(document).ready(function () {
                    $.fn.zTree.init($("#action_tree"), action_setting);
                });
                function getCheckedAction() {
                    return getActionTree().getCheckedNodes();
                }
            </script>

        </editor-folder>

    </div>
    <div data-options="region:'center',title:'角色管理'" style="height: 100%;width:40%">
        <div class="easyui-panel" style="padding:5px;">
            <a id="btn_role_add" href="#" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-add'">添加</a>
            <a id="btn_role_edit" href="#" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-edit'">编辑</a>
            <a id="btn_role_del" href="#" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-remove'">删除</a>
            <!-- <a id="btn_role_del_many" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">批量删除</a>-->
            <div style="float: right">
                <input class="easyui-searchbox" data-options="prompt:'输入关键字',searcher:searchRole"
                       style="width:300px">
            </div>
        </div>

        <editor-folder desc="">

            <ul id="role_tree" class="ztree" style="height: auto"></ul>
            <script>
                var role_list = [];
                var role_tree_click = null;
                var role_setting = {
                    async: {
                        enable: true,
                        url: "${ctx }/role/list",
                        autoParam: ["id=id"],
                        type: "get"
                    },
                    check: {
                        enable: false
                    }, edit: {
                        enable: false
                    },
                    view: {
                        expandSpeed: "",
                        selectedMulti: false,
                        fontCss: function (treeId, treeNode) {
                            return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
                                color: "#333",
                                "font-weight": "normal"
                            };
                        }
                    },
                    callback: {
                        onAsyncSuccess: function (event, treeId, treeNode, msg) {
                            $.fn.zTree.autoExpand(treeId, treeNode);
                            resize();
                        },
                        onClick: function (event, treeid, treeNode, clickFlag) {
                            if (role_tree_click)
                                role_tree_click(treeNode.id);
                        },
                        beforeRemove: function (treeId, treeNode) {
                            delRole(treeId, treeNode);
                            return false;
                        }
                    }
                };
                function delRole(treeId, treeNode) {
                    $.messager.confirm('删除角色', "确认删除角色？", function (r) {
                        if (r)
                            $.post("${ctx}/role/del", {id: treeNode.id})
                                .done(function (data) {
                                    if (data == 1) {
                                        var zTree = $.fn.zTree.getZTreeObj(treeId);
                                        zTree.removeNode(treeNode);
                                    }
                                    else if (data == 4)
                                        $.messager.alert('操作失败', "角色不存在！", 'warning');
                                    else if (data == 3)
                                        $.messager.alert('操作失败', "该角色已被引用，如需删除请解除引用！", 'warning');
                                    else if (data == 2)
                                        $.messager.alert('操作失败', "抱歉，您无权删除该角色！", 'warning');
                                    else {
                                        $.messager.alert('操作失败', "程序运行异常！", 'error');
                                    }

                                })
                                .fail(function () {
                                    $.messager.alert('失败', "删除失败！", 'error');
                                });
                    });
                }
                function searchRole(name) {
                    updateRoles(false);
                    var zTree = $.fn.zTree.getZTreeObj("role_tree");
                    role_list = zTree.getNodesByParamFuzzy('name', name);
                    updateRoles(true);
                }

                function updateRoles(highlight) {
                    var zTree = $.fn.zTree.getZTreeObj('role_tree');
                    for (var i = 0, l = role_list.length; i < l; i++) {
                        role_list[i].highlight = highlight;
                        zTree.updateNode(role_list[i]);
                    }
                }
                function getCurrentRole() {
                    var zTree = $.fn.zTree.getZTreeObj('role_tree');
                    var nodes = zTree.getSelectedNodes();
                    if (nodes.length !== 1) {
                        $.messager.alert('错误', "请选择一个角色！", 'error');
                        return;
                    }
                    return nodes[0];
                }
                function getRoleTree() {
                    return $.fn.zTree.getZTreeObj('role_tree');
                }
                function initRoleTree(callback) {
                    if (callback) {
                        if (callback.click) {
                            role_tree_click = callback.click
                        }
                    }
                    $.fn.zTree.init($("#role_tree"), role_setting);
                }
            </script>
        </editor-folder>
    </div>
</div>


<script>
    function resize() {
        //console.log("resize");
        if (parent.easyuiCommon) {
            var tabId = parent.easyuiCommon.tabId;
            parent.$(tabId).tabs('getSelected').find('iframe').trigger('onload');
        }
        var height = Math.max($("#role_tree").height(), $("#action_tree").height());
        $('#layout').layout('resize', {
            height: height + 100 + 'px'
        });
    }

    function addRoleCallBack(r) {
        var zTree = $.fn.zTree.getZTreeObj('role_tree');
        if (r && r > 0) {
            var data = getRoleFormData();
            zTree.addNodes(getCurrentRole(), {id: data.id, pId: data.pId, name: data.name});
            resize();
        }
        else {
            $.messager.alert('失败', "添加失败！", 'error');
        }
    }
    function addActionCallBack(r) {
        var zTree = $.fn.zTree.getZTreeObj('action_tree');
        if (r && r > 0) {
            var data = getActionFormData();
            zTree.addNodes(getCurrentAction(), {id: data.id, pId: data.pId, name: data.text, checked: true});
            resize();
        }
        else {
            $.messager.alert('失败', "添加失败！", 'error');
        }
    }
    function editRoleCallBack(r) {
        var zTree = $.fn.zTree.getZTreeObj('role_tree');
        if (r && r > 0) {
            var data = getRoleFormData();
            zTree.editName(getCurrentRole());
            zTree.cancelEditName(data.name);
        }
        else {
            $.messager.alert('失败', "添加失败！", 'error');
        }
    }
    function editActionCallBack(r) {
        if (r && r > 0) {
            var data = getActionFormData();
            getActionTree().cancelEditName(data.name);
        }
        else {
            $.messager.alert('失败', "添加失败！", 'error');
        }
    }
    function saveRoleCallBack(r) {
        var zTree = $.fn.zTree.getZTreeObj('role_tree');
        if (r && r > 0) {
            $.messager.alert('成功', "添加成功！", 'info');
        }
        else {
            $.messager.alert('失败', "添加失败！", 'error');
        }
    }
    $(document).ready(function () {
        initRoleTree({click: initActionTree});
        $("#btn_role_add").bind('click', function () {
            var node = getCurrentRole();
            if (node)
                showRoleAdd(node.id, addRoleCallBack);// addRole("role_tree", node);
        });
        $("#btn_role_edit").bind('click', function () {
            var node = getCurrentRole();
            if (node) {
                showRoleEdit(node.id, editRoleCallBack);
            }
        });
        $("#btn_role_del").bind('click', function () {
            var node = getCurrentRole();
            if (node)
                delRole("role_tree", node);
        });

        $("#btn_action_add").bind('click', function () {
            var node = getCurrentAction();
            if (node)
                showActionAdd(node.id, addActionCallBack);
        });
        $("#btn_action_edit").bind('click', function () {
            var node = getCurrentAction();
            if (node) {
                showActionEdit(node.id, editActionCallBack);
            }
        });
        $("#btn_action_del").bind('click', function () {
            var node = getCurrentAction();
            if (node)
                delAction("action_tree", node);
        });

        $("#btn_action_save").bind('click', function () {
            var actions = getCheckedAction();
            var ids = [];
            for (var x = 0; x < actions.length; x++) {
                ids.push(actions[x].id);
                //console.log(actions[x]);
            }
            var ids_str = ids.join(',');
            var role = getCurrentRole();

            if (role && role.level === 0) {
                $.messager.alert('警告', "不能修改自身资源！", 'warning');
                return;
            }

            if (role)
                saveRole({id: role.id, actions: ids_str}, saveRoleCallBack)
        });

        initActionTree(0);
        /* $("#btn_role_del_many").bind('click', function () {
         var zTree = $.fn.zTree.getZTreeObj("role_tree");
         var nodes = zTree.getCheckedNodes();
         nodes.forEach(function (node) {
         $.post("
        ${ctx}/role/del", {id: node.id})
         .done(function (data) {
         zTree.removeNode(node);
         });
         });
         });*/
    });

</script>


</body>
</html>
