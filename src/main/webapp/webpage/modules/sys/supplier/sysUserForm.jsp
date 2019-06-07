<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统账号管理管理</title>
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
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">代理商信息
		<div style="float: right;" >
				<a  href="${ctx}/sys/sysUser/supplierList" class="list" title="返回"><i class="btn btn-primary">返回</i> </a>
		</div>
		</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
		<form:form id="inputForm" modelAttribute="sysUser" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<input type="hidden" name="userType" value="2"> 
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">登录名：</label></td>
					<td class="width-35">
						${sysUser.loginName}
					</td>
					<td class="width-15 active"><label class="pull-right">用户头像：</label></td>
					<td class="width-35">
						<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<div style="height: 60px;width: 60px">
							<img alt="image"  class="img-responsive" src="${sysUser.photo}" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">姓名：</label></td>
					<td class="width-35">
						${sysUser.name}
					</td>
					<td class="width-15 active"><label class="pull-right">联系电话：</label></td>
					<td class="width-35">
						${sysUser.mobile}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">详细地址：</label></td>
					<td class="width-35">
						${sysUser.address}
					</td>
					<td class="width-15 active"><label class="pull-right">是否可登录：</label></td>
					<td class="width-35">
						<c:if test="${sysUser.loginFlag==1}">
							正常
						</c:if>
						<c:if test="${sysUser.loginFlag==0}">
							屏蔽
						</c:if>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
	</div>
</div>
</div>
</body>
</html>