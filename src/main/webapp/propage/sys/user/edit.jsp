<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$.ajax({
			type : 'post',
			  url: "userAction!getUserRoleList.action",
			  data :{name : $('#admin_yhgl_datagrid').datagrid('getChecked')[0].name},
			  dataType : 'json',
			  success: function(r){
				  var myobj=eval(r);
				  var col_num = 0;
				  var html_content = "";
				  for(var i=0;i<myobj.length;i++){
					  if(col_num==0)
					  {
						  html_content+='<tr>';
					  }
					  if(myobj[i].checked){
					  	html_content+='<td style=\"width:20\"><input checked=\"true\" style=\"vertical-align: middle\" id=\"check'+i+'\" name=\"ids\" type=\"checkbox\" value=\"'+myobj[i].id+'\"><label style=\"vertical-align: middle\" for=\"check'+i+'\">'+myobj[i].name+'</label></td>';  
					  }else{
					  	html_content+='<td style=\"width:20\"><input style=\"vertical-align: middle\" id=\"check'+i+'\" name=\"ids\" type=\"checkbox\" value=\"'+myobj[i].id+'\"><label style=\"vertical-align: middle\" for=\"check'+i+'\">'+myobj[i].name+'</label></td>';
					  }
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
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form_editUser">
			<table>
				<tr>
					<th>登录名</th>
					<td><input type="hidden" id="id" name="id"><input id="name" name="name" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>角色 </th>
					<td><table id="role_table" style="width:100%"></table></td>
				</tr>
			</table>
		</form>
	</div>
</div>

