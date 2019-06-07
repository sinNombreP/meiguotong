<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>保险表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					if(!$("#price").val()){
						jp.error("保险价格不能为空！");
						return;
					}
					if(!/^[0-9,.]*$/.test($("#price").val().trim())){
						  jp.error("请输入正确的保险价格！");
						return;
					} 
					if(!$("#name").val()){
						jp.error("保险名称不能为空！");
						return;
					}
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
		
	    var ue = UE.getEditor('content');//获得去机器
	    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	    UE.Editor.prototype.getActionUrl = function(action) {
	         if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
	            return '${ctx}/sys/sysUser/uploadIMG'; //远程图片上传controller
	        } else {
	            return this._bkGetActionUrl.call(this, action);
	        }
	    }
		
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/meiguotong/insurance/insurance"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
				<ul class="nav nav-tabs"
					style="padding-left: 130px; padding-top: 20px;">
					<li class="active"><a data-toggle="tab" href="#tab-1">通用</a></li>
					<li class=""><a data-toggle="tab" href="#tab-2">详细信息</a></li>
				</ul>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="insurance" action="${ctx}/meiguotong/insurance/insurance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
					<div class="panel-body tab-content">
						<div class="tab-pane fade in  active" id="tab-1">
							<div class="form-group">
								<label class="col-sm-2 control-label">保险价格：</label>
								<div class="col-sm-10">
									<form:input path="price" htmlEscape="false"    class="form-control number required"/>
								</div>
							</div>
						</div>
						<div class="tab-pane fade" id="tab-2">
						
				<div class="form-group">
					<label class="col-sm-2 control-label">语言：</label>
					<div class="col-sm-2">
						<form:select path="languageId" class="form-control m-b required">
							<form:options items="${comLanguageList}" itemLabel="content"
								itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保险名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control  required"/>
					</div>
				</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">保险说明：</label>
									<div class="col-sm-10">
						<textarea   id="content" name="content" rows="55" cols="80" style="width: 100%;height:300px">${insurance.content }</textarea>
				
									</div>
								</div>
						</div>
					</div>
		<%-- <c:if test="${fns:hasPermission('meiguotong:product:insurance:edit') || isAdd}"> --%>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
<%-- 		</c:if> --%>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>