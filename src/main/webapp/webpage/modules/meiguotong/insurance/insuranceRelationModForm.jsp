<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>保险项目关联管理</title>
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
					jp.post("${ctx}/meiguotong/insurance/insuranceRelationMod/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="insuranceRelationMod" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="languageId"/>
		<form:hidden path="type"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<c:if test="${not empty insuranceList}">
					<tr>
						<td class="width-15 "></td>
						<td class="width-35">
							保险ID
						</td>
						<td class="width-35">
							保险名称
						</td>
					</tr>
					<c:forEach items="${insuranceList}" var="insurance">
						<tr>
							<td class="width-15 ">
								<input type="checkbox" <c:if test="${insurance.checkedFlag==1}">checked="checked"</c:if> name="insuranceId" value="${insurance.id}" />
							</td>
							<td class="width-35">
								${insurance.id}
							</td>
							<td class="width-35">
								${insurance.name}
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty insuranceList}">
					<tr>
						<td class="width-15 ">没有可添加的保险，请去添加保险！</td>
					</tr>
				</c:if>
				
		 	</tbody>
		</table>
	</form:form>
</body>
</html>