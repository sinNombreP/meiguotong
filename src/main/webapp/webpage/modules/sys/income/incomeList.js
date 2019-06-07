<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#incomeTable').bootstrapTable({
		 
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
               url: "${ctx}/sys/income/income/data",
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
                   	window.location = "${ctx}/sys/income/income/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该平台流水管理记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/income/income/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#incomeTable').bootstrapTable('refresh');
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
		        title: '流水号',
		        sortable: true
		        
		       
		    },{
		        field: 'type',
		        title: '产品类型',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if (value==1) {
						return "包车租车";
					}else if (value==21) {
						return "短程接送";
					}else if (value==3) {
						return "接送机";
					}else if (value==4) {
						return "常规路线";
					}else if (value==5) {
						return "当地参团";
					}else if (value==6) {
						return "邮轮";
					}else if (value==7) {
						return "景点门票";
					}else if (value==8) {
						return "当地玩家/导游";
					}else if (value==9) {
						return "酒店";
					}else if (value==10) {
						return "保险";
					}else if (value==11) {
						return "旅游定制";
					}else if (value==13) {
						return "商务定制";
					}else if (value==14) {
						return "商务旅游";
					}else if (value==15) {
						return "定制租车";
					}
		         }
		    },{
		        field: 'payType',
		        title: '支付方式',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if (value==1) {
						return "支付宝";
					}else if (value==2) {
						return "微信支付";
					}else if (value==3) {
						return "银联支付";
					}else if (value==4) {
						return "Paypal";
					}
		         }
		       
		    }
			,{
		        field: 'income',
		        title: '流水类型',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if (value==1) {
						return "收入";
					}
		        	if (value==2) {
						return "支出";
					}
		         }
		       
		    }
			
			,{
		        field: '',
		        title: '承接方',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "平台";
		         }
		    }
			,{
		        field: 'nickName',
		        title: '联系人',
		        sortable: true
		       
		    }
			,{
		        field: 'phone',
		        title: '联系电话',
		        sortable: true
		       
		    }
			,{
		        field: 'createDate',
		        title: '支付时间',
		        sortable: true
		       
		    }
			,{
		        field: 'incomePrice',
		        title: '金额',
		        sortable: true
		       
		    },{
		        field: 'status',
		        title: '状态',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if (value==1) {
						return "待确认";
					}else if (value==2) {
						return "已完成";
					}else if (value==3) {
						return "已取消";
					}
		         }
		       
		    },{
		    	field: 'operate',
	              title: '操作',
	              align: 'center',
	              formatter:  function operateFormatter(value, row, index) {
	              	var s='<a href="${ctx}/sys/income/income/form?id='+row.id+'id="view" class="view"><i class="fa fa-eye btn btn-primary btn-xs">查看</i> </a>';
	              	return s;
	  		    }
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#incomeTable').bootstrapTable("toggleView");
		}
	  
	  $('#incomeTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#incomeTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#incomeTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/sys/income/income/import/template';
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
		  $('#incomeTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#incomeTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#incomeTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该平台流水管理记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/income/income/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#incomeTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/sys/income/income/form?id=" + getIdSelections();
  }
 
</script>