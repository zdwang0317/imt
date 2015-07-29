<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#admin_yhgl_datagrid').datagrid({
			url : 'wipAction!datagrid.action',
			fit : true,
			//fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				checkbox : true
			}, {
				field : 'lid',
				title : 'Lot Id',
				width : 100,

			} ] ],
			columns : [ [ {
				field : 'cpn',
				title : 'CPN',
				width : 150,
			}, {
				field : 'ipn',
				title : 'IPN',
				width : 150,
			}, {
				field : 'wid',
				title : 'Wafer Id',
				width : 300,
			}, {
				field : 'startDate',
				title : 'StartDate',
				width : 150,
			}] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			},{
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			},{
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					_userEdit();
				}
			},{
				text : '密码变更',
				iconCls : 'icon-pwedit',
				handler : function() {
					_userEdit();
				}
			} ]
		});
	});
	
	function searchFun() {
		$('#admin_yhgl_datagrid').datagrid('load', serializeObject($('#admin_yhgl_searchForm')));
	}
	function clearFun() {
		$('#admin_yhgl_layout input[name=name]').val('');
		$('#admin_yhgl_datagrid').datagrid('load', {});
	}
</script>
<div id="admin_yhgl_layout" class="easyui-layout"
	data-options="fit:true,border:false"
	style="margin-top: 1px; margin-left: 1px">
	<!-- <div data-options="region:'north',title:'查询条件'" style="height: 100px;">
		<form id="admin_yhgl_searchForm">
			LotId(可模糊查询)：<input name="lid" /><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearFun();">清空</a>
		</form>
	</div> -->
	<div data-options="region:'center',title:'WIP INFO'">
		<table id="admin_yhgl_datagrid"></table>
	</div>
	<!-- <div data-options="region:'west',title:'部门',split:true" style="width:150px">
		<ul id="user_dept_tree"></ul>
	</div> -->
</div>

