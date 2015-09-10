<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#tpn_nand_rule_one').datagrid({
		border : false,
		rownumbers : true,
		fit : true,
		columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_nand_rule_three').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_nand_rule_four').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_nand_rule_five').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_nand_rule_six').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_nand_rule_eight').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_nand_rule_night').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $.ajax({
		url : 'wipAction!getContentOfOption.action',
		dataType : 'json',
		success : function(d) {
			for(var i=0;i<d.length;i++){
				var type = d[i].type;
				if(type=="TPNNAND_ONE"){
					$('#tpn_nand_rule_one').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_THREE"){
					$('#tpn_nand_rule_three').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_FOUR"){
					$('#tpn_nand_rule_four').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_FIVE"){
					$('#tpn_nand_rule_five').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_SIX"){
					$('#tpn_nand_rule_six').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_SEVEN"){
					$('#tpn_nand_rule_eight').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="FTTPN_Flow"){
					$('#tpn_nand_rule_night').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
			}
		}
	});
})
</script>

<div class="easyui-accordion" style="" data-options="fit:true,border:false">
	<div title="Prod ID(FT-TPN第四六码)" data-options="iconCls:''" >
		<table id="tpn_nand_rule_one"></table>
	</div>
	<div title="Product Series(FT-TPN第七码) " data-options="iconCls:''" >
		<table id="tpn_nand_rule_three"></table>
	</div>
	<div title="Density(FT-TPN第八码)" data-options="iconCls:''" >
		<table id="tpn_nand_rule_four"></table>
	</div>
	<div title="Package Type(FT-TPN第九码)" data-options="iconCls:''" >
		<table id="tpn_nand_rule_five"></table>
	</div>
	<div title="Test Site(FT-TPN第十码)" data-options="iconCls:''" >
		<table id="tpn_nand_rule_six"></table>
	</div>
	<div title="Test Tool(FT-TPN倒数第五码)" data-options="iconCls:''" >
		<table id="tpn_nand_rule_eight"></table>
	</div>
	<div title="Test Flow(FT-TPN倒数第三四码)" data-options="iconCls:''" >
		<table id="tpn_nand_rule_night"></table>
	</div>
</div>