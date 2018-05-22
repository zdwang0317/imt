 <%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#trunkey_createdUserName').val($('#session_user_name').val());
		$('#pi_wip_turnkey_dg').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id_',
			pageSize : 50,
			rownumbers : true,
			pageList : [ 50 ,100,200],
			columns : [ [ {field : 'id_',checkbox : true,checkall:false} ,{field : 'lid',title : 'Lot Id',width : 100,} ,{
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
				width : 150,
				align : 'right'
			} ] ],
			onCheck : function(rowIndex,rowData){
				//console.info("come in onCheck");
				$('#pi_wip_turnkey_detail_dg').datagrid({
					url : 'wipAction!datagridOfWipDetail.action',
					queryParams : {lid: rowData.lid},
				})
			}
		});
		$('#pi_wip_turnkey_detail_dg').datagrid({
			fit : true,
			//fitColumns : true,
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
				width : 90,
			},{
				field : 'lid',
				title : 'Lot Id',
				width : 102,
			}] ],
			onLoadSuccess:function(data){//当数据加载成功时触发 
			    /* var rowData = data.rows;
                $.each(rowData,function(idx,val){//遍历JSON  
                	//加东西
                    $("#pi_wip_turnkey_detail_dg").datagrid("selectRow", idx);//如果数据行为已选中则选中改行  
                }) */
                $("#pi_wip_turnkey_detail_dg").datagrid("checkAll");
			},
			onUncheck : function(rowIndex,rowData){
				//console.info(rowData);
				var fieldValue = $('#cancel_ids').val();
				if(!(fieldValue.indexOf(rowData.id) >= 0)){
					$('#cancel_ids').val(fieldValue+rowData.id_+",");
				}
			},
			onCheck : function(rowIndex,rowData){
				var fieldValue = $('#cancel_ids').val();
				if(fieldValue.indexOf(rowData.id_) >= 0){
					var newStr = fieldValue.replace(rowData.id_+",","");
					$('#cancel_ids').val(newStr);
				}
			},
			onUncheckAll : function(rows){
				var fieldValue = $('#cancel_ids').val();
				var num = 0;
				$.each(rows,function(idx,val){//遍历JSON  
					if(!(fieldValue.indexOf(val.id_) >= 0)){
						fieldValue = fieldValue+val.id_+",";
						num++;
					}
                });
				$('#cancel_ids').val(fieldValue);
			},
			onCheckAll : function(rows){
				var fieldValue = $('#cancel_ids').val();
				var num = 0;
				$.each(rows,function(idx,val){//遍历JSON  
					if((fieldValue.indexOf(val.id_) >= 0)){
						fieldValue = fieldValue.replace(val.id_+",","");
						num++;
					}
                });
				$('#cancel_ids').val(fieldValue);
			}
		});
		var pager = $('#pi_wip_turnkey_dg').datagrid('getPager');    // 得到datagrid的pager对象  
		pager.pagination({    
		    buttons:[{
		    	text: '添加工单',
		        iconCls:'icon-add',    
		        handler:function(){
		        	pi_wip_show_dialog();
		        }    
		    }],    
		    onBeforeRefresh:function(){    
		        return true;    
		    }
		});
	});
	
	function pi_wip_turnkey_searchForm_searchFun() {
		$('#pi_wip_turnkey_dg').datagrid({
			url : 'wipAction!datagridOfWip.action',
			queryParams : serializeObject($('#pi_wip_turnkey_searchForm'))
		})
		var pager = $('#pi_wip_turnkey_dg').datagrid('getPager');    // 得到datagrid的pager对象  
		pager.pagination({    
		    buttons:[{
		    	text: '添加工单',
		        iconCls:'icon-add',    
		        handler:function(){
		        	pi_wip_show_dialog();
		        }    
		    }],    
		    onBeforeRefresh:function(){    
		        return true;    
		    }
		});
		/* $('#pi_wip_turnkey_dg').datagrid('load', serializeObject($('#pi_wip_turnkey_searchForm'))); */
		$('#pi_wip_turnkey_dg').datagrid('unselectAll');
		$('#ipn_ids').val('');
		$('#cancel_ids').val('');
		$('#pi_wip_turnkey_detail_dg').datagrid('loadData', { total: 0, rows: [] });
	}
	
	function pi_wip_show_dialog() {
		fullOfOptions();
		var rows = $('#pi_wip_turnkey_dg').datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].lid);
			}
			$('#ipn_ids').val(ids.join(','));
			if(rows[0].ipn.length==15||rows[0].ipn.length==8){
				if(rows[0].ipn.length==15){
					/* $('#ipn_ipn').val(rows[0].ipn.substring(3,9)); */
					 $('#pi_wip_po_form').form('load',{
						 ipn_ipn:rows[0].ipn.substring(3,9),
					 });
				}else{
					/* $('#ipn_ipn').val(rows[0].ipn.substring(2)); */
					$('#pi_wip_po_form').form('load',{
						 ipn_ipn:rows[0].ipn.substring(2),
					 });
				}
			}else{
				$('#ipn_ipn').val(rows[0].ipn);
			}
			$('#pi_wip_po_dialog').dialog('open');
		} else {
			parent.sy.messagerAlert('提示', '请选择至少一项！', 'error');
		} 
		
		
	}
	function fullOfOptions(){
		var ipn_ipn = $('#ipn_ipn').val();
		$.ajax({
			url : 'poAction!PassNandOptionsByStr.action',
			dataType : 'json',
			success : function(d) {
				$('#ipn_zero').combobox('clear');
				$('#ipn_one').combobox('clear');
				$('#ipn_two').combobox('clear');
				$('#ipn_three').combobox('clear');
				$('#ipn_four').combobox('clear'); 
				$('#ipn_five').combobox('clear'); 
				$('#ipn_six').combobox('clear');
				$('#ipn_seven').combobox('clear');
				$('#ipn_eight').combobox('clear');
				$('#ipn_nine').combobox('clear');
				$('#ipn_ten').combobox('clear');
				/* $('#prod_name').combobox('clear'); */
				$('#ipn_zero').combobox('loadData',d.k1);
				$('#ipn_zero_').combobox('loadData',d.k2);
				$('#ipn_zero__').combobox('loadData',d.k3);
				$('#ipn_three').combobox('loadData',d.k4);
				$('#ipn_four').combobox('loadData',d.k5); 
				$('#ipn_five').combobox('loadData',d.k6); 
				$('#ipn_six').combobox('loadData',d.k7);
				$('#ipn_eight').combobox('loadData',d.k8);
				$('#ipn_nine').combobox('loadData',d.k9);
				$('#ipn_ten').combobox('loadData',d.k10);
				/* var prodlist = new Array();
				var haslist = new Array();
				var j = 0;
				for(var i=0;i<d.pl.length;i++){
					var key = d.pl[i];
					var has = false;
					for(var k=0;k<haslist.length;k++){
						if(haslist[k]==key.name){
							has = true;
						}
					}
					if(has==false){
						prodlist[j] = key;
						haslist[j] = key.name;
						j++;
					}
				} */
				/* $('#prod_name').combobox('loadData',prodlist);
				$('#ipn_five').combobox('loadData',d.k5);  */
			}
		});
	}
	function assignValue(arg){
		if(arg=='3'){
			var value = $('#ipn_three').combobox('getText');
			$('#ipn_three').combobox('setValue',value);
		}
		if(arg=='4'){
			var value = $('#ipn_four').combobox('getText');
			$('#ipn_four').combobox('setValue',value);
		}
		if(arg=='7'){
			var value = $('#ipn_seven').combobox('getText');
			$('#ipn_seven').combobox('setValue',value);
		}
		
	}
</script>
<div id="pi_wip_turnkey_detaillayout" class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 1px; margin-left: -1px">
	<div data-options="region:'north',title:'查询条件'" style="height: 65px;background:#eee;">
		<form id="pi_wip_turnkey_searchForm">
			<table>
				<tr>
					<td>LotId(空格批量查询)</td><td><input id="pi_wip_turnkey_searchForm_lid" name="lid" class="easyui-textbox" /></td>
					<td>Product No</td><td><input id="pi_wip_turnkey_searchForm_pn" name="pn" class="easyui-textbox" /></td>
					<td>CPN</td><td><input id="pi_wip_turnkey_searchForm_cpn" name="cpn" class="easyui-textbox" /></td>
					<td>IPN</td><td><input id="pi_wip_turnkey_searchForm_ipn" name="ipn" class="easyui-textbox" /></td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pi_wip_turnkey_searchForm_searchFun();">查询</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',split:true,title:''" style="margin-left:-2px">
		<div id="pi_wip_turnkey_layout2" class="easyui-layout" data-options="fit:true,border:false" style="margin-top: 1px; margin-left: 1px">
			<div data-options="region:'center',title:'WIP INFO'" >
				<table id="pi_wip_turnkey_dg"></table>
			</div>
			<div data-options="region:'east',title:'Wafer Detail',split:true" style="width:250px">
				<table id="pi_wip_turnkey_detail_dg"></table>
			</div>
		</div>
		
	</div>
	<div id="pi_wip_po_dialog" class="easyui-dialog" data-options="title: '工单参数生成规则',
								width: 400,
								closed: true,
								height: 400,
								cache: false,
								modal: true,
								buttons : [ {
									text : '生成工单',
									//iconCls : 'icon-add',
									handler : function() {
										var valid = $('#pi_wip_po_form').form('validate');
										if(valid){
											$.messager.progress();
											$('#pi_wip_po_form').form('submit', {
												url : 'poAction!createNandPoAndValidateTpn.action',
												datatype : 'json',
												success : function(r) {
													$('#cancel_ids').val('');
													$('#ipn_ids').val('');
													pi_wip_turnkey_searchForm_searchFun();
													$('#pi_wip_turnkey_dg').datagrid('clearSelections');
													$('#pi_wip_turnkey_detail_dg').datagrid('load', {});
													var obj = jQuery.parseJSON(r);
													$('#pi_wip_po_dialog').dialog('close');
													$.messager.progress('close');
													$.messager.show({
														title : '提示',
														msg : obj.msg
													});
												}
											});
										}
										
									}
								} ]
								" style="padding:10px;">
	<form id="pi_wip_po_form">
		<table>
			<tr>
				<td colspan="2">IPN生成规则</td>
			</tr>
			<tr>
				<td>前2码(1-2)</td>
				<td><select id="ipn_one" name="ipn_one" class="easyui-combobox" data-options="data: [{
					name: 'JA',
					description: 'JA: 量产'
				},{
					name: 'JE',
					description: 'JE: 工程'
				},{
					name: 'JS',
					description: 'JS: SRA'
				}],required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>IPN(3-6)</td>
				<td>
				<select id="ipn_zero"  name="ipn_zero" class="easyui-combobox" data-options="required:true,valueField:'name',textField:'description',width:60"></select>
				<select id="ipn_zero_"  name="ipn_zero_" class="easyui-combobox" data-options="required:true,valueField:'name',textField:'description',width:60"></select>
				<select id="ipn_zero__"  name="ipn_zero__" class="easyui-combobox" data-options="required:true,valueField:'name',textField:'description',width:60"></select>
			</tr>
			<tr>
				<td>Product Series(7-8)</td>
				<td><select id="ipn_three"  name="ipn_three" class="easyui-combobox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>Density(9)</td>
				<td><select id="ipn_four" name="ipn_four" class="easyui-combobox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>CP Tester(10)</td>
				<td><select id="ipn_five" name="ipn_five" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>CP Test flow(11-12)</td>
				<td><select id="ipn_six" name="ipn_six" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>Application Area(13)</td>
				<td><select id="ipn_eight" name="ipn_eight" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>Reserved(14)</td>
				<td><select id="ipn_nine" name="ipn_nine" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>Reliability(15)</td>
				<td><select id="ipn_ten" name="ipn_ten" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<!-- <tr>
				<td>Reserve(13-15)</td>
				<td><select id="ipn_seven" name="ipn_seven" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr> -->
			<tr style="display:none">
				<td><!-- <input name="ipn_seven" value="000"> --><input name="createdUserName" id="trunkey_createdUserName" ><input name="ipn_ids" id="ipn_ids" ><input name="cancel_ids" id="cancel_ids"></td>
			</tr>
			<!-- <tr>
				<td colspan="2">Prod生成规则</td>
			</tr> -->
			<tr>
				<td>Prod List</td>
				<td><input name="prod_name" class="easyui-textbox easyui-validatebox" data-options="required:true,width:200"></td>
			</tr>
			<tr>
				<td>CPN List</td>
				<td>
					<input name="cpn_name" class="easyui-textbox easyui-validatebox" data-options="required:true,width:200">
				</td>
			</tr>
			<!-- <tr>
				<td colspan="2">Fab Id</td>
			</tr> -->
			<tr>
				<td>Fab Id</td>
				<td>
					<select name="fabSite" class="easyui-combobox">
					 	<option value="XMC">XMC</option>
						<option value="SMIC">SMIC</option>
						<option value="GIGA">GIGA</option>
						<option value="GIGA_HK">GIGA_HK</option>
						<option value="GIGA_BJ">GIGA_BJ</option>
						<option value="GIGA_SH">GIGA_SH</option>
						<option value="GIGA_HF">GIGA_HF</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="color:red">注:如12-14为STR,则其它参数可手动录入,不验证TPN</td>
			</tr>
		</table>
	</form>
</div>
</div>
