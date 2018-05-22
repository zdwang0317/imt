<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#dataResolveForPo').form({
			url : '${pageContext.request.contextPath}/ttAction!dataResolveForPo.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#dataResolve').form({
			url : '${pageContext.request.contextPath}/ttAction!dataResolve.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#dataResolveWipflow').form({
			url : '${pageContext.request.contextPath}/wipAction!dataResolveWipflow.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#dataResolveWipflow2').form({
			url : '${pageContext.request.contextPath}/wipAction!dataResolveTurnkeyDetail.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#passCpWip').form({
			url : '${pageContext.request.contextPath}/ttAction!passCpWip.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#dataClean').form({
			url : '${pageContext.request.contextPath}/wipAction!dataClean.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#passWaferWip').form({
			url : '${pageContext.request.contextPath}/ttAction!passWaferWip.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#updateBjGooddie').form({
			url : '${pageContext.request.contextPath}/ttAction!updateBjGooddie.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#updateHkGooddie').form({
			url : '${pageContext.request.contextPath}/ttAction!updateHkGooddie.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#updateGooddie').form({
			url : '${pageContext.request.contextPath}/ttAction!updateGooddie.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#cancelShipTo').form({
			url : '${pageContext.request.contextPath}/ttAction!cancelShipTo.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#workOrderHelper').form({
			url : '${pageContext.request.contextPath}/ttAction!helperOfWorkOrder.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#passIpnToPi').form({
			url : '${pageContext.request.contextPath}/poAction!PassIpnToPi.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#copyDataOfWipToDbOfHistory').form({
			url : '${pageContext.request.contextPath}/wipAction!CopyDataOfWipToDbOfHistory.action',
			success : function(r) {
				var obj = jQuery.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#analysisProductNo').form({
			url : '${pageContext.request.contextPath}/wipAction!analysisProductNo.action',
			success : function(r) {
				console.info(r);
				var obj = jQuery.parseJSON(r);
				console.info(obj);
				if (obj.success) {
					/* $('#user_login_loginDialog').dialog('close'); */
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#deleteRepetitiveData').form({
			url : '${pageContext.request.contextPath}/ttAction!deleteRepetitiveData.action',
			success : function(r) {
				console.info(r);
				var obj = jQuery.parseJSON(r);
				console.info(obj);
				if (obj.success) {
					/* $('#user_login_loginDialog').dialog('close'); */
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#chart').form({
			url : '${pageContext.request.contextPath}/ttAction!passDataResolveToChart.action',
			success : function(r) {
				console.info(r);
				var obj = jQuery.parseJSON(r);
				console.info(obj);
				if (obj.success) {
					/* $('#user_login_loginDialog').dialog('close'); */
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});
		$('#ttHelper').panel({
			width:500,
			height:300,
			title:'Routine Business',
			style:{
				margin:10
			}
			}); 
		$('#ttHelper2').panel({
			width:500,
			height:200,
			title:'Gooddie Update',
			style:{
				margin:10
			}
			}); 
		$('#ttHelper3').panel({
			width:500,
			height:200,
			title:'Cancel Ship To',
			style:{
				margin:10
			}
			}); 
	});
	function dataResolveSubmit() {
		$('#dataResolve').submit();
	}
	function dataResolveWipflowSubmit() {
		$('#dataResolveWipflow').submit();
	}
	function dataResolveWipflowDateSubmit() {
		$('#dataResolveWipflow2').submit();
	}
	function passCpWipSubmit() {
		$('#passCpWip').submit();
	}
	function dataClean() {
		$('#dataClean').submit();
	}
	function passWaferWipSubmit() {
		$('#passWaferWip').submit();
	}
	function updateBjGooddieSubmit() {
		$('#updateBjGooddie').submit();
	}
	function updateHkGooddieSubmit() {
		$('#updateHkGooddie').submit();
	}
	function updateGooddieSubmit() {
		$('#updateGooddie').submit();
	}
	function cancelShipToSubmit() {
		$('#cancelShipTo').submit();
	}
	function deleteRepetitiveDataSubmit() {
		$('#deleteRepetitiveData').submit();
	}
	function workOrderHelperSubmit() {
		$('#workOrderHelper').submit();
	}
	function passIpnToPiSubmit() {
		$('#passIpnToPi').submit();
	}
	function outputWipCompareSubmit() {
		$('#outputWipCompare').form('submit', {
			url: 'wipAction!excelToWipCompare.action',
			dataType : 'json',
		});
	}
	function copyDataOfWipToDbOfHistorySubmit() {
		$('#copyDataOfWipToDbOfHistory').submit();
	}
	function chart() {
		$('#chart').submit();
	}
	
</script>
<br>
<div id="ttHelper" style="padding:10px;">
<!-- <form id="outputWipCompare" method="post">
	<p>Date <input type="text" name="erpDate">like 15/02/22</p>
	<p>Old Date <input type="text" name="location">like 15/02/21</p>
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="outputWipCompareSubmit()">Output Compare</a>
</form>
<form id="passIpnToPi" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="passIpnToPiSubmit()">passIpnToPi</a>
</form>
<br>
<form id="workOrderHelper" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="workOrderHelperSubmit()">Work Order Helper</a>
</form>
<br> -->
<form id="dataResolveForPo" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dataResolveForPo').submit();">分解WIP For Po数据</a>
</form>
<br>
<form id="copyDataOfWipToDbOfHistory" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#copyDataOfWipToDbOfHistory').submit();">copyDataOfWipToDbOfHistory</a>
</form>
<br> 
<form id="dataResolve" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dataResolveSubmit()">2.分解WIP数据</a>
</form>
<!-- <form id="dataResolveWipflow" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dataResolveWipflowSubmit()">Wipflow INV</a>
</form> -->
<!-- <form id="dataResolveWipflow2" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dataResolveWipflowDateSubmit()">Wipflow Date</a>
</form> -->
<br>
<form id="chart" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chart()">3.Chart</a>
</form>
<br>
 <form id="analysisProductNo" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#analysisProductNo').submit();">1.分析数据</a>
</form>
<br>
<!-- <form id="passCpWip" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="passCpWipSubmit()">向TIPTOP传入可出货CP</a>
</form> -->
<!--<form id="passWaferWip" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="passWaferWipSubmit()">向TIPTOP传入可出货Wafer</a>
</form>
<br>

<br> -->
<!-- <form id="updateBjGooddie" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateBjGooddieSubmit()">更新北京仓库Gooddie(注:需提前关闭cxmt004作业)</a>
</form>
<br>
<form id="updateHkGooddie" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateHkGooddieSubmit()">更新香港仓库Gooddie(注:需提前关闭cxmt004作业)</a>
</form>
<br> -->

</div>
<div id="ttHelper2" style="padding:10px;">
	<form id="updateGooddie">
		<!-- <table>
			<tr><td>单号：</td><td><input name="tc_cpj01" style="width:200px"/>(多条用","分割)</td></tr>
			<tr><td>营运中心：</td><td><input name="db" type="radio" value="dsbj" checked="checked">北京<input name="db" type="radio" value="dshk">香港<input name="db" type="radio" value="dssh">上海格易<input name="db" type="radio" value="dshf">合肥格易</td></tr>
			<tr><td>更新方式：</td><td><input name="mode" type="radio" value="1" checked="checked">gooddie等于0<input name="mode" type="radio" value="2">gooddie不等于0<input name="mode" type="radio" value="3">所有</td></tr>
			<tr><td>新旧表：</td><td><input name="lotId" type="radio" value="new" checked="checked">新表<input name="lotId" type="radio" value="old">旧表</td></tr>
			<tr><td align="right" colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateGooddieSubmit()">更新</a></tr>
			<tr><td colspan="2">注:1.打开的cxmt004为非编辑状态</td>
			<tr><td colspan="2">2.请确认cxmt004中的单号为非入库状态才会更新Gooddie</td>
		</table> -->
		<table>
			<tr><td>营运中心：</td><td><input name="db" type="radio" value="dsbj" checked="checked">北京<input name="db" type="radio" value="dshk">香港<input name="db" type="radio" value="dssh">上海格易<input name="db" type="radio" value="dshf">合肥格易</td></tr>
			<tr><td align="right" colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateGooddieSubmit()">更新</a></tr>
			<tr><td colspan="2">注:1.打开的cxmt004为非编辑状态</td>
			<tr><td colspan="2">2.请确认cxmt004中的单号为非入库状态才会更新Gooddie</td>
		</table>
	</form>
</div>

<!-- <div id="ttHelper3" style="padding:10px;">
<form id="deleteRepetitiveData" method="post">
   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteRepetitiveDataSubmit()">删除cxmt003中重复数据</a>
</form>
	<form id="cancelShipTo">
		<table>
			<tr><td>单号：</td><td><input name="tc_cpj01" style="width:300px"/></td></tr>
			<tr><td>营运中心：</td><td><input name="db" type="radio" value="bj" checked="checked">北京<input name="db" type="radio" value="hk">香港</td></tr>
			<tr><td>作业编号：</td><td><input name="mode" type="radio" value="004" checked="checked">cxmt004<input name="mode" type="radio" value="002">cxmt002</td></tr>
			<tr ><td align="right" colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelShipToSubmit()">执行</a></tr>
		</table>
	</form>
</div>
 -->
