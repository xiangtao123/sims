/**
 * 学生
 */
var App = function(){};
App.searchFormId = '#listForm';
App.searchFormInputSelector = App.searchFormId + ' :input';
App.searchParamKey  = null;
App.dataGridId = "#dataGrid";
App.init = function() {
	var gridOptions = {
		url: eui.basePath + '/student/list',
		method:'post',
		toolbar: [
		     { iconCls: 'icon-add', handler: function(){ App.saveOrUpdateDlg(false) }}
		   , { iconCls: 'icon-edit', handler: function(){ App.saveOrUpdateDlg(true) }} , '-'
		  // , { iconCls: 'icon-remove', handler: function(){ App.remove() }}
		],
		columns:[[
			{field:'id', title:'编号', width:80, checkbox:true },
			{field:'name', title:'姓名', width:120 },
			{field:'studentNo', title:'学号', width:100 },
			{field:'gender', title:'性别', width:220, 
				formatter : function(value, record) {
					var text = '';
					if (value == 1) {
						text = '男';
					} else if (value == 2) {
						text = '女';
					}
					return text;
				} 
			}
		]]
	};
	eui.initGrid(App.dataGridId, gridOptions);
};

App.saveOrUpdateAjaxOptions = {
	url 	: 	'/student/saveOrUpdate',
	success	:	function(data, st, xhr) {
		if (data == 1) {
			eui.alert('操作成功：信息成功保存');
			eui.loadDataGridWithFormDataSelector(App.dataGridId, App.searchFormInputSelector, App.searchParamKey);
			$('#saveOrUpdateDlg').dialog('close');
		} else if (data == -1) {
			eui.alert('操作失败：平台管理员不允许进行此操作','warning');
		} else {
			eui.alert('操作失败，程序运行异常','error');
		}
	}
};


App.bindEvent = function() {
	$('#searchBtn').click(function(){
		eui.loadDataGridWithFormDataSelector(App.dataGridId, App.searchFormInputSelector, App.searchParamKey);
	});
	
	$('#clearBtn').click(function(){
		$(App.searchFormId).form('clear');
	});
	
	$('#saveOrUpdateBtn').click(function() {
		var isValid = $('#saveOrUpdateForm').form('validate');
		if (!isValid) {
			return;
		}
		App.saveOrUpdateAjaxOptions.postData = eui.findFormDataJsonObject('#saveOrUpdateForm :input');
		eui.commAjax(App.saveOrUpdateAjaxOptions);
	});
	
	$('#cancelBtn').click(function(){
		$('#saveOrUpdateDlg').dialog('close');
	});
	
};

$(function() {
	App.init();
	App.bindEvent();
});


App.saveOrUpdateDlg = function(isEdit) {
	$('#saveOrUpdateForm').form('clear');
	if (isEdit) {
		var rows = $(App.dataGridId).datagrid('getChecked');
		if (rows.length != 1) {
			eui.alert('请选择一条数据进行编辑','warning');
			return;
		}
		$('#saveOrUpdateForm').form('load', record);
	}
	$('#saveOrUpdateDlg').dialog('open');
};

