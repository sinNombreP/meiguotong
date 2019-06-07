<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>底部文字设置</title>
	<meta name="decorator" content="ani"/>
	
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
   
	<script type="text/javascript">
		var number = 1;
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
			$("#languageid").change(function(){
				var languageid = $(this).val();
				var params  = {"languageid":languageid};
				var url = "${ctx}/meiguotong/comprotocol/comProtocol/getComProtocol";
				jp.post(url,params,function(data){
					var comProtocol = data.body.comProtocol;
					if(comProtocol){
						$("#id").val(comProtocol.id);
						var content = "";
						if(comProtocol.footContent&&comProtocol.footContent!=undefined){
							content = comProtocol.footContent;
						}
						$(".removeContent").remove();
						var t='';
						t+='<textarea class="removeContent" id="content'
						t+=number
						t+='" name="footContent" rows="4" cols="80" style="width: 100%">';
						t+=content;
						t+='</textarea>';
						$(".contentArea").append(t);
						var ue = UE.getEditor('content'+number);//获得编辑器 
						number +=1;
					}else{
						$("#id").val("");
						$(".removeContent").remove();
						var t='';
						t+='<textarea class="removeContent" id="content'
						t+=number
						t+='" name="footContent" rows="4" cols="80" style="width: 100%">';
						t+='</textarea>';
						$(".contentArea").append(t);
						var ue = UE.getEditor('content'+number);//获得编辑器 
						number +=1;
					}
				})
			})
			var ue = UE.getEditor('content');//获得编辑器 
		    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		    UE.Editor.prototype.getActionUrl = function(action) {
		         if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
		            return '${ctx}/meiguotong/comprotocol/comProtocol/uploadIMG'; //远程图片上传controller
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
				底部文字设置
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comProtocol"  action="${ctx}/meiguotong/comprotocol/comProtocol/TextSave" method="post" class="form-horizontal">
       <form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="form-group">
					<label class="col-sm-2 control-label">选择语言</label>
					<div class="col-sm-8">
						<form:select  path="languageid" class="form-control ">
							<form:option value="" label="请选择"/>
							<c:forEach items="${comLanguageList}" var="languageList">
								<form:option value="${languageList.id}" label="${languageList.content}"/>
							</c:forEach>
						</form:select>
					</div>
				</div>					
				<div class="form-group">
					<label class="col-sm-2 control-label" >底部内容</label>
					<div class="col-sm-8 contentArea" >
						<textarea class="removeContent" id="content" name="footContent" rows="4" cols="80" style="width: 100%">${comProtocol.footContent}</textarea>
					</div>
				</div>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div id="SingleStyle">
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>