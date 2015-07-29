<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/ul/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var url = location.href;
		$.ajax({
			url : 'userAction!saveUserNameToSession.action?'+url.substring(url.indexOf("?")+1,url.length),
			dataType : 'json',
			success : function(r) {
				 window.location.href="http://192.168.15.24:8016/imt/"; 
				/* window.location.href="http://localhost:8080/imt/"; */
				
			}
		});
	});
</script>
</head>
</html>

