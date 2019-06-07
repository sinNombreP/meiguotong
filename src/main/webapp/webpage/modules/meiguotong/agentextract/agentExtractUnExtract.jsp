<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>代理商提现管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="agentExtractUnExtract.js" %>
	
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
			<h3 class="panel-title"> 
				<a class="panelButton" href="javascript:history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
			<form:form id="searchForm1" modelAttribute="orderSys" class="form form-horizontal well clearfix" style="border:none;background-color:white;margin:0px;padding:0px;">
			<div style="margin-bottom: 5px;">
				<label>订单类型：</label>
					<select id="type" name="type" htmlEscape="false" maxlength="255"  style="width:300px;display:inline-block;" class="form-control" >
				         <option value="">请选择....</option>
				         <option value="1">包车租车</option>
						 <option value="2">短程接送</option>
						 <option value="3">接送机</option>
						 <option value="4">常规路线</option>
						 <option value="5">当地参团</option>
						 <option value="6">游轮</option>
						 <option value="7">景点门票</option>
						 <option value="8">当地玩家/导游</option>
						 <option value="9">酒店</option>
						 <option value="10">保险</option>
						 <option value="15">定制租车</option>
				    </select>
					<button id="search" type="button" class="btn btn-primary">查询</button>
				</div>
			
		<!--  <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			 </div>
	    </div>	 -->
	</form:form>
	
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
	        	<!-- <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a> -->
		    </div>
		
	<!-- 表格 -->
	<table id="agentExtractUnExtractTable"   data-toolbar="#toolbar"></table>
	<div class="col-lg-10"></div>
	<div class="col-lg-6">
          <div class="form-group text-center">
              <div>
              <form:form id="searchForm2" action="${ctx}/meiguotong/agentextract/agentExtract/save" method="post" class="form form-horizontal well clearfix" style="border:none;background-color:white;">
                  <input id="orderids" type="hidden" name="orderids">
                  <button id="submitids" type="button" class="btn btn-primary btn-block btn-lg btn-parsley" >提 交</button>
                  </form:form>
              </div>
          </div>
     </div>
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
	
	
	
</body>

</html>