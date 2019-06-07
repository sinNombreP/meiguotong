<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>酒店管理管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                	if(!$("#cityId").val()){
						jp.error("酒店不能为空！");
						return;
					}
                	if(!$("#hotelId").val()){
						jp.error("酒店不能为空！");
						return;
					}
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
					if(!$("#hotelRoomDeviceId").val()){
						jp.error("房间设施不能为空！");
						return;
					}
					if(!$("input[name='beginDate'").val()){
						jp.error("日期范围不能为空！");
						return;
					}
					if(!$("input[name='endDate'").val()){
						jp.error("日期范围不能为空！");
						return;
					}
					if(!$("#money").val()){
						jp.error("价格不能为空！");
						return;
					}
					if(!/^[0-9,.]*$/.test($("#money").val().trim())){
						 jp.error("请输入正确的价格！");
						return;
					} 
					if(!$("#stockAll").val()){
						jp.error("库存不能为空！");
						return;
					}
					if(!/^0$|^[1-9]\d*$/.test($("#stockAll").val().trim())){
						jp.error("库存只能为整数！");
						return; 
					}
					if(!$("#roomName").val()){
						jp.error("房间名称不能为空！");
						return;
					}
					if(!$("#content").val()){
						jp.error("详情介绍不能为空！");
						return;
					}
                    jp.loading();
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $('#beginUpdateDate').datetimepicker({
                format: "YYYY-MM-DD"
            });
            $('#endUpdateDate').datetimepicker({
                format: "YYYY-MM-DD"
            });

            changeCity();

            
            $("#languageid").change(function(){
				$(".removeCity").remove();
				$("#hotelId").empty();
            	var url = "${ctx}/meiguotong/hotel/hotelRoom/getCity";
            	var languageid = $(this).val();
            	var params = {"languageid":languageid};
            	jp.post(url,params,function(data){
            		var list = data.body.list;
					var t='';
					for(var i in list){
						t+='<option class="removeCity" value="';
						t+=list[i].cityId
						t+='" >';
						t+=list[i].cityName
						t+='</option>';
					}
					$("#cityId").append(t);
            	})
            })
        });

        var ue = UE.getEditor('otherInfo');//获得去机器
        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        UE.Editor.prototype.getActionUrl = function (action) {
            if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
                return '${ctx}/sys/sysUser/uploadIMG'; //远程图片上传controller
            } else {
                return this._bkGetActionUrl.call(this, action);
            }
        }

        //动态调整iframe高度
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

        //城市改变
        function changeCity() {
            $("#hotelId").empty();
            var html = '<option value=""  label="请选择"></option>';
            var index = $('option:selected', '#cityId').index() - 1;
            <c:forEach items="${hotelRoomList}" var="result"  varStatus="status">
            if (index ==${status.index}) {
                <c:forEach items="${result.hotelRoomList}" var="result1">
                html += '<option value="${result1.hotelId}"';
                if (${result1.hotelId==hotelRoom.hotelId}) {
                    html += 'selected="selected"'
                }
                html += '>${result1.name}</option>';
                </c:forEach>
            }
            </c:forEach>
            $("#hotelId").append(html);
        }

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
                var fileValue = $("#src_" + divIndex).val();    //多个多图上传改class,id
                if (fileValue == null || fileValue == "") {
                    return;
                }
            }
            divIndex++;
            var html = '<div class="dv_pic_item">' +
                '<img src="" class="img_style" id="img_' + divIndex + '"/>' +
                '<input type="hidden" name="imgSrc" value=""  id="src_' + divIndex + '" class="imgSrc"/>' +
                '<input type="file" class="input_file_style" id="file_' + divIndex + '"/>' +
                '<a id="btn_' + divIndex + '" onclick="deleteElement(this)" class="deleteBtn">删除</a>' +
                '</div>';
            $(".dv_pic_box").append(html);
        };

        //删除图片
        function deleteElement(Obj) {
            Obj.parentNode.parentNode.removeChild(Obj.parentNode);
        }

        //获取房间设施数据
        function getHotelRoomDeviceList(){
            jp.post("${ctx}/meiguotong/hotelroomdevice/hotelRoomDevice/getHotelRoomDeviceList",{
                "languageId":$("#languageid").val(),
                "ids":$("#hotelRoomDeviceId").val(),
                "name":$("#searchContent").val()
            },function(data){
                if(data.success){
                    const list=data.body.hotelRoomDeviceList;
                    let html1 = '<tr><td style="text-align:center;" colspan="2">暂时无数据</td></tr>';
                    let html = "";
                    for (x in list){
                        html += '<tr>';
                        html +='<td><input type="checkbox" name="hotelRoomDeviceName" value="'+list[x].id+'"';
                        if(list[x].idFlag){html +=' checked="checked" ';}
                        html +='></td>';
                        html +='<td>'+list[x].name+'</td>';
                        html +='</tr>';
                    }
                    $("#tbody").empty();
                    $("#tbody").append(html || html1);
                }else{
                    jp.error(data.msg);
                }
            })
        }


        $(function () {

            $(".dv_pic_box").on("change", ".input_file_style", function (e) {
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
                    freader.onload = function (e) {
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


            var dateType = $("#dateType").val() - 1;
            if (dateType > -1) {
                $('#myTab li:eq(' + dateType + ') a').tab('show');
            } else {
                $("#dateType").val(1);
            }
            $('#myTab li').click(function (e) {
                var index = $(this).index();
                $('#myTab li:eq(' + index + ') a').tab('show');
                $("#dateType").val(index + 1);
            })




            //获取城市
            $(".wrapper-content").on("click","#hotelRoomDeviceName,#search,#reset",function(){
                $("#tbody").empty();
                if( $(this).attr("id") == "reset")  {
                    $("#searchContent").val("");
                }
                $('#myModal').modal('show');
                getHotelRoomDeviceList();
            })

            //选择城市
            $("#sure").on("click",function(){
                let ids = [];
                let names = [];
                $("#myModal input[type='checkbox']").each(function(i){
                    if($(this).prop('checked')){
                        ids.push($(this).val());
                        names.push($(this).parent().next().text());
                    }
                });
                $("#hotelRoomDeviceId").val(ids.join(","));
                $("#hotelRoomDeviceName").val(names.join(","));
                $("#myModal").modal('hide');
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

        .img_style {
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
                        <a class="panelButton" href="${ctx}/meiguotong/hotel/hotelRoom"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>

                <ul class="nav nav-tabs"
                    style="padding-left: 130px; padding-top: 20px;">
                    <li class="active"><a data-toggle="tab" href="#tab-1">通用</a></li>
                    <li class=""><a data-toggle="tab" href="#tab-2">详细信息</a></li>
                    <c:if test="${not empty hotelRoom.id}">
                        <li class=""><a data-toggle="tab" href="#tab-3">评价</a></li>
                    </c:if>
                </ul>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="hotelRoom" action="${ctx}/meiguotong/hotel/hotelRoom/save"
                               method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <form:hidden path="dateType" name="dateType" value="${hotelRoom.dateType}"/>
                        <%-- 		<input type="hidden" value="${hotelRoomList}" id="hotelRoomList" /> --%>
                        <sys:message content="${message}"/>
                        <div class="panel-body tab-content">
                            <div class="tab-pane fade in  active" id="tab-1">
                            	<c:if test="${empty hotelRoom.id}">
                           		 <div class="form-group">
                                    <label class="col-sm-2 control-label">选择语言<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-2">
                                        <form:select path="languageid"  class="form-control m-b required" >
                                            <form:options items="${comLanguageList}" itemLabel="content"
                                                          itemValue="id" htmlEscape="false"/>
                                        </form:select>
                                    </div>
                                 </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">选择酒店<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-2">
                                        <form:select path="cityId" id="cityId" class="form-control m-b required"
                                                     onchange="changeCity()">
                                            <form:option value="" label="请选择"/>
                                           <%--  <form:options items="${hotelRoomList}" itemLabel="cityName"
                                                          itemValue="cityId" htmlEscape="false"/> --%>
                                        </form:select>
                                    </div>
                                    <div class="col-sm-2">
                                        <form:select path="hotelId" class="form-control m-b required" id="hotelId">
                                            <form:option value="" label="请选择"/>
                                        </form:select>
                                    </div>
                                </div>
                                </c:if>
                                <c:if test="${not empty hotelRoom.id}">
                           		 <div class="form-group">
                                    <label class="col-sm-2 control-label">选择语言<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-2">
                                        <form:select path="languageid" disabled="true" class="form-control m-b required" >
                                            <form:options items="${comLanguageList}" itemLabel="content"
                                                          itemValue="id" htmlEscape="false"/>
                                        </form:select>
                                    </div>
                                 </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">选择酒店<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-2">
                                        <form:select path="cityId" id="cityId" disabled="true" class="form-control m-b required"
                                                     onchange="changeCity()">
                                            <form:option value="" label="请选择"/>
                                           <form:options items="${hotelRoomList}" itemLabel="cityName"
                                                          itemValue="cityId" htmlEscape="false"/> 
                                        </form:select>
                                    </div>
                                    <div class="col-sm-2">
                                        <form:select path="hotelId" disabled="true"  class="form-control m-b required" id="hotelId">
                                            <form:option value="" label="请选择"/>
                                        </form:select>
                                    </div>
                                </div>
                                </c:if>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">房间图片<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-10" style=" border:1px solid #cccccc;">
                                        <div class="dv_info_box">
                                            <div class="dv_pic_box">
                                                <c:if test="${not empty hotelRoom.imgUrl}">
                                                    <c:forEach var="img" items="${fn:split(hotelRoom.imgUrl, ',')}"
                                                               varStatus="status">
                                                        <div class="dv_pic_item">
                                                            <img src="${img}" class="img_style"
                                                                 id="img_${status.index+1}"/>
                                                            <input type="hidden" name="imgSrc" value="${img}"
                                                                   id="src_${status.index+1}" class="imgSrc"/>
                                                            <input type="file" class="input_file_style"
                                                                   id="file_${status.index+1}"/>
                                                            <a id="btn_${status.index+1}" onclick="deleteElement(this)"
                                                               class="deleteBtn">删除</a>
                                                        </div>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                            <button type="button" class="btn_add_pic" onclick="addDiv()">添加图片</button>
                                            <!--多个多图class修改-->
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">房间设施<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-10">
                                        <form:hidden path="hotelRoomDeviceId"/>
                                        <form:input path="hotelRoomDeviceName" htmlEscape="false" class="form-control required" readonly="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">价格设置：<i style="color: red;">*</i></label>
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
                                                                   value="<fmt:formatDate value="${hotelRoom.beginDate}" pattern="yyyy-MM-dd"/>"/>
                                                            <span class="input-group-addon"> <span
                                                                    class="glyphicon glyphicon-calendar"></span>
															</span>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-1">~</div>
                                                    <div class="col-xs-12 col-sm-5">
                                                        <div class='input-group date' id='endUpdateDate'
                                                             style="left: -10px;">
                                                            <input type='text' name="endDate"
                                                                   class="form-control required"
                                                                   value="<fmt:formatDate value="${hotelRoom.endDate}" pattern="yyyy-MM-dd"/>"/>
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
                                                                   name="weekList" value="${a.id}"
                                                                   <c:if test="${fn:contains(hotelRoom.weekDate,status.index+1)}">checked="checked"</c:if>  /></input>
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
                                                                   name="dayList" value="${a.id}"
                                                                   <c:if test="${a.digFlag==1}">checked="checked"</c:if>  /></input>
                                                            <label for="dayDate[${status.index}]">${a.name}</label>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-1 control-label">价格：</label>
                                                <div class="col-sm-4">
                                                    <form:input path="money" htmlEscape="false"
                                                                class="form-control required" placeholder="请输入价格"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-1 control-label">库存：</label>
                                                <div class="col-sm-4">
                                                    <form:input path="stockAll" htmlEscape="false"
                                                                class="form-control required"
                                                                />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="tab-2">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">房间名称<i style="color: red;">*</i>：</label>
                                    <div class="col-sm-10">
                                        <form:input path="roomName" htmlEscape="false" class="form-control required"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">详情介绍 <i style="color: red;">*</i>：</label>
                                    <div class="col-sm-10">
                                        <form:textarea path="content" rows="4" maxlength="255" htmlEscape="false"
                                                       class="form-control required"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">其他设置<i style="color: red;"></i>：</label>
                                    <div class="col-sm-10">
                                        <textarea id="otherInfo" name="otherInfo" rows="55" cols="80"
                                                  style="width: 100%;height:300px">${hotelRoom.otherInfo}</textarea>

                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="tab-3">
                                <iframe src="${ctx}/meiguotong/comcomment/comComment/goComment?type=9&typeId=${hotelRoom.id}"
                                        scrolling="no" frameborder="0" id="frame_content1" style="width:100%;"></iframe>
                            </div>
                        </div>
                        <c:if test="${fns:hasPermission('meiguotong:hotel:hotelRoom:edit') || isAdd}">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <div class="form-group text-center">
                                    <div>
                                        <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                                data-loading-text="正在提交...">提 交
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </form:form>
                </div>
            </div>
        </div>
    </div>


    <!-- 设施模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">添加房间设施</h4>
                </div>
                <div class="modal-body">
                    <div>
                        <input id="searchContent" placeholder="输入设施名称" class="form-control col-sm-2" style="width:40%;margin-right:15px;"/>
                        <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
                        <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
                    </div>
                    <br/>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th></th>
                            <th>设施名称</th>
                        </tr>
                        </thead>
                        <tbody id="tbody"></tbody>

                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="sure">提交更改</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>


</div>
</body>
</html>