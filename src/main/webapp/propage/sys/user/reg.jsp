<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		/* $('#ids').combobox({
			url : 'roleAction!getRoleList.action',
			valueField : 'id',
			textField : 'name',
			multiple : true,
			panelWidth : 300,
			width : 300
		}); */
		$.ajax({
			  url: "roleAction!getRoleList.action",
			  success: function(r){
				  console.info(r);
				  var myobj=eval(r);
				  var col_num = 0;
				  var html_content = "";
				  for(var i=0;i<myobj.length;i++){
					  if(col_num==0)
					  {
						  html_content+='<tr>';
					  }
					  html_content+='<td style=\"width:20\"><input style=\"vertical-align: middle\" id=\"ids\" name=\"ids\" type=\"checkbox\" value=\"'+myobj[i].id+'\"><label style=\"vertical-align: middle\" for=\"ids\">'+myobj[i].name+'</label></td>';
					  if(col_num==3)
					  {
						  html_content+='</tr>';
					  }
					  if(col_num<3)
					  {
						  col_num++;
					  }else{
						  col_num = 0;
					  }
					  
				  }
				  $('#role_table').append(html_content);
			  }
			});
		$('#name').blur(function() {
			$.ajax({
				type : 'post',
				url : 'userAction!checkDuplicateUser.action',
				data : {
					name : $('#name').val()
				},
				dataType : 'json',
				success : function(r) {
					//console.info(Object.prototype.toString.apply(r));
					if(r.success)
					{
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
						$('#name').focus();
					}
				}
			});
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="user_reg_regForm" method="post">
			<table>
				<tr>
					<th>登录名</th>
					<td><input id="name" name="name" class="easyui-validatebox"
						data-options="required:true,missingMessage:'登陆名称必填'" /></td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="pwd" type="password"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input name="rePwd" type="password"
						class="easyui-validatebox"
						data-options="required:true,validType:'eqPwd[\'#user_reg_regForm input[name=pwd]\']'" /></td>
				</tr>
				<tr>
					<th>角色</th>
					<td>
						<table id="role_table" style="width:100%"></table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

