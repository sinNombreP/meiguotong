<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#orderLinerTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
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
               url: "${ctx}/meiguotong/orderliner/orderLiner/data",
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
                   	window.location = "${ctx}/meiguotong/orderliner/orderLiner/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该邮轮订单表记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/meiguotong/orderliner/orderLiner/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#orderLinerTable').bootstrapTable('refresh');
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
		        sortable: true
		    }
			,{
		        field: 'productName',
		        title: '产品名称',
		        sortable: true
		    }
			,{
		        field: 'refundReason',
		        title: '联系信息',
		        sortable: true
		    }
			,{
		        field: 'refundInfo',
		        title: '邮轮公司',
		        sortable: true
		    }
			,{
		        field: 'memberId',
		        title: '下单人ID',
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
		       
		    }
			,{
		        field: 'createDate',
		        title: '下单时间',
		        sortable: true
		    }
			,{
		        field: 'useDate',
		        title: '使用时间',
		        sortable: true
		    }
			,{
		        field: 'price',
		        title: '订单金额',
		        sortable: true
		    }
			,{
		        field: 'status',
		        title: '订单状态',
		        sortable: true,
		        formatter:function(value,row,index){
		        	if(value==1){
		        		return "待付款";
		        	}else if(value==2){
		        		return "待确认";
		        	}else if(value==3){
		        		return "待出行";
		        	}else if(value==4){
		        		return "待点评";
		        	}else if(value==5){
		        		return "已完成";
		        	}else if(value==6){
		        		return "已取消";
		        	}else if(value==7){
		        		return "申请退款";
		        	}else if(value==8){
		        		return "退款成功";
		        	}else if(value==9){
		        		return "退款失败";
		        	}
		        }
		       
		    }
			, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {                
                	'click .delete': function (e, value, row, index) { 
    		        	jp.confirm('确认取消订单吗？', function(){
                           	jp.loading();
                           	jp.get("${ctx}/meiguotong/orderliner/orderLiner/status?status=6&id="+row.id, function(data){
                       	  		if(data.success){
                       	  			$('#orderLinerTable').bootstrapTable('refresh');
                       	  			jp.success(data.msg);
                       	  		}else{
                       	  			jp.error(data.msg);
                       	  		}
                       	  	})
                       	   
                       	});
    		        },
			'click .yes': function (e, value, row, index) { 
	        	jp.confirm('确认吗？', function(){
                   	jp.loading();
                   	jp.get("${ctx}/meiguotong/orderliner/orderLiner/status?status=5&id="+row.id, function(data){
               	  		if(data.success){
               	  			$('#orderLinerTable').bootstrapTable('refresh');
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
                   	jp.get("${ctx}/meiguotong/orderliner/orderLiner/status?status=7&id="+row.id, function(data){
               	  		if(data.success){
               	  			$('#orderLinerTable').bootstrapTable('refresh');
               	  			jp.success(data.msg);
               	  		}else{
               	  			jp.error(data.msg);
               	  		}
               	  	})
               	   
               	});
	           }	    
    		    },
                formatter:  function operateFormatter(value, row, index) {
    		        return [
    		        	<shiro:hasPermission name="meiguotong:orderliner:orderLiner:view"> 
							'<a id="view" class="view" onclick="view('+row.id+')"><i class="fa fa-eye btn btn-success btn-xs">查看</i> </a>',
						</shiro:hasPermission>
							row.status==1?'<a href="#" class="delete" title="取消订单"><i class="fa fa-edit btn btn-danger btn-xs">取消订单</i> </a>':'',
							row.status==2?'<a href="#" class="yes" title="确认"><i class="fa fa-edit btn btn-danger btn-xs">确认</i> </a>':'',
							row.status==2?'<a href="#" class="delete" title="取消订单"><i class="fa fa-edit btn btn-warning btn-xs">取消订单</i> </a>':'',
							row.status==3?'<a href="#" class="delete" title="取消订单"><i class="fa fa-edit btn btn-warning btn-xs">取消订单</i> </a>':''
    		        ].join('');
    		    }
            }
		     ]
		
		});
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#orderLinerTable').bootstrapTable("toggleView");
		}
	  
	  $('#orderLinerTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#orderLinerTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#orderLinerTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/meiguotong/orderliner/orderLiner/import/template';
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
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#orderLinerTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#orderLinerTable').bootstrapTable('refresh');
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
        return $.map($("#orderLinerTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该邮轮订单表记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/meiguotong/orderliner/orderLiner/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#orderLinerTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function view(id){
	  window.location = "${ctx}/meiguotong/orderliner/orderLiner/form?id=" +id;
  }
  
</script>