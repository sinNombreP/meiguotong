<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"> 
		function homeOrder(orderDate){
			/* top.openTab('${ctx}/sys/kaolaOrder/homeOrder?orderDate='+orderDate,'订单列表',false) */
			/* parent.window.open("${ctx}/sys/kaolaOrder/homeOrder?orderDate="+orderDate); */
		}
		
	</script>
	<link rel="stylesheet" href="${ctxStatic}/calendar/dist/css/calendar-order-jquery.min.css">
  <style>
    body {
      margin: 0; padding: 0; font-family: "Microsoft YaHei UI";
    }
    .orderStatusCount{
        height:30px;
    }
    .orderBtn{
    	margin-top:16px;
    	padding:4px 20px;
     	margin-left:16%;
     	background:#169BD5;
     	color:white;
     	border:none; 
     	border-radius:8px;
    }
    .val0 .val1 .val2{
    cursor: default;
    width:auto;
    color:#fff;
    }
    p:HOVER{
    cursor: pointer;
    }
    .calendar-table-wrapper table td div p{
    width:auto;
    }
    th,b{
    padding-left: 5px;
    padding-top: 5px;
    }
  </style>
</head>
<body class="gray-bg">
	<input type="hidden" id="requsturl" value="${ctx}"/>
	<input type="hidden" id="carid" value="${id}"/>
	<div class="orderStatusCount">
		<%-- <button onclick="top.openTab('${ctx}/sys/kaolaOrder/orderStatus?statusd=2','订单列表',false)" class="orderBtn" class="btn btn-primary btn-rounded btn-outline btn-sm ">未发邮件</button>
		<button href="#"  onclick="top.openTab('${ctx}/sys/kaolaOrder/orderStatus?statusd=3','订单列表',false)" class="orderBtn" class="btn btn-primary btn-rounded btn-outline btn-sm ">待确认</button>
		<button href="#"  onclick="top.openTab('${ctx}/sys/kaolaOrder/orderStatus?statusd=8','订单列表',false)"  class="orderBtn" class="btn btn-primary btn-rounded btn-outline btn-sm ">申请退款</button> --%>
	</div>
	<div class="container" ></div>
	<br/>

<script src="${ctxStatic}/calendar/dist/js/jquery.min.js"></script>
<script src="${ctxStatic}/calendar/dist/js/calendar-order-jquery.js"></script>
<script>
  $(function () {
	  $.ajaxSetup({   
	         async : false  
	     }); 
 	var mydate = new Date();
	var year =  mydate.getFullYear() ;
	var  month = (mydate.getMonth()+1) ;
	if(month<10){
		month="0"+month;
	}                        
	var  orderDate =year+"-"+month;
  	var dateList=[];
	$.post("${ctx}/meiguotong/car/carService/JourneyOfCar?date="+orderDate+"&carid="+$("#carid").val(),{suggest:"txt"},function(result){
		console.log("result:"+result);
		var list = result.body.result;
        for(var i=0;i<list.length;i++){   
        	var order={};
        	order.date=list[i].date2;
           	order.typeName=list[i].typeName;
           	order.id1=list[i].id;
           	/* //行程2
           	order.typeName1=list[i].typeName;
           	order.id2=list[i].id;
           	//行程3
           	order.typeName2=list[i].typeName;
           	order.id3=list[i].id;*/
           	dateList.push(order); 
        }
        
	});
	
	console.log("dateList:"+dateList);
    $.CalendarPrice({
      el: '.container',
      startDate: '2017-01-01',
      //endDate: '2018-2-20',
      data: dateList,
      // 配置需要设置的字段名称
      config: [{}],
      attrConfig: [{}],
      // 配置在日历中要显示的字段
      show: [
    	  {
              key: 'typeName',
              key2: 'id1',
              name: '预订'
            },
            /* {
                key: 'typeName1',
                key2: 'id2',
                name: '预订'
              },
              {
                  key: 'typeName2',
                  key2: 'id3',
                  name: '预订'
                } */
      ],
      error: function (err) {
        console.error(err.msg);
        alert(err.msg);
      },
      // 自定义颜色
      style: {
        // 头部背景色
        headerBgColor: '#098cc2',
        // 头部文字颜色
        headerTextColor: '#fff',
        // 周一至周日背景色，及文字颜色
        weekBgColor: '#098cc2',
        weekTextColor: '#fff',
        // 周末背景色，及文字颜色
        weekendBgColor: '#098cc2',
        weekendTextColor: '#fff',
        // 有效日期颜色
        validDateTextColor: '#333',
        validDateBgColor: '#fff',
        validDateBorderColor: '#eee',
        // Hover
        validDateHoverBgColor: '#098cc2',
        validDateHoverTextColor: '#fff',
        // 无效日期颜色
        invalidDateTextColor: '#ccc',
        invalidDateBgColor: '#fff',
        invalidDateBorderColor: '#eee',
        // 底部背景颜色
        footerBgColor: '#fff',
      }
      ,hideFooterButton: true
      
    });

  });
  
</script>
</body>
</html>

