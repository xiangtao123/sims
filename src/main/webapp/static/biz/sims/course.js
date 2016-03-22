/**
 * 课程
 */
var App = function(){};
App.searchFormId = '#listForm';
App.searchFormInputSelector = App.searchFormId + ' :input';
App.searchParamKey  = null;
App.dataGridId = "#dataGrid";
App.init = function() {
	var gridOptions = {
		url: eui.basePath + '/course/list',
		method:'post',
		toolbar: [
		         { text:'新增', iconCls: 'icon-add', handler: function(){ App.saveOrUpdateDlg(false) }}
		       , { text:'编辑', iconCls: 'icon-edit', handler: function(){ App.saveOrUpdateDlg(true) }} , '-'
		  // , { iconCls: 'icon-remove', handler: function(){ App.remove() }}
		],
		columns:[[
			{field:'id', title:'编号', width:80, checkbox:true },
			{field:'name', title:'课程名称', width:120 },
			{field:'credit', title:'学分', width:60 },
			{field:'material', title:'教材', width:220 },
			{field:'specialitys', title:'所属专业', width:220, 
				formatter : function(value, record) {
					var specialityName = '';
					if (record.specialitys) {
						var specialitys = record.specialitys;
						if (specialitys.length > 0) {
							for (var i = 0, len=specialitys.length; i < len; i++) {
								specialityName += ',' + record.specialitys[i].name
							}
							if (specialityName != '') {
								specialityName = specialityName.substr(1);
							}
						}
					}
					return specialityName;
				} 
			}
		]]
	};
	eui.initGrid(App.dataGridId, gridOptions);
};

App.saveOrUpdateAjaxOptions = {
	url 	: 	'/course/saveOrUpdate',
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
		var specialityIds = '';
		if (record.specialitys) {
			var specialitys = record.specialitys;
			if (specialitys.length > 0) {
				for (var i = 0, len=specialitys.length; i < len; i++) {
					specialityIds += ',' + record.specialitys[i].id
				}
				if (specialityIds != '') {
					record.specialityIds = specialityIds.substring(1);
				}
			}
		}
		$('#saveOrUpdateForm').form('load', record);
	}
	$('#saveOrUpdateDlg').dialog('open');
};

