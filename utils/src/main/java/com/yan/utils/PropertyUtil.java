package com.yan.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {
	private static String PATH = "config/sftp.properties";

	public static void setPATH(String pATH) {
		PATH = pATH;
	}
	
	public static void init(String pATH) {
		
	}

	public static String getValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(PATH));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, String> getValue(Map<String, String> map) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(PATH));
			props.load(in);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = props.getProperty(key);
				map.put(key, value);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setValue(String key, String value) {
		Properties prop = new Properties();
		try {
			InputStream fis = PropertyUtil.class.getClassLoader().getResourceAsStream(PATH);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(
					PropertyUtil.class.getClassLoader().getResource("").getPath() + PATH);
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("server", "");
		map.put("user", "");
		map.put("password", "");

		map = PropertyUtil.getValue(map);
		System.out.println(map.get("server"));
		System.out.println(map.get("user"));
		System.out.println(map.get("password"));

	}
}
