package com.william.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.william.interfaces.MyConfigFile;

import java.io.*;
import java.lang.reflect.Field;

/**
 * <p>此类是一个工具类，用于读取磁盘中的配置文件。所有配置文件必须有一个实现MyConfigFile接口的javabean对象作为映射。
 * ConfigFileManager将会把这个javabean对象序列化成json并且保存在磁盘中指定位置上。
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2021/10/31 19:35
 * @see com.william.interfaces.MyConfigFile
 */
public class ConfigFileManager {

    /**
     * 磁盘中的配置文件对象。
     */
    private File iniFile;

    /**
     * 转换为json文件的javabean对象。
     */
    private MyConfigFile configFile;

    /**
     * configFile对象的类映射。
     */
    private Class configFileClass;

    /**
     * 数据输入流。
     */
    private BufferedReader br;

    /**
     * 数据写出流。
     */
    private BufferedWriter bw;

    /**
     * 处理json配置文件的gson。
     */
    private Gson gson;

    /**
     * 是否格式化。
     */
    private boolean format;

    /**
     * 有参数构造方法，通过指定配置文件映射对象和文件保存路径构造ConfigFileManager。
     * 可以选择是否格式化Json。
     * 如果文件不存在，则会在磁盘中指定路径创建新文件。
     *
     * @param myConfigFile 配置文件对应的映射对象
     * @param path         配置文件路径
     * @param isFormat     是否格式化
     * @throws IOException 创建文件和创建输入输出流过程中可能产生IO异常
     */
    public ConfigFileManager(MyConfigFile myConfigFile, String path, boolean isFormat) throws IOException {
        iniFile = new File(path);
        gson = new Gson();
        format = isFormat;
        if (!iniFile.exists()) {
            //文件不存在
            iniFile.createNewFile();
            br = new BufferedReader(new FileReader(iniFile));
            bw = new BufferedWriter(new FileWriter(iniFile, false));
            configFile = myConfigFile;
            String jsonStr = gson.toJson(configFile);
            System.out.println(jsonStr);
            if (format)
                bw.write(toFormat(jsonStr));
            else
                bw.write(jsonStr);
            bw.flush();
            bw.close();
        } else {
            String json = "";
            StringBuilder sb = new StringBuilder();
            FileInputStream fileInputStream = new FileInputStream(iniFile);
            byte[] buffer = new byte[1024];
            int count = fileInputStream.read(buffer);
            while (count != -1) {
                sb.append(new String(buffer, 0, count));
                count = fileInputStream.read(buffer);
            }
            json = sb.toString();
            br = new BufferedReader(new FileReader(iniFile));
            bw = new BufferedWriter(new FileWriter(iniFile, false));
            if (json == null || json.equals("")) {
                //有文件，但是是空文件。
                configFile = myConfigFile;
                String jsonStr = gson.toJson(configFile);
                System.out.println(jsonStr);
                if (format)
                    bw.write(toFormat(jsonStr));
                else
                    bw.write(jsonStr);
                bw.flush();
                bw.close();
            } else {
                //有文件并且有内容。
                configFile = gson.fromJson(json, myConfigFile.getClass());
                if (format)
                    bw.write(toFormat(gson.toJson(configFile)));
                else
                    bw.write(gson.toJson(configFile));
                bw.flush();
                bw.close();
            }
        }
        configFileClass = configFile.getClass();
    }

    /**
     * 有参数构造方法，通过指定配置文件映射对象和文件保存路径构造ConfigFileManager。
     * 如果文件不存在，则会在磁盘中指定路径创建新文件。
     *
     * @param myConfigFile 配置文件对应的javabean映射对象
     * @param path         配置文件保存路径
     * @throws IOException 创建文件和创建输入输出流过程中可能产生IO异常
     */
    public ConfigFileManager(MyConfigFile myConfigFile, String path) throws IOException {
        this(myConfigFile, path, false);
    }

    /**
     * 此方法用于读取配置文件中的内容。
     *
     * @param key 配置文件的键。
     * @return 配置文件键对应的值。
     */
    public synchronized Object get(String key) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            return keyField.get(configFile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, String value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, long value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, int value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, double value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, float value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, boolean value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于设置或者添加配置文件项。
     *
     * @param key   配置或修改的键。
     * @param value 修改或者添加的值。
     */
    public synchronized void set(String key, char value) {
        try {
            Field keyField = configFileClass.getDeclaredField(key);
            keyField.setAccessible(true);
            keyField.set(configFile, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用于刷新配置文件。
     *
     * @throws IOException BufferWriter写入过程中可能产生IO异常
     */
    public synchronized void write() throws IOException {
        bw = new BufferedWriter(new FileWriter(iniFile, false));
        if (format)
            bw.write(toFormat(gson.toJson(configFile)));
        else
            bw.write(gson.toJson(configFile));
        bw.flush();
        bw.close();
    }

    private static String toFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson tempGson = new GsonBuilder().setPrettyPrinting().create();
        return tempGson.toJson(jsonObject);
    }
}
