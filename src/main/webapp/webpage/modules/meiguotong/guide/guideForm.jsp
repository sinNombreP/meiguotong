<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>导游表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<style>
 		.white{
              border:solid 1px #000;
              border-top-left-radius:10px;
              border-top-right-radius:10px;
              border-bottom:none;
              padding:15px 15px;
            
          }
		.tableBox{
               height:50px;
               margin-left:250px;
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
		var btnCount_b=0; 
		 
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
		              			xhr.open("POST", "${ctx}/meiguotong/guide/guide/uploadFile");
		              			xhr.send(fd);
		            	  }    
		         	}  
		        });
			
			var ue = UE.getEditor('content');//获得编辑器
		    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		    UE.Editor.prototype.getActionUrl = function(action) {
		         if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
		            return '${ctx}/sys/meiguotong/guide/uploadIMG'; //远程图片上传controller
		        } else {
		            return this._bkGetActionUrl.call(this, action);
		        }
		    }
		    
		    $('#birthday').datetimepicker({
				 format: "YYYY-MM-DD"
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
         if(size>20000){  
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
	
    <script type="text/javascript">
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
	function Img(target){
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
         if(size>20000){  
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
	  function changMemberInformationImg(target,e){   
	    	//判断图片格式、大小
		if(!Img(target)){
		    		return;
		    	}   
		        for (var i = 0; i < e.target.files.length; i++) {    
		            var file = e.target.files.item(i);    
		            //实例化FileReader API    
		            var freader = new FileReader();    
		            freader.readAsDataURL(file);    
		            freader.onload = function(e) {    
		                $("#idCardFrontImg1").attr("src",e.target.result);    //显示加载图片
		            }    
		        }    
		    }
	  </script>
 	<script type="text/javascript">
	function chgtt(d1){
		var NowFrame;
		if(Number(d1)){
		NowFrame=d1;
		}
		for(var i=1;i<=2;i++){
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
				<a class="panelButton" href="${ctx}/meiguotong/guide/guide"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="guide"  enctype="multipart/form-data" action="${ctx}/meiguotong/guide/guide/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="tableBox">
		<tr>
         <td id="bg1"><a class="white" onclick="chgtt(1);">详细信息</a></td>
         <td id="bg2"><a class="white" onclick="chgtt(2);">评论</a></td>
        </tr>
		 <hr style="color:#000">
		</div>
		<div id="book1" style="display:block;">	
		
		 <div class="form-group">
					<label class="col-sm-2 control-label" >会员头像:</label>
					<div class="col-sm-5" >
 					<img alt="暂无图片" id="idCardFrontImg1" src="${memberInformation.photo}" height="100px" width="100px"/>
 					<input type="hidden" name="photo">   
					<input style="width:300px"  accept="image/jpg, image/png, image/gif, image/jpeg" onchange="changMemberInformationImg(this,event)" 
					id="idCardFrontFile1" name="imageFrontFile1" htmlEscape="false" type="file"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机号码：</label>
					<div class="col-sm-5">
						<input type="text" name="mobile" value="${member.mobile}" class="form-control ">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">证件类型：</label>
					<div class="col-sm-5" class="form-control ">
					
				<form:select path="codeType" class="form-control m-b" style="width:30%;display:inline;">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="身份证"/>
					<form:option value="2" label="护照"/>
					<form:option value="3" label="本地ID"/>
				</form:select>
				<form:input path="code" htmlEscape="false" class="form-control" style="width:69%;display:inline;"/>
				
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电子邮箱：</label>
					<div class="col-sm-5">
						<input type="text" name="email" value="${member.email}" class="form-control ">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">擅长属性管理：</label>
					<div class="col-sm-10">
					 <table>
						<c:if test="${comTagList!=null}">						
						<c:forEach items="${comTagList}" var="comTagList">
						<tr style="height:30px">
						<td style="width:155px"><input type="text" name="content" value="${comTagList.content}"></td>
						</tr>						
						</c:forEach>
						</c:if>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别：</label>
					<div class="col-sm-5">
						<input  type="text" name="sexName" value="${guide.sexName}" class="form-control "> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">真实姓名：</label>
					<div class="col-sm-5">
						<input  type="text" name="realName" value="${memberInformation.realName}" class="form-control "> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">生日：</label>
					<div class="col-sm-5">
						<div class='input-group form_datetime' id='birthday'>
                 		 <input type='text'  name="birthday" class="form-control required"  value="<fmt:formatDate value="${guide.birthday}" pattern="yyyy-MM-dd"/>"/>
			           	 <span class="input-group-addon">
			            	  <span class="glyphicon glyphicon-calendar"></span>
			            	</span>
			           	</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">地址地区：</label>
					<div class="col-sm-5">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">证件：</label>
					<div class="col-sm-10" style=" border:1px solid #cccccc;">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box_b">                                <!-- 多个多图class修改-->
	       					 <c:if test="${guide.photoUrl!=null}">
	       					 <c:set value="${fn:split(guide.photoUrl, ',')}" var="photoUrl"></c:set>
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
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">抽成比例：</label>
					<div class="col-sm-5">
						<input type="text" name="discount" max="100" min="0" value="${guide1.discount}" class="form-control number">
					</div>
					<label class="control-label">%</label>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">简介：</label>
					<div class="col-sm-10">
						<form:textarea path="introduction" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详情管理：</label>
					<div class="col-sm-10">
						<textarea   id="content" name="deltails"  style="width: 100%;height:150px">${guide.deltails}</textarea>
					</div>
				</div>
		
		</div>
		<div id="book2" style="display:none;">
				<iframe id="frame_content" src="${ctx}/meiguotong/comcomment/comComment/goComment?type=8&typeId=${guide.id}" frameborder="0" height="500px"  width="100%"></iframe>
		</div>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>