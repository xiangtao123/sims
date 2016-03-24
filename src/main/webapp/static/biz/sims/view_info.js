/**
 * 个人信息
 */
var App = function(){};
App.saveOrUpdateAjaxOptions = {
	url 	: 	'/student/registerAndBind',
	success	:	function(data, st, xhr) {
		if (data == 1) {
			eui.alert('操作成功：信息成功保存');
			eui.loadDataGridWithFormDataSelector(App.dataGridId, App.searchFormInputSelector, App.searchParamKey);
		} else if (data == -1) {
			eui.alert('操作失败：平台管理员不允许进行此操作','warning');
		} else {
			eui.alert('操作失败，程序运行异常','error');
		}
	}
};

App.init = function() {
	var convert = function(record) {
		if (record.birthDay) {
			record.birthDay = new Date(record.birthDay).format('yyyy-MM-dd');
		}
		if (record.auditTime) {
			record.auditTime = new Date(record.auditTime).format('yyyy-MM-dd hh:mm:ss');
		}
		if (record.enrollDate) {
			record.enrollDate = new Date(record.enrollDate).format('yyyy-MM-dd');
		}
		if (record.graduationDate) {
			record.graduationDateStr = new Date(record.graduationDate).format('yyyy-MM-dd');
		}
		if (record.degreeDate) {
			record.degreeDateStr = new Date(record.degreeDate).format('yyyy-MM-dd');
		}
		if (record.registerTime) {
			record.registerTime = new Date(record.registerTime).format('yyyy-MM-dd hh:mm:ss');
		}
	}
	
	if ('' != studentVo) {
		var record = $.parseJSON(studentVo);
		convert(record);
		$('#saveOrUpdateForm').form('load', record);
	}
};

App.bindEvent = function() {
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
};

$(function() {
	App.init();
	App.bindEvent();
});
