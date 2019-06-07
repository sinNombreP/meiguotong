<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直客流水记录管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jp.loading();
					form.submit();
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
<body>
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
		<form:form id="inputForm" modelAttribute="memberIncome" action="${ctx}/sys/memberincome/memberIncome/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">流水号：</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.no}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会员账号：</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.number}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">流水类型：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${memberIncome.income==1}">收入</c:if>
						<c:if test="${memberIncome.income==2}">支出</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付类型：</label>
					<div class="col-sm-3 control-label control-left">
						银行卡
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开户人：</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.people}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开户人：</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.pay}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开户银行：</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.rank}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">金额：</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.incomePrice}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请时间：</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${memberIncome.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${memberIncome.checks==1}">待审核</c:if>
						<c:if test="${memberIncome.checks==2}">审核通过</c:if>
						<c:if test="${memberIncome.checks==3}">审核不通过</c:if>
						<c:if test="${memberIncome.checks==4}">已打款</c:if>
					</div>
				</div>
		<c:if test="${fns:hasPermission('sys:memberincome:memberIncome:edit') || isAdd}">
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