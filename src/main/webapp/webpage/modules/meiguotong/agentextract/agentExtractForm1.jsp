<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>代理商提现管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			var ids=""+getIdSelections();
			 $("#orderids").val(getIdSelections());
			 jp.loading();
			 
			 $.ajax({           
				 type :"post",            
				 url :"${ctx}/meiguotong/agentextract/agentExtract/save",            
				 data :{"orderids":ids},
				 async:false,            
				 success : function(data){              
					 if(data.success){
		                 	jp.success(data.msg);
		                 	jp.close($topIndex);//关闭dialog
		                 }else{
		     	  			jp.error(data.msg);
		                 }        
					 }       
			 });
			 return true;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					$table.bootstrapTable('refresh');
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
		function view(id){
			 window.location = "${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id=" +id;
		}
	</script>
</head>
<body class="bg-white">
		<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="javascript:history.go(-2)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
			<div class="form-group">
			<label class="col-sm-2 control-label" style="width:100px;text-align: right !important;">提现订单号：</label>
			<div  class="col-sm-3 control-label" style="text-align: left !important;">
				${agentExtract.no}
			</div>
		</div>
		<br>
		<div class="form-group">
			<label class="col-sm-2 control-label" style="width:100px;text-align: right !important;">提现时间：</label>
			<div  class=" col-sm-3 control-label " style="text-align: left !important;">
			<fmt:formatDate value="${agentExtract.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</div>
		</div>
		<br>
		<div class="form-group">
			<label class="col-sm-2 control-label" style="width:100px;text-align: right !important;">提现金额：</label>
			<div class="col-sm-3 control-label " style="text-align: left !important;">
			${agentExtract.extract}
			</div>
		</div> 
	<table class="table table-bordered">
						<thead>
							<tr>
								<td>订单号</td>
								<td>标题</td>
								<td>类型</td>
								<td>订单完成时间</td>
								<td>合同金额</td>
								<td>状态</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody id="tbody">
						<c:forEach items="${orderSys}" var="item">
							<tr>
							<td >${item.orderNo}</td>
							<td >${item.title}</td>
							<td >
							<c:if test="${item.type==1}">包车租车</c:if>
							<c:if test="${item.type==2}">短程接送</c:if>
							<c:if test="${item.type==3}">接送机</c:if>
							<c:if test="${item.type==4}">常规路线</c:if>
							<c:if test="${item.type==9}">当地参团</c:if>
							<c:if test="${item.type==5}">游轮</c:if>
							<c:if test="${item.type==6}">景点门票车</c:if>
							<c:if test="${item.type==7}">当地玩家/导游</c:if>
							<c:if test="${item.type==8}">酒店</c:if>
							<c:if test="${item.type==10}">保险</c:if>
							<c:if test="${item.type==15}">定制租车</c:if>
							</td>
							<td ><fmt:formatDate value="${item.finishDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td >${item.price}</td>
							<td >
								<c:if test="${item.extract==1}">未提现</c:if>
								<c:if test="${item.extract==2}">提现中</c:if>
								<c:if test="${item.extract==3}">已完成</c:if>
							</td>
							<td ><a id="view" class="view" onclick="view(${item.id})" href="#"><i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a></td>
							</c:forEach>
						</tbody>
					</table>
		</div>
		</div>
		</div>
	
	<%-- <%@ include file="agentExtractUnExtract.jsp" %>  --%>
	<script type="text/javascript">
		function reinitIframe(){
	var iframe = document.getElementById("contentCommentIfem");
	try{
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height = height;
	console.log(height);
	}catch (ex){}
	}
	window.setInterval("reinitIframe()", 200);

</script>
</body>
</html>