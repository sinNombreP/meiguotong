<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>后台账号管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
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

		$(document).ready(function() {			
			$("#no").focus();
			validateForm = $("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/sysUser/checkName?oldName=" + encodeURIComponent("${sysUser.loginName}")}//设置了远程验证，在初始化时必须预先调用一次。
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					jp.post("${ctx}/sys/sysUser/save",$('#inputForm').serialize(),function(data){
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
			
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="sysUser" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<input type="hidden" name="userType" value="1">
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">账号：</label></td>
					<td class="width-35"><input id="oldName" name="oldName" type="hidden" value="${sysUser.loginName}">
						<form:input path="loginName" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
			  	<tr>
			         <td class="active"><label class="pull-right"><c:if test="${empty sysUser.id}"><font color="red">*</font></c:if>密码:</label></td>
			         <td><input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control ${empty sysUser.id?'required':''}"/>
						<c:if test="${not empty sysUser.id}"><span class="help-inline" style="color:red;">若不修改密码，请留空。</span></c:if></td>
			         <td class="active"><label class="pull-right"><c:if test="${empty sysUser.id}"><font color="red">*</font></c:if>确认密码:</label></td>
			         <td><input id="confirmNewPassword" name="confirmNewPassword" type="password"  class="form-control ${empty sysUser.id?'required':''}" value="" maxlength="50" minlength="3" equalTo="#newPassword"/></td>
		      	</tr>
				<tr>
					 <td class="active"><label class="pull-right"><font color="red">*</font>用户角色:</label></td>
			         <td>
			         	<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks required"/>
			         	<label id="roleIdList-error" class="error" for="roleIdList"></label>
			         </td>
					<td class="width-15 active"><label class="pull-right">是否可登录：</label></td>
					<td class="width-35">
						<form:select path="loginFlag"  class="form-control m-b">
							<form:option value="1" label="正常"/>
							<form:option value="2" label="屏蔽"/>
						</form:select>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>