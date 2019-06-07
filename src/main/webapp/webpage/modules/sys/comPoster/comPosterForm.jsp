<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理管理</title>
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
			
			

		     var ue = UE.getEditor('content');//获得去机器
		        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		        UE.Editor.prototype.getActionUrl = function(action) {
		             if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
		                return '${ctx}/sys/comPoster/uploadIMG'; //远程图片上传controller
		            } else {
		                return this._bkGetActionUrl.call(this, action);
		            }
		        }
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/sys/comPoster"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comPoster" enctype="multipart/form-data" action="${ctx}/sys/comPoster/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
					<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">标题：</label>
					<div class="col-sm-2" style="position:relative; left: -77px; top: 2px;">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">链接：</label>
					<div class="col-sm-2" style="position:relative; left: -77px; top: 2px;">
						<form:input path="link" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">广告位置：</label>
					<div class="col-sm-2" style="position:relative; left: -77px; top: 2px;">
						<form:select path="positionType" class="form-control ">
							<form:option value="" label="--请选择以下--"/>
							<form:option value="1" label="首页"/>
							<form:option value="2" label="秒杀页"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">广告类型：</label>
					<div class="col-sm-2" style="position:relative; left: -77px; top: 2px;">
						<form:select path="type" class="form-control ">
							<form:option value="" label="--请选择以下--"/>
							<form:option value="1" label="链接"/>
							<form:option value="2" label="富文本"/>
							<form:option value="3" label="商品ID"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">商品ID：</label>
					<div class="col-sm-2" style="position:relative; left: -77px; top: 2px;">
						<form:input path="typeid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">富文本：</label>
					<div class="col-sm-8" style="position:relative; left: -77px; top: 2px;">
						<textarea   id="content" name="content" rows="40" cols="80" style="width: 100%">${comPoster.content }</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">轮播：</label>
					<div class="col-sm-2" style="position:relative; left: -77px; top: 2px;">
						<form:select path="carousel" class="form-control ">
							<form:option value="" label="--请选择以下--"/>
							<form:option value="1" label="1"/>
							<form:option value="2" label="2"/>
							<form:option value="3" label="3"/>
							<form:option value="4" label="4"/>
							<form:option value="5" label="5"/>
							<form:option value="6" label="6"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="position:relative; left: -77px; top: 2px;">图片：</label>
					<div class="col-sm-5"  style="position:relative; left: -77px; top: 2px;">
 					<img alt="暂无图片" id="idCardFrontImg" src="${comPoster.img}" height="100px",width="100px"/>    
					<input style="width:300px"  accept="image/jpg, image/png, image/gif, image/jpeg" onchange="changIdCardFrontImg(this,event)" 
					id="idCardFrontFile"	name="imageFrontFile" htmlEscape="false" type="file"
					/>
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