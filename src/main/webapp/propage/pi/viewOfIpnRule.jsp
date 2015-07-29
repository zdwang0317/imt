<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#ipn_rule_one').datagrid({
		border : false,
		rownumbers : true,
		fit : true,
		columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#ipn_rule_three').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#ipn_rule_four').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#ipn_rule_five').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#ipn_rule_six').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $('#ipn_rule_eight').datagrid({
    	columns:[[{field:'name',title:'值',width : 100,},
    	          {field:'description',title:'描述',width : 500,},
        ]]
    });
    $.ajax({
		url : 'wipAction!getContentOfOption.action',
		dataType : 'json',
		success : function(d) {
			console.info(d);
			for(var i=0;i<d.length;i++){
				var type = d[i].type;
				if(type=="IPN_ONE"){
					$('#ipn_rule_one').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_THREE"){
					$('#ipn_rule_three').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_FOUR"){
					$('#ipn_rule_four').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_FIVE"){
					$('#ipn_rule_five').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_SIX"){
					$('#ipn_rule_six').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				if(type=="IPN_EIGHT"){
					$('#ipn_rule_eight').datagrid('appendRow',{
						name: d[i].name,
						description : d[i].description
					}); 
				}
				 
			}
		}
	});
})
</script>
<!-- <div>
	<table id="ipn_rule_one"></table>
</div>
<div style="">
	<table id="ipn_rule_three"></table>
</div>
<div >
	<table id="ipn_rule_four"></table>
</div>
<div >
	<table id="ipn_rule_five"></table>
</div>
<div >
	<table id="ipn_rule_six"></table>
</div> -->

<div class="easyui-accordion" style="" data-options="fit:true">
	<div title="CP IPN 前三码" data-options="iconCls:''" >
		<table id="ipn_rule_one"></table>
	</div>
	<div title="Product Series (CP IPN倒数第六码)" data-options="iconCls:''" >
		<table id="ipn_rule_three"></table>
	</div>
	<div title="Density (CP IPN倒数第五码)" data-options="iconCls:''" >
		<table id="ipn_rule_four"></table>
	</div>
	<div title="CP Tester (CP IPN倒数第四码)" data-options="iconCls:''" >
		<table id="ipn_rule_five"></table>
	</div>
	<div title="CP Test flow (CP IPN倒数第二三码)" data-options="iconCls:''" >
		<table id="ipn_rule_six"></table>
	</div>
	<div title="Application Area (CP IPN末位码)" data-options="iconCls:''" >
		<table id="ipn_rule_eight"></table>
	</div>
</div>