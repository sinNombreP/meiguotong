<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="/webpage/include/treeview.jsp" %>
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
		
		$(document).ready(function(){
			$("#name").focus();
			
			validateForm= $("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")}//设置了远程验证，在初始化时必须预先调用一次。
				},
				messages: {
					name: {remote: "角色名已存在"}
				},
				submitHandler: function(form){
					jp.post("${ctx}/sys/role/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
            	  			
	                    }else{
            	  			jp.error(data.msg);
	                    }
	                    
	                    jp.close($topIndex);//关闭dialog
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
	<form:form id="inputForm" modelAttribute="role" autocomplete="off" action="${ctx}/sys/role/save" method="post" class="form-horizontal" >
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" name="office.id" value="6a642e140e40496a8d467c646b8e935e">
		<input type="hidden" name="enname" value="system">
		<input type="hidden" name="roleType" value="assignment">
		<input type="hidden" name="sysData" value="0">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active" class="active"><label class="pull-right"><font color="red">*</font>角色名称:</label></td>
		         <td class="width-35"><input id="oldName" name="oldName" type="hidden" value="${role.name}">
					<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		      	 <td  class="width-15 active" class="active"><label class="pull-right">是否可用</label></td>
		         <td class="width-35"><form:select path="useable" class="form-control ">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		      </tr>
		      <c:if test="${userType == 1}">
			      <tr>
			      	 <td  class="width-15 active" class="active"><label class="pull-right">角色类型</label></td>
			         <td class="width-35">
						<form:select path="type"  class="form-control ">
							<form:option value="1" label="后台角色"/>
							<%-- <form:option value="2" label="供应商角色"/> --%>
						</form:select>
			      </tr>
		     </c:if>
			</tbody>
			</table>
			<form:hidden path="menuIds"/>
	</form:form>
</body>
</html>