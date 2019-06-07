<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮轮管理表管理</title>
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
					jp.post("${ctx}/meiguotong/liner/liner/save",$('#inputForm').serialize(),function(data){
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
			$("#languageId").change(function(){
				var languageId = $(this).val();
				var params={"languageId":languageId};
				var url="${ctx}/meiguotong/liner/liner/getCompany";
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t='';
					if(list){
						for(var i in list){
							t+='<option value="';
							t+=list[i].id;
							t+='" class="removeCompany" >';
							t+=list[i].name;
							t+='</option>';
						}
					}
					$(".removeCompany").remove();
					$("#company").append(t);
				});
			});
			
			$('#startDate').datetimepicker({
				 format: "YYYY-MM-DD"
			});
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="liner" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
		  		 <tr>
					<td class="width-15 active"><label class="pull-right">语言</label></td>
					<td class="width-35">
					<c:if test="${empty liner.id}">
						<form:select  path="languageId" class="form-control required">
							<form:option value="" label="请选择"/>
							<c:forEach items="${languageList}" var="language">
								<form:option value="${language.languageid}" label="${language.content}"/>
							</c:forEach>
						</form:select>
					</c:if>
					<c:if test="${not empty liner.id}">
						<form:select  path="languageId" disabled="true" class="form-control required">
							<form:option value="" label="请选择"/>
							<c:forEach items="${languageList}" var="language">
								<form:option value="${language.languageid}" label="${language.content}"/>
							</c:forEach>
						</form:select>
					</c:if>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">所属公司</label></td>
					<td class="width-35">
						<form:select  path="company" class="form-control required">
							<form:option value="" label="请选择"/>
							<c:if test="${not empty liner.id}">
									<c:forEach items="${companyList}" var="company">
										<form:option value="${company.id}" label="${company.name}"/>
									</c:forEach>
							</c:if>
						</form:select>
					</td>
		  		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">邮轮名称</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">长</label></td>
					<td class="width-35">
						<form:input path="length" htmlEscape="false"    class="form-control number required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">宽</label></td>
					<td class="width-35">
						<form:input path="width" htmlEscape="false"    class="form-control  number required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">高</label></td>
					<td class="width-35">
						<form:input path="heigth" htmlEscape="false"    class="form-control  number required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">排水量</label></td>
					<td class="width-35">
						<form:input path="weight" htmlEscape="false"    class="form-control  number required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">载客人数</label></td>
					<td class="width-35">
						<form:input path="passengersNum" htmlEscape="false"    class="form-control digits required "/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">首航时间</label></td>
					<td class="width-35">
					<div class='input-group form_datetime' id='startDate'>
                 	 <input type='text'  name="startDate" class="form-control required"  value="<fmt:formatDate value="${liner.startDate}" pattern="yyyy-MM-dd"/>"/>
			            <span class="input-group-addon">
			              <span class="glyphicon glyphicon-calendar"></span>
			            </span>
			           </div>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
                     <form:select  path="status" class="form-control required">
							<form:option value="3" label="启用"/>
							<form:option value="4" label="禁用"/>
						</form:select>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>