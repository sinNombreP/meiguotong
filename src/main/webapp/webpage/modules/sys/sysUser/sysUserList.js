<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#sysUserTable').bootstrapTable({
		 
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
               url: "${ctx}/sys/sysUser/data",
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
                        jp.confirm('确认要删除该后台账号管理记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/sysUser/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#sysUserTable').bootstrapTable('refresh');
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
		        field: 'loginName',
		        title: '账号',
		        sortable: true
		       
		    }
			,{
		        field: 'name',
		        title: '姓名',
		        sortable: true
		       
		    }
			,{
		        field: 'roles',
		        title: '角色',
		        sortable: true
		       
		    }
			,{
		        field: 'createBy.loginName',
		        title: '创建人',
		        sortable: true
		       
		    }
			,{
		        field: 'createDate',
		        title: '创建时间',
		        sortable: true
		       
		    }
			,{
		        field: 'loginDate',
		        title: '最后登陆时间',
		        sortable: true
		       
		    }
			,{
		        field: 'loginIp',
		        title: '最后登陆IP',
		        sortable: true
		       
		    }
			,{
		        field: 'loginFlag',
		        title: '状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return value=="0" ? '<font color="red">禁用</font>':'<font color="green">正常</font>';
		        }
		       
		    }
			, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
    		        'click .edit': function (e, value, row, index) {
    		        	jp.openDialog('编辑角色', '${ctx}/sys/sysUser/form?id=' + row.id,'800px', '500px', $('#sysUserTable'));
    		        },
    		        'click .del': function (e, value, row, index) {
    		        	jp.confirm('确认要删除该后台账号管理记录吗？', function(){
                           	jp.loading();
                           	jp.get("${ctx}/sys/sysUser/delete?id="+row.id, function(data){
                       	  		if(data.success){
                       	  			$('#sysUserTable').bootstrapTable('refresh');
                       	  			jp.success(data.msg);
                       	  		}else{
                       	  			jp.error(data.msg);
                       	  		}
                       	  	})
                       	   
                       	});
    		        },
    		        'click .changeState': function (e, value, row, index) {
    		        	jp.confirm('确认要修改该后台账号状态吗？', function(){
                           	jp.loading();
                           	jp.get("${ctx}/sys/sysUser/changeState?id="+row.id, function(data){
                       	  		if(data.success){
                       	  			$('#sysUserTable').bootstrapTable('refresh');
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
							'<a href="#" class="edit" title="修改"><i class="fa fa-edit btn btn-primary btn-xs">&nbsp;修改</i> </a>',
						    '<a href="#" class="del" title="删除"><i class="fa fa-trash btn btn-danger btn-xs">&nbsp;删除</i> </a>',
							row.loginFlag=="1" ? '<a href="#" class="changeState" title="禁用"><i class="fa fa-edit btn btn-warning btn-xs">禁用</i> </a>':
						    '<a href="#" class="changeState" title="启用"><i class="fa fa-edit btn btn-success btn-xs">启用</i> </a>',
    		        ].join('');
    		    }
            }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#sysUserTable').bootstrapTable("toggleView");
		}
	  
	  $('#sysUserTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#sysUserTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#sysUserTable').bootstrapTable('getSelections').length!=1);
            $('#enable').prop('disabled', ! $('#sysUserTable').bootstrapTable('getSelections').length);
            $('#disable').prop('disabled', ! $('#sysUserTable').bootstrapTable('getSelections').length);
        });
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#sysUserTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#sysUserTable').bootstrapTable('refresh');
		});
	 $(document).keydown(function(event){
			if(event.keyCode==13){
				$('#sysUserTable').bootstrapTable('refresh');
			}
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#sysUserTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){
		jp.confirm('确认要删除该后台账号管理记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/sysUser/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#sysUserTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function enableAll(){
		jp.confirm('确认要启用该后台账号吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/sysUser/enable?ids=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#sysUserTable').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }
  function disableAll(){

		jp.confirm('确认要禁用该后台账号吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/sysUser/disable?ids=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#sysUserTable').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }
   function add(){
	  jp.openDialog('新增后台账号管理', "${ctx}/sys/sysUser/form",'800px', '500px', $('#sysUserTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="sys:sysUser:edit">
	  jp.openDialog('编辑后台账号管理', "${ctx}/sys/sysUser/form?id=" + id,'800px', '500px', $('#sysUserTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="sys:sysUser:edit">
	  jp.openDialogView('查看后台账号管理', "${ctx}/sys/sysUser/form?id=" + id,'800px', '500px', $('#sysUserTable'));
	  </shiro:lacksPermission>
  }

</script>