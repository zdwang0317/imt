<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function pi_po_change_record_searchForm() {
		$('#pi_po_change_record_dg').datagrid('load', serializeObject($('#pi_po_change_record_searchForm')));
	}
</script>
<div id="pi_po_change_record_layout" class="easyui-layout" data-options="border:false,fit:true">
	<table class="easyui-datagrid" id="pi_po_change_record_dg"
		data-options="pageSize : 50,fit:true,border:false,
		pageList : [ 50 ,100,200],
		pagination : true,
		rownumbers:true,
		singleSelect:true,
		url:'orderAction!datagridOfChangeList.action',
		method:'get',
		toolbar:'#pi_po_change_record_dg_toolbar'">
		<thead>
			<tr>
				<th data-options="field:'lid',width:100">Lot Id</th>
				<th data-options="field:'wid',width:100">Wafer Id</th>
				<th data-options="field:'ipn',width:100">IPN</th>
				<th data-options="field:'cpn',width:100">CPN</th>
				<th data-options="field:'prod',width:100">Prod</th>
				<th data-options="field:'serialNumber',width:150">工单单号</th>
				<th data-options="field:'createdDate',width:150">CREATED DATE</th>
			</tr>
		</thead>
	</table>
	<div id="pi_po_change_record_dg_toolbar">
		<div>
			<form id="pi_po_change_record_searchForm">
				<table>
					<tr>
						<td>LotId</td>
						<td><input name="lid" class="easyui-textbox"/></td>
						<td>单号</td>
						<td><input name="serialNumber" class="easyui-textbox"/></td>
						<td><a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-search',plain:true"
							onclick="pi_po_change_record_searchForm();">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

