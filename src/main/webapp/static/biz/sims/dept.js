/**
 * 部门管理
 */
var Dept = function(){};
Dept.searchFormId = '#listForm';
Dept.searchFormInputSelector = Dept.searchFormId + ' :input';
Dept.searchParamKey  = null;
Dept.dataGridId = "#dataGrid";
Dept.init = function() {
	var gridOptions = {
		url: eui.basePath + '/dept/list',
		method:'post',
		toolbar: [
				{ text:'新增', iconCls: 'icon-add', handler: function(){ App.saveOrUpdateDlg(false) }}
			, 	{ text:'编辑', iconCls: 'icon-edit', handler: function(){ App.saveOrUpdateDlg(true) }} , '-'
		  // , { iconCls: 'icon-remove', handler: function(){ Dept.remove() }}
		],
		columns:[[
			{field:'id', title:'编号', width:80, checkbox:true },
			{field:'deptName', title:'院系名称', width:120 },
			{field:'type', title:'类型', width:100 },
			{field:'remark', title:'备注', width:200 }
		]]
	};
	eui.initGrid(Dept.dataGridId, gridOptions);
};

Dept.saveOrUpdateAjaxOptions = {
	url 	: 	'/dept/saveOrUpdate',
	success	:	function(data, st, xhr) {
		if (data == 1) {
			eui.alert('操作成功：信息成功保存');
			eui.loadDataGridWithFormDataSelector(Dept.dataGridId, Dept.searchFormInputSelector, Dept.searchParamKey);
			$('#saveOrUpdateDlg').dialog('close');
		} else if (data == -1) {
			eui.alert('操作失败：平台管理员不允许进行此操作','warning');
		} else {
			eui.alert('操作失败，程序运行异常','error');
		}
	}
};


Dept.bindEvent = function() {
	$('#searchBtn').click(function(){
		eui.loadDataGridWithFormDataSelector(Dept.dataGridId, Dept.searchFormInputSelector, Dept.searchParamKey);
	});
	
	$('#clearBtn').click(function(){
		$(Dept.searchFormId).form('clear');
	});
	
	$('#saveOrUpdateBtn').click(function() {
		var isValid = $('#saveOrUpdateForm').form('validate');
		if (!isValid) {
			return;
		}
		Dept.saveOrUpdateAjaxOptions.postData = eui.findFormDataJsonObject('#saveOrUpdateForm :input');
		eui.commAjax(Dept.saveOrUpdateAjaxOptions);
	});
	
	$('#cancelBtn').click(function(){
		$('#saveOrUpdateDlg').dialog('close');
	});
	
};

$(function() {
	Dept.init();
	Dept.bindEvent();
});


Dept.saveOrUpdateDlg = function(isEdit) {
	$('#saveOrUpdateForm').form('clear');
	if (isEdit) {
		var rows = $(Dept.dataGridId).datagrid('getChecked');
		if (rows.length != 1) {
			eui.alert('请选择一条数据进行编辑','warning');
			return;
		}
		$('#saveOrUpdateForm').form('load', rows[0]);
	}
	$('#saveOrUpdateDlg').dialog('open');
};



