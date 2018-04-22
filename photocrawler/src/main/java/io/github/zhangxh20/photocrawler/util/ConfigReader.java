package io.github.zhangxh20.photocrawler.util;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    
    private static Properties props;
    
    public static AtomicInteger counter = new AtomicInteger(0);
    
    static {
        load();
    }
    private static void load() {
        
        props = new Properties();
        try {
            props.load(ConfigReader.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            logger.info("exception",e);
        }
    }
    
    public static String getString(String key) {
        return props.getProperty(key);
    }
    
    public static String getString(String key,String defaultValue) {
        return props.getProperty(key,defaultValue);
    }
}
