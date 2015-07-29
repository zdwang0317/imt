<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var editIndex = undefined;
 $(function() {
	$('#tpn_rule_dg').datagrid({
		url : 'tpnRuleAction!datagrid.action',method :'get',
		fit : true,
		//fitColumns : true,
		border : false,
		pagination : false,
		pageSize : 20,
		singleSelect : true,
		rownumbers : false,
		pageList : [ 10, 20, 30, 40, 50 ],
		columns : [ [ {
			field : 'ruleId',
			title : 'ID',
		},{
			field : 'ruleName',
			title : 'Rule Name',
			width : 250,
		},{
			field : 'ruleTypeId',
			title : 'Type',
			width : 50,
		}] ],
		onSelect : function(rowIndex,rowData){
			console.info(rowData.ruleId);
			$('#tpn_pn_relation_dg').datagrid({
				url : 'tpnRuleAction!datagridPnRule.action',
				queryParams : {ruleId: rowData.ruleId},
				fit : true,
				//fitColumns : true,
				border : false,
				pagination : false,
				pageSize : 20,
				singleSelect : true,
				rownumbers : false,
				pageList : [ 10, 20, 30, 40, 50 ],
				columns : [ [ {
					field : 'pn',
					title : 'Part No',
				}] ],
				toolbar: '#tpn_pn_relation_dg_toolbar'
			});
			$('#tpn_rule_item_dg').datagrid({
				url : 'tpnRuleAction!datagridItem.action',
				queryParams : {ruleId: rowData.ruleId},
				fit : true,
				//fitColumns : true,
				border : false,
				pagination : false,
				pageSize : 20,
				singleSelect : true,
				rownumbers : false,
				pageList : [ 10, 20, 30, 40, 50 ],
				columns : [ [ {
					field : 'tpn',
					title : 'WIP Flow Name',
				},{
					field : 'remLayer',
					title : 'RemLayer',
					width : 250,
				}] ],
				toolbar: '#tpn_rule_item_dg_toolbar'
			});
		},
		toolbar: '#tpn_rule_dg_toolbar'
	});
	
	
}) ;
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#tpn_flow_new_dg').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#tpn_flow_new_dg').datagrid('selectRow', editIndex);
		}
	}
}
function endEditing(){
	if (editIndex == undefined){
		return true;
	}else{
		$('#tpn_flow_new_dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	}
}

function openCreateItem(){
	var row = $('#tpn_rule_dg').datagrid('getSelected');
	$('#tpn_flow_new_dg').datagrid({
		url : 'tpnRuleAction!datagridOfTpnFlow.action',
		queryParams : {ruleTypeId: row.ruleTypeId,ruleId:row.ruleId},
		fit : true,
		//fitColumns : true,
		border : false,
		pagination : false,
		pageSize : 20,
		singleSelect : true,
		rownumbers : false,
		pageList : [ 10, 20, 30, 40, 50 ],
		columns : [ [ {
			field : 'tpn',
			title : 'WIP Flow Name',
		},{
			field : 'remLayer',
			title : 'RemLayer',
			align : 'right',
			width : 150,
			editor:'numberbox'
		}] ],
		onClickRow: onClickRow,
	});
	$('#tpn_flow_dialog').dialog('open');
}
function openCreatePnRelation(){
	var row = $('#tpn_rule_dg').datagrid('getSelected');
	$('#ruleId').val(row.ruleId);
	$('#tpn_pn_relation_dialog').dialog('open');
}
function opendeletePnRelation(){
	var row = $('#tpn_pn_relation_dg').datagrid('getSelected');
	if (row) {
		parent.sy.messagerConfirm('请确认', '您要删除Part No?', function(r) {
			if(r){
				$('#pn_del').form('submit', {
					url: 'tpnRuleAction!deletePnRelation.action?pn='+row.pn,
					dataType : 'json',
					success:function(d){
						var json = $.parseJSON(d);
						$('#tpn_pn_relation_dg').datagrid('reload');
						$.messager.show({
							title : '提示',
							msg : json.msg
						});
					}
				});
			}
		});
	} else {
		parent.sy.messagerAlert('提示', '请勾选要删除的记录！', 'error');
	}
}
</script>

<form id="pn_del" method="post"></form>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,border:false" style="width:350px">
		<div id="lot_to_hold_layout" class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',split:true,border:false" style="height:450px">
				<table id="tpn_rule_dg"></table>
			</div>
			<div data-options="region:'center',split:true,border:false">
				<table id="tpn_pn_relation_dg"></table>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="tpn_rule_item_dg"></table>
	</div>
	
</div>
<div id="tpn_flow_dialog" class="easyui-dialog" title="Basic Dialog" data-options="closed : true,modal: true,iconCls:'icon-save',buttons : [ {
									text : '生成规则',
									//iconCls : 'icon-add',
									handler : function() {
										$.messager.progress();
										var row = $('#tpn_rule_dg').datagrid('getSelected');
										endEditing();
										$.ajax({
											url : 'tpnRuleAction!createOfUpdateTpnRuleItem.action',
											data : {
												status : JSON.stringify($('#tpn_flow_new_dg').datagrid('getRows')),
												ruleId : row.ruleId
											},
											method : 'post',
											dataType : 'json',
											success : function(r) {
												$('#tpn_flow_dialog').dialog('close');
												$('#tpn_rule_item_dg').datagrid('reload');
												$.messager.progress('close');
												$.messager.show({
													title : '提示',
													msg : r.msg
												});
											}
										});
									}}]" style="width:300px;height:500px">
<table id="tpn_flow_new_dg"></table>
</div>
<div id="tpn_pn_relation_dialog" class="easyui-dialog" title="Basic Dialog" data-options="closed : true,modal: true,iconCls:'icon-save',buttons : [ {
									text : '生成Part No',
									//iconCls : 'icon-add',
									handler : function() {
										$.messager.progress();
										$('#pn_relation_form').form('submit', {
											url : 'tpnRuleAction!createPnRelation.action',
											datatype : 'json',
											success : function(r) {
												var obj = jQuery.parseJSON(r);
												$('#tpn_pn_relation_dialog').dialog('close');
												$('#tpn_pn_relation_dg').datagrid('reload');
												$.messager.progress('close');
												$.messager.show({
													title : '提示',
													msg : obj.msg
												});
											}
										});
									}}]" style="width:200px;height:120px">
<form id="pn_relation_form">
<input type="hidden" id="ruleId" name="ruleId">
<input type="text" class="easyui-textbox" name="pn" style="margin-top : 5px">
</form>
</div>
<div id="tpn_rule_item_dg_toolbar" >
	<div>
		<table style='margin-top:-3px;margin-bottom:-3px'>
			<tr>
				<td><a href="#" id="lot_to_hold_add" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openCreateItem()">编辑</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="tpn_rule_dg_toolbar" >
	<div>
		<table style='margin-top:-3px;margin-bottom:-3px'>
			<tr>
				<td><a href="#" id="lot_to_hold_add" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="">添加</a>
				<a href="#" id="lot_to_hold_delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:pi_po_delete();">编辑</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="tpn_pn_relation_dg_toolbar" >
	<div>
		<table style='margin-top:-3px;margin-bottom:-3px'>
			<tr>
				<td><a href="#" id="lot_to_hold_add" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openCreatePnRelation()">添加</a>
				<a href="#" id="lot_to_hold_delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="opendeletePnRelation();">删除</a>
				</td>
			</tr>
		</table>
	</div>
</div>