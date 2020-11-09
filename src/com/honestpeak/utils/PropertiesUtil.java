package com.honestpeak.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
	private  static Properties pros = new Properties();                
//    private  String CONFIG_FILE = "properties/time.properties";
    
    
    public static Properties getPropreties(String fileName){
    	try {
    		//pros.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName));  
    		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);  
    		BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));  
    		pros.load(bf);  
    	} catch (IOException e) {  
    		e.printStackTrace();  
    	}  
    	return pros;
    }
   
  
    
    
    /** 
     * 根据key取得对应的value 
     * @param key       配置文件中中的key 
     * @return          配置文件中中value 
     */  
    public  String getProperty(String key){  
        return pros.getProperty(key);  
    }  
}
