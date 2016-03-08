/**
 * Created by young on 14-10-9.
 */

function autoExpand(treeId, treeNode) {
    if (treeNode) {
        expandNodes(treeId, treeNode.children);
    } else {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        expandNodes(treeId, zTree.getNodes());
    }
}
function expandNodes(treeId, nodes) {
    if (!nodes) return;
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    for (var i = 0, l = nodes.length; i < l; i++) {
        zTree.expandNode(nodes[i], true, false, false);
        if (nodes[i].isParent && nodes[i].zAsync) {
            expandNodes(treeId, nodes[i].children);
        }
    }
}

function autoLoad(treeId, treeNode) {
    if (treeNode) {
        loadNodes(treeId, treeNode.children);
    } else {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        loadNodes(treeId, zTree.getNodes());
    }
}
function loadNodes(treeId, nodes) {
    if (!nodes) return;
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    for (var i = 0, l = nodes.length; i < l; i++) {
        if (nodes[i].isParent && nodes[i].zAsync) {
            loadNodes(treeId, nodes[i].children);
        } else {
            zTree.reAsyncChildNodes(nodes[i], "refresh", true);
        }
    }
}

$.fn.zTree.autoLoad = autoLoad;
$.fn.zTree.autoExpand = autoExpand;
