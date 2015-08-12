<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#tpn_rule_one').datagrid({
		border : false,
		rownumbers : true,
		fit : true,
		columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_rule_three').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_rule_four').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_rule_five').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_rule_six').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_rule_eight').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpn_rule_night').datagrid({
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
				if(type=="TPN_ONE"){
					$('#tpn_rule_one').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_THREE"){
					$('#tpn_rule_three').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_FOUR"){
					$('#tpn_rule_four').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_FIVE"){
					$('#tpn_rule_five').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPN_SIX"){
					$('#tpn_rule_six').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_EIGHT"){
					$('#tpn_rule_eight').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="FTTPN_Flow"){
					$('#tpn_rule_night').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
			}
		}
	});
})
</script>

<div class="easyui-accordion" style="" data-options="fit:true">
	<div title="Prod ID(FT-TPN第四五码)" data-options="iconCls:''" >
		<table id="tpn_rule_one"></table>
	</div>
	<div title="Product Series(FT-TPN第七码) " data-options="iconCls:''" >
		<table id="tpn_rule_three"></table>
	</div>
	<div title="Density(FT-TPN第八码)" data-options="iconCls:''" >
		<table id="tpn_rule_four"></table>
	</div>
	<div title="Package Type(FT-TPN第九码)" data-options="iconCls:''" >
		<table id="tpn_rule_five"></table>
	</div>
	<div title="Test Site(FT-TPN第十码)" data-options="iconCls:''" >
		<table id="tpn_rule_six"></table>
	</div>
	<div title="Test Tool(FT-TPN倒数第五码)" data-options="iconCls:''" >
		<table id="tpn_rule_eight"></table>
	</div>
	<div title="Test Flow(FT-TPN倒数第三四码)" data-options="iconCls:''" >
		<table id="tpn_rule_night"></table>
	</div>
</div>