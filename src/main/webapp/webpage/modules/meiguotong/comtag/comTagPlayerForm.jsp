<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>各项管理管理</title>
<meta name="decorator" content="ani" />
<script type="text/javascript">
	var validateForm;
	var $table; // 父页面table表格id
	var $topIndex;//弹出窗口的 index
	function doSubmit(table, index) {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$table = table;
			$topIndex = index;
			jp.loading();
			$("#inputForm").submit();
			return true;
		}

		return false;
	}

	$(document)
			.ready(
					function() {
						validateForm = $("#inputForm")
								.validate(
										{
											submitHandler : function(form) {
												jp
														.post(
																"${ctx}/meiguotong/comtag/comTag/save",
																$('#inputForm')
																		.serialize(),
																function(data) {
																	if (data.success) {
																		$table
																				.bootstrapTable('refresh');
																		jp
																				.success(data.msg);
																		jp
																				.close($topIndex);//关闭dialog

																	} else {
																		jp
																				.error(data.msg);
																	}
																})
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
											}
										});
					});
</script>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="comTag"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="type" />
		<sys:message content="${message}" />
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">语言:</label></td>
					<c:choose>
						<c:when test="${comTag.id!=null && comTag.id!=''}">
							<td class="width-35"><form:select path="languageId"
									class="form-control required"  disabled="true">
									<form:option value="" label="请选择" />
									<c:forEach items="${comLanguageList}" var="languageList">
										<form:option value="${languageList.id}"
											label="${languageList.content}" />
									</c:forEach>
								</form:select></td>
						</c:when>
						<c:otherwise>
							<td class="width-35"><form:select path="languageId"
									class="form-control required">
									<form:option value="" label="请选择" />
									<c:forEach items="${comLanguageList}" var="languageList">
										<form:option value="${languageList.id}"
											label="${languageList.content}" />
									</c:forEach>
								</form:select></td>
						</c:otherwise>
					</c:choose>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">标签名称:</label></td>
					<td class="width-35"><input type="text" name="content"
						value="${comTag.content}" htmlEscape="false"
						class="form-control required" /></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>