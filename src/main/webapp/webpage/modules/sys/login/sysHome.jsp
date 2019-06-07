<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="ani"/>
	<style>

		#body-container {
			margin-left: 0px !important;
			/**padding: 10px;*/
			margin-top: 0px !important;
			overflow-x: hidden!important;
			transition: all 0.2s ease-in-out !important;
			height: 100% !important;
		}
		body{
		background-color:white;
		}
		
        .right{
            width:35px;
            text-align:center;
        	float:right; 
        	background-color: red; 
        	color:white; 
        	border-radius: 10px;
        }
       /*  .modal-header{
         background-color: #97cbff;
        } */
	</style>
</head>
<body class="">

		<div class="modal-header" >
					<h4 class="modal-title" id="myModalLabel">订单管理</h4>
				</div>
				<div>
				<div class="modal-body" style="width:50%;float:left;">
					<table class="table table-bordered">
						<thead>
							<tr style=""><td><strong>未处理订单</strong></td></tr>
						</thead>
						<tbody id="tbody">
							<tr><td><div style="float:left;">包车/租车</div>
							<c:if test="${orderSys.carNum>0}"><div class="right">${orderSys.carNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">短程接送</div>
							<c:if test="${orderSys.sortNum>0}"><div class="right">${orderSys.sortNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">接送机</div>
							<c:if test="${orderSys.airNum>0}"><div class="right">${orderSys.airNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">定制租车</div>
							<c:if test="${orderSys.cusNum>0}"><div class="right">${orderSys.cusNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">常规路线</div>
							<c:if test="${orderSys.normalNum>0}"><div class="right">${orderSys.normalNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">当地参团</div>
							<c:if test="${orderSys.localNum>0}"><div class="right">${orderSys.localNum}</div></c:if></td></tr>
							<c:if test="${userType==1}">
							<tr><td><div style="float:left;">当地玩家</div>
							<c:if test="${orderSys.guideNum>0}"><div class="right">${orderSys.guideNum}</div></c:if></td></tr>
							</c:if>
							<tr><td><div style="float:left;">保险</div>
							<c:if test="${orderSys.insuranceNum>0}"><div class="right">${orderSys.insuranceNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">酒店</div>
							<c:if test="${orderSys.hotelNum>0}"><div class="right">${orderSys.hotelNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">邮轮</div>
							<c:if test="${orderSys.linerNum>0}"><div class="right">${orderSys.linerNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">景点</div>
							<c:if test="${orderSys.scenicNum>0}"><div class="right">${orderSys.scenicNum}</div></c:if></td></tr>
						</tbody>
					</table>
				</div>
				<div class="modal-body" style="width:50%;float:left;">
					<table class="table table-bordered" style=" border-top-left-radius: 12px;">
						<thead>
							<tr ><td><strong>明日待出行订单</strong></td></tr>
						</thead>
						<tbody id="tbody">
							<tr><td><div style="float:left;">包车/租车</div>
							<c:if test="${ordertravel.carNum>0}"><div class="right">${ordertravel.carNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">短程接送</div>
							<c:if test="${ordertravel.sortNum>0}"><div class="right">${ordertravel.sortNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">接送机</div>
							<c:if test="${ordertravel.airNum>0}"><div class="right">${ordertravel.airNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">定制租车</div>
							<c:if test="${ordertravel.cusNum>0}"><div class="right">${ordertravel.cusNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">常规路线</div>
							<c:if test="${ordertravel.normalNum>0}"><div class="right">${ordertravel.normalNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">当地参团</div>
							<c:if test="${ordertravel.localNum>0}"><div class="right">${ordertravel.localNum}</div></c:if></td></tr>
							<c:if test="${userType==1}">
							<tr><td><div style="float:left;">当地玩家</div>
							<c:if test="${ordertravel.guideNum>0}"><div class="right">${ordertravel.guideNum}</div></c:if></td></tr>
							</c:if>
							<tr><td><div style="float:left;">保险</div>
							<c:if test="${ordertravel.insuranceNum>0}"><div class="right">${ordertravel.insuranceNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">酒店</div>
							<c:if test="${ordertravel.hotelNum>0}"><div class="right">${ordertravel.hotelNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">邮轮</div>
							<c:if test="${ordertravel.linerNum>0}"><div class="right">${ordertravel.linerNum}</div></c:if></td></tr>
							<tr><td><div style="float:left;">景点</div>
							<c:if test="${ordertravel.scenicNum>0}"><div class="right">${ordertravel.scenicNum}</div></c:if></td></tr>
						</tbody>
					</table>
				</div>
				<div style="clear:both;"></div>
				</div>
				<c:if test="${userType==1}">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel" >通知</h4>
					</div>
					<iframe src="${ctx}/meiguotong/compush/comPush/inform" id="contentCommentIfem" style="background-color:white"  scrolling="no" frameborder="0"  width="100%" ></iframe>
				</c:if>
<!-- <div id="body-container" class="wrapper wrapper-content">
	<div class="conter-wrapper home-container">
	<p style="font-size:48px;color:blue;margin-top:30px;margin-left:30px;">欢迎来到美国通管理系统!</p> -->
		<!-- <div class="row home-row">
			<div class="col-md-4 col-lg-3">
				<div class="home-stats">
					<a href="#" class="stat hvr-wobble-horizontal">
						<div class=" stat-icon">
							<i class="fa fa-cloud-upload fa-4x text-info "></i>
						</div>
						<div class=" stat-label">
							<div class="label-header">
								88%
							</div>
							<div class="progress-sm progress ng-isolate-scope" value="progressValue" type="info">
								<div class="progress-bar progress-bar-info" role="progressbar"
									 aria-valuenow="88" aria-valuemin="0" aria-valuemax="100"  style="width: 88%;">
								</div>
							</div>
							<div class="clearfix stat-detail">
								<div class="label-body">
									<i class="fa fa-arrow-circle-o-right pull-right text-muted"></i>服务正常运行时间
								</div>
							</div>
						</div>
					</a>					<a href="#" class="stat hvr-wobble-horizontal">
					<div class=" stat-icon">
						<i class="fa fa-heartbeat fa-4x text-success "></i>
					</div>
					<div class=" stat-label">
						<div class="label-header">
							94%
						</div>
						<div class="progress-sm progress ng-isolate-scope" value="progressValue" type="info">
							<div class="progress-bar progress-bar-success" role="progressbar"
								 aria-valuenow="94" aria-valuemin="0" aria-valuemax="100"  style="width: 94%;">
							</div>
						</div>
						<div class="clearfix stat-detail">
							<div class="label-body">
								<i class="fa fa-arrow-circle-o-right pull-right text-muted"></i>积极反馈
							</div>
						</div>
					</div>
				</a>					<a href="#" class="stat hvr-wobble-horizontal">
					<div class=" stat-icon">
						<i class="fa fa-flag fa-4x text-danger "></i>
					</div>
					<div class=" stat-label">
						<div class="label-header">
							88%
						</div>
						<div class="progress-sm progress ng-isolate-scope" value="progressValue" type="info">
							<div class="progress-bar progress-bar-danger" role="progressbar"
								 aria-valuenow="88" aria-valuemin="0" aria-valuemax="100"  style="width: 88%;">
							</div>
						</div>
						<div class="clearfix stat-detail">
							<div class="label-body">
								<i class="fa fa-arrow-circle-o-right pull-right text-muted"></i>机器负载
							</div>
						</div>
					</div>
				</a>
				</div>
			</div>
			<div class="col-md-4 col-lg-6">
				<div class="home-charts-middle">
					<div class="chart-container">
						<div class="chart-comment clearfix">
							<div class="text-primary pull-left">
								<span class="comment-header">55%</span><br />
								<span class="comment-comment">搜素引擎</span>
							</div>
							<div class="text-success pull-left m-l">
								<span class="comment-header">25%</span><br />
								<span class="comment-comment">自主访问</span>
							</div>
							<div class="text-warning pull-left m-l">
								<span class="comment-header">20%</span><br />
								<span class="comment-comment">友情链接</span>
							</div>
						</div>
						<div id="lineChart" style="height:250px"></div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-lg-3">
				<div class="home-charts-right">
					<div class="top-right-chart">
						<div id="cbar" style="height:150px; padding-top:7px;"></div>
					</div>
					<div class="bottom-right-chart">
						<div class="top-left-chart clearfix">
							<div class="row">
								<div class="col-sm-6 text-left">
									<div class="padder">
										<span class="heading">本周访问人数 : </span><br />
										<big class="text-primary">22068</big>
									</div>
								</div>
								<div class="col-sm-6">
									<div id="pie"  style="height:125px; padding-top:8px;"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row home-row">
			<div class="col-lg-8 col-md-6">
				<div class="map-container box padder">
					<div id="world-map" style="width: 100%; height: 320px"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-6">
				<div class="todo-container panel panel-danger">
					<div class="panel-heading">
						<div class="todo-header text-center">
							<h4><i class="fa fa-tasks"></i>&nbsp;待办任务</h4>
						</div>
					</div>
					<div class="panel-body bg-danger">
						<div class="todo-body">
							<div class="todo-list-wrap">
								<ul class="todo-list">
									<li class="">
										<label class="checkbox1" for="option1">
											<input id="option1" type="checkbox" class="">
											<span></span>
										</label>
										<span class="done-false">9:00开晨会安排工作</span>
									</li>
									<li class="">
										<label class="checkbox1" for="option3">
											<input id="option3" type="checkbox" class="">
											<span></span>
										</label>
										<span class="done-false">9:00~12:00客户需求分析</span>
									</li>
									<li class="">
										<label class="checkbox1" for="option4">
											<input id="option4" type="checkbox" class="">
											<span></span>
										</label>
										<span class="done-false">12:00和客户电话会议</span>
									</li>
									<li class="">
										<label class="checkbox1" for="option5">
											<input id="option5" type="checkbox" class="">
											<span></span>
										</label>
										<span class="done-false">2:00参加技术论坛</span>
									</li>
									<li class="">
										<label class="checkbox1" for="option2">
											<input id="option2" type="checkbox" class="">
											<span></span>
										</label>
										<span class="done-false">5:00晚会总结进度</span>
									</li>
								</ul>
							</div>
							<form class="form-horizontal todo-from-bottom">
								<div class="row">
									<div class="col-sm-12">
										<div class="input-group">
											<input type="text" class="form-control" placeholder="">
											<span class="input-group-btn">
										<button class="btn btn-default" type="submit">增加</button>
									</span>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> -->
<!-- </div> -->
<script src="vendor/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="js/vendor.js"></script>

<script type="text/javascript">
function reinitIframe(){
	var iframe = document.getElementById("contentCommentIfem");
	try{
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height = height;
	console.log(height);
	}catch (ex){}
	}
	window.setInterval("reinitIframe()", 200);

</script>
<!-- <script>
    $(function(){
        $('#calendar2').fullCalendar({
            eventClick: function(calEvent, jsEvent, view) {
                alert('Event: ' + calEvent.title);
                alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
                alert('View: ' + view.name);
            }
        });

        $('#rtlswitch').click(function() {
            console.log('hello');
            $('body').toggleClass('rtl');

            var hasClass = $('body').hasClass('rtl');

            $.get('/api/set-rtl?rtl='+ (hasClass ? 'rtl': ''));

        });
        $('.theme-picker').click(function() {
            changeTheme($(this).attr('data-theme'));
        });
        $('#showMenu').click(function() {
            $('body').toggleClass('push-right');
        });

    });
    function changeTheme(the) {
        $("#current-theme").remove();
        $('<link>')
            .appendTo('head')
            .attr('id','current-theme')
            .attr({type : 'text/css', rel : 'stylesheet'})
            .attr('href', '/css/app-'+the+'.css');
    }
</script>

<script>
    $(function(){
        setTimeout(function() {
            var chart = c3.generate({
                bindto: '#lineChart',
                data: {
                    columns: [
                        ['搜索引擎', 30, 200, 100, 400, 150, 250],
                        ['自主访问', 50, 120, 210, 140, 115, 425],
                        ['友情链接', 40, 150, 98, 300, 175, 100]
                    ]
                },
                color: {
                    pattern: ['#3CA2E0','#5CB85C','#F1B35B']
                },
                axis: {
                    x: {
                        show: false
                    },
                    y: {
                        show: false
                    },
                }
            });
        }, 275);
        setTimeout(function() {
            $('#world-map').vectorMap({
                backgroundColor: '#FFF',
                regionStyle: {
                    initial: {
                        fill: 'black',
                        "fill-opacity": 1,
                        stroke: 'none',
                        "stroke-width": 0,
                        "stroke-opacity": 1
                    },
                    hover: {
                        "fill-opacity": 0.8,
                        cursor: 'pointer',
                    },
                    selected: {
                        fill: 'red'
                    },
                    selectedHover: {
                    }
                }
            });
        }, 275);
        setTimeout(function() {
            var chart2 = c3.generate({
                bindto: '#cbar',
                data: {
                    columns: [
                        [10,40,20,90,35,70,10,50,20,80,60,10,20,40,70]
                    ],
                    type:'bar'
                },
                bar: {
                    width: {
                        ratio: 0.5 // this makes bar width 50% of length between ticks
                    }
                },
                color: {
                    pattern: ['#DB5B57']
                },
                labels: true,
                legend: {
                    show: 0
                },
                axis: {
                    x: {
                        show: false
                    },
                    y: {
                        show: false
                    },
                }
            });

        }, 275);
        setTimeout(function() {
            var chart = c3.generate({
                bindto: '#pie',
                data: {
                    // iris data from R
                    columns: [
                        ['data1', 11],
                        ['data2', 23],
                        ['data3', 66]
                    ],
                    type : 'pie',
                },
                color: {
                    pattern: ['#5CB85C','#F0AD4E','#3CA2E0']
                },
                legend: {
                    show: 0
                },
            });

        }, 275);
    });
</script> -->

</body>
</html>