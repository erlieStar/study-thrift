package com.simpledemo.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Config {

    private static Properties properties = new Properties();
    private static final String configName = "config.properties";

    // 静态代码块和静态变量按顺序加载，所以加载配置文件要写在前面

    static {

        try {
            properties.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(configName), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static final String serverMode = Config.getString("server.mode");
    public static final String severHost = Config.getString("server.host");
    public static final int severPort = Config.getInt("server.port");

    public static int getInt(String str) {
        return Integer.parseInt(properties.getProperty(str));
    }

    public static long getLong(String str) {
        return Long.parseLong(properties.getProperty(str));
    }

    public static String getString(String str) {
        return properties.getProperty(str);
    }
}
