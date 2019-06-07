<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#couponTable').bootstrapTable({
		 
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
               url: "${ctx}/sys/coupon/data",
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
                   	window.location = "${ctx}/sys/coupon/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该优惠券记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/coupon/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#couponTable').bootstrapTable('refresh');
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
		        field: 'title',
		        title: '优惠卷名称',
		        sortable: true
		       
		    }
			,{
		        field: 'descrption',
		        title: '描述',
		        sortable: true
		       
		    }
			,{
		        field: 'stock',
		        title: '使用门槛',
		        sortable: true
		         ,formatter:function(value, row , index){
		        	return value+"元";
		         }
		       
		    }
			,{
		        field: 'price',
		        title: '减免金额',
		        sortable: true
		         ,formatter:function(value, row , index){
		        	return value+"元";
		         }
		       
		    }
		    ,{
		        field: 'beginDate',
		        title: '有效期',
		        sortable: true
		         ,formatter:function(value, row , index){
		        	return value.substring(0,10)+"~"+row.endDate.substring(0,10);
		         }
		    }
		    ,{
		        field: 'category',
		        title: '使用类别',
		        sortable: true
		         ,formatter:function(value, row , index){
		        	if(row.useType==1){
		        		return "不限制分类";
		        	}else{
		        		return value;
		        	}
		         }
		       
		    }
		    ,{
		        field: 'createDate',
		        title: '创建时间',
		        sortable: true
		       
		    }
		    ,{
		        field: 'allNum',
		        title: '发送总数',
		        sortable: true
		         ,formatter:function(value, row , index){
		        	return value+"张";
		         }
		       
		    }
			,{
		        field: 'useNum',
		        title: '使用数量',
		        sortable: true
		         ,formatter:function(value, row , index){
		        	 if(value==null){
		        		 return 0+"张";
		        	 }else{
		        		 return value+"张";
		        	 }
		         }
		       
		    }
			,{
		        field: 'status',
		        title: '状态',
		        sortable: true
		         ,formatter:function(value, row , index){
		          if(value==1){
		           	return "启用";
		           }else{
		           	return "禁用";
		           }
		        	
		         }
		       
		    }
			 , {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
    		        'click .status': function (e, value, row, index) { 
    		        	jp.confirm('确认更改状态吗？', function(){
                           	jp.loading();
                           	jp.get("${ctx}/sys/coupon/status?id="+row.id, function(data){
                       	  		if(data.success){
                       	  			$('#couponTable').bootstrapTable('refresh');
                       	  			jp.success(data.msg);
                       	  		}else{
                       	  			jp.error(data.msg);
                       	  		}
                       	  	})
                       	   
                       	});
    		        },
    		         'click .delete': function (e, value, row, index) { 
    		        	jp.confirm('确认删除优惠券吗？', function(){
                           	jp.loading();
                           	jp.get("${ctx}/sys/coupon/delete?id="+row.id, function(data){
                       	  		if(data.success){
                       	  			$('#couponTable').bootstrapTable('refresh');
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
    		        <shiro:hasPermission name="sys:coupon:view">
						'<a href="${ctx}/sys/coupon/form?view=1&id='+row.id+'" class="view" title="查看"><i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>',
					</shiro:hasPermission>	
					 <shiro:hasPermission name="sys:coupon:edit">
						'<a href="${ctx}/sys/coupon/form?view=0&id='+row.id+'" class="edit" title="修改"><i class="fa fa-edit btn btn-warning btn-xs">修改</i> </a>',
					</shiro:hasPermission>	
					<shiro:hasPermission name="sys:coupon:del">	
						'<a href="#" class="delete" title="删除"><i class="fa fa-trash btn btn-danger btn-xs"> 删除</i> </a>',
					</shiro:hasPermission>		
						row.status==1?'<a href="#" class="status" title="禁用"><i class="fa fa-edit btn btn-success btn-xs">禁用</i> </a>':'',
						row.status==2?'<a href="#" class="status" title="启用"><i class="fa fa-edit btn btn-warning btn-xs">启用</i> </a>':''
    		        ].join('');
    		    }
            }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#couponTable').bootstrapTable("toggleView");
		}
	  
	  $('#couponTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#couponTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#couponTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/sys/coupon/import/template';
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
		  $('#couponTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#couponTable').bootstrapTable('refresh');
		});
		
		$('#beginCreateDate').datetimepicker({
			 format: "YYYY-MM-DD"
		});
		$('#endCreateDate').datetimepicker({
			 format: "YYYY-MM-DD"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#couponTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该优惠券记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/coupon/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#couponTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/sys/coupon/form?view=0&id=" + getIdSelections();
  }
  
</script>