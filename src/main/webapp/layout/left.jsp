<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
			$('#tt').tree({
				data: [{
					text: 'Wip Info',
					"iconCls":"icon-zd-database",
					"attributes":{  
			            "url":"propage/pi/wipDG.jsp"
			        }
				}/* ,{
					text: 'Wip Detail',
					"iconCls":"icon-zd-wip-detail",
					"attributes":{  
						"url":"propage/pi/wipDetailDG.jsp"
					}
				} */,{
					text: 'No-Repeat Wip',
					"iconCls":"icon-zd-wip-detail",
					"attributes":{  
						"url":"propage/pi/wipDetailUniqueDG.jsp"
					}
				}/* ,{
					text: 'WIP Bar Chart',
					"iconCls":"icon-zd-chart", 
					"attributes":{  
						"url":"propage/pi/wipChart.jsp"
					}
				}  */,{
					text: 'WIP Bar Chart',
					"iconCls":"icon-zd-chart", 
					"attributes":{  
						"url":"propage/pi/wipChartByNewRule.jsp"
					}
				} ,{
					text: 'Wip Rule',
					"iconCls":"icon-zd-chart-edit",
					"attributes":{  
						"url":"propage/tpn/tpnRule.jsp"
					}
				}/*,{
					text: 'TPN Stage',
					"iconCls":"icon-zd-wrench_orange",
					"attributes":{  
						"url":"propage/pi/tpnFromStageDG.jsp"
					}
				} */,{
					text: 'WO Add',
					"iconCls":"icon-zd-add-po",
					"attributes":{  
						"url":"propage/pi/wipForAddPoDG.jsp"
					}
				},{
					text: 'WO Edit',
					"iconCls":"icon-zd-edit-po",
					"attributes":{  
						"url":"propage/pi/productOrderDG.jsp"
					}
				},{
					text: 'WO Modify Record',
					"iconCls":"icon-zd-changeRecord-po",
					"attributes":{  
						"url":"propage/pi/productOrderChangeRecordDG.jsp"
					}
				},{
					text: 'Hold Lot',
					"iconCls":"icon-zd-lock",
					"attributes":{  
						"url":"propage/pi/lotToHoldDG.jsp"
					}
				},{
					text: 'IPN Rule',
					"iconCls":"icon-zd-search-po",
					"attributes":{  
						"url":"propage/pi/viewOfIpnRule.jsp"
					}
				},{
					text: 'TPN Rule-Nor',
					"iconCls":"icon-zd-search-po",
					"attributes":{  
						"url":"propage/pi/viewOfTpnRule.jsp"
					}
				},{
					text: 'TPN Rule-MCU',
					"iconCls":"icon-zd-search-po",
					"attributes":{  
						"url":"propage/pi/viewOfMcuTpnRule.jsp"
					}
				},{
					text: 'TPN Rule-SPI Nand',
					"iconCls":"icon-zd-search-po",
					"attributes":{  
						"url":"propage/pi/viewOfNandTpnRule.jsp"
					}
				},{
					text: 'Wip Function',
					"iconCls":"icon-zd-wip_function",
					"attributes":{  
			            "url":"propage/tt/tiptpHelper.jsp"
			        }
				}],
				onClick : function(node) {
					addTab(node);
				}
			
			});
			/* $('#tiptop').tree({
				data: [{
					text: 'Wip Function',
					"iconCls":"icon-zd-wip_function",
					"attributes":{  
			            "url":"propage/tt/tiptpHelper.jsp"
			        }
				}],
				onClick : function(node) {
					addTab(node);
				}
			
			}); */
	});
</script>
<div title="WIP INFO" data-options="iconCls:'',selected:true" style="padding-top:5px">
		<ul id="tt" style="font-family: Georgia, serif;"></ul>
</div>
<!-- <div id="aa" class="easyui-accordion" data-options="border:false"> 
	
	<div title="TIPTOP" data-options="iconCls:''" style="padding-top:5px">
		<ul id="tiptop" style="font-family: Georgia, serif;"></ul>
	</div>
</div> -->
