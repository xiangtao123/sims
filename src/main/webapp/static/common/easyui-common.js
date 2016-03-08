
/**
 * easyui common tools.
 * 
 */
var easyuiCommon = function(){};
easyuiCommon.tabId = '#tt';

easyuiCommon.iframeMinHeight = 800;
easyuiCommon.iframeMaxHeight = 1200;

function dyniframesize(ifmId) {  
	var ifmBody =  $('#'+ifmId).contents().find("body");
	
	var mainheight = ifmBody.height() * 2;
	if (easyuiCommon.iframeMinHeight > mainheight) {
		mainheight = easyuiCommon.iframeMinHeight;
	} else if(easyuiCommon.iframeMaxHeight < mainheight) {
		mainheight = easyuiCommon.iframeMaxHeight;
	}
	
	$('#'+ifmId).height(mainheight); // auto height
	$('#'+ifmId).parent().width($('#'+ifmId).parent().width()); // auto width 
}; 

easyuiCommon.addTab = function(nodeId, title, targetUrl, htmlContent, closable){
	var content = '';
	if (htmlContent) {
		content = htmlContent;
	}

	if (targetUrl != null && targetUrl != '') {
		content += '<iframe class="tab-ifm" scrolling="auto" width="100%" frameborder="0"   src="'+(targetUrl.indexOf('http') > -1 ? targetUrl : $(basePath).val()+targetUrl) +'" id="ifm_'+nodeId+'" name="ifm_'+nodeId+'" onload="dyniframesize(\'ifm_'+nodeId+'\')"  ></iframe>';
	}
	
	if (closable == undefined) {
		closable = true;
	}
	if ($('#tt').tabs('exists',title)) {
		$('#tt').tabs('select',title);
	} else {
		$(easyuiCommon.tabId).tabs('add',{
			id: 'tab_panel_'+nodeId,
			title: title,
			content: content,
			closable: closable
		});
	}
};

/**
 * 监听浏览器窗口大小改变时，高度响应
 * 
 */
easyuiCommon.bindResize = function(){
	$(window).resize(function(){
		var selectTab = $('#tt').tabs('getSelected');
		var selectTabPanel = selectTab.panel('options');    // the corresponding tab object   
		setTimeout(function(){
			dyniframesize(selectTabPanel.id.replace('tab_panel_', 'ifm_'));
		}, 500);
	});
};

/**
 * 加载工作台内容
 * 
 */
easyuiCommon.homeLinkUrl = '/dispatcher/system/biz_operate_log';
easyuiCommon.homeTitle = '操作日志';
easyuiCommon.loadWorkSpace = function() {
	var nodeId = 'homeLink';
	var title = '我的首页';
	var targetUrl = easyuiCommon.homeLinkUrl;
	var htmlContent = '<div class="info" style="margin:20px;" >'+ easyuiCommon.homeTitle +'</div>'; // '<div class="info" style="margin:20px;" >参会人员报到统计</div>';
	easyuiCommon.addTab(nodeId, title, targetUrl, htmlContent, false);
};

$(function(){
	 easyuiCommon.bindResize();
	 easyuiCommon.loadWorkSpace();
});