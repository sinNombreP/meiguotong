<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>账户流水管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script>
$(document).ready(function() {
	$('#memberIncomeZhiKeTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               /* //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true, */
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/sys/memberincome/memberIncome/ZhiKeData",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	window.location = "${ctx}/sys/memberIncome/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该账户流水记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/memberIncome/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#memberIncomeTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
               ,{
   		        field: 'no',
   		        title: '流水号',
   		        sortable: true
   		        
   		       
   		    }
			,{
   		        field: 'number',
   		        title: '会员账号',
   		        align: 'center',
   		        sortable: true
   		       
   		    },{
   		        field: 'nickName',
   		        title: '会员昵称',
   		        align: 'center',
   		        sortable: true
   		       
   		    }
   		 ,{
		        field: 'type',
		        title: '订单类型',
		        align: 'center',
		        sortable: true
	   		     ,formatter:function(value, row , index){
		        	if(value=="1"){
		        		return '包车租车';
		        	}else if(value=="2"){
		        		return '短程接送';
		        	}else if(value=="3"){
		        		return '接送机';
		        	}else if(value=="4"){
		        		return '常规路线';
		        	}else if(value=="5"){
		        		return '当地参团';
		        	}else if(value=="6"){
		        		return '邮轮';
		        	}else if(value=="7"){
		        		return '景点门票';
		        	}else if(value=="8"){
		        		return '当地玩家/导游';
		        	}else if(value=="9"){
		        		return '酒店';
		        	}else if(value=="10"){
		        		return '保险';
		        	}else if(value=="11"){
		        		return '旅游定制';
		        	}else if(value=="13"){
		        		return '商务定制';
		        	}else if(value=="14"){
		        		return '商务旅游';
		        	}else if(value=="15"){
		        		return '提现';
		        	}else if(value=="16"){
		        		return '退款';
		        	}
		         }
		    }
   		    
			,{
   		        field: 'income',
   		        title: '流动类型',
   		        align: 'center',
   		        sortable: true
	   		     ,formatter:function(value, row , index){
		        	if(value=="1"){
		        		return '收入';
		        	}else if(value=="2"){
		        		return '支出';
		        	}
		         }
   		    }
			
			,{
		        field: 'payType',
		        title: '支付类型',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(value=="1"){
		        		return '支付宝';
		        	}else if(value=="2"){
		        		return '微信支付';
		        	}else if(value=="3"){
		        		return '银联支付';
		        	}else if(value=="4"){
		        		return 'Paypal';
		        	}
		         }
		       
		    }
			,{
		        field: 'incomePrice',
		        title: '金额',
		        sortable: true
		       
		    }
			,{
   		        field: 'createDate',
   		        title: '时间',
   		        align: 'center',
   		        sortable: true
   		       
   		    }
			,{
		        field: 'operate',
		        title: '操作',
		        align: 'center',
		        sortable: true,
		        formatter:function(value, row , index){
	        		return [
	        			'<a href="${ctx}/sys/memberincome/memberIncome/ZhiKeform?id='+row.id+'" class="view" title="查看" >'+
						'<i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>',
						].join('');
		         }
		    }
			]
			
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#memberIncomeZhiKeTable').bootstrapTable("toggleView");
		}
	  
	  $('#memberIncomeZhiKeTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#memberIncomeZhiKeTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#memberIncomeZhiKeTable').bootstrapTable('getSelections').length!=1);
        });
		  
		/* $("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/sys/memberIncome/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jp.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jp.close(index);
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		}); */
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#memberIncomeZhiKeTable').bootstrapTable('refresh');
		});
	 
		
	});
		
  
</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">直客流水记录</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
		
			<form:form id="searchForm" modelAttribute="memberIncome" class="form form-horizontal well clearfix">
			 <div class="col-md-3">
				<label class="label-item single-overflow pull-left" title="搜索：">搜索：</label>
				<form:input path="number"  htmlEscape="false" class="form-control" autocomplete="off" placeholder="搜索手机/邮箱/昵称"/>	
			</div>
			<input name="memberid" value="${memberid}" type="hidden">
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  href="${ctx}/sys/memberIncome/list?memberid=${memberid}" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="sys:memberIncome:add">
				<a id="add" class="btn btn-primary" href="${ctx}/sys/memberIncome/form" title="账户流水"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="sys:memberIncome:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="sys:memberIncome:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:memberIncome:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/sys/memberIncome/import" method="post" enctype="multipart/form-data"
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
	<table id="memberIncomeZhiKeTable"   data-toolbar="#toolbar"></table>

	</div>
	</div>
	</div>
</body>
</html>