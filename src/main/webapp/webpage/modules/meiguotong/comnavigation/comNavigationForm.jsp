<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>主导航管理</title>
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
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/meiguotong/comnavigation/comNavigation/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="comNavigation" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="type" value="1">
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
		   		<tr>
		   			<td class="width-15 active"><label class="pull-right">语言</label></td>
					<td class="width-35" >
						<c:choose>
							<c:when test="${comNavigation.id!=null && comNavigation.id!=''}">
								<form:select  path="languageId" class="form-control required" disabled="true">
									<form:option value="" label="请选择"/>
									<c:forEach items="${languageList}" var="languageList">
										<form:option value="${languageList.id}" label="${languageList.content}"/>
									</c:forEach>
								</form:select>
							</c:when>
							<c:otherwise>
								<form:select  path="languageId" class="form-control required">
									<form:option value="" label="请选择"/>
									<c:forEach items="${languageList}" var="languageList">
										<form:option value="${languageList.id}" label="${languageList.content}"/>
									</c:forEach>
								</form:select>
							</c:otherwise>
						</c:choose>
					</td>
		   		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">排序</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">导航名称</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">链接</label></td>
					<td class="width-35">
						<form:input path="link" htmlEscape="false"    class="form-control required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">链接说明</label></td>
					<td class="width-35">
						<form:input path="linkExplain" htmlEscape="false"    class="form-control "/>
					</td>
		  		</tr>

		 	</tbody>
		</table>
	</form:form>
</body>
</html>