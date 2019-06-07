<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#orderCommentTable').bootstrapTable({
		 
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
               url: "${ctx}/sys/order/orderComment/data",
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
                   	window.location = "${ctx}/sys/order/orderComment/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该订单评价记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/order/orderComment/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#orderCommentTable').bootstrapTable('refresh');
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
		        field: 'id',
		        title: '评价ID',
		        sortable: true
		       
		    }
			,{
		        field: 'memberid',
		        title: '会员ID',
		        sortable: true
		       
		    }
			,{
		        field: 'orderNo',
		        title: '订单编号',
		        sortable: true
		       
		    }
			,{
		        field: 'comment',
		        title: '评论',
		        sortable: true
		       
		    }
			,{
		        field: 'img',
		        title: '评论图片，多个用逗号隔开',
		        sortable: true
		       
		    }
			,{
		        field: 'star',
		        title: '星级',
		        sortable: true
		       
		    }
			,{
		        field: 'status',
		        title: '状态',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(value ==1)return '正常';
		        	if(value ==2)return '屏蔽';
		        }
		       
		    }
			,{
		        field: 'createDate',
		        title: '创建时间',
		        sortable: true
		       
		    }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
                	'click .del': function (e, value, row, index) {
                        jp.confirm('是否删除？', function(){
                             	jp.loading();
                             	jp.get("${ctx}/sys/orderSys/confirmAjax?id="+row.id, function(data){
                         	  		if(data.success){
                         	  			$('#orderSysTable').bootstrapTable('refresh');
                         	  			jp.success(data.msg);
                         	  		}else{
                         	  			jp.error(data.msg);
                         	  		}
                         	  	})
                         	   
                         	});
      		        },
                	'click .dis': function (e, value, row, index) {
                        jp.confirm('是否禁用？', function(){
                             	jp.loading();
                             	jp.get("${ctx}/sys/orderSys/confirmAjax?id="+row.id, function(data){
                         	  		if(data.success){
                         	  			$('#orderSysTable').bootstrapTable('refresh');
                         	  			jp.success(data.msg);
                         	  		}else{
                         	  			jp.error(data.msg);
                         	  		}
                         	  	})
                         	   
                         	});
      		        },
    		    },
                formatter:  function operateFormatter(value, row, index) {
    		        return [

				'<a href="#" class="del" title="删除"><i class="fa fa-eye btn btn-primary btn-xs">删除</i> </a>',
				'<a href="#" class="dis" title="禁用"><i class="fa fa-eye btn btn-primary btn-xs">禁用</i> </a>'
    		        ].join('');
    		    }
            }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#orderCommentTable').bootstrapTable("toggleView");
		}
	  
	  $('#orderCommentTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#orderCommentTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#orderCommentTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/sys/order/orderComment/import/template';
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
		  $('#orderCommentTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#orderCommentTable').bootstrapTable('refresh');
		});
		
		$('#beginCreateDate').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endCreateDate').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#orderCommentTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该订单评价记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/order/orderComment/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#orderCommentTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/sys/order/orderComment/form?id=" + getIdSelections();
  }
  
</script>