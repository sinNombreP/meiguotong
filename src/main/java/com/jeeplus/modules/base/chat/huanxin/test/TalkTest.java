package com.jeeplus.modules.base.chat.huanxin.test;

import java.io.File;
import java.util.List;
import java.util.Map;

//import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.jeeplus.modules.base.chat.huanxin.model.Authentic;
import com.jeeplus.modules.base.chat.huanxin.model.TalkNode;
import com.jeeplus.modules.base.chat.huanxin.service.TalkDataService;
import com.jeeplus.modules.base.chat.huanxin.service.TalkHttpService;
import com.jeeplus.modules.base.chat.huanxin.service.impl.TalkDataServiceImpl;
import com.jeeplus.modules.base.chat.huanxin.service.impl.TalkHttpServiceImplApache;
import com.jeeplus.modules.base.chat.huanxin.tool.JsonTool;

public class TalkTest {
	public static Authentic.Token TEST_TOKEN = new Authentic.Token("",0);
	public static String TEST_USERNAME = "13922786086";
	public static String TEST_PASSWORD = "123456";
	public static void main1(String[] args) throws Exception {
		//初始服务端Token
		Authentic.Token token = new Authentic(new TalkHttpServiceImplApache()).getToken();
		System.out.println(token.getToken());
		System.out.println(token.getExpire()+"L");
	}
	public static void main(String[] args) throws Exception {
		//通过构造方法注入http请求业务以实现数据业务
		TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
		//修改数据业务Token
		service.setToken(TEST_TOKEN);

//		List<TalkUser> entities = userList.getEntities();
//		for (TalkNode.talu node : userList.getEntities()) {
//			System.out.println(node.getUser().getUsername());
//		}
		//删除
//		System.out.println("删除="+JsonTool.write(service.userDrop(TEST_USERNAME))+"\n");
		//注册
//		System.out.println("注册="+JsonTool.write(service.userSave(TEST_USERNAME,TEST_USERNAME,"上而求索110"))+"\n");
		System.out.println(JsonTool.write(service.userModifyNickname("hello","123")));
		//登录
//		System.out.println("登录="+JsonTool.write(service.login(TEST_USERNAME,TEST_USERNAME))+"\n");
		TalkNode userList = service.userList(100L, "0", 10000L);
		System.out.println(userList.getAccess_token());
	}
	
	//TODO 尚未验证成功！
	//TalkDataService.messageList()
	//TalkDataService.blackDrop()
}