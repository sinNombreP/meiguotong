<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	
	$('#agentExtractUnExtractTable').bootstrapTable({
		 
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
             /*//是否显示分页（*）  
             pagination: true,   */
              //排序方式 
             sortOrder: "asc",  
             //初始化加载第一页，默认第一页
             pageNumber:1,   
             //每页的记录行数（*）   
             pageSize: 10,  
             //可供选择的每页的行数（*）    
             pageList: [10, 25, 50, 100],
             //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
             url: "${ctx}/meiguotong/agentextract/agentExtract/findOrderSysByExtract",
             //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
             //queryParamsType:'',   
             ////查询参数,每次调用是会带上这个参数，可自定义                         
             queryParams : function(params) {
             	var searchParam = $("#searchForm1").serializeJSON();
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
		        field: 'orderNo',
		        title: '订单号',
		        align: 'center',
		        sortable: true
		        
		    },{
		        field: 'title',
		        title: '标题',
		        align: 'center',
		        sortable: true
		        
		    },{
		        field: 'type',
		        title: '类型',
		        align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if (value==1) {
		        		return "包车/租车";
					}else if (value==2) {
		        		return "短程接送";
					}else if (value==3) {
		        		return "接送机";
					}else if (value==4) {
		        		return "常规路线";
					}else if (value==5) {
		        		return "当地参团";
					}else if (value==6) {
		        		return "游轮";
					}else if (value==7) {
		        		return "景点门票";
					}else if (value==8) {
		        		return "当地玩家/导游";
					}else if (value==9) {
		        		return "酒店";
					}else if (value==10) {
		        		return "保险";
					}else if (value==15) {
		        		return "定制租车";
					}
		         },
		    },{
		        field: 'price',
		        title: '金额',
		        align: 'center',
		        sortable: true
		        
		    },{
		        field: 'finishDate',
		        title: '完成时间',
		        align: 'center',
		        sortable: true
		        
		    },{
		        field: 'extract',
		        title: '状态',
		        align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if (value==1) {
		        		return "未提现";
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
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#agentExtractUnExtractTable').bootstrapTable("toggleView");
		}
	  
	  $('#agentExtractUnExtractTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#agentExtractUnExtractTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#agentExtractUnExtractTable').bootstrapTable('getSelections').length!=1);
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
		  $('#agentExtractUnExtractTable').bootstrapTable('refresh');
		});
	  
	  $("#submitids").on("click",function(){
		  if (getIdSelections()!=null && getIdSelections()!="") {
			  $("#orderids").val(getIdSelections());
			  document.getElementById("searchForm2").submit();
			  //$("#searchForm2").submit();
		}else{
			jp.error("请选择订单！");
		}
	  });
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm1  input").val("");
		  $("#searchForm1  select").val("");
		  $("#searchForm1  .select-item").html("");
		  $('#agentExtractUnExtractTable').bootstrapTable('refresh');
		});
		$("#sureOne").click("click",function(){
			$("#myModal").modal('hide');
		})
		
	});

function view(id){
	//jp.openTab("${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id=" +id,"订单详情",true);
	//jp.openDialog('订单详情', "${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id=" + id,'1000px', '500px', $('#agentExtractUnExtractTable'));
	 window.location = "${ctx}/meiguotong/agentextract/agentExtract/viewOrder?id=" +id;
}


        
  function getIdSelections() {
        return $.map($("#agentExtractUnExtractTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该代理商提现记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/meiguotong/agentextract/agentExtract/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#agentExtractUnExtractTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  			error.appendTo(element.parent().parent());
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增代理商提现', "${ctx}/meiguotong/agentextract/agentExtract/UnExtract",'800px', '500px', $('#agentExtractTable'));
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
  

</script>

