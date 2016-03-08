/**
 * 初始化 ztree.
 * InputTree.js
 */
var InputTree = Class(SampleTree, //extends object
{
	
		options	:	{
				inputId			: 'inputTreeSelector'
			,  contentPanelId	: 'contentPanel'	
		}
	,	Create 		: function(opts)
		{ 
			var temp = {};
			this.options = $.extend(temp, this.defaultOptions, opts);
			eui.log.info(this.options);
			this.init();
		}
	,	showPanel	: function(){
			var textboxObj = $("#"+this.options.inputId).textbox('textbox');
			var textboxObjOffset = textboxObj.offset();
			$("#"+this.options.contentPanelId).css({zIndex:9814,left:textboxObjOffset.left + "px", top:textboxObjOffset.top + textboxObj.outerHeight() + "px"}).slideDown("fast");
			
			var thisObj = this;
			$("body").bind("mousedown", function(event){
				thisObj.onBodyDown(event);
			});
		}	
	,	hidePanel	: function() {
			$("#"+this.options.contentPanelId).fadeOut("fast");
			$("body").unbind("mousedown");
		}
	,	onBodyDown	:	function(event) {
			var inTextboxArea = $(event.target).hasClass('icon-search');
			var isContentPanel = event.target.id == this.options.contentPanelId || $(event.target).parents("#"+this.options.contentPanelId).length > 0;
			if (! (isContentPanel || inTextboxArea) ) {
				this.hidePanel();
			}
		}
	,	setValue	:	function(treeNode) {
			$('#'+this.options.valueFieldId).val(treeNode.id);
			$('#'+this.options.inputId).textbox('setValue',treeNode.name);
		}
	,	selectNode	:	function(nodeId){
			var treeObj = this.getTreeObj();
			var node = this.getNodeByNodeId(nodeId);
			if (node != null) {
				treeObj.selectNode(node);
				this.setValue(node);
			}
		}
	,	cancelSelectedNode	:	function(){
			var treeObj = this.getTreeObj();
			treeObj.cancelSelectedNode();
			this.setValue({'id':'','name':''});
		}
});