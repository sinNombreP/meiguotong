/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.meiguotong.web.statistics;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Pie;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.member.MemberService;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author jeeplus
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/statistics/Statistics")
public class StatisticsController {

	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private SysUserService sysUserService;
	
	/*订单统计页*/
	@RequestMapping(value = "statisticsIndex")
    public String statisticsIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
        //model.addAttribute("dataURL", "/meiguotong/statistics/Statistics/number");
        return "modules/meiguotong/statistics/StatisticsIndex";
    }
	/*订单数量统计页*/
	@RequestMapping(value = "orderNum")
    public String orderNum(HttpServletRequest request, HttpServletResponse response, Model model) {
        //model.addAttribute("dataURL", "/meiguotong/statistics/Statistics/number");
        return "modules/meiguotong/statistics/StatisticsOrderNum";
    }
	
	/*订单数量统计*/
    @ResponseBody
    @RequestMapping("number")
    public GsonOption number(OrderSys orderSys,String begin,String end,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
        GsonOption option = new GsonOption();
        //option.title().text("某地区蒸发量和降水量").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        //option.legend("蒸发量", "降水量");
        //option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(false);
        option.xAxis(new CategoryAxis().data("租车/包车", "短程接送", "接送机", "定制租车", "常规路线", "当地参团", "邮轮", "景点", "导游", "酒店"));
        option.yAxis(new ValueAxis());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(begin)) {
        	orderSys.setBeginDate(format.parse(begin));
		}
        if (StringUtils.isNotBlank(end)) {
			orderSys.setEndDate(format.parse(end));
		}
        
        
        OrderSys order;
        if (orderSys.getType()==1) {//订单量统计
        	Bar bar = new Bar("订单量");
        	order=orderSysService.findOrderNumByDate(orderSys);
        	bar.data(order.getCarNum(), order.getSortNum(), order.getAirNum(), order.getCusNum(), order.getNormalNum(),
        			order.getLocalNum(), order.getLinerNum(), order.getScenicNum(), order.getGuideNum(), order.getHotelNum());
        	option.series(bar);
		}else if (orderSys.getType()==2) {//订单金额统计
			Bar bar = new Bar("金额");
			order=orderSysService.findOrderEDuByDate(orderSys);
			bar.data(order.getCarPrice().setScale(2,BigDecimal.ROUND_HALF_UP),order.getSortPrice().setScale(2,BigDecimal.ROUND_HALF_UP), 
					order.getAirPrice().setScale(2,BigDecimal.ROUND_HALF_UP), order.getCusPrice().setScale(2,BigDecimal.ROUND_HALF_UP), 
					order.getNormalPrice().setScale(2,BigDecimal.ROUND_HALF_UP),order.getLocalPrice().setScale(2,BigDecimal.ROUND_HALF_UP),
					order.getLinerPrice().setScale(2,BigDecimal.ROUND_HALF_UP),order.getScenicPrice().setScale(2,BigDecimal.ROUND_HALF_UP),
					order.getGuidePrice().setScale(2,BigDecimal.ROUND_HALF_UP),order.getHotelPrice().setScale(2,BigDecimal.ROUND_HALF_UP));
			option.series(bar);
		}
        
        //bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
       // bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        /*Bar bar2 = new Bar("降水量");
        List<Double> list = Arrays.asList(2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3);
        bar2.setData(list);
        bar2.markPoint().data(new PointData("年最高", 182.2).xAxis(7).yAxis(183).symbolSize(18), new PointData("年最低", 2.3).xAxis(11).yAxis(3));
        bar2.markLine().data(new PointData().type(MarkType.average).name("平均值"));*/

        
        /*option.series(bar, bar2);*/
        return option;
    }
	
	/*订单比例统计页*/
    @RequestMapping(value = "orderProportion")
    public String orderProportion(HttpServletRequest request, HttpServletResponse response, Model model) {
        //model.addAttribute("dataURL", "/meiguotong/statistics/Statistics/proportion");
        return "modules/meiguotong/statistics/StatisticsOrderProportion";
    }

    /*订单比例统计*/
    @ResponseBody
    @RequestMapping("proportion")
    public GsonOption proportion(OrderSys orderSys,String begin,String end) throws ParseException{
    	GsonOption option = new GsonOption();
        //时间轴
       /* option.timeline().data("2013-01-01", "2013-02-01", "2013-03-01", "2013-04-01", "2013-05-01",
                new LineData("2013-06-01", "emptyStart6", 8), "2013-07-01", "2013-08-01", "2013-09-01", "2013-10-01",
                "2013-11-01", new LineData("2013-12-01", "star6", 8));*/
        option.timeline().autoPlay(false);
        //timeline变态的地方在于多个Option
        Option basic = new Option();
        //basic.title().text("浏览器占比变化").subtext("纯属虚构");
        basic.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        basic.legend().data("租车/包车", "短程接送", "接送机", "定制租车", "常规路线", "当地参团", "邮轮", "景点", "导游", "酒店");
       /* basic.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage, new MagicType(Magic.pie, Magic.funnel)
                .option(new MagicType.Option().funnel(
                        new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548))));*/
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(begin)) {
        	orderSys.setBeginDate(format.parse(begin));
		}
        if (StringUtils.isNotBlank(end)) {
			orderSys.setEndDate(format.parse(end));
		}
        OrderSys order;
        Pie pie=new Pie();
        if (orderSys.getType()==2) {//订单金额比例
        	order=orderSysService.findOrderEDuByDate(orderSys);
        	//int idx = 1;
        	pie.name("金额占比").data(
        			new PieData("租车/包车", order.getCarPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("短程接送", order.getSortPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("接送机", order.getAirPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("定制租车", order.getCusPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("常规路线", order.getNormalPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("当地参团", order.getLocalPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("邮轮", order.getLinerPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("景点", order.getScenicPrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("导游", order.getGuidePrice().setScale(2,BigDecimal.ROUND_HALF_UP)),
                    new PieData("酒店", order.getHotelPrice().setScale(2,BigDecimal.ROUND_HALF_UP))).center("50%", "45%").radius("50%");
        }else if (orderSys.getType()==1) {//订单量比例统计
        	order=orderSysService.findOrderNumByDate(orderSys);
        	//int idx = 1;
        	pie.name("订单量占比").data(
                    new PieData("租车/包车 ", order.getCarNum()),
                    new PieData("短程接送 ", order.getSortNum()),
                    new PieData("接送机 ", order.getAirNum()),
                    new PieData("定制租车 ", order.getCusNum()),
                    new PieData("常规路线 ", order.getNormalNum()),
                    new PieData("当地参团 ", order.getLocalNum()),
                    new PieData("邮轮 ", order.getLinerNum()),
                    new PieData("景点 ", order.getScenicNum()),
                    new PieData("导游 ", order.getGuideNum()),
                    new PieData("酒店 ", order.getHotelNum())).center("50%", "45%").radius("50%");
        }
        
        pie.label().normal().show(true).formatter("{b}{c}({d}%)");
        basic.series(pie);
        //加入
        option.options(basic);
        //构造11个数据
      /*  Option[] os = new Option[11];
        for (int i = 0; i < os.length; i++) {
            os[i] = new Option().series(getPie(idx++));
        }
        option.options(os);*/
        return option;
    }
    
    //会员统计页
    @RequestMapping(value = "memberList")
    public String memberList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //model.addAttribute("dataURL", "/meiguotong/statistics/Statistics/proportion");
        return "modules/meiguotong/statistics/StatisticsMember";
    }
    
    //会员统计
    @ResponseBody
    @RequestMapping("member")
    public GsonOption member(Member member,String begin,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
        GsonOption option = new GsonOption();
        //option.title().text("某地区蒸发量和降水量").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        //option.legend("蒸发量", "降水量");
        //option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(false);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-01");
        Calendar calendar=Calendar.getInstance();
        if (StringUtils.isNotBlank(begin)) {
        	begin=begin+"-01";
        	member.setCurrentDate(begin);
        	calendar.setTime(format.parse(begin));
        	member.setDays(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        	calendar.add(Calendar.MONTH, 1);
        	member.setBeginDate(format.format(calendar.getTime()));
		}
        Bar bar = new Bar("注册量");
    	List<Member> members=memberService.statisticsMemberNum(member);
        CategoryAxis categoryAxis = new CategoryAxis();
        for (int i = 0; i < members.size(); i++) {
        	categoryAxis.data(i+1);
		}
        //option.xAxis(new CategoryAxis().data("租车/包车", "短程接送", "接送机", "定制租车", "常规路线", "当地参团", "邮轮", "景点", "导游", "酒店"));
        option.xAxis(categoryAxis);
        option.yAxis(new ValueAxis());
        
    	for (int i = 0; i < members.size(); i++) {
			bar.data(members.get(i).getNum());
		}
    	option.series(bar);
    	/*bar.data(order.getCarNum(), order.getSortNum(), order.getAirNum(), order.getCusNum(), order.getNormalNum(),
    			order.getLocalNum(), order.getLinerNum(), order.getScenicNum(), order.getGuideNum(), order.getHotelNum());*/
        return option;
    }
    
    //供应商统计页
    @RequestMapping(value = "agentList")
    public String agentList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //model.addAttribute("dataURL", "/meiguotong/statistics/Statistics/proportion");
        return "modules/meiguotong/statistics/StatisticsAgent";
    }
    
    //供应商统计
    @ResponseBody
    @RequestMapping("agent")
    public GsonOption agent(SysUser sysUser,String begin,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
        GsonOption option = new GsonOption();
        //option.title().text("某地区蒸发量和降水量").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        //option.legend("蒸发量", "降水量");
        //option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(false);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-01");
        Calendar calendar=Calendar.getInstance();
        if (StringUtils.isNotBlank(begin)) {
        	begin=begin+"-01";
        	sysUser.setCurrentDate(begin);
        	calendar.setTime(format.parse(begin));
        	sysUser.setDays(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        	calendar.add(Calendar.MONTH, 1);
        	sysUser.setBeginDate(format.format(calendar.getTime()));
		}
        Bar bar = new Bar("注册量");
    	List<SysUser> sysUsers=sysUserService.statisticsAgent(sysUser);
        CategoryAxis categoryAxis = new CategoryAxis();
        for (int i = 0; i < sysUsers.size(); i++) {
        	categoryAxis.data(i+1);
		}
        //option.xAxis(new CategoryAxis().data("租车/包车", "短程接送", "接送机", "定制租车", "常规路线", "当地参团", "邮轮", "景点", "导游", "酒店"));
        option.xAxis(categoryAxis);
        option.yAxis(new ValueAxis());
        
    	for (int i = 0; i < sysUsers.size(); i++) {
			bar.data(sysUsers.get(i).getNum());
		}
    	option.series(bar);
    	/*bar.data(order.getCarNum(), order.getSortNum(), order.getAirNum(), order.getCusNum(), order.getNormalNum(),
    			order.getLocalNum(), order.getLinerNum(), order.getScenicNum(), order.getGuideNum(), order.getHotelNum());*/
        return option;
    }
}
