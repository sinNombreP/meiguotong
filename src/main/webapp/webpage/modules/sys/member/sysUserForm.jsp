<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/sysUser/checkName?oldName=" + encodeURIComponent("${sysUser.loginName}")},//设置了远程验证，在初始化时必须预先调用一次。
					content: "required"
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
				},
				ignore: "",
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
		
		var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
		function checkImg(target){
			var fileSize = 0;         
		    if (isIE && !target.files) {     
		      var filePath = target.value;     
		      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
		      var file = fileSystem.GetFile (filePath);     
		      fileSize = file.Size;    
		    } else {    
		     fileSize = target.files[0].size;     
		     }   
		     var size = fileSize / 1024;
		     if(size>2000){  
		      alert("图片不能大于2M");
		      target.value="";
		      return false
		     }
		     var name=target.value;
		     var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		     if(fileName !="jpg" && fileName !="gif" && fileName !="png"){
		         alert("请选择jpg、gif、png格式上传！");
		         target.value="";
		         return false
		     }
		    return true;
		}
		
	$(function(){
		$(".wrapper").on("change", ".input_file_style", function(e) {
			var target = $(this)[0];
			var img =$(this);
			if (!checkImg(target)) {
				return;
			}
			for (var i = 0; i < e.target.files.length; i++) {
				var file = e.target.files.item(i);
				//实例化FileReader API    
				var freader = new FileReader();
				freader.readAsDataURL(file);
				freader.onload = function(e) {
					var src = e.target.result;
					var fd = new FormData();
					fd.append("attach", file);
					var xhr = new XMLHttpRequest();
					xhr.addEventListener("load", function(evt) { 
          				var textJson = JSON.parse(evt.target.responseText);
          				if(textJson.success){
          					img.prev().val(textJson.body.filePath);
          					img.prev().prev().attr("src",src);
          				} 
          				jp.success(textJson.msg);
          			}, false);
					xhr.addEventListener("error", function(evt){
						jp.info("上传失败");
					}, false);
					xhr.addEventListener("abort", function(evt){
						jp.info("取消上传");
					}, false);
					xhr.open("POST", "${ctx}/sys/img/uploadFile");
					xhr.send(fd);
				}
			}
		})
	})
        //启用供应商类型
		function addRole(index ,event, roleid, type, userid){
            let discount = $(event).parent().prev().find(".form-control").last().val();
		    if(index && !discount){
                jp.error("请完善数据");
                return;
            }
		    console.log($(event).parent().prev().find(".form-control").last().val());

            jp.post("${ctx}/sys/member/sysUserType/addRole",{
                "roleid":roleid,
                "type":type,
                "userid":userid,
                "discount":discount,
            },function(data){
                if(data.success){
                    location.reload();
                }else{
                    jp.error(data.msg);
                }
            })
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
				<a class="panelButton" href="${ctx}/sys/sysUser/list2"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<c:if test="${not empty sysUser.id}">
		      <ul class="nav nav-tabs" style="padding-left:130px;padding-top:20px;">
				<li class="active"><a data-toggle="tab" href="#tab-1" >详细信息</a></li>
				<li class=""><a data-toggle="tab" href="#tab-2">退款说明</a> </li>
				<li class=""><a data-toggle="tab" href="#tab-3">子账号</a> </li>
            </ul>
            </c:if>
         <div class="tab-content">
		<div class="tab-pane fade in  active" id="tab-1">
			<form:form id="inputForm" modelAttribute="sysUser" action="${ctx}/sys/sysUser/save3"  enctype="multipart/form-data" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">供应商LOGO：</label>
					<div class="col-sm-5">
						<img  style="height: 140px;width: 200px;" src="${sysUser.companyLogo}"  />
						<input type="hidden"name="companyLogo" value="${sysUser.companyLogo}" /> 
						<input type="file" class="input_file_style" id="input_file"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">账号：</label>
					<div class="col-sm-5">
 						<form:input path="loginName" htmlEscape="false" class="form-control required" />			
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">密码：</label>
					<div class="col-sm-5">
 						<form:input path="newPassword" htmlEscape="false" type="password" class="form-control"/>	
 						<c:if test="${not empty sysUser.id}"><span class="help-inline" style="color:red;">若不修改密码，请留空。</span></c:if>
					</div>
				</div>
<%--				<div class="form-group">
					<label class="col-sm-2 control-label">角色：</label>
					<div class="col-sm-5">
 		<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks required"/>
			         	<label id="roleIdList-error" class="error" for="roleIdList"></label>		
					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司名称：</label>
					<div class="col-sm-5">
 						<form:input path="companyName" htmlEscape="false" class="form-control required"/>			
					</div>
				</div>
				<c:if test="${not empty sysUser.id}">
				<div class="form-group">
					<label class="col-sm-2 control-label">子账号：</label>
					<div class="col-sm-5">
 						<form:input path="number" htmlEscape="false" class="form-control "/>			
					</div>
				</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人姓名：</label>
					<div class="col-sm-5">
 						<form:input path="name" htmlEscape="false" class="form-control required"/>			
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人电话：</label>
					<div class="col-sm-5">
 						<form:input path="mobile" htmlEscape="false" class="form-control required isMobile"/>			
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">地址：</label>
					<div class="col-sm-5">
						 <form:input path="address" htmlEscape="false" data-toggle="city-picker"  class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-5">
						<form:input path="addressDetails" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">证件：</label>
					<div class="col-sm-5">
						<img  style="height: 140px;width: 200px;" src="${sysUser.companyimg}"  />
						<input type="hidden"name="companyimg" value="${sysUser.companyimg}" /> 
						<input type="file" class="input_file_style" id="input_file"/>
					</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司简介：</label>
					<div class="col-sm-5">
						<form:textarea path="companyContent" class="form-control required" />
					</div>
				</div>
			<%--<c:if test="${not empty sysUser.id}">--%>
			<div class="form-group">
				<label class="col-sm-2 control-label">供应商类型：</label>
				<div class="col-sm-5">
						<table class="table table-bordered">
							<tr>
								<td>类型</td>
								<td>抽成比例</td>
								<td>操作</td>
							</tr>
							<c:forEach items="${sysUserTypeList}" var="sysUserType" varStatus="status">
                                <input type="hidden" name="sysUserTypeList[${status.index}].roleid" value="${sysUserType.roleid}"/>
                                <input type="hidden" name="sysUserTypeList[${status.index}].type" value="${sysUserType.type}"/>
							<tr>
								<td>${sysUserType.roleName}</td>
								<td><input class="form-control" type="number" value="${sysUserType.discount}" name="sysUserTypeList[${status.index}].discount"/></td>
								<td>
<%--								<c:choose>
									<c:when test="${not empty sysType.discount}"><a onclick="addRole(0,this,'${sysType.roleid}',${sysType.type},'${sysUser.id}')">禁用</a></c:when>
									<c:otherwise><a onclick="addRole(1,this,'${sysType.roleid}',${sysType.type},'${sysUser.id}')">启用</a></c:otherwise>
								</c:choose>--%>
                                <select   class="form-control" name="sysUserTypeList[${status.index}].ststus" >
                                    <option value="1" <c:if test="${sysUserType.ststus == 1}"> selected</c:if> label="启用">启用</option>
                                    <option value="2" <c:if test="${sysUserType.ststus == 2}"> selected</c:if>  label="关闭">关闭</option>
                                </select>
								</td>
							</tr>
							</c:forEach>
						</table>
				</div>
			</div>
			<%--</c:if>--%>
<%-- 		<c:if test="${fns:hasPermission('sys:member:sysUser:edit') || isAdd}" --%>
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
		<c:if test="${not empty sysUser.id}">
		<div id="tab-3" class="tab-pane fade form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">子账号数量：</label>
				<div class="col-sm-5">
					<input value="${sysUser.number}"  htmlEscape="false"    class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">子账号管理*：</label>
				<div class="col-sm-5">
						<table class="table table-bordered">
							<thead>
								<td>账号</td>
								<td>密码</td>
								<td>权限</td>
							</thead>
							<c:forEach items="${userList}" var="user">
							<tr>
								<td>${user.loginName}</td>
								<td>${user.password}</td>
								<td>${user.roles}</td>
							</tr>
							</c:forEach> 
						</table>
				</div>
			</div>
		</div>
		<div id="tab-2" class="tab-pane fade form-horizontal">

			
			
		<c:forEach  items="${memberRefundList}" var="a">
			<label class="col-sm-2 control-label" style="margin-bottom: 10px;">${a.refundContent}：</label><br/>
				<c:forEach  items="${a.memberRefundList}" var="b">
				<div class="form-group">
					<div class="col-sm-10">
						<%--订单确认后，提前（包含）<input value="${b.refundDay}"/>天，退款比例<input value="${b.refundNum}"/>%.--%>
						订单确认后，提前（包含）${b.refundDay}天，退款比例${b.refundNum}.
					</div>
				</div>
				</c:forEach>
				<hr/>
	</c:forEach>
			
			
		</div>
		</c:if>
		</div>
			
	</div>
	</div>
</div>
</div>
</body>
</html>