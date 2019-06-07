<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定制订单管理</title>
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
		<form:form id="inputForm" modelAttribute="orderTravelBusiness" action="${ctx}/meiguotong/travel/orderTravelBusiness/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">订单状态：</label>
					<div class="col-sm-3 control-label control-left" >
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
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">下单时间：</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${orderTravelBusiness.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
					<label class="col-sm-2 control-label">订单号：</label>
					<div class="col-sm-3 control-label control-left">
					${orderTravelBusiness.orderNo}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单金额：</label>
					<div class="col-sm-3 control-label control-left ">${orderTravelBusiness.price}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">下单人信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户名：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.nickName}</div>
					<label class="col-sm-2 control-label">手机号：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.mobile}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">联系信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.contactsName}</div>
					<label class="col-sm-2 control-label">联系电话：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.contactsMobile}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.remark}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">出行信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出发地：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.cityName}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">时间：</label>
					<div class="col-sm-3 control-label control-left">
					<fmt:formatDate value="${orderTravelBusiness.useDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">人数：</label>
					<div class="col-sm-5 control-label control-left">
					<c:if test="${orderTravelBusiness.adultNum>0}">${orderTravelBusiness.adultNum}成人</c:if> 
					<c:if test="${orderTravelBusiness.childNum>0}">&nbsp;&nbsp;&nbsp;${orderTravelBusiness.adultNum}儿童</c:if> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">保险方案：</label>
				</div>
				<c:if test='${insurances.name !="" && not empty insurances.name}'>
					<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-3 control-label control-left">${insurances.name}</div>
						<div class="col-sm-3 control-label control-left">￥${insurances.price}/人*${insurances.num}</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">定制信息：</label>
				</div>
				<c:forEach items="${orderTravelDetailsList}" var="a">
				<div class="form-group">
					<label class="col-sm-2 control-label">第${a.day}天</label>
				</div>
				<c:forEach items="${a.citys}" var="b">
				<div class="form-group" style="margin-bottom: 0">
					<label class="col-sm-2 control-label">${b.cityName}</label>
					
					<c:if test="${ not empty b.orderTravelDetails}">
						<div class="form-group" style="margin-left: 16.5%">
							<label class="col-sm-2 control-label" style="text-align: left; width:auto;">商务：</label>
						<c:forEach items="${b.orderTravelDetails}" var="d">
							<c:if test="${ not empty d.busiInfo}">
								<div class="col-sm-2 control-label control-left" style="width:auto">【${d.busiInfo}】</div>
							</c:if>
							</c:forEach>
						</div>
					</c:if>
					
					<c:if test="${ not empty b.scenics}">
						<div class="form-group" style="margin-left: 16.5%">
							<label class="col-sm-2 control-label" style="text-align: left; width:auto;">景点：</label>
						<c:forEach items="${b.scenics}" var="c">
							<c:if test="${ not empty c.name}">
								<div class="col-sm-2 control-label control-left" style="width:auto">【${c.name}】</div>
							</c:if>
							</c:forEach>
						</div>
					</c:if>
					
					<%-- <label class="col-sm-2 control-label" style="text-align: left; width:auto;">景点：</label>
					<c:forEach items="${b.scenics}" var="c">
					<c:if test="${ not empty c.name}">
						<div class="col-sm-2 control-label control-left" style="width:auto">【${c.name}】</div>
					</c:if>
					</c:forEach> --%>
				</div>
				
				</c:forEach>
				<c:if test="${ not empty a.hotels}">
				<div class="form-group" style="margin-left: 16.5%">
					<label class="col-sm-2 control-label" style="text-align: left; width:auto;">酒店：</label>
				<c:forEach items="${a.hotels}" var="hotel">
					<div class="col-sm-3 control-label control-left" style="width: auto;">
					${hotel.hotelName},
					【${hotel.num}*${hotel.roomName}】
					</div>
				
				</c:forEach>
				</div>
				</c:if>
				</c:forEach>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">汽车信息：</label>
				</div>
				<c:forEach items="${orderCar}" var="a">
				<div class="form-group">
					<label class="col-sm-2 control-label">${a.carName}</label>
					<div class="col-sm-3 control-label control-left">
					${a.seatNum}座 &nbsp; &nbsp; ${a.carBagNum}容量 &nbsp; &nbsp;  ${a.comfort}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-10 control-label control-left">
					预计时长${a.carTime}分钟，行驶${a.carDistance}公里 (行驶里程与时间仅供参考，具体以实际行驶为准)</div>
				</div>
				</c:forEach>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">导游信息：</label>
				</div>
				<c:if test="${not empty guide}">
				<div class="form-group">
					<label class="col-sm-2 control-label">${guide.nickName}</label>
					<div class="col-sm-10 control-label control-left">
						性别：
						<c:if test="${guide.sex==1}">男</c:if>
						<c:if test="${guide.sex==2}">女</c:if>&emsp;  
						年龄：${guide.age}&emsp;  
						所在地区：${guide.countryName} - ${guide.cityName}
					</div>
				</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">支付信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付时间：</label>
					<div class="col-sm-3 control-label control-left">	<fmt:formatDate value="${orderTravelBusiness.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付方式：</label>
					<div class="col-sm-3 control-label control-left">	
						<c:if test="${orderTravelBusiness.payWay==1}">支付宝</c:if>
						<c:if test="${orderTravelBusiness.payWay==2}">微信</c:if>
						<c:if test="${orderTravelBusiness.payWay==3}">银联</c:if>
						<c:if test="${orderTravelBusiness.payWay==4}">paypal</c:if>
						<c:if test="${orderTravelBusiness.payWay==5}">线下</c:if>
					</div>
					<label class="col-sm-2 control-label">支付流水号：</label>
					 <div class="col-sm-3 control-label control-left">${orderTravelBusiness.payNo}</div> 
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付金额：</label>
					<div class="col-sm-3 control-label control-left">${orderTravelBusiness.price}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">备注信息：</label>
				</div>
				<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-3 control-label control-left">${orderTravelBusiness.orderRemark}</div>
				</div>	
		<c:if test="${fns:hasPermission('meiguotong:order:orderTravelBusiness.edit') || isAdd}">
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