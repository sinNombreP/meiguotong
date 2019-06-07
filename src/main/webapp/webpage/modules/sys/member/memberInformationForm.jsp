<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会员详情管理</title>
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
			
	        $('#birthday').datetimepicker({
				 format: "YYYY-MM-DD"
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
				<a class="panelButton" href="${ctx}/sys/member/memberInformation"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="memberInformation" action="${ctx}/sys/member/memberInformation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">详细信息</a>
                </li>
                <c:if test="${not empty memberInformation.id}">
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">常用联系人</a>
                </li></c:if>
            </ul>
                        <div class="tab-content">
            				<div id="tab-1" class="tab-pane fade in  active" >
				<div style="height:20px;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会员头像：</label>
					<div class="col-sm-5">
						<img  style="height: 140px;width: 200px;" src="${memberInformation.photo}"  />
						<input type="hidden"name="photo" value="${memberInformation.photo}" /> 
						<%--<input type="file" class="input_file_style" />--%>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别：</label>
					<div class="col-sm-5">
						<form:select path="sex" class="form-control ">
							<form:option value="1" label="男"/>
							<form:option value="2" label="女"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">生日：</label>
					<div class="col-sm-5">
						<p class="input-group">
							<div class='input-group form_datetime' id='birthday'>
			                    <input type='text'  name="birthday" class="form-control"  value="<fmt:formatDate value="${memberInformation.birthday}" pattern="yyyy-MM-dd"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">地址：</label>
					<div class="col-sm-5">${memberInformation.countryName}-${memberInformation.cityName}</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-5">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
<%-- 		<c:if test="${fns:hasPermission('sys:member:memberInformation:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if> --%>
		       </div>
		     	<c:if test="${not empty memberInformation.id}">
				<div id="tab-2" class="tab-pane fade">
					<iframe src="${ctx}/sys/member/memberContacts/form?memberid=${memberInformation.id}" id="main"  scrolling="no" id="main" name="main" frameborder="0"  src="" style="min-height:600px;width:100%;height:100%;"></iframe>
				</div>
				</c:if>
		       </div>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>