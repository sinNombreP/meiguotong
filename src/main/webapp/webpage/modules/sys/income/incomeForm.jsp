<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台流水管理管理</title>
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
				<a class="panelButton" href="${ctx}/sys/income/income"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="income" action="${ctx}/sys/income/income/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">流水号：</label>
					<div class="col-sm-3 control-label control-left">
						${income.no}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品类型：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${income.type==1}">包车租车</c:if>
						<c:if test="${income.type==2}">短程接送</c:if>
						<c:if test="${income.type==3}">接送机</c:if>
						<c:if test="${income.type==4}">常规路线</c:if>
						<c:if test="${income.type==5}">当地参团</c:if>
						<c:if test="${income.type==6}">游轮</c:if>
						<c:if test="${income.type==7}">景点门票</c:if>
						<c:if test="${income.type==8}">当地玩家/导游</c:if>
						<c:if test="${income.type==9}">酒店</c:if>
						<c:if test="${income.type==10}">保险</c:if>
						<c:if test="${income.type==11}">旅游定制</c:if>
						<c:if test="${income.type==13}">商务定制</c:if>
						<c:if test="${income.type==14}">商务旅游</c:if>
						<c:if test="${income.type==15}">提现</c:if>
						<c:if test="${income.type==16}">退款</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">流水类型：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${income.income==1}">收入</c:if>
						<c:if test="${income.income==2}">支出</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付类型：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${income.payType==1}">支付宝</c:if>
						<c:if test="${income.payType==2}">微信</c:if>
						<c:if test="${income.payType==3}">银联</c:if>
						<c:if test="${income.payType==4}">PayPal</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人：</label>
					<div class="col-sm-3 control-label control-left">
						${income.nickName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系电话：</label>
					<div class="col-sm-3 control-label control-left">
						${income.phone}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付金额：</label>
					<div class="col-sm-3 control-label control-left">
						${income.incomePrice}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付时间：</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${income.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态：</label>
					<div class="col-sm-3 control-label control-left">
						<c:if test="${income.status==1}">待确认</c:if>
						<c:if test="${income.status==2}">已取消</c:if>
						<c:if test="${income.status==3}">已完成</c:if>
					</div>
				</div>
		<c:if test="${fns:hasPermission('sys:income:income:edit') || isAdd}">
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