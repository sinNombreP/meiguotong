<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>保险订单管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="orderInsuranceList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">保险订单列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="orderInsurance" class="form form-horizontal well clearfix">
			<form:hidden path="type"/>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单状态">订单状态：</label>
				<form:select path="status"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<c:if test="${orderInsurance.type==1}">
					<form:option value="1" label="待付款"/>
					<form:option value="2" label="待确定"/>
					<form:option value="3" label="待出行"/>
					<form:option value="4" label="待评价"/>
					<form:option value="5" label="已完成"/>
					<form:option value="6" label="取消订单"/>
					</c:if>
					<c:if test="${orderInsurance.type==2}">
					<form:option value="7" label="申请退款"/>
					<form:option value="8" label="同意退款"/>
					<form:option value="9" label="退款成功"/>
					<form:option value="10" label="拒绝退款"/>
					</c:if>
				</form:select>
			</div>
			<c:if test="${orderInsurance.type==1}">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单状态">今日订单：</label>
				<form:select path="dataFlag"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:option value="1" label="今日订单"/>
					<form:option value="2" label="今日出行"/>
				</form:select>
			</div>
			</c:if>
			<c:if test="${userType==1}">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" >供应商：</label>
				<form:select path="agentid"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<c:forEach items="${agent}" var="item">
						<form:option value="${item.agentid }" label="${item.companyName}"/>
					</c:forEach>
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
				<form:input path="searchContent" class="form-control m-b" placeholder="订单号/下单人ID/产品名称/产品ID"/>
			</div>
			<c:if test="${orderInsurance.type==1}">
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
			<shiro:hasPermission name="meiguotong:insurance:orderInsurance:add">
				<a id="add" class="btn btn-primary" href="${ctx}/meiguotong/insurance/orderInsurance/form" title="保险订单"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:insurance:orderInsurance:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:insurance:orderInsurance:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:insurance:orderInsurance:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/meiguotong/insurance/orderInsurance/import" method="post" enctype="multipart/form-data"
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
	<table id="orderInsuranceTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="meiguotong:insurance:orderInsurance:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="meiguotong:insurance:orderInsurance:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>