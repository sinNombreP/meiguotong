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
			});
			/* 添加附近城市弹框/附近城市弹框搜索 */
			$("#addNearByCity,#searchCity").click(function(){
				if($(this).attr('id')=="addNearByCity"){
					$("#cityNames").val("");
					}
				var obj2 = document.getElementsByClassName("selectColor");
				var str2 = "";
				if (obj2.length > 0) {
					for (i = 0; i < obj2.length; i++) {
						if (i == obj2.length - 1) {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val()
							str2 += colorid;
						} else {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val();
							str2 += colorid + ",";
						}
					}
				}
				str2+=","+$("#cityId").val();
				var url="${ctx}/meiguotong/comcity/comCity/findcity";
				var params="";
				/* var idname=$(this).attr('id');
				if (idname=="searchCity"){ */
					params={"languageId":$("#languageid").val(),"provinceId":$("#provinceId").val(),"cityName":$("#cityNames").val(),"cityids":str2};
				/* }else if(idname=="addNearByCity"){
					params={"languageId":$("#languageid").val(),"provinceId":$("#provinceId").val(),"cityids":str2};
				} */
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td ><input type="checkbox"';
							t+=' value="';
	                        t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].cityName;
							t+='"></td><td>';
							t += list[i].id;
							t += '</td><td>';
							t += list[i].cityName;
							t += '</td></tr>';
						}
					}
					$("#citybody").empty();
					$("#citybody").append(t);
				});
			});
			
			/* 移除已选的附近城市 */
			$("#proFeatures").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor").remove();
			});
			
			/* 添加参团弹框/参团弹框搜索 */
			$("#addRoutes,#searchRoutes").click(function(){
				if($(this).attr('id')=="addRoutes"){
				$("#titles").val("");
				}
				var obj2 = document.getElementsByClassName("selectColor2");
				var str2 = "";
				if (obj2.length > 0) {
					for (i = 0; i < obj2.length; i++) {
						if (i == obj2.length - 1) {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val()
							str2 += colorid;
						} else {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val();
							str2 += colorid + ",";
						}
					}
				}
				var url="${ctx}/meiguotong/comcity/comCity/routes";
				var	params={"title":$("#titles").val(),"routeids":str2,"cityId":$("#cityId").val()};
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td ><input type="checkbox"';
							t+=' value="';
	                        t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].title;
							t+='"></td><td>';
							t += list[i].id;
							t += '</td><td>';
							t += list[i].title;
							t += '</td></tr>';
						}
					}
					$("#routesBody").empty();
					$("#routesBody").append(t);
				});
			});
			
			/* 移除已选的行程 */
			$("#proFeatures2").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor2").remove();
			});
			
			/* 添加景点弹框/景点弹框搜索 */
			$("#addScenics,#searchScenics").click(function(){
				if($(this).attr('id')=="addScenics"){
				$("#scenicName").val("");
				}
				var obj2 = document.getElementsByClassName("selectColor3");
				var str2 = "";
				if (obj2.length > 0) {
					for (i = 0; i < obj2.length; i++) {
						if (i == obj2.length - 1) {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val()
							str2 += colorid;
						} else {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val();
							str2 += colorid + ",";
						}
					}
				}
				var url="${ctx}/meiguotong/comcity/comCity/scenics";
				var	params={"name":$("#scenicName").val(),"scenicids":str2,"cityId":$("#cityId").val()};
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td ><input type="checkbox"';
							t+=' value="';
	                        t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].name;
							t+='"></td><td>';
							t += list[i].id;
							t += '</td><td>';
							t += list[i].name;
							t += '</td></tr>';
						}
					}
					$("#scenicsBody").empty();
					$("#scenicsBody").append(t);
				});
			});
			
			/* 移除已选的景点 */
			$("#proFeatures3").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor3").remove();
			});
			
			/* 添加当地玩家弹框/当地玩家弹框搜索 */
			$("#addGuides,#searchGuides").click(function(){
				if($(this).attr('id')=="addGuides"){
				$("#realName").val("");
				}
				var obj2 = document.getElementsByClassName("selectColor4");
				var str2 = "";
				if (obj2.length > 0) {
					for (i = 0; i < obj2.length; i++) {
						if (i == obj2.length - 1) {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val()
							str2 += colorid;
						} else {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val();
							str2 += colorid + ",";
						}
					}
				}
				var url="${ctx}/meiguotong/comcity/comCity/guides";
				var	params={"realName":$("#realName").val(),"guideids":str2,"cityId":$("#cityId").val()};
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td ><input type="checkbox"';
							t+=' value="';
	                        t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].realName;
							t+='"></td><td>';
							t += list[i].id;
							t += '</td><td>';
							t += list[i].realName;
							t += '</td></tr>';
						}
					}
					$("#guidesBody").empty();
					$("#guidesBody").append(t);
				});
			});
			
			/* 移除已选的当地玩家 */
			$("#proFeatures4").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor4").remove();
			});
			
			/* 添加旅游定制弹框/旅游定制弹框搜索 */
			$("#proFeatures1").on("click","#addTravels",function(){
				$("#tableid").val($(this).parent().parent().attr('id'));
				addTravels();
			})
			/* 弹框搜索框搜索 */
			$("#searchTravel").click(function(){
				addTravels();
			})
			
			/* 点击添加筛选已选项目 */
			function addTravels(){
				if($(this).attr('id')=="addTravels"){
					$("#travelName").val("");
					}
					var obj2 = $("#"+$("#tableid").val()).find(".selectColor11");
					var str2 = "";
					if (obj2.length > 0) {
						for (i = 0; i < obj2.length; i++) {
							if (i == obj2.length - 1) {
								var id = obj2[i].id;
								var colorid = $(
										"#" + id).find(
										".colorid")
										.val()
								str2 += colorid;
							} else {
								var id = obj2[i].id;
								var colorid = $(
										"#" + id).find(
										".colorid")
										.val();
								str2 += colorid + ",";
							}
						}
					} 
					var url="${ctx}/meiguotong/comcity/comCity/travels";
					var	params={"name":$("#travelName").val(),"travelIds":str2,"city":$("#cityId").val()};
					jp.post(url,params,function(data){
						var list = data.body.list;
						var t=''; 
						if(list){
							for(var i in list){
								t += '<tr><td ><input type="checkbox"';
								t+=' value="';
		                        t+=list[i].id;
								t+='"><input type="hidden"  value="';
								t+=list[i].name;
								t+='"></td><td>';
								t += list[i].id;
								t += '</td><td>';
								t += list[i].name;
								t += '</td></tr>';
							}
						}
						$("#travelsBody").empty();
						$("#travelsBody").append(t);
					});
			}
			
			/* 移除已选的行程*/
			$("#proFeatures1").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor1").remove();
			});
			
			/* 移除已选的行程详情*/
			$("#proFeatures1").on("click", ".removetravel",
					function() {
						$(this).parents(".selectColor11").remove();
			});
			
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
		<input type="hidden" id="cityId" name="cityid" value="${comCity.id}">
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
						<input type="radio" name="status" value="1" <c:if test='${comCity.status == "1"}'>checked</c:if>>显示
                     	<input type="radio" name="status" value="2" <c:if test='${comCity.status == "2"}'>checked</c:if>>禁止
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">城市图片</label>
					<div class="col-sm-10" style=" border:1px solid #cccccc;">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box_b">                                <!-- 多个多图class修改-->
	       					 <c:if test="${comCity.photoUrl!=null}">
	       					 <c:set value="${fn:split(comCity.photoUrl, ',')}" var="photoUrl"></c:set>
	       					 	<c:forEach var="photoUrl" items="${photoUrl}">
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
				<div class="form-group">
					<label class="col-sm-2 control-label">附近城市:</label>
					<div class="col-sm-10">
					<button type="button" id="addNearByCity" class="addFeatures"  data-toggle="modal" data-target="#myModal" >添加</button>
					</div>
				</div>
						<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-10">	
						<table class="table table-bordered">
							<tbody id="proFeatures">
								<tr>
									<td class="width-15 active">ID</td>
									<td class="width-15 active">城市</td>
									<td class="width-15 active">操作</td>
								</tr>
							<c:forEach var="nearCitys" items="${nearCitys}" varStatus="sta">
								<tr class="selectColor"  id="selectColor_${sta.index}" style="text-align:center;">
									<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
										<input class="colorid" name="nearCityNumber"  type="hidden" value="${nearCitys.id}"/>${nearCitys.id}
						   			</td>
						   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${nearCitys.cityName}
						   			</td>
						   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
										<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;">租车</label>
					<div class="col-sm-10">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" >使用汽车模块:</label>
					<div class="col-sm-10">
					<input id="u3652_input" type="checkbox" value="1" name="isCar" <c:if test='${comCity.isCar == "1"}'>checked</c:if> >启用
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" ></label>
					<div class="col-sm-10">
					<table>
					<tr>
					<td>包车租车</td>
					<td>短程接送</td>
					<td>接送机</td>
					</tr>
					</table>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;">旅游定制</label>
					<div class="col-sm-10">
					<input id="u3652_input" type="checkbox" value="1" name="isTourism" <c:if test='${comCity.isTourism == "1"}'>checked</c:if>>启用
					</div>
				</div>				
					<!-- <div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-10">
					<button type="button" id="addFeatures" class="addFeatures" data-toggle="modal" data-target="#myModal1">添加</button>
					</div>
				</div> -->
				<div class="form-group">
					<label class="col-sm-2 control-label" ></label>
					<div class="col-sm-10">
					<%-- <table class="table table-bordered">
							<tbody id="proFeatures1">
								<tr>
									<td class="col-sm-3 active">分类名称</td>
									<td class="col-sm-7 active">行程</td>
									<td class="col-sm-2 active">操作</td>
								</tr>
							<c:forEach var="comCityTravels" items="${comCityTravels}" varStatus="sta">
								<tr class="selectColor1"  id="selectColor1_${sta.index}" style="text-align:center;">
									<td class="col-sm-3" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
									<input id="${sta.index}" class="colorid" name="list[${sta.index}].classname" type="hidden" value="${comCityTravels.classname}"/>${comCityTravels.classname}
					   				</td>
					   				<td class="col-sm-7 newTravel" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
					   					<table class="table table-bordered"><tbody >
											<tr><td class="width-15 active">ID</td><td class="width-15 active">行程详情</td><td class="width-15 active">操作</td>
											</tr>
					   					<c:forEach var="traveldetails" items="${traveldetails}" varStatus="vsta">
						   					<c:if test='${comCityTravels.classname == traveldetails.className}'>
												<tr class="selectColor11"  id="selectColor11_${vsta.index}" style="text-align:center;">
												<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
												<input class="colorid" name="list[${sta.index}].travelid"  type="hidden" value="${traveldetails.id}" />${traveldetails.id}
												</td>
												<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${traveldetails.name}
												</td>
												<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
												<button type="button" class="removetravel fa fa-trash btn btn-danger btn-xs">删除</button>
												</td></tr>
											</c:if>
										</c:forEach>
											</tbody>
											</table>
					   				</td>
					   				<td class="col-sm-2" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
					   				<button type="button" id="addTravels" class="addTravels fa fa-edit btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal11">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>
									</td>
								</tr>
							</c:forEach>
								
								<tr class="addTravel"  id="addTravel" style="text-align:center;">
								<td class="col-sm-3" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
									<a data-toggle="modal" data-target="#myModal1">添加</a>
					   			</td>
					   			<td class="col-sm-7" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
					   			</td>
					   			<td class="col-sm-2" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
								</td>
							</tr>					
							</tbody>
						</table> --%>
					
					<table class="table table-bordered">
							<tbody id="proFeatures1">
								<tr>
									<td class="col-sm-3 active">分类名称</td>
									<td class="col-sm-7 active">行程</td>
									<td class="col-sm-2 active">操作</td>
								</tr>
							<c:forEach var="travelsss" items="${travelsss}" varStatus="sta">
								<tr class="selectColor1"  id="selectColor1_${sta.index}" style="text-align:center;">
									<td class="col-sm-3" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
									<input id="${sta.index}" class="colorid" name="list[${sta.index}].classname" type="hidden" value="${travelsss.classname}"/>${travelsss.classname}
					   				</td>
					   				<td class="col-sm-7 newTravel" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
					   					<table class="table table-bordered"><tbody >
											<tr><td class="width-15 active">ID</td><td class="width-15 active">行程详情</td><td class="width-15 active">操作</td>
											</tr>
					   					<c:forEach var="traveldetails" items="${travelsss.travel}" varStatus="vsta">
												<tr class="selectColor11"  id="selectColor11_${vsta.index}" style="text-align:center;">
												<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
												<input class="colorid" name="list[${sta.index}].travelid"  type="hidden" value="${traveldetails.id}" />${traveldetails.id}
												</td>
												<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${traveldetails.name}
												</td>
												<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
												<button type="button" class="removetravel fa fa-trash btn btn-danger btn-xs">删除</button>
												</td></tr>
										</c:forEach>
											</tbody>
											</table>
					   				</td>
					   				<td class="col-sm-2" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
					   				<button type="button" id="addTravels" class="addTravels fa fa-edit btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal11">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>
									</td>
								</tr>
							</c:forEach>
								
								<tr class="addTravel"  id="addTravel" style="text-align:center;">
								<td class="col-sm-3" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
									<a data-toggle="modal" data-target="#myModal1">添加</a>
					   			</td>
					   			<td class="col-sm-7" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
					   			</td>
					   			<td class="col-sm-2" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
								</td>
							</tr>					
							</tbody>
						</table>
					</div>
				</div>
						<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;">当地参团</label>
					<div class="col-sm-10">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" >当地参团展示</label>
					<div class="col-sm-10">
					<input id="u3652_input" type="checkbox" value="1" name="isOffered" <c:if test='${comCity.isOffered == "1"}'>checked</c:if>>启用
					</div>
				</div>
				<div class="boxline">
					<div class="form-group">
					<label class="col-sm-2 control-label" >展示排序</label>
				<div class="col-sm-10">
					<form:select path="offeredSort"  class="form-control m-b">
					<form:option value="1" label="综合排序"/>
					<form:option value="2" label="时间排序"/>
					<form:option value="3" label="订单量排序"/>
				</form:select>
				</div>				
				</div>
				<div class="form-group">
				<label class="col-sm-2 control-label" >手动置顶</label>
				<div class="col-sm-10">
				<button type="button" id="addRoutes" class="addFeatures" data-toggle="modal" data-target="#myModal2">添加</button>
				</div>				
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label" ></label>
					<div class="col-sm-10">
					<table class="table table-bordered">
							<tbody id="proFeatures2">
								<tr>	
									<td class="width-15 active">ID</td>								
									<td class="width-15 active">行程</td>
									<td class="width-15 active">操作</td>
								</tr>		
							<c:forEach var="routes" items="${routes}" varStatus="sta">
								<tr class="selectColor2"  id="selectColor2_${sta.index}" style="text-align:center;">
									<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
										<input class="colorid" name="offerenTop"  type="hidden" value="${routes.id}"/>${routes.id}
						   			</td>
						   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${routes.title}
						   			</td>
						   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
										<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>
									</td>
								</tr>
							</c:forEach>	
							</tbody>
						</table>
					</div>
				</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;"></label>
					<div class="col-sm-10">
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;">景点</label>
					<div class="col-sm-10">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" >景点展示</label>
					<div class="col-sm-10">
					<input id="u3652_input" type="checkbox" value="1" name="isScenic" <c:if test='${comCity.isScenic == "1"}'>checked</c:if>>启用
					</div>
				</div>			
				<div class="boxline">
					<div class="form-group">
					<label class="col-sm-2 control-label" >展示排序</label>
				<div class="col-sm-10">
					<form:select path="scenicSort"  class="form-control m-b">
					<form:option value="1" label="综合排序"/>
					<form:option value="2" label="时间排序"/>
					<form:option value="3" label="订单量排序"/>
				</form:select>
				</div>				
				</div>
				<div class="form-group">
				<label class="col-sm-2 control-label" >手动置顶</label>
				<div class="col-sm-10">
				<button type="button" id="addScenics" class="addFeatures" data-toggle="modal" data-target="#myModal3">添加</button>
				</div>				
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label" ></label>
					<div class="col-sm-10">
					<table class="table table-bordered">
							<tbody id="proFeatures3">
								<tr>		
									<td class="width-15 active">ID</td>							
									<td class="width-15 active">景点</td>
									<td class="width-15 active">操作</td>
								</tr>	
							<c:forEach var="scenics" items="${scenics}" varStatus="sta">
							<tr class="selectColor3"  id="selectColor3_${sta.index}" style="text-align:center;">
								<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
									<input class="colorid" name="scenicTop"  type="hidden" value="${scenics.id}"/>${scenics.id}
					   			</td>
					   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${scenics.name}
					   			</td>
					   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
									<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>
								</td>
							</tr>
						</c:forEach>		
							</tbody>
						</table>
					</div>
				</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;"></label>
					<div class="col-sm-10">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;">当地玩家</label>
					<div class="col-sm-10">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" >当地玩家展示</label>
					<div class="col-sm-10">
					<input id="u3652_input" type="checkbox" value="1" name="isPlayer" <c:if test='${comCity.isPlayer == "1"}'>checked</c:if>>启用
					</div>
				</div>
				<div class="boxline">
					<div class="form-group">
					<label class="col-sm-2 control-label" >展示排序</label>
				<div class="col-sm-10">
					<form:select path="playerSort"  class="form-control m-b">
					<form:option value="1" label="综合排序"/>
					<form:option value="2" label="时间排序"/>
					<form:option value="3" label="订单量排序"/>
				</form:select>
				</div>				
				</div>
				<div class="form-group">
				<label class="col-sm-2 control-label" >手动置顶</label>
				<div class="col-sm-10">
				<button type="button" id="addGuides" class="addFeatures" data-toggle="modal" data-target="#myModal4">添加</button>
				</div>				
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label" ></label>
					<div class="col-sm-10">
					<table class="table table-bordered">
							<tbody id="proFeatures4">
								<tr>				
									<td class="width-15 active">ID</td>					
									<td class="width-15 active">当地玩家</td>
									<td class="width-15 active">操作</td>
								</tr>	
							<c:forEach var="guides" items="${guides}" varStatus="sta">
							<tr class="selectColor4"  id="selectColor4_${sta.index}" style="text-align:center;">
								<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
									<input class="colorid" name="playerTop"  type="hidden" value="${guides.id}"/>${guides.id}
					   			</td>
					   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${guides.realName}
					   			</td>
					   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
									<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>
								</td>
							</tr>
						</c:forEach>		
							</tbody>
						</table>
					</div>
				</div>
				
				</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加附近城市</h4>
	      </div>
	      <div class="modal-body">
					<div style="margin-bottom: 5px;">
						<label>城市名称：</label><input id="cityNames" style="height: 30px;" />
						<button id="searchCity" type="button" class="btn btn-primary" >查询</button>
					</div>

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>城市ID</td>
								<td>城市名称</td>
							</tr>
						</thead>
						<tbody id="citybody">
						</tbody>
					</table>
				</div>
	   
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="sure">确定</button>
	      </div>
	        
	    </div>
	  </div>
</div>
	<script type="text/javascript">
			var citynumber = ${fn:length(nearCitys)} +1;		//区分class的变量
			//点击确认把已选项目加入到附近城市table
	      		$("#sure").on("click",function(){
	      			var t = '';
					$("#myModal td input[type='checkbox']")
							.each(
									function(i) {
										if ($(this).is(":checked")) {
											var id = $(this).val();
											var cityName = $(this).next().val();
											$(this).attr("checked","checked");
											t += '<tr class="selectColor"  id="selectColor_';
					   						t+=citynumber;
					   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="nearCityNumber"  type="hidden" value="';
					   						t+=id;
					   						t+='" />';
					   						t += id;
					   						t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
					   						t += cityName;
											t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
											t += '<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>';
											t += '</td></tr>';
											citynumber += 1;
										}
									});
					$("#proFeatures").append(t);
					$("#myModal").modal('hide');
				})
	      
	      </script>
	      
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加</h4>
	      </div>
	      <div class="modal-body">
       			 <div class="form-group">
					<label class="col-sm-2 control-label" style="margin-top:6px;">分类名称</label>
					<div class="col-sm-10">
						<input id="featuresName1" class="form-control"/>
						<br>
					</div>
				</div>
				<div style="clear:both;"></div>
	      </div>
	   
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="sureClassName">确定</button>
	      </div>
	        
	    </div>
	  </div>
</div>

<div class="modal fade" id="myModal11" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加</h4>
	      </div>
	      <div class="modal-body">
       			 <div class="form-group">
					<label>行程：</label><input id="travelName" style="height: 30px;" />
					<input type="hidden" id="tableid" value="">
					<button id="searchTravel" type="button" class="btn btn-primary" >查询</button>
				</div>
				<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>ID</td>
								<td>名称</td>
							</tr>
						</thead>
						<tbody id="travelsBody">
						</tbody>
					</table>
				<div style="clear:both;"></div>
	      </div>
	   
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="sureTravels">确定</button>
	      </div>
	        
	    </div>
	  </div>
</div>
		<script type="text/javascript">
		var travel=${fn:length(comCityTravels)} +1;  //区分class的变量
		
		//点击确认把已填的分类名称添加到定制table
		$("#sureClassName").on("click",function(){
			travel++;//要放在字符拼接之前
			var travelName=$("#featuresName1").val();//分类名称
			var tt="";
			if(travelName==""){
				$("#myModal1").modal('hide');
			}else{
				tt+='<tr class="selectColor1"  id="selectColor1_'+travel+'" style="text-align:center;">';
				tt+='<td class="col-sm-3" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">';
				tt+='<input id="'+travel+'" class="colorid" name="list['+travel+'].classname" type="hidden" value="'+travelName+'"/>';
				tt+=travelName;
   				tt+='</td><td class="col-sm-7 newTravel" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;"></td>';
   				tt+='<td class="col-sm-2" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
   				tt+= '<button type="button" id="addTravels" function="addTravels()" class="addTravels fa fa-edit btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal11">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;';
				tt+='<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button></td></tr>';
				$("#addTravel").before(tt);
				
				$(".featuresidvalue1").val("");
				$("#myModal1").modal('hide');
			}
			
		})
		
					var travelnumberdetil = ${fn:length(traveldetails)} +1;
				//点击确认把已选项目加入到定制table
	      		$("#sureTravels").on("click",function(){
	      			var t = '';
	      			var id=$("#tableid").val();
	      			var index=$("#"+id).find("input").attr('id');	//获取定制主table tr的下标，用作嵌套table的下标
	      			if($("#"+id).find("table").length==0){
	      			t+='<table class="table table-bordered"><tbody >';
					t+='<tr><td class="width-15 active">ID</td><td class="width-15 active">行程详情</td><td class="width-15 active">操作</td>';
					t+='</tr>';
	      			}
	      				
					$("#myModal11 td input[type='checkbox']")
					.each(
							function(i) {
								if ($(this).is(":checked")) {
									var id = $(this).val();
									var travelName = $(this).next().val();
									$(this).attr("checked","checked");
									t += '<tr class="selectColor11"  id="selectColor11_';
			   						t+=travelnumberdetil;
			   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="list['+index+'].travelid"  type="hidden" value="';
			   						t+=id;
			   						t+='" />';
			   						t += id;
									t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
			   						t += travelName;
									t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
									t += '<button type="button" class="removetravel fa fa-trash btn btn-danger btn-xs">删除</button>';
									t += '</td></tr>';
									travelnumberdetil += 1;
								}
							});
					if($("#"+id).find("table").length==0){
					t+='</tbody></table>';
					$("#"+id).find(".newTravel").append(t);
					}else{
						$("#"+id).find("table").append(t);
					}
					$("#myModal11").modal('hide');
				})
	      
	      </script>
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加</h4>
	      </div>
	      <div class="modal-body">
       			 <div class="form-group">
					<label>标题：</label><input id="titles" style="height: 30px;" />
					<button id="searchRoutes" type="button" class="btn btn-primary" >查询</button>
				</div>
				<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>行程ID</td>
								<td>标题</td>
							</tr>
						</thead>
						<tbody id="routesBody">
						</tbody>
					</table>
				<div style="clear:both;"></div>
	      </div>
	   
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="sure2">确定</button>
	      </div>
	        
	    </div>
	  </div>
</div>
	<script type="text/javascript">
	var routenumber = ${fn:length(routes)} +1 ;		
		$("#sure2").on("click",function(){
			var t = '';
		$("#myModal2 td input[type='checkbox']")
				.each(
						function(i) {
							if ($(this).is(":checked")) {
								var id = $(this).val();
								var title = $(this).next().val();
								$(this).attr("checked","checked");
								t += '<tr class="selectColor2"  id="selectColor2_';
		   						t+=routenumber;
		   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="offerenTop"  type="hidden" value="';
		   						t+=id;
		   						t+='" />';
		   						t += id;
								t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
		   						t += title;
								t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
								t += '<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>';
								t += '</td></tr>';
								routenumber += 1;
							}
						});
		$("#proFeatures2").append(t);
		$("#myModal2").modal('hide');
	})
	      
	      </script>
<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加</h4>
	      </div>
	      <div class="modal-body">
       			 <div style="margin-bottom: 5px;">
						<label>景点：</label><input id="scenicName" style="height: 30px;" />
						<button id="searchScenics" type="button" class="btn btn-primary" >查询</button>
					</div>

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>景点ID</td>
								<td>景点</td>
							</tr>
						</thead>
						<tbody id="scenicsBody">
						</tbody>
					</table>
	      </div>
	   
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="sure3">确定</button>
	      </div>
	        
	    </div>
	  </div>
</div>
	<script type="text/javascript">
	var scenicnumber =  ${fn:length(scenics)} +1 ;		
	$("#sure3").on("click",function(){
		var t = '';
	$("#myModal3 td input[type='checkbox']")
			.each(
					function(i) {
						if ($(this).is(":checked")) {
							var id = $(this).val();
							var scenicName = $(this).next().val();
							$(this).attr("checked","checked");
							t += '<tr class="selectColor3"  id="selectColor3_';
	   						t+=scenicnumber;
	   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="scenicTop"  type="hidden" value="';
	   						t+=id;
	   						t+='" />';
	   						t += id;
							t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
	   						t += scenicName;
							t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
							t += '<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>';
							t += '</td></tr>';
							scenicnumber += 1;
						}
					});
	$("#proFeatures3").append(t);
	$("#myModal3").modal('hide');
	})
	      </script>	
<div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加</h4>
	      </div>
	      <div class="modal-body">
       			 <div style="margin-bottom: 5px;">
						<label>当地玩家：</label><input id="realName" style="height: 30px;" />
						<button id="searchGuides" type="button" class="btn btn-primary" >查询</button>
					</div>

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>玩家ID</td>
								<td>玩家</td>
							</tr>
						</thead>
						<tbody id="guidesBody">
						</tbody>
					</table>
	      </div>
	   
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="sure4">确定</button>
	      </div>
	        
	    </div>
	  </div>
</div>
	<script type="text/javascript">
	var guidenumber = ${fn:length(guides)} +1;		
	$("#sure4").on("click",function(){
		var t = '';
	$("#myModal4 td input[type='checkbox']")
			.each(
					function(i) {
						if ($(this).is(":checked")) {
							var id = $(this).val();
							var guides = $(this).next().val();
							$(this).attr("checked","checked");
							t += '<tr class="selectColor4"  id="selectColor4_';
	   						t+=guidenumber;
	   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="playerTop"  type="hidden" value="';
	   						t+=id;
	   						t+='" />';
	   						t += id;
							t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
	   						t += guides;
							t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
							t += '<button type="button" class="removeColor fa fa-trash btn btn-danger btn-xs">删除</button>';
							t += '</td></tr>';
							guidenumber += 1;
						}
					});
	$("#proFeatures4").append(t);
	$("#myModal4").modal('hide');
	})
	      
	      </script>	   	           	      
	      <script type="text/javascript">
	      /*  第一个确定删除 */ 
	  	$("#proFeatures").on("click","#edit",function(){
			//取到原来的值
			var id = $(this).parents("tr").attr("id");
			var fname = $(this).parent().prevAll(".fname").text();
			var fcontent = $(this).parent().prevAll(".fcontent").text();
			$(".featuresidvalue").val(id);
			$("#featuresName").val(fname);
			$("#featuresContent").val(fcontent);
		})
		$("#proFeatures").on("click",".delete",function(){
			$(this).parents("tr").remove();
		})
		 /*  第二个确定删除 */ 
		 	$("#proFeatures1").on("click","#edit",function(){
			//取到原来的值
			var id = $(this).parents("tr").attr("id");
			var fname = $(this).parent().prevAll(".fname").text();
			var fcontent = $(this).parent().prevAll(".fcontent").text();
			$(".featuresidvalue1").val(id);
			$("#featuresName1").val(fname);
			$("#featuresContent1").val(fcontent);
		})
		$("#proFeatures1").on("click",".delete",function(){
			$(this).parents("tr").remove();
		})
	   /*  第三个确定删除 */ 
		 	$("#proFeatures2").on("click","#edit",function(){
			//取到原来的值
			var id = $(this).parents("tr").attr("id");
			var fname = $(this).parent().prevAll(".fname").text();
			var fcontent = $(this).parent().prevAll(".fcontent").text();
			$(".featuresidvalue2").val(id);
			$("#featuresName2").val(fname);
			$("#featuresContent2").val(fcontent);
		})
		$("#proFeatures2").on("click",".delete",function(){
			$(this).parents("tr").remove();
		})
		  /*  第四个确定删除 */ 
		 	$("#proFeatures3").on("click","#edit",function(){
			//取到原来的值
			var id = $(this).parents("tr").attr("id");
			var fname = $(this).parent().prevAll(".fname").text();
			var fcontent = $(this).parent().prevAll(".fcontent").text();
			$(".featuresidvalue3").val(id);
			$("#featuresName3").val(fname);
			$("#featuresContent3").val(fcontent);
		})
		$("#proFeatures3").on("click",".delete",function(){
			$(this).parents("tr").remove();
		})
			  /*  第五个确定删除 */ 
		 	$("#proFeatures4").on("click","#edit",function(){
			//取到原来的值
			var id = $(this).parents("tr").attr("id");
			var fname = $(this).parent().prevAll(".fname").text();
			var fcontent = $(this).parent().prevAll(".fcontent").text();
			$(".featuresidvalue4").val(id);
			$("#featuresName4").val(fname);
			$("#featuresContent4").val(fcontent);
		})
		$("#proFeatures4").on("click",".delete",function(){
			$(this).parents("tr").remove();
		})
		</script>
	      
	         <div class="form-group">
					<label class="col-sm-2 control-label" style="font-weight:900;"></label>
					<div class="col-sm-10">
					</div>
				</div>
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