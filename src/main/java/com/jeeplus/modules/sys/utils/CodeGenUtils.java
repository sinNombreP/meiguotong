package com.jeeplus.modules.sys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class CodeGenUtils {
			
			/**
			 * 生成七位随机数
			 * @return
			 */
		    public static String getId(){  
		        SimpleDateFormat formatter = new SimpleDateFormat("mmssSSS");//分秒豪
		        String formatStr =formatter.format(new Date());
		        return formatStr;  
		    } 
		  
		    /**
		     * 生成六位随机数
		     * @return
		     */
		    public static String getSix(){  
		        Random rad=new Random();  
		          
		        String result  = rad.nextInt(1000000) +"";  
		          
		        if(result.length()!=6){  
		            return getSix();  
		        }  
		        return result;  
		    } 
		    
		    
		    /**
		     * 生成四位随机数
		     * @return
		     */
		    public static String getFour(){  
		        Random rad=new Random();  
		          
		        String result  = rad.nextInt(1000000) +"";  
		          
		        if(result.length()!=4){  
		            return getFour();  
		        }  
		        return result;  
		    } 
		    
	        
		    
		    private static Calendar cal = Calendar.getInstance();
		    /**
		     * 获取年
		     * @return
		     */
		    public static int getYear(){
		    	int year = cal.get(Calendar.YEAR);//获取年份
		    	return year;
		    }
		    /**
		     * 获取月
		     * @return
		     */
		    public static int getMonth(){
		    	return  cal.get(Calendar.MONTH)+1;   //获取月份
		    }
		    /**
		     * 获取日
		     * @return
		     */
		    public static int getDay(){
		    	return  cal.get(Calendar.DATE);//获取天
		    }
		    
		    
		    
		    /**
		     * 年月日时分秒+6位随机数
		     * @return
		     */
		    public static String getPicId(){  
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		        String formatStr =formatter.format(new Date());
		        return formatStr+getSix();  
		    } 
		    
		    /**
		     * 获取时间+四位随机数
		     * @param args
		     * @throws ParseException 
		     */
		    public static String getNowDate() {  
		    	Date date1=new Date();
				long time = date1.getTime()/1000;
				String res = String.valueOf(time);						
				return res+getFour();
		    } 
		    
		    public static void main(String[] args) throws ParseException {
		    	System.out.println(getNowDate());
			}

		}
