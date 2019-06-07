<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
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



	  function changIdCardFrontImg(target,e){   
	    	//判断图片格式、大小
		if(!checkImg(target)){
		    		return;
		    	}   
		        for (var i = 0; i < e.target.files.length; i++) {    
		            var file = e.target.files.item(i);    
		            //实例化FileReader API    
		            var freader = new FileReader();    
		            freader.readAsDataURL(file);    
		            freader.onload = function(e) {    
		                $("#idCardFrontImg").attr("src",e.target.result);    //显示加载图片
		            }    
		        }    
		    }
	
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
		
		
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
			
			
			  var ue = UE.getEditor('content');//获得去机器
		        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		        UE.Editor.prototype.getActionUrl = function(action) {
		             if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
		                return '${ctx}/sys/comArticle/uploadIMG'; //远程图片上传controller
		            } else {
		                return this._bkGetActionUrl.call(this, action);
		            }
		        }
		        
		        
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					 jp.post("${ctx}/sys/comArticle/save",$('#inputForm').serialize(),function(data){
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
                  			xhr.open("POST", "${ctx}/sys/comArticle/uploadFile");
                  			xhr.send(fd);
                	  }    
             	}  
            });
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm"   modelAttribute="comArticle" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">文章标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">分类名称：</label></td>
					<td class="width-35">
						<form:select path="categoryId"  class="form-control m-b">
							<form:option value="" label="--请选择以下--"/>
							<form:option value="1" label="新闻资讯"/>
							<form:option value="2" label="禁用"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">封面图片：</label></td>
					<td class="width-35">
						<%-- <img alt="暂无图片" id="idCardFrontImg" src="${comArticle.img}" height="100px",width="100px"/>    
						<input style="width:300px"  accept="image/jpg, image/png, image/gif, image/jpeg" onchange="changIdCardFrontImg(this,event)" 
						id="idCardFrontFile"	name="imageFrontFile" htmlEscape="false" type="file"
						/> --%>
						<div class="dv_pic_box">
	       					<div class="dv_pic_item">  
							<img alt="暂无图片" src="${comArticle.img}" height="80px" width="100px" class="img_style" id="img"/> 
						    <input type="hidden" name="img" value="${comArticle.img}" class="imgSrc"/>   
	           				<input  style="width:300px" type="file" accept="image/jpg, image/png, image/gif, image/jpeg" class="input_file_style" id="file"/>
						   </div>
	           			</div>
					</td>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" ><label class="pull-right">作者：</label></td>
					<td class="width-35" >
					<form:input path="authz" htmlEscape="false"    class="form-control "/>					</td>
		  		</tr>
				<tr>
					<td class="width-15 active" ><label class="pull-right">富 文本：</label></td>
					<td class="width-35" colspan="3">
						<textarea   id="content" name="content" rows="40" cols="80" style="width: 100%">${comArticle.content }</textarea>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>