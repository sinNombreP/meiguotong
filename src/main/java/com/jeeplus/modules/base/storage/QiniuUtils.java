package com.jeeplus.modules.base.storage;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.config.Global;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * @ClassName: QiniuUtils
 * @Description: 七牛操作工具类
 * @author xcf
 * 
 */
public class QiniuUtils {
	
	//设置好账号的ACCESS_KEY和SECRET_KEY
	public static String ACCESS_KEY = Global.getConfig("access_key_7niu"); 
	public static  String SECRET_KEY = Global.getConfig("secret_key_7niu"); 
	//要上传的空间
	public static String bucketname = Global.getConfig("bucketname_7niu");
	//密钥配置
	public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	//7牛空间存储位置
	public static Zone z = Global.getConfig("zone_7niu")=="1"? Zone.zone1():Zone.zone2();
	//7牛链接地址
	public static String QiniuUrl = Global.getConfig("url_7niu");//"http://oycxze39n.bkt.clouddn.com/";
	public static Configuration c = new Configuration(z);
	public static UploadManager uploadManager = new UploadManager(c);
	private static Logger logger = LoggerFactory.getLogger(QiniuUtils.class);

	/**
	 * @Author: Lanxiaowei(736031305@qq.com)
	 * @Title: uploadFile
	 * @Description: 七牛图片上传
	 * @param @param inputStream    待上传文件输入流
	 * @param @param bucketName     空间名称
	 * @param @param key            空间内文件的key
	 * @param @param mimeType       文件的MIME类型，可选参数，不传入会自动判断
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 * @throws
	 */
	public static String uploadFile(InputStream inputStream,String key) throws IOException {  
		try{
			Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		    String token = auth.uploadToken(bucketname, key);
		    byte[] byteData = IOUtils.toByteArray(inputStream);
		    Response response = uploadManager.put(byteData, key, token);
		    inputStream.close();
		    return response.bodyString();
		}catch (Exception e) {
			logger.info(e.getMessage());
			return "";
		}
		
	}
	
	public static void upload(String FilePath,String key) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, auth.uploadToken(bucketname, key));
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
            	logger.info(e1.getMessage());
                //ignore
            }
        }
    }
}
