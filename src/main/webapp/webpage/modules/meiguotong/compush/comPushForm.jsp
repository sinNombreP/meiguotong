<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>推送管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${ctxStatic}/ueditor/ueditor.all.min.js">
		
	</script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
	
	<style type="text/css">
	.check{
	 background:none;
	 border:solid 1px #DCDADA;
	 border-radius:5px;
	 margin:0 20px 0 0;
	 
	}
	</style>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#send").val(1);
			$("#inputForm").validate({
				submitHandler: function(form){
					if($(".some").is(':checked') && $(".colorid").length ==0){
						/* alert($("input[name='memberids']").val()); */
						jp.error("请选择推送用户！")
					}else{
					jp.loading();
					form.submit();
					} 
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						jp.close(index);
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			$("#search").click(function(){
				var str="10";
				if($("#local").is(':checked')){
					str+=",1";
				}
				if($("#directly").is(':checked')){
					str+=",2";
				}
				if($("#travel").is(':checked')){
					str+=",3";
				}
				var strnumber=$("#number").val();
				var obj2 = document.getElementsByClassName("selectColor");
				var str2 = "";
				if (obj2.length > 0) {
					for (i = 0; i < obj2.length; i++) {
						if (i == obj2.length - 1) {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val()
							str2 += colorid;
						} else {
							var id = obj2[i].id;
							var colorid = $(
									"#" + id).find(
									".colorid")
									.val();
							str2 += colorid + ",";
						}
					}
				}			
				var url="${ctx}/meiguotong/compush/comPush/member";
				var params={"sendType":str,"number":strnumber}; 
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td ><input type="checkbox"';
							t+=' value="';
	                        t+=list[i].memberid;
							t+='"><input type="hidden"  value="';
							t+=list[i].number;
							t+='"><input type="hidden"  value="';
							t+=list[i].nickName;
							t+='"><input type="hidden"  value="';
							t+=list[i].sendType;
							t+='"></td><td>';
							t += list[i].number;
							t += '</td><td>';
							t += list[i].nickName;
							t += '</td></tr>';
						}
					}
					$("#tbody").empty();
					$("#tbody").append(t);
				});
				
				
				
			});
			
			var number = $('.selectColor').length + 1;
			$("#sure").on("click",function() {
				var t = '';
				$("#myModal td input[type='checkbox']")
						.each(
								function(i) {
									if ($(this).is(":checked")) {
										var id = $(this).val();
										var numbero = $(this).next().val();
										var nickName = $(this).next().next().val();
										var sendtype = $(this).next().next().next().val();
										$(this).attr("checked","checked");
										t += '<tr class="selectColor"  id="selectColor_';
				   						t+=number;
				   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="memberids"  type="hidden" value="';
				   						t+=id;
				   						t+='" /><input name="sendType"  type="hidden" value="';
				   						t+=sendtype;
				   						t+='" />';
										t += numbero;
										t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
										t += nickName;
										t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
										t += '<button type="button" class="removeColor">删除</button>';
										t += '</td></tr>';
										number += 1;
									}
								});
				$("#tagTable").append(t);
				$("#myModal").modal('hide');
			})
			
			$("#tagTable").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor").remove();
					});
		});
		
		var ue = UE.getEditor('content');//获得编辑器
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		UE.Editor.prototype.getActionUrl = function(action) {
			if (action == 'uploadimage' || action == 'uploadscrawl'
					|| action == 'uploadimage') {
				return '${ctx}/sys/sysUser/uploadIMG'; //远程图片上传controller
			} else {
				return this._bkGetActionUrl.call(this, action);
			}
		};
		/* 判断推送给所用用户还是部分用户 */
		function getradio(){
			e  =  event.srcElement;
			if(e.value=="all"){
				$("#send").val(1);
				$(".selectColor").remove();
			}
			else(e.value=="some")
			$("#send").val(2);
	        var name = $("#name").val("");
			var obj2 = document.getElementsByClassName("selectColor");
			var str2 = "";
			if (obj2.length > 0) {
				for (i = 0; i < obj2.length; i++) {
					if (i == obj2.length - 1) {
						var id = obj2[i].id;
						var colorid = $(
								"#" + id).find(
								".colorid")
								.val()
						str2 += colorid;
					} else {
						var id = obj2[i].id;
						var colorid = $(
								"#" + id).find(
								".colorid")
								.val();
						str2 += colorid + ",";
					}
				}
			}			
			var url="${ctx}/meiguotong/compush/comPush/member";
			var params={"memberids":str2}; 
			jp.post(url,params,function(data){
				var list = data.body.list;
				var t=''; 
				if(list){
					for(var i in list){
						t += '<tr><td ><input type="checkbox"';
						t+=' value="';
                        t+=list[i].memberid;
						t+='"><input type="hidden"  value="';
						t+=list[i].number;
						t+='"><input type="hidden"  value="';
						t+=list[i].nickName;
						t+='"><input type="hidden"  value="';
						t+=list[i].sendType;
						t+='"></td><td>';
						t += list[i].number;
						t += '</td><td>';
						t += list[i].nickName;
						t += '</td></tr>';
					}
				}
				$("#tbody").empty();
				$("#tbody").append(t);
			});
		};
		
		
		
		
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="javascript:history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comPush" action="${ctx}/meiguotong/compush/comPush/save" method="post" class="form-horizontal">
		<%-- <form:hidden id="sendType" path="sendType" name="sendType"/> --%>
		<form:hidden id="send" path="send" name="send"/>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">标题<i style="color:red;">*</i>：</label>
					<div class="col-sm-10">
 						<form:input path="title" name="title" style="width:100%" htmlEscape="false" class="form-control required" autocomplete="off" />			
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">消息内容<i style="color:red;">*</i>：</label>
					<div class="col-sm-10">
						<%-- 	<form:textarea path="recommend" htmlEscape="false"
						class="form-control " /> --%>
						<textarea id="content" name="content" rows="55" cols="80"
							style="width: 100%; height: 300px">${comPush.content }</textarea>

					</div>
				</div>
				<c:if test="${isAdd==true}">
				<div class="form-group">
					<label class="col-sm-2 control-label">发布对象<i style="color:red;">*</i>：</label>
					<div class="col-sm-5">
						<div id="radiocheck">
							<input name="user" type="radio" onclick="getradio();" value="all"  style="background:none;"
							 checked>全部用户
							<input name="user" type="radio" onclick="getradio();" value="some" style="background:none;" class="some" data-toggle="modal" data-target="#myModal"
							>部分用户
 						</div>
					</div>
				</div>
				</c:if>
				<c:if test='${not empty comPushPeople}'>
				<div class="form-group">
					<label class="col-sm-2 control-label">发布对象<i style="color:red;">*</i>：</label>
					<div class="col-sm-5">
						<div id="radiocheck">
							<input name="user" type="radio" value="all"  style="background:none;"
							<c:if test='${comPush.send == "1" }'>checked</c:if> checked>全部用户
							<input name="user" type="radio" value="some" style="background:none;" 
							<c:if test='${comPush.send == "2" }'>checked</c:if>>部分用户
 						</div>
					</div>
				</div>
				<c:if test='${comPush.send == "2" }'>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <table class="table table-bordered">
		                 	<thead>
			                 	<tr class="selectColor"  id="selectColor" style="text-align:center;">
			                 		<th width="auto" style="text-align:center;table-layout:fixed;border:solid 1px #A9CBEE">账号</th>
									<th width="auto" style="text-align:center;table-layout:fixed;border:solid 1px #A9CBEE">昵称</th>
									<th width="auto" style="text-align:center;table-layout:fixed;border:solid 1px #A9CBEE">类型</th>
			                 	</tr>
		                 	</thead>
		                 	<tbody>
								<c:forEach items="${comPushPeople}" var="item">
								    <tr class="text-c">				
										<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">${item.nickName}</td>
										<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">${item.number}</td>
										<td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE">${item.strSendType}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
		             </div>
		        </div>
		        </c:if>
				</c:if>
				<table id="tagTable" style="margin-left: 20px; border: 1px solid black; margin-top: 10px;">
						</table>
		<c:if test="${fns:hasPermission('meiguotong:compush:comPush:edit') || isAdd}">
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
<!-- 标签属性 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">选择用户</h4>
				</div>
				<div class="modal-body">
				<div style="margin-bottom: 5px;">
						<label>角色：</label>
						<input type="checkbox" id="local" class="check" checked>当地玩家 &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="directly" class="check" checked>直客 &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="travel" class="check" checked>旅行社 &nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					<div style="margin-bottom: 5px;">
						<label>账号：</label><input id="number" style="height: 30px;" />
						<button id="search" type="button" class="btn btn-primary">查询</button>
					</div>

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>账号</td>
								<td>昵称</td>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="sure">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>