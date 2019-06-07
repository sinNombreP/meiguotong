<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>常用联系人管理</title>
	<meta name="decorator" content="ani"/>
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
			
			
		});
		
		
		// 计算页面的实际高度，iframe自适应会用到
		  function calcPageHeight(doc) {
		      var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight)
		      var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight)
		      var height  = Math.max(cHeight, sHeight)
		      return height
		  }
		  //根据ID获取iframe对象
		  var ifr = window.parent.document.getElementById('main');
		  console.log(ifr);
		  ifr.onload = function() {
		  	  //解决打开高度太高的页面后再打开高度较小页面滚动条不收缩
		  	  ifr.style.height='0px';
		      var iDoc = ifr.contentDocument || ifr.document
		      var height = calcPageHeight(iDoc)
		      if(height < 350){
		      	height = 350;
		      }
		      ifr.style.height = height + 'px'
		  }
		
	</script>
</head>
<body>
<div class="wrapper wrapper-content" style="overflow: hidden;">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="memberContacts" action="${ctx}/sys/member/memberContacts/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
			<c:forEach  items="${memberContactsList}" var="memberContacts">
				<div class="form-group">
					<label class="col-sm-2 control-label">中文姓名：</label>
					<div class="col-sm-3">
						${memberContacts.chineseName}
					</div>
					<label class="col-sm-2 control-label">英文姓名：</label>
					<div class="col-sm-3">
							${memberContacts.englishName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">证件类型：</label>
					<div class="col-sm-3">
							<c:if test="${memberContacts.certType==1}">身份证</c:if>
							<c:if test="${memberContacts.certType==2}">护照</c:if>
							<c:if test="${memberContacts.certType==3}">本地ID</c:if>
					</div>
					<label class="col-sm-2 control-label">证件有效期：</label>
					<div class="col-sm-3">
							
						<fmt:formatDate value="${memberContacts.certValidDate}" pattern="yyyy-MM-dd"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出生年月：</label>
					<div class="col-sm-3">
						<fmt:formatDate value="${memberContacts.birthday}" pattern="yyyy-MM-dd"/>
					</div>
					<label class="col-sm-2 control-label">手机号码：</label>
					<div class="col-sm-3">
							${memberContacts.area}  ${memberContacts.mobile}
					</div>
				</div>
				
				
				<hr/>
				
				</c:forEach>
				
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>