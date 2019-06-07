<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>代理商提现管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="agentExtractList.js" %>
<style type="text/css">
		.control-left{
			text-align: left !important;
			font-size: 14px;
			 vertical-align:middle;
		}
		.div-head{
			font-size: 18px;
			color: #FF9900;
		}
	</style>	
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">代理商提现列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="agentExtract" class="form form-horizontal well clearfix">
			<input id="userType" value="${userType}"  type="hidden" name="userType"/>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="推送类型：">状态：</label>
				<select id="send" name="status" htmlEscape="false" maxlength="255"  class=" form-control">
			         <option value="">请选择....</option>
			         <option value="1">已申请</option>
			         <option value="2">同意申请</option>
			         <option value="3">拒绝申请</option>
			         <option value="4">已完成</option>
			    </select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" >提现流水号：</label>
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
			<shiro:hasPermission name="meiguotong:agentextract:agentExtract:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:agentextract:agentExtract:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:agentextract:agentExtract:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="meiguotong:agentextract:agentExtract:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/meiguotong/agentextract/agentExtract/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				<c:if test="${userType==2}">
				<a class="btn btn-primary" onclick="add()" ><i class="glyphicon glyphicon-plus"></i> 发起提现账单</a>
				</c:if>
		    </div>
		
	<!-- 表格 -->
	<table id="agentExtractTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="meiguotong:agentextract:agentExtract:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="meiguotong:agentextract:agentExtract:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
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
					<h4 class="modal-title" id="myModalLabel">查看对账单详情</h4>
				</div>
				<div class="modal-body">
				 <div class="form-group">
					<label class="col-sm-2 control-label" style="width:100px;text-align: right !important;">提现订单号：</label>
					<div  class="col-sm-3 control-label" style="text-align: left !important;">
					<p class="no"></p>
					</div>
				</div>
				<br>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="width:100px;text-align: right !important;">提现时间：</label>
					<div  class=" col-sm-3 control-label " style="text-align: left !important;">
					<p class="createDate"></p>
					</div>
				</div>
				<br>
				<div class="form-group">
					<label class="col-sm-2 control-label" style="width:100px;text-align: right !important;">提现金额：</label>
					<div class="col-sm-3 control-label " style="text-align: left !important;">
					<p class="extract"></p>
					</div>
				</div> 

					<table class="table table-bordered">
						<thead>
							<tr>
								<td></td>
								<td style="display:none">id</td>
								<td>订单号</td>
								<td>标题</td>
								<td>类型</td>
								<td>订单完成时间</td>
								<td>合同金额</td>
								<td>状态</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="sureOne">确定</button>
				</div>
			</div>
		</div>
	</div>
	
</body>

</html>