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
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/meiguotong/agentextract/agentExtract/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
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
	</script>
	<style type="text/css">
		.control-left{
			text-align: left !important;
			font-size: 14px;
			 vertical-align:middle;
		}
		.div-head{
			font-size: 18px;
			color: #FF9900;
		}
	</style>
</head>
<body class="bg-white">
		<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="javascript:history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="agentExtract" action="${ctx}/sys/agentExtract/agentExtract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">提现流水号：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.no}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提现公司：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.agentName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单金额：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.count}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提现金额：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.extract}
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">开户银行：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.rank}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开户账号：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.pay}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开户人：</label>
					<div class="col-sm-3 control-label control-left">
						${agentExtract.people}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请时间：</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${agentExtract.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${agentExtract.status==1}">已申请</c:if>
						<c:if test="${agentExtract.status==2}">同意申请</c:if>
						<c:if test="${agentExtract.status==3}">拒绝申请</c:if>
						<c:if test="${agentExtract.status==4}">已完成</c:if>
					</div>
				</div>
		<c:if test="${fns:hasPermission('sys:agentExtract:agentExtract:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>