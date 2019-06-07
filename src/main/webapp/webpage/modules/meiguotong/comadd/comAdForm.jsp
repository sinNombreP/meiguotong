<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>广告设置管理</title>
	<meta name="decorator" content="ani"/>
	 <script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			 var imgSrc=$(".dv_pic_item").children(".imgSrc").val();
			  if(imgSrc==null||imgSrc==""){
				  alert("请上传图片");
				  return;
			  }
			if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
		//<div class="dv_pic_item">下标
		var divIndex;
		//更换图片的本地路径
		var src;
			
			function uploadComplete(evt) {
				var textJson = JSON.parse(evt.target.responseText);
				if(textJson.success){
					$(".dv_pic_item").eq(divIndex).children(".imgSrc").val(textJson.body.filePath);
					$(".dv_pic_item").eq(divIndex).children(".img_style").attr("src",src);
				} 
				alert(textJson.msg);
			}
			function uploadFailed(evt) {
				alert("上传失败");
			}
			function uploadCanceled(evt) {
				alert("取消上传");
			}
			
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/meiguotong/comadd/comAd/save",$('#inputForm').serialize(),function(data){
						if(data.success){							
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
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
                	  freader.onload = function(e) { 
                		    src=e.target.result;
                			var fd = new FormData();
                  			fd.append("attach", file);
                  			var xhr = new XMLHttpRequest();
                  			xhr.addEventListener("load", uploadComplete, false);
                  			xhr.addEventListener("error", uploadFailed, false);
                  			xhr.addEventListener("abort", uploadCanceled, false);
                  			xhr.open("POST", "${ctx}/meiguotong/comadd/comAd/uploadFile");
                  			xhr.send(fd);
                	  }    
             	}  
            });
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
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="comAd" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
		   	<tr>
		   		
				<td class="width-15 active"><label class="pull-right">语言:</label></td>

				<c:choose>
					<c:when test="${comAd.id!=null && comAd.id!=''}">
						<td class="width-35">${comAd.languageName}</td>
					</c:when>
					<c:otherwise>
						<td class="width-35"><form:select path="languageId"
								class="form-control required">
								<form:option value="" label="请选择" />
								<c:forEach items="${languageList}" var="languageList">
									<form:option value="${languageList.id}"
										label="${languageList.content}" />
								</c:forEach>
							</form:select></td>
					</c:otherwise>
				</c:choose>
				</tr>	
				
				<tr>
					<td class="width-15 active"><label class="pull-right">排序:</label></td>
					<td class="width-35">
         	        <form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</td>					
				</tr>				
				<tr>
					<td class="width-15 active"><label class="pull-right">广告图片：</label></td>
					<td>
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box">
	       					 		<div class="dv_pic_item">  
										<img alt="暂无图片" src="${comAd.imgUrl}" height="80px" width="100px" class="img_style" id="img"/> 
						            	<input type="hidden" name="imgUrl" value="${comAd.imgUrl}" class="imgSrc"/>   
	           							<input  style="width:300px" type="file" accept="image/jpg, image/png, image/gif, image/jpeg" class="input_file_style" id="file"/>
						           </div>
	           				 </div>
	   				 	</div> 
   				 	</td> 
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">广告标题:</label></td>
					<td class="width-35">         
         	        <form:input path="title" htmlEscape="false"    class="form-control required"/>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">广告链接:</label></td>
					<td class="width-35">
         	        <form:input path="link" htmlEscape="false"    class="form-control required"/>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">显示:</label></td>
					<td class="width-35">
         	       <form:select path="displayType"  class="form-control m-b required">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="电脑端"/>
					<form:option value="2" label="手机网页"/>
				</form:select>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">位置:</label></td>
					<td class="width-35">
         	        <form:select path="positionType"  class="form-control m-b required">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="网页头部"/>
					<form:option value="2" label="网页尾部"/>
				</form:select>
					</td>					
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>