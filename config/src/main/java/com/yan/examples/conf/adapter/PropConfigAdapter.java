package com.yan.examples.conf.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.yan.examples.conf.Config;
import com.yan.examples.conf.exception.ConfigAdapterException;
import com.yan.examples.conf.util.IOUtils;

/**
 * 解析Properties配置文件
 *
 */
public class PropConfigAdapter extends ConfigAdapter {
	
	@Override
	public Config read(String prop_file) {
		
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(prop_file));
			if (in != null) {
				props.load(in);
			}
			// 解析properties文件
			Set<Entry<Object, Object>> set = props.entrySet();
			Iterator<Map.Entry<Object, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				String fuKey = getWildcard(value);
				if(null != fuKey && null != props.get(fuKey)){
					String fuValue = props.get(fuKey).toString();
					value = value.replaceAll("\\$\\{" + fuKey + "\\}", fuValue);
				}
				configMap.put(key, value);
			}
			return this;
		} catch (IOException e) {
			throw new ConfigAdapterException("load properties file error!");
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	private String getWildcard(String str){
		if(null != str && str.indexOf("${") != -1){
			int start = str.indexOf("${");
			int end = str.indexOf("}");
			if(start != -1 && end != -1){
				return str.substring(start + 2, end);
			}
		}
		return null;
	}
	
}