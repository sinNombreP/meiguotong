<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>
	<c:if test="${route.type==1}">常规路线管理</c:if>
	<c:if test="${route.type==2}">当地参团管理</c:if>
	</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="routeList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
	<c:if test="${route.type==1}">	常规路线列表</c:if>
	<c:if test="${route.type==2}">	当地参团列表</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="route" class="form form-horizontal well clearfix">
			<form:hidden path="type"/>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="语言：">语言：</label>
				<form:select path="language"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:options items="${comLanguageList}" itemLabel="content" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="1审核中2已拒绝3正常4禁用：">状态：</label>
				<form:select path="status"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:option value="1" label="审核中"/>
					<form:option value="2" label="已拒绝"/>
					<form:option value="3" label="正常"/>
					<form:option value="4" label="禁用"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="到达城市：">目的地：</label>
<%-- 				<form:select path="endCity"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		
				</form:select> --%>
					<!-- 在输入框加入id -->
					<%-- <input type="text" name="endCity" class="cityinput form-control" readonly  id="citySelect" value="${requestScope.content.city}">
					<script type="text/javascript" src="${ctxStatic}/city/js/cityselect.js"></script>
					  <link rel="stylesheet" href="${ctxStatic}/city/css/cityselect.css">
					<script type="text/javascript">
						var test=new Vcity.CitySelector({input:'citySelect'});
					</script> --%>
					<form:input path="endCityContent"  class="form-control m-b"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="标签属性id：">属性：</label>
				<form:select path="labelAttrid"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:options items="${comTagList}" itemLabel="content" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
			<c:if test="${userType==1}">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="供应商ID：">供应商：</label>
				<form:select path="agentid"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:options items="${sysUserList}" itemLabel="companyName" itemValue="agentid" htmlEscape="false"/>
				</form:select>
			</div>
			</c:if>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="编号：">编号：</label>
				<form:input path="no"  class="form-control m-b"/>
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
				<c:if test="${userType==2}">
		<%-- 	<shiro:hasPermission name="meiguotong:product:route:add"> --%>
				<a id="add" class="btn btn-primary" href="${ctx}/meiguotong/product/route/form?type=${route.type}" title="当地参团"><i class="glyphicon glyphicon-plus"></i> 新建</a>
<%-- 			</shiro:hasPermission> --%>
				</c:if>
			<shiro:hasPermission name="meiguotong:product:route:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:product:route:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:product:route:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/meiguotong/product/route/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="routeTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="meiguotong:product:route:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="meiguotong:product:route:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>