package com.william.test;

/**
 * 此类是此程序的入口，程序默认打印程序介绍。
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2021/10/31 19:35
 * @see com.william.util.ConfigFileManager
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("ConfigFileManager version 1.0.0.0");
        System.out.println("此JAR用于提供配置文件的读写与修改服务。");
        System.out.println("将配置文件的javabean对象和文件位置传入ConfigFileManager构造方法即可。");
    }
}
