<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮轮订单表管理</title>
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
				<a class="panelButton" href="javascript:history.go(-1);"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="orderLiner" action="${ctx}/meiguotong/orderliner/orderLiner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单状态：</label>
					<div class="col-sm-3 control-label control-left" >
						<c:if test="${orderLiner.status==1}">待付款</c:if>
						<c:if test="${orderLiner.status==2}">待确定</c:if>
						<c:if test="${orderLiner.status==3}">待出行</c:if>
						<c:if test="${orderLiner.status==4}">待评价</c:if>
						<c:if test="${orderLiner.status==5}">已完成</c:if>
						<c:if test="${orderLiner.status==6}">取消订单</c:if>
						<c:if test="${orderLiner.status==7}">申请退款</c:if>
						<c:if test="${orderLiner.status==8}">退款中</c:if>
						<c:if test="${orderLiner.status==9}">退款成功</c:if>
						<c:if test="${orderLiner.status==10}">退款失败</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">下单时间：</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${orderLiner.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
					<label class="col-sm-2 control-label">订单号：</label>
					<div class="col-sm-3 control-label control-left">
						${orderLiner.orderNo}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单金额：</label>
					<div class="col-sm-3 control-label control-left">
						${orderLiner.price}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">下单人信息：</label>
					<div class="col-sm-3 control-label control-left">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户名：</label>
					<div class="col-sm-3 control-label control-left">
						${memberInformation.nickName}
					</div>
					<label class="col-sm-2 control-label">手机号：</label>
					<div class="col-sm-3 control-label control-left">
						${member.mobile}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">联系信息</label>
					<div class="col-sm-3 control-label control-left">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人：</label>
					<div class="col-sm-3 control-label control-left">
						${memberInformation.realName}
					</div>
					<label class="col-sm-2 control-label">联系电话：</label>
					<div class="col-sm-3 control-label control-left">
						${member.mobile}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-3 control-label control-left">
						${orderLiner.contactRemark}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">出航信息</label>
					<div class="col-sm-3 control-label control-left">
					
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出发地:</label>
					<div class="col-sm-3 control-label control-left">
						${linerLine.startCity}
					</div>
					<label class="col-sm-2 control-label">时间:</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${orderLiner.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出发城市:</label>
					<div class="col-sm-3 control-label control-left">
						${linerLine.startCity}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出发港口:</label>
					<div class="col-sm-3 control-label control-left">
					${linerLine.upPort}
					</div>
					<label class="col-sm-2 control-label">下船港口:</label>
					<div class="col-sm-3 control-label control-left">
					${linerLine.downPort}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">房间:</label>
				</div>
				<c:forEach items="${linerRoom}" var="item">
					<div class="form-group">
					<label class="col-sm-2 control-label">房间类型:</label>
					<div class="col-sm-3 control-label control-left">
					${item.name}
					</div>
					<label class="col-sm-2 control-label">房间规格:</label>
					<div class="col-sm-3 control-label control-left">
					${item.spec}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">楼层:</label>
					<div class="col-sm-3 control-label control-left">
					${item.floor}
					</div>
				</div>
				</c:forEach>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-3 control-label control-left">
				     <table>
						<c:if test="${orderlinerRoomList!=null}">						
						<c:forEach items="${orderlinerRoomList}" var="orderlinerRoomList">
						<tr style="height:30px">
						<td style="width:155px">房间规格:</td>
						<td style="width:155px">成人入住数量：${orderlinerRoomList.adultNum}</td>
						<td style="width:155px">儿童入住数量：${orderlinerRoomList.childrenNum}</td>
						<td style="width:155px">房间数量：${orderlinerRoomList.roomNum}</td>
						</tr>						
						</c:forEach>
						</c:if>
						</table>
					</div>
				</div> --%>
				<%-- 
				<div class="form-group">
					<label class="col-sm-2 control-label">人数:</label>
					<div class="col-sm-3 control-label control-left">
					${linerRoom.peopleNumber}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">房间数:</label>
					<div class="col-sm-3 control-label control-left">
					${linerRoom.roomNumber}
					</div>
				</div> --%>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">保险方案</label>
					<div class="col-sm-3 control-label control-left">
					
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">意外险:</label>
					<div class="col-sm-3 control-label control-left">
						${insurance.name}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">价格/人:</label>
					<div class="col-sm-3 control-label control-left">
						${insurance.price}
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">邮轮</label>
					<div class="col-sm-3 control-label control-left">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细信息:</label>
					<div class="col-sm-3 control-label control-left">
						${linerLine.content}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮轮航线:</label>
					<div class="col-sm-3 control-label control-left">
						${linerLine.route}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">途径地点:</label>
					<div class="col-sm-3 control-label control-left">
						${linerLine.path}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">行程</label>
				</div>
				<c:forEach items="${trips}" var="item">
				<div class="form-group">
					<label class="col-sm-2 control-label">时间:</label>
					<div class="col-sm-3 control-label control-left">
						第${item.dayCount}天
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标题:</label>
					<div class="col-sm-3 control-label control-left">
						${item.title}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详情:</label>
					<div class="col-sm-10 control-label control-left">
						${item.content}
					</div>
				</div>
				</c:forEach>
				
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">出游人信息</label>
					<div class="col-sm-3 control-label control-left">					
					
				</div>												
				</div>			
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-3 control-label control-left">
						<table>
						<c:if test="${orderMemberList!=null}">						
						<c:forEach items="${orderMemberList}" var="orderMemberList">
						<tr style="height:30px">
						<td>中文名字：${orderMemberList.chineseName}</td>
						</tr>
						<tr style="height:30px">
						<td style="width:155px">证件类型:${orderMemberList.chineseName}</td>
						<td style="width:155px">证件号码:${orderMemberList.chineseName}</td>
						<td style="width:155px">证件有效期:${orderMemberList.chineseName}</td>
						</tr>
						<tr style="height:30px"> 
						<td style="width:155px">出生年月:${orderMemberList.chineseName}</td>
						<td style="width:155px">手机号码:${orderMemberList.chineseName}</td>
						</tr>						
						</c:forEach>
						</c:if>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">支付信息</label>
					<div class="col-sm-3 control-label control-left">
					
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付时间:</label>
					<div class="col-sm-3 control-label control-left">
						${orderLiner.payDate}
					</div>
					<label class="col-sm-2 control-label">支付方式:</label>
					<div class="col-sm-3 control-label control-left">
						${orderLiner.payWay}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付流水号:</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.pay}
					</div>
					<label class="col-sm-2 control-label">支付金额:</label>
					<div class="col-sm-3 control-label control-left">
						${memberIncome.priceRmb}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息:</label>
					<div class="col-sm-3 control-label control-left">
						${orderLiner.contactRemark}
					</div>
				</div>
		<c:if test="${fns:hasPermission('meiguotong:orderliner:orderLiner:edit') || isAdd}">
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