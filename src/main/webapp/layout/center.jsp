<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var centerTabs;
var tabsMenu;
$(function() {

	$('#centerTabs').tabs({  
		onClose: function(title,index){
			var content_menu = $('#'+title+'_menu');
			if(content_menu)
			{
				content_menu.menu('destroy');
				console.info( '元素个数'+$('*').length );
				/* var shadow = $('.menu-shadow');
				console.info(shadow);
				console.info(Object.prototype.toString.apply(shadow)); */
			}
		}
	});
	
	$('#updateProductNoOfPi').form({
		url : '${pageContext.request.contextPath}/wipAction!updateProductNoOfPi.action',
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
	
	$('#p').panel({
		width:500,
		height:400,
		title:'系统动态',
		style:{
			margin:10
		}
		}); 
	$('#p2').panel({
		width:500,
		height:300, 
		title:'需求列表',
		style:{
			margin:10
		}
		}); 
	$('#p3').panel({
		width:500,
		height:400, 
		title:'定时任务详解',
		style:{
			margin:10
		}
		}); 
/* 	tabsMenu = $('#tabsMenu').menu({
		onClick : function(item) {
			var curTabTitle = $(this).data('tabTitle');
			var type = $(item.target).attr('type');

			if (type === 'refresh') {
				refreshTab(curTabTitle);
				return;
			}

			if (type === 'close') {
				var t = centerTabs.tabs('getTab', curTabTitle);
				if (t.panel('options').closable) {
					centerTabs.tabs('close', curTabTitle);
				}
				return;
			}

			var allTabs = centerTabs.tabs('tabs');
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel('options');
				if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === 'closeAll') {
					closeTabsTitle.push(opt.title);
				}
			});

			for ( var i = 0; i < closeTabsTitle.length; i++) {
				centerTabs.tabs('close', closeTabsTitle[i]);
			}
		}
	}); */

	centerTabs = $('#centerTabs').tabs({
		fit : true,
		border : false,
		onContextMenu : function(e, title) {
			e.preventDefault();
			tabsMenu.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		}
	});
});
	function addTab(node) {
		/* var t = $('#centerTabs');
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		} */
		if (centerTabs.tabs('exists', node.text)) {
			centerTabs.tabs('select', node.text);
		} else {
		/* if (node.attributes.url) { */
			centerTabs.tabs('add',{
				title : node.text,
				closable : true,
				href : node.attributes.url
				/* tools : [ {
					iconCls : 'icon-mini-refresh',
					handler : function() {
						refreshTab(node.text);
					}
				} ] */
			});
			/* var url = node.attributes.url;
			console.info(url);
			var iframe;
			//iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;" sandbox></iframe>';
			iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
			var opts = {
				title : node.text,
				closable : true,
				iconCls : node.iconCls,
				content : iframe,
				border : false
			};
			console.info(opts);
			addTab('add', opts); */
		/* }else{
			if(node.state =='closed'){
				$(this).tree('expand',node.target);
			}else{
				$(this).tree('collapse',node.target);
			}
		} */
		}
	}
	/* function refreshTab(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	} */
	function analysisProductNoSubmit() {
		$('#analysisProductNo').submit();
	}
	function updateProductNoOfPiSubmit() {
		$('#updateProductNoOfPi').submit();
	}

</script>
<div id="centerTabs" class="easyui-tabs" data-options="fit:true,border:false" style="overflow: hidden;">
	<div title="Home"><br>
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:false,border:false" style="width:550px">
				<div id="p" style="padding:5px">
					<div style="padding-top:5px">2015/03/18 功能实现:生成TPN FLOW生成规则可编辑界面</div>
					<div style="padding-top:5px">2015/03/09 功能实现:新增WIP厂数据Hlmc</div>
					<div style="padding-top:5px">2015/02/27 功能实现:WIP Stage数据比对报表</div>
					<div style="padding-top:5px">2014/07/30 Bug修复:工单IPN验证TPN失效问题</div>
					<div style="padding-top:5px">2014/07/30 功能实现:Upload No-Repeat WIP to Update TPN Data</div>
					<div style="padding-top:5px">2014/07/30 功能实现:NO-REPEAT WIP Download</div>
					<div style="padding-top:5px">2014/07/14 功能实现:工单建立验证WaferType</div>
					<div style="padding-top:5px">2014/07/10 功能实现:添加工单加入IPN选项多级联动功能</div>
			    	<div style="padding-top:5px">2014/05/27 功能实现:工单变更并生成变更记录功能</div>
			    	<div style="padding-top:5px">2014/04/28 功能实现:工单加入TPN FLOW子表</div>
			    	<div style="padding-top:5px">2014/04/28 功能实现:工单需存在与其IPN对应的TPN方可建立</div>
			    	<div style="padding-top:5px">2014/04/14 功能实现:工单加入FAB SITE字段</div>
			    	<div style="padding-top:5px">2014/04/08 Bug修复:工单在非可删除状态下可删除问题</div>
			    	<div style="padding-top:5px">2014/02/17 功能优化:Wip Info查询速度大幅度提高</div>
			    	<div style="padding-top:5px">2014/02/14 功能优化:Wip Analysis查询速度大幅度提高</div>
			    	<div style="padding-top:5px">2014/02/12 Bug修复:Wip查询条件名称与实际过滤名称不一致</div>
			    	<div style="padding-top:5px">2014/01/22 功能实现:出货List与hold lot比对功能</div>
			    	<div style="padding-top:5px">2014/01/20 功能实现:取消ship to 加入cxmt002与cxmt004选择功能</div>
		    		<div style="padding-top:5px">2014/01/15 UI:新增换肤功能,在界面的右上角</div>
		    		<div style="padding-top:5px">2014/01/14 Bug修复:turnkey工单重复提交生成多张工单问题</div> 
		    		<div style="padding-top:5px">2014/01/09  功能实现:IPN Rule 查询</div>
		    		<div style="padding-top:5px">2013/12/30 Bug修复:Wip数据不唯一问题</div>
			    </div>
			    <div id="p2" style="padding:5px;">
			    	<div style="padding-top:5px;color:#999999">2014/07/28 需求：Upload No-Repeat WIP to Update TPN Data</div>
			    	<div style="padding-top:5px;color:#999999">2014/07/28 需求：NO-REPEAT WIP Download</div>
			    	<div style="padding-top:5px;">2014/07/10 BUG：工单建立选择Wafer显示BUG</div>
			    	<div style="padding-top:5px;color:#999999">2014/07/08 需求：工单建立验证WaferType</div>
			    	<div style="padding-top:5px;">2014/04/21 需求：添加产品信息录入编辑功能</div>
			    	<div style="padding-top:5px;color:#999999">2014/04/09 需求：添加工单加入IPN选项多级联动功能</div>
			    	<div style="padding-top:5px;color:#999999">2014/04/02 需求：工单需存在与其IPN对应的TPN方可建立</div>
			    	<div style="padding-top:5px;">2014/03/25 需求：Product Rule 编辑界面</div>
			    	<div style="padding-top:5px;color:#999999">2014/03/24 需求：Pass CP Wip 添加过滤条件</div>
			    	<div style="padding-top:5px;">2014/03/24 需求：IPN Rule 编辑界面</div>
			    	<div style="padding-top:5px;color:#999999">2014/03/24 需求：工单变更并生成变更记录功能</div>
			    	<div style="padding-top:5px;color:#999999">2014/02/12 需求：工单加入FAB SITE字段</div>
			    	<div style="padding-top:5px;color:#999999">2014/02/12 需求：对原始WIP数据导入添加过滤条件</div>
			    	<div style="padding-top:5px;color:#999999">2014/01/16 需求：出货list与hold lot比对功能</div>
		    		<div style="padding-top:5px;color:#999999">2014/01/01 需求：生成TPN FLOW生成规则可编辑界面</div>
		    		<div style="padding-top:5px;color:#999999">2014/01/01 需求：IPN Rule 查询</div>
			    </div>
			</div>
			<div data-options="region:'center',border:false">
				<div id="p3" style="padding:5px;">
			    	<p>0 0/10 8-22 * * ? TaskOfPassCpWip</p> 
			    	<ol>
			    		<li>0 35 9,10,11 * * ? TaskOfDataResolve</li>
			    		<li>
			    			<ol>
			    				<li>ttService.dataResolve();数据解析</li>
			    				<li>select * from cp_wip where erpDate = ? and resolved is null</li>
			    				<li>cp_wip表(当日) resolved='Y'</li>
			    				<li>z_wip_detail表 添加分解wafer后的当日数据</li>
			    			</ol>
			    		</li>
						<li>
							<ol>
			    				<li>ttService.PassDataResolveToChart();将新数据传到Chart表</li>
			    				<li>select * from z_wip_detail where erpdate=?</li>
			    				<li>delete from z_wip_detail_chart</li>
			    				<li>insert into z_wip_detail_chart(id,lid,qty,wid,status,erpdate,tpnFlow,productNo) values(?,?,?,?,?, ?,?,?)</li>
			    			</ol>
						</li>
			    	</ol> 
			    	<p>0 35 9 * * ? TaskOfPassWafer</p> 
			    	<p>0 20 9,10,11 * * ? TaskOfAnalysisProductNo 生成ProductNo与Tpnflow</p> 
			    	<p>0 35 9 * * ? TaskOfDataResolveForPo</p> 
			    	<p>0 30 9,10,11,12 * * ? TaskOfCopyWipToDbOfHistory</p>  
			    </div>
			</div>
		</div>
		    
	</div>
</div>
<!-- <div id="tabsMenu" style="width: 120px;display:none;">
	<div type="refresh">刷新</div>
	<div class="menu-sep"></div>
	<div type="close">关闭</div>
	<div type="closeOther">关闭其他</div>
	<div type="closeAll">关闭所有</div>
</div> -->