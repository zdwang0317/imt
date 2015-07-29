<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	 $(function() {
		 $('#user_login_loginDialog').dialog({
				title: '登录(PI系统帐号)',
				height : 150,  
				width : 300,
				closed: true, 
				closable: false,
				cache: false,
				modal: true,
				buttons:[{
					text:'登录',
					handler:function(){
						$('#user_login_loginForm').form('submit', {
							url : 'userAction!CheckIn.action',
							datatype : 'json',
							success : function(r) {
								var obj = jQuery.parseJSON(r);
								if(obj.success==true){
									$('#user_login_loginDialog').dialog('close');
									$('#sessionInfoDiv').html("您好，"+obj.msg.substring(obj.msg.indexOf("_")+1));
									$('#session_user_name').val(obj.msg.substring(0,obj.msg.indexOf("_")));
									$.messager.show({
										title : '提示',
										msg : '登陆成功',
										 /* showType:'slide',
										 style:{
										 right:'',
										 top:document.body.scrollTop+document.documentElement.scrollTop,
										 bottom:''
										 } */
									});
								}else{
									$.messager.show({
										title : '提示',
										msg : '用户名或密码错误'
									});
								}
								
							}
						});
					}
				}]
			});
		/* $.ajax({
			url : 'userAction!saveUserNameToSession.action?name=fd',//+url.substring(url.indexOf("?")+1,url.length),
			dataType : 'json',
			success : function(r) {
				console.info("come in");
				//window.location.href="http://localhost:8080/imt/";
			}
		}); 
		var url = location.href;
		console.info(url); */
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : "userAction!checkUserOnSession.action",
			success : function(r) {
				if(null!=r.msg&&""!=r.msg){
					$('#sessionInfoDiv').html("您好，"+r.msg.substring(r.msg.indexOf("_")+1));
					$('#session_user_name').val(r.msg.substring(0,r.msg.indexOf("_")));
				}else{
					$('#user_login_loginDialog').dialog('open');
				}
			}
		});
	}); 
</script>

<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
</div><!--  -->
<div style="position: absolute; right: 0px; bottom: 0px; ">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-zd-themes'">更换皮肤</a>
</div>
<div style="display:none" ><input id="session_user_name"/></div>
<!-- <div style="position: absolute; right: 0px; bottom: 0px; ">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a> 
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="sy.changeTheme('default');">default</div>
	<div onclick="sy.changeTheme('gray');">gray</div>
	<div onclick="sy.changeTheme('metro');">metro</div>
	<div onclick="sy.changeTheme('cupertino');">cupertino</div>
	<div onclick="sy.changeTheme('dark-hive');">dark-hive</div>
	<div onclick="sy.changeTheme('pepper-grinder');">pepper-grinder</div>
	<div onclick="sy.changeTheme('sunny');">sunny</div>
</div> -->
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeThemeFun('default');">default</div>
	<div onclick="changeThemeFun('gray');">gray</div>
	<div onclick="changeThemeFun('black');">black</div>
	<div onclick="changeThemeFun('bootstrap');">bootstrap</div>
</div>
<div id="user_login_loginDialog" class="easyui-dialog">
	<form id="user_login_loginForm">
		<table>
			<tr>
				<th>登录名</th>
				<td><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'登陆名称必填'" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input type="password" name="pwd" class="easyui-validatebox" data-options="required:true,missingMessage:'密码必填'" />
				</td>
			</tr>
		</table>
	</form>
</div>