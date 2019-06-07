<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<meta charset="utf-8">
	<title>ECharts</title>
	<meta name="decorator" content="ani"/>
	<!-- 引入 echarts.js -->
	<%@ include file="/webpage/include/echarts.jsp"%>
</head>
<body class="bg-white">
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->

<div class="wrapper wrapper-content">
	<div class=" panel-primary">
	<div class="panel-body">
	<h3>比例统计</h3>
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="comPush" class="form form-horizontal well clearfix">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left">统计数据：</label>
				<select id="type" name="type" htmlEscape="false" maxlength="255"  class=" form-control">
			         <option value="1">订单数量</option>
			         <option value="2">订单额度</option>
			    </select>
			</div>
			<div class="col-xs-12 col-sm-6">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" >&nbsp;使用时间：</label>
					<div class="col-xs-12">
						   <div class="col-xs-12 col-sm-5">
					        	  <div class='input-group date' id='beginDate' style="left: -10px;" >
					                   <input type='text' id="begin" name="beginDate" class="form-control"  />
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
					                   <input type='text' id="end"  name="endDate" class="form-control" />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					           	</div>	
					        </div>
					</div>
				</div>
			</div>
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	</form:form>
	</div>
	</div>
	</div>
	
	</div>
	</div>
	</div>


<div id="main" style="width: 100%;height: 50%"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例4
    var myChart = echarts.init(document.getElementById('main'));
    window.onresize = myChart.resize;
    $(function () {

        jp.get("${ctx}/meiguotong/statistics/Statistics/proportion?type=1", function (option) {
            // 指定图表的配置项和数据
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        })
        $("#search").click("click", function() {// 绑定查询按扭
        	var params={"type":$("#type").val(),"begin":$("#begin").val(),"end":$("#end").val()}
        	jp.post("${ctx}/meiguotong/statistics/Statistics/proportion",params, function (option) {
                // 指定图表的配置项和数据
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            })
    		});
        
        $("#reset").click("click", function() {// 绑定查询按扭
    		  $("#searchForm  input[type!=hidden]").val("");
    		});
        
      //调用日期
    	$('#beginDate').datetimepicker({
    		 format: "YYYY-MM-DD"
    	});	
    	$('#endDate').datetimepicker({
    		 format: "YYYY-MM-DD"
    	});	
    })

    

</script>
</body>
</html>