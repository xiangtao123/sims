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
		fitColumns:false,
		toolbar: [
		     { text:'新增', iconCls: 'icon-add', handler: function(){ App.saveOrUpdateDlg(false) }}
		   , { text:'编辑', iconCls: 'icon-edit', handler: function(){ App.saveOrUpdateDlg(true) }} , '-'
		   , { text:'审核', iconCls: 'icon-ok', handler: function(){ App.auditDlg() }}
		],
		columns:[[
			{field:'id', title:'编号', width:80, checkbox:true },
			{field:'name', title:'姓名', width:80 },
			{field:'idNo', title:'身份证号', width:160 },
			{field:'studentNo', title:'学号', width:160 },
			{field:'gender', title:'性别', width:60, 
				formatter : function(value, record) {
					var text = '';
					if (value == 1) {
						text = '男';
					} else if (value == 2) {
						text = '女';
					}
					return text;
				} 
			},
			{field:'birthDay', title:'出生日期', width:100 },
			{field:'nation', title:'民族', width:80 },
			{field:'mobile', title:'手机号', width:100 },
			{field:'email', title:'邮箱', width:130 },
			{field:'politicalStatus', title:'政治面貌', width:60, 
				formatter : function(value, record) {
					var text = '';
					if (value == 1) {
						text = '团员';
					} else if (value == 2) {
						text = '党员';
					}
					return text;
				} 
			},
			{field:'homeAddr', title:'家庭住址', width:120 },
			{field:'enrollDate', title:'入学日期', width:100, 
				formatter : function(value, record) {
					if (value) {
						return new Date(value).format("yyyy-MM-dd");
					}
				} 
			},
			{field:'graduationDate', title:'毕业日期', width:100, 
				formatter : function(value, record) {
					if (value) {
						return new Date(value).format("yyyy-MM-dd");
					}
				}
			},
			{field:'degreeDate', title:'授予学位日期', width:100, 
				formatter : function(value, record) {
					if (value) {
						return new Date(value).format("yyyy-MM-dd");
					}
				}
			},
			{field:'registerTime', title:'注册时间', width:150, 
				formatter : function(value, record) {
					if (value) {
						return new Date(value).format("yyyy-MM-dd hh:mm:ss");
					}
				}
			},
			{field:'auditState', title:'审核状态', width:60, 
				formatter : function(value, record) {
					var text = '';
					if (value == 1) {
						text = '通过';
					} else if (value == 2) {
						text = '不通过';
					}
					return text;
				} 
			},
			{field:'auditRemark', title:'审核意见', width:120 },
			{field:'auditTime', title:'审核时间', width:150, 
				formatter : function(value, record) {
					if (value) {
						return new Date(value).format("yyyy-MM-dd hh:mm:ss");
					}
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

App.auditAjaxOptions = {
	url 	: 	'/student/updateStudent',
	success	:	function(data, st, xhr) {
		if (data == 1) {
			eui.alert('操作成功：信息成功保存');
			eui.loadDataGridWithFormDataSelector(App.dataGridId, App.searchFormInputSelector, App.searchParamKey);
			$('#auditDlg').dialog('close');
		} else if (data == -1) {
			eui.alert('操作失败：参数无效','warning');
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
		var formData = eui.findFormDataJsonObject('#saveOrUpdateForm :input');
		App.saveOrUpdateAjaxOptions.postData = formData;
		eui.commAjax(App.saveOrUpdateAjaxOptions);
	});
	
	$('#cancelBtn').click(function(){
		$('#saveOrUpdateDlg').dialog('close');
	});
	
	$('#auditSaveBtn').click(function() {
		var isValid = $('#auditForm').form('validate');
		if (!isValid) {
			return;
		}
		
		var rows = $(App.dataGridId).datagrid('getChecked');
		var idArray = [];
		for (var i = 0, len=rows.length; i < len; i++) {
			idArray.push(rows[i].id);
		}
		var studentIdStr = idArray.join(',');
		var formData = eui.findFormDataJsonObject('#auditForm :input');
		formData['studentIds'] = studentIdStr;
		App.auditAjaxOptions.postData = formData;
		eui.commAjax(App.auditAjaxOptions);
	});
	
	$('#auditCancelBtn').click(function(){
		$('#auditDlg').dialog('close');
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
		$('#saveOrUpdateForm').form('load', rows[0]);
	}
	$('#saveOrUpdateDlg').dialog('open');
};

App.auditDlg = function () {
	var rows = $(App.dataGridId).datagrid('getChecked');
	if (rows.length < 1) {
		eui.alert('操作提示：请至少选择一条记录','warning');
		return;
	}
	$('#auditForm').form('clear');
	$('#auditDlg').dialog('open');
};



