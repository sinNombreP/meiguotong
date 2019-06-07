<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理管理</title>
	<meta name="decorator" content="ani"/>
		<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<script type="text/javascript">

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
			            return '${ctx}/meiguotong/comarticle/comArticle/uploadIMG'; //远程图片上传controller
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
				<a class="panelButton" href="${ctx}/meiguotong/comarticle/comArticle/aboutUs"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comArticle"  action="${ctx}/meiguotong/comarticle/comArticle/saveUs" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type" value="3"/>
		<sys:message content="${message}"/>
			<c:if test="${not empty comArticle.id}">
				<div class="form-group">
					<label class="col-sm-2 control-label" >选择语言</label>
					<div class="col-sm-8" >
						<form:select  path="languageId" disabled="true" class="form-control "  >
							<form:option value="" label="请选择"/>
							<c:forEach items="${languageList}" var="languageList">
								<form:option value="${languageList.id}" label="${languageList.content}"/>
							</c:forEach>
						</form:select>
					</div>
				</div>
			</c:if>
			<c:if test="${empty comArticle.id}">
				<div class="form-group">
					<label class="col-sm-2 control-label" >选择语言</label>
					<div class="col-sm-8" >
						<form:select  path="languageId" class="form-control "  >
							<form:option value="" label="请选择"/>
							<c:forEach items="${languageList}" var="languageList">
								<form:option value="${languageList.id}" label="${languageList.content}"/>
							</c:forEach>
						</form:select>
					</div>
				</div>
			</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label" >标题</label>
					<div class="col-sm-8" >
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
					
					
				<div class="form-group">
					<label class="col-sm-2 control-label" >内容详情</label>
					<div class="col-sm-8" >
						<textarea   id="content" name="content" style="width: 100%">${comArticle.content}</textarea>
					</div>
				</div>
				<c:if test="${comArticle.flag==1}">
					<div class="col-lg-3"></div>
			        <div class="col-lg-6">
			             <div class="form-group text-center">
			                 <div>
			                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
			                 </div>
			             </div>
			        </div>
				</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>