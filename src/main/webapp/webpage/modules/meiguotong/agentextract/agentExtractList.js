<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	if ($("#userType").val()==1) {
		$('#agentExtractTable').bootstrapTable({
			 
			  //请求方法
	               method: 'get',
	               //类型json
	               dataType: "json",
	               //显示刷新按钮
	              /* showRefresh: true,
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
	               url: "${ctx}/meiguotong/agentextract/agentExtract/data",
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
	                        jp.confirm('确认要删除该代理商提现记录吗？', function(){
	                       	jp.loading();
	                       	jp.get("${ctx}/meiguotong/agentextract/agentExtract/delete?id="+row.id, function(data){
	                   	  		if(data.success){
	                   	  			$('#agentExtractTable').bootstrapTable('refresh');
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
			        field: 'no',
			        title: '提现流水号',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'agentName',
			        title: '提现公司',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'count',
			        title: '订单金额',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'extract',
			        title: '提现金额',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'rank',
			        title: '开户银行',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'pay',
			        title: '开户账号',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'people',
			        title: '开户姓名',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'createDate',
			        title: '申请时间',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: '',
			        title: '账单',
			        align: 'center',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	//return "<a href='#' onclick=\"viewExtract('"+row.no+"','"+row.createDate+"','"+row.extract+"','"+row.orderids+"')\" data-toggle='modal' data-target='#myModal'>查看对账单</a>"
			        	return '<a href="#" onclick="checks('+row.id+')" >查看对账单</a>'
			         },
			        
			    },{
			        field: 'status',
			        title: '状态',
			        align: 'center',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	if (value==1) {
			        		return "已申请";
						}
			        	if (value==2) {
							return "同意申请";
						}
			        	if (value==3) {
							return "拒绝申请";
						}
			        	if (value==4) {
			        		return "已完成";
						}
			         }
			        
			    },{
	                field: 'operate',
	                title: '操作',
	                align: 'center',
	                events: {
	    		        'click .agree': function (e, value, row, index) {
	    		        	jp.confirm('确认同意申请吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/agentextract/agentExtract/changeStatus?status=2&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#agentExtractTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        'click .reject': function (e, value, row, index) {
	    		        	jp.confirm('确认拒绝申请吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/agentextract/agentExtract/changeStatus?status=3&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#agentExtractTable').bootstrapTable('refresh');
	                       	  			jp.success(data.msg);
	                       	  		}else{
	                       	  			jp.error(data.msg);
	                       	  		}
	                       	  	})
	                       	   
	                       	});
	    		        },
	    		        'click .affirm': function (e, value, row, index) {
	    		        	jp.confirm('确认完成提现吗？', function(){
	                           	jp.loading();
	                           	jp.get("${ctx}/meiguotong/agentextract/agentExtract/changeStatus?status=4&id="+row.id, function(data){
	                       	  		if(data.success){
	                       	  			$('#agentExtractTable').bootstrapTable('refresh');
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
	                		s+='<a href="#" class="agree yes" title="确认"><i class="fa fa-edit btn btn-success btn-xs">同意申请</i> </a>'
							s+='<a href="#" class="reject" style="margin:2px;"><i class="fa fa-edit btn btn-danger btn-xs">拒绝申请</i> </a>'
						}
	                	if (row.status==2) {
	                		s+='<a href="#" class="affirm yes" title="确认"><i class="fa fa-edit btn btn-danger btn-xs">完成提现</i> </a>'
						}
	                	return s;
	    		    }
	            }
			     ]
			
			});
	}
	if ($("#userType").val()==2) {
		$('#agentExtractTable').bootstrapTable({
			 
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
	               url: "${ctx}/meiguotong/agentextract/agentExtract/data",
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
	                        jp.confirm('确认要删除该代理商提现记录吗？', function(){
	                       	jp.loading();
	                       	jp.get("${ctx}/meiguotong/agentextract/agentExtract/delete?id="+row.id, function(data){
	                   	  		if(data.success){
	                   	  			$('#agentExtractTable').bootstrapTable('refresh');
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
			        field: 'no',
			        title: '提现流水号',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'agentName',
			        title: '提现公司',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'extract',
			        title: '提现金额',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'rank',
			        title: '开户银行',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'pay',
			        title: '开户账号',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'people',
			        title: '开户姓名',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: 'createDate',
			        title: '申请时间',
			        align: 'center',
			        sortable: true
			        
			    },{
			        field: '',
			        title: '账单',
			        align: 'center',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	//return "<a href='#' onclick=\"viewExtract('"+row.no+"','"+row.createDate+"','"+row.extract+"','"+row.orderids+"')\" data-toggle='modal' data-target='#myModal'>查看对账单</a>"
			        	return '<a href="#" onclick="checks('+row.id+')" >查看对账单</a>'
			         }
			        
			    },{
			        field: 'status',
			        title: '状态',
			        align: 'center',
			        sortable: true
			        ,formatter:function(value, row , index){
			        	if (value==1) {
			        		return "已申请";
						}
			        	if (value==2) {
							return "同意申请";
						}
			        	if (value==3) {
							return "拒绝申请";
						}
			        	if (value==4) {
			        		return "已完成";
						}
			         }
			        
			    },{
	                field: 'operate',
	                title: '操作',
	                align: 'center',
	                formatter:  function operateFormatter(value, row, index) {
	                	var s='<a onclick="view('+row.id+')" id="view" class="view"><i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>';
	                	return s;
	    		    }
	            }
			     ]
			
			});
	}
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#agentExtractTable').bootstrapTable("toggleView");
		}
	  
	  $('#agentExtractTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#agentExtractTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#agentExtractTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/meiguotong/agentextract/agentExtract/import/template';
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
		  $('#agentExtractTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#agentExtractTable').bootstrapTable('refresh');
		});
		$("#sureOne").click("click",function(){
			$("#myModal").modal('hide');
		})
		
	});

function view(id){
	  window.location = "${ctx}/meiguotong/agentextract/agentExtract/form?id=" +id;
}

function checks(id) {
	 window.location = "${ctx}/meiguotong/agentextract/agentExtract/findOrderSysById_Extract?id=" +id;
}

/*function viewExtract(no,createDate,extract,orderids) {
	e  =  event.srcElement;
		var obj2 = document.getElementsByClassName("selectColor");
		var str2 = "";
		if (obj2.length > 0) {
			for (i = 0; i < obj2.length; i++) {
				if (i == obj2.length - 1) {
					var id = obj2[i].id;
					var colorid = $(
							"#" + id).find(
							".colorid")
							.val()
					str2 += colorid;
				} else {
					var id = obj2[i].id;
					var colorid = $(
							"#" + id).find(
							".colorid")
							.val();
					str2 += colorid + ",";
				}
			}
		}
		$(".no").text(no);
		$(".createDate").text(createDate);
		$(".extract").text(extract);
		var url="${ctx}/meiguotong/agentextract/agentExtract/findOrderSysById_Extract";
		var params={"orderids":orderids}; 
		jp.post(url,params,function(data){
			var list = data.body.list;
			var t=''; 
			if(list){
				for(var i in list){
					t += '<tr><td ><input type="hidden"';
					t+=' value="';
	                t+=list[i].id;
					t+='"></td><td>';
					t += list[i].orderNo;
					t += '</td><td>';
					t += list[i].title;
					t += '</td><td>';
					if (list[i].type==1) {
						t +="包车租车";
					}else if (list[i].type==2) {
						t +="短程接送";
					}else if (list[i].type==3) {
						t +="接送机";
					}else if (list[i].type==4) {
						t +="常规路线";
					}else if (list[i].type==5) {
						t +="当地参团";
					}else if (list[i].type==6) {
						t +="游轮";
					}else if (list[i].type==7) {
						t +="景点门票";
					}else if (list[i].type==8) {
						t +="当地玩家/导游 ";
					}else if (list[i].type==9) {
						t +="酒店";
					}else if (list[i].type==10) {
						t +="保险";
					}else if (list[i].type==15) {
						t +="定制租车";
					}
					t += '</td><td>';
					t += list[i].finishDate;
					t += '</td><td>';
					t += list[i].price;
					t += '</td><td>';
					if (list[i].extract==1) {
						t +="未提现";
					}else if (list[i].extract==2) {
						t +="提现中";
					}else if (list[i].extract==3) {
						t +="已提现";
					}
					t += '</td><td>';
					t += '<a id="view" class="view" href="${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id='+list[i].id+'"><i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>';
					t += '</td></tr>';
				}
			}
			$("#tbody").empty();
			$("#tbody").append(t);
		});
}*/
        
  function getIdSelections() {
        return $.map($("#agentExtractTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该代理商提现记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/meiguotong/agentextract/agentExtract/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#agentExtractTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  //jp.openTab("${ctx}/meiguotong/agentextract/agentExtract/UnExtract","新增代理商提现",true);
	  window.location ="${ctx}/meiguotong/agentextract/agentExtract/UnExtract";
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="meiguotong:agentextract:agentExtract:edit">
	  jp.openDialog('编辑代理商提现', "${ctx}/meiguotong/agentextract/agentExtract/form?id=" + id,'800px', '500px', $('#agentExtractTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="meiguotong:agentextract:agentExtract:edit">
	  jp.openDialogView('查看代理商提现', "${ctx}/meiguotong/agentextract/agentExtract/form?id=" + id,'800px', '500px', $('#agentExtractTable'));
	  </shiro:lacksPermission>
  }

  function viewDetail(id) {
	  window.location ="${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id="+id;
	  //jp.openTab("${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id="+id,'订单详情',true);
}
</script>