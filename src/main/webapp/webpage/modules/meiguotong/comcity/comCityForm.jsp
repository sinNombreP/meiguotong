<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>城市表管理</title>
	<meta name="decorator" content="ani"/>
	<style>
	   .boxline{
	   margin-top:20px;
	   padding-top:30px;
	   padding-right:30px;
	   margin-left:18%;
	   border:solid 1px #000000;
	   }
    
         td{
            text-align:center;
            border:solid 1px #000000;
            width: 150px;
            height: 40px;
            font-family: 'PingFangSC-Regular', 'PingFang SC';
            font-weight: 400;
            font-style: normal;
             }
      .btn_add_pic_a,.btn_add_pic_b,.btn_add_pic_c,.btn_add_pic_d{  
	        width: 80px;  
	        height: 30px;  
	        border-radius: 6px;  
	        outline: none;  
	        border: none;  
	        background-color: #00BCD4;  
	        color: #fff;  
	        cursor: pointer;  
	        margin-top: 20px;  
	        margin-bottom: 20px;  
	    }  
	    .deleteBtn{
	    	padding:2px 10px;
	    	background:#E1E1E1;
	    }
	    .dv_pic_item_a,.dv_pic_item_b,.dv_pic_item_c,.dv_pic_item_d{  
	        width: 200px;  
	        height: 200px;  
	        margin: 5px 5px;  
	        float: left;  
	    }  
	    .input_file_style_a,.input_file_style_b,.input_file_style_c,.input_file_style_d{  
	        width: 200px;  
	        height: 20px;  
	    }  
	    .img_style{  
	        width: 200px;  
	        height: 140px;  
	        display: block;  
	        background-size: 100% auto;  
	    } 
	    .addAttr{
	        height: 30px;  
	        border-radius: 6px;  
	        outline: none;  
	        border: none;  
	        background-color: #00BCD4;  
	        color: #fff;  
	        cursor: pointer;  
	    }
	     .remove{
	        height: 22px;  
	        border-radius: 6px;  
	        outline: none;  
	        border: none;  
	        background-color: #de6764;  
	        color: #fff;  
	        cursor: pointer;  
	    }     
	</style>
	<script type="text/javascript">
		//删除图片
		function deleteElement(Obj){
	    	Obj.parentNode.parentNode.removeChild(Obj.parentNode); 
	    }
		//<div class="dv_pic_item">下标
		var divIndex;
		//更换图片的本地路径
		var src;
		var btnCount_a=0; 
		var btnCount_b=0; 
		var btnCount_c=0; 
		var btnCount_d=0;  
	   
		function initClickListener_b(){  
	        $(".btn_add_pic_b").click(function(){
	        	if(btnCount_b>0){
	        		if($("#file_b_"+btnCount_b).length>0){
		        		var fileValue=$("#file_b_"+btnCount_b).val();
			        	if(fileValue == null || fileValue==""){
			        		return;
			        	}
	        		}
	        	} 
	            btnCount_b++;  
	            var t='<div class="dv_pic_item_b">';  
	            t+='<img src="" alt="" class="img_style" id="img_b_'+btnCount_b+'"/>';  
	            t+='<input type="hidden" name="photoUrl" class="imgSrc" id="src_b_'+btnCount_b+'"/>';  
	            t+='<input type="file" class="input_file_style_b" id="file_b_'+btnCount_b+'"/>';
	            t+='<a id="btn_b_'+btnCount_b+'" onclick="deleteElement(this)" class="deleteBtn">删除</a>'
	            t+='</div>';  
	            $(".dv_pic_box_b").append(t);
	        });
		};  
		
		
		function uploadComplete_b(evt) {
			var textJson = JSON.parse(evt.target.responseText);
			if(textJson.success){
				$(".dv_pic_item_b").eq(divIndex).children(".imgSrc").val(textJson.body.filePath);
				$(".dv_pic_item_b").eq(divIndex).children(".img_style").attr("src",src);
			} 
			jp.success(textJson.msg);//这是框架方法非框架用alert
		}
		
		function uploadFailed(evt) {
			jp.success("上传失败");//这是框架方法非框架用alert
		}
		function uploadCanceled(evt) {
			jp.success("取消上传");//这是框架方法非框架用alert
		}
		
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
			//单图上传
			$(".input_file_style").change(function(e){ 
	        	var fileId=$(this).attr("id");
	        	var target=document.getElementById($(this).attr("id"));
	        	if(!checkVideo(target)){
	      			return;
	      		}   
	         	 for (var i = 0; i < e.target.files.length; i++) {    
	             	 var file = e.target.files.item(i);    
	             	 //实例化FileReader API    
	             	 var freader = new FileReader();    
	            	  freader.readAsDataURL(file);
	            	  jp.loading();			//这是框架方法，阻塞浏览器（转圈）
	            	  freader.onload = function(e) { 
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
	              			xhr.open("POST", "${ctx}/meiguotong/comcity/comCity/uploadVideo");
	              			xhr.send(fd);
	            	  }    
	         	}  
	        });
			
			
			//点击添加多图——b
			initClickListener_b();
			$(".dv_pic_box_b").on("change",".input_file_style_b",function(e){ 
		        	var fileId=$(this).attr("id");
		        	divIndex=$(".dv_pic_item_b").index( $("#"+fileId).parents(".dv_pic_item_b"))
		        	var target=document.getElementById($(this).attr("id"));
		        	if(!checkImg(target)){
		      			return;
		      		}   
		         	 for (var i = 0; i < e.target.files.length; i++) {    
		             	 var file = e.target.files.item(i);    
		             	 //实例化FileReader API    
		             	 var freader = new FileReader();    
		            	  freader.readAsDataURL(file);  
		            	  jp.loading();			//这是框架方法，阻塞浏览器（转圈）
		            	  freader.onload = function(e) { 
		            		  	src=e.target.result;
		            			var fd = new FormData();
		              			fd.append("attach", file);
		              			var xhr = new XMLHttpRequest();
		              			xhr.addEventListener("load", uploadComplete_b, false);
		              			xhr.addEventListener("error", uploadFailed, false);
		              			xhr.addEventListener("abort", uploadCanceled, false);
		              			xhr.open("POST", "${ctx}/meiguotong/comcity/comCity/uploadFile");
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
	function checkVideo(target){
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
         if(size>51200){  
          alert("视频不能大于50M");
          target.value="";
          return false
         }
        
        return true;
       
	}
	  </script>
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
			})
			});
			
			function back(provinceId){
				  window.location = "${ctx}/meiguotong/comcity/comCity/list?provinceId=" + provinceId;
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
			<a class="panel-title" style="cursor: pointer;" onclick="back(${provinceId})">返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comCity" enctype="multipart/form-data" action="${ctx}/meiguotong/comcity/comCity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="provinceId" name="provinceId" value="${provinceId}">
		<input type="hidden" id="languageid" name="languageid" value="${languageid}">
		<sys:message content="${message}"/>
			
				<div class="form-group">
					<label class="col-sm-2 control-label">城市名称</label>
					<div class="col-sm-10">
						<form:input path="cityName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注名称</label>
					<div class="col-sm-10">
						<form:input path="cityRemark" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">城市详情</label>
					<div class="col-sm-10">
						<form:textarea path="cityDetails" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				
					<div class="form-group">
					<label class="col-sm-2 control-label">状态</label>
					<div class="col-sm-10">
						<input type="radio" name="status" value="1" checked="checked">显示
                      <input type="radio" name="status"  value="2">禁止
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">城市图片</label>
					<div class="col-sm-10" style=" border:1px solid #cccccc;">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box_b">                                <!-- 多个多图class修改-->
	       					 <c:if test="${comCity.photoUrl!=null}">
	       					 <c:set value="${fn:split(comCity.photoUrl, ',')}" var="photoUrl"></c:set>
	       					 	<c:forEach var="photoUrl" items="${photoUrl}" >
	       					 		<div class="dv_pic_item_b">  		 <!-- 多个多图class修改-->
	            						<%! 
											char number_b='a';
											synchronized void numberAdd_b()
											{
												number_b++;
											}
											synchronized void numberZero_b()
											{
												number_b='a';
											}
										%>
										<img src="${photoUrl}" class="img_style" id="img_b_<%=number_b%>"/>    <!-- 多个多图id修改-->
						            	<input type="hidden" name="photoUrl" value="${photoUrl}" class="imgSrc"/>   
	           							<input type="file" class="input_file_style_b" id="file_b_<%=number_b%>"/>       <!-- 多个多图class修改-->   <!-- 多个多图id修改-->
						            	<a id="btn_b_<%=number_b%>" onclick="deleteElement(this)" class="deleteBtn">删除</a>     <!--多个多图 id修改-->
						            	<% numberAdd_b();%>
						           </div>
	       					 	</c:forEach>
	       					 	<% numberZero_b();%>
	       					 </c:if>  
	           				 </div>
	       					 <button type="button" class="btn_add_pic_b" >添加图片</button> <!--多个多图class修改-->
	   				 	</div> 
					</div>
				</div>
				<c:choose>
					<c:when test="${comCity.id!=null && comCity.id!=''}">
						<div class="form-group">
							<label class="col-sm-2 control-label">城市视频:</label>
							<div class="col-sm-10">
								<div class="dv_info_box">
									<div class="dv_pic_box">
										<div class="dv_pic_item">
											<video controls="controls" src="${comCity.videoUrl}"
												height="80px" width="100px" class="img_style"></video>
											<input type="hidden" name="videoUrl" value=""
												class="imgSrc" /> <input style="width: 300px" type="file"
												class="input_file_style" id="file1" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<label class="col-sm-2 control-label">城市视频:</label>
							<div class="col-sm-10">
								<div class="dv_info_box">
									<div class="dv_pic_box">
										<div class="dv_pic_item">
											<video controls="controls" src="" height="80px"
												width="100px" class="img_style"></video>
											<input type="hidden" name="videoUrl" value=""
												class="imgSrc" /> <input style="width: 300px" type="file"
												class="input_file_style" id="file1" />
											<!-- 多个单图上传改id -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				
					
		<c:if test="${fns:hasPermission('meiguotong:comcity:comCity:edit') || isAdd}">
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