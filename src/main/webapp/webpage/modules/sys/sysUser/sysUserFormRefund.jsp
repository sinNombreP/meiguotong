<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
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
		
		 Number.prototype.isInteger = function (global) {
		        var floor = Math.floor,
		            isFinite = global.isFinite;

		        Object.defineProperty(Number, 'isInteger', {
		            value: function isInteger(value) {
		                return typeof value === 'number' &&
		                    isFinite(value) &&
		                    floor(value) === value;
		            },
		            configurable: true,
		            enumerable: false,
		            writable: true
		        });
		    };
		
		
		$(function(){
			
			var flag = 0;  //0 添加  1 编辑
			var index = -1;
			var index3 = -1;
			
			//点击添加
			$(".addFeatures").on("click",function(event){
	            flag = 0;
	            index = $(this).parent().prev().attr("class");
	            $("#refundDay").val("");
	            $("#refundNum").val("");
			})
			
			//点击删除
			$(".tab-content").on("click",".delete",function(event){
	             $(this).parent().parent().remove();
			})
			
			//点击编辑
			$(".tab-content").on("click",".edit",function(event){
				 flag = 1;
	           	 index3 = $(this).prev().val();
	           	 index = $(this).parent().parent().parent().attr("class");
	             $("#refundDay").val($(this).prevAll(".refundDay").val());
	             $("#refundNum").val($(this).prevAll(".refundNum").val());
			})
			
			//点击确定
			$("#sure").on("click",function(event){
	            var refundDay = $("#refundDay").val().trim();
	            var refundNum = $("#refundNum").val().trim();
	            var index2 = $(".number_"+index).val();
               	if(refundDay == "" || refundNum == ""){
               		jp.info("请填写必填信息");
               		return;
               	}
               	if(isNaN(parseInt(refundDay)) || parseInt(refundDay)!= refundDay || isNaN(refundNum)){
               		jp.info("请填写正确的信息");
               		return;
               	}
	            if(!flag){
	            	var html =
	    		'<div class="form-group">'+
					'<div class="col-sm-10">'+
	'订单确认后，提前（包含）<input readonly="readonly" class="refundDay"  name="memberRefundList['+index+'].memberRefundList['+index2+'].refundDay" value="'+refundDay+'"/>'+
	'天，退款比例<input readonly="readonly" class="refundNum" name="memberRefundList['+index+'].memberRefundList['+index2+'].refundNum" value="'+refundNum+'"/>%'+
	'<input class="number2_'+index+"_"+index2+'"  type="hidden"  value="'+index2+'"/>'+
		'<a class="edit" data-toggle="modal" data-target="#myModal"> <i class="fa fa-edit btn btn-primary btn-xs">编辑</i> </a>'+
		           '<a  class="delete" title="删除"><i class="fa fa-trash btn btn-danger btn-xs"> 删除</i> </a>'+
				'</div>'+
				'</div>';
					$("."+index).append(html);
					$(".number_"+index).val(parseInt(index2)+1);
	            }else{
	            	$(".number2_"+index+"_"+index3).prevAll(".refundDay").val(refundDay);
	            	$(".number2_"+index+"_"+index3).prevAll(".refundNum").val(refundNum);
	            }
	            $("#refundDay").val("");
	            $("#refundNum").val("");
	            $("#myModal").modal('hide');
			})
		})
		

	</script>
</head>
<body>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="myModalLabel">添加退款信息</h4>
					      </div>
					      <div class="modal-body">
				       			 <div class="form-group">
									<label class="col-sm-2 control-label" style="margin-top:6px;">退款天数：</label>
									<div class="col-sm-10">
										<input id="refundDay" class="form-control"/>
										<br>
									</div>
								</div>
				       			 <div class="form-group">
									<label class="col-sm-2 control-label" style="margin-top:6px;">退款比例：</label>
									<div class="col-sm-10">
										<input id="refundNum" class="form-control"/>
										<br>
									</div>
								</div>
								<div style="clear:both;"></div>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        <button type="button" class="btn btn-primary" id="sure">确定</button>
					      </div>
					    </div>
					  </div>
		</div>

<div class="wrapper wrapper-content">
<c:if test="${userType==2}">
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
         <div class="tab-content">
		<div class="tab-pane fade in  active" id="tab-1">
			<form:form id="inputForm" modelAttribute="MemberRefund" action="${ctx}/sys/member/memberRefund/findList?flag=1"  enctype="multipart/form-data" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<c:forEach  items="${memberRefundList}" var="a" varStatus="status">
			<label class="col-sm-2 control-label" style="margin-bottom: 10px;">${a.refundContent}：</label><br/>
			<input class=" number_${status.index}" type="hidden" value="${fn:length(a.memberRefundList)}"/>
			<input name="memberRefundList[${status.index}].refundType" type="hidden" value="${a.refundType}"/>
			<input name="memberRefundList[${status.index}].refundContent" type="hidden" value="${a.refundContent}"/>
			 <div class="${status.index}" >
				<c:forEach  items="${a.memberRefundList}" var="b" varStatus="status2">
				<div class="form-group">
					<div class="col-sm-10">
	订单确认后，提前（包含）<input readonly="readonly" class="refundDay" name="memberRefundList[${status.index}].memberRefundList[${status2.index}].refundDay" value="${b.refundDay}"/>
	 天，退款比例<input readonly="readonly" class="refundNum" name="memberRefundList[${status.index}].memberRefundList[${status2.index}].refundNum" value="${b.refundNum}"/>%
	<input class="number2_${status.index}_${status2.index}"  type="hidden"  value="${status2.index}"/>
	<a class="edit" data-toggle="modal" data-target="#myModal"> <i class="fa fa-edit btn btn-primary btn-xs">编辑</i> </a>
	             <a  class="delete" title="删除"><i class="fa fa-trash btn btn-danger btn-xs"> 删除</i> </a>
					</div>
				</div>
				</c:forEach>
			</div>
				<div >
					<button type="button"  class="addFeatures" data-toggle="modal" data-target="#myModal">添加</button>
				</div>
				<hr/>
		</c:forEach>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</form:form> 
		</div>
	</div>
	</div>
	</div>
</div>
</c:if>
<c:if test="${userType==1}">
	<div style="color: red;font-size: 20px;">供应商才可以设置</div>
</c:if>
</div>
</body>
</html>