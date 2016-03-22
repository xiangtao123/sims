/**
 * 专业
 */
var App = function(){};
App.searchFormId = '#listForm';
App.searchFormInputSelector = App.searchFormId + ' :input';
App.searchParamKey  = null;
App.dataGridId = "#dataGrid";
App.init = function() {
	var gridOptions = {
		url: eui.basePath + '/speciality/list',
		method:'post',
		toolbar: [
		          { text:'新增', iconCls: 'icon-add', handler: function(){ App.saveOrUpdateDlg(false) }}
		        , { text:'编辑', iconCls: 'icon-edit', handler: function(){ App.saveOrUpdateDlg(true) }} , '-'
		  // , { iconCls: 'icon-remove', handler: function(){ App.remove() }}
		],
		columns:[[
			{field:'id', title:'编号', width:80, checkbox:true },
			{field:'deptName', title:'院系名称', width:120, 
				formatter : function(value, record) {
					if (record.dept && record.dept.deptName) {
						return record.dept.deptName;
					}
					return '';
				} 
			},
			{field:'name', title:'专业名称', width:120 },
			{field:'code', title:'专业代码', width:120 },
			{field:'length', title:'修业年限', width:120 },
			{field:'degreeType', title:'学位门类', width:120 },
			{field:'requireCredit', title:'必修学分', width:120 },
			{field:'optionCredit', title:'选修学分', width:120 },
			
		]]
	};
	eui.initGrid(App.dataGridId, gridOptions);
};

App.saveOrUpdateAjaxOptions = {
	url 	: 	'/speciality/saveOrUpdate',
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
		var record = rows[0];
		if (record.dept) {
			record.deptId = record.dept.id;
		}
		$('#saveOrUpdateForm').form('load', record);
	}
	$('#saveOrUpdateDlg').dialog('open');
};



