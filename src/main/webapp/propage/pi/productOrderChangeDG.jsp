<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#pi_po_change_dg').datagrid({
			url : 'orderAction!datagrid.action',
			method :'get',
			queryParams : {status :'COMPLETED'},
			fit : true,
			//fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 5,
			singleSelect : true,
			rownumbers : true,
			pageList : [ 5,10, 20, 30, 40, 50 ],
			columns : [ [ {
				field : 'id',
				title : 'ID',
				hidden : true,
			},{
				field : 'serialNumber',
				title : '工单单号',
				width : 150,
			},{
				field : 'ipn',
				title : 'IPN',
				width : 150,
			},{
				field : 'createdUserName',
				title : '所有人',
				width : 150,
			},{
				field : 'status',
				title : '状态',
				width : 137,
			}] ],
			toolbar: '#pi_po_change_dg_toolbar',
			onSelect : function(rowIndex,rowData){
				$('#pi_po_change_item_dg').datagrid({
					url : 'orderAction!datagridItem.action',
					queryParams : {id: rowData.id},
					fit : true,
					fitColumns : false,
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
						hidden : true,
					},{
						field : 'seqId',
						title : '序号',
						hidden : true,
					},{
						field : 'lid',
						title : 'Lot No',
						width : 100,
					},{
						field : 'cpSite',
						title : 'CP SITE',
						width : 50,
					},{
						field : 'qty',
						title : 'Qty',
						width : 25,
						align : 'right'
					},{
						field : 'pn_new',
						title : 'Prod',
						width : 100,
					},{
						field : 'cpn_new',
						title : 'CPN',
						width : 100,
					},{
						field : 'ipn',
						title : 'IPN(W)',
						width : 100,
					},{
						field : 'ipn_new',
						title : 'IPN(CP)',
						width : 150,
					},{
						field : 'wid',
						title : 'Wafer Id',
					},{
						field : 'cpTestFlow',
						title : 'Cp Test Flow',
					}] ],
					view: detailview,
					 detailFormatter: function(rowIndex, rowDataItem){
						 return '<div style="padding-top:2px"><table id="ddv_ch-' + rowIndex + '"></table></div>';
					 },
				   	onCollapseRow : function(index,row){
						   $('#pi_po_item_detail_change_index').val("");
						   $('#turnkey_order_item_change_update').linkbutton('disable');
					 },
					 onExpandRow: function(index,row){
						 var detail_index = $('#pi_po_item_detail_change_index').val();
						 /* if(detail_index!=null){
						 	$('#pi_po_change_item_dg').datagrid('collapseRow',detail_index);
						 } */
						 $('#pi_po_item_detail_change_index').val(index);
						 var loginName = $('#session_user_name').val();
						if(rowData.createdUserName==loginName){
						 $('#turnkey_order_item_change_update').linkbutton('enable');
						}
						 $('#pi_po_item_detail_change_id').val(row.id);
						 $('#pi_po_item_detail_change_seqId').val(row.seqId);
						 $('#pi_po_item_detail_change_fids').val("");
						 $('#ddv_ch-'+index).datagrid({
		                        url:'orderAction!datagridOfTurnkeyDetail.action',
		    					queryParams : {id: row.id,seqId: row.seqId}, 
		                        width:103,
		                        height:'auto',  
		                        columns:[[  
									{field:'fid',checkbox : true}, 
		                            {field:'wid',title:'Wafer Id',width:72},  
		                        ]],  
		                        onResize:function(){  
		                            $('#pi_po_change_item_dg').datagrid('fixDetailRowHeight',index);  
		                        },  
		                        onLoadSuccess:function(){  
		                            setTimeout(function(){  
		                                $('#pi_po_change_item_dg').datagrid('fixDetailRowHeight',index);  
		                            },0);  
		                        },
		    					onUncheck : function(rowIndex,rowData){
		    						//console.info(rowData);
		    						var fieldValue = $('#pi_po_item_detail_change_fids').val();
		    						if(fieldValue.indexOf(rowData.fid) >= 0){
		    							var newStr = fieldValue.replace(","+rowData.fid,"");
		    							$('#pi_po_item_detail_change_fids').val(newStr);
		    						}
		    					},
		    					onCheck : function(rowIndex,rowData){
		    						var fieldValue = $('#pi_po_item_detail_change_fids').val();
		    						if(!(fieldValue.indexOf(rowData.fid) >= 0)){
		    							$('#pi_po_item_detail_change_fids').val(fieldValue+","+rowData.fid);
		    						}
		    					},
		    					onUncheckAll : function(rows){
		    						$('#pi_po_item_detail_change_fids').val("");
		    					},
		    					onCheckAll : function(rows){
		    						$('#pi_po_item_detail_change_fids').val("");
		    						var fieldValue = "";
		    						$.each(rows,function(idx,val){//遍历JSON  
	    								fieldValue = fieldValue+","+val.fid;
		    		                });
		    						$('#pi_po_item_detail_change_fids').val(fieldValue);
		    					}  
		                    });  
		                    $('#pi_po_change_item_dg').datagrid('fixDetailRowHeight',index);
					},
					toolbar:[{
						id:'turnkey_order_item_change_delete',
						disabled:true,
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							pi_po_item_change_delete();
						}
					}, '-', {
						id:'turnkey_order_item_change_update',
						disabled:true,
						text : '移除项',
						iconCls : 'icon-cut',
						handler : function() {
							pi_po_item_change_update();
						}
					}],
					onLoadSuccess : function(){
						var loginName = $('#session_user_name').val();
						if(rowData.createdUserName==loginName){
							$('#pi_po_item_detail_change_index').val("");
						}else{
							$('#pi_po_item_detail_change_index').val("");
						}
						
					},
					onSelect : function(rowIndex,rowItem){
						var loginName = $('#session_user_name').val();
						if(rowData.createdUserName==loginName){
							var row = $('#pi_po_change_dg').datagrid('getSelected');
							$('#turnkey_order_item_change_delete').linkbutton('enable');
						}
					}
				});
			}
		});
	});
	
	function pi_wip_detail_change_searchForm_searchFun() {
		$('#pi_po_change_dg').datagrid('load', serializeObject($('#pi_po_change_searchForm')));
		$('#pi_po_change_item_dg').datagrid('loadData', {rows: [] });
	}
	
	function pi_po_item_change_update(){
		var maindg = $('#pi_po_change_dg').datagrid('getSelected');
		var fids = $('#pi_po_item_detail_change_fids').val();
		var id = $('#pi_po_item_detail_change_id').val();
		var seqId = $('#pi_po_item_detail_change_seqId').val();
		if (fids) {
			parent.sy.messagerConfirm('请确认', '您要删除当前所选Wafer?', function(r) {
				if(r){
					 $('#pi_po_turnkey_item_detail_change_del').form('submit', {
						url: 'orderAction!deletePoItemDetailsFromFidByChange.action?serialNumber='+maindg.serialNumber+'&id='+id+'&seqId='+seqId+'&fids='+fids,
						dataType : 'json',
						success:function(d){
							var json = $.parseJSON(d);
							$('#pi_po_change_item_dg').datagrid('reload');
							$('#turnkey_order_item_change_update').linkbutton('disable');
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

	function pi_po_item_change_delete(){
		var maindg = $('#pi_po_change_dg').datagrid('getSelected');
		var row = $('#pi_po_change_item_dg').datagrid('getSelected');
		if (row) {
			parent.sy.messagerConfirm('请确认', '您要删除当前项?', function(r) {
				if(r){
					$('#pi_po_item_change_delete').form('submit', {
						url: 'orderAction!deletePoItemFromIdAndSeqIdByChange.action?serialNumber='+maindg.serialNumber+'&id='+row.id+'&seqId='+row.seqId,
						dataType : 'json',
						success:function(d){
							var json = $.parseJSON(d);
							$('#pi_po_change_item_dg').datagrid('reload');
							$('#pi_po_change_item_dg').datagrid('unselectAll');
							$('#turnkey_order_item_change_delete').linkbutton('disable');
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
<div id="pi_po_wip_change_detaillayout" class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 2px; margin-left: -1px;">
	<div data-options="region:'north',title:'Work Order Modify',split:true" style="height: 260px;background:#eee;">
		<!-- <div id="pi_po_wip_change_detaillayout" class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',title:''"> -->
				<table id="pi_po_change_dg" class="easyui-datagrid" data-options=""></table>
		<div id="pi_po_change_dg_toolbar" >
			<div>
				<form id="pi_po_change_searchForm">
					<table style='margin-top:-4px;margin-bottom:-4px'>
						<tr>
							<td>单号 <input name="serialNumber" style="width: 80px" /></td>
							<td>Lot Id<input name="lid" style="width: 80px" /></td>
							<td><a href="#" class="easyui-linkbutton"
								data-options="iconCls:'icon-search',plain:true"
								onclick="pi_wip_detail_change_searchForm_searchFun();">查询</a></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div style="display:none">
			<form id="pi_po_item_change_delete" method="post"></form>
			<form id="pi_po_turnkey_item_detail_change_del" method="post">
			</form>
			<input id="pi_po_item_detail_change_fids" name="fids"/>
			<input id="pi_po_item_detail_change_id" name="id"/>
			<input id="pi_po_item_detail_change_seqId" name="seqId"/>
			<input id="pi_po_item_detail_change_index"/>
		</div>
	</div>
	<div data-options="region:'center',title:'ITEM'">
		<table id="pi_po_change_item_dg"></table>
	</div>

</div>
	
	

