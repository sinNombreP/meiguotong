<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#comFeedbackTable').bootstrapTable({
		 
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
               url: "${ctx}/sys/comFeedback/data",
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
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该意见反馈记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/comFeedback/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#comFeedbackTable').bootstrapTable('refresh');
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
		        field: 'membername',
		        title: '用户昵称',
		        sortable: true
		       
		    }
			,{
		        field: 'mobile',
		        title: '联系方式',
		        sortable: true
		       
		    }
			,{
		        field: 'email',
		        title: '邮箱',
		        sortable: true
		       
		    }
			,{
		        field: 'content',
		        title: '反馈内容',
		        sortable: true
		       
		    }
			,{
		        field: 'isDeal',
		        title: '是否处理：',
		        sortable: true,
		        formatter:function(value, row , index){
		        	if(value==2){
		        		return "未处理 ";
		        	}else if(value==1){
		        		return "已处理 ";
		        	}else{
		        		return "未知 ";
		        	}
		        }
			
		    }
			,{
		        field: 'remark',
		        title: '回复/备注',
		        sortable: true
		       
		    }
			,{
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
    		        'click .edit': function (e, value, row, index) {
    		        	jp.openDialog('处理', '${ctx}/sys/comFeedback/form?id=' + row.id,'280px', '200px',$('#comFeedbackTable'));
    		        },
    		        'click .del': function (e, value, row, index) {
    		        	jp.confirm('确认要删除该意见反馈记录吗？', function(){
    		    			jp.loading();  	
    		    			jp.get("${ctx}/sys/comFeedback/delete?id=" + row.id, function(data){
    		             	  		if(data.success){
    		             	  			$('#comFeedbackTable').bootstrapTable('refresh');
    		             	  			jp.success(data.msg);
    		             	  		}else{
    		             	  			jp.error(data.msg);
    		             	  		}
    		             	  	})
    		              	   
    		    		})
    		        }
    		    },formatter:  function operateFormatter(value, row, index) {
    		            	if(row.isDeal==2){
    		            		return '<a href="#" class="edit btn btn-success btn-xs"><i class="fa fa-edit"></i> 处理 </a> <a href="#" class="del btn  btn-danger btn-xs" title="删除"><i class="fa fa-trash"></i>删除 </a> ';
    			        	}else{
    			        		return '<a href="#" class="del btn  btn-danger btn-xs" title="删除"><i class="fa fa-trash"></i>删除 </a> ';
    			        	}
    		    }
            }
		     ]
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#comFeedbackTable').bootstrapTable("toggleView");
		}
	  
	  $('#comFeedbackTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#comFeedbackTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#comFeedbackTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/sys/comFeedback/import/template';
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
		  $('#comFeedbackTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#comFeedbackTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#comFeedbackTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该意见反馈记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/comFeedback/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#comFeedbackTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增意见反馈', "${ctx}/sys/comFeedback/form",'800px', '500px', $('#comFeedbackTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="sys:comFeedback:edit">
	  jp.openDialog('编辑意见反馈', "${ctx}/sys/comFeedback/form?id=" + id,'800px', '500px', $('#comFeedbackTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="sys:comFeedback:edit">
	  jp.openDialogView('查看意见反馈', "${ctx}/sys/comFeedback/form?id=" + id,'800px', '500px', $('#comFeedbackTable'));
	  </shiro:lacksPermission>
  }

</script>