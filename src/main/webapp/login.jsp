<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/ul/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目管理平台</title>
<jsp:include page="inc.jsp"></jsp:include>
<!-- <script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery-1.8.3.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="jslib/syUtil.js"></script>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/gray/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/icon.css" type="text/css"></link> -->
<script type="text/javascript">
	$(function() {
		$('#dd').dialog({  
		    title: '用户登录',  
		    width: 488,  
		    height: 249,  
		    modal: true,
		    closable: false,
		    //noheader: true,
		    buttons : [ {
				text : '登录',
				//iconCls : 'icon-add',
				handler : function() {
					console.info("come in");
					$('form').form('submit', {
						url : 'userAction!login2.action',
						datatype : 'json',
						success : function(r) {
							console.info(r);
								/* $.messager.show({
									title : '提示',
									msg : obj.msg
								}); */
						}
					});
				}
			} ]
		});
	});
</script>
</head>
<body>
    <div id="dd" > 
    	<form> 
			<ul>
					<label>帐号</label>
					<input name="name" type="text" class="easyui-validatebox span2" data-options="required:true" value="">
			</ul>
			<ul>
					<label>密码</label>
					<input name="pwd" type="password" class="easyui-validatebox span2" data-options="required:true" value="">
			</ul>
		</form>
    </div>
</body>
</html>