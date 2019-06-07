<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>景点门票表管理</title>
	<meta name="decorator" content="ani"/>
<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  if($("#price").val()-$("#marketPrice").val()>0){
				  jp.error("门票价不能大于市场价");
				  return;
			  }
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/meiguotong/scenicspotticket/scenicSpotTicket/save",$('#inputForm').serialize(),function(data){
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
			$('#orderPush').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
			});	
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="scenicSpotTicket" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="scenicid" value="${scenicid}">
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">门票名称:</label></td>
					<td class="width-35">
         	<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>					
				</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right">提前天数:</label></td>
					<td class="width-35">
         	<form:input path="rule" htmlEscape="false"    class="form-control digits required"/>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">市场价：</label></td>
					<td class="width-35">
                 	<form:input path="marketPrice" htmlEscape="false"    class="form-control double required"/>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">门票价：</label></td>
					<td class="width-35">
                 	<form:input path="price" htmlEscape="false"    class="form-control double required"/>
					</td>					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">报价有效期：</label></td>
					<td class="width-35" >
					<div class='input-group form_datetime' id='orderPush'>
                 	 <input type='text'  name="validityDate" class="form-control required"  value="<fmt:formatDate value="${scenicSpotTicket.validityDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			            <span class="input-group-addon">
			              <span class="glyphicon glyphicon-calendar"></span>
			            </span>
			           </div>	
					</td>			
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">状态：</label></td>
					<td class="width-35">
                 		<form:select path="status"  class="form-control m-b ">
							<form:option value="1"  label="启用"/>
							<form:option value="2"  label="禁用"/>
						</form:select>
					</td>					
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>