<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
<head>
<title>PROMIS</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<jsp:include page="inc.jsp"></jsp:include>
<!-- <link href="/favicon.ico" type="image/x-icon" rel="shortcut icon" /> -->
<!-- <script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery-1.8.3.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="jslib/syUtil.js"></script>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/gray/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/icon.css" type="text/css"></link> -->
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:5%">  
		<jsp:include page="layout/top.jsp"></jsp:include>
	</div>
	<div data-options="region:'south',split:true" style="height:3%;overflow: hidden;"> 
		<jsp:include page="layout/bottom.jsp"></jsp:include>
	</div>
	<div data-options="region:'west',split:true" title="系统菜单" style="width:150px">
		<jsp:include page="layout/left.jsp"></jsp:include>
	</div>
	<!-- <div data-options="region:'east',title:'east',split:true" style="width:200px;"></div> -->
	<div data-options="region:'center',title:''" style="overflow: hidden;">
		<jsp:include page="layout/center.jsp"></jsp:include>
	</div>

	<%-- <jsp:include page="propage/sys/user/login.jsp"></jsp:include> --%>

	<%-- <jsp:include page="user/reg.jsp"></jsp:include> --%>
</body>
</html>
