package springboot.exceptionhandler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.exceptionhandler.domain.R;
import springboot.exceptionhandler.exception.DemoJsonException;

/**
 * 测试 json 异常处理
 *
 */
@RestController
@RequestMapping("/json")
public class JsonController {

	@SuppressWarnings("rawtypes")
	@GetMapping({"", "/"})
	public R index() {
		throw new DemoJsonException(501, "DemoJsonException");
	}
}
