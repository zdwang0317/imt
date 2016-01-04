<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
/* 	$(function() {
		$('#pi_wip_unique_dg').datagrid({
			url : 'wipDetailUniqueAction!datagrid.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 50,
			rownumbers : true,
			pageList : [ 50 ,100,200],
			columns : [ [ {field : 'lid',title : 'Lot Id',width : 100,},{field : 'wid',title : 'Wafer Id',width : 100,} ,{
				field : 'pn',
				title : 'PartNo',
				width : 100,
			},{
				field : 'cpn',
				title : 'CPN',
				width : 100,
			}, {
				field : 'ipn',
				title : 'IPN(Old)',
				width : 100,
			}, {
				field : 'ipn_new',
				title : 'IPN(New)',
				width : 100,
			}, {
				field : 'status',
				title : 'STATUS',
				width : 100,
			} ] ],
			toolbar:'#tb'
		});
	}) */
		
	
	function pi_wip_unique_searchForm() {
		$('#pi_wip_unique_dg').datagrid('load', serializeObject($('#pi_wip_unique_searchForm')));
	}
	
</script>
<div id="pi_wip_unique_layout" class="easyui-layout" data-options="border:false,fit:true">
	<table class="easyui-datagrid" id="pi_wip_unique_dg"
		data-options="pageSize : 50,fit:true,border:false,
		pageList : [ 50 ,100,200],
		pagination : true,
		rownumbers:true,
		singleSelect:true,
		url:'wipDetailUniqueAction!datagrid.action',
		queryParams: {id: '0'},
		method:'get',
		toolbar:'#pi_wip_unique_dg_toolbar',
		onBeforeLoad : function(para){
			console.info(para);
		},
		onLoadSuccess : function(data){
			console.info(data);
			var pager = $('#pi_wip_unique_dg').datagrid('getPager');    // 得到datagrid的pager对象  
			pager.pagination({    
			    buttons:[{
			    	text: 'Download Data',
			        iconCls:'icon-zd-excel-download',    
			        handler:function(){
			        	if(data.total<150000){
			        		$('#pi_wip_unique_searchForm').form('submit', {
								url: 'wipDetailUniqueAction!excelToUniqueWipByJdbc.action',
								dataType : 'json',
							});
			        	}else{
			        		$.messager.show({
								title : '提示',
								msg : '数据量过大,请过滤后导出（控制在50000条以内）'
							});
			        	}
						
			        }    
			    },{
			    	text: 'Update Tpn',
			        iconCls:'icon-zd-excel-download',    
			        handler:function(){
			        	$('#upload_wip_data_dialog').dialog('open');
			        }    
			    },{
			    	text: 'Add Wip',
			        iconCls:'icon-add',    
			        handler:function(){
			        	$('#add_wip_data_dialog').dialog('open');
			        }    
			    }]
			});
		}
		">
		<thead>
			<tr>
				<th data-options="field:'lid',width:100">LotId</th>
				<th data-options="field:'wid',width:40">Wafer</th>
				<th data-options="field:'pn',width:100">PartNo</th>
				<th data-options="field:'cpn',width:100">CPN</th>
				<th data-options="field:'ipn',width:120">IPN(Old)</th>
				<th data-options="field:'ipn_new',width:120">IPN(New)</th>
				<th data-options="field:'tpn',width:120">TPN(New)</th>
				<th data-options="field:'tpnFlow',width:60">Wip Flow</th>
				<th data-options="field:'status',width:100">Status</th>
				<th data-options="field:'waferType',width:100">Wafer Type</th>
				<th data-options="field:'piStatus',width:100">Pi_Status</th>
				<th data-options="field:'probeCount',width:100">Probe_count</th>
				<th data-options="field:'erpProgram',width:100">final_CP_PGM</th>
				<th data-options="field:'erpProgramTime',width:100">final_CP_yield</th>
			</tr>
		</thead>
	</table>
	<div id="pi_wip_unique_dg_toolbar">
		<!-- <div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true"></a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-save" plain="true"></a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a> <a
				href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true"></a>
		</div> -->
		<div>
			<form id="pi_wip_unique_searchForm">
				<table>
					<tr>
						<td align="right">LotId</td>
						<td><input name="lid" class="easyui-textbox" style="width:100px"/><input type="hidden" name="id" value="1"/></td>
						<td align="right">Part No</td>
						<td><input name="pn" class="easyui-textbox" style="width:100px"/></td>
						<td align="right">CPN</td>
						<td><input name="cpn" class="easyui-textbox" style="width:100px"/></td>
						<td align="right">IPN(Old)</td>
						<td><input name="ipn" class="easyui-textbox" style="width:100px"/></td>
						<td align="right">IPN(New)</td>
						<td><input name="ipn_new" class="easyui-textbox" style="width:100px"/></td>
						<td align="right">TPN</td>
						<td><input name="tpn" class="easyui-textbox" style="width:100px"/></td>
						<td align="right">PI_STATUS</td>
						<td>
							    <select class="easyui-combobox" name="piStatus" style="width:100px;" data-options="multiple:true">
							    	<option value="">空</option>
								    <option value="In FAB">In FAB</option>
								    <option value="WAT Finish">WAT Finish</option>
								    <option value="CP1 Finish">CP1 Finish</option>
								    <option value="CP2 Finish">CP2 Finish</option>
								    <option value="CP3 Finish">CP3 Finish</option>
								    <option value="CP4 Finish">CP4 Finish</option>
								    <option value="CP5 Finish">CP5 Finish</option>
								    <option value="CP6 Finish">CP6 Finish</option>
								    <option value="System to do">System to do</option>
								    <option value="TE to do">TE to do</option>
								    <option value="TE hold">TE hold</option>
								    
								    <option value="PDE to do">PDE to do</option>
								    <option value="PDE hold">PDE hold</option>
								    <option value="QRA to do">QRA to do</option>
								    <option value="QRA hold">QRA hold</option>
								    <option value="MKT to do">MKT to do</option>
								    <option value="MKT hold">MKT hold</option>
								    <option value="OP to do">OP to do</option>
								    <option value="OP hold">OP hold</option>
								    <option value="ERP to do">ERP to do</option>
								    <option value="rework">rework</option>
								    <option value="Merge">Merge</option>
								    <option value="INK">INK</option>
								    <option value="Finish">Finish</option>
								    <option value="Scrap/RMA">Scrap/RMA</option>
							    </select>
						</td>
					</tr>
					<tr>
						<td align="right">Wip FLow</td>
						<td><input name="tpnFlow" class="easyui-textbox" style="width:100px"/></td>
						<td align="right">Wafer Type</td>
						<td><input name="waferType" class="easyui-textbox" style="width:100px"/></td>
						<td><a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-search',plain:true"
							onclick="pi_wip_unique_searchForm();">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>


<!-- <div data-options="region:'north',title:'查询条件'" style="height: 65px;background:#eee;">
		<form id="pi_wip_unique_searchForm">
			<table>
				<tr>
					<td>LotId</td><td><input name="lid" /></td>
					<td>CPN</td><td><input  name="cpn" /></td>
					<td>IPN(Old)</td><td><input  name="ipn" /></td>
					<td>IPN(New)</td><td><input name="ipn_new" /></td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pi_wip_unique_searchForm();">查询</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',split:true,title:''">
			<table id="pi_wip_unique_dg"></table>
	</div> -->
	<!-- <table class="easyui-datagrid"
		data-options="
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 50,
			rownumbers : true,
			pageList : [ 50 ,100,200],
			url:'wipDetailUniqueAction!datagrid.action',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field : 'lid',width : 100">Lot Id</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true"></a>
		</div>
		<div>
			Date From: <input class="easyui-datebox" style="width: 80px">
			To: <input class="easyui-datebox" style="width: 80px">
			Language: <select class="easyui-combobox" panelHeight="auto"
				style="width: 100px">
				<option value="java">Java</option>
				<option value="c">C</option>
				<option value="basic">Basic</option>
				<option value="perl">Perl</option>
				<option value="python">Python</option>
			</select> <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
		</div>
	</div> -->
	<div style="display:none">
		<form id="pi_wip_download" action="wipAction!excelTest.action" method="post">
			<input id="pi_wip_download_productNo" name="pn" />
			<input id="pi_wip_download_lid" name="lid" />
			<input id="pi_wip_download_tpnFlow" name="tpnFlow" />
			<input id="pi_wip_download_erpDate" name ="erpDate">
			<input id="pi_wip_download_site" name ="firm">
			<input id="pi_wip_download_cpn" name ="cpn">
			<input id="pi_wip_download_ipn" name ="ipn">
			<input id="pi_wip_download_stage" name ="stage">
			<input id="pi_wip_download_status" name ="status">
		</form>
	</div>
	<div id="upload_wip_data_dialog" class="easyui-dialog" data-options="title: 'Upload File',
								width: 300,
								closed: true,
								height: 120,
								cache: false,
								modal: true,
								buttons : [ {
									text : '更新',
									//iconCls : 'icon-add',
									handler : function() {
										if($('#upload_data_for_update_tpn').form('validate')){
											$.messager.progress();
											$('#upload_data_for_update_tpn').form('submit', {
												url : 'lotToHoldAction!UploadDataForUpdateTpn.action',
												datatype : 'json',
												success : function(r) {
													var obj = jQuery.parseJSON(r);
													$('#upload_wip_data_dialog').dialog('close');
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
			<form id="upload_data_for_update_tpn" method="post" enctype="multipart/form-data">
				<!-- <input type="file" name="lotfile" id="lotfile" multiple="multiple" /> -->
				<input class="easyui-filebox" name="lotfile" id="lotfile" data-options="required : true" style="width:100%"/>
			</form>
		</div>
		<div id="add_wip_data_dialog" class="easyui-dialog" data-options="title: 'Add Wip',
								width: 400,
								closed: true,
								height: 320,
								cache: false,
								modal: true,
								buttons : [ {
									text : '添加',
									//iconCls : 'icon-add',
									handler : function() {
										if($('#add_wip').form('validate')){
											$.messager.progress();
											$('#add_wip').form('submit', {
												url : 'wipDetailUniqueAction!addWip.action',
												datatype : 'json',
												success : function(r) {
													$('#add_wip_data_dialog').dialog('close');
													$.messager.progress('close');
													$.messager.show({
														title : '提示',
														msg : '添加成功'
													});
												}
											});
										}
									}
								} ]
								" style="padding:10px;">
			<form id="add_wip" method="post">
				<table>
					<tr><td>Lot Id</td><td><input name="lid" class="easyui-textbox" data-options="required:true" style="margin-left:0px"/></td></tr>
					<tr><td>Wafer Id</td><td><input name="wid" class="easyui-textbox" data-options="required:true" style="margin-left:0px"/>例：1-3,5,6,11-16</td></tr>
					<tr><td>CPN</td><td><input name="cpn" class="easyui-textbox" style="margin-left:0px"/></td></tr>
					<tr><td>IPN</td><td><input name="ipn" class="easyui-textbox" style="margin-left:0px"/></td></tr>
					<tr><td>Part No</td><td><input name="pn" class="easyui-textbox" data-options="required:true" style="margin-left:0px"/></td></tr>
				</table>
			</form>
		</div>