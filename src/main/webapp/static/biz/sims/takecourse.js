/**
 * 在线选课
 */
var App = function(){};
App.searchFormId = '#listForm';
App.searchFormInputSelector = App.searchFormId + ' :input';
App.searchParamKey  = null;
App.dataGridId = "#dataGrid";
App.init = function() {
	var gridOptions = {
		url: eui.basePath + '/take_course/list',
		method:'post',
		fitColumns:false,
		toolbar: [
		     { text:'选课', iconCls: 'icon-add', handler: function(){ App.takeCourseDlg(false) }}
		   , { text:'成绩录入', iconCls: 'icon-edit', handler: function(){ App.saveGradeDlg(true) }}
		],
		columns:[[
			{field:'id', title:'编号', width:80, checkbox:true },
			{field:'studentName', title:'姓名', width:80, 
				formatter : function(value, record) {
					return record.student.name;
				} 
			},
			{field:'studentNo', title:'学号', width:160, 
				formatter : function(value, record) {
					return record.student.studentNo;
				} 
			},
			{field:'courseName', title:'课程名称', width:160, 
				formatter : function(value, record) {
					return record.course.name;
				} 
			},
			{field:'courseCredit', title:'课程学分', width:60, 
				formatter : function(value, record) {
					return record.course.credit;
				} 
			},
			{field:'grade', title:'考试成绩', width:100 },
			{field:'updateTime', title:'更新时间', width:150, 
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

App.takeCourseAjaxOptions = {
	url 	: 	'/take_course/takeCourse',
	success	:	function(data, st, xhr) {
		if (data == 1) {
			eui.alert('操作成功：信息成功保存');
			eui.loadDataGridWithFormDataSelector(App.dataGridId, App.searchFormInputSelector, App.searchParamKey);
			$('#takeCourseDlg').dialog('close');
		} else if (data == -1) {
			eui.alert('操作失败：平台管理员不允许进行此操作','warning');
		} else {
			eui.alert('操作失败，程序运行异常','error');
		}
	}
};

App.saveGradeAjaxOptions = {
	url 	: 	'/take_course/saveGrade',
	success	:	function(data, st, xhr) {
		if (data == 1) {
			eui.alert('操作成功：信息成功保存');
			eui.loadDataGridWithFormDataSelector(App.dataGridId, App.searchFormInputSelector, App.searchParamKey);
			$('#saveGradeDlg').dialog('close');
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
	
	$('#takeCourseBtn').click(function() {
		var isValid = $('#takeCourseForm').form('validate');
		if (!isValid) {
			return;
		}
		var formData = eui.findFormDataJsonObject('#takeCourseForm :input');
		App.takeCourseAjaxOptions.postData = formData;
		eui.commAjax(App.takeCourseAjaxOptions);
	});
	
	$('#cancelBtn').click(function(){
		$('#takeCourseDlg').dialog('close');
	});
	
	$('#saveGradeBtn').click(function() {
		var isValid = $('#saveGradeForm').form('validate');
		if (!isValid) {
			return;
		}
		
		var rows = $(App.dataGridId).datagrid('getChecked');
		var formData = eui.findFormDataJsonObject('#saveGradeForm :input');
		formData['studentCourseId'] = rows[0].id;
		App.saveGradeAjaxOptions.postData = formData;
		eui.commAjax(App.saveGradeAjaxOptions);
	});
	
	$('#saveGradeCancelBtn').click(function(){
		$('#saveGradeDlg').dialog('close');
	});
	
	
};

$(function() {
	App.init();
	App.bindEvent();
});


App.takeCourseDlg = function() {
	$('#takeCourseForm').form('clear');
	$('#takeCourseDlg').dialog('open');
};

App.saveGradeDlg = function () {
	var rows = $(App.dataGridId).datagrid('getChecked');
	if (rows.length != 1) {
		eui.alert('操作提示：请选择一条记录','warning');
		return;
	}
	$('#saveGradeForm').form('clear');
	$('#saveGradeDlg').dialog('open');
};



