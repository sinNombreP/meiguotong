<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#memberIncomeTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               /*showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,*/
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       /* showExport: true,
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
               url: "${ctx}/sys/memberincome/memberIncome/data",
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
                   	window.location = "${ctx}/sys/memberincome/memberIncome/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该直客流水记录记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/memberincome/memberIncome/delete?id="+row.id, function(data){
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
   		    }
			
			,{
		        field: 'income',
		        title: '流水类型',
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
		        field: 'people',
		        title: '开户人',
		        sortable: true
		    }
			,{
   		        field: 'pay',
   		        title: '开户账号',
   		        align: 'center',
   		        sortable: true
   		    }
			,{
		        field: 'rank',
		        title: '开户银行',
		        sortable: true
		    }
			
			,{
		        field: 'payType',
		        title: '支付类型',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "银行卡"
		         }
		    }
			,{
		        field: 'incomePrice',
		        title: '提现金额',
		        sortable: true
		       
		    }
			,{
   		        field: 'createDate',
   		        title: '申请时间',
   		        align: 'center',
   		        sortable: true
   		    }
			,{
		        field: 'checks',
		        title: '状态',
		        sortable: true,
		        formatter:function(value,row,index){
			    	if(value == 1) return "待审核";
			    	if(value == 2) return "审核通过";
			    	if(value == 3) return "审核不通过";
			    	if(value == 4) return "已打款";
		        }
		    }
			,{
		        field: 'operate',
		        title: '操作',
		        align: 'center',
		        sortable: true,
		        events: {
	    		      'click .sure': function (e, value, row, index) { 
	    		    	  sure(row);
	    		        },
	    		        'click .confirm': function (e, value, row, index) { 
	    		        	confirm(row);
	    		        },
	    		        'click .no': function (e, value, row, index) { 
	    		        	cancel(row);
	    		        },
	    		    },
		        formatter:function(value, row , index){
	        		return [
	        			'<a href="${ctx}/sys/memberincome/memberIncome/form?id='+row.id+'" class="view" title="查看" >'+
						'<i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>',
						row.checks==1?'<a href="#" class="sure"><i class="fa fa-edit btn btn-danger btn-xs">审核通过</i> </a>':'',
						row.checks==1?'<a href="#" class="no" class="some"><i class="fa fa-edit btn btn-danger btn-xs">审核不通过</i> </a>':'',
						row.checks==2?'<a href="#" class="confirm"><i class="fa fa-edit btn btn-danger btn-xs">确认已打款</i> </a>':'',
						].join('');
		         }
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#memberIncomeTable').bootstrapTable("toggleView");
		}
	  
	  $('#memberIncomeTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#memberIncomeTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#memberIncomeTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/sys/memberincome/memberIncome/import/template';
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
		  $('#memberIncomeTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#memberIncomeTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#memberIncomeTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该直客流水记录记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/memberincome/memberIncome/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#memberIncomeTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function confirm(row){
		jp.confirm('确认已打款吗？', function(){
			jp.loading();
			jp.get("${ctx}/sys/memberincome/memberIncome/UpdateChecks?id="+row.id+"&checks=4", function(data){
				if(data.success){
					$('#memberIncomeTable').bootstrapTable('refresh');
					jp.success(data.msg);
				}else{
					jp.error(data.msg);
				}
			})
		});
	};
	
	function sure(row){
		jp.confirm('确定通过吗？', function(){
			jp.loading();
			jp.get("${ctx}/sys/memberincome/memberIncome/UpdateChecks?id="+row.id+"&checks=2", function(data){
				if(data.success){
					$('#memberIncomeTable').bootstrapTable('refresh');
					jp.success(data.msg);
				}else{
					jp.error(data.msg);
				}
			})
		});
	}
	
	function cancel(row){
			jp.loading();
			jp.prompt("审核不通过原因",function (text) {						
				   jp.get("${ctx}/sys/memberincome/memberIncome/UpdateChecks?id="+row.id+"&checks=3&remark="+encodeURIComponent(text),function (result) {
					   if(result.success){
					   	 $('#memberIncomeTable').bootstrapTable('refresh');
					   	 jp.success(result.msg);
					   }else{
					   	 jp.error(result.msg);
					   }
	              })
		});
	}
  
	
	
  function edit(){
	  window.location = "${ctx}/sys/memberincome/memberIncome/form?id=" + getIdSelections();
  }
  
</script>