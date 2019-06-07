<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#comPushTable').bootstrapTable({
		 
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
               url: "${ctx}/meiguotong/compush/comPush/data",
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
                   	window.location = "${ctx}/meiguotong/compush/comPush/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该推送管理记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/meiguotong/compush/comPush/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#comPushTable').bootstrapTable('refresh');
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
		        title: '推送标题',
		        align: 'center',
		        sortable: true
		        /*,formatter:function(value, row , index){
		        	return "<a href='${ctx}/meiguotong/compush/comPush/form?id="+row.id+"'>"+value+"</a>";
		         }*/
			}
			,{
		        field: 'content',
		        title: '内容',
		        align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return value.substring(9,value.length-10);
			        }
		         }
			,{
		        field: 'send',
		        title: '推送类型',
		        align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
			    	   if(value==1) return '<label style="font-weight:normal">全部用户</label>';
			          else if(value==2) return '<label style="font-weight:normal">部分用户</label>';
			        }
		         }
			,{
		        field: 'sendType',
		        title: '推送对象',
		        align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(value.length>1){
		        		var str=value.split(",");
		        		var tt='';
			        	   for(var i=0;i<str.length;i++){
			        		   if(str[i]==1) 
			        			  tt+='当地玩家,';
						          else if(str[i]==2) 
						        	  tt+='直客,';
						          else if(str[i]==3) 
						        	  tt+='旅行社,';
			        	   }
			        	   return tt.substring(0,tt.length-1);
		        	}else{
		        		if(value==1) return '<label style="font-weight:normal">当地玩家</label>';
				          else if(value==2) return '<label style="font-weight:normal">直客</label>';
				          else if(value==3) return '<label  style="font-weight:normal">旅行社</label>';
		        	}
		        	   
			        }
		         }
			,{
		        field: 'createDate',
		        title: '时间',
		        align: 'center',
		        sortable: true
		         },
		         {
		                field: 'operate',
		                title: '操作',
		                align: 'center',
		                
		                formatter:  function operateFormatter(value, row, index) {
		    		        return [
								'<a href="${ctx}/meiguotong/compush/comPush/form?id='+row.id+'" class="view" title="查看" >'+
								'<i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>'
		    		        ].join('');
		    		    }
		            }
		       
		    
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#comPushTable').bootstrapTable("toggleView");
		}
	  
	  $('#comPushTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#comPushTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#comPushTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/meiguotong/compush/comPush/import/template';
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
		  
		  
		  $('#comPushTable').bootstrapTable('refresh');
		  
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input[type!=hidden]").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#comPushTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#comPushTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该推送管理记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/meiguotong/compush/comPush/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#comPushTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/meiguotong/compush/comPush/form?id=" + getIdSelections();
  }
  
  function add(){
	  jp.openDialog('新增推送', "${ctx}/meiguotong/compush/comPush/form",'800px', '500px', $('#comPushTable'));
  }
  
</script>