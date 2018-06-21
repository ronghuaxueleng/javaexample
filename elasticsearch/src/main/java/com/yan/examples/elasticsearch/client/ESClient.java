package com.yan.examples.elasticsearch.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 
 * @author caoqiang
 * @date: 2018年6月14日 下午4:51:18
 */
public class ESClient {
    public static Client getClient(String cluster, Map<String, Integer> address) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", cluster).build();
        
        TransportClient client = new PreBuiltTransportClient(settings);
        if (address != null) {
        	address.keySet().forEach(ip -> {
        		try {
        			int port = address.get(ip);
					client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	});
        }
        return client;
    }
}
