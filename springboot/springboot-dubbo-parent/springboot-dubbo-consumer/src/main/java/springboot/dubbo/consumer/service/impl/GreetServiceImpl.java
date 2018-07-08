package springboot.dubbo.consumer.service.impl;

import org.springframework.stereotype.Service;

import com.reger.dubbo.annotation.Inject;

import springboot.dubbo.api.service.HelloService;
import springboot.dubbo.consumer.service.GreetService;

/**
 * <p>
 * GreetServiceImpl
 * </p>
 */
@Service
public class GreetServiceImpl implements GreetService {
	@Inject
	private HelloService helloService;

	public String greeting(String name) {
		return helloService.hello(name);
	}
}
