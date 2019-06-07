<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>旅游定制管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					if(!$("#imgSrc").val()){
						jp.error("封面不能为空！");
						return;
					}
					var num1=0;
					var num2=0;
					var num3=0;
					$(".cityNameClick").each(function(){
						if(!$(this).val()){
							num1=1;
						}
					})
					$(".cityImg").each(function(){
						if(!$(this).val()){
							num2=1;
						}
					})
					$(".scenicNameClick").each(function(){
						if(!$(this).val()){
							num3=1;
						}
					})
					if(num1!=0){
						jp.error("行程城市不能为空！");
						return;
					}
					if(num2!=0){
						jp.error("行程城市图片不能为空！");
						return;				
					}
					if(num3!=0){
						jp.error("行程景点不能为空！");
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
		
		var cityId;
		var cityName;
		var scenicId;
		var scenicName;
		
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
						xhr.addEventListener("error", uploadFailed, false);
						xhr.addEventListener("abort", uploadCanceled, false);
						xhr.open("POST", "${ctx}/sys/img/uploadFile");
						xhr.send(fd);
					}
				}
			})
			
			//添加天数
			$(".wrapper").on("click","#addDay",function(){
				var maxDay = $("#dayNum").val();
				var day = $("#travelDetailsListLength").val();
				var day2 = parseInt(day) + 1;
				if(maxDay =="" || maxDay == 0 || maxDay == null || maxDay == undefined || day >= maxDay){
					jp.info("请输入正确的天数");
					return;
				}
				var html = 
				'<div class="form-group" id="dayList_'+day+'">'+
				'<label class="col-sm-2 control-label">第'+day2+'天</label>'+
				'<input type="hidden" name="travelDetailsList['+day+'].day" value="'+day2+'"/>'+
				'<div class="col-sm-10" id="dayList_'+day+'">'+
				'<div class="form-group form-group2">'+
					'<label class="col-sm-2 control-label"></label>'+
					'<div class="col-sm-10">'+
						'<button type="button" class="btn btn-info"   id="add_'+day+'">添加城市</button>'+
						'<button type="button" class="btn btn-danger" id="delete_'+day+'" >删除最后一个城市</button>'+
					'</div>'+
				'</div>'+
				'<input type="hidden" id="travelDetailsListLength_'+day+'" value="0"/>'+
				'<div id="dayList2_'+day+'">'+
				'</div>'+
				'</div>'+
			'</div>';
				$("#dayList").append(html);
				$("#travelDetailsListLength").val(day2);
				addCity(day,0);
			});
			
			//删除天数
			$(".wrapper").on("click","#deleteDay",function(){
				var day =parseInt($("#travelDetailsListLength").val()) - 1;
				if(day > -1){
					$("#dayList_"+day).remove();
					$("#travelDetailsListLength").val(day);
				}
			});
			
			
			//添加城市
			$("#dayList").on("click",".btn-info",function(){
			var id = $(this).attr("id");
			var index1 = parseInt(id.split("_")[1]);
			var index2 = parseInt($("#travelDetailsListLength_"+index1).val());
			addCity(index1,index2);
			});
			
			
			//删除城市
			$("#dayList").on("click",".btn-danger",function(){
				var id = $(this).attr("id");
				var index1 = parseInt(id.split("_")[1]);
				var index2 = parseInt($("#travelDetailsListLength_"+index1).val())-1;
				if(index2 <= 0){
					jp.info("至少一个城市");
					return;
				}
				$("#dayList2_"+index1+"_"+index2).remove();
				$("#travelDetailsListLength_"+index1).val(index2);
			});
			
			
	    	//获取城市
	 	    $(".wrapper").on("click",".cityNameClick,#search2,#reset2",function(){
	  	    	$("#tbody2").empty();
	  	    	var language = $("#language option:selected").val();
	  	    	if(language == null || language == "" || language == undefined){
	  	    		jp.info("请选择语言");
	  	    		return;
	  	    	}
	  	    	console.log($(this).attr("id"));
	  	    	if( $(this).attr("id") != "search2" ){
	  	    		$("#searchContent2").val("");
	  	    	}
	  	    	if( $(this).attr("id") != "search2" && $(this).attr("id") != "reset2" ){
	  	    		cityId = $(this).prev();
	  	    		cityName = $(this);
	  	    	}
	  	    	$('#myModal2').modal('show');
	  	    	getCityDate();
	    	})
	    	
	    	
	    	//选择城市
	     	$("#sure2").on("click",function(){
	     		var id = $("input[name='cityName']:checked").val();
	     		var name = $("input[name='cityName']:checked").parent().next().text();
	     		var id1 = cityId.val();
	     		cityId.val(id);
	     		cityName.val(name);
				$("#myModal2").modal('hide');
 	     		if(id !=  id1){
 	     			cityId.parent().parent().parent().find(".scenicClick").val("");
 	     			cityId.parent().parent().parent().find(".scenicNameClick").val("");
	     		} 
			})
			
	    	
    	//获取景点
 	    $(".wrapper").on("click",".scenicNameClick,#search3,#reset3",function(){
  	    	$("#tbody3").empty();
  	    	var language = $("#language option:selected").val();
  	    	cityId = $(this).parent().parent().parent().find(".cityClick");
  	    	var city = cityId.val();
  	    	scenicId = $(this).prev();
  	    	scenicName = $(this);
  	    	if(language== null || language == "" || language == undefined){
  	    		jp.info("请选择语言");
  	    		return;
  	    	}
  	    	if(city== null || city == "" || city == undefined){
  	    		jp.info("请选择城市");
  	    		return;
  	    	}
  	    	if( $(this).attr("id") == "reset3" || $(this).attr("id") == "scenicContent" ){
  	    		$("#searchContent3").val("");
  	    	}
  	    	$('#myModal3').modal('show');
  	    	getTripScenic();
    	}) 
	    	
	    	
    	//选择景点
     	$("#sure3").on("click",function(){
				var ids = [];
				var names = [];
	          $("#myModal3 input[type='checkbox']").each(function(i){   
	        		if($(this).prop('checked')){
	        			ids.push($(this).val());
	        			names.push($(this).parent().next().text());
	        		}
	          });
	          scenicId.val(ids.join(","));
	          scenicName.val(names.join(","));
			  $("#myModal3").modal('hide');
		})
		
		
		//语言改变
 	    $(".wrapper").on("change","#language",function(){
			$("#dayList").find(".cityNameClick").val("");
			$("#dayList").find(".cityClick").val("");
			$("#dayList").find(".scenicNameClick").val("");
			$("#dayList").find(".scenicClick").val("");
			$("#cityName").val("");
			$("#city").val("");
    	})
    	
		
		
	})
	
		//添加城市
		function addCity(index1,index2){
			var html = 	
				'<div id="dayList2_'+index1+'_'+index2+'">'+
				'<div class="form-group">'+
				'<label class="col-sm-2 control-label">城市名称</label>'+
				'<div class="col-sm-2">'+
					'<input type="hidden" name="travelDetailsList['+index1+'].travelDetailsList['+index2+'].city" value="" class="cityClick"/>'+
					'<input value="" readonly="true"  name="travelDetailsList['+index1+'].travelDetailsList['+index2+'].cityName"   class="form-control  cityNameClick  required"/>'+
				'</div>'+
			'</div>'+
			'<div class="form-group">'+
				'<label class="col-sm-2 control-label">城市图片</label>'+
				'<div class="col-sm-10">'+
					'<img  class="img_style" src="${travelDetails2.img}"  class="form-control required"/>'+
					'<input type="hidden" class="cityImg" name="travelDetailsList['+index1+'].travelDetailsList['+index2+'].img" /> '+
					'<input type="file" class="input_file_style" />'+
				'</div>'+
			'</div>'+
			'<div class="form-group">'+
				'<label class="col-sm-2 control-label">城市景点</label>'+
				'<div class="col-sm-8">'+
					'<input type="hidden" name="travelDetailsList['+index1+'].travelDetailsList['+index2+'].scenic"  class="scenicClick" value=""/>'+
					'<input  value=""  readonly="true"  class="form-control required scenicNameClick"/>'+
				'</div>'+
			'</div>'+'<hr />'+
			'</div>';
			$("#dayList2_"+index1).append(html);
			$("#travelDetailsListLength_"+index1).val(parseInt(index2)+1);
		}
		
		
		//获取城市数据
		function getCityDate(){
			jp.post("${ctx}/meiguotong/comcity/comCity/getData",{
				"languageId":$("#language option:selected").val(),
				"endCity":cityId.val(),
				"cityName":$("#searchContent2").val()
				},function(data){
				if(data.success){
						var list=data.body.comCityList;
						var html = "";
						var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
							for (x in list){
						  	html += '<tr>';
						  	html +='<td><input type="radio" name="cityName" value="'+list[x].id+'"';
					  		if(list[x].endCityFlag){html +=' checked="checked" ';}
						  	html +='></td>';
						  	html +='<td>'+list[x].cityName+'</td>';
						  	html +='</tr>';
							}
						$("#tbody2").empty();
						$("#tbody2").append(html==""?html1:html);
	            }else{
		  			jp.error(data.msg);
	            }
			}) 
		} 
		
		
    	//获取行程景点
    	function getTripScenic(){
		$("#tbody3").empty();
   		jp.post("${ctx}/meiguotong/scenicspot/scenicSpot/getTripScenic",{
   			"languageId":$("#language option:selected").val(),
   			"cityId":cityId.val(),
   			"scenicIds":scenicId.val(),
   			"scenicFlag":1,
   			"name":$("#searchContent3").val()
   			},function(data){
   			if(data.success){
   					scenicFlag = 0;
					scenicSpotList = data.body.scenicSpotList;
					var html = "";
					var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
					var html2 = "";
					var html3 = '<option value="" label="请选择" />';
					for (x in scenicSpotList){
					  	html += '<tr>';
					  	html +='<td><input type="checkbox"   value="'+scenicSpotList[x].id+'"';
				  		if(scenicSpotList[x].checkFlag){html +=' checked="checked" ';}
					  	html +='></td>';
					  	html +='<td>'+scenicSpotList[x].name+'</td>';
					  	html +='</tr>';
					  	
						html2 += '<option value="'+scenicSpotList[x].id+'" label="'+scenicSpotList[x].name+'" />';
					  	
					}
					$("#tbody3").append(html==""?html1:html);
               }else{
   	  			jp.error(data.msg);
               }
   		}) 
   	} 
	
		
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
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/meiguotong/travel/travelCustomization"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="travelCustomization" action="${ctx}/meiguotong/travel/travelCustomization/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">语言：</label>
					<div class="col-sm-2">
						<form:select path="language" class="form-control m-b required">
							<form:options items="${comLanguageList}" itemLabel="content"
								itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">城市<i style="color: red;">*</i>：</label>
					<div class="col-sm-2">
						<form:hidden path="city"/>
						<form:input path="cityName" htmlEscape="false" readonly="true"   class="form-control required cityNameClick "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">封面<i style="color: red;">*</i>：</label>
					<div class="col-sm-10" >
						<img  class="img_style1" src="${travelCustomization.img}" id="img"  />
						<input type="hidden" name="img" value="${travelCustomization.img}" id="imgSrc" /> 
						<input type="file" class="input_file_style" id="input_file"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出游天数<i style="color: red;">*</i>：</label>
					<div class="col-sm-2">
						<form:input path="dayNum" htmlEscape="false"    class="form-control required digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">行程内容</label>
					<div class="col-sm-10">
						<button type="button" class="btn btn-info" id="addDay">添加天数</button>
						<button type="button" class="btn btn-danger" id="deleteDay">删除最后一天</button>
					</div>
				</div>
				<input type="hidden" id="travelDetailsListLength" value="${fn:length(travelDetailsList)}"/>
				<div id="dayList">
				<c:forEach items="${travelDetailsList}" var="travelDetails" varStatus="status">
				<div class="form-group" id="dayList_${status.index}">
					<label class="col-sm-2 control-label">第${travelDetails.day}天</label>
					<input type="hidden" name="travelDetailsList[${status.index}].day" value="${travelDetails.day}"/>
					<div class="col-sm-10" >
					<div class="form-group form-group2">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<button type="button" class="btn btn-info" id="add_${status.index}" >添加城市</button>
							<button type="button" class="btn btn-danger" id="delete_${status.index}" >删除最后一个城市</button>
						</div>
					</div>
					<input type="hidden" id="travelDetailsListLength_${status.index}" value="${fn:length(travelDetails.travelDetailsList)}"/>
					<div id="dayList2_${status.index}">
					<c:forEach items="${travelDetails.travelDetailsList}" varStatus="status2" var="travelDetails2">
							<%-- <input type="hidden" name="travelDetailsList[${status.index}].travelDetailsList[${status2.index}].sort" value="${travelDetails2.sort}"/> --%>
							<div id="dayList2_${status.index}_${status2.index}">
							<div class="form-group ">
								<label class="col-sm-2 control-label">城市名称</label>
								<div class="col-sm-2">
									<input type="hidden" name="travelDetailsList[${status.index}].travelDetailsList[${status2.index}].city" value="${travelDetails2.city}" class="cityClick"/>
									<input readonly="true"  value="${travelDetails2.cityName}" name="travelDetailsList[${status.index}].travelDetailsList[${status2.index}].cityName" class="form-control cityNameClick  required" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">城市图片</label>
								<div class="col-sm-10">
									<img  class="img_style" src="${travelDetails2.img}"  class="form-control required"/>
									<input type="hidden" class="cityImg" value="${travelDetails2.img}" name="travelDetailsList[${status.index}].travelDetailsList[${status2.index}].img" /> 
									<input type="file" class="input_file_style" id="file_${status.index}_${status2.index}" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">城市景点</label>
								<div class="col-sm-8">
									<input type="hidden" name="travelDetailsList[${status.index}].travelDetailsList[${status2.index}].scenic" class="scenicClick" value="${travelDetails2.scenic}"/>
									<input readonly="true"  value="${travelDetails2.scenicContent}"   class="form-control scenicNameClick required"/>
								</div>
							</div>
							<hr />
							</div>
					</c:forEach>
					</div>
					</div>
				</div>
				</c:forEach>
			</div>
<%-- 		<c:if test="${fns:hasPermission('meiguotong:travel:travelCustomization:edit') || isAdd}"> --%>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
	<%-- 	</c:if> --%>
		</form:form>
		</div>				
	</div>
	</div>
</div>


<!-- 城市模态框（Modal） -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加城市</h4>
            </div>
            <div class="modal-body">
	      <div>
	      	<input id="searchContent2" placeholder="输入城市名称" class="form-control col-sm-2" style="width:40%;margin-right:15px;"/>
	      	 <a  id="search2" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
	      	 <a  id="reset2" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
	      </div>
        <br/>
		<table class="table table-bordered">
					 <thead>
					    <tr>
					        <th> </th>
					       	<th>城市名称</th>
					    </tr>
					    </thead>
					    <tbody id="tbody2">
						  	<tr>
						  		<td><input type="checkbox"   class="name" value="1"></td>
						  		<td>666666</td>
						  	</tr>
					    </tbody>
					    
		</table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sure2">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
		
	
<!-- 途径景点模态框（Modal） -->
	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加景点</h4>
            </div>
            <div class="modal-body">
	      <div>
	      	<input id="searchContent3" placeholder="输入景点名称" class="form-control col-sm-2" style="width:40%;margin-right:15px;"/>
	      	 <a  id="search3" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
	      	 <a  id="reset3" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
	      </div>
        <br/>
		<table class="table table-bordered">
					 <thead>
					    <tr>
					        <th> </th>
					       	<th>景点名称</th>
					    </tr>
					    </thead>
					    <tbody id="tbody3">
						  	<tr>
						  		<td><input type="checkbox"   class="name" value="1"></td>
						  		<td>666666</td>
						  	</tr>
					    </tbody>
					    
		</table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sure3">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

</div>
</body>
</html>