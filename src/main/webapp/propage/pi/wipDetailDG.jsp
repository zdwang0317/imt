<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#pi_wip_detail_dg').datagrid({
			url : 'wipAction!datagridOfDetail.action',
			fit : true,
			//fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			frozenColumns : [ [ /* {
				field : 'id',
				title : '编号',
				checkbox : true
			}, */ {
				field : 'lid',
				title : 'Lot Id',
				width : 100,
			}, {
				field : 'wid',
				title : 'Wafer Id',
				width : 80,
			} ] ],
			columns : [ [ {
				field : 'pn',
				title : 'PartNo',
				width : 150,
			},{
				field : 'cpn',
				title : 'CPN',
				width : 150,
			}, {
				field : 'ipn',
				title : 'IPN',
				width : 150,
			}, {
				field : 'startDate',
				title : 'StartDate',
				width : 150,
			}, {
				field : 'stage',
				title : 'Stage',
				width : 150,
			}, {
				field : 'status',
				title : 'Status',
				width : 150,

			}, {
				field : 'foTime',
				title : 'FoTime',
				width : 150,

			}, {
				field : 'remLayer',
				title : 'RemLayer',
				width : 150,

			}, {
				field : 'holdDate',
				title : 'HoldDate',
				width : 150,

			}, {
				field : 'holdRemark',
				title : 'HoldRemark',
				width : 150,

			}, {
				field : 'location',
				title : 'Location',
				width : 150,

			}, {
				field : 'sendDate',
				title : 'SendDate',
				width : 150,

			}, {
				field : 'firm',
				title : 'Firm',
				width : 150,
			}, {
				field : 'productNo',
				title : 'Product No',
				width : 150,
			} , {
				field : 'tpnFlow',
				title : 'TPN FLOW',
				width : 150,
			}  ] ]
		});
	});
	
	function pi_wip_detail_searchForm_searchFun() {
		$('#pi_wip_detail_dg').datagrid('load', serializeObject($('#pi_wip_detail_searchForm')));
	}
	/* function clearFun() {
		$('#pi_wip_layout input[name=name]').val('');
		$('#pi_wip_detail_dg').datagrid('load', {});
	} */
</script>
<div id="pi_wip_detaillayout" class="easyui-layout"
	data-options="fit:true,border:false"
	style="margin-top: 1px; margin-left: 1px">
	<div data-options="region:'north',title:'查询条件'" style="height: 100px;">
		<form id="pi_wip_detail_searchForm">
			Lot(可模糊查询)：<input name="lid" /><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pi_wip_detail_searchForm_searchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearFun();">清空</a>
		</form>
	</div>
	<div data-options="region:'center',title:'WIP INFO'">
		<table id="pi_wip_detail_dg"></table>
	</div>
	<!-- <div data-options="region:'west',title:'部门',split:true" style="width:150px">
		<ul id="user_dept_tree"></ul>
	</div> -->
</div>

