<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>推送管理管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="comPushList_inform.js" %>
</head>
<body style="background-color: white;">
	<div class="panel panel-primary">
	<div class="panel-body">
		<sys:message content="${message}"/>
	<!-- 表格 -->
	<table id="comPushTable"   data-toolbar="#toolbar"></table>

    
	</div>
	</div>
</body>
</html>