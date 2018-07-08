package springboot.dubbo.provider.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import springboot.dubbo.api.service.HelloService;

/**
 * <p>
 * HelloServiceImpl
 * </p>
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {
	@Value("${server.port}")
	private Integer port;

	public String hello(String name) {
		log.info("Hello, {}, from port: {}",name, port);
		return "Hello, " + name + ", from port: " + port;
	}
}
