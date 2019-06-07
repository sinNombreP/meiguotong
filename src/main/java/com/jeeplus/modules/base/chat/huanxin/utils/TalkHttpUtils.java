package com.jeeplus.modules.base.chat.huanxin.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jeeplus.modules.base.chat.huanxin.model.Authentic;
import com.jeeplus.modules.base.chat.huanxin.model.TalkNode;
import com.jeeplus.modules.base.chat.huanxin.tool.JsonTool;
import com.jeeplus.modules.base.chat.huanxin.tool._Global;

public class TalkHttpUtils {
	
	public static TalkNode request(String url, int method, Object param, String token, String[][] field) throws Exception {
		CloseableHttpClient client =  HttpClients.createDefault();
		try{
			HttpResponse response = null;
			switch(method){
				case _Global.HTTP_METHOD_GET:
					HttpGet get = new HttpGet(url);
					if (token!=null) {
						get.addHeader("Authorization","Bearer "+token.toString());
					}
					get.addHeader("Content-Type","application/json");
					response = client.execute(get);
					break;
				case _Global.HTTP_METHOD_POST:
					HttpPost post = new HttpPost(url);
					if (token!=null) {
						post.addHeader("Authorization","Bearer "+token.toString());
					}
					if(param!=null){
						post.setEntity(new StringEntity(JsonTool.write(param),"UTF-8"));
					}
					post.addHeader("Content-Type","application/json");
					response = client.execute(post);
					break;
				case _Global.HTTP_METHOD_PUT:
					HttpPut put = new HttpPut(url);
					if (token!=null) {
						put.addHeader("Authorization","Bearer "+token.toString());
					}
					if(param!=null){
						put.setEntity(new StringEntity(JsonTool.write(param),"UTF-8"));
					}
					put.addHeader("Content-Type","application/json");
					response = client.execute(put);
					break;
				case _Global.HTTP_METHOD_DELETE:
					HttpDelete delete = new HttpDelete(url);
					if (token!=null) {
						delete.addHeader("Authorization","Bearer "+token.toString());
					}
					delete.addHeader("Content-Type","application/json");
					response = client.execute(delete);
					break;
				default:throw new Exception("非法请求方式");
			}
			int code = response.getStatusLine().getStatusCode();
			System.out.println("request url = "+url+", statusCode = "+code+".");//TODO 此行可以删除
			if(code==HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				if(entity!=null){
					String json = EntityUtils.toString(entity,"UTF-8");
					if(field!=null&&field.length>0){
						for(String[]temp:field){
							json = json.replace(temp[0],temp[1]);
						}
					}
					System.out.println(json);//TODO 此行可以删除
					return (TalkNode) JsonTool.read(json,TalkNode.class);
				}
			}else{
				return new TalkNode(code);
			}
		}catch(Exception e){
			throw e;
		}finally{
			client.close();
		}
		return null;
	}
	
//	public static TalkNode request(String url, int method, Object param, Authentic auth, String[][] field) throws Exception {
//		CloseableHttpClient client =  HttpClients.createDefault();
//		try{
//			HttpResponse response = null;
//			switch(method){
//				case _Global.HTTP_METHOD_GET:
//					HttpGet get = new HttpGet(url);
//					if(auth!=null){
//						auth.applyAuthentication(get);
//					}
//					get.addHeader("Content-Type","application/json");
//					response = client.execute(get);
//					break;
//				case _Global.HTTP_METHOD_POST:
//					HttpPost post = new HttpPost(url);
//					if(auth!=null){
//						auth.applyAuthentication(post);
//					}
//					if(param!=null){
//						post.setEntity(new StringEntity(JsonTool.write(param),"UTF-8"));
//					}
//					post.addHeader("Content-Type","application/json");
//					response = client.execute(post);
//					break;
//				case _Global.HTTP_METHOD_PUT:
//					HttpPut put = new HttpPut(url);
//					if(put!=null){
//						auth.applyAuthentication(put);
//					}
//					if(param!=null){
//						put.setEntity(new StringEntity(JsonTool.write(param),"UTF-8"));
//					}
//					put.addHeader("Content-Type","application/json");
//					response = client.execute(put);
//					break;
//				case _Global.HTTP_METHOD_DELETE:
//					HttpDelete delete = new HttpDelete(url);
//					if(auth!=null){
//						auth.applyAuthentication(delete);
//					}
//					delete.addHeader("Content-Type","application/json");
//					response = client.execute(delete);
//					break;
//				default:throw new Exception("非法请求方式");
//			}
//			int code = response.getStatusLine().getStatusCode();
//			System.out.println("request url = "+url+", statusCode = "+code+".");//TODO 此行可以删除
//			if(code==HttpStatus.SC_OK){
//				HttpEntity entity = response.getEntity();
//				if(entity!=null){
//					String json = EntityUtils.toString(entity,"UTF-8");
//					if(field!=null&&field.length>0){
//						for(String[]temp:field){
//							json = json.replace(temp[0],temp[1]);
//						}
//					}
//					System.out.println(json);//TODO 此行可以删除
//					return (TalkNode) JsonTool.read(json,TalkNode.class);
//				}
//			}else{
//				return new TalkNode(code);
//			}
//		}catch(Exception e){
//			throw e;
//		}finally{
//			client.close();
//		}
//		return null;
//	}
}
