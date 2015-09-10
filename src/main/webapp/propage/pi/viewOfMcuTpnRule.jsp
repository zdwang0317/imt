<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#tpnmcu_rule_two').datagrid({
		border : false,
		rownumbers : true,
		fit : true,
		columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_three').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_four').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_five').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_six').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_seven').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_eight').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_night').datagrid({
    	border : false,
		rownumbers : true,
		fit : true,
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#tpnmcu_rule_ten').datagrid({
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
				if(type=="TPNMCU_TWO"){
					$('#tpnmcu_rule_two').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_THREE"){
					$('#tpnmcu_rule_three').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_FOUR"){
					$('#tpnmcu_rule_four').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_FIVE"){
					$('#tpnmcu_rule_five').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_SIX"){
					$('#tpnmcu_rule_six').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_SEVEN"){
					$('#tpnmcu_rule_seven').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_TEN"){
					$('#tpnmcu_rule_eight').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_EIGHT"){
					$('#tpnmcu_rule_night').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="TPNMCU_NIGHT"){
					$('#tpnmcu_rule_ten').datagrid('appendRow',{
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
	<div title="Stack Flach ID(FT-TPN第二码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_two"></table>
	</div>
	<div title="PROD ID(FT-TPN第三四码) " data-options="iconCls:''" >
		<table id="tpnmcu_rule_three"></table>
	</div>
	<div title="Prod Series(FT-TPN第六码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_four"></table>
	</div>
	<div title="Pin Count(FT-TPN第七码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_five"></table>
	</div>
	<div title="Flash Memory Size(FT-TPN第八码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_six"></table>
	</div>
	<div title="Package(FT-TPN第九码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_seven"></table>
	</div>
	<div title="Test Side(FT-TPN第十码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_eight"></table>
	</div>
	<div title="Test Tool(FT-TPN倒数第五码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_night"></table>
	</div>
	<div title="Test Flow(FT-TPN倒数第三四码)" data-options="iconCls:''" >
		<table id="tpnmcu_rule_ten"></table>
	</div>
</div>