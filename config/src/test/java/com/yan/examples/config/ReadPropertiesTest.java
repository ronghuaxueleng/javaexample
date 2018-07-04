package com.yan.examples.config;

import com.yan.examples.conf.Config;
import com.yan.examples.conf.loader.ConfigLoader;

public class ReadPropertiesTest {
	public static void main(String[] args) {
		Config config = ConfigLoader.load("E:\\codes\\java\\eclipse\\javaexample\\config\\configs\\appconf.properties");
		String name = config.getString("name");
		System.out.println("name = " + name);

		AppConf appConf = config.get(AppConf.class);
		System.out.println(appConf.name());
		System.out.println(appConf.age());
	}
}
