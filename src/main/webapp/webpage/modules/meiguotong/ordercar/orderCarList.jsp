<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>车辆订单表管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="orderCarList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
	<c:if test='${type==1 && ordertype==1}'>
		<h3 class="panel-title">包车租车订单列表</h3>
	</c:if>
	<c:if test='${type==1 && ordertype==2}'>
		<h3 class="panel-title">包车租车售后列表</h3>
	</c:if>
	<c:if test='${type==2 && ordertype==1}'>
		<h3 class="panel-title">短程接送订单列表</h3>
	</c:if>
	<c:if test='${type==2 && ordertype==2}'>
		<h3 class="panel-title">短程接送售后列表</h3>
	</c:if>
	<c:if test='${type==3 && ordertype==1}'>
		<h3 class="panel-title">接送机订单列表</h3>
	</c:if>
	<c:if test='${type==3 && ordertype==2}'>
		<h3 class="panel-title">接送机售后列表</h3>
	</c:if>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="orderCar" class="form form-horizontal well clearfix">
			 <%-- <form:input id="type" path="type" type="hidden"/>
			 <form:input id="ordertype" path="ordertype" type="hidden"/> --%>
			 <input id="type" type="hidden" name="type" value="${type}">
			 <input id="ordertype" type="hidden" name="ordertype" value="${ordertype}">
			 
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" >订单状态：</label>
				<select id="status" name="status" htmlEscape="false" maxlength="255"  class=" form-control">
			         <option value="">全部</option>
			         <c:if test='${ordertype==1}'>
			         <option value="1">待付款</option>
			         <option value="2">待确认</option>
			         <option value="3">待出行</option>
			         <option value="4">待点评</option>
			         <option value="5">已完成</option>
			         <option value="6">已取消</option>
			         </c:if>
			         <c:if test='${ordertype==2}'>
			          <option value="7">待审核</option>
			         <option value="8">退款中</option>
			         <option value="9">同意退款</option>
			         <option value="10">退款失败</option>
			         </c:if>
			    </select>
			</div>
			
			
			<c:if test="${userType==1}">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left">供应商：</label>
				<select id="agentid" name="agentid"   class=" form-control">
			         <option value="">全部</option>
					<c:forEach items="${agent}" var="item">
			         <option value="${item.agentid}">${item.companyName}</option>
			         </c:forEach>
			    </select>
			</div>
			</c:if>
			<c:if test="${ordertype==1}">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单状态">今日订单：</label>
				<form:select path="dataFlag"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:option value="1" label="今日订单"/>
					<form:option value="2" label="今日出行"/>
				</form:select>
			</div>
			</c:if>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left">下单人类型：</label>
				<form:select path="memberType"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:option value="1" label="普通用户"/>
					<form:option value="2" label="旅行社"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" >下单人名称：</label>
				<form:input path="nickName" class="form-control m-b" />
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单状态">搜索订单：</label>
				<form:input path="searchContent" class="form-control m-b" placeholder="订单号/下单人名称/产品名称"/>
			</div>
			<c:if test="${ordertype==1}">
			 <div class="col-xs-12 col-sm-6">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" >&nbsp;使用时间：</label>
					<div class="col-xs-12">
						   <div class="col-xs-12 col-sm-5">
					        	  <div class='input-group date' id='beginDate' style="left: -10px;" >
					                   <input type='text'  name="beginDate" class="form-control"  />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					             </div>	
					        </div>
					        <div class="col-xs-12 col-sm-1">
					        		~
					       	</div>
					        <div class="col-xs-12 col-sm-5">
					          	<div class='input-group date' id='endDate' style="left: -10px;" >
					                   <input type='text'  name="endDate" class="form-control" />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					           	</div>	
					        </div>
					</div>
				</div>
			</div>
			</c:if>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="searchs" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="meiguotong:ordercar:orderCar:add">
				<a id="add" class="btn btn-primary" href="${ctx}/meiguotong/ordercar/orderCar/form" title="车辆订单表"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:ordercar:orderCar:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:ordercar:orderCar:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:ordercar:orderCar:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/meiguotong/ordercar/orderCar/import" method="post" enctype="multipart/form-data"
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
	<table id="orderCarTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="meiguotong:ordercar:orderCar:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="meiguotong:ordercar:orderCar:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
<script type="text/javascript">

</script>
</html>