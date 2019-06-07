<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	
	 if ($("#ordertype").val()==1) {
		 $('#orderCarTable').bootstrapTable({
			
			  //请求方法
	               method: 'get',
	               //类型json
	               dataType: "json",
	               //显示刷新按钮
	            /*   showRefresh: true,
	               //显示切换手机试图按钮
	               showToggle: true,
	               //显示 内容列下拉框
	    	       showColumns: true,
	    	       //显示到处按钮
	    	       showExport: true,
	    	       //显示切换分页按钮
	    	       showPaginationSwitch: true,*/
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
	               url: "${ctx}/meiguotong/ordercar/orderCar/data",
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
	                   	window.location = "${ctx}/meiguotong/ordercar/orderCar/form?id=" + row.id;
	                   } else if($el.data("item") == "delete"){
	                        jp.confirm('确认要删除该车辆订单表记录吗？', function(){
	                       	jp.loading();
	                       	jp.get("${ctx}/meiguotong/ordercar/orderCar/delete?id="+row.id, function(data){
	                   	  		if(data.success){
	                   	  			$('#orderCarTable').bootstrapTable('refresh');
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
			        field: 'orderNo',
			        title: '订单号',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'carName',
			        title: '车辆名称',
			        align: 'center',
			        sortable: true
			       
			    }
			    ,{
			        field: 'contactsName',
			        title: '联系信息',
			        align: 'center',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	return row.contactsName+","+row.contactsMobile+","+row.remark
			         }
			       
			    },{
			        field: 'companyName',
			        title: '供应商',
			        align: 'center',
			        sortable: true
			       
			    }
			    ,{
			        field: 'memberType',
			        title: '下单人类型',
			        sortable: true,
			        formatter:function(value,row,index){
				    	if(value == 1) return "普通用户";
				    	if(value == 2) return "旅行社";
			        }
			       
			    }
			    ,{
			        field: 'nickName',
			        title: '下单人名称',
			        sortable: true
			       
			    },{
			        field: 'createDate',
			        title: '下单时间',
			        align: 'center',
			        sortable: true
			    }
			    ,{
			        field: 'useDate',
			        title: '用车时间',
			        align: 'center',
			        sortable: true
			    },{
			        field: 'price',
			        title: '金额',
			        sortable: true
			    },{
			        field: 'status',
			        title: '状态',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	if (value==1) {
			        		return "待付款";
						}
			        	if (value==2) {
							return "待确认";
						}
			        	if (value==3) {
							return "待出行";
						}
			        	if (value==4) {
			        		return "待评价";
						}
			        	if (value==5) {
							return "已完成";
						}
			        	if (value==6) {
							return "取消订单";
						}
			         }
			    }
			    ,{
	                field: 'operate',
	                title: '操作',
	                align: 'center',
	                events: {
	    		        'click .console': function (e, value, row, index) {
	    		        	jp.confirm('确认要取消订单吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/ordercar/orderCar/changStatus?status=6&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#orderCarTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        'click .affirm': function (e, value, row, index) {
	    		        	jp.confirm('要确认订单吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/ordercar/orderCar/changStatus?status=3&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#orderCarTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        'click .refund': function (e, value, row, index) {
	    		        	jp.confirm('确认申请退款吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/ordercar/orderCar/changStatus?status=7&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#orderCarTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        },
	                formatter:  function operateFormatter(value, row, index) {
	    		        /*return [
							'<a href="${ctx}/meiguotong/compush/comPush/form?id='+row.id+'" class="view" title="查看" >'+
							'<i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>'
	    		        ].join('');*/
	                	var s='<a onclick="view('+row.id+')" id="view" class="view"><i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>';
	                	if (row.status==1) {
							s+='<a href="#" class="console no" style="margin:2px;"><i class="fa fa-edit btn btn-danger btn-xs">取消订单</i> </a>'
						}
	                	if (row.status==2) {
	                		s+='<a href="#" class="affirm yes" title="确认"><i class="fa fa-edit btn btn-warning btn-xs">确认</i> </a>'
	                		s+='<a href="#" class="console no" style="margin:2px;"><i class="fa fa-edit btn btn-danger btn-xs">取消订单</i> </a>'
						}
	                	if (row.status==3) {
	                		s+='<a href="#" class="console no" style="margin:2px;"><i class="fa fa-edit btn btn-danger btn-xs">取消订单</i> </a>'
						}
	                	return s;
	    		    }
	            }
			     ]
			 
			});
	 }
	 if ($("#ordertype").val()==2) {
		 $('#orderCarTable').bootstrapTable({
				
				
			  //请求方法
	               method: 'get',
	               //类型json
	               dataType: "json",
	               //显示刷新按钮
	               /*showRefresh: true,
	               //显示切换手机试图按钮
	               showToggle: true,
	               //显示 内容列下拉框
	    	       showColumns: true,
	    	       //显示到处按钮
	    	       showExport: true,
	    	       //显示切换分页按钮
	    	       showPaginationSwitch: true,*/
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
	               url: "${ctx}/meiguotong/ordercar/orderCar/data",
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
	                   	window.location = "${ctx}/meiguotong/ordercar/orderCar/form?id=" + row.id;
	                   } else if($el.data("item") == "delete"){
	                        jp.confirm('确认要删除该车辆订单表记录吗？', function(){
	                       	jp.loading();
	                       	jp.get("${ctx}/meiguotong/ordercar/orderCar/delete?id="+row.id, function(data){
	                   	  		if(data.success){
	                   	  			$('#orderCarTable').bootstrapTable('refresh');
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
			       
			    },{
			        field: 'afterNo',
			        title: '售后订单',
			        align: 'center',
			        sortable: true
			        
			    }
				,{
			        field: 'orderNo',
			        title: '订单号',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'carName',
			        title: '车辆名称',
			        align: 'center',
			        sortable: true
			       
			    },{
			        field: 'refundReason',
			        title: '退款原因',
			        align: 'center',
			        sortable: true
			       
			    },{
			        field: 'refundInfo',
			        title: '退款说明',
			        align: 'center',
			        sortable: true
			       
			    }
			    ,{
			        field: 'companyName',
			        title: '供应商',
			        align: 'center',
			        sortable: true
			       
			    }
			    ,{
			        field: 'memberType',
			        title: '下单人类型',
			        sortable: true,
			        formatter:function(value,row,index){
				    	if(value == 1) return "普通用户";
				    	if(value == 2) return "旅行社";
			        }
			       
			    }
			    ,{
			        field: 'nickName',
			        title: '下单人名称',
			        sortable: true
			       
			    },{
			        field: 'createDate',
			        title: '下单时间',
			        align: 'center',
			        sortable: true
			    }
			    ,{
			        field: 'useDate',
			        title: '用车时间',
			        align: 'center',
			        sortable: true
			    }
			    ,{
			        field: 'price',
			        title: '订单金额',
			        sortable: true
			    }
			    ,{
			        field: 'price',
			        title: '退款金额',
			        sortable: true
			    }
			    ,{
			        field: 'status',
			        title: '状态',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	if (value==7) {
			        		return "待审核";
						}
			        	if (value==8) {
							return "退款中";
						}
			        	if (value==9) {
							return "退款成功";
						}
			        	if (value==9) {
							return "退款失败";
						}
			         }
			    }
			    ,{
	                field: 'operate',
	                title: '操作',
	                align: 'center',
	                events: {
	    		        'click .yes': function (e, value, row, index) {
	    		        	jp.confirm('确认同意退款吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/ordercar/orderCar/changStatus?status=8&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#orderCarTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        'click .no': function (e, value, row, index) {
	    		        	jp.confirm('确认拒绝退款吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/ordercar/orderCar/changStatus?status=10&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#orderCarTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        'click .sure': function (e, value, row, index) {
	    		        	jp.confirm('确认退款吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/ordercar/orderCar/changStatus?status=9&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#orderCarTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        },
	                formatter:  function operateFormatter(value, row, index) {
	    		        /*return [
							'<a href="${ctx}/meiguotong/compush/comPush/form?id='+row.id+'" class="view" title="查看" >'+
							'<i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>'
	    		        ].join('');*/
	                	var s='<a onclick="view('+row.id+')" id="view" class="view"><i class="fa fa-eye btn btn-success btn-xs">查看</i> </a>';
	                	if (row.status==7) {
							s+='<a href="#" class="yes"  title="同意退款"><i class="fa fa-edit btn btn-warning btn-xs">同意退款</i> </a>';
							s+='<a href="#" class="no" title="拒绝退款"><i class="fa fa-edit btn btn-danger btn-xs">拒绝退款</i> </a>';
						}
	                	if (row.status==8) {
	                		s+='<a href="#" class="sure"  title="确定退款"><i class="fa fa-edit btn btn-warning btn-xs">确定退款</i> </a>';
						}
	                	return s;
	    		    }
	            }
			     ]
			
			});
		}
	
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#orderCarTable').bootstrapTable("toggleView");
		}
	  
	  $('#orderCarTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#orderCarTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#orderCarTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/meiguotong/ordercar/orderCar/import/template';
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
		});
		    
	  $("#searchs").click("click", function() {// 绑定查询按扭
		  $('#orderCarTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input[type!=hidden]").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#orderCarTable').bootstrapTable('refresh');
		});
		
	//调用日期
		$('#beginDate').datetimepicker({
			 format: "YYYY-MM-DD"
		});	
		$('#endDate').datetimepicker({
			 format: "YYYY-MM-DD"
		});	
	});
		
  function getIdSelections() {
        return $.map($("#orderCarTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该车辆订单表记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/meiguotong/ordercar/orderCar/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#orderCarTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/meiguotong/ordercar/orderCar/form?id=" + getIdSelections();
  }
  
  function view(id){
	  window.location = "${ctx}/meiguotong/ordercar/orderCar/form?id=" +id;
  }
  
</script>