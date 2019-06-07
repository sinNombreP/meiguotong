<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>租车管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					
					if(!$("#carName").val()){
						jp.error("汽车名称不能为空！");
						return;
					}
					/* if(!$("#carTrait").val()){
						jp.error("汽车卖点不能为空！");
						return;
					} */
					if(!$("#cityName").val()){
						jp.error("所在城市不能为空！");
						return;
					}
					var num=0;
					$("input[name='carImg']").each(function(){
						if(!$(this).val()){
							num=1;
						}
					})
					if(num!=0){
						jp.error("汽车图片不能为空！");
						return;
					}
					if(!$("input[name='carVideo']").val()){
						jp.error("汽车视频不能为空！");
						return;
					}
					if(!$("#seatNum").val()){
						jp.error("汽车座位数不能为空！");
						return;
					}
					if(!/^0$|^[1-9]\d*$/.test($("#seatNum").val().trim())){
						  jp.error("汽车座位数只能为整数！");
						  return;
					}
					if(!$("#adviseNum").val()){
						jp.error("汽车建议乘坐人数不能为空！");
						return;
					}
					if(!/^0$|^[1-9]\d*$/.test($("#adviseNum").val().trim())){
						  jp.error("汽车建议乘坐人数只能为整数！");
						  return;
					}
					if(!$("#bagNum").val()){
						jp.error("行李数不能为空！");
						return;
					}
					if(!/^0$|^[1-9]\d*$/.test($("#bagNum").val().trim())){
						  jp.error("行李数只能为整数！");
						  return;
					}
					if(!$("#useYear").val()){
						jp.error("汽车使用年限不能为空！");
						return;
					}
					if(!/^0$|^[1-9]\d*$/.test($("#useYear").val().trim())){
						  jp.error("汽车使用年限只能为整数！");
						  return;
					}
					var serviceNumD1=0;
					var serviceNumD2=0;
					var serviceNumD3=0;
					var serviceNumD4=0;
					var serviceNumD5=0;
					var serviceNumD6=0;
					var serviceNumD7=0;
					var serviceNumD8=0;
					var serviceNumD9=0;
					var serviceNumD10=0;
					var serviceNumD11=0;
					var serviceNumD12=0;
					var serviceNumD13=0;
					var serviceNumD14=0;
					var serviceNumD15=0;
					var serviceNumD16=0;
					var serviceNumD17=0;
					var serviceNumD18=0;
					var serviceNumD19=0;
					var serviceNumD20=0;
					var serviceNum1=0;
					var serviceNum2=0;
					var serviceNum3=0;
					var serviceNum4=0;
					var serviceNum5=0;
					var serviceNum6=0;
					var serviceNum7=0;
					var serviceNum8=0;
					var serviceNum9=0;
					var serviceNum10=0;
					var serviceNum11=0;
					var serviceNum12=0;
					var serviceNum13=0;
					var serviceNum14=0;
					var serviceNum15=0;
					var serviceNum16=0;
					var serviceNum17=0;
					var serviceNum18=0;
					var serviceNum19=0;
					var serviceNum20=0;
					var dateNumB5=0;
					var dateNumE5=0;
					var dateNumB6=0;
					var dateNumE6=0;
					var dateNumB7=0;
					var dateNumE7=0;
					$(".car_tab").each(function(){
			    		if($(this).find(".car_status").is(":checked")){
			    			if($(this).attr("id")=="tab-4"){
			    				$(this).find(".car_service").each(function(){
			    					if(!$(this).find(".service_price").val()){
				    					serviceNum1=1;
			    					}
			    					if(!/^[0-9,.]*$/.test($(this).find(".service_price").val().trim())){
			    						serviceNumD1=1;
									} 
			    					if(!$(this).find(".service_startPrice").val()){
			    						serviceNum2=1;
			    					}
			    					if(!/^[0-9,.]*$/.test($(this).find(".service_startPrice").val().trim())){
			    						serviceNumD2=1;
									} 
			    					if(!$(this).find(".service_headPrice").val()){
			    						serviceNum3=1;
			    					}
			    					if(!/^[0-9,.]*$/.test($(this).find(".service_headPrice").val().trim())){
			    						serviceNumD3=1;
									} 
			    					if(!$(this).find(".service_foodPrice").val()){
			    						serviceNum4=1;
			    					}
			    					if(!/^[0-9,.]*$/.test($(this).find(".service_foodPrice").val().trim())){
			    						serviceNumD4=1;
									} 
			    					if(!$(this).find(".service_emptyPrice").val()){
			    						serviceNum5=1;
			    					}
			    					if(!/^[0-9,.]*$/.test($(this).find(".service_emptyPrice").val().trim())){
			    						serviceNumD5=1;
									} 
			    				})
			    			}else if($(this).attr("id")=="tab-5"){
			    				if(!$(this).find(".car_beginDate").val()){
			    					dateNumB5=1;
		    					}
			    				if(!$(this).find(".car_endDate").val()){
			    					dateNumE5=1;
		    					}
			    				if(!$(this).find(".car_price").val()){
			    					serviceNum6=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_price").val().trim())){
		    						serviceNumD6=1;
								} 
		    					if(!$(this).find(".car_startPrice").val()){
		    						serviceNum7=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_startPrice").val().trim())){
		    						serviceNumD7=1;
								} 
		    					if(!$(this).find(".car_headPrice").val()){
		    						serviceNum8=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_headPrice").val().trim())){
		    						serviceNumD8=1;
								} 
		    					if(!$(this).find(".car_foodPrice").val()){
		    						serviceNum9=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_foodPrice").val().trim())){
		    						serviceNumD9=1;
								} 
		    					if(!$(this).find(".car_emptyPrice").val()){
		    						serviceNum10=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_emptyPrice").val().trim())){
		    						serviceNumD10=1;
								} 
			    			}else if($(this).attr("id")=="tab-6"){
			    				if(!$(this).find(".car_beginDate").val()){
			    					dateNumB6=1;
		    					}
			    				if(!$(this).find(".car_endDate").val()){
			    					dateNumE6=1;
		    					}
			    				if(!$(this).find(".car_price").val()){
			    					serviceNum11=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_price").val().trim())){
		    						serviceNumD11=1;
								} 
		    					if(!$(this).find(".car_startPrice").val()){
		    						serviceNum12=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_startPrice").val().trim())){
		    						serviceNumD12=1;
								} 
		    					if(!$(this).find(".car_headPrice").val()){
		    						serviceNum13=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_headPrice").val().trim())){
		    						serviceNumD13=1;
								} 
		    					if(!$(this).find(".car_foodPrice").val()){
		    						serviceNum14=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_foodPrice").val().trim())){
		    						serviceNumD14=1;
								}
		    					/* if(!$(this).find(".car_emptyPrice").val()){
		    						serviceNum15=1;
		    					} 
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_emptyPrice").val().trim())){
	    							serviceNumD15=1;
								}*/
			    			}else if($(this).attr("id")=="tab-7"){
			    				if(!$(this).find(".car_beginDate").val()){
			    					dateNumB7=1;
		    					}
			    				if(!$(this).find(".car_endDate").val()){
			    					dateNumE7=1;
		    					}
			    				if(!$(this).find(".car_price").val()){
			    					serviceNum16=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_price").val().trim())){
		    						serviceNumD16=1;
								}
		    					if(!$(this).find(".car_startPrice").val()){
		    						serviceNum17=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_startPrice").val().trim())){
		    						serviceNumD17=1;
								}
		    					if(!$(this).find(".car_headPrice").val()){
		    						serviceNum18=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_headPrice").val().trim())){
		    						serviceNumD18=1;
								}
		    					if(!$(this).find(".car_foodPrice").val()){
		    						serviceNum19=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_foodPrice").val().trim())){
		    						serviceNumD19=1;
								}
		    					if(!$(this).find(".car_emptyPrice").val()){
		    						serviceNum20=1;
		    					}
		    					if(!/^[0-9,.]*$/.test($(this).find(".car_emptyPrice").val().trim())){
		    						serviceNumD20=1;
								}
			    			}
			    			
			    		} 
			    	})
			    	if(dateNumB5!=0){
						jp.error("短程接送日期范围不能为空！");
						return;
			    	}
					if(dateNumE5!=0){
						jp.error("短程接送日期范围不能为空！");
						return;
			    	}
					if(dateNumB6!=0){
						jp.error("接送机日期范围不能为空！");
						return;
			    	}
					if(dateNumE6!=0){
						jp.error("接送机日期范围不能为空！");
						return;
			    	}
			    	if(dateNumB7!=0){
						jp.error("定制租车日期范围不能为空！");
						return;
			    	}
					if(dateNumE7!=0){
						jp.error("定制租车日期范围不能为空！");
						return;
			    	}
			    	if(serviceNum1!=0){
						jp.error("业务价格不能为空！");
						return;
			    	}
			    	if(serviceNumD1!=0){
						jp.error("请输入正确的业务价格！");
						return;
			    	}
					if(serviceNum2!=0){
						jp.error("业务起价小费不能为空！");
						return;   		
					}
					if(serviceNumD2!=0){
						jp.error("请输入正确的业务起价小费！");
						return;
			    	}
					if(serviceNum3!=0){
						jp.error("业务人头小费不能为空！");
						return;
					}
					if(serviceNumD3!=0){
						jp.error("请输入正确的业务人头小费！");
						return;
			    	}
					if(serviceNum4!=0){
						jp.error("业务餐补费用不能为空！");
						return;
					}
					if(serviceNumD4!=0){
						jp.error("请输入正确的业务餐补费用！");
						return;
			    	}
					if(serviceNum5!=0){
						jp.error("业务空车返回费用不能为空！");
						return;
					}
					if(serviceNumD5!=0){
						jp.error("请输入正确的业务空车返回费用！");
						return;
			    	}
					if(serviceNum6!=0){
						jp.error("短程接送价格不能为空！");
						return;
			    	}
					if(serviceNumD6!=0){
						jp.error("请输入正确的短程接送价格！");
						return;
			    	}
					if(serviceNum7!=0){
						jp.error("短程接送起价小费不能为空！");
						return;   		
					}
					if(serviceNumD7!=0){
						jp.error("请输入正确的短程接送起价小费！");
						return;
			    	}
					if(serviceNum8!=0){
						jp.error("短程接送人头小费不能为空！");
						return;
					}
					if(serviceNumD8!=0){
						jp.error("请输入正确的短程接送人头小费！");
						return;
			    	}
					if(serviceNum9!=0){
						jp.error("短程接送餐补费用不能为空！");
						return;
					}
					if(serviceNumD9!=0){
						jp.error("请输入正确的短程接送餐补费用！");
						return;
			    	}
					if(serviceNum10!=0){
						jp.error("短程接送空车返回费用不能为空！");
						return;
					}
					if(serviceNumD10!=0){
						jp.error("请输入正确的短程接送空车返回费用！");
						return;
			    	}
					if(serviceNum11!=0){
						jp.error("接送机价格不能为空！");
						return;
			    	}
					if(serviceNumD11!=0){
						jp.error("请输入正确的接送机价格！");
						return;
			    	}
					if(serviceNum12!=0){
						jp.error("接送机起价小费不能为空！");
						return;   		
					}
					if(serviceNumD12!=0){
						jp.error("请输入正确的接送机起价小费！");
						return;
			    	}
					if(serviceNum13!=0){
						jp.error("接送机人头小费不能为空！");
						return;
					}
					if(serviceNumD13!=0){
						jp.error("请输入正确的接送机人头小费！");
						return;
			    	}
					if(serviceNum14!=0){
						jp.error("接送机餐补费用不能为空！");
						return;
					}
					if(serviceNumD14!=0){
						jp.error("请输入正确的接送机餐补费用！");
						return;
			    	}
					/* if(serviceNum15!=0){
						jp.error("接送机空车返回费用不能为空！");
						return;
					} 
					if(serviceNumD15!=0){
						jp.error("请输入正确的接送机空车返回费用！");
						return;
			    	}
					*/
					if(serviceNum16!=0){
						jp.error("定制价格不能为空！");
						return;
			    	}
					if(serviceNumD16!=0){
						jp.error("请输入正确的定制价格！");
						return;
			    	}
					if(serviceNum17!=0){
						jp.error("定制起价小费不能为空！");
						return;   		
					}
					if(serviceNumD17!=0){
						jp.error("请输入正确的定制起价小费！");
						return;
			    	}
					if(serviceNum18!=0){
						jp.error("定制人头小费不能为空！");
						return;
					}
					if(serviceNumD18!=0){
						jp.error("请输入正确的定制人头小费！");
						return;
			    	}
					if(serviceNum19!=0){
						jp.error("定制餐补费用不能为空！");
						return;
					}
					if(serviceNumD19!=0){
						jp.error("请输入正确的定制餐补费用！");
						return;
			    	}
					if(serviceNum20!=0){
						jp.error("定制空车返回费用不能为空！");
						return;
					}
					if(serviceNumD20!=0){
						jp.error("请输入正确的定制空车返回费用！");
						return;
			    	}
					jp.loading();
               		$(".hourListListClick label:gt(0)").each( (index,element)=> {
               			$($(element).children("input").get(0)).prop("disabled",false);
            		})
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
		
		var fileType; //文件类型 1 图片 2视频
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
			if (size > 2000 && fileType == 1) {
				jp.info("图片不能大于2M");
				target.value = "";
				return false
			}
			if (size > 100000 && fileType == 2) {
				jp.info("视频不能大于100M");
				target.value = "";
				return false
			}
			var name = target.value;
			var fileName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			var videoType = ",mp4,wmv,asf,asx,,3gp,rm,rmvb,mov,m4v,";
			var flag = ","+fileName+",";
 			if (fileName != "jpg" && fileName != "gif" && fileName != "png" && fileType == 1) {
				jp.info("请选择jpg、gif、png格式上传！");
				target.value = "";
				return false
			}else if(videoType.indexOf(flag) == -1 && fileType == 2){
				jp.info("请选择正确的文件格式");
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
			//图片视频上传
			$(".wrapper").on("change", ".input_file_style,#carVideo", function(e) {
				fileType = 1;
				if($(this).attr("id") == "carVideo"){
					fileType = 2;
				}
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
					jp.loading();
					freader.onload = function(e) {
						var src = e.target.result;
						var fd = new FormData();
						fd.append("attach", file);
						var xhr = new XMLHttpRequest();
						xhr.addEventListener("load", function(evt) { 
	          				var textJson = JSON.parse(evt.target.responseText);
	          				if(textJson.success){
	          					img.prev().val(textJson.body.filePath);
	          					if(fileType == 1){
	          						img.prev().prev().attr("src",src);
	          					}else{
	          						img.prev().prev().attr("src",textJson.body.filePath);
	          						img.prev().val(textJson.body.filePath);
	          					}
	          				} 
	          				jp.success(textJson.msg);
	          			}, false);
						xhr.addEventListener("error", uploadFailed, false);
						xhr.addEventListener("abort", uploadCanceled, false);
						xhr.open("POST", "${ctx}/sys/img/uploadVideo");
						xhr.send(fd);
					}
				}
			})
			
    	//获取城市
 	    $(".wrapper-content").on("click","#cityName,#search2,#reset2",function(){
  	    	var language = $("#language option:selected").val();
  	    	if(language == null || language == "" || language == undefined){
  	    		jp.info("请选择语言");
  	    		return;
  	    	}
  	    	if( $(this).attr("id") == "reset2" || $(this).attr("id") == "cityName" ){
  	    		$("#searchContent2").val("");
  	    	}
  	    	$('#myModal2').modal('show');
  	    	getCityData();
    	})
    	
    	//选择城市
     	$("#sure2").on("click",function(){
     		var id = $("input[name='cityName']:checked").val();
     		var name = $("input[name='cityName']:checked").parent().next().text();
     		var id2 = $("#endCity").val();
             $("#city").val(id);
             $("#cityName").val(name);
			$("#myModal2").modal('hide');
		})
			
		//语言改变
 	    $(".wrapper-content").on("change","#language",function(){
 	    	getTripScenic();
            $("#city").val("");
            $("#cityName").val("");
    	})
    	
			//时间初始化
    		dataInit();
			//tab初始化
			dateTypeInit();
			
			//tab改变
			$(".businessType li a").click(function(){
				$("#dateType-"+($(this).attr("href").split("-")[2])).val(parseInt($(this).parent().index())+1);
			});
			
		
		//获取标题
 	    $(".wrapper-content").on("click","#addTitle,#search1,#reset1",function(){
  	    	var language = $("#language option:selected").val();
  	    	if(language == null || language == "" || language == undefined){
  	    		jp.info("请选择语言");
  	    		return;
  	    	}
  	    	if( $(this).attr("id") != "search1"){
  	    		$("#searchContent1").val("");
  	    	}
			$('#myModal1').modal('show');
			getTitleData();
		});
		
    	//选择标题
     	$("#sure1").on("click",function(){
     		let id = $("input[name='titleName']:checked").val();
     		let name = $("input[name='titleName']:checked").parent().next().text();
     		let range = $("input[name='titleName']:checked").parent().next().next().text();
     		let radius = $("input[name='titleName']:checked").parent().next().next().next().text();
     		if(!id || !name){
     			jp.info("至少选择一条");
     			return;
     		}
     		var ids = $("#titleids").val()==""?[]: $("#titleids").val().split(",");
     		if(ids.indexOf(id) >= 0){
     			jp.info("已选择此标题");
     			return;
     		}
     		$("#myModal1").modal('hide');
     		ids.push(id);
     		let index = $("#carTimePriceListLength").val();
     		let html = '<li><a data-toggle="tab" href="#tab_'+index+'">'+name+'</a></li>';
     		let html2 =
     			'<div class="tab-pane fade car_service" id="tab_'+index+'">'+
     			'<input  id="serviceid_'+index+'" type="hidden" name="carBusinessList[0].carTimePriceList['+index+'].serviceid" value="'+id+'"/>'+
						'<div class="col-sm-10" >'+
							'<div class="form-group">'+
								'<label class="col-sm-2 control-label">范围设置：</label>'+
								'<div class="col-sm-3">'+range
									// '<input type="radio" name="carBusinessList[0].carTimePriceList['+index+'].range" value="1"/>接机'+
									// '<input type="radio" name="carBusinessList[0].carTimePriceList['+index+'].range" value="2"/>附近城市'+
									// '<input type="radio" name="carBusinessList[0].carTimePriceList['+index+'].range" value="3"/>距离半径'+
								+'</div>'+
								'<div class="col-sm-3">'+
									radius +"KM"+
								'</div>'+
							'</div>'+
							'<div class="form-group">'+
								'<label class="col-sm-2 control-label">价格/次：</label>'+
								'<div class="col-sm-5">'+
									'<input name="carBusinessList[0].carTimePriceList['+index+'].price" class="form-control required number service_price" value="" placeholder="请输入价格"/>'+
								'</div>'+
							'</div>'+
							'<div class="form-group">'+
								'<label class="col-sm-2 control-label">起价小费：</label>'+
								'<div class="col-sm-5">'+
									'<input name="carBusinessList[0].carTimePriceList['+index+'].startPrice" class="form-control required number service_startPrice" value="" placeholder="请输入价格，人数未达标准时固定计算小费"/>'+
								'</div>'+
							'</div>'+
							'<div class="form-group">'+
								'<label class="col-sm-2 control-label">人头小费：</label>'+
								'<div class="col-sm-5">'+
									'<input name="carBusinessList[0].carTimePriceList['+index+'].headPrice" class="form-control required number service_headPrice" value="" placeholder="请输入价格，人数达到标准来计算小费"/>'+
								'</div>'+
							'</div>'+
							'<div class="form-group">'+
								'<label class="col-sm-2 control-label">餐补：</label>'+
								'<div class="col-sm-5">'+
									'<input name="carBusinessList[0].carTimePriceList['+index+'].foodPrice" class="form-control required number service_foodPrice" value="" placeholder="请输入价格"/>'+
								'</div>'+
							'</div>'+
							'<div class="form-group">'+
								'<label class="col-sm-2 control-label">空车返回：</label>'+
								'<div class="col-sm-5">'+
									'<input name="carBusinessList[0].carTimePriceList['+index+'].emptyPrice" class="form-control required number service_emptyPrice" value="" placeholder="请输入价格/km"/>'+
								'</div>'+
							'</div>'+
						'</div>'+	
					'</div>';
		     $("#titleids").val(ids.join(","));
		     $("#carTimePriceListLength").val(parseInt(index)+1);
     		$("#myTab-0").append(html);
     		$("#myTab-0-content").append(html2);
		})
		
		//删除标题
		$("#deleteTitle").click(function(){
			var length = $("#carTimePriceListLength").val();
			var ids = $("#titleids").val();
			if(!length || !ids){
				return;
			}
			$("#tab_"+(parseInt(length)-1)).remove();
			$("#myTab-0 li:last-child").remove();
			$("#carTimePriceListLength").val(parseInt(length)-1);
			var titleids = ids.split(",");
			titleids.pop();
			 $("#titleids").val(titleids.join(","));
		});
		
    	
    	$(".hourListListClick").on("change","input",function(){
    		console.log($(this).parent().index());
    		console.log($(this).parent().parent().prop("id"));
    		console.log($(this).prop("checked"));
    		
    		let index = $(this).parent().index();
    		let id = $(this).parent().parent().prop("id");
    		if($(this).prop("checked")){  //选中
    			if(index == 0){
               		$("#"+id+" label:gt(0)").each( (index,element)=> {
               			$($(element).children("input").get(0)).prop("checked",false);
               			$($(element).children("input").get(0)).prop("disabled",false);
            		})
    			}else{
               		$("#"+id+" label:lt("+index+")").each( (index,element)=> {
               			$($(element).children("input").get(0)).prop("disabled",true);
            		})
            		$($("#"+id+" label:eq(0)").children("input").get(0)).prop("checked",false);
            		$($("#"+id+" label:eq(0)").children("input").get(0)).prop("disabled",false);
    			}
    		}else if(index != 0){  //不是全部的取消
    			let flag = 0;
         		$("#"+id+" label:lt("+index+")").each( (item,element)=> {
         			$($(element).children("input").get(0)).prop("disabled",false);
           			if($($(element).children("input").get(0)).prop("checked")){
           				flag = item;
           			}
        		}) 
         		if(flag){
               		$("#"+id+" label:lt("+flag+")").each( (item,element)=> {
               			if(item != 0){
                   			$($(element).children("input").get(0)).prop("disabled",true);
               			}
            		})
        		}  
    		}
    		
    	
    	})
	})
	
	//tab初始化
	function dateTypeInit(){
		$('#myTab-0 a:first').tab('show');
		for(var i = 1;i<5;i++){
			console.log(i+"---"+$('#dateType-'+i).val());
			if(!$('#dateType-'+i).val()){
				$('#myTab-'+i+' li:eq('+(0)+') a').tab('show');
				$('#dateType-'+i).val(1);
			}else{
				$('#myTab-'+i+' li:eq('+(parseInt($('#dateType-'+i).val())-1)+') a').tab('show');
			}
		}
	}
		
	//时间初始化
	function dataInit(){
		$('.tripDate').datetimepicker({
			format : "YYYY-MM-DD"
		});
	}
		
	//获取城市数据
	function getCityData(){
		jp.post("${ctx}/meiguotong/comcity/comCity/getData",{
			"languageId":$("#language option:selected").val(),
			"endCity":$("#city").val(),
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
	
	//获取标题数据
	function getTitleData(){
		jp.post("${ctx}/meiguotong/car/carService/getTitleData",{
			"language":$("#language option:selected").val(),
			"ids":$("#titleids").val(),
			"title":$("#searchContent1").val()
			},function(data){
			if(data.success){
					var list=data.body.carServiceList;
					var html = "";
					var html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
						for (x in list){
							let range = "";
							if(list[x].range == 1){
								range = "接机";
							}else if(list[x].range == 2){
								range = "本地城市";
							}else{
								range = "附近城市";
							}
					  	html += '<tr>';
					  	html += '<input type="hidden" value="'+list.range+'"/>';
					  	html += '<input type="hidden" value="'+list.radius+'"/>';
					  	html +='<td><input type="radio" name="titleName" value="'+list[x].id+'"';
					  	html +='></td>';
					  	html +='<td>'+list[x].title+'</td>';
					  	html +='<td>'+range+'</td>';
					  	html +='<td>'+list[x].radius+'</td>';
					  	html +='</tr>';
					  	html +='';
						}
					$("#tbody1").empty();
					$("#tbody1").append(html==""?html1:html);
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
				<a class="panelButton" href="${ctx}/meiguotong/car/car"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		
		<ul class="nav nav-tabs"
			style="padding-left: 130px; padding-top: 20px;">
			<li class="active"><a data-toggle="tab" href="#tab-1">通用</a></li>
			<li class=""><a data-toggle="tab" href="#tab-2">详细信息</a></li>
			<c:if test="${not empty carInfo.id}">
				<li class=""><a data-toggle="tab" href="#tab-3">时间表管理</a></li>
				<li class=""><a data-toggle="tab" href="#tab-9">评价</a></li>
			</c:if>
		</ul>
		
		<form:form id="inputForm" modelAttribute="carInfo" action="${ctx}/meiguotong/car/car/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="agentid"/>
		<sys:message content="${message}"/>	
		
				<div class="panel-body tab-content">
				<div class="tab-pane fade in  active" id="tab-1">
				<div class="form-group">
					<label class="col-sm-2 control-label">语言：</label>
					<div class="col-sm-10">
						<form:select path="language" class="form-control m-b required">
							<form:options items="${comLanguageList}" itemLabel="content"
								itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所在城市：</label>
					<div class="col-sm-2">
						<form:hidden path="city"/>
						<form:input path="cityName" readonly="true" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">汽车图片：</label>
					<div class="col-sm-10">
						<img  class="img_style1" src="${carInfo.carImg}"  />
						<input type="hidden"name="carImg" value="${carInfo.carImg}" /> 
						<input type="file" class="input_file_style"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">汽车视频：</label>
					<div class="col-sm-10">
						<%-- <a target="_blank" href="${carInfo.carVideo}">${carInfo.carVideo}</a>
						<input type="hidden"name="carVideo" value="${carInfo.carVideo}" /> 
						<input style="width:300px" type="file" id="carVideo"  class="form-control"/> --%>
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box">	
	       					 		<div class="dv_pic_item">  
									<video controls="controls" src="${carInfo.carVideo}" height="140px" width="200px" class="img_style"></video> 
						            	<input id="video" type="hidden" name="carVideo" value="${carInfo.carVideo}" class="imgSrc"/>   
	           							<input style="width:300px" type="file" class="form-control" id="carVideo"/> 	
						           </div>
	           				 </div>
	   				 	</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">座位数：</label>
					<div class="col-sm-2">
						<form:input path="seatNum" htmlEscape="false"    class="form-control required digits"/>
					</div>
					<label class="col-sm-2 control-label">建议乘坐人数：</label>
					<div class="col-sm-2">
						<form:input path="adviseNum" htmlEscape="false"    class="form-control required digits"/>
					</div>
					<label class="col-sm-2 control-label">行李箱：</label>
					<div class="col-sm-2">
						<form:input path="bagNum" htmlEscape="false"    class="form-control required digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">舒适度：</label>
					<div class="col-sm-2">
						<form:select path="comfort"   class="form-control required">
							<form:option value="1" label="一般"/>
							<form:option value="2" label="舒适"/>
							<form:option value="3" label="很舒适"/>
							<form:option value="3" label="非常舒适"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">使用年限：</label>
					<div class="col-sm-2">
						<form:input path="useYear" htmlEscape="false"    class="form-control required  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">汽车业务：</label>
					<div class="col-sm-10">
							
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-4">包车租车</a></li>
							<li class=""><a data-toggle="tab" href="#tab-5">短程接送</a></li>
							<li class=""><a data-toggle="tab" href="#tab-6">接送机</a></li>
							<li class=""><a data-toggle="tab" href="#tab-7">定制租车</a></li>
							<!-- <li class=""><a data-toggle="tab" href="#tab-8">商务定制</a></li> -->
						</ul>
							
						<div class="panel-body tab-content">
						<c:forEach items="${carBusinessList}" var="a" varStatus="status">
							<input type="hidden" name="carBusinessList[${a.businessType-1}].businessType" value="${a.businessType}" />
							<input type="hidden" name="carBusinessList[${a.businessType-1}].dateType" id="dateType-${status.index}" value="${a.dateType}" />
							<input type="hidden" name="carBusinessList[${a.businessType-1}].id" value="${a.id}" />
							<c:if test="${a.businessType==1}">
							<div class="tab-pane fade in  active car_tab" id="tab-4">
								<div class="form-group">
									<label class="col-sm-1" style="padding-top: 7px;">状态：</label>
									<label class="col-sm-1" style="padding-top: 7px;"><input class="car_status" type="checkbox" value="1" name="carBusinessList[${a.businessType-1}].status" <c:if test="${a.status==1}">checked="checked"</c:if> />启用</label>
								</div>
								<div class="form-group">
									<div class="col-sm-10" >
											<button type="button" class="btn btn-info"   id="addTitle">添加标题</button>
											<button type="button" class="btn btn-danger" id="deleteTitle">删除标题</button>
									</div>
								</div>
								<div class="form-group">
									<ul class="nav nav-tabs"  id="myTab-0">
										<c:forEach items="${a.carTimePriceList}" var="b" varStatus="status2">
											<li><a data-toggle="tab" href="#tab_${status2.index}">${b.title}</a></li>
										</c:forEach>
									</ul>
									<input type="hidden" id="carTimePriceListLength" value="${fn:length(a.carTimePriceList)}"/>
									<input type="hidden" id="titleids" name="carBusinessList[${a.businessType-1}].titleids" value="${a.titleids}"/>
									<div class="panel-body tab-content" id="myTab-0-content">
										<c:forEach items="${a.carTimePriceList}" var="b" varStatus="status2">
										<div class="tab-pane fade car_service" id="tab_${status2.index}">
										<input type="hidden"  id="serviceid_${status2.index}" name="carBusinessList[0].carTimePriceList[${status2.index}].serviceid" value="${b.serviceid}"/>
											<div class="col-sm-10" >
												<div class="form-group">
													<label class="col-sm-2 control-label">范围设置：</label>
													<div class="col-sm-3">
														<%--<input type="radio" name="carBusinessList[0].carTimePriceList[${status2.index}].range" <c:if test="${b.range==1}">checked</c:if> value="1"/>接机--%>
														<%--<input type="radio" name="carBusinessList[0].carTimePriceList[${status2.index}].range" <c:if test="${b.range==2}">checked</c:if> value="2"/>附近城市--%>
														<%--<input type="radio" name="carBusinessList[0].carTimePriceList[${status2.index}].range" <c:if test="${b.range==3}">checked</c:if> value="3"/>距离半径--%>
														<c:if test="${b.range == 1}">接机</c:if>
														<c:if test="${b.range == 2}">附近城市</c:if>
														<c:if test="${b.range == 3}">距离半径</c:if>
													</div>
													<c:if test="${not empty b.range}">
													<div class="col-sm-2">
														<%--<input name="carBusinessList[0].carTimePriceList[${status2.index}].radius" value="${b.radius}" placeholder="请输入距离半径(km)" class="form-control digits"/>--%>
																${b.range} KM
													</div>
													</c:if>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">价格/次：</label>
													<div class="col-sm-5">
														<input name="carBusinessList[0].carTimePriceList[${status2.index}].price" class="form-control required number service_price" value="${b.price}" placeholder="请输入价格"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">起价小费：</label>
													<div class="col-sm-5">
														<input name="carBusinessList[0].carTimePriceList[${status2.index}].startPrice" class="form-control required number service_startPrice" value="${b.startPrice}" placeholder="请输入价格，人数未达标准时固定计算小费"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">人头小费：</label>
													<div class="col-sm-5">
														<input name="carBusinessList[0].carTimePriceList[${status2.index}].headPrice" class="form-control required number service_headPrice" value="${b.headPrice}" placeholder="请输入价格，人数达到标准来计算小费"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">餐补：</label>
													<div class="col-sm-5">
														<input name="carBusinessList[0].carTimePriceList[${status2.index}].foodPrice" class="form-control required number service_foodPrice" value="${b.foodPrice}" placeholder="请输入价格"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">空车返回：</label>
													<div class="col-sm-5">
														<input name="carBusinessList[0].carTimePriceList[${status2.index}].emptyPrice" class="form-control required number service_emptyPrice" value="${b.emptyPrice}" placeholder="请输入价格/km"/>
													</div>
												</div>
											</div>	
										</div>
										</c:forEach>
									</div>
								</div>
							</div>
							</c:if>
							<c:if test="${a.businessType != 1}">
							<div class="tab-pane fade car_tab" id="tab-${a.businessType+3}">
								<div class="form-group">
									<label class="col-sm-1" style="padding-top: 7px;">状态：</label>
									<label class="col-sm-1" style="padding-top: 7px;"><input type="checkbox" class="car_status" value="1" name="carBusinessList[${a.businessType-1}].status" <c:if test="${a.status==1}">checked="checked"</c:if> />启用</label>
								</div>
								<ul class="nav nav-tabs businessType" id="myTab-${a.businessType-1}">
									<li class=""><a data-toggle="tab" href="#tab-1-${a.businessType-1}">所有日期</a></li>
									<li class=""><a data-toggle="tab" href="#tab-2-${a.businessType-1}">按星期</a></li>
									<li class=""><a data-toggle="tab" href="#tab-3-${a.businessType-1}">按号数</a></li>
								</ul>
								<div class="panel-body tab-content">
								<div class="form-group">
									<label class="col-sm-2 control-label">日期范围：</label>
									<div class="col-xs-8">
										<div class="col-xs-12 col-sm-5">
											<div class='input-group date tripDate' >
												<input type='text' name="carBusinessList[${a.businessType-1}].beginDate" class="form-control required car_beginDate" value="<fmt:formatDate value="${a.beginDate}" pattern="yyyy-MM-dd"/>" />
												<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span></span>
											</div>
										</div>
										<div class="col-xs-12 col-sm-1">~</div>
										<div class="col-xs-12 col-sm-5">
											<div class='input-group date tripDate'style="left: -10px;">
												<input type='text' name="carBusinessList[${a.businessType-1}].endDate" class="form-control required car_endDate" value="<fmt:formatDate value="${a.endDate}" pattern="yyyy-MM-dd"/>" />
												<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span></span>
											</div>
										</div>
									</div>                    
								</div>
								<c:if test="${a.businessType == 2 || a.businessType == 3}">
								<div class="form-group">
									<label class="col-sm-2 control-label">日期范围：</label>
									<div class="col-sm-10  hourListListClick" id="hourListListClick_${a.businessType}">
										<c:forEach items="${hourList}" varStatus="status2" var="b">
										<label for="carBusinessList_${a.businessType}_${status2.index}"><input type="checkbox"  name="carBusinessList[${a.businessType-1}].hourList" value="${b.id}"  id="carBusinessList_${a.businessType}_${status2.index}" 
										<c:if test="${fn:contains(a.hourDate,b.id)}">checked="checked"</c:if> /></input>${b.name}</label>
										</c:forEach>
									</div>
								</div>
								</c:if>
								<div class="tab-pane fade" id="tab-1-${a.businessType-1}"></div>
								<div class="tab-pane fade" id="tab-2-${a.businessType-1}">
								<div class="form-group">
									<label class="col-sm-2 control-label">日期范围：</label>
									<div class="col-sm-10  ">
										<c:forEach items="${weekDateList}" varStatus="status2" var="b">
										<label><input type="checkbox" name="carBusinessList[${a.businessType-1}].weekList" value="${b.id}" 
										<c:if test="${fn:contains(a.weekDate,status2.index+1)}">checked="checked"</c:if> /></input>${b.name}</label>
										</c:forEach>
									</div>
								</div>
								</div>
								<div class="tab-pane fade" id="tab-3-${a.businessType-1}">
								<div class="form-group">
									<label class="col-sm-2 control-label">日期范围：</label>
									<div class="col-sm-10">
										<c:forEach items="${a.dayList1}" varStatus="status2" var="b">
										<label><input type="checkbox" name="carBusinessList[${a.businessType-1}].dayList" value="${b.id}" <c:if test="${b.digFlag==1}">checked="checked"</c:if> /></input>${b.name}</label>
										</c:forEach>
									</div>
								</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">价格：</label>
									<div class="col-sm-4">
										<input name="carBusinessList[${a.businessType-1}].price" class="form-control required number car_price" value="${a.price}" placeholder="请输入价格"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">起价小费：</label>
									<div class="col-sm-4">
										<input name="carBusinessList[${a.businessType-1}].startPrice" class="form-control required number car_startPrice" value="${a.startPrice}" placeholder="请输入价格，人数未达标准时固定计算小费"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">人头小费：</label>
									<div class="col-sm-4">
										<input name="carBusinessList[${a.businessType-1}].headPrice" class="form-control required number car_headPrice" value="${a.headPrice}" placeholder="请输入价格，人数达到标准来计算小费"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">餐补：</label>
									<div class="col-sm-4">
										<input name="carBusinessList[${a.businessType-1}].foodPrice" class="form-control required number car_foodPrice" value="${a.foodPrice}" placeholder="请输入价格"/>
									</div>
								</div>
								<c:if test="${a.businessType != 3}">
								<div class="form-group">
									<label class="col-sm-2 control-label">空车返回：</label>
									<div class="col-sm-4">
										<input name="carBusinessList[${a.businessType-1}].emptyPrice" class="form-control required number car_emptyPrice" value="${a.emptyPrice}" placeholder="请输入价格/km"/>
									</div>
								</div>
								</c:if>
								</div>
								
							</div>
							</c:if>
							</c:forEach>
						</div>	
						
					</div>
				</div>
				<%-- 	<c:if test="${fns:hasPermission('meiguotong:car:car:edit') || isAdd}"> --%>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
<%-- 		</c:if> --%>
				</div>
				<div class="tab-pane fade" id="tab-2">
				
				<div class="form-group">
					<label class="col-sm-2 control-label">汽车名称：</label>
					<div class="col-sm-10">
						<form:input path="carName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">汽车卖点：</label>
					<div class="col-sm-10">
						<form:input path="carTrait" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<%-- 	<c:if test="${fns:hasPermission('meiguotong:car:car:edit') || isAdd}"> --%>
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
<%-- 		</c:if> --%>
				</div>
				
				<div class="tab-pane fade" id="tab-3">
				<iframe src="${ctx}/meiguotong/car/carService/calander?id=${carInfo.id}" id="contentCommentIfem" style="background-color:white"  scrolling="no" frameborder="0" height="550px" width="100%" ></iframe>
				</div>
				
				<div class="tab-pane fade" id="tab-9">
					<iframe id="frame_content1"
							src="${ctx}/meiguotong/comcomment/comComment/goComment?type=1&typeId=${car.id}"
							frameborder="0" height="500px" width="100%"></iframe>
				</div>
				<script type="text/javascript">
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
				</script>
				</div>
	
		</form:form>
		</div>				
	</div>
	</div>
</div>

<!-- 标题模态框（Modal） -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">租车标题</h4>
            </div>
            <div class="modal-body">
	      <div>
	      	<input id="searchContent1" placeholder="输入标题名称" class="form-control col-sm-2" style="width:40%;margin-right:15px;"/>
	      	 <a  id="search1" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
	      	 <a  id="reset1" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
	      </div>
        <br/>
		<table class="table table-bordered">
					 <thead>
					    <tr>
					        <th> </th>
					       	<th>标题名称</th>
					       	<th>范围</th>
					       	<th>距离(KM)</th>
					    </tr>
					    </thead>
					    <tbody id="tbody1">
						  	<tr><td style="text-align:center;" colspan="2">加载中</td></tr>
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
					    <tbody id="tbody2">
						  	<tr><td style="text-align:center;" colspan="2">加载中</td></tr>
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

</div>
</body>
</html>