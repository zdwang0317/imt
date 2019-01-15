<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var wipForAddPoDG = 0;  //0为datagrid末初始化  1为已经初始化
var wipForAddPoDGItem = 0;
	$(function() {
		$('#pi_po_dg').datagrid({
			url : 'orderAction!datagrid.action',
			fit : true,
			//fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 5,
			singleSelect : true,
			rownumbers : true,
			pageList : [ 5,10, 20, 30, 40, 50 ],
			columns : [ [ {field : 'id',hidden : true,},
			              {field : 'serialNumber',title : '工单单号',width : 150,},
			              {field : 'ipn',title : 'IPN',width : 150,},
			              {field : 'tpn',title : 'TPN',width : 150,},
			              {field : 'fabSite',title : 'Fab Site',width : 80,},
			              {field : 'field1',title : 'Tran. WO',width : 80,},
			              {field : 'field2',title : 'Stage',width : 80,},
			              {field : 'field3',title : 'B or A',width : 100,},
			              {field : 'createdUserName',title : '所有人',width : 80,},
			              {field : 'status',title : '状态',width : 80,}] ],
			toolbar: '#pi_po_dg_toolbar',
			onSelect : function(rowIndex,rowData){
				//判断单据如果为本人则可以进行删除审核等操作
				var loginName = $('#session_user_name').val();
				if(rowData.createdUserName==loginName){
						if(rowData.status=='CREATED'){
							$('#turnkey_order_delete').linkbutton('enable');
							$('#turnkey_order_complete').linkbutton('enable');
						}else if(rowData.status=='COMPLETED'){
							$('#turnkey_order_delete').linkbutton('enable');
						}else{
							$('#turnkey_order_delete').linkbutton('disable');
							$('#turnkey_order_complete').linkbutton('disable');
							$('#turnkey_order_cancel').linkbutton('disable');
						}
				}else{
					$('#turnkey_order_delete').linkbutton('disable');
					$('#turnkey_order_complete').linkbutton('disable');
					$('#turnkey_order_cancel').linkbutton('disable');
				}
				//查询TPNFLOW
				/* $('#pi_po_tpnflow_dg').datagrid({
					url : 'orderAction!datagridTpnTestFlows.action',
					queryParams : {tpn: rowData.tpn},
					fit : true,
					fitColumns : false,
					border : false,
					idField : 'id',
					singleSelect : true,
					rownumbers : true,
					columns : [ [ {field : 'id',hidden : true,},
					              {field : 'cp',title : 'CP',hidden : true,},
					              {field : 'testProgram',title : 'testProgram',width : 100,},
					              {field : 'customerID',title : 'customerID',},
					              {field : 'uniqueID',title : 'uniqueID',width : 100,},
					              {field : 'temperature',title : 'temperature',width : 100,},
					              {field : 'time',title : 'time',width : 100,},
					              {field : 'holdGrade',title : 'holdGrade'}
					              ] ]
				}); */
				//查询WO ITEM
				$('#pi_po_item_dg').datagrid({
					url : 'orderAction!datagridItem.action',
					queryParams : {id: rowData.id},
					fit : true,
					fitColumns : false,
					border : false,
					idField : 'id',
					singleSelect : true,
					rownumbers : true,
					columns : [ [ {field : 'id',hidden : true,},
					              {field : 'seqId',hidden : true,},
					              {field : 'lid',title : 'Lot No',width : 100,},
					              {field : 'cpSite',title : 'CP SITE',width : 50,},
					              {field : 'qty',title : 'Qty',width : 25,align : 'right'},
					              {field : 'pn_new',title : 'Prod',width : 100,},
					              {field : 'cpn_new',title : 'CPN',width : 100,},
					              {field : 'ipn',title : 'IPN(W)',width : 100,},
					              {field : 'ipn_new',title : 'IPN(CP)',width : 150,},
					              {field : 'wid',title : 'Wafer Id',},
					              {field : 'cpTestFlow',title : 'Cp Test Flow',}] ],
					view: detailview,
					detailFormatter: function(rowIndex, rowDataItem){
						return '<div style="padding-top:2px"><table id="ddv-' + rowIndex + '"></table></div>';
					},
					onCollapseRow : function(index,row){
						$('#pi_po_item_detail_index').val("");
						$('#turnkey_order_item_update').linkbutton('disable');
					},
					onExpandRow: function(index,row){
						var detail_index = $('#pi_po_item_detail_index').val();
						if(detail_index!=null){
							$('#pi_po_item_dg').datagrid('collapseRow',detail_index);
						}
						$('#pi_po_item_detail_index').val(index);
						if(rowData.createdUserName==loginName){
							if(rowData.status!='CANCELED'){
								$('#turnkey_order_item_update').linkbutton('enable');
							}
						}
						$('#pi_po_item_detail_id').val(row.id);
						$('#pi_po_item_detail_seqId').val(row.seqId);
						$('#pi_po_item_detail_fids').val("");
						$('#ddv-'+index).datagrid({
							url:'orderAction!datagridOfTurnkeyDetail.action',
							queryParams : {id: row.id,seqId: row.seqId}, 
							width:103,
							height:'auto',  
							columns:[[  {field:'fid_',checkbox : true}, 
										{field:'wid',title:'Wafer Id',width:72},  ]],  
							onResize:function(){  
							    $('#pi_po_item_dg').datagrid('fixDetailRowHeight',index);  
							},  
							onLoadSuccess:function(){  
							    setTimeout(function(){  
							        $('#pi_po_item_dg').datagrid('fixDetailRowHeight',index);  
							    },0);  
							},
							onUncheck : function(rowIndex,rowData){
								//console.info(rowData);
								var fieldValue = $('#pi_po_item_detail_fids').val();
								if(fieldValue.indexOf(rowData.fid_) >= 0){
									var newStr = fieldValue.replace(","+rowData.fid_,"");
									$('#pi_po_item_detail_fids').val(newStr);
								}
							},
							onCheck : function(rowIndex,rowData){
								var fieldValue = $('#pi_po_item_detail_fids').val();
								if(!(fieldValue.indexOf(rowData.fid_) >= 0)){
									$('#pi_po_item_detail_fids').val(fieldValue+","+rowData.fid_);
								}
							},
							onUncheckAll : function(rows){
								$('#pi_po_item_detail_fids').val("");
							},
							onCheckAll : function(rows){
								$('#pi_po_item_detail_fids').val("");
								var fieldValue = "";
								$.each(rows,function(idx,val){//遍历JSON  
									fieldValue = fieldValue+","+val.fid_;
								});
								$('#pi_po_item_detail_fids').val(fieldValue);
							}
						});
					$('#pi_po_item_dg').datagrid('fixDetailRowHeight',index);
					},
					toolbar:[{
						id:'turnkey_order_item_add',
						text:'添加',
						disabled:true,
						iconCls:'icon-add',
						handler:function(){
							pi_po_wip_showItemAddDialog();
						}
					}, '-', {
						id:'turnkey_order_item_delete',
						disabled:true,
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							pi_po_item_delete();
						}
					}, '-', {
						id:'turnkey_order_item_update',
						disabled:true,
						text : '移除项',
						iconCls : 'icon-cut',
						handler : function() {
							pi_po_item_update();
						}
					}, '-', {
						id:'turnkey_order_item_huanyuan',
						disabled:true,
						text : '转厂',
						iconCls : 'icon-back',
						handler : function() {
							pi_po_item_huanyuan();
						}
					}],
					onLoadSuccess : function(){
						if(rowData.createdUserName==loginName){
							var row = $('#pi_po_dg').datagrid('getSelected');
							if(row){
								if(row.status=='CREATED'){
									toolbar_created();
								}
								if(row.status=='COMPLETED'){
									toolbar_completed();
								}
							}
							$('#pi_po_item_detail_index').val("");
						}else{
							$('#pi_po_item_detail_index').val("");
						}
						
					},
					onSelect : function(rowIndex,rowItem){
						if(rowData.createdUserName==loginName){
							if(rowData.status!='CANCELED'){
								$('#turnkey_order_item_delete').linkbutton('enable');
								$('#turnkey_order_item_huanyuan').linkbutton('enable');
							}
						}
					}
				});
			}
		});
	});
	
	function pi_wip_detail_searchForm_searchFun() {
		$('#pi_po_dg').datagrid('load', serializeObject($('#pi_po_searchForm')));
		$('#pi_po_item_dg').datagrid('loadData', {rows: [] });
	}
	
	function pi_po_download(){
		var row = $('#pi_po_dg').datagrid('getSelected');
		$('#pi_po_download').form('submit', {
			url: 'orderAction!exportToExcel.action?id='+row.id+'&serialNumber='+row.serialNumber+'&fabSite='+row.fabSite+'&field1='+row.field1+'&field2='+row.field2+'&field3='+row.field3,
		});
	}
	
	function pi_po_item_update(){
		var mainrow = $('#pi_po_dg').datagrid('getSelected');
		var fids = $('#pi_po_item_detail_fids').val();
		$('#pi_po_item_detail_serialNumber').val(mainrow.serialNumber);
		$('#pi_po_item_detail_status').val(mainrow.status);
		if (fids) {
			parent.sy.messagerConfirm('请确认', '您要删除当前所选Wafer?', function(r) {
				if(r){
					 $('#pi_po_turnkey_item_detail_del').form('submit', {
						url: 'orderAction!deletePoItemDetailsFromFid.action',
						dataType : 'json',
						success:function(d){
							var json = $.parseJSON(d);
							$('#pi_po_item_dg').datagrid('reload');
							$('#turnkey_order_item_update').linkbutton('disable');
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
	function pi_po_delete(){
		var row = $('#pi_po_dg').datagrid('getSelected');
		if (row) {
			parent.sy.messagerConfirm('请确认', '您要删除当前所选工单?', function(r) {
				if(r){
					$('#pi_po_download').form('submit', {
						url: 'orderAction!deletePoFromId.action?serialNumber='+row.serialNumber+'&id='+row.id+'&status='+row.status,
						dataType : 'json',
						success:function(d){
							var json = $.parseJSON(d);
							$('#pi_po_dg').datagrid('load', serializeObject($('#pi_po_searchForm')));
							$('#pi_po_item_dg').datagrid('loadData', { total: 0, rows: [] });
							//$('#pi_po_item_dg').datagrid('reload');
							$('#pi_po_dg').datagrid('clearSelections');
							toolbar_disable();
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
	
	function pi_po_item_delete(){
		var mainrow = $('#pi_po_dg').datagrid('getSelected');
		var row = $('#pi_po_item_dg').datagrid('getSelected');
		if (row) {
			parent.sy.messagerConfirm('请确认', '您要删除当前项?', function(r) {
				if(r){
					$('#pi_po_delete').form('submit', {
						url: 'orderAction!deletePoItemFromIdAndSeqId.action?serialNumber='+mainrow.serialNumber+'&id='+row.id+'&seqId='+row.seqId+'&status='+mainrow.status,
						dataType : 'json',
						success:function(d){
							var json = $.parseJSON(d);
							$('#pi_po_item_dg').datagrid('reload');
							$('#pi_po_item_dg').datagrid('unselectAll');
							$('#turnkey_order_item_delete').linkbutton('disable');
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
	
	function pi_po_item_huanyuan(){
		var mainrow = $('#pi_po_dg').datagrid('getSelected');
		var row = $('#pi_po_item_dg').datagrid('getSelected');
		if (row) {
			parent.sy.messagerConfirm('请确认', '您要转厂当前项?', function(r) {
				if(r){
					$('#pi_po_delete').form('submit', {
						url: 'orderAction!huanyuanPoItemFromIdAndSeqId.action?serialNumber='+mainrow.serialNumber+'&id='+row.id+'&seqId='+row.seqId+'&status='+mainrow.status,
						dataType : 'json',
						success:function(d){
							var json = $.parseJSON(d);
							$('#pi_po_item_dg').datagrid('unselectAll');
							$('#turnkey_order_item_delete').linkbutton('disable');
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
	
	function pi_po_completed(){
		console.info("come in4");
		var row = $('#pi_po_dg').datagrid('getSelected');
		var rowIndex = $('#pi_po_dg').datagrid('getRowIndex',row);
		parent.sy.messagerConfirm('请确认', '您要审核所选工单?', function(r) {
			if(r){
				$('#pi_po_examine').form('submit', {
					/* url: 'orderAction!completedProductOrder.action?id='+row.id+'&ipn='+row.ipn+'&tpn='+row.tpn, */
					url: 'orderAction!completedProductOrderAndInvokeWebService.action?id='+row.id+'&ipn='+row.ipn+'&tpn='+row.tpn,
					dataType : 'json',
					success:function(d){
						var json = $.parseJSON(d);
						$.messager.show({
							title : '提示',
							msg : json.msg
						});
						 row.status = 'COMPLETED';
						$('#pi_po_dg').datagrid('updateRow',{'index':rowIndex,'row':row});
						toolbar_completed();
					}
				});
			}
		}); 
	}
	function pi_po_cancel(){
		var row = $('#pi_po_dg').datagrid('getSelected');
		var rowIndex = $('#pi_po_dg').datagrid('getRowIndex',row);
		parent.sy.messagerConfirm('请确认', '您要取消审核所选工单?', function(r) {
			 if(r){
				$('#pi_po_cancel').form('submit', {
					url: 'orderAction!cancelProductOrder.action?id='+row.id,
					dataType : 'json',
					success:function(d){
						toolbar_created();
						var json = $.parseJSON(d);
						$.messager.show({
							title : '提示',
							msg : json.msg
						});
						 row.status = 'CANCELED';
						$('#pi_po_dg').datagrid('updateRow',{'index':rowIndex,'row':row});
					}
				});
			}
		}); 
	}
	
	function toolbar_created(){
		$('#turnkey_order_delete').linkbutton('enable');
		$('#turnkey_order_complete').linkbutton('enable');
		$('#turnkey_order_item_add').linkbutton('enable');
		$('#turnkey_order_cancel').linkbutton('disable');
	}
	function toolbar_completed(){
		$('#turnkey_order_complete').linkbutton('disable');
		/* $('#turnkey_order_delete').linkbutton('disable'); */
		$('#turnkey_order_cancel').linkbutton('enable');
		$('#turnkey_order_item_delete').linkbutton('disable');
		$('#turnkey_order_item_add').linkbutton('disable');
	}
	
	function toolbar_disable(){
		$('#turnkey_order_delete').linkbutton('disable');
		$('#turnkey_order_complete').linkbutton('disable');
		$('#turnkey_order_item_add').linkbutton('disable');
		$('#turnkey_order_cancel').linkbutton('disable');
		$('#turnkey_order_item_delete').linkbutton('disable');
		$('#turnkey_order_item_add').linkbutton('disable');
		$('#turnkey_order_item_update').linkbutton('disable');
	}
	function pi_po_wip_showItemAddDialog() {
		$('#work_order_id').val($('#pi_po_dg').datagrid('getSelected').id);
		$('#pi_po_wip_itemAdd').dialog({
	        modal:true,
	        title : 'Item Add',
	        //fit : true ,
	        width : 1000,
	        height : 800,
	        buttons : [ {
				text : '添加',
				//iconCls : 'icon-add',
				handler : function() {
					var rows = $('#pi_wip_wo_turnkey_dg').datagrid('getSelections');
					var ids = [];
					if (rows.length > 0) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].lid);
						}
						$('#order_item_ids').val(ids.join(','));
						$('#pi_item_add_form').form('submit', {
							url : 'poAction!createPoItem.action',
							datatype : 'json',
							success : function(r) {
								$('#cancel_item_ids').val('');
								$('#order_item_ids').val('');
								pi_wip_turnkey_itemAdd_searchForm_searchFun();
								$('#pi_wip_wo_turnkey_dg').datagrid('clearSelections');
								$('#pi_wip_wo_turnkey_detail_dg').datagrid('load', {});
								$('#pi_po_item_dg').datagrid('reload');
								var obj = jQuery.parseJSON(r);
								$('#pi_po_wip_itemAdd').dialog('close');
								$.messager.show({
									title : '提示',
									msg : obj.msg,
								});
							}
						});
					} else {
						parent.sy.messagerAlert('提示', '请选择至少一项！', 'error');
					}
				}
			} ]
        });
	}
	function pi_wip_turnkey_itemAdd_searchForm_searchFun(){
		if(wipForAddPoDG>0){
			$('#pi_wip_wo_turnkey_dg').datagrid('load', serializeObject($('#pi_wip_po_item_searchForm')));
		}else{
			wipForAddPoDG++;
			$('#pi_wip_wo_turnkey_dg').datagrid({
				url : 'wipAction!datagridOfWip.action',
				queryParams : {lid: $('#pi_item_add_lid').val(),cpn:$('#pi_item_add_cpn').val(),ipn:$('#pi_item_add_ipn').val()},
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				pageSize : 20,
				rownumbers : true,
				pageList : [ 20,50,100,200],
				view:myview,
				rowStyler: function(index,row){
	               if (row.lid == 'Total'){
	                 return 'background-color:#CCCCCC;';
	             }
				},
				showFooter :true,
				/* frozenColumns : [ [ 
				    
					] ], */
				columns : [ [ {field : 'id',checkbox : true} ,{field : 'lid',title : 'Lot Id',width : 100,} ,{
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
					field : 'qty',
					title : 'Qty',
					align : 'right'
				} ] ],
				onSelect : function(rowIndex,rowData){
					//calculate total
					var footerQty = $('#pi_wip_wo_turnkey_dg').datagrid("getFooterRows")[0].qty;
	                $('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: footerQty+rowData.qty}]);
					/* var rows = $('#pi_wip_wo_turnkey_dg').datagrid('getSelections');
					var wipQty = 0;
					$.each(rows,function(idx,val){//遍历JSON  
	                	//加东西
	                	wipQty = wipQty + val.qty;
	                }) */
				},
				onLoadSuccess:function(data){//当数据加载成功时触发 
	                $("#pi_wip_wo_turnkey_dg").datagrid("unselectAll");
				},
				onUnselect : function(rowIndex,rowData){
					//calculate total
					var footerQty = $('#pi_wip_wo_turnkey_dg').datagrid("getFooterRows")[0].qty;
	                $('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: footerQty-rowData.qty}]);
	                //clear wafer datagird
					$('#pi_wip_wo_turnkey_detail_dg').datagrid('loadData', { total: 0, rows: [] });
					/* var rows = $('#pi_wip_wo_turnkey_dg').datagrid('getSelections');
					var wipQty = 0;
					$.each(rows,function(idx,val){//遍历JSON  
	                	//加东西
	                	wipQty = wipQty + val.qty;
	                })*/
				},
				onSelectAll: function(rows){
					var wipQty = 0;
					$.each(rows,function(idx,val){//遍历JSON  
	                	//加东西
	                	wipQty = wipQty + val.qty;
	                });
	                $('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: wipQty}]);
				},
				onUnselectAll: function(rows){
	                //$('#pi_wip_wo_turnkey_detail_dg').datagrid('loadData', { total: 0, rows: [] });
				},
				/* onCheckAll : function(rowIndex,rowData){
					var rows = $('#pi_wip_wo_turnkey_dg').datagrid('getSelections');
					var wipQty = 0;
					$.each(rows,function(idx,val){//遍历JSON  
	                	//加东西
	                	wipQty = wipQty + val.qty;
	                })
	                $('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: wipQty}])
				}, */
				onCheck : function(rowIndex,rowData){
					if(wipForAddPoDGItem>0){
						$('#pi_wip_wo_turnkey_detail_dg').datagrid('load', {lid:rowData.lid});
					}else{
						wipForAddPoDGItem++;
						$('#pi_wip_wo_turnkey_detail_dg').datagrid({
							url : 'wipAction!datagridOfWipDetail.action',
							queryParams : {lid: rowData.lid},
							fit : true,
							fitColumns : true,
							border : false,
							rownumbers : true,
							checkOnSelect : false,
							selectOnCheck : false,
							idField : 'id_',
							columns : [ [ {
								field : 'id_',
								title : 'ID',
								checkbox : true,
							},{
								field : 'wid',
								title : 'Wafer Id',
								width : 100,
							} ,{
								field : 'lid',
								title : 'Lot Id',
								width : 100,
							} ] ],
							onLoadSuccess:function(data){//当数据加载成功时触发 
							    /* var rowData = data.rows;
				                $.each(rowData,function(idx,val){//遍历JSON  
				                	//加东西
			                        $("#pi_wip_wo_turnkey_detail_dg").datagrid("selectRow", idx);//如果数据行为已选中则选中改行  
				                }) */
				                $("#pi_wip_wo_turnkey_detail_dg").datagrid("checkAll");
							},
							onUncheck : function(rowIndex,rowData){
								//console.info(rowData);
								$('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: $('#pi_wip_wo_turnkey_dg').datagrid("getFooterRows")[0].qty-1}]);
								var fieldValue = $('#cancel_item_ids').val();
								if(!(fieldValue.indexOf(rowData.id_) >= 0)){
									$('#cancel_item_ids').val(fieldValue+rowData.id_+",");
								}
							},
							onCheck : function(rowIndex,rowData){
								$('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: $('#pi_wip_wo_turnkey_dg').datagrid("getFooterRows")[0].qty+1}]);
								var fieldValue = $('#cancel_item_ids').val();
								if(fieldValue.indexOf(rowData.id_) >= 0){
									var newStr = fieldValue.replace(rowData.id_+",","");
									$('#cancel_item_ids').val(newStr);
								}
							},
							onUncheckAll : function(rows){
								var fieldValue = $('#cancel_item_ids').val();
								var num = 0;
								$.each(rows,function(idx,val){//遍历JSON  
									if(!(fieldValue.indexOf(val.id_) >= 0)){
										fieldValue = fieldValue+val.id_+",";
										num++;
									}
				                });
								$('#cancel_item_ids').val(fieldValue);
								$('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: $('#pi_wip_wo_turnkey_dg').datagrid("getFooterRows")[0].qty-num}]);
							},
							onCheckAll : function(rows){
								var fieldValue = $('#cancel_item_ids').val();
								var num = 0;
								$.each(rows,function(idx,val){//遍历JSON  
									if((fieldValue.indexOf(val.id_) >= 0)){
										fieldValue = fieldValue.replace(val.id_+",","");
										num++;
									}
				                });
								$('#cancel_item_ids').val(fieldValue);
								$('#pi_wip_wo_turnkey_dg').datagrid("reloadFooter",[{lid: 'Total', qty: $('#pi_wip_wo_turnkey_dg').datagrid("getFooterRows")[0].qty+num}]);
							}
						});
					}
				}
			});
		}
		
	}
</script>
<div id="pi_po_wip_detaillayout" class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 2px; margin-left: -1px;">
	<div data-options="region:'north',title:'Work Order',split:true" style="height: 260px;background:#eee;">
		<table id="pi_po_dg"></table>
		<div id="pi_po_dg_toolbar" >
			<form id="pi_po_searchForm">
			<table style='margin-top:-4px;margin-bottom:-4px'>
				<tr>
					<td>
						<a href="#" id="excel-download-po" class="easyui-linkbutton" iconCls="icon-zd-excel-download" plain="true" onclick="javascript:pi_po_download();">导出</a> 
						<a href="#" id="turnkey_order_delete" class="easyui-linkbutton" data-options="disabled:true" iconCls="icon-remove" plain="true" onclick="javascript:pi_po_delete();">删除</a> 
						<!-- <a href="#" id="turnkey_order_cancel" class="easyui-linkbutton" data-options="disabled:true" iconCls="icon-undo" plain="true" onclick="javascript:pi_po_cancel();">取消审核</a> --> 
						<a href="#" id="turnkey_order_complete" class="easyui-linkbutton" data-options="disabled:true" iconCls="icon-ok" plain="true" onclick="pi_po_completed();">审核</a>
					</td>
					<td><div class="pagination-btn-separator"></div></td>
					<td>单号 <input name="serialNumber" style="width: 80px" class="easyui-textbox"/></td>
					<td>Lot Id<input name="lid" style="width: 80px" class="easyui-textbox"/></td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pi_wip_detail_searchForm_searchFun();">查询</a></td>
				</tr>
			</table>
			</form>
		</div>
		<form id="pi_po_download" method="post"></form>
		<form id="pi_po_delete" method="post"></form>
		<form id="pi_po_examine" method="post"></form>
		<form id="pi_po_cancel" method="post"></form>
		<div style="display:none">
			<form id="pi_po_turnkey_item_detail_del">
				<input id="pi_po_item_detail_fids" name="fids"/>
				<input id="pi_po_item_detail_id" name="id"/>
				<input id="pi_po_item_detail_seqId" name="seqId"/>
				<input id="pi_po_item_detail_serialNumber" name="serialNumber"/>
				<input id="pi_po_item_detail_status" name="status"/>
			</form>
			<input id="pi_po_item_detail_index"/>
		</div>
	</div>
	<div data-options="region:'center',title:'ITEM'">
		<table id="pi_po_item_dg"></table>
	</div>
	<!-- <div data-options="region:'south',title:'TPN Flows'" style="height: 200px;">
		<table id="pi_po_tpnflow_dg"></table>
	</div> -->
		
<!-- 分割线 -->		
	<div id="pi_po_wip_itemAdd">
		<div id="pi_wip_po_item_layout" class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false," style="height: 40px;">
			 	<form id="pi_wip_po_item_searchForm">
					<table>
						<tr>
							<td>LotId</td><td><input id="pi_item_add_lid" name="lid" /></td>
							<td>CPN</td><td><input id="pi_item_add_cpn" name="cpn" /></td>
							<td>IPN</td><td><input id="pi_item_add_ipn" name="ipn" /></td>
							<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pi_wip_turnkey_itemAdd_searchForm_searchFun();">查询</a></td>
						</tr>
					</table>
				</form>
				<div>
					<form id="pi_item_add_form">
						<table>
						<tr style="display:none">
							<td><input name="ipn_ids" id="order_item_ids" ><input name="cancel_ids" id="cancel_item_ids"><input name="id" id="work_order_id"></td>
						</tr>
						</table>
					</form>
				</div> 
			</div>
			<div data-options="region:'center',split:true,border:false">
				<div id="pi_wip_po_item_layout2" class="easyui-layout" data-options="fit:true,border:false" >
					<div data-options="region:'center',title:'WIP INFO'" >
						<table id="pi_wip_wo_turnkey_dg"></table>
					</div>
					<div data-options="region:'east',title:'Wafer Detail'" style="width:200px">
						<table id="pi_wip_wo_turnkey_detail_dg"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
	

