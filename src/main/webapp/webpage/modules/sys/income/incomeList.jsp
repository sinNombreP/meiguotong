<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台流水管理管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="incomeList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">平台收入汇总</h3>
		
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="income" class="form form-horizontal well clearfix">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="推送类型：">产品类型：</label>
				<select id="type" name="type" htmlEscape="false" maxlength="255"  class=" form-control">
			         <option value="">请选择...</option>
			         <option value="1">包车租车</option>
					<option value="2">短程接送</option>
					<option value="3">接送机</option>
					<option value="4">常规路线</option>
					<option value="5">当地参团</option>
					<option value="6">邮轮</option>
					<option value="7">景点门票</option>
					<option value="8">当地玩家/导游</option>
					<option value="9">酒店</option>
					<option value="10">保险</option>
					<option value="11">旅游定制</option>
					<option value="13">商务定制</option>
					<option value="14">商务旅游</option>
					<option value="15">定制租车</option>
			    </select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="推送类型：">推送类型：</label>
				<select id="payType" name="payType" htmlEscape="false" maxlength="255"  class=" form-control">
			         <option value="">请选择...</option>
			         <option value="1">支付宝</option>
					<option value="2">微信支付</option>
					<option value="3">银联支付</option>
					<option value="4">Paypal</option>
			    </select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="推送类型：">推送类型：</label>
				<select id="status" name="status" htmlEscape="false" maxlength="255"  class=" form-control">
			         <option value="">请选择...</option>
			         <option value="1">待确认</option>
					<option value="2">已完成</option>
					<option value="3">已取消</option>
			    </select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="内容：">流水号：</label>
				<form:input path="no" htmlEscape="false" maxlength="255"  class=" form-control"/>
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
			<shiro:hasPermission name="sys:income:income:add">
				<a id="add" class="btn btn-primary" href="${ctx}/sys/income/income/form" title="平台流水管理"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:income:income:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:income:income:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:income:income:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/sys/income/income/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				<br>
				<a style="margin-left: 100px;color: black;">今日订单金额：<i style="color: red;">￥${income.toDay}</i></a>
				<a style="margin-left: 20px;color: black;">昨日订单金额：<i style="color: red;">￥${income.torrow}</i></a>
				<a style="margin-left: 20px;color: black;">本周订单金额：<i style="color: red;">￥${income.toWeek}</i></a>
				<a style="margin-left: 20px;color: black;">本月订单金额：<i style="color: red;">￥${income.toMonth}</i></a>
				<a style="margin-left: 20px;color: black;">总销售额：<i style="color: red;">￥${income.all}</i></a>
		    </div>
		
	<!-- 表格 -->
	<table id="incomeTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="sys:income:income:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys:income:income:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>