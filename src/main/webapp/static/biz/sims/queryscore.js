/**
 * 成绩查询
 */
var App = function(){};
App.dataGridId = "#dataGrid";
App.init = function() {
	var gridOptions = {
		title:'成绩列表',
		url: eui.basePath + '/query_score/list',
		method:'post',
		fitColumns:false,
		toolbar: [ ],
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

$(function() {
	App.init();
});
