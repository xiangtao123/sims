/**
 * 初始化 ztree.
 * sampleTree.js
 */
var SampleTree = Class(object, //extends object
{
		defaultOptions  : {
			treeId 	: 'treeDemo'
		,	setting	: {
					data: {
						simpleData: {
							enable: true
						}
					}
			}
		,	loadDataUrl	: eui.basePath+'/group/loaddata'
		,	loadDataQueryParams	: {}
		}
	,	options 		: 	{}
	,	setLoadDataQueryParams : function(params) {
			this.options.loadDataQueryParams = params;
		}
	,	Create 		: function(opts)
		{ 
			var temp = {};
			this.options = $.extend(temp, this.defaultOptions, opts);
			eui.log.info(this.options);
			this.init();
		}
	,	zTreeObj		:	null
	,	getTreeObj  : function(){
			if (this.zTreeObj == null) {
				this.zTreeObj = $.fn.zTree.getZTreeObj(this.options.treeId);
			}
			return this.zTreeObj; 
		}
	,	getSelectedNodes :	function(){
			var selectArray = [];
			var treeObj = this.getTreeObj();
			if (treeObj) {
				selectArray = treeObj.getSelectedNodes();
			}
			return selectArray;
		}
	,	getCheckedNodes :	function(flag) {
			var selectArray = [];
			var treeObj = this.getTreeObj();
			if (treeObj) {
				selectArray = treeObj.getCheckedNodes(flag);
			}
			return selectArray;
		}
	,	checkAllNodes :	function(flag) {
			var treeObj = this.getTreeObj();
			if (treeObj) {
				treeObj.checkAllNodes(flag);
			}
		}
	,	checkNode :	function(node) {
			var treeObj = this.getTreeObj();
			if (treeObj) {
				treeObj.checkNode(node);
			}
		}
	,	getCheckedNodeIdStr :	function(flag, seperator) {
			var idArray = [];
			var checkedNodeArray = this.getCheckedNodes(flag);
			for (var i=0, len = checkedNodeArray.length; i < len; i++) {
				idArray.push(checkedNodeArray[i].id);
			}
			return idArray.join(seperator);
		}
	,	checkNodeByNodeIdStr : function(nodeIdStr){
			if (nodeIdStr) {
				var nodeIdArray = nodeIdStr.split(',');
				for (var i=0,len=nodeIdArray.length;i<len;i++) {
					var node = this.getNodeByNodeId(nodeIdArray[i]);
					if (node) {
						this.checkNode(node);
					}
				}
			}
		}
	,	init			: function() {
			var treeId = this.options.treeId;
			var setting = this.options.setting;
			var fnZtree = $.fn.zTree;
			$.messager.progress();	
			$.post(this.options.loadDataUrl, this.options.loadDataQueryParams, function(data) {
				$.messager.progress('close');	
				fnZtree.init( $('#'+treeId), setting, data);
			});
		}	
	,	reloadData	: function(){
			var treeObj = this.getTreeObj();
			if (treeObj) {
				treeObj.destroy();
			}
			this.init();
		}
	,	extendAll	: function(flag) {
			var treeObj = this.getTreeObj();
			if (treeObj) {
				treeObj.expandAll(flag);
			}
		}
	,	getNodeByNodeId	:	function(nodeId){
			var treeObj = this.getTreeObj();
			return treeObj.getNodeByParam("id", nodeId, null);
		}
});