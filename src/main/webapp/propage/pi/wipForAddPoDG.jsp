<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var options;
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
			}, {
				field : 'poNo',
				title : 'PO',
				width : 50,
				align : 'right'
			}, {
				field : 'firm',
				title : 'Fab',
				width : 50,
				align : 'right'
			} ] ],
			onCheck : function(rowIndex,rowData){
				//console.info("come in onCheck");
				$('#pi_wip_turnkey_detail_dg').datagrid({
					url : 'wipAction!datagridOfWipDetail.action',
					queryParams : {lid: rowData.lid},
				})
			},
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
		//
		options = null;
		
		var rows = $('#pi_wip_turnkey_dg').datagrid('getSelections');
		var ids = [];
		var poNo = "";
		if (rows.length > 0) {
			var firm = "";
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].lid);
				if(rows[i].poNo!=""){
					poNo = rows[i].poNo;
				}
				if(""==firm){
					var f = rows[i].firm;
					if(""!=f){
						firm = f;
					}
				}
			}
			$('#ipn_ids').val(ids.join(','));
			if(""!=firm){
				var pn = rows[0].pn;
				if(""!=pn){
					var ipn = "";
					if(firm=="hlmc"){
						var ipn_ = pn.substring(0,5);
						var ipn_2 = pn.substring(6,7);
						if(ipn_2=="1"){
							ipn_ = ipn_+"0";
						}else{
							ipn_ = ipn_+ipn_2;
						}
						ipn = ipn_;
					}
					if(firm=="xmc"||firm=="smic"){
						var ipn_ = pn.substring(0,5);
						if(pn.length>7){
							ipn_ = ipn_+pn.substring(7,8);
						}else{
							ipn_ = ipn_+"0";
						}
						ipn = ipn_;
					}
					$('#pi_wip_po_form').form('load',{
						 ipn_ipn:ipn,
					 });
				}
			}else{
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
			}
			
			var hasV = false;
			if(poNo!=""&&null!=poNo){
				var ifc = poNo.substring(4,5);
				if("C"==ifc.toUpperCase()){
					$('#fabSite').combobox('select', 'SMIC');
					hasV = true;
				}else{
					var fir = poNo.substring(0,1);
					if("1"==fir){
						$('#fabSite').combobox('select', 'GIGA_BJ');
						hasV = true;
					}else if("2"==fir){
						$('#fabSite').combobox('select', 'GIGA_HK');
						hasV = true;
					}else if("3"==fir){
						$('#fabSite').combobox('select', 'GIGA_SH');
						hasV = true;
					}else if("4"==fir){
						$('#fabSite').combobox('select', 'GIGA_HF');
						hasV = true;
					}
				}
				
			}
			if(hasV){
				$("#hasAuto").show();
			}else{
				$("#hasAuto").hide();
			}
			$('#pi_wip_po_dialog').dialog('open');
		} else {
			parent.sy.messagerAlert('提示', '请选择至少一项！', 'error');
		} 
		
		
	}
	function fullOfOptions(){
		var ipn_ipn = $('#ipn_ipn').val();
		$.ajax({
			url : 'poAction!PassOptionsByStr.action',
			data : {
				ipn_ipn : ipn_ipn
			},
			dataType : 'json',
			success : function(d) {
				$('#ipn_three').combobox('clear');
				$('#ipn_four').combobox('clear'); 
				$('#ipn_five').combobox('clear'); 
				$('#ipn_six').combobox('clear');
				$('#ipn_seven').combobox('clear');
				$('#prod_name').combobox('clear');
				$('#ipn_three').combobox('loadData',d.k3);
				$('#ipn_four').combobox('loadData',d.k4); 
				$('#ipn_five').combobox('loadData',d.k5); 
				$('#ipn_six').combobox('loadData',d.k6);
				$('#ipn_seven').combobox('loadData',d.k7);
				var prodlist = new Array();
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
				}
				$('#prod_name').combobox('loadData',prodlist);
				$('#ipn_five').combobox('loadData',d.k5); 
				$('#ipn_five').combobox('setValues',["S"]);
				$('#ipn_six').combobox('setValues',["TR"]);
				/* if(d.k3.length==1){
					$('#ipn_three').combobox('select',d.k3[0].description);
				}
				if(d.k4.length==1){
					$('#ipn_four').combobox('select',d.k4[0].description);
				}
				if(d.k5.length==1){
					$('#ipn_five').combobox('select',d.k5[0].description);
				}
				if(d.k6.length==1){
					$('#ipn_six').combobox('select',d.k6[0].description);
				}
				if(d.k7.length==1){
					$('#ipn_seven').combobox('select',d.k7[0].name);
				} */
			}
		});
	}
	
	function fullOfOptions_new(){
		var ipn_ipn = $('#ipn_ipn').val();
		$('#ipn_three').combobox('clear');
		$('#ipn_four').combobox('clear');
		$('#ipn_five').combobox('clear');
		$('#ipn_six').combobox('clear');
		$('#ipn_seven').combobox('clear');
		$('#prod_name').combobox('clear');
		$('#cpn_name').combobox('clear');
		$.ajax({
			url : 'poAction!PassOptionsByStrNew.action',
			data : {
				ipn_ipn : ipn_ipn
			},
			dataType : 'json',
			success : function(d) {
				$('#ipn_three').combobox('loadData',d);
				options = d;
			}
		});
	}
	function fullOfOptions_new_filter(value,id){
		if("ipn_three"==id){
			$('#ipn_four').combobox('clear');
			$('#ipn_five').combobox('clear');
			$('#ipn_six').combobox('clear');
			$('#ipn_seven').combobox('clear');
			$('#prod_name').combobox('clear');
			$('#cpn_name').combobox('clear');
			var filllist = new Array();
			var j = 0;
			for(var i=0;i<options.length;i++){
				var a = options[i];
				if(a.ipn_three==value){
					filllist[j] = a;
					j++;
				}
			}
			$('#ipn_four').combobox('loadData',filllist);
			$('#ipn_five').combobox('clear');
			$('#ipn_six').combobox('clear');
			$('#ipn_seven').combobox('clear');
			$('#prod_name').combobox('clear');
			$('#cpn_name').combobox('clear');
		}
		if("ipn_four"==id){
			var three = $('#ipn_three').combobox('getValue');
			var filllist = new Array();
			var j = 0;
			for(var i=0;i<options.length;i++){
				var a = options[i];
				if(a.ipn_four==value&&a.ipn_three==three){
					filllist[j] = a;
					j++;
				}
			}
			$('#ipn_five').combobox('loadData',filllist);
			$('#ipn_six').combobox('clear');
			$('#ipn_seven').combobox('clear');
			$('#prod_name').combobox('clear');
			$('#cpn_name').combobox('clear');
		}
		if("ipn_five"==id){
			var three = $('#ipn_three').combobox('getValue');
			var four = $('#ipn_four').combobox('getValue');
			var filllist = new Array();
			var j = 0;
			for(var i=0;i<options.length;i++){
				var a = options[i];
				if(a.ipn_five==value&&a.ipn_three==three&&a.ipn_four==four){
					filllist[j] = a;
					j++;
				}
			}
			$('#ipn_six').combobox('loadData',filllist);
			$('#ipn_seven').combobox('clear');
			$('#prod_name').combobox('clear');
			$('#cpn_name').combobox('clear');
		}
		if("ipn_six"==id){
			var three = $('#ipn_three').combobox('getValue');
			var four = $('#ipn_four').combobox('getValue');
			var five = $('#ipn_five').combobox('getValue');
			var filllist = new Array();
			var j = 0;
			for(var i=0;i<options.length;i++){
				var a = options[i];
				if(a.ipn_six==value&&a.ipn_three==three&&a.ipn_four==four&&a.ipn_five==five){
					filllist[j] = a;
					j++;
				}
			}
			$('#ipn_seven').combobox('loadData',filllist);
			$('#prod_name').combobox('clear');
			$('#cpn_name').combobox('clear');
		}
		if("ipn_seven"==id){
			var three = $('#ipn_three').combobox('getValue');
			var four = $('#ipn_four').combobox('getValue');
			var five = $('#ipn_five').combobox('getValue');
			var six = $('#ipn_six').combobox('getValue');
			var filllist = new Array();
			var j = 0;
			for(var i=0;i<options.length;i++){
				var a = options[i];
				if(a.ipn_seven==value&&a.ipn_three==three&&a.ipn_four==four&&a.ipn_five==five&&a.ipn_six==six){
					filllist[j] = a;
					j++;
				}
			}
			$('#prod_name').combobox('loadData',filllist);
			$('#cpn_name').combobox('clear');
		}
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
												url : 'poAction!createPoAndValidateTpn.action',
												datatype : 'json',
												success : function(r) {
													$('#ipn_ipn').val('');
													$('#cancel_ids').val('');
													$('#ipn_ids').val('');
													pi_wip_turnkey_searchForm_searchFun();
													$('#pi_wip_turnkey_dg').datagrid('clearSelections');
													$('#pi_wip_turnkey_detail_dg').datagrid('load', {});
													var obj = jQuery.parseJSON(r);
													var objs = obj.msg.split('_');
													$('#pi_wip_po_dialog').dialog('close');
													$('#pi_wip_po_form').form('clear');
													$('#trunkey_createdUserName').val($('#session_user_name').val());
													$.messager.progress('close');
													$.messager.show({
														title : '提示',
														msg : objs[0]
													});
													$.ajax({
														type : 'post',
														url : 'orderAction!completedProductOrderAndInvokeWebService.action',
														data : {
															id : objs[1],
															ipn : objs[2],
															tpn : objs[3],
														},
														dataType : 'json',
														success : function(d) {
														}
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
				<td>前3码(1-3)</td>
				<td><select id="ipn_one" name="ipn_one" class="easyui-combobox" data-options="data: [{
					name: 'CEA',
					description: 'CEA:工程'
				},{
					name: 'CMA',
					description: 'CMA:量产'
				}],required:true,valueField:'name',textField:'description',width:200"></select></td>
			</tr>
			<tr>
				<td>IPN(4-9)</td>
				<td><input id = "ipn_ipn" name="ipn_ipn" class="easyui-textbox" data-options="required:true,onChange : function(newValue,oldValue){fullOfOptions_new()}" style="margin-left:0px"/></td>
			</tr>
			<tr>
				<td>Product Series(10)</td>
				<td><select id="ipn_three"  name="ipn_three" class="easyui-combobox" data-options="required:true,valueField:'ipn_three',textField:'ipn_three_name',width:200,onSelect: function(rec){fullOfOptions_new_filter(rec.ipn_three,'ipn_three')}"></select></td>
			</tr>
			<tr>
				<td>Density(11)</td>
				<td><select id="ipn_four" name="ipn_four" class="easyui-combobox" data-options="required:true,valueField:'ipn_four',textField:'ipn_four_name',width:200,onSelect: function(rec){fullOfOptions_new_filter(rec.ipn_four,'ipn_four')}"></select></td>
			</tr>
			<tr>
				<td>CP Tester(12)</td>
				<td><select id="ipn_five" name="ipn_five" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'ipn_five',textField:'ipn_five_name',width:200,onSelect: function(rec){fullOfOptions_new_filter(rec.ipn_five,'ipn_five')}"></select></td>
			</tr>
			<tr>
				<td>CP Test flow(13-14)</td>
				<td><select id="ipn_six" name="ipn_six" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'ipn_six',textField:'ipn_six',width:200,onSelect: function(rec){fullOfOptions_new_filter(rec.ipn_six,'ipn_six')}"></select></td>
			</tr>
			<tr>
				<td>Application Area(15)</td>
				<td><select id="ipn_seven" name="ipn_seven" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'ipn_seven',textField:'ipn_seven_name',width:200,onSelect: function(rec){fullOfOptions_new_filter(rec.ipn_seven,'ipn_seven')}"></select></td>
			</tr>
			<tr style="display:none">
				<td><input name="createdUserName" id="trunkey_createdUserName" ><input name="ipn_ids" id="ipn_ids" ><input name="cancel_ids" id="cancel_ids"></td>
			</tr>
			<!-- <tr>
				<td colspan="2">Prod生成规则</td>
			</tr> -->
			<tr>
				<td>Prod List</td>
				<td><select id="prod_name" name="prod_name" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'testingPID',textField:'testingPID',width:200,onSelect:function(d){$('#cpn_name').combobox('reload','wipAction!getContentOfCpn.action?pn='+d.testingPID)}"></select></td>
			</tr>
			<tr>
				<td>CPN List</td>
				<td><select id="cpn_name" name="cpn_name" class="easyui-combobox easyui-validatebox" data-options="required:true,valueField:'cpn',textField:'cpn',width:200"></select></td>
			</tr>
			<!-- <tr>
				<td colspan="2">Fab Id</td>
			</tr> -->
			<tr>
				<td>Fab Id</td>
				<td>
					<select id="fabSite" name="fabSite" class="easyui-combobox">
					 	<option value="XMC">XMC</option>
						<option value="SMIC">SMIC</option>
						<option value="GIGA">GIGA</option>
						<option value="GIGA_HK">GIGA_HK</option>
						<option value="GIGA_BJ">GIGA_BJ</option>
						<option value="GIGA_SH">GIGA_SH</option>
						<option value="GIGA_HF">GIGA_HF</option>
					</select>
					<span id="hasAuto" style="color:green">已经自动赋值</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="color:red">注:如12-14为STR,则其它参数可手动录入,不验证TPN</td>
			</tr>
		</table>
	</form>
</div>
</div>
