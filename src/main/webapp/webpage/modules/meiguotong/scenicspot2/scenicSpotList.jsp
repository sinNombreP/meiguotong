<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>景点管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="scenicSpotList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	
	<div class="panel-heading" style="width:90%;float:left;">
		<h3 class="panel-title">景点列表</h3>
	</div>
	<div class="panel-heading" style="width:10%;float:left;">
		<h3 class="panel-title" style="cursor: pointer;" onclick="back(${scenicSpot.countryId})">返回</h3>
	</div>
	<div style="clear:both;"></div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<input type="hidden" value="${scenicSpot.cityId}" id="cityId">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="scenicSpot" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" >选择语言：</label>
				<form:select path="languageId"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:options items="${comLanguageList}" itemLabel="content" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="景点名称：">景点名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<%-- <shiro:hasPermission name="meiguotong:scenicspot2:scenicSpot:add"> --%>
				<a id="add" class="btn btn-primary" href="${ctx}/meiguotong/scenicspot/scenicSpot/AddForm?cityId=${scenicSpot.cityId}" ><i class="glyphicon glyphicon-plus"></i> 新建</a>
			<%-- </shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:scenicspot2:scenicSpot:edit"> 
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:scenicspot2:scenicSpot:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="meiguotong:scenicspot2:scenicSpot:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/meiguotong/scenicspot2/scenicSpot/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
			<a id="add" class="btn btn-primary" href="${ctx}/meiguotong/comcity/comCity/list?provinceId=${scenicSpot.countryId}" title="景点门票">
			 返回城市列表</a>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		
		    </div>
	<!-- 表格 -->
	<table id="scenicSpotTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="meiguotong:scenicspot2:scenicSpot:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="meiguotong:scenicspot2:scenicSpot:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
	<!-- 代理商列表 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">配置供应商</h4>
					</div>
					<div class="modal-body">
						<div style="margin-bottom: 5px;">
							<label>供应商账号：</label><input id="agentName" style="height: 30px;" />&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="searchAgent" type="button" class="addAttr">查询</button>
						</div> 
						<input type="hidden" id="scenicSpotid" value="" />
						<table class="table table-bordered">
							<thead>
								<tr>
									<td></td>
									<td>供应商id</td>
									<td>账号</td>
									<td>公司名称</td>
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
		<!-- 直营model框确定按钮-->
	<script type="text/javascript">
		$("#sure").on("click",function() {
							
							var t = "";
							$("#myModal input[type='radio']").each(
											function(i) {
												if ($(this).is(":checked")) {
													var agentid = $(this).val();
													var scenicSpotid = $("#scenicSpotid").val();
													var url="${ctx}/meiguotong/scenicspot/scenicSpot/updateUser";
													var params={"supplierId":agentid,"id":scenicSpotid}
										  	  		jp.post(url,params,function(data){
										  	  			if(data.success){
										  	  				jp.success("配置供应商成功");
										  	  			}else{
										  	  				jp.error("配置供应商失败");
										  	  			}
										  	 		})
												}
											});
							$("#myModal").modal('hide');
						})
			</script>
</body>
</html>