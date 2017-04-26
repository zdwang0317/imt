<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/* $(function() {
		$('#pi_wip_dg').datagrid({
			
		});
	}); */
	
	function pi_wip_searchForm_searchFun() {
		if($('#pi_wip_searchForm').form('validate')){
			$('#pi_wip_dg').datagrid('load', serializeObject($('#pi_wip_searchForm')));
			$('#pi_wip_download_productNo').val($('#pi_wip_searchForm_productNo').val());
			$('#pi_wip_download_lid').val($('#pi_wip_searchForm_lid').val());
			$('#pi_wip_download_tpnFlow').val($('#pi_wip_searchForm_tpnFlow').val());
			$('#pi_wip_download_erpDate').val($("form[id='pi_wip_searchForm'] input[name='erpDate']").val());
			$('#pi_wip_download_site').val($('#pi_wip_searchForm_site').val());
			$('#pi_wip_download_cpn').val($('#pi_wip_searchForm_cpn').val());
			$('#pi_wip_download_ipn').val($('#pi_wip_searchForm_ipn').val());
			$('#pi_wip_download_stage').val($('#pi_wip_searchForm_stage').val());
			$('#pi_wip_download_status').val($('#pi_wip_searchForm_status').val());
		}
	}
</script>
<div id="pi_wip_layout" class="easyui-layout" data-options="fit:true,border:false">
	<!-- <div data-options="region:'north',title:'查询条件'" style="height: 90px;background:#eee;">
		<form id="pi_wip_searchForm">
			<table>
				<tr>
					<td>Product No</td><td><input id="pi_wip_searchForm_productNo" name="productNo" /></td>
					<td>Lot</td><td><input id="pi_wip_searchForm_lid" name="lid" /></td>
					<td>TPN Flow</td><td><input id="pi_wip_searchForm_tpnFlow" name="tpnFlow" /></td>
					<td>Site</td><td><input id="pi_wip_searchForm_site" name="location" /></td>
					<td>Wip Date</td><td><input id="pi_wip_searchForm_erpDate" class="easyui-datebox" name ="erpDate"></td>
				</tr>
				<tr>
					<td>CPN</td><td><input id="pi_wip_searchForm_cpn" name="cpn" /></td>
					<td>IPN</td><td><input id="pi_wip_searchForm_ipn" name="ipn" /></td>
					<td>Stage</td><td><input id="pi_wip_searchForm_stage" name="stage" /></td>
					<td>Status</td><td><input id="pi_wip_searchForm_status" name ="status"></td>
					<td></td><td></td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pi_wip_searchForm_searchFun();">查询</a></td>
				</tr>
			</table>
		</form>
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
		<table class="easyui-datagrid" id="pi_wip_dg"  data-options="url : 'wipAction!datagrid.action',
			fit : true,
			method : 'post',
			//fitColumns : true,
			rowNumber : true,
			border : false,
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ,10000],
			frozenColumns : [ [ {
				field : 'lid',
				title : 'Lot ID',
				width : 100,
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
				field : 'qty',
				title : 'Qty',
				width : 50,
			}, {
				field : 'wid',
				title : 'Wafer Id',
				width : 300,
			}, {
				field : 'startDate',
				title : 'Recived Date',
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
				title : 'Site',
				width : 150,
			}, {
				field : 'productNo',
				title : 'Product No',
				width : 150,
			} , {
				field : 'tpnFlow',
				title : 'Wip FLOW',
				width : 150,
			}  ] ],
			/* toolbar:[{
				id:'excel-download',
				text:'导出',
				disabled:true,
				iconCls:'icon-zd-excel-download',
				handler:function(){
					$('#pi_wip_download').submit();
				}
			}] */
			toolbar : '#pi_wip_dg_toolbar',
			onLoadSuccess : function(data){
				var pager = $('#pi_wip_dg').datagrid('getPager');    // 得到datagrid的pager对象
				pager.pagination({    
					    buttons:[{
					    	text: '导出',
					        iconCls:'icon-zd-excel-download',    
					        handler:function(){
					        	var date = $('#pi_wip_download_erpDate').val();
					        	if(date){
					        		$('#pi_wip_download').submit();
					        	}else{
					        		$.messager.show({
										title : '提示',
										msg : '日期不能为空'
									});
					        	}
					        }    
					    }]
					});
			}
			"></table>
		<div id="pi_wip_dg_toolbar">
			<div>
				<form id="pi_wip_searchForm">
					<table>
						<tr>
							<td align="right">Lot ID</td>
							<td><input id="pi_wip_searchForm_lid" name="lid" class="easyui-textbox" style="width:100px"/></td>
							<td align="right">Part No</td>
							<td><input id="pi_wip_searchForm_productNo" name="pn" class="easyui-textbox" style="width:100px"/></td>
							<td align="right">CPN</td>
							<td><input id="pi_wip_searchForm_cpn" name="cpn" class="easyui-textbox" style="width:100px"/></td>
							<td align="right">IPN</td>
							<td><input id="pi_wip_searchForm_ipn" name="ipn" class="easyui-textbox" style="width:100px"/></td>
							<td align="right">Stage</td>
							<td><input id="pi_wip_searchForm_stage" name="stage" class="easyui-textbox" style="width:100px"/></td>
							<td>Wip Flow</td>
							<td><input id="pi_wip_searchForm_tpnFlow" name="tpnFlow" class="easyui-textbox" style="width:100px"/></td>
							
						</tr>
						<tr>
							<td align="right">Status</td>
							<td><input id="pi_wip_searchForm_status" name="status" class="easyui-textbox" style="width:100px"></td>
							<td align="right">Date</td>
							<td><input id="pi_wip_searchForm_erpDate"
								class="easyui-datebox" name="erpDate" style="width:100px" data-options="required:true"></td>
							<!-- <td align="right">Thru Date</td>
							<td><input id="pi_wip_searchForm_erpDate"
								class="easyui-datebox" name="thruDate" style="width:100px"></td> -->
							<td align="right">Site</td>
							<td>
								<select id="pi_wip_searchForm_site" name="firm" class="easyui-combobox" style="width:100px">
									<option></option>
									<option value="chipmos">chipmos</option>
									<option value="chipmosSH">chipmosSH</option>
									<option value="csmc">csmc</option>
									<option value="klt">klt</option> 
									<option value="smic">smic</option>
									<option value="xmc">xmc</option>
									<option value="hlmc">hlmc</option>
									<option value="umc">umc</option>
									<option value="sjsemi">sjsemi</option>
								</select>
							</td>
							<td colspan="7"></td>
							<td colspan="1" align="center"><a href="#" class="easyui-linkbutton"
								data-options="iconCls:'icon-search',plain:true"
								onclick="pi_wip_searchForm_searchFun();">查询</a></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- <div>
				<a href="#" id="excel-download" class="easyui-linkbutton" data-options="disabled:true" iconCls="icon-zd-excel-download" plain="true" onclick="javascript:$('#pi_wip_download').submit();">导出</a>
			</div> -->
		</div>
</div>

