<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>城市表管理</title>
<meta name="decorator" content="ani" />
<style type="text/css">
.boxline {
	 margin-top:20px;
	   padding-top:30px;
	   padding-right:30px;
	   margin-left:18%;
	   border:solid 1px #000000;
}

td {
	text-align:center;
    border:solid 1px #000000;
    width: 150px;
    height: 40px;
    font-family: 'PingFangSC-Regular', 'PingFang SC';
    font-weight: 400;
    font-style: normal;
}

.white {
	border: solid 1px #000;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
	border-bottom: none;
	padding: 15px 15px;
}

.tableBox {
	height: 50px;
	margin-left: 250px;
}

.bg {
	color: #F00;
	background-color: #0C6;
}

.nobg {
	background-color: #0cf;
}

.btn_add_pic_a, .btn_add_pic_b, .btn_add_pic_c, .btn_add_pic_d {
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

.deleteBtn {
	padding: 2px 10px;
	background: #E1E1E1;
}

.dv_pic_item_a, .dv_pic_item_b, .dv_pic_item_c, .dv_pic_item_d {
	width: 200px;
	height: 200px;
	margin: 5px 5px;
	float: left;
}

.input_file_style_a, .input_file_style_b, .input_file_style_c,
	.input_file_style_d {
	width: 200px;
	height: 20px;
}

.img_style {
	width: 200px;
	height: 140px;
	display: block;
	background-size: 100% auto;
}

.addAttr {
	height: 30px;
	border-radius: 6px;
	outline: none;
	border: none;
	background-color: #00BCD4;
	color: #fff;
	cursor: pointer;
}

.remove {
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
			$("input").attr("disabled","disabled");
			$("textarea").attr("disabled","disabled");
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
		function back(provinceId){
			  window.location = "${ctx}/meiguotong/comcity/comCity/list?provinceId=" + provinceId;
		  }	

	</script>
<script type="text/javascript">
	function chgtt(d1){
		var NowFrame;
		if(Number(d1)){
		NowFrame=d1;
		}
		for(var i=1;i<=4;i++){
			if(i==NowFrame){
				document.getElementById("book"+NowFrame).style.display="block";
			}else{
				document.getElementById("book"+i).style.display="none";
			}
		}
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
							<a class="panel-title" style="cursor: pointer;"
								onclick="back(${provinceId})">返回</a>
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="inputForm" modelAttribute="comCity"
							enctype="multipart/form-data"
							action="${ctx}/meiguotong/comcity/comCity/save" method="post"
							class="form-horizontal">
							<form:hidden path="id" />
							<sys:message content="${message}" />
							<div class="tableBox">
								<tr>
									<td id="bg1"><a class="white" onclick="chgtt(1);">通用</a></td>
									<td id="bg2"><a class="white" onclick="chgtt(2);">详情信息</a></td>
									<td id="bg3"><a class="white" onclick="chgtt(3);">攻略</a></td>
									<td id="bg4"><a class="white" onclick="chgtt(4);">评论</a></td>
								</tr>
								<hr style="color: #000">
							</div>

							<div id="book1" style="display: block;">

								<div class="form-group">
									<label class="col-sm-2 control-label">城市图片</label>
									<div class="col-sm-10" style="border: 1px solid #cccccc;">
										<div class="dv_info_box">
											<div class="dv_pic_box_b">
												<!-- 多个多图class修改-->
												<c:if test="${comCity.photoUrl!=null}">
													<c:set value="${fn:split(comCity.photoUrl, ',')}"
														var="photoUrl"></c:set>
													<c:forEach var="photoUrl" items="${photoUrl}">
														<div class="dv_pic_item_b">
															<!-- 多个多图class修改-->
															<%!char number_b = 'a';
	synchronized void numberAdd_b() {
		number_b++;
	}
	synchronized void numberZero_b() {
		number_b = 'a';
	}%>
															<img src="${photoUrl}" class="img_style"
																id="img_b_<%=number_b%>" />
															<!-- 多个多图id修改-->
															<input type="hidden" name="photoUrl" value="${photoUrl}"
																class="imgSrc" /> 
																<%-- <input type="file"
																class="input_file_style_b" id="file_b_<%=number_b%>" />
															<!-- 多个多图class修改-->
															<!-- 多个多图id修改-->
															<a id="btn_b_<%=number_b%>" onclick="deleteElement(this)"
																class="deleteBtn">删除</a> --%>
															<!--多个多图 id修改-->
															<%
																numberAdd_b();
															%>
														</div>
													</c:forEach>
													<%
														numberZero_b();
													%>
												</c:if>
											</div>
											<!-- <button type="button" class="btn_add_pic_b">添加图片</button> -->
											<!--多个多图class修改-->
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">城市视频:</label>
									<div class="col-sm-10">
										<div class="dv_info_box">  
					       					 <div class="dv_pic_box">	
					       					 		<div class="dv_pic_item">  
													<video controls="controls" src="${comCity.videoUrl}" height="80px" width="100px" class="img_style"></video>
										            	<!-- <input type="hidden" name="videoUrl" value="" class="imgSrc"/>   
					           							<input  style="width:300px" type="file" 
					           							class="input_file_style" id="file1"/>  -->	
										           </div>
					           				 </div>
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
										<label class="col-sm-2 control-label">附近城市:</label>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<table class="table table-bordered">
												<tbody id="proFeatures">
													<tr>
														<td class="width-15 active">城市ID</td>
														<td class="width-15 active">城市</td>
													</tr>
													<c:forEach var="nearCitys" items="${nearCitys}">
														<tr class="selectColor"  id="selectColor_${nearCitys.id}" style="text-align:center;">
															<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
																<input class="colorid" name="nearCityNumber"  type="hidden" value="${nearCitys.id}"/>${nearCitys.id}
												   			</td>
												   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${nearCitys.cityName}
												   			</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;">租车</label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">使用汽车模块:</label>
										<div class="col-sm-10">
											<input id="u3652_input" type="checkbox" value="checkbox" <c:if test='${comCity.isCar == "1"}'>checked</c:if>
												name="isCar">启用
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<table>
												<tr>
													<td class="box">包车租车</td>
													<td class="box">短程接送</td>
													<td class="box">接送机</td>
												</tr>
											</table>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;">旅游定制</label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">旅游定制分类</label>
										<div class="col-sm-10">
											<input id="u3652_input" type="checkbox" value="checkbox" <c:if test='${comCity.isTourism == "1"}'>checked</c:if>
												name="isTourism">启用
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<table>
												<tr>
													<td>分类名称</td>
													<td>小行程ID</td>
													<td>操作</td>
												</tr>
											</table>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;">当地参团</label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">当地参团展示</label>
										<div class="col-sm-10">
											<input id="u3652_input" type="checkbox" value="checkbox" <c:if test='${comCity.isOffered == "1"}'>checked</c:if>
												name="isOffered">启用
										</div>
									</div>
									<div class="boxline">
										<div class="form-group">
											<label class="col-sm-2 control-label">展示排序：</label>
											<c:if test='${comCity.offeredSort == "1"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">综合排序</label></c:if>
											<c:if test='${comCity.offeredSort == "2"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">时间排序</label></c:if>
											<c:if test='${comCity.offeredSort == "3"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">订单量排序</label></c:if>
										</div>
										<div class="form-group">
											<div class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-10">
													<table class="table table-bordered">
														<tbody id="proFeatures2">
															<tr>
																<td class="width-15 active">行程ID</td>
																<td class="width-15 active">行程</td>
															</tr>
															<c:forEach var="routes" items="${routes}">
																<tr class="selectColor2"  id="selectColor_${routes.id}" style="text-align:center;">
																	<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
																		<input class="colorid" name="offerenTop"  type="hidden" value="${routes.id}"/>${routes.id}
														   			</td>
														   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${routes.title}
														   			</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;"></label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;">景点</label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">景点展示</label>
										<div class="col-sm-10">
											<input id="u3652_input" type="checkbox" value="checkbox" <c:if test='${comCity.isScenic == "1"}'>checked</c:if>
												name="isScenic">启用
										</div>
									</div>
									<div class="boxline">
										<div class="form-group">
											<label class="col-sm-2 control-label">展示排序：</label>
											<c:if test='${comCity.scenicSort == "1"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">综合排序</label></c:if>
											<c:if test='${comCity.scenicSort == "2"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">时间排序</label></c:if>
											<c:if test='${comCity.scenicSort == "3"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">订单量排序</label></c:if>
										</div>
										<div class="form-group">
											<div class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-10">
													<table class="table table-bordered">
														<tbody id="proFeatures3">
															<tr>
																<td class="width-15 active">景点ID</td>
																<td class="width-15 active">景点</td>
															</tr>
															<c:forEach var="scenics" items="${scenics}">
																<tr class="selectColor3"  id="selectColor_${scenics.id}" style="text-align:center;">
																	<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
																		<input class="colorid" name="scenicTop"  type="hidden" value="${scenics.id}"/>${scenics.id}
														   			</td>
														   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${scenics.name}
														   			</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;"></label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;">当地玩家</label>
										<div class="col-sm-10"></div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">当地玩家展示</label>
										<div class="col-sm-10">
											<input id="u3652_input" type="checkbox" value="checkbox" <c:if test='${comCity.isPlayer == "1"}'>checked</c:if>
												name="isPlayer">启用
										</div>
									</div>
									<div class="boxline">
										<div class="form-group">
											<label class="col-sm-2 control-label">展示排序：</label>
											<c:if test='${comCity.playerSort == "1"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">综合排序</label></c:if>
											<c:if test='${comCity.playerSort == "2"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">时间排序</label></c:if>
											<c:if test='${comCity.playerSort == "3"}'><label class="col-sm-2 control-label" style="text-align:left;font-weight:400;">订单量排序</label></c:if>
										</div>
										<div class="form-group">
											<div class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-10">
													<table class="table table-bordered">
														<tbody id="proFeatures4">
															<tr>
																<td class="width-15 active">玩家ID</td>
																<td class="width-15 active">玩家</td>
															</tr>
															<c:forEach var="guides" items="${guides}">
																<tr class="selectColor4"  id="selectColor_${guides.id}" style="text-align:center;">
																	<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
																		<input class="colorid" name="playerTop"  type="hidden" value="${guides.id}"/>${guides.id}
														   			</td>
														   			<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">${guides.realName}
														   			</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>

									
									

									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="font-weight: 900;"></label>
										<div class="col-sm-10"></div>
									</div>
									
								</div>




							</div>
							<div id="book2" style="display: none;">
								<div class="form-group">
									<label class="col-sm-2 control-label">城市名称</label>
									<div class="col-sm-10">
										<form:input path="cityName" htmlEscape="false"
											class="form-control required" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">备注名称</label>
									<div class="col-sm-10">
										<form:input path="cityRemark" htmlEscape="false"
											class="form-control " />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">城市详情</label>
									<div class="col-sm-10">
										<form:textarea path="cityDetails" htmlEscape="false" rows="4"
											class="form-control " />
									</div>
								</div>
								
							</div>

							<div id="book3" style="display: none;">
								<iframe id="frame_content"
									src="${ctx}/meiguotong/citystrategy/cityStrategy?cityId=${comCity.id}"
									frameborder="0" height="500px" width="100%"></iframe>
							</div>
							<div id="book4" style="display: none">
								<iframe id="frame_content"
									src="${ctx}/meiguotong/comcomment/comComment/goComment?type=14&typeId=${comCity.id}"
									frameborder="0" height="500px" width="100%"></iframe>
							</div>

						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>