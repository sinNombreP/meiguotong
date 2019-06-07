<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>自动通知模板管理</title>
	<meta name="decorator" content="ani"/>
	<style type="text/css">
		.form-group{
		margin-bottom:10px;
		}
		.form-control{
		border-radius:5px;
		}
		.search{
		border-radius:5px;
		}
	</style>
	<script type="text/javascript">

		$(document).ready(function() {
			$('.textarea').val(" ");
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
			
			/* 语言改变后，通知模板绑定相应内容 */
			$("#language").change(function(){
				var url="${ctx}/meiguotong/compush/comPushContent/search";
				var params={"id":$("#language").val()}; 
				jp.post(url,params,function(data){
					var list = data.body.list;
					$(".id").val("");
					$(".check").prop("checked",false);
					$('.textarea').val(" ");
					if(list){
						for(var i in list){
							var ii=parseInt(i)+1;
							$("#id"+ii).val(list[i].id);
							list[i].status==1?$("#statuss"+ii).prop("checked",true):$("#statuss"+ii).prop("checked",false);
							$("#content"+ii).val(list[i].content);
						}
					}
				});
			});
			
			/* 没有选择语言点击复选框或文本编辑 */
			$('.check,.textarea').click(function(){
				if($("#language").val()==null || $("#language").val()==""){
					$(".check").attr("checked", false);
					$('.textarea').blur();
					jp.error("请先选择语言！")
				}
			});
			
			/* 点击按钮拼接字符 */
			/* $(".search").on("click",function(){
				if($("#language").val()==null || $("#language").val()=="")
				jp.error("请先选择语言！")
				else
				var id=$(this).parent().attr('id');
				$("#content"+id).val($("#content"+id).val()+"【"+$(this).text()+"】");
				$("#content"+id).focus();
			}); */
			$(".search").on("click",function(){
				if($("#language").val()==null || $("#language").val()=="")
				jp.error("请先选择语言！")
				else
				var id=$(this).parent().attr('id');
				var field = document.getElementById("content"+id);
				var v=$("#content"+id).val();
				var len=$("#content"+id).val().length;
				if (document.selection) {
                    //IE
                    document.getElementById("content"+id).focus();
                    document.selection.createRange().text = str;
                } else {
                    //非IE
                    var selPos = field.selectionStart;
                    $("#content"+id).val($("#content"+id).val().slice(0, field.selectionStart) + "【"+$(this).text()+"】" + $("#content"+id).val().slice(field.selectionStart, len));
                    $("#content"+id).focus();
                    //document.getElementById("content"+id).iSelectField(selPos + $(this).text().length);
                }
				/* $("#content"+id).val($("#content"+id).val()+"【"+$(this).text()+"】");
				$("#content"+id).focus(); */
			});
			
			/* 复选框值改变事件 */
			 $(".check").change(function(){
				var id=$(this).parent().attr('id');
				if($.trim($("#content"+id).val())==""){
					jp.error("请先填写通知内容！");
					$(this).attr("checked", false);
					$("#content"+id).focus();
				}
			}); 
			
			/* 文本域失去焦点 */
			$(".textarea").blur(function(){
				if($.trim($(this).val())==""){
				var id=$(this).parent().attr('id');
				$(this).parent().parent().prev().find(".check").attr("checked", false);
				}
			});
			
			/* $(".textarea").keydown(function(event){
					var pos = $(this).iGetFieldPos();
				if (event.keyCode == 8){
	                var v = $(this).val();
	                
	                var before=$(this).val().slice(0, $(this).selectionStart);
	                var ii=before.charAt(before.length-1);
	                if(before.charAt(before.length-1)=="】"){
	                	var i=before.lastIndexOf("】");
	                	var j=before.lastIndexOf("【");
	                	var n=i-j;
	                //大于0则删除后面，小于0则删除前面
	                 $(this).val(n &gt; 0 ? v.slice(0, pos - n) + v.slice(pos) : v.slice(0, pos) + v.slice(pos - n));
	                $(this).iSelectField(pos - (n &lt; 0 ? 0 : n)); 
	                }

				}
			}) */
			
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
				<i class="ti-angle-left"></i> 通知模板
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="comPushContent" action="${ctx}/meiguotong/compush/comPushContent/save" method="post" class="form-horizontal">
		<div class="form-group" >
			<label class="col-sm-2 control-label">语言：</label>
			<div class="col-sm-10">
				<form:select path="languageid" id="language" class="form-control required">
					<form:option value="" label=""/>
					<c:forEach items="${comlanguage}" var="item">
					<form:option value="${item.id}" label="${item.content}"/>
				</c:forEach>
				</form:select>
			</div>
		</div>
		<sys:message content="${message}"/>	
				<input type="hidden"  id="id1" name="list[0].id" class="id" value=''/>
				<input type="hidden"  id="type1" name="list[0].type" class="type" value='1'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">注册成功<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="1" class="col-sm-5">
						<input id="statuss1" name="list[0].status" type="checkbox" class="check" value="1">启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button  type="button" class="btn btn-primary search" >账户/名称</button>
						<button  type="button" class="btn btn-primary search" >密码</button>
						<button  type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div id="1" class="col-sm-10">
						<form:textarea id="content1" path=""  name="list[0].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden" id="id2" name="list[1].id" class="id" value=""/>
				<input type="hidden"  id="type2" name="list[1].type" class="type" value='2'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">未付款订单<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="2" class="col-sm-7">
						<input id="statuss2" type="checkbox" name="list[1].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button  type="button" class="btn btn-primary search">账户/名称</button>
						<button  type="button" class="btn btn-primary search">下单时间</button>
						<button  type="button" class="btn btn-primary search" >订单号</button>
						<button  type="button" class="btn btn-primary search" >多长时间</button>
						<button  type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content2" path="" name="list[1].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden"  id="id3" name="list[2].id" class="id" value=""/>
				<input type="hidden"  id="type3" name="list[2].type" class="type" value='3'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">下单成功<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="3" class="col-sm-5">
						<input id="statuss3" type="checkbox" id="" name="list[2].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button  type="button" class="btn btn-primary search" >账户/名称</button>
						<button  type="button" class="btn btn-primary search" >订单号</button>
						<button  type="button" class="btn btn-primary search" >下单时间</button>
						<button  type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content3" path="" name="list[2].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden"  id="type4" name="list[3].type" class="type" value='4'/>
				<input type="hidden"  id="id4" name="list[3].id" class="id" value=""/>
				<div class="form-group">
					<label class="col-sm-2 control-label">后台确认<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="4" class="col-sm-7">
						<input id="statuss4" type="checkbox" name="list[3].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button  type="button" class="btn btn-primary search" >账户/名称</button>
						<button  type="button" class="btn btn-primary search" >订单号</button>
						<button  type="button" class="btn btn-primary search" >确认时间</button>
						<button  type="button" class="btn btn-primary search" >成功/失败</button>
						<button  type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content4" path="" name="list[3].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden"  id="id5" name="list[4].id" class="id" value=""/>
				<input type="hidden"  id="type5" name="list[4].type" class="type" value='5'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">开始订单<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="5" class="col-sm-5">
						<input id="statuss5" type="checkbox" name="list[4].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button  type="button" class="btn btn-primary search" >账户/名称</button>
						<button  type="button" class="btn btn-primary search" >订单号</button>
						<button  type="button" class="btn btn-primary search" >时间</button>
						<button  type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content5" path="" name="list[4].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden"  id="id6" name="list[5].id" class="id" value=""/>
				<input type="hidden"  id="type6" name="list[5].type" class="type" value='6'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">取消订单<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="6" class="col-sm-5">
						<input id="statuss6" type="checkbox" name="list[5].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button  type="button" class="btn btn-primary search" >账户/名称</button>
						<button  type="button" class="btn btn-primary search" >订单号</button>
						<button  type="button" class="btn btn-primary search" >取消时间</button>
						<button  type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content6" path="" name="list[5].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden"  id="id7" name="list[6].id" class="id" value=""/>
				<input type="hidden"  id="type7" name="list[6].type" class="type" value='7'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">注册验证码<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="7" class="col-sm-5">
						<input  type="checkbox" name="list[6].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-primary search" >验证码</button>
						<button type="button" class="btn btn-primary search" >账户/名称</button>
						<button type="button" class="btn btn-primary search" >网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content7" path="" name="list[6].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				<input type="hidden"  id="id8" name="list[7].id" class="id" value=""/>
				<input type="hidden"  id="type8" name="list[7].type" class="type" value='8'/>
				<div class="form-group">
					<label class="col-sm-2 control-label">忘记密码<i style="color:red;">*</i>：</label>
					<div class="form-group">
					<div id="8" class="col-sm-5">
						<input id="statuss8" type="checkbox"  name="list[7].status" class="check" value="1" >启用 &nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-primary search" onclick="forgetPassword(this)">验证码</button>
						<button type="button" class="btn btn-primary search" onclick="forgetPassword(this)">账户/名称</button>
						<button type="button" class="btn btn-primary search" onclick="forgetPassword(this)">网址</button>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<form:textarea id="content8" path=""  name="list[7].content" htmlEscape="false" style="margin-left:20%;" rows="4"    class="form-control textarea"/>
					</div>
				</div>
				
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
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