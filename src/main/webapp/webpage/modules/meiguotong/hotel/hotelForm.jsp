<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>酒店管理管理</title>
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
					jp.post("${ctx}/meiguotong/hotel/hotel/save",$('#inputForm').serialize(),function(data){
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
		
		
		var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
		function checkImg(target) {
			var fileSize = 0;
			if (isIE && !target.files) {
				var filePath = target.value;
				var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
				var file = fileSystem.GetFile(filePath);
				fileSize = file.Size;
			} else {
				fileSize = target.files[0].size;
			}
			var size = fileSize / 1024;
			if (size > 2000) {
				jp.info("图片不能大于2M");
				target.value = "";
				return false
			}
			var name = target.value;
			var fileName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			if (fileName != "jpg" && fileName != "gif" && fileName != "png") {
				jp.info("请选择jpg、gif、png格式上传！");
				target.value = "";
				return false
			}
			return true;
		}

		function uploadFailed(evt) {
			jp.info("上传失败");
		}
		function uploadCanceled(evt) {
			jp.info("取消上传");
		}
		
		$(function(){
			$(".table").on("change", ".input_file_style", function(e) {
				var target = document.getElementById($(this).attr("id"));
				if (!checkImg(target)) {
					return;
				}
				var fileId = $(this).attr("id");
				var index = fileId.split("_")[1];
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
	          					$("#imgSrc").val(textJson.body.filePath);
	          					$("#img").attr("src",src);
	          				} 
	          				jp.success(textJson.msg);
	          			}, false);
						xhr.addEventListener("error", uploadFailed, false);
						xhr.addEventListener("abort", uploadCanceled, false);
						xhr.open("POST", "${ctx}/sys/img/uploadFile");
						xhr.send(fd);
					}
				}
			})
			//根据语言获取城市
			$("#languageId").change(function(){
				$(".removeCity").remove();
				var languageId = $(this).val();
				var params = {"languageId":languageId};
				var url = "${ctx}/meiguotong/hotel/hotel/getCity";
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t='';
					for(var i in list){
						t+='<option class="removeCity" value="';
						t+=list[i].cityid
						t+='" >';
						t+=list[i].cityName
						t+='</option>';
					}
					$("#cityId").append(t);
				});
			})
		})
		
		
	</script>
<style type="text/css">
.dv_pic_item {
	width: 200px;
	height: 200px;
	margin: 5px 5px;
	float: left;
}

.btn_add_pic {
	width: 80px;
	height: 30px;
	border-radius: 6px;
	outline: none;
	border: none;
	background-color: #00BCD4;
	color: #fff;
	cursor: pointer;
	margin-top: 20px;
	margin-bottom: 20px;
}

.input_file_style {
	width: 200px;
	height: 20px;
}

.img_style,.img_style1{
	width: 200px;
	height: 140px;
	display: block;
	background-size: 100% auto;
}


.deleteBtn {
	padding: 2px 10px;
	background: #E1E1E1;
}
</style>
	
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="hotel" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
		  	 <c:if test="${empty hotel.id}">
				<tr>
					<td class="width-15 active"><label class="pull-right">语言：</label></td>
					<td class="width-35">
						<form:select path="languageId"  class="form-control m-b requiled">
							<form:option value="" label="全部"/>
							<form:options items="${comLanguageList}" itemLabel="content" itemValue="id" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty hotel.id}">
				<tr>
					<td class="width-15 active"><label class="pull-right">语言：</label></td>
					<td class="width-35">
						<form:select path="languageId" disabled="true"  class="form-control m-b requiled">
							<form:option value="" label="全部"/>
							<form:options items="${comLanguageList}" itemLabel="content" itemValue="id" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
			</c:if>
				<tr>
					<td class="width-15 active"><label class="pull-right">酒店名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control requiled"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">酒店星级：</label></td>
					<td class="width-35">
						<form:select path="level" class="form-control m-b requiled">
							<form:option value="" label="请选择"/>
							<form:option value="1" label="★"/>
							<form:option value="2" label="★★"/>
							<form:option value="3" label="★★★"/>
							<form:option value="4" label="★★★★"/>
							<form:option value="5" label="★★★★★"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">城市：</label></td>
					<td class="width-35">
						<form:select path="cityId" class="form-control m-b requiled">
							<form:option value="" label="请选择"/>
							<c:if test="${not empty hotel.id}">
								<c:forEach items="${cityList}" var="city">
									<form:option value="${city.cityid}" label="${city.cityName}"/>
								</c:forEach>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">详细地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">封面图片：</label></td>
					<td class="width-35">
						<img  class="img_style1" src="${hotel.imgUrl}" id="img"  />
						<input type="hidden"name="imgUrl" value="${hotel.imgUrl}" id="imgSrc" /> 
						<input type="file" class="input_file_style" id="input_file"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>