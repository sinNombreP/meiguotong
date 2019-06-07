<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮轮航线管理管理</title>
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
		<style>
      .btn_add_pic_a,.btn_add_pic_b,.btn_add_pic_c,.btn_add_pic_d{  
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
	    .deleteBtn{
	    	padding:2px 10px;
	    	background:#E1E1E1;
	    }
	    .dv_pic_item_a,.dv_pic_item_b,.dv_pic_item_c,.dv_pic_item_d{  
	        width: 200px;  
	        height: 200px;  
	        margin: 5px 5px;  
	        float: left;  
	    }  
	    .input_file_style_a,.input_file_style_b,.input_file_style_c,.input_file_style_d{  
	        width: 200px;  
	        height: 20px;  
	    }  
	    .img_style{  
	        width: 200px;  
	        height: 140px;  
	        display: block;  
	        background-size: 100% auto;  
	    } 
	    .addAttr{
	        height: 30px;  
	        border-radius: 6px;  
	        outline: none;  
	        border: none;  
	        background-color: #00BCD4;  
	        color: #fff;  
	        cursor: pointer;  
	    }
	     .remove{
	        height: 22px;  
	        border-radius: 6px;  
	        outline: none;  
	        border: none;  
	        background-color: #de6764;  
	        color: #fff;  
	        cursor: pointer;  
	    }     
	    .roomType1{
	    	display: none;
	    }
	</style>
	<script type="text/javascript">
		//景点集合
		var scenicSpotList;
		
		var ue = UE.getEditor('content');//获得去机器
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		UE.Editor.prototype.getActionUrl = function(action) {
			if (action == 'uploadimage' || action == 'uploadscrawl'
					|| action == 'uploadimage') {
				return '${ctx}/sys/sysUser/uploadIMG'; //远程图片上传controller
			} else {
				return this._bkGetActionUrl.call(this, action);
			}
		}
		//删除图片
		function deleteElement(Obj){
	    	Obj.parentNode.parentNode.removeChild(Obj.parentNode); 
	    }
		//<div class="dv_pic_item">下标
		var divIndex;
		//更换图片的本地路径
		var src;
		var btnCount_b=0; 
		function initClickListener_b(){  
	        $(".btn_add_pic_b").click(function(){
	        	if(btnCount_b>0){
	        		if($("#file_b_"+btnCount_b).length>0){
		        		var fileValue=$("#file_b_"+btnCount_b).val();
			        	if(fileValue == null || fileValue==""){
			        		return;
			        	}
	        		}
	        	} 
	            btnCount_b++;  
	            var t='<div class="dv_pic_item_b">';  
	            t+='<img src="" alt="" class="img_style" id="img_b_'+btnCount_b+'"/>';  
	            t+='<input type="hidden" name="imgUrl" class="imgSrc" id="src_b_'+btnCount_b+'"/>';  
	            t+='<input type="file" class="input_file_style_b" id="file_b_'+btnCount_b+'"/>';
	            t+='<a id="btn_b_'+btnCount_b+'" onclick="deleteElement(this)" class="deleteBtn">删除</a>'
	            t+='</div>';  
	            $(".dv_pic_box_b").append(t);
	        });
		};
		function uploadComplete_b(evt) {
			var textJson = JSON.parse(evt.target.responseText);
			if(textJson.success){
				$(".dv_pic_item_b").eq(divIndex).children(".imgSrc").val(textJson.body.filePath);
				$(".dv_pic_item_b").eq(divIndex).children(".img_style").attr("src",src);
			} 
			jp.success(textJson.msg);//这是框架方法非框架用alert
		}				
		function uploadFailed(evt) {
			jp.success("上传失败");//这是框架方法非框架用alert
		}
		function uploadCanceled(evt) {
			jp.success("取消上传");//这是框架方法非框架用alert
		}
		
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					if($("input[name='imgUrl']").length==0){
						jp.error("产品图片不能为空！");
						return;
					}
					var num=0;
					$("input[name='imgUrl']").each(function(){
						var img=$(this).val();
						if(!img){
							num=1;
						}
					})
					if(num!=0){
						jp.error("产品图片不能为空！");
						return;
					}
					if($("input[name='tagId']").length==0){
						jp.error("标签属性不能为空！");
						return;
					}
					/* if($(".line_room").length==0){
						jp.error("房间不能为空！");
						return;
					} */
					var room1=0;
					var room2=0;
					var room3=0;
					var room4=0;
					var room5=0;
					var room6=0;
					var room7=0;
					var room8=0;
					var room9=0;
					var room4D=0;
					var room5D=0;
					var room6D=0;
					var room7N=0;
					var room8D=0;
					
					$(".line_room").each(function(){
						if(!$(this).find(".room_name").val()){
							room1=1;
						}
						if(!$(this).find(".room_spec").val()){
							room2=1;
						}
						if(!$(this).find(".room_floor").val()){
							room3=1;
						}
						if($(this).find(".room_checkInType").is(":checked")){
							if(!$(this).find(".room_minPeopleNumber").val()){
								room4=1;
							}
							if(!/^0$|^[1-9]\d*$/.test($(this).find(".room_minPeopleNumber").val().trim())){
								room4D=1;  
							}
							if(!$(this).find(".room_maxPeopleNumber").val()){
								room5=1;
							}
							if(!/^0$|^[1-9]\d*$/.test($(this).find(".room_maxPeopleNumber").val().trim())){
								room5D=1;  
							}
						}else{
							if(!$(this).find(".room_peopleNumber").val()){
								room6=1;
							}
							if(!/^0$|^[1-9]\d*$/.test($(this).find(".room_peopleNumber").val().trim())){
								room6D=1;  
							}
						}
						
						if(!$(this).find(".room_price").val()){
							room7=1;
						}
						if(!/^[0-9,.]*$/.test($(this).find(".room_price").val().trim())){
							room7N=1;
						} 
						if(!$(this).find(".room_number").val()){
							room8=1;
						}
						if(!/^0$|^[1-9]\d*$/.test($(this).find(".room_number").val().trim())){
							room8D=1;  
						}
						if(!$(this).find(".imgSrc").val()){
							room9=1;
						}
					})
					if(room1!=0){
						jp.error("房间类型不能为空！");
						return;
					}
					if(room2!=0){
						jp.error("房间规格不能为空！");
						return;
					}
					if(room3!=0){
						jp.error("房间层数不能为空！");
						return;
					}
					if(room4!=0){
						jp.error("最小入住人数不能为空！");
						return;
					}
					if(room5!=0){
						jp.error("最大入住人数不能为空！");
						return;
					}
					if(room4D!=0){
						jp.error("最小入住人数只能为整数！");
						return;
					}
					if(room5D!=0){
						jp.error("最大入住人数只能为整数！");
						return;
					}
					if(room6!=0){
						jp.error("必须入住人数不能为空！");
						return;
					}
					if(room6D!=0){
						jp.error("必须入住人数只能为整数！");
						return;
					}
					if(room7!=0){
						jp.error("房间价格不能为空！");
						return;
					}
					if(room7N!=0){
						jp.error("请输入正确的房间价格！");
						return;
					} 
					if(room8!=0){
						jp.error("房间数量不能为空！");
						return;
					}
					if(room8D!=0){
						jp.error("房间数量只能为整数！");
						return;
					}
					if(room9!=0){
						jp.error("房间图片不能为空！");
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
			//单图上传
			$("#myTab3s").on("change",".input_file_style1",function(e){ 
	        	var fileId=$(this).attr("id");
	        	var target=document.getElementById($(this).attr("id"));
	        	if(!checkImg(target)){
	      			return;
	      		}   
	         	 for (var i = 0; i < e.target.files.length; i++) {    
	             	 var file = e.target.files.item(i);    
	             	 //实例化FileReader API    
	             	 var freader = new FileReader();    
	            	  freader.readAsDataURL(file);
	            	  jp.loading();			//这是框架方法，阻塞浏览器（转圈）
	            	  freader.onload = function(e) { 
	            			var fd = new FormData();
	              			fd.append("attach", file);
	              			var xhr = new XMLHttpRequest();
	              			xhr.addEventListener("load", function(evt) { 
	              				var textJson = JSON.parse(evt.target.responseText);
	              				if(textJson.success){
	              					$("#"+fileId).parent(".dv_pic_item").children(".imgSrc").val(textJson.body.filePath);
	              					$("#"+fileId).parent(".dv_pic_item").children(".img_style").attr("src",textJson.body.filePath);
	              				} 
	              				jp.success(textJson.msg);//这是框架方法非框架用alert
	              			}, false);
	              			xhr.addEventListener("error", uploadFailed, false);
	              			xhr.addEventListener("abort", uploadCanceled, false);
	              			xhr.open("POST", "${ctx}/meiguotong/liner_line/linerLine/uploadFile");
	              			xhr.send(fd);
	            	  }    
	         	}  
	        });
			//单图上传
			$("#myTab3").on("change",".input_file_style1",function(e){ 
	        	var fileId=$(this).attr("id");
	        	var target=document.getElementById($(this).attr("id"));
	        	if(!checkImg(target)){
	      			return;
	      		}   
	         	 for (var i = 0; i < e.target.files.length; i++) {    
	             	 var file = e.target.files.item(i);    
	             	 //实例化FileReader API    
	             	 var freader = new FileReader();    
	            	  freader.readAsDataURL(file);
	            	  jp.loading();			//这是框架方法，阻塞浏览器（转圈）
	            	  freader.onload = function(e) { 
	            			var fd = new FormData();
	              			fd.append("attach", file);
	              			var xhr = new XMLHttpRequest();
	              			xhr.addEventListener("load", function(evt) { 
	              				var textJson = JSON.parse(evt.target.responseText);
	              				if(textJson.success){
	              					$("#"+fileId).parent(".dv_pic_item").children(".imgSrc").val(textJson.body.filePath);
	              					$("#"+fileId).parent(".dv_pic_item").children(".img_style").attr("src",textJson.body.filePath);
	              				} 
	              				jp.success(textJson.msg);//这是框架方法非框架用alert
	              			}, false);
	              			xhr.addEventListener("error", uploadFailed, false);
	              			xhr.addEventListener("abort", uploadCanceled, false);
	              			xhr.open("POST", "${ctx}/meiguotong/liner_line/linerLine/uploadFile");
	              			xhr.send(fd);
	            	  }    
	         	}  
	        });
			//点击添加多图——b
			initClickListener_b();
			$(".dv_pic_box_b").on("change",".input_file_style_b",function(e){ 
		        	var fileId=$(this).attr("id");
		        	divIndex=$(".dv_pic_item_b").index( $("#"+fileId).parents(".dv_pic_item_b"))
		        	var target=document.getElementById($(this).attr("id"));
		        	if(!checkImg(target)){
		      			return;
		      		}   
		         	 for (var i = 0; i < e.target.files.length; i++) {    
		             	 var file = e.target.files.item(i);    
		             	 //实例化FileReader API    
		             	 var freader = new FileReader();    
		            	  freader.readAsDataURL(file);  
		            	  jp.loading();			//这是框架方法，阻塞浏览器（转圈）
		            	  freader.onload = function(e) { 
		            		  	src=e.target.result;
		            			var fd = new FormData();
		              			fd.append("attach", file);
		              			var xhr = new XMLHttpRequest();
		              			xhr.addEventListener("load", uploadComplete_b, false);
		              			xhr.addEventListener("error", uploadFailed, false);
		              			xhr.addEventListener("abort", uploadCanceled, false);
		              			xhr.open("POST", "${ctx}/meiguotong/liner_line/linerLine/uploadFile");
		              			xhr.send(fd);
		            	  }    
		         	}  
		        });
			

			$('#updateDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
			});
			
			$("#languageId").change(function(){
				var languageId = $(this).val();
				if(languageId){
					//获取航区
					var url1="${ctx}/meiguotong/liner_line/linerLine/getLinerCourse";
					var params1={"languageId":languageId};
					jp.post(url1,params1,function(data){
						var list = data.body.list;
						var t='';
						if(list){
							for(var i in list){
								t+='<option class="removeCourse" value="';
								t+=list[i].id;
								t+='" >';
								t+=list[i].name;
								t+='</option>';
							}
						}
						$(".removeCourse").remove();
						$("#route").append(t);
					});
					//获取城市
					var url2="${ctx}/meiguotong/liner_line/linerLine/getCity";
					var params2={"languageId":languageId};
					jp.post(url2,params2,function(data){
						var list = data.body.list;
						var t='';
						if(list){
							for(var i in list){
								t+='<option class="removeCity" value="';
								t+=list[i].cityid;
								t+='" >';
								t+=list[i].cityName;
								t+='</option>';
							}
						}
						$(".removeCity").remove();
						$("#startCity").append(t);
						$("#downPort").append(t);
					});
					
					$(".removePort").remove();
					$("#tagTable").empty();
					
					//获取游轮
					var url4="${ctx}/meiguotong/liner_line/linerLine/getLiner";
					var params4={"languageId":languageId};
					jp.post(url4,params4,function(data){
						var list = data.body.list;
						var t='';
						if(list){
							for(var i in list){
								t+='<option class="removeLiner" value="';
								t+=list[i].id;
								t+='" >';
								t+=list[i].name;
								t+='</option>';
							}
						}
						$(".removeLiner").remove();
						$("#linerId").append(t);
					}); 
					//获取景点
					var url5="${ctx}/meiguotong/liner_line/linerLine/getScenicSpot";
					var params5={"languageId":languageId};
					jp.post(url5,params5,function(data){
						scenicSpotList = data.body.list;
						var t='';
						t+='<option class="removeScenicSpot" value="">请选择</option>';
						if(scenicSpotList){
							for(var i in scenicSpotList){
								t+='<option class="removeScenicSpot" value="';
								t+=scenicSpotList[i].id;
								t+='" >';
								t+=scenicSpotList[i].name;
								t+='</option>';
							}
						}
						$(".tripScenic").empty();
						$(".tripScenic").append(t);
					}); 
				}else{
					$(".removeCourse").remove();
					$(".removeCity").remove();
					$(".removePort").remove();
					$("#tagTable").empty();
					$(".removeLiner").remove();
					$(".removeScenicSpot").remove();
				}
				
				
			});
			$("#startCity").change(function(){
				var cityid=$("#startCity").val();
				var languageId = $("#languageId").val();
				var url3="${ctx}/meiguotong/liner_line/linerLine/getPort";
				var params3={"languageId":languageId,"startCity":cityid};
				if(cityid){
					jp.post(url3,params3,function(data){
						var list = data.body.list;
						var t='';
						if(list){
							for(var i in list){
								t+='<option class="removePort" value="';
								t+=list[i].id;
								t+='" >';
								t+=list[i].name;
								t+='</option>';
							}
						}
						$(".removePort").remove();
						$("#upPort").append(t);
					}); 
				}else{
					$(".removePort").remove();
				}
			});
			
			$("#addTag").click(function(){
				$("#tagName").val("");
				var languageId = $("#languageId").val();
				if(!languageId){
					jp.error("请选择语言");
					return;
				}
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
				var url="${ctx}/meiguotong/liner_line/linerLine/getTag";
				var params={"languageId":languageId,"tagId":str2};
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td><input type="checkbox"';
							t+=' value="';
                            t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].content;
							t+='"></td><td>';
							t += list[i].id;
							t += '</td><td>';
							t += list[i].content;
							t += '</td></tr>';
						}
					}
					$("#tbody").empty();
					$("#tbody").append(t);
				}); 
			});
			$("#search").click(function(){
				var languageId = $("#languageId").val();
				var name = $("#tagName").val();
				if(!languageId){
					jp.error("请选择语言");
					return;
				}
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
				var url="${ctx}/meiguotong/liner_line/linerLine/getTag";
				var params={"languageId":languageId,"tagId":str2,"name":name};
				jp.post(url,params,function(data){
					var list = data.body.list;
					var t=''; 
					if(list){
						for(var i in list){
							t += '<tr><td><input type="checkbox"';
							t+=' value="';
                            t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].id;
							t+='"><input type="hidden"  value="';
							t+=list[i].content;
							t+='"></td><td>';
							t += list[i].id;
							t += '</td><td>';
							t += list[i].content;
							t += '</td></tr>';
						}
					}
					$("#tbody").empty();
					$("#tbody").append(t);
				}); 
			});
			
			$("#tagTable").on("click", ".removeColor",
					function() {
						$(this).parents(".selectColor").remove();
					});
			//删除房间
			$("#myTab2s").on("click",".room_del",function(e){
				var a = $(this).parent().attr("id");
				jp.confirm("请确认是否删除！", function(){
					$("#"+a).remove();
					$("."+a).remove();
				})
			})
			//添加房间
			var roomNumber = 0;
			$("#addRouteContent")
					.click(
							function() {
								var html1 = '<li id="room_'+roomNumber+'" ><a data-toggle="tab" href="#tab1-'+roomNumber+'" style="display:inline-block;padding-right:4px;">房间</a><img src="${ctxStatic}/del.png" width="30px" height="30px" class="room_del" style="margin-bottom:2px;"></li>';
								var html2 = '<div class="tab-pane fade room_'+roomNumber+' line_room" id="tab1-'+roomNumber+'">'
										+ '<div class="form-group">'
										+ '<label class="col-sm-2 control-label"style="text-align: left;">房间类型<i style="color: red;">*</i>：'
										+ '</label>'
										+ '<div class="col-sm-6">'
										+ '<input name="linerRoomList['
										+ roomNumber
										+ '].name" value="" htmlEscape="false" class="form-control room_name" />'
										+ '</div>'
										+ '</div>'
										+ '<div class="form-group">'
										+ '<label class="col-sm-2 control-label"style="text-align: left;">房间规格<i style="color: red;">*</i>：'
										+ '</label>'
										+ '<div class="col-sm-3">'
										+ '<input name="linerRoomList['
										+ roomNumber
										+ '].spec" value="" htmlEscape="false" class="form-control room_spec" />'
										+ '</div>'
										+ '<label class="col-sm-2 control-label">房间层数<i style="color: red;">*</i>：</label>'
										+ '<div class="col-sm-3">'
										+ '<input name="linerRoomList['
										+ roomNumber
										+ '].floor"  value="" htmlEscape="false" class="form-control room_floor" />'
										+ '</div>'
										+ '</div>'
										+ '<div class="form-group">'
										+ '<label class="col-sm-2 control-label" style="text-align: left;">人数要求'
									    + '<i style="color: red;">*</i>：'
										+ '</label>'
										+ '<div class="col-sm-10">'
										+ '<table>'
										+ '<tr>'
										+ '<td "><input type="checkbox" name="linerRoomList['
										+ roomNumber
										+ '].checkInType" value="1" class="room_checkInType"></td>'
										+ '<td style="width:100px;">可入住</td>'
										+ '<td class="roomType1"><input type="text" name="linerRoomList['
										+ roomNumber
										+ '].minPeopleNumber" value="" placeholder="最小入住/人" class="room_minPeopleNumber"></td>'
										+ '<td class="roomType1">———</td>'
										+ '<td class="roomType1"><input type="text" name="linerRoomList['
										+ roomNumber
										+ '].maxPeopleNumber" value="" placeholder="最多入住/人" class="room_maxPeopleNumber"></td>'
										+ '<td class="roomType2"><input type="text" name="linerRoomList['
										+ roomNumber
										+ '].peopleNumber" value="" placeholder="必须入住/人" class="room_peopleNumber"></td>'
										+ '</tr>'
										+ '</table>'
										+ '</div>'
										+ '</div>'
										+ '<div class="form-group">'
										+ '<label class="col-sm-2 control-label"style="text-align: left;">价格<i style="color: red;">*</i>：'
										+ '</label>'
										+ '<div class="col-sm-3">'
										+ '<input name="linerRoomList['
										+ roomNumber
										+ '].price" style="width:80%;display:inline;" value="" htmlEscape="false" class="form-control room_price" />&nbsp;起/人'
										+ '</div>'
										+ '<label class="col-sm-2 control-label">房间数量<i style="color: red;">*</i>：</label>'
										+ '<div class="col-sm-3">'
										+ '<input name="linerRoomList['
										+ roomNumber
										+ '].roomNumber" value="" htmlEscape="false" class="form-control room_number" />'
										+ '</div>'
										+ '</div>'
										+ '<div class="form-group">'
										+ '<label class="col-sm-2 control-label" style="text-align: left;">添加图片'
										+ '<i style="color: red;">*</i>：</label>'
										+ '<div class="col-sm-3">'
										+ '<div class="dv_info_box">'  
										+ '<div class="dv_pic_box">'	
										+ '<div class="dv_pic_item">'
										+ '<img  class="img_style" id="img1_'
										+ roomNumber
										+ '" src="" />'
										+ '<input type="hidden" name="linerRoomList['
										+ roomNumber
										+ '].imgUrl" value="" id="src1_'
										+ roomNumber
										+ '" class="imgSrc" />'
										+ '<input type="file" class="input_file_style1" id="file1_'
										+ roomNumber
										+ '" />'
										+ '</div></div></div></div>' + '</div>' + '</div>';
							
								$("#myTab2s").append(html1);
								$("#myTab3s").append(html2);
								roomNumber += 1;
							})
			//添加行程天数			
			
			$("#addRouteContent1")
				.click(
						function() {
							var day = $("#routeContentLength1").val();
							var dayMax = $("#travelDay").val();
							if (dayMax == null || dayMax == "") {
								jp.info("请添加行程天数");
								return;
							}
							if (day >= dayMax) {
								jp.info("大于最大行程天数");
								return;
							}
							var languageId = $("#languageId").val();
							if(!languageId){
								jp.info("请选择语言");
								return;
							}
							var index = parseInt(day);
							var day2 = parseInt(day) + 1;
							var html1 = '<li ><a data-toggle="tab" href="#tab2-'+index+'">第'
									+ day2 + '天</a></li>';
							var html2 = '<div class="tab-pane fade" id="tab2-'+index+'">'
									+ '<input  value="'+day2+'" type="hidden" name="linerTripList['+day+'].dayCount"/>'
									
									+ '<div class="form-group">'
									+ '<label class="col-sm-2 control-label" style="text-align: left;">行程内容<i style="color: red;">*</i>：'
									+ '</label>'
									+ '<div class="col-sm-10">'
									+ '<button type="button" class="btn btn-info addRouteContent" >添加行程</button>'
									+ '<button type="button" class="btn btn-danger deleteRouteContent">删除最后一个行程</button>'
									+ '</div>'
									+ '</div>'
									+ '<input  type="hidden"  value="1" readonly/>'
									;
							html2 = addHtml(html2,day,0)+'</div>';
							$("#myTab2").append(html1);
							$("#myTab3").append(html2);
							$("#routeContentLength1").val(day2);
							dataInit();
						})

			//删除最后一天行程
			$("#deleteRouteContent").click(function() {
				var day = $("#routeContentLength1").val();
				day--;
				if (day > 0) {
					var index = parseInt(day);
					$("#tab-" + index).remove();
					$("#myTab2 li:last-child").remove();
					console.log($("#myTab2 li:last-child"));
					$("#routeContentLength1").val(day);
					$('#myTab2 a:last').tab('show');
				}
	
			})
			
			//点击添加行程
	 		$(".wrapper-content").on("click",".addRouteContent",function(){
	 			const this_ = $(this);
	 			var day = this_.parent().parent().prev().val();
				var day2 = this_.parent().parent().next().val();
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
			
			$('#beginUpdateDate').datetimepicker({
				format : "YYYY-MM-DD"
			});
			
			$('#endUpdateDate').datetimepicker({
				format : "YYYY-MM-DD"
			});
			
			$('#myTab li').click(function (e) {
	    		var index = $(this).index();
	    		$("#dateType").val(index+1);
	    	})
	    	
	    	$("#myTab3s").on("click",".room_checkInType",function(){
	    		if($(this).is(":checked")){
	    			$(this).parents(".line_room").find(".roomType1").css("display","inline-block");
	    			$(this).parents(".line_room").find(".roomType2").css("display","none");
	    		}else{
	    			$(this).parents(".line_room").find(".roomType2").css("display","inline-block");
	    			$(this).parents(".line_room").find(".roomType1").css("display","none");
	    		}
	    	})
 		});
		//时间初始化
		function dataInit(){
			$('.tripDate').datetimepicker({
				format : "HH:mm:ss"
			});
		}
		
		function addHtml(str,day,day2){
			str +='<div class="del_flag_content">'
			+ '<div class="form-group">'
			+ '<label class="col-sm-2 control-label" style="text-align: left;">选择时间<i style="color: red;"></i>：'
			+ '</label>'
			+ '<div class="col-sm-5">'
			+ '<div class="col-xs-12 col-sm-5">'
			+ '<div class="input-group date tripDate"  style="left: -10px;">'
			+ '<input type="text" name="linerTripList['+day+'].contentList['+day2+'].tripDate" class="form-control "/>'
			+ '<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span></span>'
			+ '</div>'
			+ '</div>'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="col-sm-2 control-label" style="text-align: left;">行程类型<i style="color: red;">*</i>：</label>'
			+ '<div class="col-sm-3">'
			+ '<select class="form-control m-b" name="linerTripList['+day+'].contentList['+day2+'].tripType" >'
			+ '<option value="1" >就餐</option>'
			+ '<option value="2" >旅游</option>'
			+ '<option value="3" >其他</option>'
			+ '</select>'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="col-sm-2 control-label" style="text-align: left;">行程景点<i style="color: red;"></i>：</label>'
			+ '<div class="col-sm-3">'
			+ '<select class="form-control m-b tripScenic" name="linerTripList['+day+'].contentList['+day2+'].scenicSpotId">';
			str += '<option value="">请选择</option>';
		if (scenicSpotList != null && scenicSpotList.length > 0) {
		for (var x in scenicSpotList) {
			str += '<option value="'+scenicSpotList[x].id+'" >'+scenicSpotList[x].name+'</option>';
		}
	}
	str += '</select> '
			+ '</div>'
			+ '<label class="col-sm-2 control-label">行程标题<i style="color: red;">*</i>：</label>'
			+ '<div class="col-sm-3">'
			+ '<input name="linerTripList['+day+'].contentList['+day2+'].title"  htmlEscape="false" class="form-control tripTitle" />'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="col-sm-2 control-label" style="text-align: left;">行程详情 <i style="color: red;"></i>：</label>'
			+ '<div class="col-sm-10">'
			+ '<textarea rows="5" name="linerTripList['+day+'].contentList['+day2+'].content" class="form-control"></textarea>'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="col-sm-2 control-label" style="text-align: left;">行程图片<i style="color: red;"></i>：</label>'
			+ '<div class="col-sm-3">'
			+ '<div class="dv_info_box">'  
			+ '<div class="dv_pic_box">'	
			+ '<div class="dv_pic_item">'
			+ '<img class="img_style" width="200px"  id="img1_'+day+'_'+day2+'" src="" />'
			+ '<input class="imgSrc" type="hidden" name="linerTripList['+day+'].contentList['+day2+'].imgUrl" value="" id="src1_'+day+'_'+day2+'" />'
			+ '<input type="file" class="input_file_style1" id="file1_'+day+'_'+day2+'" />'
			+ '</div></div></div></div>' + '</div><hr noshade="noshade" style="height:1px;"/>'
			+ '</div>';
			return str;
		}
	</script>
	
	   <script type="text/javascript">
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
	  function changIdCardFrontImg(target,e){   
	    	//判断图片格式、大小
		if(!checkImg(target)){
		    		return;
		    	}   
		        for (var i = 0; i < e.target.files.length; i++) {    
		            var file = e.target.files.item(i);    
		            //实例化FileReader API    
		            var freader = new FileReader();    
		            freader.readAsDataURL(file);    
		            freader.onload = function(e) {    
		                $("#idCardFrontImg").attr("src",e.target.result);    //显示加载图片
		            }    
		        }    
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
				<a class="panelButton" href="${ctx}/meiguotong/liner_line/linerLine"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="linerLine" enctype="multipart/form-data"  action="${ctx}/meiguotong/liner_line/linerLine/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="dateType" value="1"/>
		<sys:message content="${message}"/>	
				
				<div class="form-group">
					<label class="col-sm-2 control-label">选择语言:</label>
					<div class="col-sm-10">						
						<form:select  path="languageId" class="form-control required">
							<form:option value="" label="请选择"/>
							<c:forEach items="${languageList}" var="languageList">
								<form:option value="${languageList.id}" label="${languageList.content}"/>
							</c:forEach>
						</form:select>					
					</div>
				</div>
						<div class="form-group">
					<label class="col-sm-2 control-label">邮轮图片:</label>
					<div class="col-sm-10" style=" border:1px solid #cccccc;">
						<div class="dv_info_box">  
	       					 <div class="dv_pic_box_b">                                <!-- 多个多图class修改-->
	       					 <c:if test="${linerLine.imgUrl!=null}">
	       					 <c:set value="${fn:split(linerLine.imgUrl, ',')}" var="imgUrl"></c:set>
	       					 	<c:forEach var="photoUrl" items="${imgUrl}">
	       					 		<div class="dv_pic_item_b">  		 <!-- 多个多图class修改-->
	            						<%! 
											char number_b='a';
											synchronized void numberAdd_b()
											{
												number_b++;
											}
											synchronized void numberZero_b()
											{
												number_b='a';
											}
										%>
										<img src="${imgUrl}" class="img_style" id="img_b_<%=number_b%>"/>    <!-- 多个多图id修改-->
						            	<input type="hidden" name="imgUrl" value="${imgUrl}" class="imgSrc"/>   
	           							<input type="file" class="input_file_style_b" id="file_b_<%=number_b%>"/>       <!-- 多个多图class修改-->   <!-- 多个多图id修改-->
						            	<a id="btn_b_<%=number_b%>" onclick="deleteElement(this)" class="deleteBtn">删除</a>     <!--多个多图 id修改-->
						            	<% numberAdd_b();%>
						           </div>
	       					 	</c:forEach>
	       					 	<% numberZero_b();%>
	       					 </c:if>  
	           				 </div>
	       					 <button type="button" class="btn_add_pic_b" >添加图片</button> <!--多个多图class修改-->
	   				 	</div> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">选择航区:</label>
					<div class="col-sm-10">
						<form:select  path="route" class="form-control required">
							<form:option value="" label="请选择"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出发城市:</label>
					<div class="col-sm-10">
						<form:select  path="startCity" class="form-control required">
							<form:option value="" label="请选择"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上船港口:</label>
					<div class="col-sm-10">
						<form:select  path="upPort" class="form-control required">
							<form:option value="" label="请选择"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">下船地点:</label>
					<div class="col-sm-10">
						<form:select  path="downPort" class="form-control required">
							<form:option value="" label="请选择"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">选择邮轮:</label>
					<div class="col-sm-10">						
						<form:select  path="linerId" class="form-control required">
							<form:option value="" label="请选择"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标签属性:</label>
					<div class="col-sm-10">
						<button type="button" id="addTag" class="btn_add"
												data-toggle="modal" data-target="#myModal">选择标签</button>
						<table id="tagTable" style="margin-left: 20px; border: 1px solid black; margin-top: 10px;">
						</table>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮轮路线名称:</label>
					<div class="col-sm-10">
					<input  type="text" name="name" class="form-control required"/>
				</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">路线简介:</label>
					<div class="col-sm-10">
					<form:textarea path="infor" htmlEscape="false" rows="4"    class="form-control required"/>
				</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">途径地点:</label>
					<div class="col-sm-10">
					<input  type="text" name="path" class="form-control  "/>
				</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品经理推荐:</label>
					<div class="col-sm-10">
					<form:textarea path="recommend" htmlEscape="false" rows="4"    class="form-control required"/>
				</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">旅行天数:</label>
					<div class="col-sm-10">
						<form:input path="travelDay" htmlEscape="false"  class="form-control digits required" style="width:20%;display:inline-block;"/><span>&nbsp;&nbsp;&nbsp;天</span>
						<form:input path="travelNight" htmlEscape="false"  class="form-control digits required" style="width:20%;display:inline-block;"/><span>&nbsp;&nbsp;&nbsp;晚</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提前天数:</label>
					<div class="col-sm-10">
						<span>&nbsp;&nbsp;&nbsp;建议提前</span>
						<form:input path="advanceNum" htmlEscape="false"   class="form-control digits required" style="width:10%;display:inline-block;"/>
						<span>&nbsp;&nbsp;&nbsp;天</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">市场价格:</label>
					<div class="col-sm-10">
				<form:input path="price" htmlEscape="false"   class="form-control number required" style="width:20%;display:inline-block;"/>
				<span>&nbsp;&nbsp;&nbsp;</span>
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
																value="<fmt:formatDate value="${linerLine.beginDate}" pattern="yyyy-MM-dd"/>" />
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
																value="<fmt:formatDate value="${linerLine.endDate}" pattern="yyyy-MM-dd"/>" />
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
												<input type="checkbox" id="weekDate[${status.index}]" 
													name="weekList" value="${a.id}" /></input>
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
									<input type="checkbox" id="dayDate[${status.index}]"
										 name="dayList" value="${a.id}"  /></input>
									<label for="dayDate[${status.index}]">${a.name}</label>
														</c:forEach>
													</div>
												</div>
											</div>
							</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">房间类型:</label>
					<div class="col-sm-10">
						<button type="button" class="btn btn-info"
											id="addRouteContent">添加房间</button>
								<ul class="nav nav-tabs" id="myTab2s"
											style="padding-left: 10px;">
											
								</ul>
								<div class="panel-body tab-content" id="myTab3s">
									
								</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">行程内容<i style="color: red;">*</i>:
					</label>
					<div class="col-sm-10">
						<button type="button" class="btn btn-info"
							id="addRouteContent1">添加天数</button>
						<button type="button" class="btn btn-danger"
							id="deleteRouteContent">删除最后一天</button>
					</div>
				</div>
				<input type="hidden" id="routeContentLength1"
									value="0" />
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-10">
						<ul class="nav nav-tabs" id="myTab2"
							style="padding-left: 10px;">
							
						</ul>
						<div class="panel-body tab-content" id="myTab3">
							
						</div>
					</div>
				</div>
				<!-- <div class="form-group">
					<label class="col-sm-2 control-label">视频资料:</label>
					<div class="col-sm-10">
						<textarea id="content" name="content" style="height:150px;"></textarea>
					</div>
				</div>
				 -->
		<c:if test="${fns:hasPermission('meiguotong:liner_line:linerLine:edit') || isAdd}">
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
					<h4 class="modal-title" id="myModalLabel">添加标签</h4>
				</div>
				<div class="modal-body">
					<div style="margin-bottom: 5px;">
						<label>标签名称：</label><input id="tagName" style="height: 30px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<button id="search" type="button" class="addAttr">查询</button>
					</div>

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td>标签ID</td>
								<td>标签名称</td>
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
<!-- model框确定按钮-->
	<script type="text/javascript">
		var number = $('.selectColor').length + 1;
		$("#sure").on("click",function() {
							var t = '';
							$("#myModal input[type='checkbox']")
									.each(
											function(i) {
												if ($(this).is(":checked")) {
													var id = $(this).val();
													var content = $(this).next().next().val();
													$(this).attr("checked","checked");
													t += '<tr class="selectColor"  id="selectColor_';
							   						t+=number;
							   						t+='" style="text-align:center;"><td class="width-15" width="100%"  style="table-layout:fixed;border:solid 1px #A9CBEE"><input class="colorid" name="tagId"  type="hidden" value="';
							   						t+=id;
							   						t+='" />';
													t += id;
													t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
													t += content;
													t += '</td><td class="width-15" width="100%"  style="table-layout;fixed;border:solid 1px #A9CBEE;">';
													t += '<button type="button" class="removeColor">删除</button>';
													t += '</td></tr>';
													number += 1;
												}
											});
							$("#tagTable").append(t);
							$("#myModal").modal('hide');
						})
					</script>
</body>
</html>