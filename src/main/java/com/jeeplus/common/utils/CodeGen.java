package com.jeeplus.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CodeGen {
    public static String getSix(){  
        Random rad=new Random();  
          
        String result  = rad.nextInt(1000000) +"";  
          
        if(result.length()!=6){  
            return getSix();  
        }  
        return result;  
    }
    
    public static String getPicId(){  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String formatStr =formatter.format(new Date());
        return formatStr+getSix();  
    } 
    
    public static void main(String[] args) {
    	System.out.println(getPicId());
	}
}
