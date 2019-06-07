<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>导游订单管理</title>
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
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/meiguotong/orderguide/orderGuide"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="orderGuide" action="${ctx}/meiguotong/orderguide/orderGuide/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table>	
			<tbody>
			<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">订单详情</label></td>
					<td class="width-35">
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">订单状态:</label></td>
					<td class="width-35">
         	        	<c:if test="${orderTravelBusiness.status==1}">待付款</c:if>
						<c:if test="${orderTravelBusiness.status==2}">待确定</c:if>
						<c:if test="${orderTravelBusiness.status==3}">待出行</c:if>
						<c:if test="${orderTravelBusiness.status==4}">待评价</c:if>
						<c:if test="${orderTravelBusiness.status==5}">已完成</c:if>
						<c:if test="${orderTravelBusiness.status==6}">取消订单</c:if>
						<c:if test="${orderTravelBusiness.status==7}">申请退款</c:if>
						<c:if test="${orderTravelBusiness.status==8}">退款中</c:if>
						<c:if test="${orderTravelBusiness.status==9}">退款成功</c:if>
						<c:if test="${orderTravelBusiness.status==10}">退款失败</c:if>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">下单时间:</label></td>
					<td class="width-35">
                 	<fmt:formatDate value="${orderTravelBusiness.createDate}" pattern="yyyy-MM-dd HH:MM:dd:ss"/>
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">订单号:</label></td>
					<td class="width-35">
					${orderTravelBusiness.orderNo}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">订单金额:</label></td>
					<td class="width-35">
         	        ${orderTravelBusiness.price}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">下单人信息</label></td>
					<td class="width-35">
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">用户名:</label></td>
					<td class="width-35">
         	       ${orderTravelBusiness.nickName}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">手机号:</label></td>
					<td class="width-35">
         	        ${orderTravelBusiness.mobile}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">联系信息</label></td>
					<td class="width-35">
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">联系人:</label></td>
					<td class="width-35">
         	        ${orderTravelBusiness.contactsName}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
					<td class="width-35">
         	         ${orderTravelBusiness.contactMobile}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">备注信息:</label></td>
					<td class="width-35">
         	        ${orderTravelBusiness.remark}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">出行信息</label></td>
					<td class="width-35">
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">目的地:</label></td>
					<td class="width-35">
					${orderTravelBusiness.address}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">时间:</label></td>
					<td class="width-35">
         	     <fmt:formatDate value="${orderTravelBusiness.startDate}" pattern="yyyy-MM-dd HH:MM:dd:ss"/>
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">成人人数:</label></td>
					<td class="width-35">
					${orderTravelBusiness.adultNum}
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">儿童人数:</label></td>
					<td class="width-35">
					${orderTravelBusiness.childNum}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">保险方案</label></td>
					<td class="width-35">
					
					</td>					
				</tr>			
					<tr>
					<td class="width-15 active"><label class="pull-right">意外险:</label></td>
					<td class="width-35">
					${insurance.name}
					￥${insurance.price}/人*${orderInsurance.num}
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">当地玩家</label></td>
					<td class="width-35">
					
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">    :</label></td>
					<td class="width-35">
					。。。。。。。。。。
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">    </label></td>
					<td class="width-35">
         	        。。。。。。。。。。
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">导游信息</label></td>
					<td class="width-35">
					
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">真实姓名：</label></td>
					<td class="width-35">	
					${guide.realName}	
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">性别：</label></td>
					<td class="width-35">
					${guide.sex}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">年龄：</label></td>
					<td class="width-35">
					${guide.age}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">地址：</label></td>
					<td class="width-35">
					${guide.address}
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right" style="font-size:20px;">支付信息</label></td>
					<td class="width-35">
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">支付时间:</label></td>
					<td class="width-35">
         	         <fmt:formatDate value="${orderTravelBusiness.payDate}" pattern="yyyy-MM-dd HH:MM:dd:ss"/>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">支付方式:</label></td>
					<td class="width-35">
         	       ${orderTravelBusiness.payWay}
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">支付流水号:</label></td>
					<td class="width-35">
         	        ${orderTravelBusiness.payNo}
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">支付金额:</label></td>
					<td class="width-35">
         	       ${orderTravelBusiness.price}
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息:</label></td>
					<td class="width-35">
         	        ${orderTravelBusiness.remark}
					</td>					
				</tr>
		 	</tbody>
		</table>
				
		<c:if test="${fns:hasPermission('meiguotong:orderguide:orderGuide:edit') || isAdd}">
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