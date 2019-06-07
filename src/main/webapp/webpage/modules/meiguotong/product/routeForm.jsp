<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>当地参团管理</title>
<meta name="decorator" content="ani" />
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.all.min.js">
	
</script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>

<link rel="stylesheet" href="${ctxStatic}/city/css/cityselect.css">
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#inputForm").validate({
									submitHandler : function(form) {
										if($("input[name='imgSrc']").length==0){
											jp.error("产品图片不能为空！");
											return;
										}
										var num=0;
										$("input[name='imgSrc']").each(function(){
											var img=$(this).val();
											if(!img){
												num=1;
											}
										})
										if(num!=0){
											jp.error("产品图片不能为空！");
											return;
										}
										if(${route.type==1}){
											if(!$("#startCity").val()){
												jp.error("出发城市不能为空！");
												return;
											}
										}
										if(!$("#endCity").val()){
											jp.error("目的地城市不能为空！");
											return;
										}
										if(!$("#labelAttrid").val()){
											jp.error("标签属性不能为空！");
											return;
										}
										if(!$("#dayNum").val()){
											jp.error("旅游天数不能为空！");
											return;
										}
										if(!/^0$|^[1-9]\d*$/.test($("#dayNum").val().trim())){
											  jp.error("旅游天数只能为整数！");
											  return;
										}
										if(!$("#nightNum").val()){
											jp.error("旅游天数不能为空！");
											return;
										}
										if(!/^0$|^[1-9]\d*$/.test($("#nightNum").val().trim())){
											  jp.error("旅游天数只能为整数！");
											  return;
										}
										if(!$("#advanceDay").val()){
											jp.error("订票提前天数不能为空！");
											return;
										}
										if(!/^0$|^[1-9]\d*$/.test($("#advanceDay").val().trim())){
											  jp.error("订票提前天数只能为整数！");
											  return;
										}
										if(!$("#price").val()){
											jp.error("价格不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#price").val().trim())){
											  jp.error("请输入正确的价格！");
											return;
										} 
										if(!$("input[name='beginDate']").val()){
											jp.error("日期范围不能为空！");
											return;
										}
										if(!$("input[name='endDate']").val()){
											jp.error("日期范围不能为空！");
											return;
										}
										if(!$("#oneCost").val()){
											jp.error("单人房价成本不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#oneCost").val().trim())){
											  jp.error("请输入正确的单人房价成本！");
											return;
										} 
										if(!$("#oneProfit").val()){
											jp.error("单人房价利润不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#oneProfit").val().trim())){
											  jp.error("请输入正确的单人房价利润！");
											return;
										} 
										if(!$("#twoCost").val()){
											jp.error("双人房价成本不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#twoCost").val().trim())){
											  jp.error("请输入正确的双人房价成本！");
											return;
										} 
										if(!$("#twoProfit").val()){
											jp.error("双人房价利润不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#twoProfit").val().trim())){
											  jp.error("请输入正确的双人房价利润！");
											return;
										} 
										if(!$("#threeCost").val()){
											jp.error("三人房价成本不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#threeCost").val().trim())){
											  jp.error("请输入正确的三人房价成本！");
											return;
										} 
										if(!$("#threeProfit").val()){
											jp.error("三人房价利润不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#threeProfit").val().trim())){
											  jp.error("请输入正确的三人房价利润！");
											return;
										} 
										if(!$("#fourCost").val()){
											jp.error("四人房价成本不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#fourCost").val().trim())){
											  jp.error("请输入正确的四人房价成本！");
											return;
										} 
										if(!$("#fourProfit").val()){
											jp.error("四人房价利润不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#fourProfit").val().trim())){
											  jp.error("请输入正确的四人房价利润！");
											return;
										} 
										if(!$("#arrangeCost").val()){
											jp.error("配房价成本不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#arrangeCost").val().trim())){
											  jp.error("请输入正确的配房价成本！");
											return;
										} 
										if(!$("#arrangeProfit").val()){
											jp.error("配房价利润不能为空！");
											return;
										}
										if(!/^[0-9,.]*$/.test($("#arrangeProfit").val().trim())){
											  jp.error("请输入正确的配房价利润！");
											return;
										} 
										if(!$("#stock").val()){
											jp.error("库存不能为空！");
											return;
										}
										if(!/^(-)?[1-9][0-9]*$/.test($("#stock").val().trim())){
											  jp.error("库存只能为整数！");
											  return;
										}
										if(!$("#title").val()){
											jp.error("产品名称不能为空！");
											return;
										}
										var numTitle=0;
										$(".tripTitle").each(function(){
											var tripTitle=$(this).val();
											if(!tripTitle){
												numTitle=1;
											}
										})
										if(numTitle!=0){
											jp.error("行程标题不能为空！");
											return;
										} 
										jp.loading();
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

				$('#beginUpdateDate').datetimepicker({
					format : "YYYY-MM-DD"
				});
				
				$('#endUpdateDate').datetimepicker({
					format : "YYYY-MM-DD"
				});
				
				dataInit();
				
				//初始化新增标题编辑器
				$(".titleid").each(function(){
					UE.getEditor('content_'+$(this).val());//获得编辑器
				});
				
			});

	
	//时间初始化
	function dataInit(){
		$('.tripDate').datetimepicker({
			format : "HH:mm:ss"
		});
	}
	
	var ue = UE.getEditor('content');//获得编辑器
	var ue1 = UE.getEditor('content1');//获得编辑器
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == 'uploadimage' || action == 'uploadscrawl'
				|| action == 'uploadimage') {
			return '${ctx}/sys/sysUser/uploadIMG'; //远程图片上传controller
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}

	function reinitIframe() {
		var iframe = document.getElementById("frame_content1");
		try {
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.height = height;
		} catch (ex) {
		}
	}
	window.setInterval("reinitIframe()", 200);

	function reinitIframe2() {
		var iframe = document.getElementById("frame_content2");
		try {
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.height = height;
		} catch (ex) {
		}
	}
	window.setInterval("reinitIframe2()", 200);
	
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

	function uploadComplete(evt) {
		var textJson = JSON.parse(evt.target.responseText);
		if (textJson.success) {
			$("#img_" + divIndex).attr("src", src);
			$("#src_" + divIndex).val(textJson.body.filePath);
		}
		//	jp.info(textJson.msg);
	}

	function uploadFailed(evt) {
		jp.info("上传失败");
	}
	function uploadCanceled(evt) {
		jp.info("取消上传");
	}

	function addDiv() {
		divIndex = $('.img_style').length;
		if (divIndex > 0) {
			var fileValue = $("#src_" + divIndex).val(); //多个多图上传改class,id
			if (fileValue == null || fileValue == "" || fileValue == undefined) {
				return;
			}
		}
		divIndex++;
		var html = '<div class="dv_pic_item">'
				+ '<img src="" class="img_style" id="img_'+divIndex+'"/>'
				+ '<input type="hidden" name="imgSrc" value=""  id="src_'+divIndex+'" class="imgSrc"/>'
				+ '<input type="file" class="input_file_style" id="file_'+divIndex+'"/>'
				+ '<a id="btn_' + divIndex
				+ '" onclick="deleteElement(this)" class="deleteBtn">删除</a>'
				+ '</div>';
		$(".dv_pic_box").append(html);
	};

	function deleteElement(Obj) {
		Obj.parentNode.parentNode.removeChild(Obj.parentNode);
	}

	function addHtml(str,day,day2){
		str +='<div class="del_flag_content">'+
						'<div class="form-group">'+
				'<label class="col-sm-2 control-label" style="text-align: left;">选择时间：'+
							'</label>'+
							'<div class="col-sm-5">'+
								'<div class="col-xs-12 col-sm-5">'+
									'<div class="input-group date tripDate"style="left: -10px;">'+
										'<input type="text" name="routeContentList['+day+'].contentList['+day2+'].tripDate" class="form-control"/>'+
										'<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span></span>'+
									'</div>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="form-group">'+
							'<label class="col-sm-2 control-label" style="text-align: left;">行程类型<i style="color: red;">*</i>：</label>'+
							'<div class="col-sm-3">'+
								'<select class="form-control m-b" name="routeContentList['+day+'].contentList['+day2+'].tripType" >'+
									'<option value="1"  >吃饭</option>'+
									'<option value="2"  >旅游</option>'+
									'<option value="3"  >其他</option>'+
								'</select>'+
							'</div>'+
						'</div>'+
						'<div class="form-group">'+
							'<label class="col-sm-2 control-label" style="text-align: left;">行程景点<i style="color: red;"></i>：</label>'+
							'<div class="col-sm-3">'+
							 	// '<select class="form-control m-b tripScenic" name="routeContentList['+day+'].contentList['+day2+'].tripScenic">';
								// 	if(scenicSpotList != null && scenicSpotList.length>0){
								// 		for (x in scenicSpotList){
								// 			str += '<option value="'+scenicSpotList[x].id+'" label="'+scenicSpotList[x].name+'" />';
								// 		}
								// 	}
								// 	str += '</select> '+
				'<input name="routeContentList['+day+'].contentList['+day2+'].tripScenic" type="hidden"    htmlEscape="false" class="form-control"  />'+
				'<input   htmlEscape="false"  readonly="true"  class="form-control tripScenicNameClass"  />'+
							'</div>'+
							'<label class="col-sm-2 control-label">行程标题<i style="color: red;">*</i>：</label>'+
						'<div class="col-sm-3">'+
				'<input name="routeContentList['+day+'].contentList['+day2+'].title"  htmlEscape="false" class="form-control tripTitle" />'+
							'</div>'+
						'</div>'+
						'<div class="form-group">'+
							'<label class="col-sm-2 control-label" style="text-align: left;">行程详情 <i style="color: red;">*</i>：</label>'+
							'<div class="col-sm-10">'+
				'<textarea rows="5" name="routeContentList['+day+'].contentList['+day2+'].infor" class="form-control"></textarea>'+
							'</div>'+
						'</div>'+
						'<div class="form-group">'+
							'<label class="col-sm-2 control-label" style="text-align: left;">行程图片：</label>'+
							'<div class="col-sm-3">'+
				'<img class="img_style1" id="img1_'+day+'" src="" />'+
				'<input type="hidden"name="routeContentList['+day+'].contentList['+day2+'].img" value="" id="src1_'+day+'" />'+
				'<input type="file" class="input_file_style1" id="file1_'+day+'" />'+
							'</div>'+
				'</div><hr noshade="noshade" style="height:1px;"/>'+
						'</div>';
			return str;
	}
	
	
	var scenicSpotList;
	var scenicFlag;
	var tripScenicFlag = false;
	var cityType; //0出发城市 1目的城市
	var tripScenicNameClass;  //点击行程景点的当前对象

	$(function() {
		$(".dv_pic_box").on("change", ".input_file_style", function(e) {
			var target = document.getElementById($(this).attr("id"));
			if (!checkImg(target)) {
				return;
			}
			var fileId = $(this).attr("id");
			divIndex = fileId.split('_')[1];
			for (var i = 0; i < e.target.files.length; i++) {
				var file = e.target.files.item(i);
				//实例化FileReader API    
				var freader = new FileReader();
				freader.readAsDataURL(file);
				freader.onload = function(e) {
					src = e.target.result;
					var fd = new FormData();
					fd.append("attach", file);
					var xhr = new XMLHttpRequest();
					xhr.addEventListener("load", uploadComplete, false);
					xhr.addEventListener("error", uploadFailed, false);
					xhr.addEventListener("abort", uploadCanceled, false);
					xhr.open("POST", "${ctx}/sys/img/uploadFile");
					xhr.send(fd);
				}
			}
		})
		
		//上传行程图片
		$("#myTab3").on("change", ".input_file_style1", function(e) {
			var target = document.getElementById($(this).attr("id"));
			if (!checkImg(target)) {
				return;
			}
/* 			var fileId = $(this).attr("id");
			var index = fileId.split("_")[1]; */
			let this_ = $(this);
			for (var i = 0; i < e.target.files.length; i++) {
				var file = e.target.files.item(i);
				//实例化FileReader API    
				var freader = new FileReader();
				freader.readAsDataURL(file);
				freader.onload = function(e) {
					let src = e.target.result;
					let fd = new FormData();
					fd.append("attach", file);
					var xhr = new XMLHttpRequest();
					xhr.addEventListener("load", function(evt) { 
          				var textJson = JSON.parse(evt.target.responseText);
          				if(textJson.success){
          					this_.prev().val(textJson.body.filePath);
          					this_.prev().prev().attr("src",src);
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




    	var dateType = $("#dateType").val()-1;
		if(dateType>-1){
			$('#myTab li:eq('+dateType+') a').tab('show');
		}else{
			$("#dateType").val(1);
		}
    	$('#myTab li').click(function (e) {
    		var index = $(this).index();
    		$('#myTab li:eq('+index+') a').tab('show');
    		$("#dateType").val(index+1);
    		console.log(index+1);
    	})
    	
    	$('#myTab2 a:first').tab('show');
    	
	//添加天数
    	$("#addRouteContent").click(function(){
    		var day = $("#routeContentLength").val();
    		var dayMax = $("#dayNum").val();
    		console.log("day:"+day+"----------dayMax:"+dayMax);
    		if(dayMax == null || dayMax ==""){
    			jp.info("请查询填写行程天数");
    			return;
    		}
    		if(parseInt(day) >= parseInt(dayMax)){
    			jp.info("大于最大行程天数");
    			return;
    		}
			let index = parseInt(day) + 8;
    	//	var day2 = parseInt(day) + 1;
    		let day2 = 0;
			let html1 = '<li ><a data-toggle="tab" href="#tab-'+index+'">第'+(parseInt(day)+1)+'天</a></li>';
			let html2 =
					'<div class="tab-pane fade" id="tab-'+index+'">'+
					'<input  value="'+(parseInt(day)+1)+'" type="hidden" name="routeContentList['+day+'].dayCount"/>'+
					'<div class="form-group">'+
					'<label class="col-sm-2 control-label" style="text-align: left;">行程内容<i style="color: red;">*</i>：'+
					'</label>'+
					'<div class="col-sm-10">'+
					'<button type="button" class="btn btn-info addRouteContent" >添加行程</button>'+
					'<button type="button" class="btn btn-danger deleteRouteContent">删除最后一个行程</button>'+
					'</div>'+
					'</div>'+
					'<input  type="hidden"  value="1" readonly/>';

			html2 = addHtml(html2,day,day2)+'</div>';
    		 $("#myTab2").append(html1);
    		 $("#myTab3").append(html2); 
    		 $("#routeContentLength").val(parseInt(day)+1);
    	 	 dataInit(); 
    	})

    	//删除最后一天
    	$("#deleteRouteContent").click(function(){
    		var day = $("#routeContentLength").val();
			day --;
    		if(day > -1){
    			var index = parseInt(day) + 8;
    			$("#tab-"+index).remove();
    			$("#myTab2 li:last-child").remove();
    			console.log($("#myTab2 li:last-child"));
        		$("#routeContentLength").val(parseInt(day));
        		$('#myTab2 a:last').tab('show');
    		}
    	})

		//点击添加行程
 		$(".wrapper-content").on("click",".addRouteContent",function(){
 			const this_ = $(this);
 			let day = this_.parent().parent().prev().val();
			let day2 = this_.parent().parent().next().val();
			console.log(day+"-------------"+day2);
			this_.parent().parent().parent().append(addHtml("", parseInt(day)-1, day2));
			this_.parent().parent().next().val(parseInt(day2)+1);
			dataInit();
		})

		//删除行程
 		$(".wrapper-content").on("click",".deleteRouteContent",function(){
			if($(this).parent().parent().next().val() <= 1){
				jp.info("一天最少一个行程");
				return;
			}
			$(this).parent().parent().parent().find(".del_flag_content").last().remove()
			$(this).parent().parent().next().val(parseInt($(this).parent().parent().next().val()) - 1);
			console.log($(this).parent().parent().next().val());
		})




    	//获取标签
 	    $(".wrapper-content").on("click","#tagContent,#search1,#reset1",function(){
  	    	$("#tbody1").empty();
  	    	var language = $("#language option:selected").val();
  	    	if(language== null || language == "" || language == undefined){
  	    		jp.info("请选择语言");
  	    		return;
  	    	}
  	    	if( $(this).attr("id") == "reset1" || $(this).attr("id") == "tagContent" ){
  	    		$("#searchContent1").val("");
  	    	}
  	    	$('#myModal1').modal('show');
  	    	getTagDate();
    	}) 
    	
    	
    	//选择标签
     	$("#sure1").on("click",function(){
     					var ids = [];
     					var names = [];
                  $("#myModal1 input[type='checkbox']").each(function(i){   
                		if($(this).prop('checked')){
                			ids.push($(this).val());
                			names.push($(this).parent().next().text());
                		}
                  });
                  $("#labelAttrid").val(ids.join(","));
                  $("#tagContent").val(names.join(","));
			$("#myModal1").modal('hide');
		})
		

		
    	//获取城市
 	    $(".wrapper-content").on("click","#startCityContent,#endCityContent,#search2,#reset2",function(){
  	    	$("#tbody2").empty();
  	    	var language = $("#language option:selected").val();
  	    	if(language == null || language == "" || language == undefined){
  	    		jp.info("请选择语言");
  	    		return;
  	    	}
  	    	console.log($(this).attr("id"));
  	    	if( $(this).attr("id") == "reset2" || $(this).attr("id") == "startCityContent" || $(this).attr("id") == "endCityContent" ){
  	    		$("#searchContent2").val("");
  	  	    	if( $(this).attr("id") == "startCityContent" ){
  	  	    		cityType = 0;
  	  	    	}else if(  $(this).attr("id") == "endCityContent" ){
  	  	    		cityType = 1;
  	  	    	}
  	    	}
  	    	$('#myModal2').modal('show');
  	    	getCityDate();
    	})
    	
    	//选择城市
     	$("#sure2").on("click",function(){
     		var id = $("input[name='cityName']:checked").val();
     		var name = $("input[name='cityName']:checked").parent().next().text();
     		var id2 = $("#endCity").val();
     		if(cityType){  //目的城市
                $("#endCity").val(id);
                $("#endCityContent").val(name);
     		}else{  //出发城市
                $("#startCity").val(id);
                $("#startCityContent").val(name);
     		}
			$("#myModal2").modal('hide');
     		if(id !=  id2 && cityType){
     			getTripScenic();
                $("#scenic").val("");
                $("#scenicContent").val("");
     		}
		})
    	
		
    	//获取途径景点
 	    $(".wrapper-content").on("click","#addScenicContent,#search3,#reset3",function(){
  	    	$("#tbody3").empty();
  	    	var language = $("#language option:selected").val();
  	    	//var endCity = $("#endCity").val();
  	    	if(language== null || language == "" || language == undefined){
  	    		jp.info("请选择语言");
  	    		return;
  	    	}
  	    	/* if(endCity== null || endCity == "" || endCity == undefined){
  	    		jp.info("请选择城市");
  	    		return;
  	    	} */
  	    	if( $(this).attr("id") == "reset3" || $(this).attr("id") == "scenicContent" ){
  	    		$("#searchContent3").val("");
  	    	}
  	    	scenicFlag =1;
  	    	$('#myModal3').modal('show');
  	    	getScenic();
    	}) 
		
    	
    	//选择途径景点
     	$("#sure3").on("click",function(){
				//var ids = [];
				//var names = [];
	          //$("#myModal3 input[type='checkbox']").each(function(i){   
	        	//	if($(this).prop('checked')){
	        	//		ids.push($(this).val());
	        	//		names.push($(this).parent().next().text());
	        	//	}
	          //});
	          //$("#scenic").val(ids.join(","));
	          //$("#scenicContent").val(names.join(","));
	          var t='';
	          $("#myModal3 input[type='checkbox']").each(function(i){   
	        		if($(this).prop('checked')){
	        			t+='<tr class="secnicDiv"><td style="width:300px;"><input class="secnicSelect" name="scenic" type="hidden" value="';
	        			t+=$(this).val();
	        			t+='" >';
	        			t+=$(this).parent().next().text();
	        			t+='</td><td><button type="button" class="removeSecnic">删除</button>';
	        			t+='</td></tr>';
	        		}
	          });
	          $(".addScenicContent").append(t);
			  $("#myModal3").modal('hide');
	  	      $("#searchContent3").val("");
		})
		//删除选中的途经景点
		$(".addScenicContent").on("click",".removeSecnic",function(){
			$(this).parents(".secnicDiv").remove();
		})
		//获取景点数据
		getTripScenic();
		
		//语言改变
 	    $(".wrapper-content").on("change","#language",function(){
 	    	getTripScenic();
            $("#endCity").val("");
            $("#endCityContent").val("");
            $("#startCity").val("");
            $("#startCityContent").val("");
            $("#labelAttrid").val("");
            $("#tagContent").val("");
            $("#scenic").val("");
            $("#scenicContent").val("");
 	    	//修改iframe的url
/*  	    	$("#frame_content1").attr("src":"${ctx}/meiguotong/comconsult/comConsult?type="+${'#type'}.val()+3+"&typeId=+''+&languageId=${route.language}");
 	    	$("#frame_content2").attr("src":); */
    	})

		//点击行程景点
		$(".wrapper-content").on("click", ".tripScenicNameClass,#search4,#reset4", function(e) {
			// console.log($(this).val(0))
			scenicFlag = 1;
			if( $(this).attr("id") == "reset4"){
				$("#searchContent4").val("");
			}else if($(this).attr("id") != "search4"){
				tripScenicNameClass = $(this);
			}
			$('#myModal4').modal('show');
			//获取行程景点数据
			getScenicSpot();
		})



		//选择途径景点
		$("#sure4").on("click",function(){
			var id = $("input[name='tripScenicName']:checked").val();
			var name = $("input[name='tripScenicName']:checked").parent().next().text();
			console.log(name+"---------------------"+id);
			console.log(tripScenicNameClass.val()+"---------------------"+tripScenicNameClass.prev().val());
			tripScenicNameClass.val(name);
			tripScenicNameClass.prev().val(id);
			$('#myModal4').modal('hide');
		})
	})

	//获取行程景点数据
	 function getScenicSpot(){
		 $("#tbody4").empty();
		 jp.post("${ctx}/meiguotong/scenicspot/scenicSpot/getTripScenic",{
			 "languageId":$("#language option:selected").val(),
			 // "cityId":$("#endCity").val(),
			 "supplierId":$("#agentid").val(),
			 "scenicIds":tripScenicNameClass.prev().val(),
			 "scenicFlag":scenicFlag,
			 "name":$("#searchContent4").val()
		 },function(data){
			 if(data.success){
				 scenicFlag = 0;
				 scenicSpotList = data.body.scenicSpotList;
				 var html = "";
				 var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
				 var html2 = "";
				 var html3 = '<option value="" >请选择</option>';
				 for (x in scenicSpotList){
					 html += '<tr>';
					 html +='<td><input type="radio" name="tripScenicName"   value="'+scenicSpotList[x].id+'"';
					 if(scenicSpotList[x].checkFlag){html +=' checked="checked" ';}
					 html +='></td>';
					 html +='<td>'+scenicSpotList[x].name+'</td>';
					 html +='</tr>';

					 html2 += '<option value="'+scenicSpotList[x].id+'"  >'+scenicSpotList[x].name+'</option>';

				 }
				 $("#tbody4").empty();
				 $("#tbody4").append(html==""?html1:html);
			 }else{
				 jp.error(data.msg);
			 }
		 })
	 }

	//获取标签数据
	function getTagDate(){
		jp.post("${ctx}/meiguotong/comtag/comTag/getDate",{
			"languageId":$("#language option:selected").val(),
			"type":$("#type").val(),
			"content":$("#searchContent1").val(),
			"labelAttrid":$("#labelAttrid").val()
			},function(data){
			if(data.success){
					var list=data.body.comTagList;
					var html = "";
					var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
						for (x in list){
					  	html += '<tr>';
					  	html +='<td><input type="checkbox"   value="'+list[x].id+'"';
				  		if(list[x].checkFlag){html +=' checked="checked" ';}
					  	html +='></td>';
					  	html +='<td>'+list[x].content+'</td>';
					  	html +='</tr>';
						}
					$("#tbody1").empty();
					$("#tbody1").append(html==""?html1:html);
            }else{
	  			jp.error(data.msg);
            }
		}) 
	}
	
	//获取城市数据
	function getCityDate(){
		jp.post("${ctx}/meiguotong/comcity/comCity/getData",{
			"languageId":$("#language option:selected").val(),
			"endCity":cityType?$("#endCity").val():$("#startCity").val(),
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
		$(".tripScenic").empty();
		$("#tbody3").empty();
   		jp.post("${ctx}/meiguotong/scenicspot/scenicSpot/getTripScenic",{
   			"languageId":$("#language option:selected").val(),
   			"cityId":$("#endCity").val(),
   			"supplierId":$("#agentid").val(),
   			"scenicIds":$("#scenic").val(),
   			"scenicFlag":scenicFlag,
   			"name":$("#searchContent3").val()
   			},function(data){
   			if(data.success){
   					scenicFlag = 0;
					scenicSpotList = data.body.scenicSpotList;
					var html = "";
					var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
					var html2 = "";
					var html3 = '<option value="" >请选择</option>';
					for (x in scenicSpotList){
					  	html += '<tr>';
					  	html +='<td><input type="checkbox"   value="'+scenicSpotList[x].id+'"';
				  		if(scenicSpotList[x].checkFlag){html +=' checked="checked" ';}
					  	html +='></td>';
					  	html +='<td>'+scenicSpotList[x].name+'</td>';
					  	html +='</tr>';
					  	
						html2 += '<option value="'+scenicSpotList[x].id+'" >'+scenicSpotList[x].name+'</option>';
					  	
					}
					$("#tbody3").empty();
					$("#tbody3").append(html==""?html1:html);
					$(".tripScenic").append(html2==""?html3:html2);
					tripScenicInit();
               }else{
   	  			jp.error(data.msg);
               }
   		}) 
   	} 
    	//获取途径景点
    	function getScenic(){
			$("#tbody3").empty();
			var str='';
			var obj2 = document.getElementsByClassName("secnicDiv");
			if (obj2.length > 0) {
				for (i = 0; i < obj2.length; i++) {
					var id = obj2[i].id;
					var colorid = $(obj2[i]).find(
							".secnicSelect")
							.val();
					if (i == obj2.length - 1) {
						str += colorid;
					} else {
						str += colorid + ",";
					}
				}
			}
	   		jp.post("${ctx}/meiguotong/scenicspot/scenicSpot/getScenic",{
	   			"languageId":$("#language option:selected").val(),
	   			"supplierId":$("#agentid").val(),
	   			"scenicIds":str,
	   			"name":$("#searchContent3").val()
	   			},function(data){
	   			if(data.success){
						scenicSpotList = data.body.scenicSpotList;
						var html = "";
						var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
						for (x in scenicSpotList){
						  	html += '<tr>';
						  	html +='<td><input type="checkbox"   value="'+scenicSpotList[x].id+'"';
					  		if(scenicSpotList[x].checkFlag){html +=' checked="checked" ';}
						  	html +='></td>';
						  	html +='<td>'+scenicSpotList[x].name+'</td>';
						  	html +='</tr>';
						}
						$("#tbody3").empty();
						$("#tbody3").append(html==""?html1:html);
	               }else{
	   	  			jp.error(data.msg);
	               }
	   		}) 
	   	} 
	

	    //设置景点默认值
	    function tripScenicInit(){
   	 	 if(tripScenicFlag){
 	 		tripScenicFlag = true;
 	 		return;
 	 	 }
   	 	 $(".tripScenic").each(function(index,data){
   	 		 $(this).val($(this).prev().val());
   	 		 console.log($(this).prev().val());
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
							<a class="panelButton" href="${ctx}/meiguotong/product/route?type=${route.type}"><i
								class="ti-angle-left"></i> 返回</a>
						</h3>
					</div>

					<ul class="nav nav-tabs"
						style="padding-left: 130px; padding-top: 20px;">
						<li class="active"><a data-toggle="tab" href="#tab-1">通用</a></li>
						<li class=""><a data-toggle="tab" href="#tab-2">详细信息</a></li>
						<c:if test="${not empty route.id}">
							<li class=""><a data-toggle="tab" href="#tab-3">评价</a></li>
							<li class=""><a data-toggle="tab" href="#tab-4">用户咨询</a></li>
						</c:if>
						<c:if test="${not empty listTitle}">
							<c:forEach items="${listTitle}" var="title">
								<li class=""><a data-toggle="tab" href="#tab-${title.id}">${title.title}</a></li>
							</c:forEach>
						</c:if>
					</ul>
					
					<form:form id="inputForm" modelAttribute="route"
						action="${ctx}/meiguotong/product/route/save" method="post"
						class="form-horizontal">
						<form:hidden path="id" />
						<form:hidden path="dateType"/>
						<form:hidden path="agentid"/>
						<form:hidden path="type"/>
						<sys:message content="${message}" />
						<div class="panel-body tab-content">
						<div class="tab-pane fade in  active" id="tab-1">
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
						<label class="col-sm-2 control-label">
							<c:if test="${route.type==1}">常规路线图片：</c:if>
							<c:if test="${route.type==2}">当地参团图片：</c:if>
						</label>
							<div class="col-sm-10" style="border: 1px solid #cccccc;">
							<div class="dv_info_box">
								<div class="dv_pic_box">
								<c:if test="${not empty route.carImg}">
				<c:forEach var="img" items="${fn:split(route.carImg, ',')}" varStatus="status">
					<div class="dv_pic_item">
						<img src="${img}" class="img_style"	id="img_${status.index+1}" />
						<input type="hidden" name="imgSrc" value="${img}" id="src_${status.index+1}" class="imgSrc" /> 
						<input type="file" class="input_file_style" id="file_${status.index+1}" />
						<a id="btn_${status.index+1}" onclick="deleteElement(this)" class="deleteBtn">删除</a>
					</div>
				</c:forEach>
								</c:if>
							</div>
								<button type="button" class="btn_add_pic" onclick="addDiv()">添加图片</button>
								</div>
							</div>
						</div>
								<div class="form-group">
									<c:if test="${route.type==1}">
									<label class="col-sm-2 control-label">出发城市：</label>
									<div class="col-sm-3">
										<form:hidden path="startCity"/>
										<form:input path="startCityContent" readonly="true" class="form-control required"/>
									</div>
									</c:if>
									<label class="col-sm-2 control-label">目的地城市：</label>
									<div class="col-sm-3">
										<form:hidden path="endCity"/>
										<form:input path="endCityContent" readonly="true" class="form-control required"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">标签属性：</label>
									<div class="col-sm-4">
									<form:hidden path="labelAttrid"/>
									<form:input path="tagContent" readonly="true" class="form-control required"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">旅游天数：</label>
									<div class="col-sm-1">
										<form:input path="dayNum" htmlEscape="false"   class="form-control required" />
									</div>
									<label class="col-sm-1 control-label" style="text-align: left;">天</label>
									<div class="col-sm-1">
										<form:input path="nightNum" htmlEscape="false"
											class="form-control required" />
									</div>
									<label class="col-sm-1 control-label" style="text-align: left;">晚</label>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">提前天数：</label> <label
										class="col-sm-1 control-label">建议提前</label>
									<div class="col-sm-1">
										<form:input path="advanceDay" htmlEscape="false" class="form-control required" />
									</div>
									<label class="col-sm-1 control-label" style="text-align: left;">天</label>
									<label class="col-sm-1 control-label">市场价格</label>
									<div class="col-sm-1">
										<form:input path="price" htmlEscape="false" 
											class="form-control required" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">价格设置：</label>
									<div class="col-sm-10">
										<ul class="nav nav-tabs" id="myTab">
											<li class="active"><a data-toggle="tab" href="#tab-5">所有日期</a></li>
											<li class=""><a data-toggle="tab" href="#tab-6">按星期</a></li>
											<li class=""><a data-toggle="tab" href="#tab-7">按号数</a></li>
										</ul>
										<div class="panel-body tab-content">
											<div class="form-group">
												<label class="col-sm-1 control-label">日期范围：</label>
												<div class="col-xs-8">
													<div class="col-xs-12 col-sm-5">
														<div class='input-group date' id='beginUpdateDate'>
															<input type='text' name="beginDate"
																class="form-control required"
																value="<fmt:formatDate value="${route.beginDate}" pattern="yyyy-MM-dd"/>" />
															<span class="input-group-addon">
																<span class="glyphicon glyphicon-calendar"></span>
															</span>
														</div>
													</div>
													<div class="col-xs-12 col-sm-1">~</div>
													<div class="col-xs-12 col-sm-5">
														<div class='input-group date' id='endUpdateDate'
															style="left: -10px;">
															<input type='text' name="endDate"
																class="form-control required"
																value="<fmt:formatDate value="${route.endDate}" pattern="yyyy-MM-dd"/>" />
															<span class="input-group-addon"> <span
																class="glyphicon glyphicon-calendar"></span>
															</span>
														</div>
													</div>
												</div>
											</div>
											<div class="tab-pane fade in  active" id="tab-5"></div>
											<div class="tab-pane fade" id="tab-6">
												<div class="form-group">
													<label class="col-sm-1 control-label">日期范围：</label>
													<div class="col-sm-10">
									<c:forEach items="${weekDateList}" varStatus="status" var="a">
	<input type="checkbox" id="weekDate[${status.index}]" name="weekList" value="${a.id}" <c:if test="${fn:contains(route.weekDate,status.index+1)}">checked="checked"</c:if>  /></input>
									<label for="weekDate[${status.index}]">${a.name}</label>
														</c:forEach>
														
													</div>
												</div>
											</div>
											<div class="tab-pane fade" id="tab-7">
												<div class="form-group">
													<label class="col-sm-1 control-label">日期范围：</label>
													<div class="col-sm-10">
					<c:forEach items="${dayList}" varStatus="status" var="a">
		<input type="checkbox" id="dayDate[${status.index}]" name="dayList" value="${a.id}" <c:if test="${a.digFlag==1}">checked="checked"</c:if>  /></input>
					<label for="dayDate[${status.index}]">${a.name}</label>
														</c:forEach>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-1 control-label">报价：</label>
												<div class="col-sm-10">
													<c:if test="${userType==1}">
														<div class="form-group">
						<label class="col-sm-2 control-label"style="text-align: left;">单人房价：</label> 
						<label class="col-sm-1 control-label">价格</label>
								<div class="col-sm-2">
									<input value="${route.oneCost+route.oneProfit}" class="form-control" />
								</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="text-align: left;">双人房价：</label> <label
											class="col-sm-1 control-label">价格</label>
										<div class="col-sm-2">
											<input value="${route.twoCost+route.twoProfit}"
												class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="text-align: left;">三人房价：</label> <label
											class="col-sm-1 control-label">价格</label>
										<div class="col-sm-2">
											<input value="${route.threeCost+route.threeProfit}"
												class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="text-align: left;">四人房价：</label> <label
											class="col-sm-1 control-label">价格</label>
										<div class="col-sm-2">
											<input value="${route.fourCost+route.fourProfit}"
												class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											style="text-align: left;">配房价：</label> <label
											class="col-sm-1 control-label">价格</label>
										<div class="col-sm-2">
											<input value="${route.arrangeCost+route.arrangeProfit}"
												class="form-control" />
										</div>
									</div>
								</c:if>
						<c:if test="${userType==2}">
							<div class="form-group">
									<label class="col-sm-2 control-label" style="text-align: left;">单人房价：</label>
									 <label class="col-sm-1 control-label">成本</label>
									<div class="col-sm-2">
										<form:input path="oneCost" class="form-control required"/>
									</div>
									<label class="col-sm-1 control-label">+利润</label>
									<div class="col-sm-2">
										<form:input path="oneProfit" class="form-control required"/>
									</div>
									<div class="col-sm-2" style="color: red;">$ :${route.oneCost+route.oneProfit}</div>
							</div>
							<div class="form-group">
									<label class="col-sm-2 control-label" style="text-align: left;">双人房价：</label> 
									<label class="col-sm-1 control-label">成本</label>
									<div class="col-sm-2">
										<form:input path="twoCost" class="form-control required"/>
									</div>
									<label class="col-sm-1 control-label">+利润</label>
									<div class="col-sm-2">
										<form:input path="twoProfit" class="form-control required"/>
									</div>
									<div class="col-sm-2" style="color: red;">$ :${route.twoCost+route.twoProfit}</div>
							</div>
							<div class="form-group">
									<label class="col-sm-2 control-label" style="text-align: left;">三人房价：</label> 
									<label class="col-sm-1 control-label">成本</label>
									<div class="col-sm-2">
										<form:input path="threeCost" class="form-control required"/>
									</div>
									<label class="col-sm-1 control-label">+利润</label>
									<div class="col-sm-2">
										 <form:input path="threeProfit" class="form-control required"/>
									</div>
									<div class="col-sm-2" style="color: red;">$ :${route.threeCost+route.threeProfit}</div>
							</div>
							<div class="form-group">
									<label class="col-sm-2 control-label" style="text-align: left;">四人房价：</label>
									 <label class="col-sm-1 control-label">成本</label>
									<div class="col-sm-2">
										 <form:input path="fourCost" class="form-control required"/>
									</div>
									<label class="col-sm-1 control-label">+利润</label>
									<div class="col-sm-2">
										 <form:input path="fourProfit" class="form-control required"/>
									</div>
									<div class="col-sm-2" style="color: red;">$ :${route.fourCost+route.fourProfit}</div>
							</div>
							<div class="form-group"> 
							<label class="col-sm-2 control-label" style="text-align: left;">配房价：</label> 
							<label class="col-sm-1 control-label">成本</label>
									<div class="col-sm-2">
										 <form:input path="arrangeCost" class="form-control required"/>
									</div>
									<label class="col-sm-1 control-label">+利润</label>
									<div class="col-sm-2">
										 <form:input path="arrangeProfit" class="form-control required"/>
									</div>
									<div class="col-sm-2" style="color: red;">$ :${route.arrangeCost+route.arrangeProfit}</div>
							</div>
					</c:if>
												</div>
											</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">价格描述：</label>
								<div class="col-sm-10">
									<form:textarea path="priceInfo" rows="5" htmlEscape="false" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">单房差：</label>
								<div class="col-sm-4">
									<form:input path="houseOne" htmlEscape="false" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">库存：</label>
								<div class="col-sm-4">
									<form:input path="stock" htmlEscape="false"
										class="form-control required number"  placeholder="请输入库存数量，-1标识不限()" />
								</div>
							</div>
										</div>
									</div>
								</div>
							</div>
								<div class="tab-pane fade" id="tab-2">
									
									<div class="form-group">
										<label class="col-sm-2 control-label">
										<c:if test="${route.type==1}">常规路线名称</c:if> 
										<c:if test="${route.type==2}">当地参团名称</c:if>
										<i style="color: red;">*</i>：</label>
										<div class="col-sm-10">
											<form:input path="title" htmlEscape="false" class="form-control required" />
										</div>
									</div>
									<c:if test="${route.type==1}">
									<div class="form-group">
										<label class="col-sm-2 control-label">旅游副标题  <i style="color: red;">*</i>：</label>
										<div class="col-sm-10">
											<form:input path="subtitle" htmlEscape="false" class="form-control" />
										</div>
									</div>
									</c:if>
									<div class="form-group">
										<label class="col-sm-2 control-label">
										<%-- <c:if test="${route.type==1}">常规路线详情</c:if> 
										<c:if test="${route.type==2}">当地参团详情</c:if> --%>
										行程特色
										 <i style="color: red;">*</i>：</label>
										<div class="col-sm-10">
											<form:textarea path="infor" rows="5" htmlEscape="false"
												class="form-control " />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">产品经理推荐 <i style="color: red;">*</i>：</label>
										<div class="col-sm-10">
											<textarea id="content1" name="recommend" rows="55" cols="80"
												style="width: 100%; height: 300px">${route.recommend }</textarea>	
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">费用说明<i style="color: red;">*</i>：
										</label>
										<div class="col-sm-10">
											<form:textarea path="priceInfor" rows="5" htmlEscape="false"
												class="form-control " />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">
										备注：</label>
										<div class="col-sm-10">
											<form:textarea path="remark" rows="5" htmlEscape="false"
												class="form-control " />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">行程内容<i style="color: red;">*</i>：
										</label>
										<div class="col-sm-10">
											<button type="button" class="btn btn-info" id="addRouteContent">添加天数</button>
											<button type="button" class="btn btn-danger" id="deleteRouteContent">删除最后一天</button>
										</div>
									</div>
									<input type="hidden" id="routeContentLength" value="${fn:length(routeContentList)}"/>
									<div class="form-group">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<ul class="nav nav-tabs" id="myTab2" style="padding-left: 10px;" >
												<c:forEach items="${routeContentList}" var="routeContent" varStatus="status">
												<li ><a data-toggle="tab" href="#tab-${status.index+8}">第${routeContent.dayCount}天</a></li>
											</c:forEach>
											</ul>
							<div class="panel-body tab-content" id="myTab3">
								<c:forEach items="${routeContentList}" var="routeContentFlag" varStatus="status1">
									<div class="tab-pane fade" id="tab-${status1.index+8}">
									<input type="hidden"  value="${routeContentFlag.dayCount}" name="routeContentList[${status1.index}].dayCount"/>
									<div class="form-group">
										<label class="col-sm-2 control-label" style="text-align: left;">行程内容<i style="color: red;">*</i>：
										</label>
										<div class="col-sm-10">
											<button type="button" class="btn btn-info addRouteContent" >添加行程</button>
											<button type="button" class="btn btn-danger deleteRouteContent">删除最后一个行程</button>
										</div>
									</div>
									<input type="hidden"  value="${fn:length(routeContentFlag.contentList)}" readonly/>
									<c:forEach items="${routeContentFlag.contentList}"  var="routeContent" varStatus="status2">
										<div class="del_flag_content">
												<div class="form-group">
											<label class="col-sm-2 control-label" style="text-align: left;">选择时间：</label>
													<div class="col-sm-5">
														<div class="col-xs-12 col-sm-5">
															<div class='input-group date tripDate' style="left: -10px;">
																<input type='text' name="routeContentList[${status1.index}].contentList[${status2.index}].tripDate"
																	class="form-control"
																	value="${routeContent.tripDate}" />
																<span class="input-group-addon"> <span
																	class="glyphicon glyphicon-calendar"></span>
																</span>
															</div>
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label"
														style="text-align: left;">行程类型<i
														style="color: red;">*</i>：
													</label>
													<div class="col-sm-3">
														<select class="form-control m-b" name="routeContentList[${status1.index}].contentList[${status2.index}].tripType" >
															<option value="1" label="吃饭"
																<c:if test="${routeContent.tripType==1}"> selected="selected" </c:if> >吃饭</option>
															<option value="2" label="旅游"
																<c:if test="${routeContent.tripType==2}"> selected="selected" </c:if> >旅游</option>
															<option value="3" label="其他"
																<c:if test="${routeContent.tripType==3}"> selected="selected" </c:if> >其他</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label"
														style="text-align: left;">行程景点<i
														style="color: red;"></i>：
													</label>
													<div class="col-sm-3">
														<%--<input type="hidden" value="${routeContent.tripScenic}" id="tripScenic_${status1.index}"/>--%>
														<%--${routeContent.scenicName}--%>
														<%--<select class="form-control m-b tripScenic" --%>
																<%--name="routeContentList[${status1.index}].contentList[${status2.index}].tripScenic">--%>
														<%--</select>--%>
														<input name="routeContentList[${status1.index}].contentList[${status2.index}].tripScenic" type="hidden"
															   htmlEscape="false" class="form-control"  value="${routeContent.tripScenic}" />
														<input   htmlEscape="false" value="${routeContent.scenicName}"  readonly="true"  class="form-control tripScenicNameClass"  />

													</div>
													<label class="col-sm-2 control-label">行程标题<i style="color: red;">*</i>：</label>
													<div class="col-sm-3">
							<input name="routeContentList[${status1.index}].contentList[${status2.index}].title" value="${routeContent.title}" htmlEscape="false" class="form-control tripTitle" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label"
														style="text-align: left;">行程详情 <i style="color: red;">*</i>：</label>
													<div class="col-sm-10">
						<textarea rows="5" name="routeContentList[${status1.index}].contentList[${status2.index}].infor" class="form-control">${routeContent.infor}</textarea>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label" style="text-align: left;">行程图片：</label>
													<div class="col-sm-3">
									<img  class="img_style1" id="img1_${status1.index}__${status2.index}" src="${routeContent.img}" />
									<input type="hidden"name="routeContentList[${status1.index}].contentList[${status2.index}].img" value="${routeContent.img}" id="src1_${status1.index}_${status2.index}" /> 
									<input type="file" class="input_file_style1" id="file1_${status1.index}_${status2.index}" />
													</div>
												</div>
										<hr noshade="noshade" style="height:1px;" />
											</div>

									</c:forEach>
									</div>
								</c:forEach>
							</div>
						</div>
									</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">途径景点：
									</label>
									<div class="col-sm-10">
										<%-- <form:hidden path="scenic"/>
										<form:input path="scenicContent" htmlEscape="false" class="form-control"  readonly="true"/> --%>
										<button type="button" id="addScenicContent">添加</button>
										<br/>
										<br/>
										<table class="addScenicContent">
											<c:if test="${not empty scenicList}">
												<c:forEach items="${scenicList}" var="scenic">
													<tr class="secnicDiv">
														<td style="width:300px;">
															<input class="secnicSelect" name="scenic" type="hidden" value="${scenic.id}" >
									        				${scenic.name}
									        			</td>
								        				<td>
								        				<button type="button" class="removeSecnic">删除</button>
								        				</td>
								        			</tr>
												</c:forEach>
											</c:if>
										</table>
									</div>
								</div>
								<br/>
								<br/>
								<br/>
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label">视频资料<i style="color: red;">*</i>：
										</label>
										<div class="col-sm-10">

												<form:textarea path="recommend" htmlEscape="false"
											class="form-control " />
											<textarea id="content" name="content" rows="55" cols="80"
												style="width: 100%; height: 300px">${route.content }</textarea>

										</div>
									</div> --%>
								</div>
								<div class="tab-pane fade" id="tab-3">
									<iframe 
									<c:if test="${route.type == 1}">
									src="${ctx}/meiguotong/comcomment/comComment/goComment?type=4&typeId=${route.id}"
									</c:if>
									<c:if test="${route.type == 2}">
									src="${ctx}/meiguotong/comcomment/comComment/goComment?type=5&typeId=${route.id}"
									</c:if>
										scrolling="no" frameborder="0" id="frame_content1"
										style="width: 100%;"></iframe>
								</div>
								<div class="tab-pane fade" id="tab-4">
									
									<iframe 
									<c:if test="${route.type == 1}">
									src="${ctx}/meiguotong/comconsult/comConsult?type=4&typeId=${route.id}&languageId=${route.language}"
									</c:if>
									<c:if test="${route.type == 2}">
									src="${ctx}/meiguotong/comconsult/comConsult?type=5&typeId=${route.id}&languageId=${route.language}"
									</c:if>
										scrolling="no" frameborder="0" id="frame_content2"
										style="width: 100%;"></iframe>
								</div>
								<c:if test="${not empty listTitle}">
									<c:forEach items="${listTitle}" var="title" varStatus="sta">
										<div class="tab-pane fade" id="tab-${title.id}">
											<label class="col-sm-2 control-label">${title.title}：
											</label>
											<div class="col-sm-10">
											<input type="hidden" class="titleid" value="${title.id}"> 
											<input type="hidden" name="settingTitleProList[${sta.index}].titleid" value="${title.id}"> 
											<input type="hidden" name="settingTitleProList[${sta.index}].id" value="${title.titleProid}"> 
											<textarea id="content_${title.id}" name="settingTitleProList[${sta.index}].content" rows="55" cols="80"
												style="width: 100%; height: 300px">${title.content}</textarea>
											</div>
										</div>
									</c:forEach>
								</c:if>
						</div>

						<c:if test="${userType==2}"> 
							<div class="col-lg-3"></div>
							<div class="col-lg-6">
								<div class="form-group text-center">
									<div>
										<button class="btn btn-primary btn-block btn-lg btn-parsley"
											data-loading-text="正在提交...">提 交</button>
									</div>
								</div>
							</div>
					</c:if> 
					</form:form>
				</div>
			</div>
		</div>
		
		
<!-- 标签模态框（Modal） -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">标签属性</h4>
            </div>
            <div class="modal-body">
	      <div>
	      	<input id="searchContent1" placeholder="输入标签属性名称" class="form-control col-sm-2" style="width:40%;margin-right:15px;"/>
	      	 <a  id="search1" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
	      	 <a  id="reset1" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
	      </div>
        <br/>
		<table class="table table-bordered">
					 <thead>
					    <tr>
					        <th> </th>
					       	<th>属性名称</th>
					    </tr>
					    </thead>
					    <tbody id="tbody1">
						  	<tr>
						  		<td><input type="checkbox"   class="name" value="1"></td>
						  		<td>666666</td>
						  	</tr>
					    </tbody>
					    
		</table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sure1">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
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
					    <tbody id="tbody2"></tbody>
					    
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
                <h4 class="modal-title" id="myModalLabel">添加途径景点</h4>
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
					        <th></th>
					       	<th>景点名称</th>
					    </tr>
					    </thead>
					    <tbody id="tbody3"></tbody>
					    
		</table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sure3">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 行程景点模态框（Modal） -->
<div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">添加行程景点</h4>
			</div>
			<div class="modal-body">
				<div>
					<input id="searchContent4" placeholder="输入景点名称" class="form-control col-sm-2" style="width:40%;margin-right:15px;"/>
					<a  id="search4" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
					<a  id="reset4" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
				</div>
				<br/>
				<table class="table table-bordered">
					<thead>
					<tr>
						<th> </th>
						<th>景点名称</th>
					</tr>
					</thead>
					<tbody id="tbody4"></tbody>

				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="sure4">提交更改</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>


	</div>
</body>
</html>