package com.jeeplus.modules.app;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeeplus.core.security.Digests;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;
import com.jeeplus.modules.meiguotong.service.compage.ComPageNoService;
import com.jeeplus.modules.meiguotong.service.comprotocol.ComProtocolService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.service.member.MemberService;

import sun.misc.BASE64Decoder;

/**
 * APP工具类包
 * @author xutianlong
 *
 */
@Component
public class AppUtils {

	@Autowired
	private MemberService memberService;
	@Autowired
	private ComPageNoService comPageNoService;
	
	public  Integer getPageSize() {
		Integer pageSize = comPageNoService.getPageSize();
		return pageSize;
	}
	
	/**
	 * 
	 * @param imgStr Base64编码的字符串
	 * @param imgFilePath 图片文件保存的路径
	 * @return
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * 通过uid和time生成key
	 * @param uid
	 * @param time
	 * @return
	 */
	public static String getKeyByUidAndTime(String uid, String time) {
		return Digests.string2MD5(uid+time);
	}
	

	public static void main(String[] args) {
		String  time = getNowTime();
		String key = getKeyByUidAndTime("2",time);
		System.out.println(time);
		System.out.println(key);
	}
	
	/**
	 * 判断key是否正确
	 * @param uid
	 * @param time
	 * @param key
	 * @return 0正常 1用户名或者密码错误  2禁止登录  
	 */
	public Integer keyIsTrue(String uid, String time,String key) {
		/*if (StringUtils.isBlank(uid) || StringUtils.isBlank(time)  || StringUtils.isBlank(key) ) {
			return 1;
		}

		if (!key.equals(Digests.string2MD5(uid+time))) {
			return 1;
		}*/
		/*Member member = memberService.get(uid);
		if(member.getStatus() == 2) {
			return 2;
		}
		if(member.getFatherId() > 0 && memberService.get(member.getFatherId().toString()).getStatus() == 2) {
			return 2;
		}
		*/
		return 0;
	}
	
	/**
	 * 获取time，主要用于登陆成功时需要返回给APP
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

		Date date = new Date();

		String str = simpleDateFormat.format(date);
		
		return str;
	}
	
	
	/**
	 * 随机六位数验证码
	 * @return
	 */
	public static String createVerificationCode() {
		        Random rad=new Random();  
		          
		        String result  = rad.nextInt(1000000) +"";  
		          
		        if(result.length()!=6 ||  result.startsWith("0")){  
		            return createVerificationCode();  
		        }  
		        return result;  
	}
	

	
	/**
	 * MD5加密
	 * @param inStr
	 * @return
	 */
	public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
	
	/**
	 * 有些手机号是不能全部显示的，中间四位一般需要替换成*
	 * @param str
	 */
	public static String updatePhone(String phone){ 
	     String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	     return phoneNumber;
	}
	

	/**
	 * 获取一个没有"-"的uuid
	 * @return
	 */
	public static String randomUUID(){
		
		UUID uuid = UUID.randomUUID();
		
		String u = uuid.toString();
		
		return u.replaceAll("-", "");
	}

	
}
