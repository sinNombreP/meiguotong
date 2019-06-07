<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>各项管理管理</title>
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
					jp.post("${ctx}/meiguotong/comtag/comTag/save",$('#inputForm').serialize(),function(data){
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
			$("#city").change(function(){
				fuc();
			})
			$("#language").on("change","#languageid",function(){
				var languageId=$(this).val();
				var type=$("#type").val();
				var params={"languageId":languageId,"type":type};
				var url="${ctx}/meiguotong/comtag/comTag/getLabelByLanguage";
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t='';
					for( var i in list){
					    t+='<option value="';
					    t+=list[i].id;
					    t+='" class="removeLabel">';
					    t+=list[i].content;
					    t+='</option>';
					}
					$(".removeLabel").remove();
					$(".labelName").append(t);
				})
			})
		});
		var fuc = function fuc(){
			var select=document.getElementById("city");
			var c=select.value;
			var language=document.getElementById("language");
			var labelTitle=document.getElementById("labelTitle");
			var attrTitle=document.getElementById("attrTitle");
			       switch(c)
			{
			case "1": language.innerHTML=
				'<td class="width-15 active"><label class="pull-right">语言:</label></td>'+
				'<td class="width-35">'+
				'<select name="languageId" class="form-control required">'+
				'<option value="" >选择语言</option>'+
				'<c:forEach items="${comLanguageList}" var="languageList">'+
				'<option value="${languageList.id}">${languageList.content}</option>'+
				'</c:forEach>'+
				'</select>'+
				'</td>'
				;
				
				labelTitle.innerHTML=
					'<td class="width-15 active"><label class="pull-right">标签名称:</label></td>'+
					'<td class="width-35">'+
					'<input type="hidden" name="fatherId" value="0">'+
	     	        '<input  name="content" htmlEscape="false" class="form-control required"/>'+
					'</td>';
				attrTitle.innerHTML='';	
			break;
			
			case "2":language.innerHTML=
				'<td class="width-15 active"><label class="pull-right">语言:</label></td>'+
				'<td class="width-35">'+
				'<select id="languageid"  name="languageId" class="form-control required">'+
				'<option value="" >选择语言</option>'+
				'<c:forEach items="${comLanguageList}" var="languageList">'+
				'<option value="${languageList.id}">${languageList.content}</option>'+
				'</c:forEach>'+
				'</select>'+
				'</td>'
				;
				
			     labelTitle.innerHTML=
			    	 '<td class="width-15 active"><label class="pull-right">标签标题:</label></td>'+
						'<td class="width-35 ">'+
						'<select name="fatherId" class="form-control required labelName">'+
						'<option value="" >选择标签</option>'+
						'</td>';
			    
			    attrTitle.innerHTML=
			    	'<td class="width-15 active"><label class="pull-right">属性名称:</label></td>'+
				    '<td class="width-35">'+
	     	        '<input name="content" htmlEscape="false"    class="form-control required"/>'+
				    '</td>';
			break;
			}
			}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="comTag" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">选择等级</label></td>
					<td class="width-35">
                 <select id="city" class="form-control m-b required" >
					<option value="" >请选择</option>
					<option value="1" >标签标题</option>
					<option value="2" >标签属性</option>
				</select>
					</td>					
				</tr>
				<tr id="language">
				</tr>
				<tr id="labelTitle">		
				</tr>
				<tr id="attrTitle">		
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>