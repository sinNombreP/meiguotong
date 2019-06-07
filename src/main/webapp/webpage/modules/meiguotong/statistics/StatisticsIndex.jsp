<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title></title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
</head>
<script type="text/javascript">

	
</script>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
	<div class="modal-body" >
		<iframe src="${ctx}/meiguotong/statistics/Statistics/orderNum" id="contentCommentIfem1" style="background-color:white"  scrolling="no" frameborder="0"  width="100%" height="600px"></iframe>
	</div>
	<div class="modal-body" >
		<iframe src="${ctx}/meiguotong/statistics/Statistics/orderProportion" id="contentCommentIfem2" style="background-color:white"  scrolling="no" frameborder="0"  width="100%" height="1000px"></iframe>
	</div>
	<!-- 搜索 -->
	
	
	</div>
	</div>
	</div>
	
	
	<script type="text/javascript">
 /* function reinitIframe1(){
	var iframe = document.getElementById("contentCommentIfem1");
	try{
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height = height;
	console.log(height);
	}catch (ex){}
	}
 window.setInterval("reinitIframe1()", 200);
 
 function reinitIframe2(){
	var iframe = document.getElementById("contentCommentIfem2");
	try{
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height = height;
	console.log(height);
	}catch (ex){}
	}
	
	window.setInterval("reinitIframe2()", 200);  */
	
	

</script>
</body>
</html>