
/**
 * easyui common tools.
 * 
 */

String.prototype.replaceAll = function(regexp,replaceValue){   
	return this.replace(new RegExp(regexp,"gm"),replaceValue);   
};

var eui = function(){};
eui.basePath = '/demo';

eui.isArray = function(obj) {   
	return Object.prototype.toString.call(obj) === '[object Array]';    
};  


/**
 * 默认配置 data grid list 
 */
eui.defaultGridOptions = {
			rownumbers:false
		,	idField:'id'
		,	pagination:true
		,	singleSelect:false
		,	collapsible:true
		,	method:'post'
		,	fitColumns:false
		,	striped:true
		,	nowrap:false
		,	rownumbers:true
		,	pageSize:10
		,	pageList:[10, 20]
};

/**
 * 初始化data grid list
 */
eui.initGrid = function(dataGridId, gridOptions){
	var temp = {};
	var setting = $.extend(temp, eui.defaultGridOptions, gridOptions);
	$(dataGridId).datagrid(setting);
};
/**
 * 初始化 combobox grid list
 * 
 */
eui.initComboboxGrid = function(dataGridId, gridOptions){
	var temp = {};
	var setting = $.extend(temp, eui.defaultGridOptions, gridOptions);
	$(dataGridId).combogrid(setting);
};
/**
 *  查询列表 参数json对象
 *  
 * @param dataGridId 列表jQuery 选择器eg. '#dgid'
 * @param paramJsonObj 搜索条件json对象eg. {name:'abc'}
 */
eui.loadDataGridWithParamJsonObj = function(dataGridId, paramJsonObj){
		$(dataGridId).datagrid('load', paramJsonObj);
};

/**
 *  查询列表 表单域jQuery 选择器
 *  
 * @param dataGridId 列表jQuery 选择器eg. '#dgid'
 * @param paramJsonObj 搜索条件json对象eg. {name:'abc'}
 * @param paramKey server-side eg . reqeust.getParameter('paramKey');
 */
eui.loadDataGridWithFormDataSelector = function(dataGridId, fromDataSelector, paramKey){
	 var paramJsonObj = eui.findFormDataJsonObject(fromDataSelector);
	 var paramConditon = {};
	 if (paramKey != undefined && paramKey != null && paramKey != ''){
		 var paramJsonStr = eui.josnStringify(paramJsonObj); 
		 paramConditon[paramKey] = paramJsonStr;
	 } else {
		 paramConditon = paramJsonObj;
	 }
	 $(dataGridId).datagrid('clearChecked');
	 eui.loadDataGridWithParamJsonObj(dataGridId, paramConditon);
};

/**
 *  获取from 表单数据
 * @param fromSelector
 * @returns {___anonymous1335_1337}
 */
eui.findFormDataJsonObject = function(fromDataSelector){
	var formData = { };
	var input = $(fromDataSelector);
	for (var i = 0; i < input.length; i++){
		if (input[i].name != undefined && input[i].name != ""){
			if (input[i].type == 'radio'){
				if (input[i].checked)
					formData[input[i].name] = input[i].value;
			} else {
				if (input[i].type == 'checkbox'){
					if (input[i].checked){
						var fd  = formData[input[i].name];
						if (fd == undefined ){
							formData[input[i].name] = input[i].value;
						}else{
							formData[input[i].name] = fd + "," + input[i].value;
						}
					}
				} else {
					var fd  = formData[input[i].name];
					if (fd == undefined) {
						formData[input[i].name] = input[i].value;
					}else {
						formData[input[i].name] = fd + "," + input[i].value;
					}
				}
			} 
		}
	}
	return formData;
};

/**
 * 序列化：将对象转成可传输字符传
 * @param fromSelector
 * @returns stringify
 */
eui.findFormDataJsonStr = function(fromSelector){
	return JSON.stringify(this.findFormDataJsonObject(fromSelector));
};
/**
 * 序列化：将对象转成可传输字符传
 * @param objectData
 * @returns
 */
eui.josnStringify = function(objectData){
	return JSON.stringify(objectData);
};

/**
 * 
 * @param ajaxOptions{
 * 		url		:	''							//请求地址
 * 		mtype	:	'POST'						//method type : get or post(defualt)
 * 		postData	:	{}							//post data params请求参数json data type.
 * 		success :	function(data,st, xhr){}	//callback function sucess.
 * 		error	:	function(xhr,st,err){}		//callback function error.
 * }
 */
eui.commAjax = function(ajaxOptions){
	eui.log.info(ajaxOptions);
	if (ajaxOptions.showProgress) {
		$.messager.progress();
	}
	$.ajax({
		url:eui.basePath+ajaxOptions.url,
		type:ajaxOptions.mtype == undefined ? 'POST' : ajaxOptions.mtype,
		dataType: ajaxOptions.dataType == undefined ? 'json' : ajaxOptions.dataType ,
		data: ajaxOptions.postData == undefined ? {} : ajaxOptions.postData ,
		async:ajaxOptions.async == undefined ? true : ajaxOptions.async,
		traditional:ajaxOptions.traditional == undefined ? false : ajaxOptions.traditional,
		success:function(data,st, xhr) {
			if (ajaxOptions.showProgress) {
				$.messager.progress('close');	
			}
			if ($.isFunction(ajaxOptions.success)) {
				ajaxOptions.success.call(ajaxOptions,data, st, xhr);
			} else{
				
			}
			xhr=null;
		},
		error:function(xhr,st,err){
			if (ajaxOptions.showProgress) {
				$.messager.progress('close');	
			}
			if ($.isFunction(ajaxOptions.error)) {
				ajaxOptions.error.call(ajaxOptions,xhr,st,err);
			} else{
				
			}
			xhr=null;
		}
	});
};
/**
 * 新开页面
 * @param url
 */
eui.openWindow = function(url){
	window.open(url,'_blank',"location=yes,menubar=no,width="+ (screen.availWidth - 0) +',height='+ (screen.availHeight-0) +",resizable=yes,scrollbars=yes,status=no,toolbar=no" );
},
/**
 * 下载请求
 * @param url
 */

eui.down = function(url) {
	var downloadform = $('#downloadform');
	if (downloadform.length <= 0)
		$('body').append('<form id="downloadform" action="" method="post" target="downloadiframe" ></form><iframe id="downloadiframe" name="downloadiframe" style="display:none;"></iframe>');
	$('#downloadform').attr('action',url);
	document.getElementById('downloadform').submit();
//	window.open(url,'_blank',"location=no,menubar=no,width=1,height=1,resizable=no,scrollbars=no,status=no,toolbar=no" );
};

eui.downLoadFormFieldTpl = '<input type="hidden" name="{name}" />';
/**
 * 
 * @param url : form-action
 * @param fieldAray : [{'key':'name1', 'value':'value1'}...]
 */
eui.downLoadForm = function(url, fieldAray) {
	var downloadform = $('#downloadform');
	if (downloadform.length <= 0)
		$('body').append('<form id="downloadform" action="" method="post" target="downloadiframe" ></form><iframe id="downloadiframe" name="downloadiframe" style="display:none;"></iframe>');
	 
	$('#downloadform').html('').attr('action',url).hide();
	if (fieldAray && fieldAray.length > 0) {
		for (var i = 0, len= fieldAray.length; i < len; i++) {
			var inputObj = $('<input type="hidden" />') ;
			inputObj.attr('name', fieldAray[i].key);
			inputObj.val(fieldAray[i].value);
			$('#downloadform').append(inputObj);
		}
	}
	
	document.getElementById('downloadform').submit();
//	window.open(url,'_blank',"location=no,menubar=no,width=1,height=1,resizable=no,scrollbars=no,status=no,toolbar=no" );
};


/**
 * color : rgb(r,g,b) to hex
 * @param rgb
 * @returns
 */
eui.RGBToHex = function (rgb) {
	var hex = [
		rgb.r.toString(16),
		rgb.g.toString(16),
		rgb.b.toString(16)
	];
	$.each(hex, function (nr, val) {
		if (val.length == 1) {
			hex[nr] = '0' + val;
		}
	});
	return hex.join('');
};
/**
 * 导出CSV
 * @param formSelector 查询条件的选择器
 * @param bizKey 业务标识
 * @param formData 查询条件json字符串
 */
eui.exportCSV = function(formSelector, bizKey, formData){
	if (!confirm('您确定导出符合搜索条件的数据吗？'))
		return;
	var params = "{}";
	if (formSelector != null && formSelector != '')
		params = sysutil.findFormData(formSelector);
	if (formData != undefined && formData != null && formData != '')
		params = formData;
	var url = eui.basePath+"/export/downloadCSV?params="+params+"&bizKey="+bizKey;
//	console.log(url);
	url = encodeURI( encodeURI( url ) );
	this.down(url);
};
/**
 * 初始化下拉框
 * @param targetSelector select input selector ,eg.#selid or .selclass
 * @param textArray		option's text array
 * @param defaultText	default text
 */
eui.initSelect = function(targetSelector, textArray, defaultText){
	var opts = this.selectorTpl.replaceAll(this.selectorTplValue,'').replaceAll(this.selectorTplText, defaultText);
	for (var i = 0; i < textArray.length; i++){
		opts += this.selectorTpl.replaceAll(this.selectorTplValue,i).replaceAll(this.selectorTplText, textArray[i]);
	}
	$(targetSelector).html(opts);
};
/**
 * 初始化下拉框
 * @param targetSelector select input selector ,eg.#selid or .selclass
 * @param textArray		option's [{text:'',value:''}]
 * @param defaultText	default text
 */
eui.initSelectArray = function(targetSelector, textArray, defaultText){
	var opts = this.selectorTpl.replaceAll(this.selectorTplValue,'').replaceAll(this.selectorTplText, defaultText);
	for (var i = 0; i < textArray.length; i++){
		opts += this.selectorTpl.replaceAll(this.selectorTplValue,textArray[i].value).replaceAll(this.selectorTplText, textArray[i].text);
	}
	$(targetSelector).html(opts);
};
/**
 * 当浏览器窗口改变时，gridWith自适应
 */
eui.getGridWith = function(containerSelector){
	if (undefined == containerSelector)
		return $(this.gridWithSelector).width() * this.widthPercent;
	else
		return $(containerSelector).width() * this.widthPercent * 0.94;
};

/**
 * 对话框 alert
 */
eui.alert = function(content, icoClass, title){
	$.messager.alert(title ? title : '操作提示', content, icoClass ? icoClass : 'info');
};

/**
 *  对话框 confirm
 * @param content
 * @param callback
 */
eui.confirm = function(content, callback){
	$.messager.confirm('操作确认', content, callback);
};

/**
 * 绑定热键功能
 * 
 */
eui.bindHotKey = function(){
	$(document).keydown(function(e){
		if ($('.window-mask:visible').length > 0) {
			return;
		} else {
			$.each($('.easyui-hotkey:visible'), function(index, obj){
				var hkObj = $(obj);
				var keyCode = hkObj.data('hotkey-keycode');
				if (e.keyCode == keyCode) {
					hkObj.focus();
//					var event = hkObj.data('hotkey-event');
//					hkObj.trigger(event);
					return;
				}
			});
		}
	});
};

/**
 *  logutil
 *    
 *  trun on/off : isDebug
 *  method : [info, error, debug]
 *   
 */
eui.log = {
		isDebug 	: false
	,	isalert 	: document.all ? true : false
	,  print	: function(msg, level){
			if (!eui.log.isDebug){
				return;
			}
			if (eui.log.isalert) {
				$.messager.alert('日志记录：'+level, msg, (level ? level : 'warning') );
			} else {
				if (level === 'info'){
					console.info(msg);
				} else if (level === 'error'){
					console.error(msg);
				} else {
					console.log(msg);
				}
			}
		}
	, 	info	:	function(msg){
			eui.log.print(msg, 'info');
		}
	,	error	:	function(){
			eui.log.print(msg, 'error');
		}
	,	debug	:	function(){
			eui.log.print(msg);
		}
};
$(function(){
	eui.bindHotKey();
});
