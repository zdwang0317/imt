<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
/* $(function() {
	$('#lot_to_hold_dg').datagrid({
		onSelect : function(rowIndex,rowData){
			$('#lot_to_hold_delete').linkbutton('enable');
		}
	})
}) */
function lot_to_hold_searchFun() {
	$('#lot_to_hold_dg').datagrid('load', serializeObject($('#lot_to_hold_searchForm')));
}
function lot_to_hold_compare(){
	$('#lot_to_hold_compare').form('submit', {
		url: 'lotToHoldAction!holdLotCompare.action',
		dataType : 'json',
		success:function(d){
			var json = $.parseJSON(d);
			$('#lot_to_hold_binggo').datagrid({
				data : json,
				fit : true,
				border : false,
				columns : [ [ {
					field : 'pn',
					title : 'PartNo',
					width : 150,
				}, {
					field : 'lid',
					title : 'Lot Id',
					width : 150,
				}, {
					field : 'wid',
					title : 'Wafer Id',
					width : 150,
				}, {
					field : 'qty',
					title : 'Quantity',
					width : 150,
				}, {
					field : 'remark',
					title : 'Remark',
				}  ] ],
			});
			$('#lot_to_hold_binggo_dialog').dialog('open');
		}
	});
}
function pi_po_delete(){
	var row = $('#lot_to_hold_dg').datagrid('getSelected');
	if (row) {
		console.info(row);
		parent.sy.messagerConfirm('请确认', '您要删除当前选项?', function(r) {
			if(r){
				$('#lot_to_hold_form').form('submit', {
					url: 'lotToHoldAction!deleteHoldLot.action?id='+row.id,
					dataType : 'json',
					success:function(d){
						var json = $.parseJSON(d);
						$('#lot_to_hold_dg').datagrid('load', serializeObject($('#lot_to_hold_searchForm')));
						$('#lot_to_hold_dg').datagrid('clearSelections');
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
<div id="lot_to_hold_layout" class="easyui-layout" data-options="fit:true,border:false">
	<table id="lot_to_hold_dg" class="easyui-datagrid" data-options="url : 'lotToHoldAction!datagrid.action',method :'get',
			fit : true,
			//fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			singleSelect : true,
			rownumbers : true,
			pageList : [ 10, 20, 30, 40, 50 ],
			columns : [ [ {
				field : 'id',
				title : 'ID',
			},{
				field : 'pn',
				title : 'Product ID',
				width : 150,
			},{
				field : 'lid',
				title : 'Lot ID',
				width : 150,
			},{
				field : 'wid',
				title : 'Wafer ID',
				width : 150,
			},{
				field : 'qty',
				title : 'Qty',
				width : 50,
			},{
				field : 'remark',
				title : 'Remark',
				width : 137,
			}] ],
			toolbar: '#lot_to_hold_toolbar'">
	</table>
		<div id="lot_to_hold_toolbar" >
			<div>
				<form id="lot_to_hold_searchForm">
				<table style='margin-top:-3px;margin-bottom:-3px'>
					<tr >
						<td><a href="#" id="lot_to_hold_add"
							class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="javascript:$('#lot_to_hold_dialog').dialog('open');">添加</a> <a href="#"
							id="lot_to_hold_delete" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true"
							onclick="javascript:pi_po_delete();">删除</a>
							<td><div class="pagination-btn-separator"></div></td>
							<td>Lot ID&nbsp;<input name="lid" style="width: 80px" class="easyui-textbox"/></td>
							<td><a href="#" class="easyui-linkbutton"
								data-options="iconCls:'icon-search',plain:true"
								onclick="lot_to_hold_searchFun();">查询</a></td>
							<td><div class="pagination-btn-separator"></div></td>
							<td><a href="#" class="easyui-linkbutton"
								data-options="iconCls:'icon-search',plain:true"
								onclick="javascript:$('#upload_data_dialog').dialog('open');">比对</a></td>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
		<div id="lot_to_hold_dialog" class="easyui-dialog" data-options="title: 'Add Hold Lot',
								width: 300,
								closed: true,
								height: 350,
								cache: false,
								modal: true,
								buttons : [ {
									text : '添加',
									//iconCls : 'icon-add',
									handler : function() {
										$.messager.progress();
										$('#lot_to_hold_form').form('submit', {
											url : 'lotToHoldAction!addHoldLot.action',
											datatype : 'json',
											success : function(r) {
												lot_to_hold_searchFun();
												var obj = jQuery.parseJSON(r);
												$('#lot_to_hold_dialog').dialog('close');
												$.messager.progress('close');
												$.messager.show({
													title : '提示',
													msg : obj.msg
												});
											}
										});
									}
								} ]
								" style="padding:10px;">
			<form id="lot_to_hold_form" method="post">
				<table>
					<tr>
						<td align="right">Product Id</td><td><input name="pn"></td>
					</tr>
					<tr>
						<td align="right">Lot Id</td><td><input name="lid"></td>
					</tr>
					<tr>
						<td align="right">Wafer Id</td><td><input name="wid" ></td>
					</tr>
					<tr>
						<td align="right">Remark</td><td><textarea name="remark" rows="5" cols="20"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="upload_data_dialog" class="easyui-dialog" data-options="title: 'Compare Hold Lot',
								width: 300,
								closed: true,
								height: 120,
								cache: false,
								modal: true,
								buttons : [ {
									text : '比对',
									//iconCls : 'icon-add',
									handler : function() {
										if($('#lot_to_hold_compare').form('validate')){
											$.messager.progress();
											$('#lot_to_hold_compare').form('submit', {
												url: 'lotToHoldAction!holdLotCompare.action',
												dataType : 'json',
												success:function(d){
													var json = $.parseJSON(d);
													$.messager.progress('close');
													$('#lot_to_hold_binggo').datagrid({
														data : json,
														fit : true,
														border : false,
														columns : [ [ {
															field : 'pn',
															title : 'PartNo',
															width : 150,
														}, {
															field : 'lid',
															title : 'Lot Id',
															width : 150,
														}, {
															field : 'wid',
															title : 'Wafer Id',
															width : 150,
														}, {
															field : 'qty',
															title : 'Quantity',
															width : 150,
														}, {
															field : 'remark',
															title : 'Remark',
														}  ] ],
													})
													$('#upload_data_dialog').dialog('close');
													$('#lot_to_hold_binggo_dialog').dialog('open');
												}
											});
										}
									}
								} ]
								" style="padding:10px;">
			<form id="lot_to_hold_compare" method="post" enctype="multipart/form-data">
				<!-- <input type="file" name="lotfile" id="lotfile" multiple="multiple" /> -->
				<input class="easyui-filebox" name="lotfile" id="lotfile" style="width:100%" data-options="required : true"/>
			</form>
		</div>
	<div id="lot_to_hold_binggo_dialog" class="easyui-dialog" data-options="title: '比对结果',closed: true,
							height: 400,
							width: 800,
							cache: false,
							modal: true">
			<table id="lot_to_hold_binggo"></table>
	</div>
</div>