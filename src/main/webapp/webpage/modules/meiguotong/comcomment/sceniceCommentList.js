<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#comCommentTable').bootstrapTable({
		 
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
               //是否显示父子表
               detailView : true, 
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/meiguotong/comcomment/comComment/SceniceCommentData",
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
                        jp.confirm('确认要删除该评论表记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/meiguotong/comcomment/comComment/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#comCommentTable').bootstrapTable('refresh');
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
    		        field: 'fatherId',
    		        title: '评论等级',
    		        sortable: true,
    		        formatter:function(value,row,index){
    		        	if(value==0) return '一级评论'; return '二级评论';
    		        }
    		    }
			,{
		        field: 'content',
		        title: '内容',
		        sortable: true
		       
		    }
			,{
		        field: 'account',
		        title: '评论人账号',
		        sortable: true
		       
		    }
 			,{
 				field: 'nickName',
 				title: '评论人昵称',
 				sortable: true
 				
 			}
			,{
		        field: 'createDate',
		        title: '评论时间',
		        sortable: true
		       
		    }
			,{
		        field: 'status',
		        title: '状态',
		        sortable: true,
		        formatter:function(value,row,index){
		        	if(value==0){
		        		return "禁止";
		        	}else if(value==1){
		        		return "启用";
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
                           	jp.get("${ctx}/meiguotong/comcomment/comComment/status?id="+row.id, function(data){
                       	  		if(data.success){
                       	  			$('#comCommentTable').bootstrapTable('refresh');
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
					(row.status==0?'<a href="#" class="status" title="禁用"><i class="fa fa-edit btn btn-warning btn-xs">显示</i> </a>':
					(row.status==1?'<a href="#"  class="status" title="显示"><i class="fa fa-edit btn btn-success btn-xs">禁用</i> </a>':'')),
    		        ].join('');
    		    }
            }
		     ],
				
               
               //注册加载子表的事件。注意下这里的三个参数！
               onExpandRow : function (index, row, $detail) {
               //  onloadInitTable(index, row, $detail);
                   console.log(index);
                   console.log(row);
                   onclick = row.id;
                   var parentId = row.id;                          
                   var prjLogBookProblemTable = $detail.html('<table></table>').find('table');
                   $(prjLogBookProblemTable).bootstrapTable({                                             
                       columns : [                          
                    	   {
               		        checkbox: false
               		       
               		    }
               			,{
               		        field: 'fatherId',
               		        title: '评论等级',
               		        sortable: true,
               		        formatter:function(value,row,index){
               		        	if(value==0) return '一级评论'; return '二级评论';
               		        }
               		    }
               			,{
               		        field: 'content',
               		        title: '内容',
               		        sortable: true
               		       
               		    }
               			,{
               		        field: 'account',
               		        title: '评论人账号',
               		        sortable: true
               		       
               		    }
               			,{
               				field: 'nickName',
               				title: '评论人昵称',
               				sortable: true
               				
               			}
               			,{
               		        field: 'createDate',
               		        title: '评论时间',
               		        sortable: true
               		       
               		    }
               			,{
               		        field: 'status',
               		        title: '状态',
               		        sortable: true,
               		        formatter:function(value,row,index){
               			    	 if(value==1) return '<button type="button" class="btn btn-primary">正常</button>';
               			         if(value==0) return '<button type="button" class="btn btn-danger">禁用</button>';
               		        }
               		       
               		    }
               		    , {
                               field: 'operate',
                               title: '操作',
                               align: 'center',
                               events: {
                   		         'click .status': function (e, value, row, index) { 
                   		        	updateStatus(row);
                   		        }
                   		
                   		    },
                               formatter:  function operateFormatter(value, row, index) {
                   		        return [
               					row.status==0?'<a href="#" class="status" title="禁用"><i class="fa fa-edit btn btn-warning btn-xs">显示</i> </a>':'',
               					row.status==1?'<a href="#"  class="status" title="显示"><i class="fa fa-edit btn btn-danger btn-xs">禁用</i> </a>':''
                   		        ].join('');
                   		    }
                           }
                       ],
                     //  url: "${ctx}/meiguotong/comcomment/comComment/getCommentDate",
                       data:row.sceniceCommentList,
                       method : 'POST', //请求方式(*)
                       sortable : true, //是否启用排序
                       sortName : 'id',
                       showHeader:false,
                       sortOrder : 'asc', //排序方式                               
                       clickToSelect : true, //是否启用点击选中行
                       uniqueId : "id", //每一行的唯一标识，一般为主键列
                       ajaxOptions: { parentId: parentId },
                       queryParams: { parentId: parentId },
                       contentType : "application/x-www-form-urlencoded; charset=UTF-8",                                                
                       onLoadError: function(){  //加载失败时执行  
                            layer.msg("加载数据出错!", {time : 1500, icon : 5});
                       }                              
                   });
               },
  		});
  		
  	function updateStatus(row){
  		jp.confirm('确认更改状态吗？', function(){
          	jp.loading();
          	jp.get("${ctx}/meiguotong/comcomment/comComment/updateStatus?id="+row.id, function(data){
      	  		if(data.success){
      	  			$('#comCommentTable').bootstrapTable('refresh');
      	  			jp.success(data.msg);
      	  		}else{
      	  			jp.error(data.msg);
      	  		}
      	  	})
      	});
  	}

  		 
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#comCommentTable').bootstrapTable("toggleView");
		}
	  
	  $('#comCommentTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#comCommentTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#comCommentTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/meiguotong/comcomment/comComment/import/template';
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
		  $('#comCommentTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#comCommentTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#comCommentTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该评论表记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/meiguotong/comcomment/comComment/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#comCommentTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增评论表', "${ctx}/meiguotong/comcomment/comComment/form",'800px', '500px', $('#comCommentTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="meiguotong:comcomment:comComment:edit">
	  jp.openDialog('编辑评论表', "${ctx}/meiguotong/comcomment/comComment/form?id=" + id,'800px', '500px', $('#comCommentTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="meiguotong:comcomment:comComment:edit">
	  jp.openDialogView('查看评论表', "${ctx}/meiguotong/comcomment/comComment/form?id=" + id,'800px', '500px', $('#comCommentTable'));
	  </shiro:lacksPermission>
  }
  function view(id){
	  window.location = "${ctx}/meiguotong/comcomment/comComment/cityChildComment?typeId=" + id;
  }
</script>