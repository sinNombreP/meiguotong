<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>酒店订单管理</title>
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
		<form:form id="inputForm" modelAttribute="orderHotel" action="${ctx}/meiguotong/hotel/orderHotel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">订单状态：</label>
					<div class="col-sm-3 control-label control-left" >
						<c:if test="${orderHotel.status==1}">待付款</c:if>
						<c:if test="${orderHotel.status==2}">待确定</c:if>
						<c:if test="${orderHotel.status==3}">待出行</c:if>
						<c:if test="${orderHotel.status==4}">待评价</c:if>
						<c:if test="${orderHotel.status==5}">已完成</c:if>
						<c:if test="${orderHotel.status==6}">取消订单</c:if>
						<c:if test="${orderHotel.status==7}">申请退款</c:if>
						<c:if test="${orderHotel.status==8}">退款中</c:if>
						<c:if test="${orderHotel.status==9}">退款成功</c:if>
						<c:if test="${orderHotel.status==10}">退款失败</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">下单时间：</label>
					<div class="col-sm-3 control-label control-left">
						<fmt:formatDate value="${orderHotel.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
					<label class="col-sm-2 control-label">订单号：</label>
					<div class="col-sm-3 control-label control-left">
					${orderHotel.orderNo}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单金额：</label>
					<div class="col-sm-3 control-label control-left ">${orderHotel.price}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">下单人信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户名：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.nickName}</div>
					<label class="col-sm-2 control-label">手机号：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.mobile}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">联系信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.contacts}</div>
					<label class="col-sm-2 control-label">联系电话：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.contactMobile}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.contactRemark}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">出行信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出发地：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.cityName}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">时间：</label>
					<div class="col-sm-3 control-label control-left">
					<fmt:formatDate value="${orderHotel.startDate}" pattern="yyyy-MM-dd"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">人数：</label>
					<div class="col-sm-5 control-label control-left"">
					<c:if test="${orderHotel.adultNum>0}">${orderHotel.adultNum}成人</c:if> 
					<c:if test="${orderHotel.childNum>0}">&nbsp;&nbsp;&nbsp;${orderHotel.childNum}儿童</c:if> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">酒店信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">酒店编号：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.hotelNo}</div>
					<label class="col-sm-2 control-label">酒店名称：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.hotelName}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">酒店地址：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.hotelAddress}</div>
				</div>
				<c:forEach items="${rooms}" var="item">
				<div class="form-group">
					<label class="col-sm-2 control-label">房间名称：</label>
					<div class="col-sm-3 control-label control-left">${item.roomName}</div>
					<label class="col-sm-2 control-label">最大入住人数：</label>
					<div class="col-sm-3 control-label control-left">${item.people}</div>
				</div>
				</c:forEach>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">出游人信息：</label>
				</div>
				<c:forEach items="${orderMemberList}" var="a">
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名：</label>
					<div class="col-sm-3 control-label control-left">${a.chineseName}（${a.englishName}）</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">证件类型：</label>
					<div class="col-sm-2 control-label control-left">
						<c:if test="${a.certType==1}">身份证</c:if>
						<c:if test="${a.certType==2}">护照</c:if>
						<c:if test="${a.certType==3}">本地ID</c:if>
					</div>
					<label class="col-sm-2 control-label">证件号码：</label>
					<div class="col-sm-2 control-label control-left">${a.certNo}</div>
					<label class="col-sm-2 control-label">证件有效期：</label>
					<div class="col-sm-2 control-label control-left">	<fmt:formatDate value="${a.certValidDate}" pattern="yyyy-MM-dd"/></div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出生年月：</label>
					<div class="col-sm-2 control-label control-left">
					<fmt:formatDate value="${a.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
					<label class="col-sm-2 control-label">手机号码：</label>
					<div class="col-sm-2 control-label control-left">${a.area}&nbsp;&nbsp;${a.mobile}</div>
				</div>
				</c:forEach>
				
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">支付信息：</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付时间：</label>
					<div class="col-sm-3 control-label control-left">	<fmt:formatDate value="${orderHotel.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付方式：</label>
					<div class="col-sm-3 control-label control-left">	
						<c:if test="${orderHotel.payWay==1}">支付宝</c:if>
						<c:if test="${orderHotel.payWay==2}">微信</c:if>
						<c:if test="${orderHotel.payWay==3}">银联</c:if>
						<c:if test="${orderHotel.payWay==4}">paypal</c:if>
						<c:if test="${orderHotel.payWay==5}">线下</c:if>
					</div>
					<label class="col-sm-2 control-label">支付流水号：</label>
					 <div class="col-sm-3 control-label control-left">${orderHotel.payNo}</div> 
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付金额：</label>
					<div class="col-sm-3 control-label control-left">${orderHotel.price}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label div-head">备注信息：</label>
				</div>
				<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-3 control-label control-left">${orderHotel.remark}</div>
				</div>
		<c:if test="${fns:hasPermission('meiguotong:hotel:orderHotel:edit') || isAdd}">
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