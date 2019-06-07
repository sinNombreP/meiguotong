<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>Logo设置管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		//<div class="dv_pic_item">下标
		var divIndex;
		//更换图片的本地路径
		var src;
		
		function uploadFailed(evt) {
			alert("上传失败");
		}
		function uploadCanceled(evt) {
			alert("取消上传");
		}
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var pcLogoHidden = $("#pcLogoHidden").val();
					var imgHidden =  $("#imgHidden").val();
					if(!pcLogoHidden){
						jp.error("请添加网站logo图片");
						return;
					}
					if(!imgHidden){
						jp.error("请添加服务热线图片");
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
			$(".input_file_style").change(function(e){ 
            	var fileId=$(this).attr("id");
            	divIndex=$(".dv_pic_item").index( $("#"+fileId).parents(".dv_pic_item"))
            	var target=document.getElementById($(this).attr("id"));
            	if(!checkImg(target)){
          			return;
          		}   
             	 for (var i = 0; i < e.target.files.length; i++) {    
                 	 var file = e.target.files.item(i);    
                 	 //实例化FileReader API    
                 	 var freader = new FileReader();    
                	  freader.readAsDataURL(file); 
                	  jp.loading();
                	  freader.onload = function(e) { 
                		    src=e.target.result;
                			var fd = new FormData();
                  			fd.append("attach", file);
                  			var xhr = new XMLHttpRequest();
                  			xhr.addEventListener("load", function(evt) { 
	              				var textJson = JSON.parse(evt.target.responseText);
	              				if(textJson.success){
	              					$("#"+fileId).parent(".dv_pic_item").children(".imgSrc").val(textJson.body.filePath);
	              					$("#"+fileId).parent(".dv_pic_item").children(".img_style").attr("src",textJson.body.filePath);
	              				} 
	              				jp.success(textJson.msg);//这是框架方法非框架用alert
	              			}, false);
                  			xhr.addEventListener("error", uploadFailed, false);
                  			xhr.addEventListener("abort", uploadCanceled, false);
                  			xhr.open("POST", "${ctx}/meiguotong/hotelroomdevice/hotelRoomDevice/uploadFile");
                  			xhr.send(fd);
                	  }    
             	}  
            });
			$("#languageid").change(function(){
				var languageid = $(this).val();
				var params  = {"languageid":languageid};
				var url = "${ctx}/meiguotong/comprotocol/comProtocol/getComProtocol";
				jp.post(url,params,function(data){
					var comProtocol = data.body.comProtocol;
					if(comProtocol){
						$("#id").val(comProtocol.id);
						var pcLogo = "";
						var img = "";
						if(comProtocol.pcLogo&&comProtocol.pcLogo!=undefined){
							pcLogo = comProtocol.pcLogo;
						}
						if(comProtocol.img&&comProtocol.img!=undefined){
							img = comProtocol.img;
						}
						$("#pcLogoImg").attr("src",pcLogo);
						$("#pcLogoHidden").val(pcLogo);
						$("#logoLink").val(comProtocol.logoLink);
						$("#title").val(comProtocol.title);
						$("#img").attr("src",img);
						$("#imgHidden").val(img);
						$("#phone").val(comProtocol.phone);
					}else{
						$("#id").val("");
						$("#pcLogoImg").attr("src","");
						$("#pcLogoHidden").val("");
						$("#logoLink").val("");
						$("#title").val("");
						$("#img").attr("src","");
						$("#imgHidden").val("");
						$("#phone").val("");
					}
				})
			})
			
		});
	</script>
	     <script type="text/javascript">
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
	 
	  </script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				Logo设置管理
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comProtocol" action="${ctx}/meiguotong/comprotocol/comProtocol/save" method="post" class="form-horizontal">
       <form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">选择语言</label>
					<div class="col-sm-10">
						<form:select  path="languageid" class="form-control required">
							<form:option value="" label="请选择"/>
							<c:forEach items="${comLanguageList}" var="languageList">
								<form:option value="${languageList.id}" label="${languageList.content}"/>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">网站logo</label>
					<div class="col-sm-10">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box">	
	       					 		<div class="dv_pic_item">  
									<img alt="暂无图片" id="pcLogoImg" src="${comProtocol.pcLogo}" height="80px" width="100px" class="img_style"/> 
						            	<input id="pcLogoHidden" type="hidden" name="pcLogo" value="${comProtocol.pcLogo}" class="imgSrc"/>   
	           							<input  style="width:300px" type="file" accept="image/jpg, image/png,image/gif, image/jpeg" 
	           							class="input_file_style" id="file2"/> 	<!-- 多个单图上传改id -->
						           </div>
	           				 </div>
	   				 	</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">logo链接</label>
					<div class="col-sm-10">
						<form:input path="logoLink" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">title设置</label>
					<div class="col-sm-10">
						<form:input path="title" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">服务热线图标</label>
					<div class="col-sm-10">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box">	
	       					 		<div class="dv_pic_item">  
									<img alt="暂无图片" id="img" src="${comProtocol.img}" height="80px" width="100px" class="img_style"/> 
						            	<input id="imgHidden" type="hidden" name="img" value="${comProtocol.img}" class="imgSrc"/>   
	           							<input  style="width:300px" type="file" accept="image/jpg, image/png,image/gif, image/jpeg" 
	           							class="input_file_style" id="file1"/> 	<!-- 多个单图上传改id -->
						           </div>
	           				 </div>
	   				 	</div>
					
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">电话管理</label>
					<div class="col-sm-10">
						<form:input path="phone" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
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
</body>
</html>