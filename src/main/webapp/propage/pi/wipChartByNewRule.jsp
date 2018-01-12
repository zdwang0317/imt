<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var log_ = 0;
var log1_ = 0;
var nand_ = 0;
$(function() {
	$('#MainTab').tabs({
		border:false,
        fit : true,
        plain : true,
        cache : true,
        onSelect:function(title,index){
        	if(index==1&&log_==0){
        		log_ = 1;        		
        		$('#wipChart_logic').tabs({
        	        border:false,
        	        fit : true,
        	        plain : true,
        	        cache : true,
        	        onSelect:function(title,index){
        	        	var selectTab = $('#wipChart_logic').tabs('getTab',index);
        	        	var ruleId = selectTab[0].id;
        	        	$.ajax({
        	    			type : 'post',
        	    			data : 'ruleTypeId=LOGIC&ruleId='+ruleId,
        	    			dataType : 'json',
        	    			url : "tpnRuleAction!showHighChartByNewRule.action",
        	    			success : function(r) {
        	    				generateChart('chart_'+ruleId,r.dataList,r.msg);
        	    			}
        	    		});
        	        }
        	    });
        	    $.ajax({
        			type : 'post',
        			data : 'ruleTypeId=LOGIC',
        			dataType : 'json',
        			url : "tpnRuleAction!getRuleHeaderFromRuleTypeId.action",
        			success : function(r) {
        				var rows = r.rows;
        				for(var i=0;i<rows.length;i++){
        					var obj = rows[i];
        					$('#wipChart_logic').tabs('add',{
        						id : obj.ruleId,
        						selected: false,
        						title: obj.ruleName,
        						content: '<div style="padding:10px"><div id ="chart_'+obj.ruleId+'" style="height: 650px"></div></div>',
        						closable: false
        					}); 
        				}
        			}
        		});
        	}
        	if(index==3&&log1_==0){
        		log1_ = 1;        		
        		$('#wipChart_logic1').tabs({
        	        border:false,
        	        fit : true,
        	        plain : true,
        	        cache : true,
        	        onSelect:function(title,index){
        	        	var selectTab = $('#wipChart_logic1').tabs('getTab',index);
        	        	var ruleId = selectTab[0].id;
        	        	$.ajax({
        	    			type : 'post',
        	    			data : 'ruleTypeId=LOGIC1&ruleId='+ruleId,
        	    			dataType : 'json',
        	    			url : "tpnRuleAction!showHighChartByNewRule.action",
        	    			success : function(r) {
        	    				generateChart('chart_'+ruleId,r.dataList,r.msg);
        	    			}
        	    		});
        	        }
        	    });
        	    $.ajax({
        			type : 'post',
        			data : 'ruleTypeId=LOGIC1',
        			dataType : 'json',
        			url : "tpnRuleAction!getRuleHeaderFromRuleTypeId.action",
        			success : function(r) {
        				var rows = r.rows;
        				for(var i=0;i<rows.length;i++){
        					var obj = rows[i];
        					$('#wipChart_logic1').tabs('add',{
        						id : obj.ruleId,
        						selected: false,
        						title: obj.ruleName,
        						content: '<div style="padding:10px"><div id ="chart_'+obj.ruleId+'" style="height: 650px"></div></div>',
        						closable: false
        					}); 
        				}
        			}
        		});
        	}
        	if(index==2&&nand_==0){
        		nand_ = 1;        		
        		$('#wipChart_nand').tabs({
        	        border:false,
        	        fit : true,
        	        plain : true,
        	        cache : true,
        	        onSelect:function(title,index){
        	        	var selectTab = $('#wipChart_nand').tabs('getTab',index);
        	        	var ruleId = selectTab[0].id;
        	        	$.ajax({
        	    			type : 'post',
        	    			data : 'ruleTypeId=NAND&ruleId='+ruleId,
        	    			dataType : 'json',
        	    			url : "tpnRuleAction!showHighChartByNewRule.action",
        	    			success : function(r) {
        	    				generateChart('chart_'+ruleId,r.dataList,r.msg);
        	    			}
        	    		});
        	        }
        	    });
        	    $.ajax({
        			type : 'post',
        			data : 'ruleTypeId=NAND',
        			dataType : 'json',
        			url : "tpnRuleAction!getRuleHeaderFromRuleTypeId.action",
        			success : function(r) {
        				var rows = r.rows;
        				for(var i=0;i<rows.length;i++){
        					var obj = rows[i];
        					$('#wipChart_nand').tabs('add',{
        						id : obj.ruleId,
        						selected: false,
        						title: obj.ruleName,
        						content: '<div style="padding:10px"><div id ="chart_'+obj.ruleId+'" style="height: 650px"></div></div>',
        						closable: false
        					}); 
        				}
        			}
        		});
        	}
        }
	});
    $('#wipChart_flash').tabs({
        border:false,
        fit : true,
        plain : true,
        cache : true,
        onSelect:function(title,index){
        	var selectTab = $('#wipChart_flash').tabs('getTab',index);
        	var ruleId = selectTab[0].id;
        	$.ajax({
    			type : 'post',
    			data : 'ruleTypeId=FLASH&ruleId='+ruleId,
    			dataType : 'json',
    			url : "tpnRuleAction!showHighChartByNewRule.action",
    			success : function(r) {
    				generateChart('chart_'+ruleId,r.dataList,r.msg);
    			}
    		});
        }
    });
    $.ajax({
		type : 'post',
		data : 'ruleTypeId=FLASH',
		dataType : 'json',
		url : "tpnRuleAction!getRuleHeaderFromRuleTypeId.action",
		success : function(r) {
			var rows = r.rows;
			for(var i=0;i<rows.length;i++){
				var obj = rows[i];
				$('#wipChart_flash').tabs('add',{
					id : obj.ruleId,
					selected: false,
					title: obj.ruleName,
					content: '<div style="padding:10px"><div id ="chart_'+obj.ruleId+'" style="height: 650px"></div></div>',
					closable: false
				}); 
			}
		}
	});
});
function generateChart(id,list,msg){
	$('#'+id+'').highcharts({
		credits : {enabled:false},
        chart: {
            /* type: 'bar', */
            type: 'column',
        },
        title: {
            text: 'Wip Analysis Daily'
        },
        /* subtitle: {
            text: 'Source: WorldClimate.com'
        }, */
        xAxis: {
            categories: msg.split(','),
            title :{
            	text : 'WIP Flow'
            },
            labels: {
                rotation: -90,
                align: 'right',
                style: {font: 'bold 8px 微软雅黑'}
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Wip Quantity'
            },
            labels: {
                overflow: 'justify'
            }
            /* opposite:true */
        },
        legend: {
        	   labelFormatter: function() {
        	      var total = 0;
        	      for(var i=this.yData.length; i--;) { total += this.yData[i]; };
        	      return this.name + '——'+total;
        	   },
        	   itemWidth : 150
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0/* ,
                 dataLabels: {
                    enabled: true,
                    formatter: function() {  
                        if (this.y > 0)  
                            return this.y;
                    },
                    rotation: -90,
                } */
            }/* ,
            series: {
                events: {
                    show: redrawXaxis,
                    hide: redrawXaxis
                }
            } */
        },
        series: formatDataList(list)
    });
}
function formatDataList(list){
	var returnData =[];
	for(var i=0;i<list.length;i++){
		var obj = list[i];
		returnData[i] = {name:obj.name,data:obj.data,visible:obj.visible};
	}
	return returnData;
}
</script>
<div title="Sub Tabs" style="height:750px;width:100%">
	<div id="MainTab">
		<div title="NOR Flash" style="padding:5px;">
			<div id ="maincontainer" style="height: 700px;width:1100">
				<div id="wipChart_flash"></div>
			</div>
		</div>
		<div title="LOGIC" style="padding:5px;">
			<div id ="maincontainer2" style="height: 700px;width:1100">
				<div id="wipChart_logic"></div>
			</div>
		</div>
		<div title="NAND" style="padding:5px;">
			<div id ="maincontainer3" style="height: 700px;width:1100">
				<div id="wipChart_nand"></div>
			</div>
		</div>
		<div title="LOGIC1" style="padding:5px;">
			<div id ="maincontainer4" style="height: 700px;width:1100">
				<div id="wipChart_logic1"></div>
			</div>
		</div>
	</div>
	
</div>

