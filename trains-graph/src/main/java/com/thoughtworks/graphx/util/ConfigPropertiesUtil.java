package com.thoughtworks.graphx.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件静态类，通过此类读取配置文件中的属性
 * Created by songchanghui on 2019/2/11.
 */
public class ConfigPropertiesUtil {
    public static final String CONF_PATH = "config.properties";
    public static Properties properties = new Properties();
    static{
        try {
            properties.load(new InputStreamReader(ClassLoader.getSystemResourceAsStream(CONF_PATH),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //根据key获取值
    public static  String get(String key){
        return properties.getProperty(key).trim();
    }
    //根据key获取值，值为空则返回defaultValue
    public static  String get(String key,String defaultValue){
        return properties.getProperty(key, defaultValue);
    }
}
