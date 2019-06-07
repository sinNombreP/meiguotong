<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券管理</title>
	<meta name="decorator" content="ani"/>
	<link rel="stylesheet" href="${ctxStatic}/rightleftchoosebox/css/orso.css" />
	<script src="${ctxStatic}/rightleftchoosebox/js/orso.min.js" type="text/javascript"></script> 
	
	<script type="text/javascript">

		$(document).ready(function() {
			 $('#beginDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
	        $('#endDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
		        
			var userType=$(".userType:checked").val();
			if(userType==1){
				$(".useLabel").hide();
			}
			
			var type=$("#type").val();
			if(type==0){
				$(".maxNum").hide();
				$(".pushType").hide();
			}
			if(type==1){
				$(".maxNum").hide();
			}
			if(type==2){
				$(".pushType").hide();
			}		
			
			$(".userType").change(function(){
				var userType=$(".userType:checked").val();
				if(userType==1){
					$(".useLabel").hide();
				}
				if(userType==2){
					$(".useLabel").show();
				}
			});
			$("#type").change(function(){
				var type=$("#type").val();
				if(type==0){
					$(".maxNum").hide();
					$(".pushType").hide();
				}
				if(type==1){
					$(".maxNum").hide();
					$(".pushType").show();
				}
				if(type==2){
					$(".maxNum").show();
					$(".pushType").hide();
				}	
			});
			
			$(".box").orso({
				boxl:".box_l",//左边大盒子
				boxr:".box_r",//右边大盒子
				boxlrX:"li",//移动小盒子
				boxon:"choose",//点击添加属性
				idclass:true,//添加的属性是否为class//true=class; false=id;
				boxlan:"#left",//单个向左移动按钮
				boxran:"#right",//单个向右移动按钮
				boxtan:"#top",//单个向上移动按钮
				boxban:"#bottom",//单个向下移动按钮
				boxalllan:"#allleft",//批量向左移动按钮
				boxallran:"#allright",//批量向右移动按钮
				boxalltan:"#alltop",//移动第一个按钮
				boxallban:"#allbottom"//移动最后一个按钮
			});
			
			$("#inputForm").validate({
				submitHandler: function(form){
					var type=$("#type").val();
					if(type==0){
						alert("请选择推送模式");
						return;
					}

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
		function formSub(){
			var count = document.getElementById("box_r").getElementsByTagName("li").length; 
			var useLabel="";
			var useLabeltitle="";
			for(var i=0;i<count;i++){
				var label=$(".box_r>li").eq(i).attr("value");
				var labeltitle=$(".box_r>li").eq(i).text();
				if(i==(count-1)){
					useLabel += label;
					useLabeltitle += labeltitle;
				}else{
					useLabel += label+",";
					useLabeltitle += labeltitle+",";
				}
			}
			$("#useLabel").val(useLabel);
			$("#useLabeltitle").val(useLabeltitle);
			
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/sys/coupon"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="coupon" action="${ctx}/sys/coupon/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">优惠卷标题：</label>
					<div class="col-sm-10" style="width:300px">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">优惠卷描述：</label>
					<div class="col-sm-10" style="width:300px">
						<form:input path="descrption" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">使用门槛：</label>
					<div class="col-sm-10" style="width:300px">
						<form:input path="stock" htmlEscape="false" class="form-control " />
					</div><span style="line-height:32px">元</span>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">使用有效期：</label>
					<div class="col-sm-10"  style="width:300px">
						<div>
							<div class='input-group form_datetime' id='beginDate'>
			                    <input type='text'  name="beginDate" class="form-control"  value="<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </div>
					</div>
					<div class="col-sm-10"  style="width:20px;line-height:50px;">~</div>
					<div class="col-sm-10" style="width:300px">
						<div>
							<div class='input-group form_datetime' id='endDate'>
			                    <input type='text'  name="endDate" class="form-control"  value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">减免金额：</label>
					<div class="col-sm-10" style="width:300px">
						<form:input path="price" htmlEscape="false" class="form-control "/>
					</div><span style="line-height:32px">元</span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">使用类别限定：</label>
					<div class="col-sm-10" style="margin-top:6px">
						<input class="userType" type="radio" name="useType" value="1" <c:if test="${coupon.useType!=2}">checked</c:if> />无限制
						<input class="userType" type="radio" name="useType" value="2" <c:if test="${coupon.useType==2}">checked</c:if> />限制使用分类
					</div>
				</div>
				<div class="form-group useLabel">
					<div>
						<div class="box">
							<div class="box_l">
								<div id="test">
								<c:forEach items="${labelList}" var="label">
									<li  value="${label.id}">${label.title}</li>
								</c:forEach>
								</div>
							</div>
							<div class="box_m">
								<!-- <a href="javascript:" id="right">选择→</a> --> <a href="javascript:"
									id="allright">全选&gt;&gt;</a> <!-- <a href="javascript:" id="left">删除←</a> -->
								<a href="javascript:" id="allleft">&lt;&lt;全删</a>
							</div>
							<div id="box_r" class="box_r">
								<c:forEach items="${labelUseList}" var="label">
									<li class="useLabel" value="${label.id}">${label.title}</li>
								</c:forEach>
							</div>
							<input id="useLabel" type="hidden" name="category"/>
							<input id="useLabeltitle" type="hidden" name="categoryName"/>
						</div>
					</div>
				</div>
				<div class="form-group type">
					<label class="col-sm-2 control-label" >推送模式：</label>
					<div class="col-sm-10"  style="width:300px">
						<form:select id="type" path="type" class="form-control ">
							<form:option value="0" label="请选择"/>
							<form:option value="1" label="后台推送"/>
							<form:option value="2" label="点击领取"/>
						</form:select>
					</div>
				</div>
				<div class="form-group maxNum">
					<label class="col-sm-2 control-label">最大张数：</label>
					<div class="col-sm-10"  style="width:300px">
						<form:input path="allNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group pushType">
					<label class="col-sm-2 control-label">推送人员：</label>
					<div class="col-sm-10" style="margin-top:6px">
						<input type="radio" name="typeWay" value="1" <c:if test="${coupon.typeWay!=2&&coupon.typeWay!=3}">checked</c:if> />所有会员
						<input type="radio" name="typeWay" value="2" <c:if test="${coupon.typeWay==2}">checked</c:if> />一个月内有交易的会员
						<input type="radio" name="typeWay" value="3" <c:if test="${coupon.typeWay==3}">checked</c:if> />本月注册的会员
					</div>
				</div>
		<c:if test="${isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div style="width:1000px">
		                     <button type="button" onclick="formSub()" class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
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