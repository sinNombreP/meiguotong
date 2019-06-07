<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>旅行社管理</title>
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
		
		
		var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
		function checkImg(target){
			var fileSize = 0;         
		    if (isIE && !target.files) {     
		      var filePath = target.value;     
		      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
		      var file = fileSystem.GetFile (filePath);     
		      fileSize = file.Size;    
		    } else {    
		     fileSize = target.files[0].size;     
		     }   
		     var size = fileSize / 1024;
		     if(size>2000){  
		      alert("图片不能大于2M");
		      target.value="";
		      return false
		     }
		     var name=target.value;
		     var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		     if(fileName !="jpg" && fileName !="gif" && fileName !="png"){
		         alert("请选择jpg、gif、png格式上传！");
		         target.value="";
		         return false
		     }
		    return true;
		}
		
	$(function(){
		$(".wrapper").on("change", ".input_file_style", function(e) {
			var target = $(this)[0];
			var img =$(this);
			if (!checkImg(target)) {
				return;
			}
			for (var i = 0; i < e.target.files.length; i++) {
				var file = e.target.files.item(i);
				//实例化FileReader API    
				var freader = new FileReader();
				freader.readAsDataURL(file);
				freader.onload = function(e) {
					var src = e.target.result;
					var fd = new FormData();
					fd.append("attach", file);
					var xhr = new XMLHttpRequest();
					xhr.addEventListener("load", function(evt) { 
          				var textJson = JSON.parse(evt.target.responseText);
          				if(textJson.success){
          					img.prev().val(textJson.body.filePath);
          					img.prev().prev().attr("src",src);
          				} 
          				jp.success(textJson.msg);
          			}, false);
					xhr.addEventListener("error", function(evt){
						jp.info("上传失败");
					}, false);
					xhr.addEventListener("abort", function(evt){
						jp.info("取消上传");
					}, false);
					xhr.open("POST", "${ctx}/sys/img/uploadFile");
					xhr.send(fd);
				}
			}
		})
	})
		
		
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/sys/member/memberTravel"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
            <ul class="nav nav-tabs" style="padding-left:130px;padding-top:20px;">
				<li class="active"><a data-toggle="tab" href="#tab-1" >详细信息</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2">子账号管理</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3">账号优惠</a>
                </li>
            </ul>
         <div class="tab-content">
		<div class="tab-pane fade in  active" id="tab-1">
		<form:form id="inputForm" modelAttribute="memberTravel" action="${ctx}/sys/member/memberTravel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				
				<div class="form-group">
					<label class="col-sm-2 control-label">公司类型：</label>
					<div class="col-sm-5">
 						<form:select path="companyType" class="form-control" >
							<form:option value="1" label="个人"/>
							<form:option value="2" label="公司"/>
 						</form:select>			
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机号码：</label>
					<div class="col-sm-5">
 						<form:input path="mobile" htmlEscape="false" class="form-control isMobile"/>			
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电子邮箱：</label>
					<div class="col-sm-5">
						<form:input path="companyEmail" htmlEscape="false"    class="form-control email"/>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">地址：</label>
				<div class="col-sm-5">${memberInformation.countryName}${memberInformation.cityName}</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-5">
						<form:input path="address" htmlEscape="false"  class="form-control "/>
					</div>
				</div>
				<c:if test="${memberTravel.companyType==1}">
				<div class="form-group">
					<label class="col-sm-2 control-label">头像：</label>
					<div class="col-sm-5">
						<img  style="height: 140px;width: 200px;" src="${memberTravel.companyLogo}"  />
						<input type="hidden"name="companyLogo" value="${memberTravel.companyLogo}" /> 
						
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">昵称：</label>
					<div class="col-sm-5">
						<form:input path="companyName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名：</label>
					<div class="col-sm-5">
						<form:input path="legalPerson" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">身份证照：</label>
					<div class="col-sm-5">
						<c:set var="imgs" value="${fn:split(memberTravel.cardsImg,',')}"></c:set>
						<c:forEach items="${imgs}" var="img">
							<img  style="height: 140px;width: 200px;" src="${img}"  />
						</c:forEach>
						<input type="hidden"name="cardsImg" value="${memberTravel.cardsImg}" /> 
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司简介：</label>
					<div class="col-sm-5">
						<form:textarea path="companyContent" class="form-control" />
					</div>
				</div>
				</c:if>
				<c:if test="${memberTravel.companyType==2}">
				<div class="form-group">
					<label class="col-sm-2 control-label">法人代表：</label>
					<div class="col-sm-5">
						<form:input path="legalPerson" htmlEscape="false"    class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">企业名称：</label>
					<div class="col-sm-5">
						<form:input path="companyName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">企业logo：</label>
					<div class="col-sm-5">
						<img  style="height: 140px;width: 200px;" src="${memberTravel.companyLogo}"  />
						<input type="hidden"name="companyLogo" value="${memberTravel.companyLogo}" /> 
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">营业执照：</label>
					<div class="col-sm-5">
						<img  style="height: 140px;width: 200px;" src="${memberTravel.companyImg}"  />
						<input type="hidden"name="companyImg" value="${memberTravel.companyImg}" /> 
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">法人身份证：</label>
					<div class="col-sm-5">
						<c:set var="imgs" value="${fn:split(memberTravel.cardsImg,',')}"></c:set>
						<c:forEach items="${imgs}" var="img">
							<img  style="height: 140px;width: 200px;" src="${img}"  />
						</c:forEach>
						<input type="hidden"name="cardsImg" value="${memberTravel.cardsImg}" /> 
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">企业简介：</label>
					<div class="col-sm-5">
						<form:textarea path="companyContent" class="form-control" />
					</div>
				</div>
				</c:if>
				
				
<%-- 		<c:if test="${fns:hasPermission('sys:member:memberTravel:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if> --%>
		</form:form> 
		</div>
		
		<div id="tab-2" class="tab-pane fade form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">子账号数量：</label>
				<div class="col-sm-5">
					<%--<input value="${number}"  htmlEscape="false"    class="form-control "/>	--%>
						${number}
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">子账号管理*：</label>
				<div class="col-sm-5">
						<table class="table table-bordered">
							<thead>
								<td>账号</td>
								<td>密码</td>
							</thead>
							<c:forEach items="${memberList}" var="member">
							<tr>
								<td>
									<c:if test="${member.createType==1}">${member.mobile}</c:if>
									<c:if test="${member.createType==2}">${member.email}</c:if>
								</td>
								<td>${member.password}</td>
							</tr>
							</c:forEach> 
						</table>
				</div>
			</div>
		</div>
		<div id="tab-3" class="tab-pane fade form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">账号优惠*：</label>
				<div class="col-sm-5">
						<table  class="table table-bordered">
							<thead>
								<td>项目</td>
								<td>消费量</td>
								<td>返利</td>
								<td>操作</td>
							</thead>
							<c:forEach items="${memberDiscountList }" var="memberDiscount">
							<tr>
								<td>
							
									<c:if test="${memberDiscount.type==1}">包车租车</c:if>
									<c:if test="${memberDiscount.type==2}">短程接送</c:if>
									<c:if test="${memberDiscount.type==3}">接送机</c:if>
								</td>
								<td>${memberDiscount.stock}</td>
								<td>${memberDiscount.price}</td>
								<td><a href="#">操作</a></a></td>
							</tr>
							</c:forEach> 
						</table>
				</div>
			</div>
		</div>
		</div>
	</div>
	</div>
</div>
</div>
</body>
</html>