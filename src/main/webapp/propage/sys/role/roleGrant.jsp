<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var resourceTree;
	$(function() {
		/* parent.$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		}); */

		resourceTree = $('#resourceTree').tree({
			url : 'resourceAction!getAllTrees.action',
			parentField : 'pid',
			checkbox : true,
			cascadeCheck : false,
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
				$.ajax({
					url : 'roleAction!getRoleRes.action',
					data : {
						id : $('#role_id').val()
					},
					dataType : 'json',
					success : function(r) {
						if (r.length > 0) {
							for ( var i = 0; i < r.length; i++) {
								if (resourceTree.tree('find', r[i])) {
									resourceTree.tree('check', resourceTree.tree('find', r[i]).target);
								}
							}
						}
					}
				});
			}
			
		});
		
	});

	function checkAll() {
		var nodes = resourceTree.tree('getChecked', 'unchecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceTree.tree('check', nodes[i].target);
			}
		}
	}
	function uncheckAll() {
		var nodes = resourceTree.tree('getChecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceTree.tree('uncheck', nodes[i].target);
			}
		}
	}
	function checkInverse() {
		var unchecknodes = resourceTree.tree('getChecked', 'unchecked');
		var checknodes = resourceTree.tree('getChecked');
		if (unchecknodes && unchecknodes.length > 0) {
			for ( var i = 0; i < unchecknodes.length; i++) {
				resourceTree.tree('check', unchecknodes[i].target);
			}
		}
		if (checknodes && checknodes.length > 0) {
			for ( var i = 0; i < checknodes.length; i++) {
				resourceTree.tree('uncheck', checknodes[i].target);
			}
		}
	}
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west'" style="width: 300px; padding: 1px;">
		<div class="well well-small">
			<form id="form_roleGrant" method="post">
				<input id="role_id" name="id" type="hidden" class="span2" readonly="readonly">
				<ul id="resourceTree"></ul>
				<input id="resourceIds" name="resourceIds" type="hidden" />
			</form>
		</div>
	</div>
	<div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
		<div class="well well-small">
			<div id="role_name"></div>
			<div id="role_remark"></div>
		</div>
		<div>
			<button onclick="checkAll();">全选</button>
			<br /> <br />
			<button onclick="checkInverse();">反选</button>
			<br /> <br />
			<button onclick="uncheckAll();">取消</button>
		</div>
	</div>
</div>