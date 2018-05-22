<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var index_value = ['NOR','SPI_NAND','Para_Nand','MCP','eMCP_eMMC','MCU','DRAM','W_nonGiga'];
var key2_array = ['CP','Assy.','CPN','CP','Assy.','NOR_KGD8'];
var special_key2 = ['NOR_KGD8','NOR_KGD6','NOR_Digit5','NOR_Para','NOR_MCP'];
             
function fillTableValue(title,index){
	var main_index = $('#tab_main').tabs('getTabIndex',$('#tab_main').tabs('getSelected'))
	var s_index = $('#s_'+main_index+'').tabs('getTabIndex',$('#s_'+main_index+'').tabs('getSelected'));
	var key1 = index_value[main_index];
	var key2 = ""
	if(((key1=='NOR'||key1=='SPI_NAND')&&s_index==4&&(index==7||index==8||index==9))||(key1=='MCU'&&s_index==4&&(index==8||index==10))){
		key2 = "FT";
	}else if(key1=='MCP'&&s_index==4&&(index==10||index==11)){
		key2 = "FT";
	}else{
		if(s_index>4){
			key2 = special_key2[s_index-5];
		}else{
			key2 = key2_array[s_index];
		}
	}
	if(main_index==7){
		if(s_index>0){
			key2 = "Vendor Code";
		}else{
			key2 = "WIPN";
		}
	}
	var table_id = main_index+'_'+s_index+'_'+index;
	$('#'+main_index+'_'+s_index+'_'+index+'').datagrid({
		url : 'poAction!getNewRuleByCondition.action',
		queryParams: {key1: key1,key2: key2,key3: title},
    	border : true,
		fit : false,
    	columns:[[{field:'name',title:'值'},
    	          {field:'description',title:'描述',width:150},	
    	          {field:'field1',title:'field1',width:100},
    	          {field:'field2',title:'field2',width:100},
    	          {field:'field3',title:'field3',width:100},
    	          {field:'field4',title:'field4',width:100},
    	          {field:'field5',title:'field5',width:100},
        ]],
        onLoadSuccess:function(data){
        	console.info(data);
        }
    });
}
function fillTableHeader(title,index){
	var main_index = $('#tab_main').tabs('getTabIndex',$('#tab_main').tabs('getSelected'));
	var key2 = index_value[main_index];
	if(index>4){
		title = special_key2[index-5];
	}
	$.ajax({
		type : 'post',
		data : 'key1='+title+'&key2='+key2,
		dataType : 'json',
		url : 'poAction!getNewRuleHeader.action',
		success : function(r) {
			var rows = r.rows;
			var j = 0 ;
			for(var i=0;i<rows.length;i++){
				var obj = rows[i];
				var isHas = $('#last_'+main_index+'_'+index+'').tabs('getTab',i);
				if(isHas){
					break;
				}else{
					var title = obj.title;
					if(title=='N'){
						title = '-';
					}
					$('#last_'+main_index+'_'+index+'').tabs('add',{
						selected: false,
						title: title,
						content: '<div style="padding:10px"><table id="'+main_index+'_'+index+'_'+j+'"></table></div>',
						closable: false,
					});
					j++;
				}
			}
		}
	});
}
</script>
<div title="Sub Tabs" style="height:750px;width:100%">
	<div id="tab_main" class="easyui-tabs" data-options="tabWidth:112,border: false,fit:true,plain:true,selected: false">
		<div title="Nor Flash" style="padding:5px;">
			<div id="s_0" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_0_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_0_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_0_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_0_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_0_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN-KGD8" style="padding:5px;">
					<div id="last_0_5" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN-KGD6" style="padding:5px;">
					<div id="last_0_6" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN-Digit5" style="padding:5px;">
					<div id="last_0_7" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN-Para" style="padding:5px;">
					<div id="last_0_8" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN-MCP" style="padding:5px;">
					<div id="last_0_9" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="SPI_Nand" style="padding:5px;">
			<div id="s_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_1_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_1_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_1_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_1_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_1_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="Para_Nand" style="padding:5px;">
			<div id="s_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_2_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_2_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_2_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_2_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_2_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="MCP" style="padding:5px;">
			<div id="s_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_3_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_3_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_3_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_3_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_3_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="eMCP_eMMC" style="padding:5px;">
			<div id="s_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_4_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_4_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_4_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_4_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_4_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="MCU" style="padding:5px;">
			<div id="s_5" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_5_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_5_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_5_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_5_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_5_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="DRAM" style="padding:5px;">
			<div id="s_6" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="CPIPN" style="padding:5px;">
					<div id="last_6_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTIPN" style="padding:5px;">
					<div id="last_6_1" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPN" style="padding:5px;">
					<div id="last_6_2" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="CPTPN" style="padding:5px;">
					<div id="last_6_3" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="FTTPN" style="padding:5px;">
					<div id="last_6_4" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
		<div title="W-nonGiga" style="padding:5px;">
			<div id="s_7" class="easyui-tabs" data-options="border: false,fit:true,plain:true,selected: false,onSelect:function(title,index){fillTableHeader(title,index)}">
				<div title="WIPN" style="padding:5px;">
					<div id="last_7_0" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
				<div title="Vendor Code&Density Table" style="padding:5px;">
					<div id="last_7_1" class="easyui-tabs" data-options="border: false,plain:true,selected: false,onSelect:function(title,index){fillTableValue(title,index)}">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>