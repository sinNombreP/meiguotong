package com.jeeplus.modules.base.pay.alipay;

import java.io.FileWriter;
import java.io.IOException;

import com.jeeplus.common.config.Global;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = Global.getConfig("ali_app_id"); // "2017091808799058";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key =  Global.getConfig("ali_merchant_private_key");//"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdLXVdUYuEMQ+lfnVtzMWPqzhSNr2Ys1nT/RSMmLovFSqwFUGU8Y1xe0/76BrPGKUrYCwKmtNRUSs+qyLMbiPSX7t1yJmobcmkRnNyoCAasc09ESfFt5nyaszSFRGjfilqnWMyEgRi03CS7qzbpywbbzsbfXxJPezC5rlQXv/Mn7ok7A72JmEyOaum+J37J6hdpEw+ABe9U2/aB9K2ZREp0OlllwjEP2sX5SuVCq548JzNL6SBetWdxdGQ6sleJXDOPQgeGHRiZXERYNOrskcZzq+jfmOe0J3rteouZ0eJC3YHRVffDkN2BHT6tntVYRj7I1IBu2rDhuGE+QKrgd0pAgMBAAECggEAEQGhKEaxdM0mJz7mj8nPOntJLcQUgZguSPyiI5BG3N9KbiDupqH4LuYrwlLby7qb7HSMb11PLgY9SpuTuVDVeWa4Tsc0sP+oCKlCxo1CzACe91OK3R6/fnZF0RlMfkchThLXb8x5QwQjsxI2C9IruJAxcgkC0J3tpyz1sheFT+tw0bUREBcoyprJC5G5xkD7AyTVP/4sgGu4oIdV1AnkaZHIs7xKoRZZzMY4+P+x1i+oGtXNmVR4EW329ZWVRxommR2ZR/HdNqUN/DikLAmkLVdfnEIk76ewoKhMFG+p3Pnoxa4HOJKB5Sg5669biD0+mpYG3PVEQ7DurgKv6+mU8QKBgQDbHc+fi7ZPDv3XHC2qG/A8gf3EecUYCxESAVbI4ep0YODDj9/qw2G6JP1kssQyTcPuy+JqtVOxaFL97wjVOWQ97RXsu0JmzxABbJJd5kf2qDMEfysvGQkk5QF2b8+9J5XFV5cjc8f7LlGBg0SHspXblgyN8c1odb+8PkEc3DxZVQKBgQC3opIi6OU5935FT/Riq4bx1BMcejSy/nZ1zyl2TaXPdKHw2rKXXW63qMl106FAbImh0ifYLC6Zpw+IKL2RV+6XBGGdOchmlb4ugXCd0enRsJ9aSVap+E79ZmoOIHAbMwEpY/ILUCvycP7VuL5oehQr9rO4ugnDc5sWcLpa5zmkhQKBgQCWA9nzLI8bn3LxBEzLSyd19bWM/6WN6X29awdEr096cB1SJr2yGCRfchW+DJpAqnc7lYEld5GeQ4OWcUIF4a4cq6Y4FpjGlHsrzm6DNTcNy2hqarphG23kQZl3weNjaKeknWxPjGRdNv/8gKe5k+3/kcJ0qBjurLchpSmlHa9/pQKBgQCdoS9QSBp8Z7Y8SSW+jP6JF0JQb5ZND6Ef/3PX2U87hSJ9XT1wMQOZI4nLWp5Qbtq1zW/mYAagXoc2mcIfwRtNBrXEAJXFeUSHJxVEY6wSE0q2db2uqTckzYS8G5wfvOiCXwimLWuL8PLPAoZvqFsoYdxj9U3sOJAn8xwEa/dPAQKBgFe7Bv8eswQHpmPXAuZ2JTKGLK4Gzx+00kz1FNnnK+nDb0a8pbM8Jf2V3zeq9FzzQuuNuNNSYR8XMd/6ZMN6lhUnwwfl/efe/5z+U9aVMccC6nSEJiGBhTeCi/4rVktXdsJBwenXPXwy6h+ZUvqSEu0gq7QftCX4vaaNRSVvZ4f1";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = Global.getConfig("ali_alipay_public_key");//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA82TMpCOA4g+e3v7NE3XEnJ97dQLIJC8JM5KHyIDqkBrQZAAijZby13Xqd4cUrTS7+rQpoIxd9I7UNi18r6Lzh07I3NIvIfXz5peK5llfsxkuYwII0RwSepinu/D5kSrhs5pq0bj9p2KbqILuNHikNFiUKmajqvTtc3Ot5FVgR6sioWHhEoFdeaIMl3JnGdwmhA+Z6/tHI7H0lWznCDtRaSTF/YKtSsUGMdHlyvJqXZqW59nsTPM2Nzhd1vCPA9h92GCtkMU2BLGJzhWN0MLGHYPMMb/tfsBbIYpUOxYGr5jO5KuApuDLvzsHQXQzIUGxNQqBT41LoeqN4bB2aEO9yQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

//	public static String notify_url = "http://39.108.221.65:8080/xsh_sys/a/sys/alipay/notifyUrl";

    public static String notify_url =  Global.getConfig("ali_notify_url");//"http://39.108.112.1:8080/xsh_sys/a/sys/alipay/notifyUrl";


    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

    //public static String return_url =  "http://39.108.221.65:8080/xsh_sys/a/sys/alipay/returnUrl";

    public static String return_url =  Global.getConfig("ali_return_url");//"http://39.108.112.1:8080/xsh_us/pc/alipayrefund.jsp";


    //	页面跳转URl（验证之后跳转）
    public static String returnPath = "http://39.108.221.65:8080/xsh_us/pc/order-self-a.html";
    //public static String returnPath = "http://localhost:8080/xsh_us/pc/order-self-a.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String log_path = "/";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

