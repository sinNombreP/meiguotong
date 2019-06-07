<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>景点表管理</title>
	<meta name="decorator" content="ani"/>
	<!-- 富文本编辑器 -->
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<style type="text/css">
	   .boxline{
	   margin-top:20px;
	   padding-top:30px;
	   padding-right:30px;
	   margin-left:280px;
	   height:200px;
	   width:50%;
	   border:solid 1px #000000;
	   border-radius: 30px;
	   }
    
         .bg1,.bg2,.bg3{
            text-align:center;
            border:solid 1px #000000;
            border-radius: 10px;
            width: 150px;
            height: 40px;
            font-family: 'PingFangSC-Regular', 'PingFang SC';
            font-weight: 400;
            font-style: normal;
             }
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
          .bg{
	color:#F00;
	background-color:#0C6;}
           .nobg{
	background-color:#0cf;}
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
	            t+='<input type="hidden" name="imgUrl" class="imgSrc" id="src_b_'+btnCount_b+'"/>';  
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
		              			xhr.open("POST", "${ctx}/meiguotong/scenicspot/scenicSpot/uploadFile");
		              			xhr.send(fd);
		            	  }    
		         	}  
		        });
			
			
			 var ue = UE.getEditor('reserveInfo');//获得编辑器
			    var ue1 = UE.getEditor('introduce');//获得编辑器
			    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
			    UE.Editor.prototype.getActionUrl = function(action) {
			         if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
			            return '${ctx}/meiguotong/scenicspot/scenicSpot/uploadIMG'; //远程图片上传controller
			        } else {
			            return this._bkGetActionUrl.call(this, action);
			        }
			    }
			    
			    $("#addTag").click(function(){
					$("#tagName").val("");
					var languageId = $("#languageId").val();
					if(!languageId){
						jp.error("请选择语言");
						return;
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
					var url="${ctx}/meiguotong/scenicspot/scenicSpot/getTag";
					var params={"languageId":languageId,"tagId":str2};
					jp.post(url,params,function(data){
						var list = data.body.list;
						var t=''; 
						if(list){
							for(var i in list){
								t += '<tr><td><input type="checkbox"';
								t+=' value="';
	                            t+=list[i].id;
								t+='"><input type="hidden"  value="';
								t+=list[i].id;
								t+='"><input type="hidden"  value="';
								t+=list[i].content;
								t+='"></td><td>';
								t += list[i].id;
								t += '</td><td>';
								t += list[i].content;
								t += '</td></tr>';
							}
						}
						$("#tbody").empty();
						$("#tbody").append(t);
					}); 
				});
				$("#search").click(function(){
					var languageId = $("#languageId").val();
					var name = $("#tagName").val();
					if(!languageId){
						jp.error("请选择语言");
						return;
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
					var url="${ctx}/meiguotong/scenicspot/scenicSpot/getTag";
					var params={"languageId":languageId,"tagId":str2,"name":name};
					jp.post(url,params,function(data){
						var list = data.body.list;
						var t=''; 
						if(list){
							for(var i in list){
								t += '<tr><td><input type="checkbox"';
								t+=' value="';
	                            t+=list[i].id;
								t+='"><input type="hidden"  value="';
								t+=list[i].id;
								t+='"><input type="hidden"  value="';
								t+=list[i].content;
								t+='"></td><td>';
								t += list[i].id;
								t += '</td><td>';
								t += list[i].content;
								t += '</td></tr>';
							}
						}
						$("#tbody").empty();
						$("#tbody").append(t);
					}); 
				});
				
				$("#tagTable").on("click", ".removeColor",
						function() {
							$(this).parents(".selectColor").remove();
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

	<script type="text/javascript">
		function chgtt(d1){
			var NowFrame;
			if(Number(d1)){
			NowFrame=d1;
			}
			for(var i=1;i<=3;i++){
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
				<a class="panelButton" href="${ctx}/meiguotong/scenicspot/scenicSpot/getscenicSopt?cityId=${scenicSpot.cityId}&countryId=${scenicSpot.countryId}"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="scenicSpot" action="${ctx}/meiguotong/scenicspot/scenicSpot/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="tableBox">
		<tr>
         <td id="bg1"><a class="white" onclick="chgtt(1);">通用</a></td>
         <td id="bg2"><a class="white" onclick="chgtt(2);">详情信息</a></td>
         <td id="bg3"><a class="white" onclick="chgtt(3);">评论</a></td>
        </tr>
		 <hr style="color:#000">
		</div>
			<div id="book1" style="display:block;">
			<iframe id="frame_content" src="${ctx}/meiguotong/scenicspotticket/scenicSpotTicket/list?scenicid=${scenicSpot.id}" frameborder="0" height="500px"  width="100%"></iframe>
			</div>
			<div id="book2" style="display:none;">
				<div class="form-group">
					<label class="col-sm-2 control-label">景点图片：</label>
					<div class="col-sm-10" style=" border:1px solid #cccccc;">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box_b">
	       					 <c:if test="${scenicSpot.imgUrl!=null}">
	       					 <c:set value="${fn:split(scenicSpot.imgUrl, ',')}" var="imgUrl"></c:set>
	       					 	<c:forEach var="imgUrl" items="${imgUrl}">
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
										<img src="${imgUrl}" class="img_style" id="img_b_<%=number_b%>"/>    <!-- 多个多图id修改-->
						            	<input type="hidden" name="imgUrl" value="${imgUrl}" class="imgSrc"/>   
	           							<input type="file" class="input_file_style_b" id="file_b_<%=number_b%>"/>       <!-- 多个多图class修改-->   <!-- 多个多图id修改-->
						            	<a id="btn_b_<%=number_b%>" onclick="deleteElement(this)" class="deleteBtn">删除</a>     <!--多个多图 id修改-->
						            	<% numberAdd_b();%>
						           </div>
	       					 	</c:forEach>
	       					 	<% numberZero_b();%>
	       					 </c:if>                                  <!-- 多个多图class修改-->	       					
	           				 </div>
	       					 <button type="button" class="btn_add_pic_b" >添加图片</button> <!--多个多图class修改-->
	   				 	</div> 
					</div>
				</div>
			<div class="form-group">
					<label class="col-sm-2 control-label">景点地址：</label>
					<div class="col-sm-10">
						<form:input path="address" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">选择语言：</label>
					<div class="col-sm-10">
						<form:hidden path="languageId"/>
						<form:input path="languageName" htmlEscape="false"  readonly="true"  class="form-control required"/>
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">景点名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标签属性:</label>
					<div class="col-sm-10">
						<button type="button" id="addTag" class="btn_add"
												data-toggle="modal" data-target="#myModal">选择标签</button>
						<table id="tagTable" style="margin-left: 20px; border: 1px solid black; margin-top: 10px;">
							<c:forEach items="${tagList}" var="tag" varStatus="sta">
									<tr class="selectColor"  id="selectColor_${sta.index}" style="text-align:center;">
										<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">
											<input class="colorid" name="labelAttrid"  type="hidden" value="${tag.id}" />
											${tag.id}
										</td>
										<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
											${tag.content}
										</td>
										<td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">
											<button type="button" class="removeColor">删除</button>
										</td>
									</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">建议游玩时长：</label>
					<div class="col-sm-10">
						<form:input path="playerTime" htmlEscape="false"    class="form-control"/>
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">最佳游玩季节：</label>
					<div class="col-sm-10">
						<form:input path="season" htmlEscape="false"    class="form-control"/>
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label class="col-sm-2 control-label">价格：</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<form:input path="price" htmlEscape="false"    class="form-control required"/>--%>
					<%--</div>--%>
				<%--</div>--%>
					<div class="form-group">
					<label class="col-sm-2 control-label">景点详情：</label>
					<div class="col-sm-10">
						<form:textarea path="content" htmlEscape="false" rows="4"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品经理推荐：</label>
					<div class="col-sm-10">
						<form:textarea path="recommend" htmlEscape="false" rows="4"    class="form-control"/>
					</div>
				</div>
				<div class="form-group">					
						<label class="col-sm-2 control-label">预定须知：</label>
					<div class="col-sm-10" >
						<textarea   id="reserveInfo" name="reserveInfo"  style="width: 100%;height:150px">${scenicSpot.reserveInfo}</textarea>
					</div>					
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">景点介绍：</label>
					<div class="col-sm-10" >
						<textarea   id="introduce" name="introduce"  style="width: 100%;height:150px">${scenicSpot.introduce }</textarea>
					</div>
					</div>
				<c:if test="${isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
				</div>
			</div>
			<div id="book3" style="display:none;">
				<iframe id="frame_content" src="${ctx}/meiguotong/comcomment/comComment/sceniceComment?typeId=${scenicSpot.id}" frameborder="0" height="500px"  width="100%"></iframe>
			</div>

				

		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>

<!-- 标签属性 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加标签</h4>
				</div>
				<div class="modal-body">
					<div style="margin-bottom: 5px;">
						<label>标签名称：</label><input id="tagName" style="height: 30px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<button id="search" type="button" class="addAttr">查询</button>
					</div>

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td>标签ID</td>
								<td>标签名称</td>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="sure">确定</button>
				</div>
			</div>
		</div>
	</div>
<!-- model框确定按钮-->
	<script type="text/javascript">
		var number = $('.selectColor').length + 1;
		$("#sure").on("click",function() {
							var t = '';
							$("#myModal input[type='checkbox']")
									.each(
											function(i) {
												if ($(this).is(":checked")) {
													var id = $(this).val();
													var content = $(this).next().next().val();
													$(this).attr("checked","checked");
													t += '<tr class="selectColor"  id="selectColor_';
							   						t+=number;
							   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="labelAttrid"  type="hidden" value="';
							   						t+=id;
							   						t+='" />';
													t += id;
													t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
													t += content;
													t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
													t += '<button type="button" class="removeColor">删除</button>';
													t += '</td></tr>';
													number += 1;
												}
											});
							$("#tagTable").append(t);
							$("#myModal").modal('hide');
						})
					</script>
</body>
</html>