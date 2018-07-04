package com.yan.examples.conf.loader;

import com.yan.examples.conf.Config;
import com.yan.examples.conf.adapter.ConfigAdapter;
import com.yan.examples.conf.adapter.PropConfigAdapter;
import com.yan.examples.conf.exception.LoadException;
import com.yan.examples.conf.util.ClassUtils;

/**
 * 配置加载器，用于加载配置文件
 */
public class ConfigLoader {
	
	public static Config load(String conf){
		return load(conf, PropConfigAdapter.class);
	}
	
	public static Config load(String conf, Class<? extends ConfigAdapter> adapter){
		
		if(null == conf || conf.equals("")){
			throw new LoadException("the config file name is null");
		}
		
		if(null == adapter){
			throw new LoadException("the config adapter class is null");
		}
		
		ConfigAdapter configAdapter = (ConfigAdapter) ClassUtils.newInstance(adapter);
		return configAdapter.read(conf);
	}
}
