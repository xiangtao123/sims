<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
<!-- 
	<link rel="stylesheet" href="${ctx }/static/ztree_v3/css/demo.css" type="text/css">
 -->
<link rel="stylesheet" href="${ctx }/static/ztree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="${ctx }/static/ztree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/common/easyui-common.js"></script>

<script type="text/javascript">
<!--
	var menu = function(){};
	menu.treeSetting = {
			data: {
				key: {
					title:"title"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick:function(event, treeId, treeNode, clickFlag) {
					var url = treeNode.targetUrl;
					if (url != undefined && url != '') {
						easyuiCommon.addTab(treeNode.id, treeNode.name,url);
					}
				}
			}
	};
	
	menu.loadMenuTree = function() {
		var loadMenuDataUrl = '${ctx }/action/loadMenuTree';
		$.post(loadMenuDataUrl, function(data){
			if (data && data.length > 0) {
				$.fn.zTree.init($("#menuTree"), menu.treeSetting, data);
			} else {
				$.fn.zTree.init($("#menuTree"), menu.treeSetting, [{id:0, pId:0, name:'暂无信息', title:'该面板显示已授权的操作菜单'}]);
			}
		});
	};
	
	$(function(){
		menu.loadMenuTree();
	});

//-->
</script>

<input id="basePath" name="basePath" type="hidden" value="${ctx }" />

<ul id="menuTree" class="ztree"></ul>



