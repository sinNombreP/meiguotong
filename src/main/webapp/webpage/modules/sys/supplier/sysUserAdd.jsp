<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统账号管理管理</title>
	<meta name="decorator" content="ani"/>
	
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>


	 <script type="text/javascript" src="${ctxStatic}/area/city-picker.data.js"></script>
	<script type="text/javascript" src="${ctxStatic}/area/city-picker.js?version=164"></script>
	<link href="${ctxStatic}/area/city-picker.css" rel="stylesheet" type="text/css" />
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
	
	 function changIdCardFrontImg5(target,e){   
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
		                $("#idCardFrontImg5").attr("src",e.target.result);    //显示加载图片
		            }    
		        }    
		    }
	

		$(document).ready(function() {
			$("#no").focus();
			validateForm = $("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/sysUser/checkName?oldName=" + encodeURIComponent("${sysUser.loginName}")},//设置了远程验证，在初始化时必须预先调用一次。
					content: "required"
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				ignore: "",
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
	</script>
<style type="text/css">
.dept_select{min-width: 200px;}
button{
	padding: 4px 10px;
	border-radius: 3px;
	border: 0;
	background: #444;
	color: #fff;
}
</style>
</head>
<body class="bg-white">
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-body">
		<sys:message content="${message}"/>
		<form:form id="inputForm" modelAttribute="sysUser" action="${ctx}/sys/sysUser/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<input type="hidden" name="userType" value="2"> 
		<div class="panel-heading">
		<h3>代理商信息
		</h3>
		</div>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">登录名：</label></td>
					<td class="width-35">
						<form:input path="loginName" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="active"><label class="pull-right"><c:if test="${empty sysUser.id}"><font color="red">*</font></c:if>密码:</label></td>
			         <td><input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control ${empty sysUser.id?'required':''}"/>
						<c:if test="${not empty sysUser.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if></td>
			         <td class="active"><label class="pull-right"><c:if test="${empty sysUser.id}"><font color="red">*</font></c:if>确认密码:</label></td>
			         <td><input id="confirmNewPassword" name="confirmNewPassword" type="password"  class="form-control ${empty sysUser.id?'required':''}" value="" maxlength="50" minlength="3" equalTo="#newPassword"/></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">联系电话：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否可登录：</label></td>
					<td class="width-35">
						<form:select path="loginFlag"  class="form-control m-b">
						<form:option value="1" label="正常"/>
						<form:option value="0" label="屏蔽"/>
					</form:select>
					</td>
				</tr>
				<tr >
					<td class="width-15 active"><label class="pull-right">用户头像：</label></td>
					<td class="width-35">
						<img alt="暂无图片" id="idCardFrontImg5" src="${sysUser.photo}" height="100px" width="100px"/>    
										<input style="width:300px"  accept="image/jpg, image/png, image/gif, image/jpeg" onchange="changIdCardFrontImg5(this,event)" 
										id="idCardFrontFile5"	name="idCardFrontFile5"  type="file"/>
					</td>
					<td class="width-15 active"><label class="pull-right">地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div>
		<div class="col-xs-12 col-sm-6 col-md-4">
			
		</div>
		<div class="col-xs-12 col-sm-6 col-md-4">
			<a href="${ctx}/sys/sysUser" class="list" title="返回"><i class="btn btn-success btn-lg">返回</i> </a>
			<button class="btn btn-primary btn-lg " type="submit">提 交</button>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-4">
			
		</div>
	</div>
	</form:form>
</div>
</div>
</div>
</body>
</html>