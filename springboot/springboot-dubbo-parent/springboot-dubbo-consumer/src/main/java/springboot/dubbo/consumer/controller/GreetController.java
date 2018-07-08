package springboot.dubbo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.dubbo.consumer.service.GreetService;

/**
 * <p>
 * GreetController
 * </p>
 *
 */
@RestController
@RequestMapping("/greet")
public class GreetController {
	@Autowired
	private GreetService greetService;

	@GetMapping("")
	public String hello(@RequestParam String name) {
		return greetService.greeting(name);
	}
}
