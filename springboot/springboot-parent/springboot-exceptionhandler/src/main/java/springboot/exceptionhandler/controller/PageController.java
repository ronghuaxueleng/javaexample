package springboot.exceptionhandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springboot.exceptionhandler.exception.DemoPageException;

/**
 * 测试 page 异常处理
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {

	@GetMapping({"", "/"})
	public ModelAndView index() {
		throw new DemoPageException(600, "DemoPageException");
	}
}
